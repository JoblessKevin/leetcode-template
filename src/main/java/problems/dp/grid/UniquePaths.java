package problems.dp.grid;

import java.util.Arrays;

public class UniquePaths {
    public int uniquePaths(int m, int n) {
        int[][] dp = new int[m][n];

        // 初始化第一行與第一列
        for (int i = 0; i < m; i++)
            dp[i][0] = 1;
        for (int j = 0; j < n; j++)
            dp[0][j] = 1;

        // 填表
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
            }
        }

        return dp[m - 1][n - 1];
    }

    public int uniquePaths_optimized(int m, int n) {
        int[] dp = new int[n];
        Arrays.fill(dp, 1); // 相當於初始化第一列

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                // dp[j] (新) = dp[j] (舊，代表上方) + dp[j-1] (左方)
                dp[j] = dp[j] + dp[j - 1];
            }
        }

        return dp[n - 1];
    }

    public static void main(String[] args) {
        UniquePaths solver = new UniquePaths();

        int m = 3, n = 7;
        System.out.println("Visualizing Grid for " + m + "x" + n + ":");
        System.out.println();

        // 1. 畫出完整的 2D 矩陣
        solver.printUniquePathsGrid(m, n);

        // 2. 顯示 1D 優化版的演進過程
        int result = solver.uniquePaths_optimized_with_print(m, n);
        System.out.println("Final Answer: " + result);
    }

    /**
     * 空間優化 1D DP：打印每一列更新後的狀態
     */
    public int uniquePaths_optimized_with_print(int m, int n) {
        int[] dp = new int[n];
        Arrays.fill(dp, 1);

        System.out.println("--- 1D DP Scanline Process ---");
        System.out.println("Initial (Row 0): " + Arrays.toString(dp));

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[j] = dp[j] + dp[j - 1];
            }
            System.out.println("After Row " + i + ":    " + Arrays.toString(dp));
        }
        System.out.println();
        return dp[n - 1];
    }

    /**
     * 標準 2D DP：畫出完整的矩陣
     */
    public void printUniquePathsGrid(int m, int n) {
        int[][] dp = new int[m][n];

        // 初始化
        for (int i = 0; i < m; i++)
            dp[i][0] = 1;
        for (int j = 0; j < n; j++)
            dp[0][j] = 1;

        // 填表
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
            }
        }

        // 打印矩陣
        System.out.println("--- 2D DP Grid Visualization ---");
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                // 使用 printf 讓數字對齊，看起來更像三角形/矩陣
                System.out.printf("%-4d", dp[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }
}
