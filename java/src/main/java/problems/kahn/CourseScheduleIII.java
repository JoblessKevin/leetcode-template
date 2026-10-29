package problems.kahn;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * Greedy + PriorityQueue
 */
public class CourseScheduleIII {
    public int scheduleCourse(int[][] courses) {
        // 1. 按照課程的「截止日期 (lastDay)」從小到大排序
        // courses[i] = [duration, lastDay]
        Arrays.sort(courses, (a, b) -> a[1] - b[1]);

        // 2. 建立一個 Max Heap，用來儲存「已經修過的課程的 duration」
        // 預設是 Min Heap，所以要改成 (a, b) -> b - a 變成 Max Heap
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> b - a);

        int totalTime = 0; // 記錄目前總共花費了多少天

        // 3. 遍歷排序後的課程
        for (int[] course : courses) {
            int duration = course[0];
            int lastDay = course[1];

            // 先假設我們修了這門課
            totalTime += duration;
            maxHeap.offer(duration);

            // 4. 吃後悔藥機制：如果超時了
            // 把我們目前修過「最耗時」的課退掉 (從總時間扣除，並從 Heap 移除)
            if (totalTime > lastDay) {
                totalTime -= maxHeap.poll();
            }
        }

        // 5. Heap 裡面剩下的元素個數，就是我們最多能修的課程數量
        return maxHeap.size();
    }
}
