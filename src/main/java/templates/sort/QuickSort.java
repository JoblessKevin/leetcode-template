package templates.sort;

import java.util.Random;

/**
 * @formatter:off
 * Time: O(n log n) Avg, O(n^2) Worst | Space: O(log n) recursion stack
 * Note: 雙指針、快取友善、In-place (原地修改)
 * @formatter:on
 */
public class QuickSort {
    private Random random = new Random();

    public int[] sortArray(int[] nums) {
        quickSort(nums, 0, nums.length - 1);
        return nums;
    }

    private void quickSort(int[] nums, int left, int right) {
        if (left >= right)
            return;

        // 隨機選一個索引並換到最右邊作為 pivot，防止面對有序陣列時退化到 O(n^2)
        int pivotIndex = left + random.nextInt(right - left + 1);
        swap(nums, pivotIndex, right);

        // Partition 過程
        int partitionIdx = partition(nums, left, right);

        // 遞迴左右兩半
        quickSort(nums, left, partitionIdx - 1);
        quickSort(nums, partitionIdx + 1, right);
    }

    private int partition(int[] nums, int left, int right) {
        int pivot = nums[right];
        int i = left; // i 追蹤「大於等於 pivot 的區域」的起始位置

        for (int j = left; j < right; j++) {
            if (nums[j] < pivot) {
                swap(nums, i, j);
                i++;
            }
        }
        swap(nums, i, right); // 最後把 pivot 換到正確的分割點
        return i;
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
