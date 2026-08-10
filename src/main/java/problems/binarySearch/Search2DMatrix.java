package problems.binarySearch;

public class Search2DMatrix {
    class BinarySearch {
        public boolean searchMatrix(int[][] matrix, int target) {
            int m = matrix.length;
            int n = matrix[0].length;

            int l = 0;
            int r = m * n - 1;

            while (l <= r) {
                int mid = l + (r - l) / 2;
                int midValue = matrix[mid / n][mid % n];

                if (midValue == target) {
                    return true;
                } else if (midValue < target) {
                    l = mid + 1;
                } else {
                    r = mid - 1;
                }
            }

            return false;
        }
    }

    class BruteForce {
        public boolean searchMatrix(int[][] matrix, int target) {
            int m = matrix.length;
            int n = matrix[0].length;
            int i = 0;

            while (i < m) {
                if (matrix[i][n - 1] == target) {
                    return true;
                } else if (matrix[i][n - 1] < target) {
                    i++;
                } else {
                    for (int j = n - 1; j >= 0; j--) {
                        if (matrix[i][j] == target) {
                            return true;
                        }
                    }
                    break;
                }
            }

            return false;
        }
    }

    // @formatter:off
    public static void main(String[] args) {
        Search2DMatrix search2DMatrix = new Search2DMatrix();
        BinarySearch binarySearch = search2DMatrix.new BinarySearch();
        BruteForce bruteForce = search2DMatrix.new BruteForce();
        int[][] matrix = {
                {1, 3, 5, 7},
                {10, 11, 16, 20},
                {23, 30, 34, 60}
        };
        System.out.println(binarySearch.searchMatrix(matrix, 3)); // true
        System.out.println(binarySearch.searchMatrix(matrix, 13)); // false
    }
}
