package problems.intervals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 題目要求： 給定一個區間集合，合併所有重疊的區間。 Insert Interval: 題目保證輸入的 intervals 已經按起點排好序且沒有重疊。 Merge Intervals: 題目給的
 * intervals 是完全亂序的。 Interval Pointer 版 = 「我是來合併的，我關心塊與塊的關係」。 Interval Sweep Line 版 =
 * 「我是來紀錄變化的，我關心時間點的狀態」。
 */
public class MergeIntervals {
    public int[][] merge(int[][] intervals) {
        if (intervals.length <= 1)
            return intervals;

        // 1. 這是這題的靈魂：沒排過序，Intervals 題就無從談起
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));

        List<int[]> result = new ArrayList<>();
        int[] currentInterval = intervals[0];
        result.add(currentInterval);

        for (int[] nextInterval : intervals) {
            // 如果 current.end >= next.start，代表撞到了！
            if (currentInterval[1] >= nextInterval[0]) {
                // 融合：更新結束時間
                currentInterval[1] = Math.max(currentInterval[1], nextInterval[1]);
            } else {
                // 沒撞到：開啟一個新的區間來作為比對基準
                currentInterval = nextInterval;
                result.add(currentInterval);
            }
        }

        return result.toArray(new int[result.size()][]);
    }

    public int[][] merge_sweepLine(int[][] intervals) {
        if (intervals.length <= 1)
            return intervals;

        // 1. 拆解事件：將每個區間拆成 (時間, 類型)
        // 類型 1 代表開始，-1 代表結束
        List<int[]> events = new ArrayList<>();
        for (int[] interval : intervals) {
            events.add(new int[] {interval[0], 1}); // 開始
            events.add(new int[] {interval[1], -1}); // 結束
        }

        // 2. 排序：先按時間排，如果時間相同，讓「開始(1)」排在「結束(-1)」前面
        // 這樣 [1, 2] 和 [2, 3] 在時間點 2 的時候，count 會是 1 -> 2 -> 1，不會斷掉
        Collections.sort(events, (a, b) -> {
            if (a[0] != b[0])
                return a[0] - b[0];
            return b[1] - a[1]; // 讓 1 排在 -1 前面
        });

        List<int[]> result = new ArrayList<>();
        int count = 0;
        int start = 0;

        // 3. 掃描
        for (int[] event : events) {
            if (count == 0) {
                // 從 0 變 1，紀錄大起點
                start = event[0];
            }

            count += event[1];

            if (count == 0) {
                // 從 1 變回 0，大區間結束，收錄結果
                result.add(new int[] {start, event[0]});
            }
        }

        return result.toArray(new int[result.size()][]);
    }

    public static void main(String[] args) {
        MergeIntervals solver = new MergeIntervals();

        // Test Case 1
        int[][] intervals1 = {{1, 3}, {2, 6}, {8, 10}, {15, 18}};
        int[][] result1 = solver.merge(intervals1);
        System.out.println("Test Case 1 Result:");
        for (int[] interval : result1) {
            System.out.print("[" + interval[0] + ", " + interval[1] + "] ");
        }
        System.out.println(); // Expected: [1, 6] [8, 10] [15, 18]

        // Test Case 2
        int[][] intervals2 = {{1, 4}, {4, 5}};
        int[][] result2 = solver.merge(intervals2);
        System.out.println("Test Case 2 Result:");
        for (int[] interval : result2) {
            System.out.print("[" + interval[0] + ", " + interval[1] + "] ");
        }
        System.out.println(); // Expected: [1, 5]
    }
}
