package problems.heap;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Random;

public class KthLargestElementInAnArray {
    /**
     * Standard solution: Min-Heap
     * Strategy: Maintain a heap of size K. The root is the Kth largest.
     * Time complexity: O(N log K)
     * Space complexity: O(K)
     */
    public int findKthLargest_heap(int[] nums, int k) {
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();

        for (int num : nums) {
            minHeap.offer(num);

            // 如果堆積大小超過 k，代表目前的 root 是 "第 k+1 大" (或更小) 的元素，
            // 它沒資格待在前 k 大的俱樂部裡，踢掉它。
            if (minHeap.size() > k) {
                minHeap.poll();
            }
        }

        return minHeap.peek();
    }

    /**
     * Optimal Solution: Quick Select (with Randomization)
     * Strategy: Partition logic similar to QuickSort, but drop half the array.
     * Time complexity: O(N) average, O(N^2) worst case (extremely rare with random
     * pivot)
     * Space complexity: O(1)
     */
    private static final Random rand = new Random();

    public int findKthLargest_quickSelect(int[] nums, int k) {
        int left = 0;
        int right = nums.length - 1;

        // 為了方便理解，我們這次不做 index 轉換
        // 直接找 "第 k 大" 的數
        while (left <= right) {
            // 隨機選 Pivot
            int pivot = nums[left + rand.nextInt(right - left + 1)];

            // 定義三指針
            int lt = left; // lt 左邊都是 "大於" Pivot 的
            int gt = right; // gt 右邊都是 "小於" Pivot 的
            int i = left; // i 是當前掃描指針

            // 3-Way Partition (降序: 大 -> 中 -> 小)
            while (i <= gt) {
                if (nums[i] > pivot) {
                    swap(nums, i, lt);
                    lt++;
                    i++;
                } else if (nums[i] < pivot) {
                    swap(nums, i, gt);
                    gt--;
                    // 注意：這裡 i 不用 ++，因為換過來的數字還沒檢查過
                } else {
                    // nums[i] == pivot
                    i++;
                }
            }

            // 現在陣列變成了：
            // [left ... lt-1] -> 大於 Pivot
            // [lt ... gt] -> 等於 Pivot (這區不用再排了！)
            // [gt+1 ... right] -> 小於 Pivot

            // 判斷第 k 大落在哪一區
            if (k - 1 < lt) {
                // 答案在 "大於區"
                right = lt - 1;
            } else if (k - 1 > gt) {
                // 答案在 "小於區"
                left = gt + 1;
            } else {
                // 答案就在 "等於區"，中了！直接回傳
                return nums[lt];
            }
        }
        return -1;
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    // ==========================================
    // Test cases
    // ==========================================
    public static void main(String[] args) {
        KthLargestElementInAnArray solution = new KthLargestElementInAnArray();

        // Case 1
        int[] nums1 = { 3, 2, 1, 5, 6, 4 };
        int k1 = 2;
        System.out.println("Test Case 1 Array: " + Arrays.toString(nums1));
        System.out.println("Heap Solution (2nd Largest): " + solution.findKthLargest_heap(nums1, k1));
        // 注意：QuickSelect 會改變原本陣列的順序 (In-place)
        System.out.println("QuickSelect Solution (2nd Largest): " + solution.findKthLargest_quickSelect(nums1, k1));
        System.out.println("--------------------------------------------------");

        // Case 2
        int[] nums2 = { 3, 2, 3, 1, 2, 4, 5, 5, 6 };
        int k2 = 4;
        System.out.println("Test Case 2 Array: " + Arrays.toString(nums2));
        System.out.println("Heap Solution (4th Largest): " + solution.findKthLargest_heap(nums2, k2));
        // 為了公平測試，重新複製一份陣列給 QuickSelect，因為 nums2 可能已經被改動
        int[] nums2Copy = { 3, 2, 3, 1, 2, 4, 5, 5, 6 };
        System.out.println("QuickSelect Solution (4th Largest): " + solution.findKthLargest_quickSelect(nums2Copy, k2));
    }
}