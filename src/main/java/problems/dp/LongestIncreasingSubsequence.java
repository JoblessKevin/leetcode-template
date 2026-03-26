package problems.dp;

import java.util.Arrays;

/**
 * 找最長遞增子序列 (LIS), Sequence (序列) 是一個數學和資料結構上的名詞，指的是「一連串有先後順序的資料」
 */
public class LongestIncreasingSubsequence {
    public int lengthOfLIS(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        // dp[i] 代表以 nums[i] 結尾的最長遞增子序列長度
        int[] dp = new int[nums.length];

        // 初始化：每個元素至少可以構成長度為 1 的遞增子序列（就是它自己）
        Arrays.fill(dp, 1);

        // 遍歷每個元素，嘗試將它接在前面的元素後面
        for (int i = 1; i < nums.length; i++) {
            for (int j = 0; j < i; j++) {
                // 如果前面的元素 nums[j] 小於當前元素 nums[i]
                // 說明 nums[i] 可以接在以 nums[j] 結尾的遞增子序列後面
                if (nums[j] < nums[i]) {
                    // 更新 dp[i]：取目前長度與 (dp[j] + 1) 的較大值
                    // dp[j] + 1 就是將 nums[i] 接在以 nums[j] 結尾的子序列後面
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
        }

        // 遍歷 dp 陣列，找出最大值，這就是整個數組的最長遞增子序列長度
        int maxLength = 0;
        for (int length : dp) {
            maxLength = Math.max(maxLength, length);
        }

        return maxLength;
    }

    public static void main(String[] args) {
        LongestIncreasingSubsequence lis = new LongestIncreasingSubsequence();

        // 測試案例 1：經典案例
        int[] nums1 = {10, 9, 2, 5, 3, 7, 101, 18};
        System.out.println("測試案例 1: " + Arrays.toString(nums1));
        System.out.println("最長遞增子序列長度: " + lis.lengthOfLIS(nums1)); // 預期輸出: 4 (2, 3, 7, 101 或 2, 3,
                                                                    // 7, 18)
        System.out.println("--------------------------------------------------");

        // 測試案例 2：完全遞增
        int[] nums2 = {1, 2, 3, 4, 5};
        System.out.println("測試案例 2: " + Arrays.toString(nums2));
        System.out.println("最長遞增子序列長度: " + lis.lengthOfLIS(nums2)); // 預期輸出: 5
        System.out.println("--------------------------------------------------");

        // 測試案例 3：完全遞減
        int[] nums3 = {5, 4, 3, 2, 1};
        System.out.println("測試案例 3: " + Arrays.toString(nums3));
        System.out.println("最長遞增子序列長度: " + lis.lengthOfLIS(nums3)); // 預期輸出: 1
        System.out.println("--------------------------------------------------");

        // 測試案例 4：包含重複元素
        int[] nums4 = {0, 1, 0, 3, 2, 3};
        System.out.println("測試案例 4: " + Arrays.toString(nums4));
        System.out.println("最長遞增子序列長度: " + lis.lengthOfLIS(nums4)); // 預期輸出: 4 (0, 1, 2, 3)
        System.out.println("--------------------------------------------------");

        // 測試案例 5：所有元素都相同
        int[] nums5 = {7, 7, 7, 7, 7};
        System.out.println("測試案例 5: " + Arrays.toString(nums5));
        System.out.println("最長遞增子序列長度: " + lis.lengthOfLIS(nums5)); // 預期輸出: 1
        System.out.println("--------------------------------------------------");
    }
}
