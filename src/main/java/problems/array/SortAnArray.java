package problems.array;

/**
 * Merge Sort, Time: O(n log n), Space: O(n)
 */
public class SortAnArray {
    public int[] sortArray(int[] nums) {
        if (nums == null || nums.length <= 1)
            return nums;

        // 為了效能優化，只建立一次暫存陣列，避免在遞迴中頻繁 new 陣列
        int[] helper = new int[nums.length];
        mergeSort(nums, 0, nums.length - 1, helper);
        return nums;
    }

    private void mergeSort(int[] nums, int left, int right, int[] helper) {
        if (left >= right)
            return;

        // 算出中點，防止 (left + right) / 2 可能發生的整數溢位
        int mid = left + (right - left) / 2;

        // 【分】：遞迴拆解左半部與右半部
        mergeSort(nums, left, mid, helper);
        mergeSort(nums, mid + 1, right, helper);

        // 【治】：將兩個排序好的部分合併
        merge(nums, left, mid, right, helper);
    }

    private void merge(int[] nums, int left, int mid, int right, int[] helper) {
        // 1. 先將目前的範圍拷貝到 helper 陣列
        for (int i = left; i <= right; i++) {
            helper[i] = nums[i];
        }

        int i = left; // 左半部的讀取指針
        int j = mid + 1; // 右半部的讀取指針
        int k = left; // 寫回原陣列的寫入指針

        // 2. 比較左右兩邊，誰小就先放誰
        while (i <= mid && j <= right) {
            if (helper[i] <= helper[j]) {
                nums[k++] = helper[i++];
            } else {
                nums[k++] = helper[j++];
            }
        }

        // 3. 如果左邊還有剩餘，全部搬回來（右邊剩餘不用搬，因為它們本來就在位置上）
        while (i <= mid) {
            nums[k++] = helper[i++];
        }
    }
}
