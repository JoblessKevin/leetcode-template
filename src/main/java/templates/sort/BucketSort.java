package templates.sort;

/**
 * @formatter:off
 * Time: Best: O(n + k), Worst: O(n^2), O(n log n) | Space: O(n + k)
 * Note: 適用於均勻分佈的資料
 * @formatter:on
 */
public class BucketSort {
    public int[] sortArray(int[] nums) {
        if (nums == null || nums.length <= 1)
            return nums;

        int min = nums[0], max = nums[0];
        for (int num : nums) {
            min = Math.min(min, num);
            max = Math.max(max, num);
        }

        // 1. 定義桶子數量 (這裡設與 n 相同)
        int n = nums.length;
        int bucketCount = n;
        double range = (double) (max - min) / (bucketCount - 1);

        // 2. 統計每個桶子的元素個數 (不存資料，只存次數)
        int[] bucketSizes = new int[bucketCount];
        for (int num : nums) {
            int idx = (range == 0) ? 0 : (int) ((num - min) / range);
            bucketSizes[idx]++;
        }

        // 3. 計算每個桶子在「結果陣列」中的起始偏移量 (Offset)
        int[] offsets = new int[bucketCount];
        for (int i = 1; i < bucketCount; i++) {
            offsets[i] = offsets[i - 1] + bucketSizes[i - 1];
        }

        // 4. 將元素放入「扁平化」的輔助陣列中
        int[] sorted = new int[n];
        int[] currentOffsets = offsets.clone(); // 用來記錄每個桶子目前寫到哪
        for (int num : nums) {
            int idx = (range == 0) ? 0 : (int) ((num - min) / range);
            sorted[currentOffsets[idx]++] = num;
        }

        // 5. 對每個桶子的區間內進行排序 (因為範圍小，用 Insertion Sort 最快)
        for (int i = 0; i < bucketCount; i++) {
            int start = offsets[i];
            int end = (i == bucketCount - 1) ? n : offsets[i + 1];
            if (end - start > 1) {
                insertionSort(sorted, start, end - 1);
            }
        }

        return sorted;
    }

    private void insertionSort(int[] arr, int left, int right) {
        for (int i = left + 1; i <= right; i++) {
            int key = arr[i];
            int j = i - 1;
            while (j >= left && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }
}
