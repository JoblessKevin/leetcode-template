package problems.dp.linear;

/**
 * @formatter:off
 * 70. Climbing Stairs
 * 題目描述：
 * 你正在爬樓梯，需要 n 階才能到達頂端。
 * 每次你可以爬 1 階或 2 階。
 * 有多少種不同的方法可以爬到頂端？
 * * 核心概念：
 * 這是一個典型的「動態規劃 (DP)」問題，且與「費波那契數列 (Fibonacci Sequence)」有著密不可分的關係。
 * * 思考方式：
 * 要到達第 n 階，最後一步一定是從哪裡來的？
 * 可能性 A：從第 n-1 階爬 1 階上來。
 * 可能性 B：從第 n-2 階爬 2 階上來。
 * 因此，到達第 n 階的方法總數 = 到達第 n-1 階的方法數 + 到達第 n-2 階的方法數。
 * 遞迴關係式：dp[n] = dp[n-1] + dp[n-2]
 * * 演算法 (迭代優化)：
 * 1. 定義 Base Cases：
 * dp[0] = 1 (站在原地，有 1 種方式)
 * dp[1] = 1 (爬 1 階)
 * dp[2] = 2 (1+1 或 2)
 * 2. 迭代計算：從 i = 2 開始，利用前兩項的值計算當前項，直到 i = n。
 * 3. 空間優化：由於 dp[i] 只依賴於前兩項，我們不需要整個陣列，只需要三個變數 (prev2, prev1, current) 即可，將空間複雜度從 O(N) 降為 O(1)。
 * * 時間複雜度：O(N)。我們只需要從 1 掃描到 N 一次。
 * 空間複雜度：O(1)。只使用固定數量的變數。
 * @formatter:on
 */
public class ClimbStairs {

    /**
     * 暴力遞迴 (Brute Force Recursion) ❌ 缺點：重複計算非常嚴重，時間複雜度呈指數級 (O(2^n))，n 稍大就會超時。
     */
    public int climbStairs_recursive(int n) {
        if (n <= 1)
            return 1;
        return climbStairs_recursive(n - 1) + climbStairs_recursive(n - 2);
    }

    /**
     * 記憶化遞迴 (Memoization - Top-Down DP) 💡 說明：在遞迴的基礎上加一個備忘錄 (Memo)。 每次計算前先檢查備忘錄，如果算過就直接回傳，避免重複計算。
     * 時間複雜度：O(N) 空間複雜度：O(N) (用來存備忘錄和遞迴堆疊)
     */
    public int climbStairs_memo(int n) {
        int[] memo = new int[n + 1];
        return climbStairs_memo_helper(n, memo);
    }

    private int climbStairs_memo_helper(int n, int[] memo) {
        if (n <= 1)
            return 1;
        // 如果已經計算過，直接回傳
        if (memo[n] != 0)
            return memo[n];

        // 沒計算過，遞迴往下算，算完存進 memo
        memo[n] = climbStairs_memo_helper(n - 1, memo) + climbStairs_memo_helper(n - 2, memo);
        return memo[n];
    }

    /**
     * 動態規劃 (Dynamic Programming - Bottom-Up) 💡 說明：從最簡單的 Base Case (第 1 階、第 2 階) 開始往上推算，直到第 n 階。
     * 時間複雜度：O(N) 空間複雜度：O(N) (用來存 dp 陣列)
     */
    public int climbStairs_dp(int n) {
        if (n <= 1)
            return 1;

        int[] dp = new int[n + 1];
        dp[0] = 1; // 站在原地有 1 種方式
        dp[1] = 1; // 爬 1 階有 1 種方式

        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }

        return dp[n];
    }

    /**
     * 動態規劃 (Space Optimized) 💡 說明：觀察 dp[i] = dp[i-1] + dp[i-2]，我們發現只需要前兩項的值，不需要整個陣列。
     * 這是最推薦的解法，因為它既快又省記憶體。 時間複雜度：O(N) 空間複雜度：O(1)
     */
    public int climbStairs(int n) {
        if (n <= 1)
            return 1;

        int prev2 = 1; // 代表 dp[i-2]，初始是 dp[0]
        int prev1 = 1; // 代表 dp[i-1]，初始是 dp[1]
        int current = 0;

        for (int i = 2; i <= n; i++) {
            current = prev1 + prev2;
            prev2 = prev1;
            prev1 = current;
        }

        return current;
    }

    public static void main(String[] args) {
        ClimbStairs solution = new ClimbStairs();

        System.out.println("=== 測試案例 1：標準情況 (n=2) ===");
        int n1 = 2;
        System.out.println("暴力遞迴: " + solution.climbStairs_recursive(n1) + " (預期: 2)");
        System.out.println("記憶化遞迴: " + solution.climbStairs_memo(n1) + " (預期: 2)");
        System.out.println("動態規劃: " + solution.climbStairs_dp(n1) + " (預期: 2)");
        System.out.println("空間優化: " + solution.climbStairs(n1) + " (預期: 2)\n");

        System.out.println("=== 測試案例 2：標準情況 (n=3) ===");
        int n2 = 3;
        System.out.println("暴力遞迴: " + solution.climbStairs_recursive(n2) + " (預期: 3)");
        System.out.println("記憶化遞迴: " + solution.climbStairs_memo(n2) + " (預期: 3)");
        System.out.println("動態規劃: " + solution.climbStairs_dp(n2) + " (預期: 3)");
        System.out.println("空間優化: " + solution.climbStairs(n2) + " (預期: 3)\n");

        System.out.println("=== 測試案例 3：較大數字 (n=10) ===");
        int n6 = 10;
        System.out.println("暴力遞迴: " + solution.climbStairs_recursive(n6) + " (預期: 89)");
        System.out.println("記憶化遞迴: " + solution.climbStairs_memo(n6) + " (預期: 89)");
        System.out.println("動態規劃: " + solution.climbStairs_dp(n6) + " (預期: 89)");
        System.out.println("空間優化: " + solution.climbStairs(n6) + " (預期: 89)\n");
    }
}
