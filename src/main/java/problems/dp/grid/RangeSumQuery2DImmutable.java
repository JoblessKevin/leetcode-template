package problems.dp.grid;

public class RangeSumQuery2DImmutable {
    public class NumMatrix {
        // 儲存累計面積的 2D 陣列
        private int[][] dp;

        public NumMatrix(int[][] matrix) {
            if (matrix.length == 0 || matrix[0].length == 0)
                return;

            int rows = matrix.length;
            int cols = matrix[0].length;

            // 多開一行一列，避免邊界判斷的 if-else
            dp = new int[rows + 1][cols + 1];

            // 預處理：計算 2D 前綴和
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    dp[i + 1][j + 1] = matrix[i][j] + dp[i][j + 1] + dp[i + 1][j] - dp[i][j];
                }
            }
        }

        public int sumRegion(int r1, int c1, int r2, int c2) {
            // 利用排容原理，O(1) 算出答案
            return dp[r2 + 1][c2 + 1] - dp[r1][c2 + 1] - dp[r2 + 1][c1] + dp[r1][c1];
        }
    }
}
