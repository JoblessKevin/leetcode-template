package problems.dp.grid;

public class UniquePathsII {
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;

        if (obstacleGrid[0][0] == 1)
            return 0;

        int[][] dp = new int[m][n];
        // 1. 初始化起點
        dp[0][0] = 1;

        // 2. 初始化第一行 (第一列)：只有左邊能到且這格沒障礙，才是 1
        for (int i = 1; i < m; i++) {
            dp[i][0] = (obstacleGrid[i][0] == 0 && dp[i - 1][0] == 1) ? 1 : 0;
        }

        // 3. 初始化第一列 (第一行)：只有左邊能到且這格沒障礙，才是 1
        for (int j = 1; j < n; j++) {
            dp[0][j] = (obstacleGrid[0][j] == 0 && dp[0][j - 1] == 1) ? 1 : 0;
        }

        // 4. 填滿剩餘的格子
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (obstacleGrid[i][j] == 1) {
                    dp[i][j] = 0; // 遇到障礙物，此路不通
                } else {
                    // 狀態轉移方程式
                    dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
                }
            }
        }

        return dp[m - 1][n - 1];
    }

    public int uniquePathsWithObstacles_optimized(int[][] obstacleGrid) {
        int n = obstacleGrid[0].length;
        int[] dp = new int[n];

        // 1. 初始化起點：如果起點沒障礙物，設為 1
        dp[0] = (obstacleGrid[0][0] == 0) ? 1 : 0;

        // 2. 遍歷每一行 (Row)
        for (int i = 0; i < obstacleGrid.length; i++) {
            for (int j = 0; j < n; j++) {
                // 如果當前格子是障礙物
                if (obstacleGrid[i][j] == 1) {
                    dp[j] = 0;
                    continue; // 處理下一格
                }

                // 如果不是障礙物，且不是該行的第一格 (j > 0)
                // 這裡的 dp[j] 代表上方，dp[j-1] 代表左方
                if (j > 0) {
                    dp[j] = dp[j] + dp[j - 1];
                }

                // 註：當 j == 0 時，dp[0] 不需要額外加法，
                // 因為它只會從上方 (舊的 dp[0]) 傳下來。
                // 如果上方是障礙物，之前的 loop 就會把 dp[0] 設為 0 了。
            }
        }

        return dp[n - 1];
    }

    public static void main(String[] args) {
        UniquePathsII solver = new UniquePathsII();

        // 測試案例：3x3 網格，中間有一個障礙物
        // [0, 0, 0]
        // [0, 1, 0]
        // [0, 0, 0]
        int[][] obstacleGrid1 = {{0, 0, 0}, {0, 1, 0}, {0, 0, 0}};

        System.out.println("=== Test Case 1: Middle Obstacle ===");
        printInputGrid(obstacleGrid1);
        solver.uniquePathsWithObstacles(obstacleGrid1);
        System.out.println();
        solver.uniquePathsWithObstacles_optimized(obstacleGrid1);

        int result2D = solver.uniquePathsWithObstacles(obstacleGrid1);
        System.out.println("\n[2D 版最終答案]: " + result2D);

        int result1D = solver.uniquePathsWithObstacles_optimized(obstacleGrid1);
        System.out.println("\n[1D 版最終答案]: " + result1D);

        System.out.println("\n" + "=".repeat(30) + "\n");
    }

    // 輔助方法：印出原始地圖 (障礙物用 X 表示)
    private static void printInputGrid(int[][] grid) {
        System.out.println("Input Obstacle Map (X = Obstacle):");
        for (int[] row : grid) {
            for (int cell : row) {
                System.out.print(cell == 1 ? " [X] " : " [ ] ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
