package templates.dp;

/** Weight capacity W; w[i] and v[i] are the weight and value,
 *  respectively; return the maximum total value. */
public class Knapsack01 {
    public static int knapsack(int[] w, int[] v, int W) {
        int n = w.length; int[] dp = new int[W+1];
        for (int i = 0; i < n; i++)
            for (int cap = W; cap >= w[i]; cap--)
                dp[cap] = Math.max(dp[cap], dp[cap - w[i]] + v[i]);
        return dp[W];
    }
}
