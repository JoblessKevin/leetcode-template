package templates.dp.string;

public class KmpDp {
    /**
     * KMP 主程式：在 text 中尋找 pattern
     */
    public int search(String text, String pattern) {
        if (pattern.length() == 0)
            return 0;

        // 1. 預處理：計算 LPS (Longest Prefix Suffix) 陣列
        int[] lps = buildLPS(pattern);

        int i = 0; // text 的指針
        int j = 0; // pattern 的指針

        while (i < text.length()) {
            if (text.charAt(i) == pattern.charAt(j)) {
                // 字元匹配，雙方前進
                i++;
                j++;
            }

            if (j == pattern.length()) {
                // 完全匹配成功！回傳起始索引
                return i - j;
            } else if (i < text.length() && text.charAt(i) != pattern.charAt(j)) {
                // 匹配失敗，關鍵就在這裡！
                if (j != 0) {
                    // 根據 LPS 陣列「跳躍」，不需要從頭開始
                    j = lps[j - 1];
                } else {
                    // 如果 j 已經在起點了，只好移動 text 指針
                    i++;
                }
            }
        }
        return -1; // 找不到
    }

    /**
     * 核心 DP：計算模式字串的 LPS 陣列, LPS(Longest Proper Prefix which is also a Suffix)
     */
    private int[] buildLPS(String pattern) {
        int m = pattern.length();
        int[] lps = new int[m];

        // l (Left): 前綴指針，也代表當前最長相同前後綴的長度
        // r (Right): 後綴指針，負責向右掃描
        int l = 0;
        int r = 1;

        lps[0] = 0; // 第一個字元沒有前後綴

        while (r < m) {
            if (pattern.charAt(r) == pattern.charAt(l)) {
                // 情況一：字元相同，表示前後綴長度增加了
                l++;
                lps[r] = l;
                r++;
            } else {
                // 情況二：字元不同，l 指針需要「往回跳」
                if (l != 0) {
                    // DP 的精髓：跳回前一個可能的最長前綴位置
                    l = lps[l - 1];
                } else {
                    // 如果跳回起點還是不同，那這個位置的 lps 就是 0
                    lps[r] = 0;
                    r++;
                }
            }
        }
        return lps;
    }

    public static void main(String[] args) {
        KmpDp kmp = new KmpDp();
        String text = "ABABDABACDABABCABAB";
        String pattern = "ABABCABAB";
        int result = kmp.search(text, pattern);
        System.out.println("Pattern 找到的起始位置: " + result);
    }
}
