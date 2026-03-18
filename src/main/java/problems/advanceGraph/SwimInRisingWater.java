package problems.advanceGraph;

import java.util.PriorityQueue;

/**
 * @formatter:off
 * 778. Swim in Rising Water
 * 題目描述：
 * 你有一個 n x n 的二維網格，其中每個格子 grid[i][j] 代表該處的海拔高度。
 * 你從左上角 (0, 0) 出發，想要抵達右下角 (n-1, n-1)。
 * 在任意時刻 t，水位的高度就是 t。你只能游到海拔高度「小於或等於」 t 的格子。
 * 你的目標是找到一個最小的等待時間 t，使得你能夠從 (0, 0) 游到 (n-1, n-1)。
 * * ⚠️ 重要陷阱釐清：
 * 移動（上下左右）本身「不花費任何時間」（可以視為瞬間移動）。
 * 時間 t 僅代表水位的上升。你花費的時間其實是在「原地等待水位淹過前方的障礙物」。
 * * 核心概念：
 * 這題其實是在問：從起點到終點的所有路徑中，哪一條路徑的「最高峰 (瓶頸)」最矮？
 * 我們需要找到一條路徑，使得這條路徑上所有格子的最大高度，是所有可能路徑中最小的。
 * * 演算法：
 * 1. 使用 Dijkstra 演算法的變形（處理瓶頸路徑 / Minimax Path）。
 * 2. 使用優先佇列 (PriorityQueue) 來儲存待探索的格子，依照「目前累積經歷的最大高度」從小到大排序。
 * 3. 從 (0, 0) 開始探索，每次取出優先佇列中「瓶頸最小」的格子。
 * 4. 如果取出的格子是終點，直接回傳其記錄的最大高度。
 * 5. 否則，將其相鄰且未造訪的格子加入優先佇列，其傳遞下去的最大高度為：「當前路線的最大高度」與「下一格本身高度」取 MAX。
 * * 時間複雜度：O(N^2 * log(N))，其中 N 是網格的邊長。最壞情況下 N^2 個格子進出 PQ。
 * 空間複雜度：O(N^2)，用於儲存優先佇列和 visited 陣列。
 * @formatter:on
 */
public class SwimInRisingWater {
    public int swimInWater(int[][] grid) {
        int n = grid.length;

        // PriorityQueue 陣列格式: [row, col, 這條路徑上目前遇到的最大高度]
        // 依照「最大高度 (時間)」從小到大排序 (誰遇到的瓶頸矮，誰優先探索)
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[2] - b[2]);

        boolean[][] visited = new boolean[n][n];
        int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

        // 把起點丟進 PQ，起點的瓶頸就是它自己的高度
        pq.offer(new int[] {0, 0, grid[0][0]});
        visited[0][0] = true;

        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int r = current[0];
            int c = current[1];
            int maxElevation = current[2];

            // 🌟 只要從 PQ 拿出來的點是終點，直接回傳！
            // 因為 PQ 是從小到大排，第一個碰到的絕對是「最小的瓶頸」
            if (r == n - 1 && c == n - 1) {
                return maxElevation;
            }

            // 往上下左右游
            for (int[] dir : dirs) {
                int nr = r + dir[0];
                int nc = c + dir[1];

                // 檢查邊界與是否走過
                if (nr >= 0 && nr < n && nc >= 0 && nc < n && !visited[nr][nc]) {
                    visited[nr][nc] = true;

                    // 🌟 核心邏輯：這條路徑的新瓶頸，是「原本的瓶頸」和「下一格高度」取最大值
                    int nextMaxElevation = Math.max(maxElevation, grid[nr][nc]);

                    pq.offer(new int[] {nr, nc, nextMaxElevation});
                }
            }
        }

        return -1;
    }

    public static void main(String[] args) {
        SwimInRisingWater solution = new SwimInRisingWater();

        System.out.println("=== 測試案例 1：簡單 2x2 網格 ===");
        // 說明：路徑 0 -> 1 -> 3，途中的最大高度是 3。
        // 等到 t=3 時，0, 1, 2, 3 全部淹沒，瞬間游過去。
        // 預期輸出: 3
        int[][] grid1 = {{0, 2}, {1, 3}};
        System.out.println("最少等待時間: " + solution.swimInWater(grid1) + "\n");

        System.out.println("=== 測試案例 2：經典的蛇形深淵 ===");
        // 說明：這是一個 5x5 的矩陣。中間卡了一道由 24, 23, 22, 21 組成的高牆。
        // 你不可能翻過高牆，你必須沿著邊緣 [0, 1, 2, 3, 4, 5, 16, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6] 像貪食蛇一樣繞一圈。
        // 在這條繞遠路的路線上，最高的一格是 16。只要等到 t=16，整條水路就通了！
        // 預期輸出: 16
        int[][] grid2 = {{0, 1, 2, 3, 4}, {24, 23, 22, 21, 5}, {12, 13, 14, 15, 16},
                                        {11, 17, 18, 19, 20}, {10, 9, 8, 7, 6}};
        System.out.println("最少等待時間: " + solution.swimInWater(grid2) + "\n");
    }
}
