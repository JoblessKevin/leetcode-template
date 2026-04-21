package templates.sort;

/**
 * @formatter:off
 * Time: O(n log n) | Space: O(1)
 * Note: 樹狀結構、空間 O(1)、In-place (原地修改)
 * @formatter:on
 */
public class HeapSort {
    public int[] sortArray(int[] nums) {
        int n = nums.length;

        // 1. 建立 Max Heap (從最後一個非葉子節點開始調整)
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(nums, n, i);
        }

        // 2. 拔出最大值（根節點），放到陣列尾端，然後重新調整 heap
        for (int i = n - 1; i > 0; i--) {
            swap(nums, 0, i);
            heapify(nums, i, 0); // 剩餘的範圍繼續 heapify
        }
        return nums;
    }

    private void heapify(int[] nums, int n, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < n && nums[left] > nums[largest])
            largest = left;
        if (right < n && nums[right] > nums[largest])
            largest = right;

        if (largest != i) {
            swap(nums, i, largest);
            heapify(nums, n, largest);
        }
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
