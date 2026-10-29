package problems.dp.string;

public class InterleavingString {
    public boolean isInterleave(String s1, String s2, String s3) {
        int m = s1.length(), n = s2.length();
        if (m + n != s3.length())
            return false;

        boolean[][] dp = new boolean[m + 1][n + 1];
        dp[0][0] = true; // 起點：空字串可以組成空字串

        // 初始化第一行 (只用 s1 湊 s3)
        for (int i = 1; i <= m; i++)
            dp[i][0] = dp[i - 1][0] && s1.charAt(i - 1) == s3.charAt(i - 1);

        // 初始化第一列 (只用 s2 湊 s3)
        for (int j = 1; j <= n; j++)
            dp[0][j] = dp[0][j - 1] && s2.charAt(j - 1) == s3.charAt(j - 1);

        // 填表 (Grid 邏輯：從左邊或上邊走過來)
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                // 從左邊 (s1) 來 || 從上面 (s2) 來
                dp[i][j] = (dp[i - 1][j] && s1.charAt(i - 1) == s3.charAt(i + j - 1)) || (dp[i][j
                                                - 1] && s2.charAt(j - 1) == s3.charAt(i + j - 1));
            }
        }
        return dp[m][n];
    }

    public boolean isInterleave_optimized(String s1, String s2, String s3) {
        int m = s1.length(), n = s2.length();
        if (m + n != s3.length())
            return false;

        // 空間優化：只需要長度為 n + 1 的陣列
        boolean[] dp = new boolean[n + 1];

        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                if (i == 0 && j == 0) {
                    // 起點
                    dp[j] = true;
                } else if (i == 0) {
                    // 第一列 (只能從左邊來，也就是只看 s2)
                    dp[j] = dp[j - 1] && s2.charAt(j - 1) == s3.charAt(j - 1);
                } else if (j == 0) {
                    // 第一行 (只能從上面來，也就是只看 s1)
                    // 注意：這裡的 dp[j] 代表的是 dp[i-1][0]
                    dp[j] = dp[j] && s1.charAt(i - 1) == s3.charAt(i - 1);
                } else {
                    // 核心轉移：從上面來 (dp[j]) 或 從左邊來 (dp[j-1])
                    // dp[j] 依然存著上一列的結果
                    dp[j] = (dp[j] && s1.charAt(i - 1) == s3.charAt(i + j - 1)) || (dp[j - 1]
                                                    && s2.charAt(j - 1) == s3.charAt(i + j - 1));
                }
            }
        }
        return dp[n];
    }

    public static void main(String[] args) {
        InterleavingString solver = new InterleavingString();

        // 測試案例 1: 經典案例
        String s1_1 = "aabcc";
        String s2_1 = "dbbca";
        String s3_1 = "aadbbcbcac";
        runTest(solver, s1_1, s2_1, s3_1, 1);

        // 測試案例 2: 失敗案例
        String s1_2 = "aabcc";
        String s2_2 = "dbbca";
        String s3_2 = "aadbbbaccc";
        runTest(solver, s1_2, s2_2, s3_2, 2);
    }

    private static void runTest(InterleavingString solver, String s1, String s2, String s3,
                                    int caseNum) {
        System.out.println("===== Test Case " + caseNum + " =====");
        System.out.println("s1: " + s1);
        System.out.println("s2: " + s2);
        System.out.println("s3: " + s3);

        boolean result = solver.isInterleave(s1, s2, s3);
        System.out.println("Result: " + result);
        System.out.println();
    }
}
