package problems.graph;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * DFS: Time O(M*N), Space O(M*N) BFS: Time O(M*N), Space O(min(M, N)) M = rows, N = cols
 */
public class NumberOfIslands {

    private static final int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    /**
     * DFS (Optimal)
     */
    public int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0)
            return 0;

        int ROWS = grid.length, COLS = grid[0].length;
        int islands = 0;

        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                if (grid[r][c] == '1') {
                    dfs(grid, r, c);
                    islands++;
                }
            }
        }

        return islands;
    }

    private void dfs(char[][] grid, int r, int c) {
        if (r < 0 || c < 0 || r >= grid.length || c >= grid[0].length || grid[r][c] == '0') {
            return;
        }

        grid[r][c] = '0';
        for (int[] dir : directions) {
            dfs(grid, r + dir[0], c + dir[1]);
        }
    }

    /**
     * DFS (original)
     * @formatter:off

    // public int numIslands(char[][] grid) {
    //     if (grid == null || grid.length == 0)
    //         return 0;

    //     int rows = grid.length;
    //     int cols = grid[0].length;
    //     int island = 0;
        
    //     for (int r = 0; r < rows; r++) {
    //         for (int c = 0; c < cols; c++) {
    //             if (grid[r][c] == '1') {
    //                 island++;
    //                 dfs(grid, r, c);
    //             }
    //         }
    //     }
    //     return island;
    // }

    // private void dfs(char[][] grid, int r, int c) {
    //     if (r < 0 || r >= grid.length || c < 0 || c >= grid[0].length || grid[r][c] == '0')
    //         return;
    //     grid[r][c] = '0';
    //     dfs(grid, r + 1, c);
    //     dfs(grid, r - 1, c);
    //     dfs(grid, r, c + 1);
    //     dfs(grid, r, c - 1);
    // }

    * @formatter:on
    */

    /**
     * BFS
     */
    public int numIslands_bfs(char[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }

        int rows = grid.length;
        int cols = grid[0].length;
        int islands = 0;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c] == '1') {
                    bfs(grid, r, c);
                    islands++;
                }
            }
        }

        return islands;
    }

    private void bfs(char[][] grid, int r, int c) {
        Queue<Integer> queue = new ArrayDeque<>();
        int cols = grid[0].length;

        queue.offer(r * cols + c);
        grid[r][c] = '0';

        while (!queue.isEmpty()) {
            int curr = queue.poll();
            int currR = curr / cols;
            int currC = curr % cols;

            for (int[] dir : directions) {
                int newR = currR + dir[0];
                int newC = currC + dir[1];
                if (newR >= 0 && newR < grid.length && newC >= 0 && newC < cols
                                                && grid[newR][newC] == '1') {

                    queue.offer(newR * cols + newC);
                    grid[newR][newC] = '0';
                }
            }
        }
    }
}
