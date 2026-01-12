package problems.heap;

import java.util.Arrays;
import java.util.PriorityQueue;

public class KClosestPointsToOrigin {

    /**
     * 解法：使用 Max-Heap 維護前 K 個最小距離的點
     * 策略：因為我們要保留 "最小" 的，所以要把 "最大" 的踢出去 -> 用 Max-Heap
     * Time complexity: O(N log K), N: points.length, K: k
     * Space complexity: O(K)
     */
    public int[][] kClosest(int[][] points, int k) {
        // 1. 建立 Max-Heap
        // 比較邏輯：(b 的距離平方) - (a 的距離平方) -> 降序 -> Max-Heap
        PriorityQueue<int[]> maxHeap = new PriorityQueue<>(
                (a, b) -> (b[0] * b[0] + b[1] * b[1]) - (a[0] * a[0] + a[1] * a[1]));

        // 2. 遍歷所有點 (N)
        for (int[] point : points) {
            maxHeap.offer(point); // 加入 Heap: O(log K)

            // 如果超過 K 個，把最遠的 (Root) 踢掉
            if (maxHeap.size() > k) {
                maxHeap.poll(); // 移除 Max: O(log K)
            }
        }

        // 3. 輸出結果 (K)
        int[][] result = new int[k][2];
        for (int i = 0; i < k; i++) {
            result[i] = maxHeap.poll();
        }

        return result;
    }

    // ==========================================
    // Test cases
    // ==========================================
    public static void main(String[] args) {
        KClosestPointsToOrigin solution = new KClosestPointsToOrigin();

        // Test Case 1
        int[][] points1 = { { 1, 3 }, { -2, 2 } };
        int k1 = 1;
        // 距離平方: [1,3]->10, [-2,2]->8. 選小的 -> [-2,2]
        System.out.println("Test Case 1:");
        System.out.println("Input: [[1,3], [-2,2]], k=1");
        System.out.println("Output: " + Arrays.deepToString(solution.kClosest(points1, k1)));

        System.out.println("-----------------");

        // Test Case 2
        int[][] points2 = { { 3, 3 }, { 5, -1 }, { -2, 4 } };
        int k2 = 2;
        // 距離平方: [3,3]->18, [5,-1]->26, [-2,4]->20
        // 前兩名小的: 18 和 20 -> [3,3] 和 [-2,4]
        System.out.println("Test Case 2:");
        System.out.println("Input: [[3,3], [5,-1], [-2,4]], k=2");
        System.out.println("Output: " + Arrays.deepToString(solution.kClosest(points2, k2)));
    }
}