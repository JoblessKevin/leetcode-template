package problems.dp.string;

/**
 * 關鍵細節：為什麼字元相同時要 + dp[i-1][j]?
 */
public class DistinctSubsequences {
    public int numDistinct(String s, String t) {
        int m = s.length();
        int n = t.length();

        // dp[i][j] 代表 s 的前 i 個字元中，有多少個子序列等於 t 的前 j 個字元
        int[][] dp = new int[m + 1][n + 1];

        // 邊界條件：如果 t 是空字串，方法數永遠是 1 (全部刪光)
        for (int i = 0; i <= m; i++) {
            dp[i][0] = 1;
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                // 如果字元相同
                if (s.charAt(i - 1) == t.charAt(j - 1)) {
                    // 方法 = 用掉 s[i-1] 去匹配 (dp[i-1][j-1]) + 留著 s[i-1] 不用 (dp[i-1][j])
                    dp[i][j] = dp[i - 1][j - 1] + dp[i - 1][j];
                } else {
                    // 字元不同，只能略過 s[i-1]，看之前的 s 能配出多少個 t
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }
        return dp[m][n];
    }

    public int numDistinct_optimized(String s, String t) {
        int n = t.length();
        int[] dp = new int[n + 1];

        // 初始狀態：湊出空字串的方法數為 1
        dp[0] = 1;

        for (int i = 1; i <= s.length(); i++) {
            // 必須逆序遍歷，才不會踩到這一輪剛更新的值
            for (int j = n; j >= 1; j--) {
                if (s.charAt(i - 1) == t.charAt(j - 1)) {
                    // dp[j] (新) = dp[j-1] (左上舊值) + dp[j] (正上舊值)
                    dp[j] = dp[j - 1] + dp[j];
                }
                // else: dp[j] = dp[j] (保持原樣)，所以不用寫
            }
        }
        return dp[n];
    }

    public static void main(String[] args) {
        DistinctSubsequences solver = new DistinctSubsequences();

        String s1 = "rabbbit", t1 = "rabbit";
        System.out.println("Test Case 1: " + s1 + ", " + t1);
        System.out.println("Result: " + solver.numDistinct_optimized(s1, t1)); // 應為 3

        String s2 = "babgbag", t2 = "bag";
        System.out.println("Test Case 2: " + s2 + ", " + t2);
        System.out.println("Result: " + solver.numDistinct_optimized(s2, t2)); // 應為 5
    }
}
