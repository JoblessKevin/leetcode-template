package problems.dp;

import java.util.*;

public class WordBreakII {
    public List<String> wordBreak(String s, List<String> wordDict) {
        // 1. 轉成 HashSet 加速查詢
        Set<String> wordSet = new HashSet<>(wordDict);

        // 2. 宣告我們的 DP 表格 (記憶體)
        // Key: 某段剩餘的字串 (例如 "sanddog")
        // Value: 這段字串能切出的所有合法組合 (例如 ["sand dog"])
        Map<String, List<String>> memo = new HashMap<>();

        // 3. 呼叫 DFS 開始由上而下切蛋糕
        return dfs(s, wordSet, memo);
    }

    private List<String> dfs(String s, Set<String> wordSet, Map<String, List<String>> memo) {
        // 🌟 DP 精神在此：如果這段字串以前算過，直接抄答案！絕對不重算！
        if (memo.containsKey(s)) {
            return memo.get(s);
        }

        List<String> result = new ArrayList<>();

        // Base Case：如果字串被切光了，代表我們成功走到底了
        // 回傳一個包含「空字串」的 List，讓上一層知道這條路是通的
        if (s.isEmpty()) {
            result.add("");
            return result;
        }

        // 開始嘗試從前面切出第一個單字 (長度從 1 到 s.length())
        for (int i = 1; i <= s.length(); i++) {
            String word = s.substring(0, i);

            // 如果切出來的這個單字在字典裡，我們就把剩下的字串丟給下一層！
            if (wordSet.contains(word)) {
                String remainder = s.substring(i);

                // 遞迴呼叫：請問剩下的字串 (remainder) 有幾種切法？
                List<String> subList = dfs(remainder, wordSet, memo);

                // 把下一層回傳的結果，接在我們剛剛切出的單字後面
                for (String sub : subList) {
                    if (sub.isEmpty()) {
                        // 如果後面是空的，代表這是最後一個字，不需要加空白
                        result.add(word);
                    } else {
                        // 如果後面還有字，中間補一個空白接起來
                        result.add(word + " " + sub);
                    }
                }
            }
        }

        // 🌟 存入 DP 表格：把這段字串算出來的所有結果存進 HashMap，下次就不用算了！
        memo.put(s, result);
        return result;
    }

    public static void main(String[] args) {
        WordBreakII solver = new WordBreakII();

        // --------------------------------------------------
        // 測試案例 1：經典多重拆分
        // 觀察重點：看 dfs 是如何一路切到底，再把結果「往上組裝」回來的
        // --------------------------------------------------
        String s1 = "catsanddog";
        List<String> dict1 = Arrays.asList("cat", "cats", "and", "sand", "dog");
        System.out.println("=== 測試案例 1 (catsanddog) ===");
        System.out.println(solver.wordBreak(s1, dict1));
        System.out.println("--------------------------------------------------");

        // --------------------------------------------------
        // 測試案例 2：排列組合大爆發
        // --------------------------------------------------
        String s2 = "pineapplepenapple";
        List<String> dict2 = Arrays.asList("apple", "pen", "applepen", "pine", "pineapple");
        System.out.println("=== 測試案例 2 (pineapple...) ===");
        System.out.println(solver.wordBreak(s2, dict2));
        System.out.println("--------------------------------------------------");

        // --------------------------------------------------
        // 測試案例 3：Memoization 救命測資 (必看！)
        // 觀察重點：如果沒有 HashMap，這題會跑到天荒地老。
        // 有了 HashMap，它會瞬間發現 "b" 湊不出來而瘋狂剪枝！
        // --------------------------------------------------
        String s3 = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaab";
        List<String> dict3 = Arrays.asList("a", "aa", "aaa", "aaaa");
        System.out.println("=== 測試案例 3 (極端死胡同) ===");
        long startTime = System.currentTimeMillis();
        System.out.println(solver.wordBreak(s3, dict3));
        System.out.println("耗時: " + (System.currentTimeMillis() - startTime) + " ms");
    }
}
