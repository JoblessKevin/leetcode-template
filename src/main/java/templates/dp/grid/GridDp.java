package templates.dp.grid;

/**
 * 線上性 DP 的基礎上增加一個維度，通常涉及路徑移動。
 * Time Complexity: O(m * n)
 */
public class GridDp {
    public int gridDP(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[][] dp = new int[m][n];

        // 1. 初始化第一行與第一列 (Base Cases)
        dp[0][0] = grid[0][0];
        for (int i = 1; i < m; i++) dp[i][0] = dp[i-1][0] + grid[i][0];
        for (int j = 1; j < n; j++) dp[0][j] = dp[0][j-1] + grid[0][j];

        // 2. 狀態轉移
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                // 只能從上面(i-1)或左邊(j-1)走過來
                dp[i][j] = Math.min(dp[i-1][j], dp[i][j-1]) + grid[i][j];
            }
        }
        return dp[m-1][n-1];
    }
}