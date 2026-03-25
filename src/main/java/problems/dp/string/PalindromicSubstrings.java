package problems.dp.string;

/**
 * @formatter:off
 * 題目：給你一個字串 s，請計算 s 中「所有」子字串（Substrings）共有多少個是回文（Palindromes）。
 * 範例：s = "aaa"
 * 子字串有："a", "a", "a", "aa", "aa", "aaa"
 * 回文有："a", "a", "a", "aa", "aa", "aaa"
 * 答案：6
 * @formatter:on
 */
public class PalindromicSubstrings {
    public int countSubstrings(String s) {
        if (s == null || s.length() == 0)
            return 0;

        // 1. 預處理字串
        StringBuilder sb = new StringBuilder("#");
        for (char c : s.toCharArray()) {
            sb.append(c).append("#");
        }
        String t = sb.toString();
        int n = t.length();
        int[] p = new int[n];
        int C = 0, R = 0;
        int totalCount = 0;

        // 2. 馬拉車核心邏輯
        for (int i = 0; i < n; i++) {
            int mirror = 2 * C - i;

            if (i < R) {
                p[i] = Math.min(R - i, p[mirror]);
            }

            // 擴散
            int r = i + (1 + p[i]);
            int l = i - (1 + p[i]);
            while (r < n && l >= 0 && t.charAt(r) == t.charAt(l)) {
                p[i]++;
                r++;
                l--;
            }

            // 更新邊界
            if (i + p[i] > R) {
                C = i;
                R = i + p[i];
            }

            // 3. 累加這個點貢獻的迴文數量
            // 公式：(半徑 + 1) / 2
            totalCount += (p[i] + 1) / 2;
        }

        return totalCount;
    }

    public static void main(String[] args) {
        PalindromicSubstrings solver = new PalindromicSubstrings();

        // --------------------------------------------------
        // 測試案例 1：一般字串
        // s = "aba" -> "a", "b", "a", "aba"
        // 預期結果：4
        // --------------------------------------------------
        printTest(solver, "aba", 4);

        // --------------------------------------------------
        // 測試案例 2：全相同字串 (最考驗效能的情況)
        // s = "aaa" -> "a", "a", "a", "aa", "aa", "aaa"
        // 預期結果：6
        // --------------------------------------------------
        printTest(solver, "aaa", 6);

        // --------------------------------------------------
        // 測試案例 3：偶數長度迴文
        // s = "abba" -> "a", "b", "b", "a", "bb", "abba"
        // 預期結果：6
        // --------------------------------------------------
        printTest(solver, "abba", 6);

        // --------------------------------------------------
        // 測試案例 4：完全沒迴文 (除了單個字母)
        // s = "abc" -> "a", "b", "c"
        // 預期結果：3
        // --------------------------------------------------
        printTest(solver, "abc", 3);
    }

    private static void printTest(PalindromicSubstrings solver, String s, int expected) {
        int result = solver.countSubstrings(s);
        System.out.printf("輸入: \"%s\"%n", s);
        System.out.printf("  預期結果: %d%n", expected);
        System.out.printf("  實際結果: %d%n", result);
        System.out.println(result == expected ? "  測試通過！" : "  測試失敗...");
        System.out.println("--------------------------------------------------");
    }
}
