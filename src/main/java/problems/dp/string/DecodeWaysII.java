package problems.dp.string;

/**
 * @formatter:off
 * 639. Decode Ways II
 * 題目描述：
 * 和 91 題類似，但現在數字可以用 '*' 代替，'*' 可以代表 1 到 9 的任意數字。
 * 請問這個包含 '*' 的字串總共有多少種解讀方式？
 * 限制：
 * - '*' 可以是 1-9。
 * - '**' 可以是 11-19 或 21-26。
 * - 答案需要對 10^9 + 7 取模。
 * 範例：
 * Input: s = "*"
 * Output: 9 (1-9)
 * Input: s = "1*"
 * Output: 9 (11-19)
 * Input: s = "2*"
 * Output: 15 (21-26, 121-126)
 * Input: s = "**"
 * Output: 96
 * * 核心概念：
 * 這是序列型動態規劃 (Sequence DP) 的進階版。
 * 難點在於 '*' 的不確定性，我們需要分情況討論 '*' 的不同可能性。
 * * 思考方式：
 * 1. 遞迴關係式：
 * 考慮字串的最後一個位置 i：
 * 情況 A (取單一數字)：
 * - 如果 s[i] == '*': 有 9 種可能 (1-9)。dp[i] += 9 * dp[i-1]。
 * - 如果 s[i] != '*': 有 1 種可能 (如果不是 0)。dp[i] += dp[i-1]。
 * 情況 B (取雙位數字)：
 * - 如果 s[i-1]s[i] 是 "**": 有 15 種可能 (11-19, 21-26)。dp[i] += 15 * dp[i-2]。
 * - 如果 s[i-1] 是 '1' 且 s[i] 是 '*': 有 9 種可能 (11-19)。dp[i] += 9 * dp[i-2]。
 * - 如果 s[i-1] 是 '2' 且 s[i] 是 '*': 有 6 種可能 (21-26)。dp[i] += 6 * dp[i-2]。
 * - 其他情況：0 種可能。
 * 2. Base Cases (邊界條件)：
 * dp[0] = 1 (空字串)。
 * dp[1]：
 * - 如果 s[0] == '*': 9 種可能。
 * - 如果 s[0] == '0': 0 種可能。
 * - 如果 s[0] 是 1-9: 1 種可能。
 * 3. 演算法 (Bottom-Up DP)：
 * 初始化 dp 陣列。
 * 遍歷字串，根據上述情況計算 dp[i]。
 * 4. 空間優化：
 * 同樣可以使用 O(1) 的 prev2, prev1 變數。
 * * 時間複雜度：O(N)。
 * - 只需要遍歷一次字串。
 * 空間複雜度：O(1)。
 * - 只使用固定數量的變數。
 * @formatter:on
 */
public class DecodeWaysII {
    public int numDecodings(String s) {
        long MOD = 1_000_000_007;
        int n = s.length();

        // prev2 = dp[i-2], prev1 = dp[i-1]
        long prev2 = 1;
        long prev1 = getSingleMultiplier(s.charAt(0));

        for (int i = 2; i <= n; i++) {
            char curr = s.charAt(i - 1);
            char prev = s.charAt(i - 2);

            // 1. 單字元貢獻：當前字元的倍數 * 前一格的總解法
            long ways1 = getSingleMultiplier(curr) * prev1;

            // 2. 雙字元貢獻：這兩個字元組合的倍數 * 前前一格的總解法
            long ways2 = getDoubleMultiplier(prev, curr) * prev2;

            long current = (ways1 + ways2) % MOD;

            // 滾動更新
            prev2 = prev1;
            prev1 = current;
        }

        return (int) prev1;
    }

    // ⚡ 邏輯拆解 A：單個字元能產生幾種解？
    private int getSingleMultiplier(char c) {
        if (c == '*')
            return 9; // 1~9
        if (c == '0')
            return 0; // 0 不能單獨存在
        return 1; // 1~9 的固定數字
    }

    // ⚡ 邏輯拆解 B：兩個字元湊在一起能產生幾種解？ (這是本題最難的部分)
    private int getDoubleMultiplier(char p, char c) {
        // 情況 1：兩個都是星號 **
        if (p == '*' && c == '*') {
            return 15; // 11~19 (9種) + 21~26 (6種)
        }

        // 情況 2：前星後數 *[0-9]
        if (p == '*') {
            if (c <= '6')
                return 2; // 可以是 1c 或 2c (例如 *6 可能是 16, 26)
            return 1; // 只能是 1c (例如 *7 只能是 17，因為 27 不合法)
        }

        // 情況 3：前數後星 [0-9]*
        if (c == '*') {
            if (p == '1')
                return 9; // 11~19
            if (p == '2')
                return 6; // 21~26
            return 0; // 3* 或 0* 都沒救
        }

        // 情況 4：兩個都是固定數字 (回到第一集的邏輯)
        int val = (p - '0') * 10 + (c - '0');
        if (val >= 10 && val <= 26)
            return 1;

        return 0;
    }

    // ---------------- Brute Force ------------------

    public int numDecodings_bruteForce(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        int n = s.length();
        long MOD = 1_000_000_007;

        // dp[i] 表示 s[0...i-1] 的解碼方式數量
        long[] dp = new long[n + 1];

        // Base case: 空字串有一種解讀方式
        dp[0] = 1;

        // Base case: 第一個字符
        char first = s.charAt(0);
        if (first == '*') {
            dp[1] = 9;
        } else if (first == '0') {
            dp[1] = 0;
        } else {
            dp[1] = 1;
        }

        for (int i = 2; i <= n; i++) {
            char one = s.charAt(i - 1);
            char two = s.charAt(i - 2);

            // 情況 A: 處理單一數字 (s[i-1])
            if (one == '*') {
                // '*' 可以是 1-9，所以有 9 種可能
                dp[i] = (dp[i] + 9 * dp[i - 1]) % MOD;
            } else if (one != '0') {
                // 普通數字 (1-9)，只有 1 種可能
                dp[i] = (dp[i] + dp[i - 1]) % MOD;
            }

            // 情況 B: 處理雙位數字 (s[i-2]s[i-1])
            if (two == '*') {
                if (one == '*') {
                    // "**": 11-19 (9種) + 21-26 (6種) = 15 種
                    dp[i] = (dp[i] + 15 * dp[i - 2]) % MOD;
                } else if (one >= '0' && one <= '6') {
                    // "*1" ~ "*6": 只有 1x, 2x valid，共 6 種
                    dp[i] = (dp[i] + 6 * dp[i - 2]) % MOD;
                } else {
                    // "*7" ~ "*9": 無效
                }
            } else if (two == '1') {
                if (one == '*') {
                    // "1*": 11-19，共 9 種
                    dp[i] = (dp[i] + 9 * dp[i - 2]) % MOD;
                } else {
                    // "11" ~ "19": 只有 1 種
                    dp[i] = (dp[i] + dp[i - 2]) % MOD;
                }
            } else if (two == '2') {
                if (one == '*') {
                    // "2*": 21-26，共 6 種
                    dp[i] = (dp[i] + 6 * dp[i - 2]) % MOD;
                } else if (one >= '0' && one <= '6') {
                    // "20" ~ "26": 只有 1 種
                    dp[i] = (dp[i] + dp[i - 2]) % MOD;
                }
            }
            // 其他情況 (two == '0' 或 two >= '3') 無效
        }

        return (int) dp[n];
    }
}
