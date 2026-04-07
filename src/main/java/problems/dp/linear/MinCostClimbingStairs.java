package problems.dp.linear;

public class MinCostClimbingStairs {
    public int minCostClimbingStairs(int[] cost) {
        int n = cost.length;

        // 1. 建立 DP 表格，長度為 n + 1 (因為要走到樓頂 n)
        int[] dp = new int[n + 1];

        // 2. Base Case: 起點不需要先付費
        dp[0] = 0;
        dp[1] = 0;

        // 3. 從第 2 階開始推導，直到樓頂 (第 n 階)
        for (int i = 2; i <= n; i++) {
            // 從「前一階」過來 (付出前一階的代價 + 踩在前一階的過路費)
            int step1 = dp[i - 1] + cost[i - 1];
            // 從「前兩階」過來 (付出前兩階的代價 + 踩在前兩階的過路費)
            int step2 = dp[i - 2] + cost[i - 2];

            // 挑選比較便宜的路徑
            dp[i] = Math.min(step1, step2);
        }

        // 4. 回傳到達樓頂的最小花費
        return dp[n];
    }

    public int minCostClimbingStairs_optimized(int[] cost) {
        int n = cost.length;

        // 1. Base Case 初始化 (相當於原本的 dp[0] = 0 和 dp[1] = 0)
        int prev2 = 0; // 站在第 0 階的最小花費
        int prev1 = 0; // 站在第 1 階的最小花費

        // 2. 從第 2 階開始推導，直到樓頂 (第 n 階)
        for (int i = 2; i <= n; i++) {
            // 套用公式，但把 dp[i-1] 換成 prev1，dp[i-2] 換成 prev2
            int step1 = prev1 + cost[i - 1];
            int step2 = prev2 + cost[i - 2];

            // 算出當前這一步的答案
            int current = Math.min(step1, step2);

            // 3. 狀態往前滾動 (為下一次迴圈做準備)
            // 注意順序：必須先把舊的 prev1 交接給 prev2，再把新的 current 交接給 prev1
            prev2 = prev1;
            prev1 = current;
        }

        // 4. 迴圈結束時，視窗剛好滑到終點，prev1 就是樓頂的答案
        return prev1;
    }

    public static void main(String[] args) {
        MinCostClimbingStairs solution = new MinCostClimbingStairs();

        System.out.println("=== 測試案例 1：短樓梯 ===");
        // 說明：從 index 1 開始，支付 15，然後跨 2 步到達樓頂。
        int[] cost1 = {10, 15, 20};
        System.out.println("動態規劃: " + solution.minCostClimbingStairs(cost1) + " (預期: 15)\n");

        System.out.println("=== 測試案例 2：長樓梯 ===");
        // 說明：盡量踩在便宜的 1 上面，避開昂貴的 100。
        int[] cost2 = {1, 100, 1, 1, 1, 100, 1, 1, 100, 1};
        System.out.println("動態規劃: " + solution.minCostClimbingStairs(cost2) + " (預期: 6)\n");

        System.out.println("=== 測試案例 3：邊界情況 (長度只有 2) ===");
        // 說明：題目限制 cost 長度至少為 2。直接從較便宜的 0 開始跨 2 步。
        int[] cost3 = {0, 2};
        System.out.println("動態規劃: " + solution.minCostClimbingStairs(cost3) + " (預期: 0)\n");
    }
}
