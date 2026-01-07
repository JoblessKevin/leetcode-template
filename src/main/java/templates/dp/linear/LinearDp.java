package templates.dp.linear;

import java.util.Arrays;

/**
 * 線性與網格 DP (Linear & Grid DP) 模板
 * -------------------------------------------------------
 * 核心思想：
 * 1. 狀態通常定義為 dp[i] (截至第 i 個元素) 或 dp[i][j] (到達座標 i,j)。
 * 2. 重點在於辨識狀態依賴方向 (前一個、上一個、還是前面所有個)。
 */
public class LinearDp {

    /**
     * Type A: 網格路徑 - 求極值 (Min Path Sum)
     * 規則：只能向下或向右走，求路徑數字總和最小值。
     * 轉移：dp[i][j] = min(上, 左) + 當前值
     */
    public int minPathSum(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        int[][] dp = new int[m][n];

        dp[0][0] = grid[0][0];

        // 1. 初始化邊界 (第一列只能從上面來，第一行只能從左邊來)
        for (int i = 1; i < m; i++) dp[i][0] = dp[i - 1][0] + grid[i][0];
        for (int j = 1; j < n; j++) dp[0][j] = dp[0][j - 1] + grid[0][j];

        // 2. 填滿其餘格子
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + grid[i][j];
            }
        }
        return dp[m - 1][n - 1];
    }

    /**
     * Type B: 網格路徑 - 求方案數 (Unique Paths with Obstacles)
     * 規則：只能向下或向右走，遇到障礙物 (1) 不能走，求到達終點的方法數。
     * 轉移：dp[i][j] = dp[i-1][j] (上) + dp[i][j-1] (左)
     */
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;
        int[][] dp = new int[m][n];

        // 起點如果有障礙，直接無法出發
        if (obstacleGrid[0][0] == 1) return 0;
        dp[0][0] = 1; // 起點只有 1 種方法 (站著不動)

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                // 如果當前是障礙物，方法數為 0，跳過計算
                if (obstacleGrid[i][j] == 1) {
                    dp[i][j] = 0;
                    continue;
                }
                // 略過起點 (已初始化)
                if (i == 0 && j == 0) continue;

                // 來自上方
                if (i > 0) dp[i][j] += dp[i - 1][j];
                // 來自左方
                if (j > 0) dp[i][j] += dp[i][j - 1];
            }
        }
        return dp[m - 1][n - 1];
    }

    /**
     * Type C: 1D 線性 - 非相鄰限制 (House Robber)
     * 規則：不能選相鄰的元素，求最大總和。
     * 轉移：dp[i] = max(不搶這家, 搶這家+前前家)
     */
    public int houseRobber(int[] nums) {
        if (nums.length == 0) return 0;
        int n = nums.length;
        // dp[i] 代表考慮前 i 個房子能搶到的最大值
        int[] dp = new int[n + 1];
        
        dp[0] = 0;
        dp[1] = nums[0];
        
        for (int i = 2; i <= n; i++) {
            // dp[i-1]: 不搶第 i 家 (nums[i-1])
            // dp[i-2] + nums[i-1]: 搶第 i 家 (nums[i-1])，那 i-1 家就不能搶
            dp[i] = Math.max(dp[i - 1], dp[i - 2] + nums[i - 1]);
        }
        return dp[n];
    }

    /**
     * Type D: 1D 線性 - 依賴所有前序狀態 (Longest Increasing Subsequence, LIS)
     * 規則：求最長的嚴格遞增子序列長度。
     * 特點：dp[i] 依賴於所有 j < i 的狀態，複雜度 O(N^2)。
     * 轉移：dp[i] = max(dp[j]) + 1, for all j < i where nums[j] < nums[i]
     */
    public int lengthOfLIS(int[] nums) {
        if (nums.length == 0) return 0;
        int n = nums.length;
        int[] dp = new int[n];
        
        // 每個元素本身至少是一個長度為 1 的子序列
        Arrays.fill(dp, 1);
        int maxLen = 1;

        for (int i = 1; i < n; i++) {
            // 檢查 i 之前的所有元素 j
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    // 如果 nums[i] 能接在 nums[j] 後面，嘗試更新
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            maxLen = Math.max(maxLen, dp[i]);
        }
        return maxLen;
    }

    // ==========================================
    // Test Cases
    // ==========================================
    public static void main(String[] args) {
        LinearDp solver = new LinearDp();

        System.out.println("--- Min Path Sum ---");
        int[][] grid = {{1, 3, 1}, {1, 5, 1}, {4, 2, 1}};
        System.out.println("Result: " + solver.minPathSum(grid)); // Expected: 7 (1->3->1->1->1)

        System.out.println("\n--- Unique Paths with Obstacles ---");
        int[][] obstacleGrid = {{0, 0, 0}, {0, 1, 0}, {0, 0, 0}};
        System.out.println("Paths: " + solver.uniquePathsWithObstacles(obstacleGrid)); // Expected: 2

        System.out.println("\n--- House Robber ---");
        int[] houses = {2, 7, 9, 3, 1};
        // 搶 2, 9, 1 = 12
        System.out.println("Max Money: " + solver.houseRobber(houses)); // Expected: 12

        System.out.println("\n--- Longest Increasing Subsequence (LIS) ---");
        int[] nums = {10, 9, 2, 5, 3, 7, 101, 18};
        // LIS: [2, 3, 7, 101] or [2, 3, 7, 18] -> Length 4
        System.out.println("LIS Length: " + solver.lengthOfLIS(nums)); // Expected: 4
    }
}