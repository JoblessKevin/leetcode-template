package problems.intervals;

import java.util.ArrayList;
import java.util.List;

/**
 * 題目要求： 給定一個不重疊的區間集合，且這些區間已經按照起始點排序。 插入一個新的區間，並確保結果仍然是不重疊且排序好的。
 */
public class InsertInterval {
    public int[][] insert(int[][] intervals, int[] newInterval) {
        List<int[]> result = new ArrayList<>();
        int i = 0;
        int n = intervals.length;

        // 1. 將所有「完全在 newInterval 左側」且不重疊的區間加入結果
        // 條件：interval.end < newInterval.start
        while (i < n && intervals[i][1] < newInterval[0]) {
            result.add(intervals[i]);
            i++;
        }

        // 2. 合併所有與 newInterval 重疊的區間
        // 重疊條件：interval.start <= newInterval.end
        // (因為第 1 步已經排除了 interval.end < newInterval.start 的情況)
        while (i < n && intervals[i][0] <= newInterval[1]) {
            // 更新 newInterval 的邊界
            newInterval[0] = Math.min(newInterval[0], intervals[i][0]);
            newInterval[1] = Math.max(newInterval[1], intervals[i][1]);
            i++;
        }
        // 將合併後的 newInterval 加入結果
        result.add(newInterval);

        // 3. 將所有「完全在 newInterval 右側」的區間加入結果
        while (i < n) {
            result.add(intervals[i]);
            i++;
        }

        return result.toArray(new int[result.size()][]);
    }

    public static void main(String[] args) {
        InsertInterval solver = new InsertInterval();

        // Test Case 1
        int[][] intervals1 = {{1, 2}, {3, 5}, {6, 7}, {8, 10}, {12, 16}};
        int[] newInterval1 = {4, 8};
        int[][] result1 = solver.insert(intervals1, newInterval1);
        System.out.println("Test Case 1 Result:");
        for (int[] interval : result1) {
            System.out.print("[" + interval[0] + ", " + interval[1] + "] ");
        }
        System.out.println(); // Expected: [1, 2] [3, 10] [12, 16]

        // Test Case 2
        int[][] intervals2 = {{1, 3}, {6, 9}};
        int[] newInterval2 = {2, 5};
        int[][] result2 = solver.insert(intervals2, newInterval2);
        System.out.println("Test Case 2 Result:");
        for (int[] interval : result2) {
            System.out.print("[" + interval[0] + ", " + interval[1] + "] ");
        }
        System.out.println(); // Expected: [1, 5] [6, 9]
    }
}
