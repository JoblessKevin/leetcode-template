package problems.math;

public class SetMatrixZeroes {
    public void setZeroes(int[][] matrix) {
        int ROWS = matrix.length; // Row count
        int COLS = matrix[0].length; // Column count

        // 為了使用 matrix 的第一 Row 和第一 Column 作為標記區
        // 我們需要額外兩個變數來記錄它們「原本」是否包含 0
        boolean firstRowHasZero = false;
        boolean firstColHasZero = false;

        // 1. 檢查第一 Column (matrix[0...m-1][0]) 是否原本就有 0
        for (int i = 0; i < ROWS; i++) {
            if (matrix[i][0] == 0) {
                firstColHasZero = true;
                break;
            }
        }

        // 2. 檢查第一 Row (matrix[0][0...n-1]) 是否原本就有 0
        for (int j = 0; j < COLS; j++) {
            if (matrix[0][j] == 0) {
                firstRowHasZero = true;
                break;
            }
        }

        // 3. 開始「投影標記」：遍歷其餘所有位置 (Row 1~m-1, Col 1~n-1)
        // 如果發現 0，就在該 Row 的開頭與該 Column 的頂端做上記號
        for (int i = 1; i < ROWS; i++) {
            for (int j = 1; j < COLS; j++) {
                if (matrix[i][j] == 0) {
                    matrix[i][0] = 0; // 在該 Row 的起點標記
                    matrix[0][j] = 0; // 在該 Column 的頂端標記
                }
            }
        }

        // 4. 根據標記進行「塗黑」：遍歷內部區域
        // 如果 Row 起點或 Column 頂端有標記，則將該格變為 0
        for (int i = 1; i < ROWS; i++) {
            for (int j = 1; j < COLS; j++) {
                if (matrix[i][0] == 0 || matrix[0][j] == 0) {
                    matrix[i][j] = 0;
                }
            }
        }

        // 5. 最後處理第一 Row 與第一 Column 的「原罪」
        // 如果第一 Column 原本就有 0，整條變 0
        if (firstColHasZero) {
            for (int i = 0; i < ROWS; i++)
                matrix[i][0] = 0;
        }
        // 如果第一 Row 原本就有 0，整條變 0
        if (firstRowHasZero) {
            for (int j = 0; j < COLS; j++)
                matrix[0][j] = 0;
        }
    }

    public void setZeroes_optimized(int[][] matrix) {
        int ROWS = matrix.length, COLS = matrix[0].length;
        boolean rowZero = false;

        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                if (matrix[r][c] == 0) {
                    matrix[0][c] = 0;
                    if (r > 0) {
                        matrix[r][0] = 0;
                    } else {
                        rowZero = true;
                    }
                }
            }
        }

        for (int r = 1; r < ROWS; r++) {
            for (int c = 1; c < COLS; c++) {
                if (matrix[0][c] == 0 || matrix[r][0] == 0) {
                    matrix[r][c] = 0;
                }
            }
        }

        if (matrix[0][0] == 0) {
            for (int r = 0; r < ROWS; r++) {
                matrix[r][0] = 0;
            }
        }

        if (rowZero) {
            for (int c = 0; c < COLS; c++) {
                matrix[0][c] = 0;
            }
        }
    }

    // @formatter:off
    public static void main(String[] args) {
        SetMatrixZeroes solver = new SetMatrixZeroes();

        // Test Case 1: 基本情況
        int[][] matrix1 = {
                {1, 1, 1},
                {1, 0, 1},
                {1, 1, 1}
        };
        System.out.println("Original Matrix 1:");
        printMatrix(matrix1);
        solver.setZeroes(matrix1);
        System.out.println("Modified Matrix 1:");
        printMatrix(matrix1);
        // Expected:
        // [1, 0, 1]
        // [0, 0, 0]
        // [1, 0, 1]

        System.out.println("--------------------------");

        // Test Case 2: 第一行和第一列都有 0
        int[][] matrix2 = {
                {0, 1, 2, 0},
                {3, 4, 5, 2},
                {1, 3, 1, 5}
        };
        System.out.println("Original Matrix 2:");
        printMatrix(matrix2);
        solver.setZeroes(matrix2);
        System.out.println("Modified Matrix 2:");
        printMatrix(matrix2);
        // Expected:
        // [0, 0, 0, 0]
        // [0, 4, 5, 0]
        // [0, 3, 1, 0]
    }

    // Helper function to print matrix
    public static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            for (int val : row) {
                System.out.printf("%4d ", val);
            }
            System.out.println();
        }
    }
}
