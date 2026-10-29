package problems.intervals;

import java.util.Arrays;

/**
 * @formatter:off
 * 題目要求： 給定一個區間集合，移除最少數量的區間，使得剩下的區間互不重疊。
 * 
 * 核心概念：
 * 1. 排序：按照區間的「結束點」排序。
 * 2. 貪婪選擇：優先保留「快要結束」的區間，因為它會給後面的區間留下更多空間。
 * 3. 移除：如果下一個區間的開始點 < 目前保留區間的結束點，則移除下一個區間。
 * @formatter:on
 */
public class NonOverlappingIntervals {
    public int eraseOverlapIntervals(int[][] intervals) {
        if (intervals.length <= 1)
            return 0;

        // 1. 排序：按照區間的「結束點」排序
        // 這是這題的關鍵！我們希望優先處理「快要結束」的區間，
        // 這樣能給後面的區間留下更多空間，達成「最少移除」。
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[1], b[1]));

        int count = 0; // 紀錄被移除的數量
        int currentEnd = intervals[0][1]; // 紀錄目前保留區間的結束點

        // 2. 掃描
        for (int i = 1; i < intervals.length; i++) {
            int[] nextInterval = intervals[i];

            // 檢查重疊：如果下一個區間的開始點 < 目前保留區間的結束點
            if (nextInterval[0] < currentEnd) {
                // 重疊了！因為我們是按照 end 排序的，
                // nextInterval 的 end 一定 >= currentEnd，
                // 所以我們必須移除 nextInterval (或者之前的 currentInterval，但我們已經決定保留 currentInterval)
                count++;
            } else {
                // 不重疊：保留這個 nextInterval，並更新結束點
                currentEnd = nextInterval[1];
            }
        }

        return count;
    }

    public static void main(String[] args) {
        NonOverlappingIntervals solver = new NonOverlappingIntervals();

        // Test Case 1
        int[][] intervals1 = {{1, 2}, {2, 3}, {3, 4}, {1, 3}}; // [1,3] 和 [1,2] 重疊
        int result1 = solver.eraseOverlapIntervals(intervals1);
        System.out.println("Test Case 1 Result: " + result1); // Expected: 1

        // Test Case 2
        int[][] intervals2 = {{1, 2}, {1, 2}, {1, 2}}; // 三個都重疊
        int result2 = solver.eraseOverlapIntervals(intervals2);
        System.out.println("Test Case 2 Result: " + result2); // Expected: 2

        // Test Case 3
        int[][] intervals3 = {{1, 2}, {2, 3}}; // 不重疊
        int result3 = solver.eraseOverlapIntervals(intervals3);
        System.out.println("Test Case 3 Result: " + result3); // Expected: 0
    }
}
