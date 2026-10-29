package problems.dp.string;

public class DecodeWays {
    public int numDecodings(String s) {
        int n = s.length();
        int[] dp = new int[n + 1];
        dp[0] = 1;
        dp[1] = s.charAt(0) == '0' ? 0 : 1;
        for (int i = 2; i <= n; i++) {
            int oneDigit = Integer.parseInt(s.substring(i - 1, i));
            int twoDigit = Integer.parseInt(s.substring(i - 2, i));
            if (oneDigit >= 1 && oneDigit <= 9) {
                dp[i] += dp[i - 1];
            }
            if (twoDigit >= 10 && twoDigit <= 26) {
                dp[i] += dp[i - 2];
            }
        }
        return dp[n];
    }

    public int numDecodings_optimized(String s) {
        if (s == null || s.length() == 0 || s.charAt(0) == '0')
            return 0;

        int n = s.length();
        // 想像成爬樓梯：dp[i-2] 是 prev2, dp[i-1] 是 prev1
        int prev2 = 1; // 代表空字串或初始狀態
        int prev1 = 1; // 代表第一個字元的解法

        for (int i = 2; i <= n; i++) {
            int current = 0;

            // 1. 嘗試取一格 (1-9)
            char one = s.charAt(i - 1);
            if (one != '0') {
                current += prev1;
            }

            // 2. 嘗試取兩格 (10-26)
            char ten = s.charAt(i - 2);
            char unit = s.charAt(i - 1);
            if (ten == '1' || (ten == '2' && unit <= '6')) {
                current += prev2;
            }

            // 如果當前這格完全沒路走 (例如出現 30)，直接回傳 0
            if (current == 0)
                return 0;

            // 滾動更新
            prev2 = prev1;
            prev1 = current;
        }

        return prev1;
    }
}
