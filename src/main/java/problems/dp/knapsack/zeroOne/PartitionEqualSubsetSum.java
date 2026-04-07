package problems.dp.knapsack.zeroOne;

/**
 * 0/1 背包問題 (0/1 Knapsack Problem) 規則：每個物品只能選 1 次 (選 或 不選)。
 */
public class PartitionEqualSubsetSum {

    /**
     * @formatter:off
     * Time complexity: O(n*target), Space complexity: O(n*target)
     * @formatter:on
     */
    public boolean canPartition_recursive(int[] nums) {
        int sum = 0;
        for (int n : nums)
            sum += n;
        if (sum % 2 != 0)
            return false; // 奇數不可能平分

        int target = sum / 2;
        // memo[index][target]: 0=未計算, 1=true, 2=false
        byte[][] memo = new byte[nums.length][target + 1];
        return helper(nums, 0, target, memo);
    }

    private boolean helper(int[] nums, int i, int target, byte[][] memo) {
        if (target == 0)
            return true;
        if (i >= nums.length || target < 0)
            return false;
        if (memo[i][target] != 0)
            return memo[i][target] == 1;

        // 兩種選擇：選這個數字 或 不選這個數字
        boolean res = helper(nums, i + 1, target - nums[i], memo)
                                        || helper(nums, i + 1, target, memo);

        memo[i][target] = (byte) (res ? 1 : 2);
        return res;
    }

    /**
     * @formatter:off
     * Time complexity: O(n*target), Space complexity: O(n*target)
     * @formatter:on
     */
    public boolean canPartition_2d(int[] nums) {
        int sum = 0;
        for (int n : nums)
            sum += n;
        if (sum % 2 != 0)
            return false;

        int n = nums.length;
        int target = sum / 2;
        boolean[][] dp = new boolean[n + 1][target + 1];

        // 初始化：目標和為 0 永遠為 true (什麼都不選)
        for (int i = 0; i <= n; i++)
            dp[i][0] = true;

        for (int i = 1; i <= n; i++) {
            int currentNum = nums[i - 1];
            for (int j = 1; j <= target; j++) {
                if (j < currentNum) {
                    dp[i][j] = dp[i - 1][j]; // 裝不下，只能沿用前面的結果
                } else {
                    dp[i][j] = dp[i - 1][j] || dp[i - 1][j - currentNum];
                }
            }
        }
        return dp[n][target];
    }

    /**
     * @formatter:off
     * 空間優化版本 (Space Optimized)
     * Time complexity: O(n*target), Space complexity: O(target)
     * @formatter:on
     */
    public boolean canPartition(int[] nums) {
        int sum = 0;
        for (int n : nums)
            sum += n;
        if (sum % 2 != 0)
            return false;

        int target = sum / 2;
        boolean[] dp = new boolean[target + 1];
        dp[0] = true; // 初始狀態

        for (int num : nums) {
            // 必須從右往左更新！
            for (int j = target; j >= num; j--) {
                if (dp[j - num]) {
                    dp[j] = true;
                }
            }
            // 提早結束優化：如果目標已經達成了，直接回傳
            if (dp[target])
                return true;
        }

        return dp[target];
    }

    public static void main(String[] args) {
        PartitionEqualSubsetSum solver = new PartitionEqualSubsetSum();
        int[] nums1 = {1, 5, 11, 5};
        System.out.println("Test 1: " + solver.canPartition(nums1)); // true

        int[] nums2 = {1, 2, 3, 5};
        System.out.println("Test 2: " + solver.canPartition(nums2)); // false
    }
}
