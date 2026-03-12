package problems.graph;

import java.util.ArrayDeque;
import java.util.Queue;

public class RottingOranges {
    public int orangesRotting(int[][] grid) {
        if (grid == null || grid.length == 0)
            return 0;

        int rows = grid.length;
        int cols = grid[0].length;

        Queue<int[]> queue = new ArrayDeque<>();
        int freshCount = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == 2) {
                    queue.offer(new int[] {i, j});
                } else if (grid[i][j] == 1) {
                    freshCount++;
                }
            }
        }

        if (freshCount == 0)
            return 0;

        int minutes = 0;
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        while (!queue.isEmpty() && freshCount > 0) {
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                int[] current = queue.poll();
                int row = current[0];
                int col = current[1];

                for (int[] dir : directions) {
                    int newRow = row + dir[0];
                    int newCol = col + dir[1];

                    if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols
                                                    && grid[newRow][newCol] == 1) {

                        grid[newRow][newCol] = 2;
                        freshCount--;
                        queue.offer(new int[] {newRow, newCol});
                    }
                }
            }
            minutes++;
        }

        return freshCount == 0 ? minutes : -1;
    }

    public static void main(String[] args) {
        RottingOranges solution = new RottingOranges();

        System.out.println("=== 測試案例 1: 正常蔓延 ===");
        // 預期結果: 4
        // 說明：你可以觀察每一分鐘 queue 的 size 變化，以及 grid 裡面的 1 怎麼慢慢變成 2。
        int[][] grid1 = {{2, 1, 1}, {1, 1, 0}, {0, 1, 1}};
        int result1 = solution.orangesRotting(grid1);
        System.out.println("花費分鐘數: " + result1 + "\n");

        System.out.println("=== 測試案例 2: 永遠無法全部腐爛 ===");
        // 預期結果: -1
        // 說明：左下角的 1 被 0 擋住了，你可以觀察 while 迴圈結束後，freshCount 仍然大於 0 的情況。
        int[][] grid2 = {{2, 1, 1}, {0, 1, 1}, {1, 0, 1}};
        int result2 = solution.orangesRotting(grid2);
        System.out.println("花費分鐘數: " + result2 + "\n");

        System.out.println("=== 測試案例 3: 一開始就沒有新鮮橘子 ===");
        // 預期結果: 0
        // 說明：這會直接觸發 if (freshCount == 0) return 0; 的提早結束條件。
        int[][] grid3 = {{0, 2, 0}, {2, 0, 2}, {0, 0, 0}};
        int result3 = solution.orangesRotting(grid3);
        System.out.println("花費分鐘數: " + result3 + "\n");
    }
}
