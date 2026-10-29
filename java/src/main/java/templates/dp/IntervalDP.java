package templates.dp;

/** Provides a basic structure: dp[l][r] represents the optimal solution for the range [l, r]. */
public class IntervalDP {
    public static int solve(int[] a) {
        int n = a.length;
        int[][] dp = new int[n][n];
        for (int len = 1; len <= n; len++) {
            for (int l = 0; l + len - 1 < n; l++) {
                int r = l + len - 1;
                int best = 0;
                for (int k = l; k <= r; k++) {
                    // best = max(best, dp[l][k-1] + gain(k) + dp[k+1][r]);
                }
                dp[l][r] = best;
            }
        }
        return dp[0][n-1];
    }
}
