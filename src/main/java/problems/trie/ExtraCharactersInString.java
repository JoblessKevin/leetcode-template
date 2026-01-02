package problems.trie;

public class ExtraCharactersInString {
    private static class TrieNode {
        private TrieNode[] children;
        private boolean isEndOfWord;

        public TrieNode() {
            this.children = new TrieNode[26];
            this.isEndOfWord = false;
        }
    }

    private TrieNode root;

    private void insert(String word) {
        TrieNode curr = root;
        for (char ch : word.toCharArray()) {
            int index = ch - 'a';

            if (curr.children[index] == null) {
                curr.children[index] = new TrieNode();
            }

            curr = curr.children[index];
        }

        curr.isEndOfWord = true;
    }

    public int minExtraChar(String s, String[] dictionary) {
        root = new TrieNode();
        for (String word : dictionary) {
            insert(word);
        }

        int n = s.length();
        int[] dp = new int[n + 1];

        for (int i = n - 1; i >= 0; i--) {
            dp[i] = dp[i + 1] + 1;
            TrieNode node = root;
            for (int j = i; j < n; j++) {
                int index = s.charAt(j) - 'a';
                if (node.children[index] == null)
                    break;

                node = node.children[index];

                if (node.isEndOfWord) {
                    dp[i] = Math.min(dp[i], dp[j + 1]);
                }
            }
        }

        return dp[0];
    }

    // --- 把這段 main 方法貼在類別裡面 ---
    public static void main(String[] args) {
        ExtraCharactersInString solver = new ExtraCharactersInString();

        System.out.println("=== 測試開始 ===");

        // 測試案例 1: LeetCode 標準範例
        // "leet" 和 "code" 在字典裡，中間夾了一個 "s" 是多餘的
        String s1 = "leetscode";
        String[] dict1 = { "leet", "code", "leetcode" };
        int result1 = solver.minExtraChar(s1, dict1);
        printResult(1, s1, result1, 1);

        // 測試案例 2: 完全匹配 (沒有多餘字元)
        // 應該由 "apple" + "pie" 組成
        String s2 = "applepie";
        String[] dict2 = { "apple", "pie", "ap", "ple" };
        int result2 = solver.minExtraChar(s2, dict2);
        printResult(2, s2, result2, 0);

        // 測試案例 3: 完全不匹配 (全部都是多餘字元)
        // 字典裡沒有任何字能匹配
        String s3 = "hello";
        String[] dict3 = { "world", "java" };
        int result3 = solver.minExtraChar(s3, dict3);
        printResult(3, s3, result3, 5); // hello 長度為 5

        // 測試案例 4: 重疊選擇 (貪婪陷阱)
        // 雖然 "abcd" 在字典裡，但如果選了 "abcd"，剩下 "ef" (2個多餘)
        // 如果選 "abc" + "def"，則剩下 0 個多餘
        // 您的 DP 演算法應該能找到最佳解 0
        String s4 = "abcdef";
        String[] dict4 = { "abc", "def", "abcd" };
        int result4 = solver.minExtraChar(s4, dict4);
        printResult(4, s4, result4, 0);

        // 測試案例 5: 單字元測試
        String s5 = "a";
        String[] dict5 = { "a" };
        int result5 = solver.minExtraChar(s5, dict5);
        printResult(5, s5, result5, 0);
    }

    // 輔助函式：用來印出漂亮的測試結果
    private static void printResult(int id, String input, int actual, int expected) {
        String status = (actual == expected) ? "PASS" : "FAIL";
        System.out.printf("Case %d [%s]: Input='%s', Expected=%d, Actual=%d\n",
                id, status, input, expected, actual);
    }
}
