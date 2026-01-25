package problems.dp;

import java.util.Arrays;

/**
 * LeetCode 377. Combination Sum IV * 核心觀念： 一般的剪枝 (Pruning) 只能剪掉「死路」(數值 < 0)，但 DP (或是加了備忘錄的
 * Backtracking) 剪掉的是「重複的路」。 Backtracking 的剪枝是為了「不走錯路」； DP 的剪枝是為了「不走回頭路」。
 */
public class CombinationSumIV {

    // 用於 Top-Down 方法的備忘錄
    int[] memo;

    /**
     * 方法 1: Top-Down DP (Recursive + Memoization) 特點：惰性計算 (Lazy Evaluation)，只算需要的狀態。 在這題的 LeetCode
     * 測試資料分佈下，通常跑最快 (0ms)。
     */
    public int combinationSum4_TopDown(int[] nums, int target) {
        Arrays.sort(nums); // 排序為了剪枝
        memo = new int[target + 1];
        Arrays.fill(memo, -1); // -1 代表未計算
        return helper(nums, target);
    }

    private int helper(int[] nums, int remain) {
        if (remain == 0)
            return 1;
        if (memo[remain] != -1)
            return memo[remain]; // 查表：不走回頭路

        int count = 0;
        for (int num : nums) {
            // 剪枝：因為已排序，減下去小於 0 後面都不用看了 (不走錯路)
            if (remain - num < 0) {
                break;
            }
            count += helper(nums, remain - num);
        }

        return memo[remain] = count;
    }

    /**
     * 方法 2: Bottom-Up DP (Iterative) 特點：積極計算 (Eager Evaluation)，由小到大把表格填滿。 這是最標準的 DP 寫法，沒有遞迴深度限制
     * (Stack Overflow) 的風險。
     */
    public int combinationSum4_BottomUp(int[] nums, int target) {
        Arrays.sort(nums); // 排序同樣是為了剪枝優化

        // dp[i] 代表湊出數字 i 的方法數
        int[] dp = new int[target + 1];
        dp[0] = 1; // Base Case: 湊出 0 的方法有一種 (什麼都不選)

        // 外層：從 1 階樓梯爬到 target 階
        for (int i = 1; i <= target; i++) {
            // 內層：嘗試最後一步是哪一個數字
            for (int num : nums) {
                // 剪枝：如果當前目標 i 比 num 還小，代表減下去會是負的
                // 因為 nums 有排序，後面更大的 num 肯定也不行，直接 break
                if (i - num < 0) {
                    break;
                }
                dp[i] += dp[i - num];
            }
        }
        return dp[target];
    }

    /**
     * 方法 3: Pure Backtracking 特點：暴力窮舉，會超時 (Time Limit Exceeded)。 用於理解邏輯與驗證小數據的正確性。
     */
    public int combinationSum4_backtrack(int[] nums, int target) {
        return backtrack(nums, target);
    }

    private int backtrack(int[] nums, int remain) {
        if (remain == 0)
            return 1;
        if (remain < 0)
            return 0;

        int result = 0;
        for (int num : nums) {
            result += backtrack(nums, remain - num);
        }
        return result;
    }

    // ==========================================
    // Main 方法 (Debug 入口)
    // ==========================================
    public static void main(String[] args) {
        CombinationSumIV solver = new CombinationSumIV();

        System.out.println("===== 測試案例 1: 基礎案例 =====");
        int[] nums1 = {1, 2, 3};
        int target1 = 4;
        // 注意：Arrays.sort 會修改陣列，為了測試公平，我們每次傳入前可以視情況 copy，
        // 但這裡我們讓它排序沒關係，因為內容物不變。

        System.out.println("Top-Down Result: " + solver.combinationSum4_TopDown(nums1, target1));
        System.out.println("Bottom-Up Result: " + solver.combinationSum4_BottomUp(nums1, target1));
        System.out.println("Backtrack Result: " + solver.combinationSum4_backtrack(nums1, target1));


        System.out.println("\n===== 測試案例 2: 順序很重要 =====");
        int[] nums2 = {1, 2};
        int target2 = 3; // (1,1,1), (1,2), (2,1) -> 3種

        System.out.println("Top-Down Result: " + solver.combinationSum4_TopDown(nums2, target2));
        System.out.println("Bottom-Up Result: " + solver.combinationSum4_BottomUp(nums2, target2));


        System.out.println("\n===== 測試案例 3: 稍大的 Target (效能差異) =====");
        int[] nums3 = {1, 2, 3};
        int target3 = 35;
        // Target 35 對於純 Backtrack 來說已經是天文數字級別的運算量

        System.out.println("Nums: " + Arrays.toString(nums3) + ", Target: " + target3);

        long start = System.nanoTime();
        int topDownRes = solver.combinationSum4_TopDown(nums3, target3);
        long mid = System.nanoTime();
        int bottomUpRes = solver.combinationSum4_BottomUp(nums3, target3);
        long end = System.nanoTime();

        System.out.println("Top-Down Result:  " + topDownRes + " (Time: " + (mid - start) / 1000
                                        + " us)");
        System.out.println("Bottom-Up Result: " + bottomUpRes + " (Time: " + (end - mid) / 1000
                                        + " us)");

        // 千萬不要跑 Backtrack，會等到天荒地老
        // System.out.println("Backtrack Result: " + solver.combinationSum4_backtrack(nums3,
        // target3));
    }
}
