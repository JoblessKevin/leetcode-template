package problems.dp.string;

/**
 * @formatter:off
 * a.k.a: Levenshtein Distance (這題的 optimized 空間壓縮非常困難，當字元不同時，同時依賴"上方、左方、左上方"三個位置)
 * 給你兩個單字 word1 和 word2，請計算出 word1 轉換成 word2 所需的最少操作次數。
 * 你可以對 word1 進行以下 3 種操作：
 * 1. 插入一個字元
 * 2. 刪除一個字元
 * 3. 替換一個字元
 * @formatter:on
 */
public class EditDistance {
    public int minDistance(String word1, String word2) {
        int m = word1.length();
        int n = word2.length();

        // dp[i][j] 代表 word1 的前 i 個字元轉換成 word2 的前 j 個字元所需的最少操作次數
        int[][] dp = new int[m + 1][n + 1];

        // 邊界條件：如果 word1 是空字串，需要 j 次插入才能變成 word2
        for (int j = 0; j <= n; j++) {
            dp[0][j] = j;
        }

        // 邊界條件：如果 word2 是空字串，需要 i 次刪除才能變成 word2
        for (int i = 0; i <= m; i++) {
            dp[i][0] = i;
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                // 如果字元相同，不需要操作，直接繼承左上角的結果
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    // 字元不同，需要操作，取三種操作的最小值：
                    // 1. 插入：dp[i][j - 1] + 1
                    // 2. 刪除：dp[i - 1][j] + 1
                    // 3. 替換：dp[i - 1][j - 1] + 1
                    dp[i][j] = Math.min(dp[i][j - 1], Math.min(dp[i - 1][j], dp[i - 1][j - 1])) + 1;
                }
            }
        }
        return dp[m][n];
    }

    public int minDistance_optimized(String word1, String word2) {
        int m = word1.length();
        int n = word2.length();

        // 確保 dp 陣列是較短的那一個，可以進一步優化空間
        if (m < n)
            return minDistance_optimized(word2, word1);

        int[] dp = new int[n + 1];

        // 初始化：空字串變成 word2 的前 j 個字元需要 j 次插入
        for (int j = 0; j <= n; j++) {
            dp[j] = j;
        }

        for (int i = 1; i <= m; i++) {
            // prev 紀錄的是「左上方」的值，對每一列開始時，它相當於 dp[i-1][0]
            int prev = dp[0];
            dp[0] = i; // 更新 dp[0] 為 i，代表 word1 前 i 個變成空字串需要 i 次刪除

            for (int j = 1; j <= n; j++) {
                int temp = dp[j]; // 備份：這格目前的數值是下一格的「左上方」

                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    // 字元相同，直接繼承「左上方」舊值
                    dp[j] = prev;
                } else {
                    // 字元不同，取 (左方, 上方, 左上方) 的最小值 + 1
                    // 此時：dp[j-1] 是左方, dp[j] 是上方, prev 是左上方
                    dp[j] = Math.min(prev, Math.min(dp[j - 1], dp[j])) + 1;
                }

                // 接力：把剛備份的舊值傳給下一個 j 使用
                prev = temp;
            }
        }
        return dp[n];
    }

    public static void main(String[] args) {
        EditDistance solver = new EditDistance();

        String word1 = "horse", word2 = "ros";
        System.out.println("Test Case 1: " + word1 + ", " + word2);
        System.out.println("Result: " + solver.minDistance(word1, word2)); // 應為 3
        System.out.println("Result_optimized: " + solver.minDistance_optimized(word1, word2));

        String word3 = "intention", word4 = "execution";
        System.out.println("Test Case 2: " + word3 + ", " + word4);
        System.out.println("Result: " + solver.minDistance(word3, word4)); // 應為 5
        System.out.println("Result_optimized: " + solver.minDistance_optimized(word3, word4));
    }
}
