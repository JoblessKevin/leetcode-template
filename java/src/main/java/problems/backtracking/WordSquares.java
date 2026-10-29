package problems.backtracking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WordSquares {
    public List<List<String>> wordSquares(String[] words) {
        List<List<String>> result = new ArrayList<>();

        // 1. 題目要求回傳結果需依「字典序」排列
        // 所以我們先把原有的單字庫排好序，這樣接下來遍歷找到的解就會自動符合順序
        Arrays.sort(words);

        int n = words.length;

        // --- 開始 Backtracking (這裡用巢狀迴圈模擬 4 個步驟) ---

        // 第一步：選 Top (上)
        for (int i = 0; i < n; i++) {
            String top = words[i];

            // 第二步：選 Left (左)
            for (int j = 0; j < n; j++) {
                if (i == j)
                    continue; // 規則：單字不能重複
                String left = words[j];

                // [檢查點 1] 左上角吻合嗎？ (Top 開頭 == Left 開頭)
                // 如果不吻合，continue (回溯，換下一個 left 試試)
                if (top.charAt(0) != left.charAt(0))
                    continue;

                // 第三步：選 Right (右)
                for (int k = 0; k < n; k++) {
                    if (k == i || k == j)
                        continue; // 不能重複
                    String right = words[k];

                    // [檢查點 2] 右上角吻合嗎？ (Top 結尾 == Right 開頭)
                    // 注意：字串長度固定為 4，所以結尾索引是 3
                    if (top.charAt(3) != right.charAt(0))
                        continue;

                    // 第四步：選 Bottom (下)
                    for (int l = 0; l < n; l++) {
                        if (l == i || l == j || l == k)
                            continue; // 不能重複
                        String bottom = words[l];

                        // [檢查點 3] 左下角 & 右下角 都要吻合
                        // 左下：Bottom 開頭 == Left 結尾
                        // 右下：Bottom 結尾 == Right 結尾
                        if (bottom.charAt(0) == left.charAt(3) &&
                                bottom.charAt(3) == right.charAt(3)) {

                            // 恭喜！四個步驟都走通了，這是一個合法的答案
                            result.add(Arrays.asList(top, left, right, bottom));
                        }
                    }
                }
            }
        }

        return result;
    }

    public static void main(String[] args) {
        // 1. 準備測試資料 (Input)
        String[] words = { "able", "area", "echo", "also" };

        // 2. 建立解題物件並呼叫方法
        WordSquares solver = new WordSquares();
        List<List<String>> results = solver.wordSquares(words);

        // 3. 印出結果 (Output)
        System.out.println("找到符合的 Word Squares 數量: " + results.size());

        for (int i = 0; i < results.size(); i++) {
            System.out.println("解法 " + (i + 1) + ":");
            for (String row : results.get(i)) {
                System.out.println("  " + row);
            }
            System.out.println();
        }
    }
}
