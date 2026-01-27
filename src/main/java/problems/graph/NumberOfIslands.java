package problems.graph;

public class NumberOfIslands {
    /**
     * Time: O(m * n)
     * Space: O(m * n)
     */
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

    private static final int[][] directions = {{1, 0}, {-1, 0},
                                               {0, 1}, {0, -1}};

    public int numIslands(char[][] grid) {
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
        if (r < 0 || c < 0 || r >= grid.length ||
            c >= grid[0].length || grid[r][c] == '0') {
            return;
        }

        grid[r][c] = '0';
        for (int[] dir : directions) {
            dfs(grid, r + dir[0], c + dir[1]);
        }
    }

    private static final int[][] DIRECTIONS = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    public int numIslands(char[][] grid) {
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
        Queue<int[]> queue = new ArrayDeque<>();
        
        queue.offer(new int[]{r, c});
        grid[r][c] = '0';

        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            int currR = curr[0];
            int currC = curr[1];

            for (int[] dir : DIRECTIONS) {
                int newR = currR + dir[0];
                int newC = currC + dir[1];
                if (newR >= 0 && newR < grid.length && 
                    newC >= 0 && newC < grid[0].length && 
                    grid[newR][newC] == '1') {
                    
                    queue.offer(new int[]{newR, newC});
                    grid[newR][newC] = '0';
                }
            }
        }
    }
}
