package problems.array;

public class ValidSudoku {
    public static boolean isValidSudoku(char[][] board) {
        boolean[][] rows = new boolean[9][9];
        boolean[][] cols = new boolean[9][9];
        boolean[][] boxes = new boolean[9][9];

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                // 如果是空格，直接跳過
                if (board[r][c] == '.')
                    continue;

                // 將字元轉為 0-8 的索引
                int num = board[r][c] - '1';

                // 計算宮格索引
                int boxIndex = (r / 3) * 3 + (c / 3);

                // 檢查是否重複出現
                if (rows[r][num] || cols[c][num] || boxes[boxIndex][num]) {
                    return false; // 發現重複，直接判定無效
                }

                // 標記為已出現
                rows[r][num] = true;
                cols[c][num] = true;
                boxes[boxIndex][num] = true;
            }
        }
        return true;
    }

    public static void printMarkers(char[][] board) {
        boolean[][] rows = new boolean[9][9];
        boolean[][] cols = new boolean[9][9];
        boolean[][] boxes = new boolean[9][9];

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (board[r][c] == '.')
                    continue;
                int num = board[r][c] - '1';
                int boxIndex = (r / 3) * 3 + (c / 3);
                rows[r][num] = true;
                cols[c][num] = true;
                boxes[boxIndex][num] = true;
            }
        }

        System.out.println("=== 標記結果摘要 ===");
        System.out.println("Row 0 被標記的數字索引: " + getTrueIndices(rows[0]));
        System.out.println("Col 0 被標記的數字索引: " + getTrueIndices(cols[0]));
        System.out.println("Box 0 (左上宮格) 被標記的數字索引: " + getTrueIndices(boxes[0]));
    }

    private static String getTrueIndices(boolean[] array) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            if (array[i])
                sb.append((i + 1)).append(" ");
        }
        return sb.length() == 0 ? "無" : sb.toString();
    }

    //@formatter:off
    public static void main(String[] args) {
        char[][] board = 
            {{'5', '3', '.', '.', '7', '.', '.', '.', '.'},
            {'6', '.', '.', '1', '9', '5', '.', '.', '.'},
            {'.', '9', '8', '.', '.', '.', '.', '6', '.'},
            {'8', '.', '.', '.', '6', '.', '.', '.', '3'},
            {'4', '.', '.', '8', '.', '3', '.', '.', '1'},
            {'7', '.', '.', '.', '2', '.', '.', '.', '6'},
            {'.', '6', '.', '.', '.', '.', '2', '8', '.'},
            {'.', '.', '.', '4', '1', '9', '.', '.', '5'},
            {'.', '.', '.', '.', '8', '.', '.', '7', '9'}};

        if (isValidSudoku(board)) {
            System.out.println("數獨有效！");
            printMarkers(board); // 印出標記結果
        } else {
            System.out.println("數獨無效！");
        }
    }
    //@formatter:on

    //@formatter:off
    // public static void main(String[] args) {
    //     // 測試案例：一個有效的數獨盤面 (截取部分)
    //     char[][] board = 
    //         {{'5', '3', '.', '.', '7', '.', '.', '.', '.'},
    //         {'6', '.', '.', '1', '9', '5', '.', '.', '.'},
    //         {'.', '9', '8', '.', '.', '.', '.', '6', '.'},
    //         {'8', '.', '.', '.', '6', '.', '.', '.', '3'},
    //         {'4', '.', '.', '8', '.', '3', '.', '.', '1'},
    //         {'7', '.', '.', '.', '2', '.', '.', '.', '6'},
    //         {'.', '6', '.', '.', '.', '.', '2', '8', '.'},
    //         {'.', '.', '.', '4', '1', '9', '.', '.', '5'},
    //         {'.', '.', '.', '.', '8', '.', '.', '7', '9'}};

    //     System.out.println("測試有效數獨: " + (isValidSudoku(board) ? "通過 ✅" : "失敗 ❌"));

    //     // 測試案例：構造一個無效盤面 (在第一行重複放入 5)
    //     board[0][2] = '5';
    //     System.out.println("測試無效數獨 (第一行有重複): " + (isValidSudoku(board) ? "通過 ✅" : "失敗 ❌"));
    // }
    //@formatter:on
}
