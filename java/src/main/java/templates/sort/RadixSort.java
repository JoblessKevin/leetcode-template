package templates.sort;

import java.util.Arrays;

/**
 * @formatter:off
 * Time: O(nk), where n is number of elements and k is number of digits. 
 * Space: O(n + d), where d is the base (usually 10).
 * Note: 線性時間、範圍大且數位固定首選
 * @formatter:on
 */
public class RadixSort {
    public int[] sortArray(int[] nums) {
        if (nums == null || nums.length <= 1)
            return nums;

        // 1. 處理負數：找到最小值，將所有數值偏移成非負數（0 到 100,000）
        int min = Arrays.stream(nums).min().getAsInt();
        for (int i = 0; i < nums.length; i++) {
            nums[i] -= min;
        }

        // 2. 找到最大值，確定最高位數
        int max = Arrays.stream(nums).max().getAsInt();

        // 3. 從個位數開始 (exp = 1, 10, 100...)，每一位進行穩定的計數排序
        for (int exp = 1; max / exp > 0; exp *= 10) {
            countingSortByDigit(nums, exp);
        }

        // 4. 還原偏移量
        for (int i = 0; i < nums.length; i++) {
            nums[i] += min;
        }
        return nums;
    }

    private void countingSortByDigit(int[] nums, int exp) {
        int n = nums.length;
        int[] output = new int[n];
        int[] counts = new int[10]; // 每一位只有 0-9

        // 統計當前位數 (digit) 出現的頻率
        for (int num : nums) {
            int digit = (num / exp) % 10;
            counts[digit]++;
        }

        // 計算累積頻率：決定每個數字在 output 陣列中的最後一個位置
        for (int i = 1; i < 10; i++) {
            counts[i] += counts[i - 1];
        }

        // 【關鍵】：從後往前填入，確保排序的穩定性 (Stability)
        for (int i = n - 1; i >= 0; i--) {
            int digit = (nums[i] / exp) % 10;
            output[counts[digit] - 1] = nums[i];
            counts[digit]--;
        }

        // 將排好這一位的結果複製回原陣列
        System.arraycopy(output, 0, nums, 0, n);
    }
}
