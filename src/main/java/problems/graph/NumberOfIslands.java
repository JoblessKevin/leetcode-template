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

    /**
     * DSU
     */
    private static class DSU {
        int[] parent;
        int[] rank;
        int count;

        public DSU(char[][] grid) {
            int m = grid.length;
            int n = grid[0].length;
            parent = new int[m * n];
            rank = new int[m * n];
            count = 0;

            for (int r = 0; r < m; r++) {
                for (int c = 0; c < n; c++) {
                    if (grid[r][c] == '1') {
                        int id = r * n + c;
                        parent[id] = id;
                        count++;
                    }
                }
            }
        }

        public int find(int i) {
            if (parent[i] != i) {
                parent[i] = find(parent[i]);
            }
            return parent[i];
        }

        public void union(int i, int j) {
            int rootX = find(i);
            int rootY = find(j);
            if (rootX != rootY) {
                if (rank[rootX] > rank[rootY]) {
                    parent[rootY] = rootX;
                } else if (rank[rootX] < rank[rootY]) {
                    parent[rootX] = rootY;
                } else {
                    parent[rootY] = rootX;
                    rank[rootX]++;
                }
                count--;
            }
        }

        public int getCount() {
            return count;
        }
    }

    public int numIslands_dsu(char[][] grid) {
        if (grid == null || grid.length == 0)
            return 0;

        int rows = grid.length;
        int cols = grid[0].length;
        DSU dsu = new DSU(grid);

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c] == '1') {
                    int id = r * cols + c;

                    if (c + 1 < cols && grid[r][c + 1] == '1') {
                        dsu.union(id, r * cols + (c + 1));
                    }

                    if (r + 1 < rows && grid[r + 1][c] == '1') {
                        dsu.union(id, (r + 1) * cols + c);
                    }
                }
            }
        }

        return dsu.getCount();
    }

    // ==========================================
    // For DSU
    // ==========================================
    public static void main(String[] args) {
        // 1. 建立解題物件
        NumberOfIslands solver = new NumberOfIslands();

        // 2. 準備測試資料 (Input)
        // 這裡設計了一個經典案例：
        // 左上角有一大塊 (1,1,1,1)，右下角有一小塊 (1)，中間被水隔開
        // 預期結果應該是：3 (左上角一塊 + 中間一塊 + 右下角一塊)
        char[][] grid = {{'1', '1', '0', '0', '0'}, {'1', '1', '0', '0', '0'},
                                        {'0', '0', '1', '0', '0'}, {'0', '0', '0', '1', '1'}};

        System.out.println("========== 測試開始 ==========");

        // 印出原本的地圖讓你看比較清楚
        System.out.println("輸入地圖:");
        for (char[] row : grid) {
            for (char c : row) {
                System.out.print(c + " ");
            }
            System.out.println();
        }

        // 3. 【關鍵步驟】呼叫你的 function
        System.out.println("\n正在呼叫 numIslands_dsu()...");
        int result = solver.numIslands_dsu(grid);

        // 4. 印出結果
        System.out.println("計算結果 (島嶼數量): " + result);
        System.out.println("========== 測試結束 ==========");
    }
}
