package problems.math;

public class RotateImage {
    public void rotate(int[][] matrix) {
        int n = matrix.length;

        // 1. 轉置 (Transpose)
        // 遍歷上三角區域 (i < j)，將 matrix[i][j] 和 matrix[j][i] 交換
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }

        // 2. 水平翻轉 (Reverse each row)
        // 遍歷每一列，將左右兩邊的元素交換
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n / 2; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[i][n - 1 - j];
                matrix[i][n - 1 - j] = temp;
            }
        }
    }

    // @formatter:off
    public static void main(String[] args) {
        RotateImage solver = new RotateImage();

        // Test Case 1: 3x3 矩陣
        int[][] matrix1 = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        System.out.println("Original Matrix 1:");
        printMatrix(matrix1);
        solver.rotate(matrix1);
        System.out.println("Rotated Matrix 1:");
        printMatrix(matrix1);
        // Expected:
        // [7, 4, 1]
        // [8, 5, 2]
        // [9, 6, 3]

        System.out.println("--------------------------");

        // Test Case 2: 4x4 矩陣
        int[][] matrix2 = {
                {5, 1, 9, 11},
                {2, 4, 8, 10},
                {13, 3, 6, 7},
                {15, 14, 12, 16}
        };
        System.out.println("Original Matrix 2:");
        printMatrix(matrix2);
        solver.rotate(matrix2);
        System.out.println("Rotated Matrix 2:");
        printMatrix(matrix2);
        // Expected:
        // [15, 13, 2, 5]
        // [14, 3, 4, 1]
        // [12, 6, 8, 9]
        // [16, 7, 10, 11]
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
