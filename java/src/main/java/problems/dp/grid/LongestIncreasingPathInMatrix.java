package problems.dp.grid;

import java.util.LinkedList;
import java.util.Queue;

public class LongestIncreasingPathInMatrix {
    private static final int[][] DIRS = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

    public int longestIncreasingPath(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0)
            return 0;

        int m = matrix.length;
        int n = matrix[0].length;
        int[][] memo = new int[m][n];
        int maxPath = 0;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                maxPath = Math.max(maxPath, dfs(matrix, i, j, memo, m, n));
            }
        }
        return maxPath;
    }

    private int dfs(int[][] matrix, int r, int c, int[][] memo, int m, int n) {
        if (memo[r][c] != 0)
            return memo[r][c];

        int max = 1;

        for (int[] dir : DIRS) {
            int nr = r + dir[0];
            int nc = c + dir[1];

            if (nr >= 0 && nr < m && nc >= 0 && nc < n && matrix[nr][nc] > matrix[r][c]) {
                max = Math.max(max, 1 + dfs(matrix, nr, nc, memo, m, n));
            }
        }

        memo[r][c] = max;
        return max;
    }

    public int longestIncreasingPath_kahn(int[][] matrix) {
        if (matrix == null || matrix.length == 0)
            return 0;
        int m = matrix.length, n = matrix[0].length;
        int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

        int[][] indegree = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                for (int[] d : dirs) {
                    int x = i + d[0], y = j + d[1];
                    // 如果鄰居比我小，代表鄰居指向我，我的入度 +1
                    if (x >= 0 && x < m && y >= 0 && y < n && matrix[x][y] < matrix[i][j]) {
                        indegree[i][j]++;
                    }
                }
            }
        }

        Queue<int[]> queue = new LinkedList<>();
        // 將所有起點（入度為 0）放入隊列
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (indegree[i][j] == 0)
                    queue.offer(new int[] {i, j});
            }
        }

        int height = 0;
        while (!queue.isEmpty()) {
            height++; // 每剝一層，路徑長度 +1
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int[] curr = queue.poll();
                for (int[] d : dirs) {
                    int x = curr[0] + d[0], y = curr[1] + d[1];
                    // 找到比我大的鄰居，砍掉它的入度
                    if (x >= 0 && x < m && y >= 0 && y < n
                                                    && matrix[x][y] > matrix[curr[0]][curr[1]]) {
                        if (--indegree[x][y] == 0) {
                            queue.offer(new int[] {x, y});
                        }
                    }
                }
            }
        }
        return height;
    }

    public static void main(String[] args) {
        LongestIncreasingPathInMatrix solver = new LongestIncreasingPathInMatrix();

        // 測試案例 1：LeetCode 範例 1
        // [9, 9, 4]
        // [6, 6, 8]
        // [2, 1, 1]
        // 最長路徑是 1-2-6-9，長度為 4
        int[][] matrix1 = {{9, 9, 4}, {6, 6, 8}, {2, 1, 1}};

        // 測試案例 2：LeetCode 範例 2
        // [3, 4, 5]
        // [3, 2, 6]
        // [2, 2, 1]
        // 最長路徑是 3-4-5-6，長度為 4
        int[][] matrix2 = {{3, 4, 5}, {3, 2, 6}, {2, 2, 1}};

        // 測試案例 3：單一元素
        int[][] matrix3 = {{1}};

        runTest(solver, matrix1, "Case 1 (Standard)");
        runTest(solver, matrix2, "Case 2 (Sequential)");
        runTest(solver, matrix3, "Case 3 (Single Element)");
    }

    private static void runTest(LongestIncreasingPathInMatrix solver, int[][] matrix,
                                    String testName) {
        System.out.println("===== Running " + testName + " =====");
        System.out.println("Matrix:");
        for (int[] row : matrix) {
            System.out.println(java.util.Arrays.toString(row));
        }

        // 呼叫 DFS + Memo 版本
        int resultMemo = solver.longestIncreasingPath(matrix);
        // 呼叫 Kahn's Algo 版本
        int resultKahn = solver.longestIncreasingPath_kahn(matrix);

        System.out.println("DFS + Memo Result: " + resultMemo);
        System.out.println("Kahn's Algo Result: " + resultKahn);

        // 驗證兩者結果是否一致
        if (resultMemo == resultKahn) {
            System.out.println("Result consistent!");
        } else {
            System.out.println("Result mismatch!");
        }
        System.out.println();
    }
}
