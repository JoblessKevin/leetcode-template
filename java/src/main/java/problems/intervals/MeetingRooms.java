package problems.intervals;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 題目要求： 給定一個區間集合，判斷是否所有區間都互不重疊（即能否在同一個時間內召開所有會議）。
 * 
 * 核心概念： 1. 排序：按照區間的「開始點」排序。 2. 檢查：掃描排序後的區間，如果下一個區間的開始點 < 目前區間的結束點，則表示重疊。
 */
public class MeetingRooms {
    public boolean canAttendMeetings(int[][] intervals) {
        if (intervals.length <= 1)
            return true;

        // 1. 排序：按照區間的「開始點」排序
        // 這是這題的關鍵！只有排序後，我們才能確保「下一個」就是「緊鄰的」潛在重疊者。
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));

        // 2. 掃描
        for (int i = 1; i < intervals.length; i++) {
            int[] currentInterval = intervals[i];
            int[] prevInterval = intervals[i - 1];

            // 檢查重疊：如果下一個區間的開始點 < 目前區間的結束點
            if (currentInterval[0] < prevInterval[1]) {
                // 重疊了！無法同時召開所有會議
                return false;
            }
        }

        // 3. 如果跑完迴圈都沒事，代表完全不重疊
        return true;
    }

    // ----------------------------------Overloading--------------------------------------

    public class Interval {
        public int start, end;

        public Interval(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    public boolean canAttendMeetings(List<Interval> intervals) {
        if (intervals == null || intervals.size() <= 1)
            return true;

        // 1. 排序：Collections.sort 針對 List
        // 使用 Java 8+ 的 Lambda 語法非常簡潔
        Collections.sort(intervals, (a, b) -> Integer.compare(a.start, b.start));

        // 2. 掃描
        for (int i = 1; i < intervals.size(); i++) {
            Interval current = intervals.get(i);
            Interval prev = intervals.get(i - 1);

            // 如果前一個會議還沒結束，下一個就開始了
            if (prev.end > current.start) {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        MeetingRooms solver = new MeetingRooms();

        // Test Case 1
        int[][] intervals1 = {{0, 30}, {5, 10}, {15, 20}}; // [5,10] 和 [0,30] 重疊
        boolean result1 = solver.canAttendMeetings(intervals1);
        System.out.println("Test Case 1 Result: " + result1); // Expected: false

        // Test Case 2
        int[][] intervals2 = {{7, 10}, {2, 4}}; // 不重疊
        boolean result2 = solver.canAttendMeetings(intervals2);
        System.out.println("Test Case 2 Result: " + result2); // Expected: true

        // Test Case 3
        int[][] intervals3 = {{1, 2}, {2, 3}}; // 邊界接觸，不重疊
        boolean result3 = solver.canAttendMeetings(intervals3);
        System.out.println("Test Case 3 Result: " + result3); // Expected: true
    }
}
