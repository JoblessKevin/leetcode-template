package problems.scheduling;

import java.util.*;

/**
 * 簡易排程引擎：
 * - Forward pass：計算 ES / EF
 * - Backward pass：計算 LS / LF / Slack
 */
public class SchedulingEngine {

    /**
     * 代表一個任務節點（Work Package）
     */
    public static class Task {
        private final String id;
        private final String name;
        private final int duration; // 工期（天數）
        private final List<String> predecessorIds; // 前置任務 ID 列表

        // 由排程演算法計算出來的欄位：
        private int earlyStart;   // ES
        private int earlyFinish;  // EF
        private int lateStart;    // LS
        private int lateFinish;   // LF
        private int slack;        // 浮時 = LS - ES

        // 方便演算法用的欄位（不對外暴露也可以）
        final List<Task> successors = new ArrayList<>();

        public Task(String id, String name, int duration, List<String> predecessorIds) {
            this.id = id;
            this.name = name;
            this.duration = duration;
            this.predecessorIds = predecessorIds != null ? predecessorIds : new ArrayList<>();
        }

        // === Getter（可以用 Lombok 簡化） ===
        public String getId() { return id; }
        public String getName() { return name; }
        public int getDuration() { return duration; }
        public List<String> getPredecessorIds() { return predecessorIds; }
        public int getEarlyStart() { return earlyStart; }
        public int getEarlyFinish() { return earlyFinish; }
        public int getLateStart() { return lateStart; }
        public int getLateFinish() { return lateFinish; }
        public int getSlack() { return slack; }

        // === Setter 只給 Scheduler 使用 ===
        void setEarlyStart(int earlyStart) { this.earlyStart = earlyStart; }
        void setEarlyFinish(int earlyFinish) { this.earlyFinish = earlyFinish; }
        void setLateStart(int lateStart) { this.lateStart = lateStart; }
        void setLateFinish(int lateFinish) { this.lateFinish = lateFinish; }
        void setSlack(int slack) { this.slack = slack; }

        @Override
        public String toString() {
            return String.format(
                "Task{id=%s, name=%s, dur=%d, ES=%d, EF=%d, LS=%d, LF=%d, slack=%d}",
                id, name, duration, earlyStart, earlyFinish, lateStart, lateFinish, slack
            );
        }
    }

    /**
     * @param tasks           任務列表（必須所有 predecessor 都存在於其中）
     * @param projectStart    專案開始日（用「第幾天」表示，例如 0 代表 Day0）
     * @param fixedProjectEnd 若你有固定的 Deadline，就傳入；沒有就用 null，會自動用 max(EF)
     */
    public static void schedule(List<Task> tasks, int projectStart, Integer fixedProjectEnd) {
        if (tasks == null || tasks.isEmpty()) {
            return;
        }

        // 1. 建 id -> Task map
        Map<String, Task> taskMap = new HashMap<>();
        for (Task t : tasks) {
            taskMap.put(t.getId(), t);
        }

        // 2. 建 successor 關係與 inDegree（拓樸排序用）
        Map<Task, Integer> inDegree = new HashMap<>();
        for (Task t : tasks) {
            inDegree.put(t, 0);
        }

        for (Task t : tasks) {
            for (String predId : t.getPredecessorIds()) {
                Task pred = taskMap.get(predId);
                if (pred == null) {
                    throw new IllegalArgumentException("Predecessor not found: " + predId);
                }
                pred.successors.add(t);
                inDegree.put(t, inDegree.get(t) + 1);
            }
        }

        // 3. 拓樸排序（Kahn's Algorithm）
        List<Task> topoOrder = new ArrayList<>();
        Queue<Task> queue = new ArrayDeque<>();

        // 入度為 0 的先入 queue（沒有 predecessor 的 root 任務）
        for (Map.Entry<Task, Integer> entry : inDegree.entrySet()) {
            if (entry.getValue() == 0) {
                queue.add(entry.getKey());
            }
        }

        while (!queue.isEmpty()) {
            Task cur = queue.poll();
            topoOrder.add(cur);

            for (Task succ : cur.successors) {
                int deg = inDegree.get(succ) - 1;
                inDegree.put(succ, deg);
                if (deg == 0) {
                    queue.add(succ);
                }
            }
        }

        if (topoOrder.size() != tasks.size()) {
            throw new IllegalStateException("圖中存在循環依賴（predecessors 有 cycle）");
        }

        // 4. Forward pass：計算 ES / EF
        // ES = max(pred.EF), 沒 predecessor 則用 projectStart
        for (Task t : topoOrder) {
            int es = projectStart;
            if (!t.getPredecessorIds().isEmpty()) {
                es = Integer.MIN_VALUE;
                for (String predId : t.getPredecessorIds()) {
                    Task pred = taskMap.get(predId);
                    es = Math.max(es, pred.getEarlyFinish());
                }
            }
            int ef = es + t.getDuration();
            t.setEarlyStart(es);
            t.setEarlyFinish(ef);
        }

        // 5. 決定 projectEnd（給 backward pass 用）
        int projectEnd;
        if (fixedProjectEnd != null) {
            projectEnd = fixedProjectEnd;
        } else {
            projectEnd = topoOrder.stream()
                                  .mapToInt(Task::getEarlyFinish)
                                  .max()
                                  .orElse(projectStart);
        }

        // 6. Backward pass：反向計算 LS / LF
        // LF = min(succ.LS)，若沒有 successor 就用 projectEnd
        ListIterator<Task> it = topoOrder.listIterator(topoOrder.size());
        while (it.hasPrevious()) {
            Task t = it.previous();

            int lf;
            if (t.successors.isEmpty()) {
                lf = projectEnd;
            } else {
                lf = Integer.MAX_VALUE;
                for (Task succ : t.successors) {
                    lf = Math.min(lf, succ.getLateStart());
                }
            }
            int ls = lf - t.getDuration();
            t.setLateFinish(lf);
            t.setLateStart(ls);
        }

        // 7. Slack（浮時）= LS - ES
        for (Task t : tasks) {
            int slack = t.getLateStart() - t.getEarlyStart();
            t.setSlack(slack);
        }
    }

    /**
     * Test cases
     */
    public static void main(String[] args) {
        // 範例：
        // A: duration 3, 沒有前置
        // B: duration 4, predecessor = A
        // C: duration 2, predecessor = B
        Task a = new Task("A", "設計", 3, List.of());
        Task b = new Task("B", "開發", 4, List.of("A"));
        Task c = new Task("C", "測試", 2, List.of("B"));

        List<Task> tasks = List.of(a, b, c);

        // 專案從 Day 0 開始，沒有固定 deadline
        schedule(tasks, 0, null);

        System.out.println("=== 排程結果 ===");
        for (Task t : tasks) {
            System.out.println(t);
        }

        System.out.println("=== Critical Path（slack=0）===");
        for (Task t : tasks) {
            if (t.getSlack() == 0) {
                System.out.println("CP: " + t.getId() + " - " + t.getName());
            }
        }
    }
}