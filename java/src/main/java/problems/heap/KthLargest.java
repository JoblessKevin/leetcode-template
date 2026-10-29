package problems.heap;

import java.util.PriorityQueue;

public class KthLargest {
    private PriorityQueue<Integer> minHeap;
    private int k;

    public KthLargest(int k, int[] nums) {
        this.k = k;
        // 這裡的 initialCapacity 設為 k，是為了效能優化 (避免頻繁擴容)
        this.minHeap = new PriorityQueue<>(k);

        for (int num : nums) {
            add(num);
        }
    }

    public int add(int val) {
        minHeap.offer(val);

        if (minHeap.size() > k) {
            minHeap.poll();
        }

        return minHeap.peek();
    }

    // ==========================================
    // Test cases
    // ==========================================
    public static void main(String[] args) {
            
            System.out.println("====== 測試案例 1 ======");
            int k1 = 3;
            int[] nums1 = {4, 5, 8, 2};
            KthLargest obj1 = new KthLargest(k1, nums1);

            System.out.println("加入 3 -> 回傳: " + obj1.add(3));
            System.out.println("加入 5 -> 回傳: " + obj1.add(5));
            System.out.println("加入 10 -> 回傳: " + obj1.add(10));
            System.out.println("加入 9 -> 回傳: " + obj1.add(9));
            System.out.println("加入 4 -> 回傳: " + obj1.add(4));

            System.out.println("\n====== 測試案例 2 ======");
            int k2 = 4;
            int[] nums2 = {7, 7, 7, 7, 8, 3};
            KthLargest obj2 = new KthLargest(k2, nums2);

            System.out.println("加入 2 -> 回傳: " + obj2.add(2));
            System.out.println("加入 10 -> 回傳: " + obj2.add(10));
            System.out.println("加入 9 -> 回傳: " + obj2.add(9));
            System.out.println("加入 9 -> 回傳: " + obj2.add(9));
    }
}