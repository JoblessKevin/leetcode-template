package problems.dp.string;

public class LongestCommonSubsequence {
    public int longestCommonSubsequence(String text1, String text2) {
        int m = text1.length();
        int n = text2.length();

        // dp[i][j] 代表 text1 前 i 個字元與 text2 前 j 個字元的最長公共子序列
        int[][] dp = new int[m + 1][n + 1];

        // i, j 從 1 開始，代表字串的長度
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                // 如果當前字元相同 (注意索引要減 1)
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    // 情況 1：找到一個共同字元，長度 = 1 + 去掉這個字元後的結果
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                } else {
                    // 情況 2：字元不同，取「捨棄 text1 當前字元」或「捨棄 text2 當前字元」的最大值
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        return dp[m][n];
    }

    public int longestCommonSubsequence_optimized(String text1, String text2) {
        int m = text1.length();
        int n = text2.length();

        // 字串交換技巧: 確保 n 是較短的字串，可以進一步節省空間
        if (m < n)
            return longestCommonSubsequence_optimized(text2, text1);

        int[] dp = new int[n + 1];

        for (int i = 1; i <= m; i++) {
            // prev 紀錄的是 dp[j-1] 在被更新前的值，也就是「左上方」
            int prev = 0;
            for (int j = 1; j <= n; j++) {
                // temp 暫存目前的 dp[j] (它是下一格 j+1 的左上方)
                int temp = dp[j];

                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    // 相同：1 + 左上方 (prev)
                    dp[j] = 1 + prev;
                } else {
                    // 不同：max(上方 dp[j], 左方 dp[j-1])
                    dp[j] = Math.max(dp[j], dp[j - 1]);
                }

                // 更新 prev，讓下一格使用
                prev = temp;
            }
        }

        return dp[n];
    }

    public static void main(String[] args) {
        LongestCommonSubsequence solver = new LongestCommonSubsequence();
        String t1 = "abcde";
        String t2 = "ace";

        int result = solver.longestCommonSubsequence(t1, t2);
        System.out.println("Text1: " + t1 + ", Text2: " + t2);
        System.out.println("LCS Length: " + result); // 預期輸出: 3 ("ace")

        int result2 = solver.longestCommonSubsequence_optimized(t1, t2);
        System.out.println("Text1: " + t1 + ", Text2: " + t2);
        System.out.println("LCS Length (Optimized): " + result2);
    }
}
