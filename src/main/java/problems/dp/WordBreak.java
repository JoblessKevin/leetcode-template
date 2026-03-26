package problems.dp;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WordBreak {
    /**
     * O(N^2), 每次都要切 1~N 長度的字串
     */
    public boolean wordBreak(String s, List<String> wordDict) {
        Set<String> wordSet = new HashSet<>(wordDict);

        int n = s.length();
        boolean[] dp = new boolean[n + 1];
        dp[0] = true;

        for (int i = 1; i <= n; i++) {
            for (int j = 0; j < i; j++) {
                if (dp[j] && wordSet.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }

        return dp[n];
    }

    /**
     * O(N*L), L 是字典裡最長單字的長度
     */
    public boolean wordBreak_optimized(String s, List<String> wordDict) {
        Set<String> wordSet = new HashSet<>(wordDict);

        int maxLength = 0;
        for (String word : wordDict) {
            maxLength = Math.max(maxLength, word.length());
        }

        int n = s.length();
        boolean[] dp = new boolean[n + 1];
        dp[0] = true;

        for (int i = 1; i <= n; i++) {
            int start = Math.max(0, i - maxLength);

            for (int j = start; j < i; j++) {
                if (dp[j] && wordSet.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }

        return dp[n];
    }

    public static void main(String[] args) {
        WordBreak solver = new WordBreak();

        // --------------------------------------------------
        // 測試案例 1：經典的 leetcode
        // 觀察重點：dp 陣列是如何在 index 4 和 8 變成 true 的
        // --------------------------------------------------
        String s1 = "leetcode";
        List<String> dict1 = Arrays.asList("leet", "code");

        System.out.println("=== 測試案例 1 (leetcode) ===");
        System.out.println("一般版結果: " + solver.wordBreak(s1, dict1));
        System.out.println("優化版結果: " + solver.wordBreak_optimized(s1, dict1));
        System.out.println("--------------------------------------------------");

        // --------------------------------------------------
        // 測試案例 2：長度極端差異的字典
        // 觀察重點：這組測資最能體現「優化版」的威力！
        // --------------------------------------------------
        String s2 = "catsandog";
        List<String> dict2 = Arrays.asList("cats", "dog", "sand", "and", "cat");

        System.out.println("=== 測試案例 2 (catsandog) ===");
        System.out.println("一般版結果: " + solver.wordBreak(s2, dict2));
        System.out.println("優化版結果: " + solver.wordBreak_optimized(s2, dict2));
    }
}
