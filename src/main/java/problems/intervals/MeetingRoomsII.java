package problems.intervals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

/**
 * @formatter:off
 * 題目要求： 給定一個區間集合，計算最少需要多少個房間才能容納所有會議。
 * 
 * 核心概念：
 * 1. 排序：按照區間的「開始點」排序。
 * 2. 優先隊列 (Priority Queue)：用一個小頂堆 (Min-Heap) 來追蹤「目前正在使用的房間」以及它們的「結束時間」。
 * 3. 貪婪選擇：掃描每個新會議，如果它能「接手」任何一個已經結束的房間 (即新會議開始時間 >= 舊會議結束時間)，就接手；否則，申請新房間。
 * @formatter:on
 */
public class MeetingRoomsII {
    public int minMeetingRooms(int[][] intervals) {
        if (intervals == null || intervals.length == 0)
            return 0;

        // 1. 排序：按照區間的「開始點」排序
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));

        // 2. 優先隊列 (Min-Heap)：儲存「結束時間」
        // 堆頂永遠是「最早結束」的那個會議
        PriorityQueue<Integer> heap = new PriorityQueue<>();

        // 3. 掃描
        for (int[] interval : intervals) {
            // 檢查：目前這個會議的開始時間，是否大於或等於「最早結束」的會議？
            if (!heap.isEmpty() && interval[0] >= heap.peek()) {
                // 可以接手！把那個舊房間的結束時間拿掉 (poll)
                heap.poll();
            }

            // 無論是否接手，這個會議都要進一個房間，並記錄它的結束時間
            heap.offer(interval[1]);
        }

        // 4. 最終堆的大小，就是需要的房間數
        return heap.size();
    }

    public int minMeetingRooms_sweepLine(int[][] intervals) {
        if (intervals.length <= 1)
            return intervals.length;

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

        int count = 0; // 目前重疊的區間數
        int maxOverlap = 0; // 紀錄峰值

        // 3. 掃描
        for (int[] event : events) {
            count += event[1]; // 進入則 +1，離開則 -1

            // 根據題目需求更新狀態
            maxOverlap = Math.max(maxOverlap, count);
        }

        return maxOverlap;
    }

    // ----------------------------------Overloading--------------------------------------

    public class Interval {
        public int start, end;

        public Interval(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    /**
     * 使用 Priority Queue 統計最大併發數
     */
    public int minMeetingRooms(List<Interval> intervals) {
        if (intervals == null || intervals.isEmpty())
            return 0;

        // 1. 排序：按照區間的「開始點」排序
        Collections.sort(intervals, (a, b) -> Integer.compare(a.start, b.start));

        // 2. 優先隊列 (Min-Heap)：儲存「結束時間」
        PriorityQueue<Integer> heap = new PriorityQueue<>();

        // 3. 掃描
        for (Interval interval : intervals) {
            // 檢查：目前這個會議的開始時間，是否大於或等於「最早結束」的會議？
            if (!heap.isEmpty() && interval.start >= heap.peek()) {
                // 可以接手！把那個舊房間的結束時間拿掉 (poll)
                heap.poll();
            }

            // 無論是否接手，這個會議都要進一個房間，並記錄它的結束時間
            heap.offer(interval.end);
        }

        // 4. 最終堆的大小，就是需要的房間數
        return heap.size();
    }

    /**
     * 使用 Sweep Line 統計最大併發數
     */
    public int minMeetingRooms_sweepLine(List<Interval> intervals) {
        if (intervals == null || intervals.isEmpty())
            return 0;

        // 1. 拆解事件：將 Interval 拆成 Start(+1) 和 End(-1)
        List<int[]> events = new ArrayList<>();
        for (Interval interval : intervals) {
            events.add(new int[] {interval.start, 1}); // 進入
            events.add(new int[] {interval.end, -1}); // 離開
        }

        // 2. 排序：時間相同時，讓 -1 (End) 優先於 1 (Start)
        // 這樣 2 點結束可以剛好給 2 點開始的人用，不需加開房
        Collections.sort(events, (a, b) -> {
            if (a[0] != b[0])
                return Integer.compare(a[0], b[0]);
            return Integer.compare(a[1], b[1]); // -1 排在 1 前面
        });

        // 3. 掃描
        int rooms = 0;
        int maxRooms = 0;
        for (int[] event : events) {
            rooms += event[1];
            maxRooms = Math.max(maxRooms, rooms);
        }

        return maxRooms;
    }

    public static void main(String[] args) {
        MeetingRoomsII solver = new MeetingRoomsII();

        // Test Case 1
        int[][] intervals1 = {{0, 30}, {5, 10}, {15, 20}};
        int result1 = solver.minMeetingRooms(intervals1);
        System.out.println("Test Case 1 Result (Heap): " + result1); // Expected: 2
        int result1_sweep = solver.minMeetingRooms_sweepLine(intervals1);
        System.out.println("Test Case 1 Result (Sweep): " + result1_sweep); // Expected: 2

        // Test Case 2
        int[][] intervals2 = {{7, 10}, {2, 4}};
        int result2 = solver.minMeetingRooms(intervals2);
        System.out.println("Test Case 2 Result (Heap): " + result2); // Expected: 1
        int result2_sweep = solver.minMeetingRooms_sweepLine(intervals2);
        System.out.println("Test Case 2 Result (Sweep): " + result2_sweep); // Expected: 1

        // Test Case 3
        int[][] intervals3 = {{1, 5}, {8, 9}, {8, 9}}; // 兩個 [8, 9] 會重疊
        int result3 = solver.minMeetingRooms(intervals3);
        System.out.println("Test Case 3 Result (Heap): " + result3); // Expected: 2
        int result3_sweep = solver.minMeetingRooms_sweepLine(intervals3);
        System.out.println("Test Case 3 Result (Sweep): " + result3_sweep); // Expected: 2
    }
}
