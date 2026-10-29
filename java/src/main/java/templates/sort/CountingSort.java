package templates.sort;

/**
 * @formatter:off
 * Time: O(n + k), where k is the range of elements | Space: O(k)
 * Note: 線性時間、範圍小首選 (最快，空間換時間)
 * @formatter:on
 */
public class CountingSort {
    public int[] sortArray(int[] nums) {
        int min = -50000;
        int max = 50000;
        int offset = 50000; // 偏移量，用來處理負數索引

        int[] counts = new int[max - min + 1];

        // 1. 統計每個數字出現次數
        for (int num : nums) {
            counts[num + offset]++;
        }

        // 2. 根據統計結果，重新填回原陣列
        int k = 0;
        for (int i = 0; i < counts.length; i++) {
            while (counts[i] > 0) {
                nums[k++] = i - offset;
                counts[i]--;
            }
        }
        return nums;
    }
}
