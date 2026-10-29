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
        int count; // 目前獨立島嶼的數量

        public DSU(char[][] grid) {
            int m = grid.length;
            int n = grid[0].length;
            parent = new int[m * n];
            rank = new int[m * n];
            count = 0;

            System.out.println("--- [DSU 初始化階段] ---");
            for (int r = 0; r < m; r++) {
                for (int c = 0; c < n; c++) {
                    if (grid[r][c] == '1') {
                        int id = r * n + c;
                        parent[id] = id;
                        count++;
                        System.out.println(String.format("發現陸地 (%d, %d) [ID: %d]，暫時視為一個獨立島嶼。", r, c,
                                                        id));
                    }
                }
            }
            System.out.println(">>> 初始化完成，目前共有 " + count + " 個獨立島嶼 (Count)\n");
        }

        public int find(int i) {
            if (parent[i] != i) {
                parent[i] = find(parent[i]); // 路徑壓縮
            }
            return parent[i];
        }

        public void union(int i, int j) {
            int rootX = find(i);
            int rootY = find(j);

            System.out.print(String.format("嘗試連結 ID %d 和 ID %d... ", i, j));

            if (rootX != rootY) {
                // 兩者老大不同，可以合併
                if (rank[rootX] > rank[rootY]) {
                    parent[rootY] = rootX;
                    System.out.print("合併成功！" + rootY + " 拜 " + rootX + " 為老大。");
                } else if (rank[rootX] < rank[rootY]) {
                    parent[rootX] = rootY;
                    System.out.print("合併成功！" + rootX + " 拜 " + rootY + " 為老大。");
                } else {
                    parent[rootY] = rootX;
                    rank[rootX]++;
                    System.out.print("合併成功！" + rootY + " 拜 " + rootX + " 為老大。");
                }
                count--; // 關鍵：島嶼數量減一
                System.out.println(" (目前 Count 降為: " + count + ")");
            } else {
                System.out.println("失敗。它們原本就是同一國的 (老大都是 " + rootX + ")，無需合併。");
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

        System.out.println("--- [開始遍歷地圖與合併] ---");
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c] == '1') {
                    int id = r * cols + c;

                    // 檢查右邊
                    if (c + 1 < cols && grid[r][c + 1] == '1') {
                        int rightId = r * cols + (c + 1);
                        System.out.println("位置 (" + r + "," + c + ") 發現右邊也是陸地，發起 Union...");
                        dsu.union(id, rightId);
                    }

                    // 檢查下面
                    if (r + 1 < rows && grid[r + 1][c] == '1') {
                        int downId = (r + 1) * cols + c;
                        System.out.println("位置 (" + r + "," + c + ") 發現下面也是陸地，發起 Union...");
                        dsu.union(id, downId);
                    }
                }
            }
        }
        return dsu.getCount();
    }

    // Main Test Case
    public static void main(String[] args) {
        NumberOfIslands solver = new NumberOfIslands();

        // 建立一個 3x3 的地圖
        // 1 1 0 (ID: 0, 1, 2) -> 0和1是陸地
        // 1 0 0 (ID: 3, 4, 5) -> 3是陸地
        // 0 0 1 (ID: 6, 7, 8) -> 8是陸地 (孤島)
        // 預期結果：2 (左上角連成一塊，右下角一塊)
        char[][] grid = {{'1', '1', '0'}, {'1', '0', '0'}, {'0', '0', '1'}};

        System.out.println("地圖預覽 (ID對照):");
        System.out.println("[0] [1] [2]");
        System.out.println("[3] [4] [5]");
        System.out.println("[6] [7] [8]");
        System.out.println("其中 0, 1, 3, 8 是陸地 '1'\n");

        int result = solver.numIslands_dsu(grid);

        System.out.println("\n--- [最終結果] ---");
        System.out.println("島嶼數量: " + result);
    }
}
