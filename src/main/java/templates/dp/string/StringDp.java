package templates.dp.string;

/**
 * 字串 DP (String DP) 模板
 * -------------------------------------------------------
 * 核心思想：使用二維陣列 dp[i][j] 來解決兩個字串 s1, s2 之間的匹配、修改或比較問題。
 * * 通用技巧：
 * 1. 陣列大小通常開 [n+1][m+1] 以處理空字串 (Empty String) 的邊界情況。
 * 2. 索引 i 對應 s1.charAt(i-1)，索引 j 對應 s2.charAt(j-1)。
 */
public class StringDp {

    /**
     * Type A: 最長公共子序列 (Longest Common Subsequence, LCS)
     * 規則：子序列 "不要求連續" (e.g., "ace" is subsequence of "abcde")。
     * 狀態轉移：
     * - 相等：左上角 + 1
     * - 不等：取 左邊 或 上面 的最大值 (繼承之前的結果)
     */
    public int longestCommonSubsequence(String text1, String text2) {
        int n = text1.length();
        int m = text2.length();
        
        // dp[i][j] 表示 text1[0...i-1] 和 text2[0...j-1] 的 LCS 長度
        int[][] dp = new int[n + 1][m + 1];

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                char c1 = text1.charAt(i - 1);
                char c2 = text2.charAt(j - 1);

                if (c1 == c2) {
                    // 字元相同：找到一個公共元素，長度 = 剩餘字串的 LCS + 1
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    // 字元不同：既然 c1 != c2，那 LCS 一定在
                    // "s1去掉c1" 或 "s2去掉c2" 之中產生，取大的
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[n][m];
    }

    /**
     * Type B: 編輯距離 (Edit Distance / Levenshtein Distance)
     * 規則：求將 s1 轉變成 s2 的最少操作數 (插入、刪除、替換)。
     * 狀態轉移：
     * - 相等：不需要操作，直接繼承左上角
     * - 不等：min(插入, 刪除, 替換) + 1
     */
    public int minDistance(String word1, String word2) {
        int n = word1.length();
        int m = word2.length();
        int[][] dp = new int[n + 1][m + 1];

        // 1. 初始化邊界 (Base Cases)
        // 當 word2 為空，word1 需要刪除 i 次才能變成空
        for (int i = 0; i <= n; i++) dp[i][0] = i;
        // 當 word1 為空，word1 需要插入 j 次才能變成 word2
        for (int j = 0; j <= m; j++) dp[0][j] = j;

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    // 字元相同：不需要任何操作
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    // 字元不同：取三種操作的最小值 + 1
                    // dp[i][j-1]   -> 插入 (Insert)
                    // dp[i-1][j]   -> 刪除 (Delete)
                    // dp[i-1][j-1] -> 替換 (Replace)
                    dp[i][j] = Math.min(dp[i - 1][j - 1], 
                               Math.min(dp[i][j - 1], dp[i - 1][j])) + 1;
                }
            }
        }
        return dp[n][m];
    }

    /**
     * Type C: 最長公共子字串 (Longest Common Substring) - 變體
     * 規則：子字串 "必須連續" (e.g., "abc" and "abd", substring is "ab")。
     * 區別：如果不匹配，長度歸零 (reset)。
     */
    public int longestCommonSubstring(String text1, String text2) {
        int n = text1.length();
        int m = text2.length();
        int[][] dp = new int[n + 1][m + 1];
        int maxLength = 0;

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                    // 因為是子字串，最大值不一定在 dp[n][m]，過程重要更新
                    maxLength = Math.max(maxLength, dp[i][j]);
                } else {
                    // 字元不同：連續性中斷，長度歸 0
                    dp[i][j] = 0;
                }
            }
        }
        return maxLength;
    }

    // ==========================================
    // Test Cases
    // ==========================================
    public static void main(String[] args) {
        StringDp solver = new StringDp();

        System.out.println("--- LCS (Longest Common Subsequence) ---");
        // "ace" is subsequence of "abcde"
        System.out.println("LCS: " + solver.longestCommonSubsequence("abcde", "ace")); // Expected: 3

        System.out.println("\n--- Edit Distance ---");
        // horse -> rorse (replace 'h' with 'r') -> rose (remove 'r') -> ros (remove 'e')
        System.out.println("Min Dist: " + solver.minDistance("horse", "ros")); // Expected: 3

        System.out.println("\n--- Longest Common Substring ---");
        // "ABABC", "BABCA" -> "BABC" (Length 4)
        System.out.println("Substring: " + solver.longestCommonSubstring("ABABC", "BABCA")); // Expected: 4
    }
}