package problems.backTracking;

/**
 * DFS + Backtracking
 */
public class WordSearch {
    public boolean exist(char[][] board, String word) {
        int m = board.length;
        int n = board[0].length;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == word.charAt(0)) {
                    if (dfs(board, word, i, j, 0)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean dfs(char[][] board, String word, int i, int j, int index) {
        // Base Case 1: 找到所有的字元了！(成功)
        if (index == word.length()) {
            return true;
        }

        // Base Case 2: 失敗條件
        // 1. 越界 (超出上下左右)
        // 2. 字元不匹配
        // 3. 該格子已經被訪問過 (我們之後會把訪問過的設為 '#')
        if (i < 0 || i >= board.length || j < 0 || j >= board[0].length || board[i][j] != word.charAt(index)) {
            return false;
        }

        // --- Backtracking 開始 ---

        // 1. 備份當前字元 (為了之後還原)
        char temp = board[i][j];

        // 2. 標記為已訪問 (改成特殊符號，避免走回頭路)
        board[i][j] = '#';

        // 3. 往四個方向探索 (只要有一條路通，就是 true)
        // 順序通常沒差，這裡順序是：下、上、右、左
        boolean found = dfs(board, word, i + 1, j, index + 1) ||
                dfs(board, word, i - 1, j, index + 1) ||
                dfs(board, word, i, j + 1, index + 1) ||
                dfs(board, word, i, j - 1, index + 1);

        // 4. 還原現場 (Backtrack)
        // 很重要！如果不改回來，會影響到上層迴圈的其他搜尋路徑
        board[i][j] = temp;

        return found;
    }
}
