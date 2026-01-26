package problems.backtracking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NQueens {

    // 備忘錄：用來瞬間判斷 (O(1)) 某個位置是否安全
    // 1. 直行是否被佔用
    boolean[] cols;
    // 2. 主對角線 (\) 是否被佔用 (規律: row - col 為定值)
    boolean[] d1;
    // 3. 副對角線 (/) 是否被佔用 (規律: row + col 為定值)
    boolean[] d2;

    public List<List<String>> solveNQueens(int n) {
        List<List<String>> result = new ArrayList<>();

        // 初始化備忘錄
        // d1 和 d2 的長度設為 2*n 確保索引不會越界 (因為 row+col 最大是 2n)
        cols = new boolean[n];
        d1 = new boolean[2 * n];
        d2 = new boolean[2 * n];

        // 初始化棋盤
        char[][] board = new char[n][n];
        for (char[] row : board) {
            Arrays.fill(row, '.');
        }

        // 開始遞迴：從第 0 列 (row 0) 開始放
        backtrack(result, board, n, 0);

        return result;
    }

    private void backtrack(List<List<String>> result, char[][] board, int n, int row) {
        // Base Case: 如果 row 等於 n，代表 0 ~ n-1 列都放滿了，找到一組解
        if (row == n) {
            result.add(construct(board));
            return;
        }

        // 嘗試在當前 row 的每一欄 (col) 放皇后
        for (int col = 0; col < n; col++) {
            // 計算對角線 ID
            int id1 = row - col + n; // 加 n 是為了避免負數索引
            int id2 = row + col;

            // 【剪枝核心】 O(1) 檢查是否衝突
            if (cols[col] || d1[id1] || d2[id2]) {
                continue; // 這裡不安全，跳過
            }

            // 1. 做選擇 (佔地盤)
            board[row][col] = 'Q';
            cols[col] = true;
            d1[id1] = true;
            d2[id2] = true;

            // 2. 遞迴下一列
            backtrack(result, board, n, row + 1);

            // 3. 撤銷選擇 (還原現場)
            board[row][col] = '.';
            cols[col] = false;
            d1[id1] = false;
            d2[id2] = false;
        }
    }

    // 輔助函式：將 char[][] 轉成題目要求的 List<String> 格式
    private List<String> construct(char[][] board) {
        List<String> res = new ArrayList<>();
        for (char[] row : board) {
            res.add(new String(row));
        }
        return res;
    }

    // ==========================================
    // Main 方法 (Debug 測試)
    // ==========================================
    public static void main(String[] args) {
        NQueens solver = new NQueens();
        int n = 4;

        System.out.println("===== N-Queens Solution (N=" + n + ") =====");
        List<List<String>> solutions = solver.solveNQueens(n);

        System.out.println("總共找到 " + solutions.size() + " 種解法：");
        for (int i = 0; i < solutions.size(); i++) {
            System.out.println("解法 " + (i + 1) + ":");
            for (String row : solutions.get(i)) {
                System.out.println(row);
            }
            System.out.println();
        }
    }
}
