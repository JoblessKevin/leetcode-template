package problems.math;

import java.util.ArrayList;
import java.util.List;

public class SpiralMatrix {
    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> res = new ArrayList<>();
        if (matrix == null || matrix.length == 0)
            return res;

        int top = 0, bottom = matrix.length - 1;
        int left = 0, right = matrix[0].length - 1;

        while (left <= right && top <= bottom) {
            for (int j = left; j <= right; j++) {
                res.add(matrix[top][j]);
            }
            top++;

            for (int i = top; i <= bottom; i++) {
                res.add(matrix[i][right]);
            }
            right--;

            if (!(left <= right && top <= bottom))
                break;

            for (int j = right; j >= left; j--) {
                res.add(matrix[bottom][j]);
            }
            bottom--;

            for (int i = bottom; i >= top; i--) {
                res.add(matrix[i][left]);
            }
            left++;
        }

        return res;
    }

    public List<Integer> spiralOrder_optimized(int[][] matrix) {
        List<Integer> res = new ArrayList<>();
        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        int[] steps = {matrix[0].length, matrix.length - 1};

        int r = 0, c = -1, d = 0;

        while (steps[d % 2] > 0) {
            for (int i = 0; i < steps[d % 2]; i++) {
                r += directions[d][0];
                c += directions[d][1];
                res.add(matrix[r][c]);
            }
            steps[d % 2]--;
            d = (d + 1) % 4;
        }

        return res;
    }

    // @formatter:off
    public static void main(String[] args) {
        SpiralMatrix solver = new SpiralMatrix();

        // Test Case 1: 3x3 矩陣
        int[][] matrix1 = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        List<Integer> result1 = solver.spiralOrder(matrix1);
        System.out.println("Test Case 1 Result: " + result1);
        // Expected: [1, 2, 3, 6, 9, 8, 7, 4, 5]

        System.out.println("--------------------------");

        // Test Case 2: 4x3 矩陣
        int[][] matrix2 = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12}
        };
        List<Integer> result2 = solver.spiralOrder(matrix2);
        System.out.println("Test Case 2 Result: " + result2);
        // Expected: [1, 2, 3, 4, 8, 12, 11, 10, 9, 5, 6, 7]

        System.out.println("--------------------------");

        // Test Case 3: 單行矩陣
        int[][] matrix3 = {
                {1, 2, 3, 4}
        };
        List<Integer> result3 = solver.spiralOrder(matrix3);
        System.out.println("Test Case 3 Result: " + result3);
        // Expected: [1, 2, 3, 4]

        System.out.println("--------------------------");

        // Test Case 4: 單列矩陣
        int[][] matrix4 = {
                {1},
                {2},
                {3},
                {4}
        };
        List<Integer> result4 = solver.spiralOrder(matrix4);
        System.out.println("Test Case 4 Result: " + result4);
        // Expected: [1, 2, 3, 4]
    }
}
