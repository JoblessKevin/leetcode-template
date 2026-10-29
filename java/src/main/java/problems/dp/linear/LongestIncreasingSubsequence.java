package problems.dp.linear;

import java.util.Arrays;

/**
 * @formatter:off
 * 找最長遞增子序列 (LIS), Sequence (序列) 是一個數學和資料結構上的名詞，指的是「一連串有先後順序的資料」 
 * DP: Time complexity: O(n^2), Space complexity: O(n)
 * @formatter:on
 */
public class LongestIncreasingSubsequence {
    public int lengthOfLIS_dp(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int n = nums.length;
        int[] dp = new int[n];

        for (int i = 0; i < n; i++) {
            dp[i] = 1;
        }

        int maxLen = 1;

        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            maxLen = Math.max(maxLen, dp[i]);
        }

        return maxLen;
    }

    /**
     * @formatter:off
     * Patience Sorting (greedy + binary search)
     * Time complexity: O(nlog(n)), Space complexity: O(n)
     * @formatter:on
     */
    public int lengthOfLIS(int[] nums) {
        if (nums == null || nums.length == 0)
            return 0;

        // tails[i] 表示長度為 i+1 的遞增子序列中最小的結尾數值
        int[] tails = new int[nums.length];
        int size = 0; // 目前有效 tails 的長度

        for (int x : nums) {
            // 使用二分搜尋找第一個大於等於 x 的位置
            // 如果沒找到，binarySearch 會回傳 (-(插入點) - 1)
            int i = Arrays.binarySearch(tails, 0, size, x);

            // 如果找不到精確匹配，轉化為插入點
            if (i < 0) {
                i = -(i + 1);
            }

            // 更新或替換
            tails[i] = x;

            // 如果插入點正好是目前的末端，代表序列長度增加了
            if (i == size) {
                size++;
            }
        }

        return size;
    }

    public static void main(String[] args) {
        LongestIncreasingSubsequence lis = new LongestIncreasingSubsequence();

        // 測試案例 1：經典案例
        int[] nums1 = {10, 9, 2, 5, 3, 7, 101, 18};
        System.out.println("測試案例 1: " + Arrays.toString(nums1));
        System.out.println("最長遞增子序列長度: " + lis.lengthOfLIS(nums1));
        // 預期輸出: 4 (2, 3, 7, 101 或 2, 3, 7, 18)
        System.out.println("--------------------------------------------------");

        // 測試案例 2：完全遞增
        int[] nums2 = {1, 2, 3, 4, 5};
        System.out.println("測試案例 2: " + Arrays.toString(nums2));
        System.out.println("最長遞增子序列長度: " + lis.lengthOfLIS(nums2));
        // 預期輸出: 5
        System.out.println("--------------------------------------------------");

        // 測試案例 3：完全遞減
        int[] nums3 = {5, 4, 3, 2, 1};
        System.out.println("測試案例 3: " + Arrays.toString(nums3));
        System.out.println("最長遞增子序列長度: " + lis.lengthOfLIS(nums3));
        // 預期輸出: 1
        System.out.println("--------------------------------------------------");

        // 測試案例 4：包含重複元素
        int[] nums4 = {0, 1, 0, 3, 2, 3};
        System.out.println("測試案例 4: " + Arrays.toString(nums4));
        System.out.println("最長遞增子序列長度: " + lis.lengthOfLIS(nums4));
        // 預期輸出: 4 (0, 1, 2, 3)
        System.out.println("--------------------------------------------------");

        // 測試案例 5：所有元素都相同
        int[] nums5 = {7, 7, 7, 7, 7};
        System.out.println("測試案例 5: " + Arrays.toString(nums5));
        System.out.println("最長遞增子序列長度: " + lis.lengthOfLIS(nums5));
        // 預期輸出: 1
        System.out.println("--------------------------------------------------");
    }
}
