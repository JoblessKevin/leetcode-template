package problems.dp.string;

public class RegularExpressionMatching {
    public boolean isMatch(String s, String p) {
        int m = s.length();
        int n = p.length();
        boolean[][] dp = new boolean[m + 1][n + 1];

        // 1. Base Case: 空字串與空模式匹配
        dp[0][0] = true;

        // 2. 初始化第一行 (s 是空字串，但 p 含有 * 的情況)
        // 例如 s="", p="a*b*"，dp[0][2] 依賴 dp[0][0]
        for (int j = 2; j <= n; j++) {
            if (p.charAt(j - 1) == '*') {
                dp[0][j] = dp[0][j - 2];
            }
        }

        // 3. 填表
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                char sc = s.charAt(i - 1);
                char pc = p.charAt(j - 1);

                if (pc == sc || pc == '.') {
                    // 一般匹配：直接看左上角
                    dp[i][j] = dp[i - 1][j - 1];
                } else if (pc == '*') {
                    // 遇到魔王星號
                    // 選項 1: 匹配 0 次 (忽略這組 x*)
                    if (dp[i][j - 2]) {
                        dp[i][j] = true;
                    }
                    // 選項 2: 匹配 1 次或多次 (前提是 s 目前字元能對上 * 前面的字元)
                    else {
                        char prevP = p.charAt(j - 2);
                        if (prevP == sc || prevP == '.') {
                            dp[i][j] = dp[i - 1][j];
                        }
                    }
                }
            }
        }
        return dp[m][n];
    }

    public static void main(String[] args) {
        RegularExpressionMatching solver = new RegularExpressionMatching();

        String s1 = "aa", p1 = "a";
        System.out.println("Test Case 1: " + s1 + ", " + p1);
        System.out.println("Result: " + solver.isMatch(s1, p1)); // 應為 false

        String s2 = "aa", p2 = "a*";
        System.out.println("Test Case 2: " + s2 + ", " + p2);
        System.out.println("Result: " + solver.isMatch(s2, p2)); // 應為 true

        String s3 = "ab", p3 = ".*";
        System.out.println("Test Case 3: " + s3 + ", " + p3);
        System.out.println("Result: " + solver.isMatch(s3, p3)); // 應為 true

        String s4 = "aab", p4 = "c*a*b";
        System.out.println("Test Case 4: " + s4 + ", " + p4);
        System.out.println("Result: " + solver.isMatch(s4, p4)); // 應為 true

        String s5 = "mississippi", p5 = "mis*is*p*.";
        System.out.println("Test Case 5: " + s5 + ", " + p5);
        System.out.println("Result: " + solver.isMatch(s5, p5)); // 應為 false
    }
}
