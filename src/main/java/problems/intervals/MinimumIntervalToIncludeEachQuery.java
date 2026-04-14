package problems.intervals;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * @formatter:off
 * 題目要求： 給定一個區間集合 intervals 和一個查詢數組 queries。
 * 對於每個查詢 query，找到包含它的「最小長度」區間。
 * 如果沒有區間包含它，返回 -1。
 * 
 * 核心概念：
 * 1. 排序：將 intervals 和 queries 都按照「起始點」排序。
 * 2. 掃描：使用 Sweep Line 的概念，從左向右掃描。
 * 3. 優先隊列 (Min-Heap)：在掃描過程中，維護一個只包含「目前正在活動」且「長度最小」的區間。
 * 
 * 演算法步驟：
 * 1. 排序 intervals 和 queries。
 * 2. 初始化一個 Min-Heap，用來儲存「長度」和「結束點」。
 * 3. 遍歷 queries：
 *    a. 將所有「開始點 <= query」的 intervals 加入 heap。
 *    b. 移除 heap 中「結束點 < query」的 intervals (它們已經失效)。
 *    c. 如果 heap 不為空，heap.peek() 就是目前包含 query 的「最小長度」區間。
 *    d. 否則返回 -1。
 * @formatter:on
 */
public class MinimumIntervalToIncludeEachQuery {
    public int[] minInterval(int[][] intervals, int[] queries) {
        int n = intervals.length;
        int m = queries.length;

        // 1. 排序 intervals (按 start 排序)
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));

        // 2. 排序 queries 並記錄原始索引 (因為要返回對應順序的結果)
        int[][] indexedQueries = new int[m][2];
        for (int i = 0; i < m; i++) {
            indexedQueries[i][0] = queries[i];
            indexedQueries[i][1] = i;
        }
        Arrays.sort(indexedQueries, (a, b) -> Integer.compare(a[0], b[0]));

        int[] result = new int[m];
        Arrays.fill(result, -1);

        // 3. 優先隊列 (Min-Heap)：儲存 {length, end}
        // 按長度排序，長度相同按 end 排序
        PriorityQueue<int[]> minHeap = new PriorityQueue<>((a, b) -> {
            if (a[0] != b[0])
                return Integer.compare(a[0], b[0]);
            return Integer.compare(a[1], b[1]);
        });

        int intervalIndex = 0;

        // 4. 掃描 queries
        for (int[] query : indexedQueries) {
            int queryValue = query[0];
            int originalIndex = query[1];

            // a. 將所有「開始點 <= query」的 intervals 加入 heap
            while (intervalIndex < n && intervals[intervalIndex][0] <= queryValue) {
                int start = intervals[intervalIndex][0];
                int end = intervals[intervalIndex][1];
                int length = end - start + 1;
                minHeap.offer(new int[] {length, end});
                intervalIndex++;
            }

            // b. 移除 heap 中「結束點 < query」的 intervals (它們已經失效)
            while (!minHeap.isEmpty() && minHeap.peek()[1] < queryValue) {
                minHeap.poll();
            }

            // c. 如果 heap 不為空，heap.peek() 就是目前包含 query 的「最小長度」區間
            if (!minHeap.isEmpty()) {
                result[originalIndex] = minHeap.peek()[0];
            }
            // d. 否則返回 -1 (已在初始化時填入)
        }

        return result;
    }

    public static void main(String[] args) {
        MinimumIntervalToIncludeEachQuery solver = new MinimumIntervalToIncludeEachQuery();

        // Test Case 1
        int[][] intervals1 = {{1, 4}, {2, 4}, {3, 6}, {4, 4}};
        int[] queries1 = {2, 3, 4, 5};
        int[] result1 = solver.minInterval(intervals1, queries1);
        System.out.println("Test Case 1 Result: " + Arrays.toString(result1)); // Expected: [3, 3,
                                                                               // 1, 4]

        // Test Case 2
        int[][] intervals2 = {{2, 3}, {2, 5}, {1, 8}, {20, 25}}; // 20-25 不會被用到
        int[] queries2 = {2, 4, 6, 8, 10, 20};
        int[] result2 = solver.minInterval(intervals2, queries2);
        System.out.println("Test Case 2 Result: " + Arrays.toString(result2)); // Expected: [2, 3,
                                                                               // 4, 4, -1, -1]
    }
}
