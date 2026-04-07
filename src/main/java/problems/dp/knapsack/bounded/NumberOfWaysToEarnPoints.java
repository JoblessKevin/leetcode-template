package problems.dp.knapsack.bounded;

/**
 * 極度少見的 "有上限的背包問題"
 */
public class NumberOfWaysToEarnPoints {
    public int waysToReachTarget(int target, int[][] types) {
        int MOD = 1_000_000_007;
        int[] dp = new int[target + 1];
        dp[0] = 1; // 湊成 0 分的方法只有 1 種（什麼都不做）

        for (int[] type : types) {
            int count = type[0];
            int marks = type[1];

            // 空間優化：從右往左跑 (避免重複使用當前物品)
            for (int j = target; j >= marks; j--) {
                // 關鍵：嘗試選取 1 個到 count 個這類題目
                for (int k = 1; k <= count; k++) {
                    if (j - k * marks >= 0) {
                        dp[j] = (dp[j] + dp[j - k * marks]) % MOD;
                    } else {
                        break; // 超過當前 target，沒必要再試更多的 k
                    }
                }
            }
        }
        return dp[target];
    }

    /**
     * 二進位拆分 (Binary Splitting)
     */
    public int waysToReachTarget_optimized(int target, int[][] types) {
        int MOD = 1_000_000_007;
        int[] dp = new int[target + 1];
        dp[0] = 1;

        for (int[] type : types) {
            int count = type[0];
            int marks = type[1];
            int[] next_dp = dp.clone(); // 使用輔助陣列

            // 依照餘數分組 (0, 1, ..., marks-1)
            for (int rem = 0; rem < marks; rem++) {
                long windowSum = 0;
                for (int j = rem; j <= target; j += marks) {
                    // 將當前位置的值加入視窗
                    // 注意：這裡是用上一輪的 dp
                    windowSum = (windowSum + dp[j]) % MOD;

                    // 如果視窗長度超過 count + 1，減去最左邊那個
                    if (j >= (count + 1) * marks) {
                        windowSum = (windowSum - dp[j - (count + 1) * marks] + MOD) % MOD;
                    }

                    // 更新這一輪的結果 (排除掉「選0個」的情況，或者直接覆蓋)
                    next_dp[j] = (int) windowSum;
                }
            }
            dp = next_dp;
        }
        return dp[target];
    }

    public static void main(String[] args) {
        NumberOfWaysToEarnPoints solver = new NumberOfWaysToEarnPoints();

        // 測試案例 1
        // target = 6, types = [[6,1], [3,2], [2,3]]
        // 預期結果: 7
        int target1 = 6;
        int[][] types1 = {{6, 1}, {3, 2}, {2, 3}};
        System.out.println("Test Case 1:");
        System.out.println("Standard DP: " + solver.waysToReachTarget(target1, types1));
        System.out.println("Optimized DP: " + solver.waysToReachTarget_optimized(target1, types1));

        System.out.println("---");

        // 測試案例 2
        // target = 5, types = [[5,1], [4,3], [2,2]]
        // 預期結果: 4
        int target2 = 5;
        int[][] types2 = {{5, 1}, {4, 3}, {2, 2}};
        System.out.println("Test Case 2:");
        System.out.println("Standard DP: " + solver.waysToReachTarget(target2, types2));
        System.out.println("Optimized DP: " + solver.waysToReachTarget_optimized(target2, types2));
    }
}
