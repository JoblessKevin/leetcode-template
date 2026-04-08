package problems.dp.knapsack.zeroOne;

import java.util.Arrays;

public class TargetSum {
    public int findTargetSumWays_Direct(int[] nums, int target) {
        int sum = 0;
        for (int n : nums)
            sum += n;

        // 基礎邊界檢查
        if (Math.abs(target) > sum)
            return 0;

        int n = nums.length;
        // 偏移量，讓總和 0 對應到 dp 索引的 sum 位置
        int offset = sum;
        int[][] dp = new int[n + 1][2 * sum + 1];

        // 初始狀態：什麼都不選，總和為 0 的方法有 1 種
        dp[0][offset] = 1;

        for (int i = 1; i <= n; i++) {
            int num = nums[i - 1];
            for (int j = 0; j <= 2 * sum; j++) {
                // 如果上一個狀態有值，就分別嘗試 +num 和 -num
                if (dp[i - 1][j] > 0) {
                    dp[i][j + num] += dp[i - 1][j];
                    dp[i][j - num] += dp[i - 1][j];
                }
            }
        }
        return dp[n][target + offset];
    }

    public int findTargetSumWays_Optimized(int[] nums, int target) {
        int sum = 0;
        for (int n : nums)
            sum += n;

        // 數學檢查：(target + sum) 必須是偶數且不為負數
        if ((target + sum) % 2 != 0 || Math.abs(target) > sum) {
            return 0;
        }

        int P = (target + sum) / 2;
        int[] dp = new int[P + 1];

        // 初始狀態：湊出總和 0 的方法只有 1 種 (什麼都不選)
        dp[0] = 1;

        for (int num : nums) {
            // 0/1 背包空間優化：必須「從後往前」遍歷，避免重複計算同一個數字
            for (int j = P; j >= num; j--) {
                dp[j] = dp[j] + dp[j - num];
            }
        }

        return dp[P];
    }

    public static void main(String[] args) {
        TargetSum solver = new TargetSum();

        // 案例 1: 標準案例
        int[] nums1 = {1, 1, 1, 1, 1};
        int target1 = 3;
        runTest(solver, nums1, target1, 1);

        // 案例 2: 包含不同數值的數字
        int[] nums2 = {1, 2, 3};
        int target2 = 0; // P = (0 + 6) / 2 = 3. 組合有 {1,2} 和 {3}
        runTest(solver, nums2, target2, 2);
    }

    private static void runTest(TargetSum solver, int[] nums, int target, int caseNum) {
        System.out.println("===== Test Case " + caseNum + " =====");
        System.out.println("Nums: " + Arrays.toString(nums) + ", Target: " + target);

        int directResult = solver.findTargetSumWays_Direct(nums, target);
        int optResult = solver.findTargetSumWays_Optimized(nums, target);

        System.out.println("Direct Result: " + directResult);
        System.out.println("Optimized Result: " + optResult);
        System.out.println();
    }
}
