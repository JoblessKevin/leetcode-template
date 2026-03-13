package problems.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * @formatter:off
 * 題目：太平洋大西洋水流問題 (Pacific Atlantic Water Flow)
 * 描述：給定一個二維整數矩陣 representing the height of each unit cell in a continent. 
 *      水可以從高處流向低處。太平洋和大西洋分別包圍著矩陣的左邊/上邊和右邊/下邊。
 *      找出所有可以同時流向太平洋和大西洋的格子座標。
 * 
 * 關鍵點：
 * 1. 正常的水流是「高 -> 低」。
 * 2. 題目問的是「哪些格子可以同時流向兩片海洋」。
 * 3. 直接從格子往外流很難判斷，因為路徑很多。反過來想：如果我們從「海洋」往「內陸」逆推，
 *    只要一個格子「能被逆推到」，就代表水可以從那個格子流向海洋。
 * 4. 逆推的條件：內陸格子必須「小於或等於」外側格子 (因為水流是高 -> 低，逆流就是低 -> 高)。
 * @formatter:on
 */
public class PacificAtlantic {
    // 定義上下左右四個方向的移動向量
    int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
    int rows, cols;

    public List<List<Integer>> pacificAtlantic(int[][] heights) {
        List<List<Integer>> result = new ArrayList<>();
        if (heights == null || heights.length == 0)
            return result;

        rows = heights.length;
        cols = heights[0].length;

        // 建立兩個布林矩陣，分別標記「哪些格子可以流到太平洋」和「哪些格子可以流到大西洋」
        boolean[][] canReachPacific = new boolean[rows][cols];
        boolean[][] canReachAtlantic = new boolean[rows][cols];

        // 1. 從「太平洋」邊界出發，進行 DFS
        for (int i = 0; i < rows; i++) {
            dfs(heights, i, 0, canReachPacific);
        }
        for (int j = 0; j < cols; j++) {
            dfs(heights, 0, j, canReachPacific);
        }

        // 2. 從「大西洋」邊界出發，進行 DFS
        for (int i = 0; i < rows; i++) {
            dfs(heights, i, cols - 1, canReachAtlantic);
        }
        for (int j = 0; j < cols; j++) {
            dfs(heights, rows - 1, j, canReachAtlantic);
        }

        // 3. 找出同時可以流向兩個海洋的格子
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (canReachPacific[i][j] && canReachAtlantic[i][j]) {
                    result.add(List.of(i, j));
                }
            }
        }

        return result;
    }

    // 深度優先搜尋 (DFS)
    private void dfs(int[][] heights, int r, int c, boolean[][] visited) {
        // 標記當前格子已經被訪問過
        visited[r][c] = true;

        // 探索四個方向
        for (int[] dir : dirs) {
            int nr = r + dir[0]; // 下一個 row
            int nc = c + dir[1]; // 下一個 col

            // 檢查邊界條件
            if (nr < 0 || nr >= rows || nc < 0 || nc >= cols)
                continue;

            // 檢查是否已經訪問過，如果訪問過就跳過，避免無限迴圈
            if (visited[nr][nc])
                continue;

            // 【核心邏輯】只有當下一個格子的水流高度「大於或等於」當前格子的水流高度時，水才能流上去
            if (heights[nr][nc] >= heights[r][c]) {
                dfs(heights, nr, nc, visited);
            }
        }
    }

    // Queue 裡面一開始已經裝滿了所有邊界的起點 (多源點)
    private void bfs(int[][] heights, Queue<int[]> queue, boolean[][] visited) {
        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            int r = curr[0];
            int c = curr[1];

            for (int[] dir : dirs) {
                int nr = r + dir[0];
                int nc = c + dir[1];

                // 1. 邊界檢查
                if (nr < 0 || nr >= rows || nc < 0 || nc >= cols)
                    continue;
                // 2. 走訪檢查
                if (visited[nr][nc])
                    continue;
                // 3. 地形檢查 (水往高處爬)
                if (heights[nr][nc] >= heights[r][c]) {

                    visited[nr][nc] = true; // 標記為安全/可到達
                    queue.offer(new int[] {nr, nc}); // 加入 Queue，準備下一波蔓延
                }
            }
        }
    }

    public static void main(String[] args) {
        PacificAtlantic solution = new PacificAtlantic();

        System.out.println("=== 測試案例 1：標準地形 (LeetCode 經典範例) ===");
        // 說明：這是一個 5x5 的矩陣，包含了各種高低起伏。
        // 預期輸出: [[0, 4], [1, 3], [1, 4], [2, 2], [3, 0], [3, 1], [4, 0]]
        int[][] heights1 = {{1, 2, 2, 3, 5}, {3, 2, 3, 4, 4}, {2, 4, 5, 3, 1}, {6, 7, 1, 4, 5},
                                        {5, 1, 1, 2, 4}};
        List<List<Integer>> result1 = solution.pacificAtlantic(heights1);
        System.out.println("同時流向兩大洋的座標: " + result1 + "\n");

        System.out.println("=== 測試案例 2：平坦的島嶼 ===");
        // 說明：所有格子高度都一樣 (都是 1)。
        // 因為高度相等也符合逆流條件 (>=)，所以整座島的水都可以互相流動，全部都能流向兩洋。
        // 預期輸出: [[0, 0], [0, 1], [1, 0], [1, 1]]
        int[][] heights2 = {{1, 1}, {1, 1}};
        List<List<Integer>> result2 = solution.pacificAtlantic(heights2);
        System.out.println("同時流向兩大洋的座標: " + result2 + "\n");

        System.out.println("=== 測試案例 3：單行/單列的極窄地形 ===");
        // 說明：只有一列。左邊是太平洋，右邊是大西洋，上下也各自被兩大洋包圍。
        // 最高點 3 往左可以到太平洋，往右可以到大西洋。
        // 預期輸出: [[0, 2]] (高度為 3 的那個點)
        int[][] heights3 = {{1, 2, 3, 2, 1}};
        List<List<Integer>> result3 = solution.pacificAtlantic(heights3);
        System.out.println("同時流向兩大洋的座標: " + result3 + "\n");
    }
}
