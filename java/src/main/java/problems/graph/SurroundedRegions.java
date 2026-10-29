package problems.graph;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Optimal Solution: DFS
 */
public class SurroundedRegions {
    public void solve_bfs(char[][] board) {
        if (board == null || board.length == 0)
            return;

        int rows = board.length;
        int cols = board[0].length;
        Queue<int[]> queue = new ArrayDeque<>();

        for (int i = 0; i < rows; i++) {
            if (board[i][0] == 'O') {
                board[i][0] = 'T';
                queue.offer(new int[] {i, 0});
            }
            if (board[i][cols - 1] == 'O') {
                board[i][cols - 1] = 'T';
                queue.offer(new int[] {i, cols - 1});
            }
        }

        for (int j = 0; j < cols; j++) {
            if (board[0][j] == 'O') {
                board[0][j] = 'T';
                queue.offer(new int[] {0, j});
            }
            if (board[rows - 1][j] == 'O') {
                board[rows - 1][j] = 'T';
                queue.offer(new int[] {rows - 1, j});
            }
        }

        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int row = current[0];
            int col = current[1];

            for (int[] dir : directions) {
                int newRow = row + dir[0];
                int newCol = col + dir[1];

                if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols
                                                && board[newRow][newCol] == 'O') {

                    board[newRow][newCol] = 'T';
                    queue.offer(new int[] {newRow, newCol});
                }
            }
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (board[i][j] == 'O') {
                    board[i][j] = 'X';
                } else if (board[i][j] == 'T') {
                    board[i][j] = 'O';
                }
            }
        }
    }

    public void solve_dfs(char[][] board) {
        if (board == null || board.length == 0)
            return;

        int rows = board.length;
        int cols = board[0].length;

        for (int i = 0; i < rows; i++) {
            if (board[i][0] == 'O')
                dfs(board, i, 0);
            if (board[i][cols - 1] == 'O')
                dfs(board, i, cols - 1);
        }

        for (int j = 0; j < cols; j++) {
            if (board[0][j] == 'O')
                dfs(board, 0, j);
            if (board[rows - 1][j] == 'O')
                dfs(board, rows - 1, j);
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (board[i][j] == 'O') {
                    board[i][j] = 'X';
                } else if (board[i][j] == 'T') {
                    board[i][j] = 'O';
                }
            }
        }
    }

    private void dfs(char[][] board, int r, int c) {
        if (r < 0 || r >= board.length || c < 0 || c >= board[0].length || board[r][c] != 'O') {
            return;
        }

        board[r][c] = 'T';

        dfs(board, r - 1, c);
        dfs(board, r + 1, c);
        dfs(board, r, c - 1);
        dfs(board, r, c + 1);
    }

    public static void main(String[] args) {
        SurroundedRegions solution = new SurroundedRegions();

        System.out.println("=== 測試案例 1：標準情況 ===");
        // 說明：中間的 O 會被包圍吃掉，但底部的 O 連接到了邊界，所以它和它上面的 O 都會活下來。
        char[][] board1 = {{'X', 'X', 'X', 'X'}, {'X', 'O', 'O', 'X'}, {'X', 'X', 'O', 'X'},
                                        {'X', 'O', 'X', 'X'}};
        System.out.println("修改前：");
        printBoard(board1);
        solution.solve_bfs(board1);
        System.out.println("修改後：");
        printBoard(board1);

        System.out.println("=== 測試案例 2：完全沒有被包圍的 O ===");
        // 說明：所有的 O 都連通到邊界，所以執行完後陣列應該要一模一樣。
        char[][] board2 = {{'X', 'O', 'X'}, {'X', 'O', 'X'}, {'X', 'O', 'X'}};
        System.out.println("修改前：");
        printBoard(board2);
        solution.solve_bfs(board2);
        System.out.println("修改後：");
        printBoard(board2);

        System.out.println("=== 測試案例 3：極小矩陣邊界測試 ===");
        // 說明：在 1x1 或 2x2 的矩陣中，所有的格子都是邊界，所以絕對不可能有 O 被包圍。
        char[][] board3 = {{'O', 'O'}, {'O', 'O'}};
        System.out.println("修改前：");
        printBoard(board3);
        solution.solve_bfs(board3);
        System.out.println("修改後：");
        printBoard(board3);
    }

    public static void printBoard(char[][] board) {
        for (char[] row : board) {
            System.out.print("[ ");
            for (char c : row) {
                System.out.print(c + " ");
            }
            System.out.println("]");
        }
        System.out.println();
    }
}
