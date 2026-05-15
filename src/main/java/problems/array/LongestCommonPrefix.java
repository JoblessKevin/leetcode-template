package problems.array;

/**
 * @formatter:off
 * 題目要求： 給定一個字串陣列，找出所有字串的最長共同前綴 (Longest Common Prefix)。 如果沒有共同前綴，則返回空字串 ""。
 * 
 * 核心概念： - 橫向掃描 (Horizontal Scanning)：逐一比較字串，不斷縮小共同前綴的範圍。 - 縱向掃描 (Vertical Scanning)：逐一比較字串的第 i
 * 個字元，一旦有不同就停止。 - 分而治之 (Divide and Conquer)：將問題拆解成左右兩半，分別求出共同前綴再合併。 - 字典序 (Lexicographical
 * Order)：利用排序後首尾字串的特性來優化。
 * 
 * 解題思路 (橫向掃描)： 1. 初始化共同前綴為第一個字串。 2. 遍歷後續每個字串，將當前共同前綴與該字串進行比較。 3. 如果發現共同前綴不是當前字串的前綴，就縮短共同前綴
 * (去掉最後一個字元)。 4. 重複步驟 3，直到共同前綴是當前字串的前綴，或者共同前綴變為空字串。 5. 如果共同前綴變為空字串，表示沒有共同前綴，直接返回 ""。 6.
 * 遍歷完所有字串後，剩下的共同前綴即為答案。
 * 
 * 複雜度分析 (橫向掃描)： - 時間複雜度：O(S)，其中 S 是所有字串的總長度。在最壞情況下，需要遍歷所有字元。 - 空間複雜度：O(1)，只需要常數空間來儲存共同前綴。
 * @formatter:on
 */
public class LongestCommonPrefix {
    // ------------------------------- Horizon Solution -----------------------------------

    public String longestCommonPrefix_horizon(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }

        // 初始化共同前綴為第一個字串
        String prefix = strs[0];

        // 從第二個字串開始遍歷
        for (int i = 1; i < strs.length; i++) {
            // 如果當前字串不以 prefix 開頭，就縮短 prefix
            while (strs[i].indexOf(prefix) != 0) {
                prefix = prefix.substring(0, prefix.length() - 1);

                // 如果 prefix 變為空字串，表示沒有共同前綴
                if (prefix.isEmpty()) {
                    return "";
                }
            }
        }

        return prefix;
    }

    // ------------------------------- Vertical Solution -----------------------------------

    public String longestCommonPrefix_vertical(String[] strs) {
        if (strs == null || strs.length == 0)
            return "";

        // 外層迴圈：以第一個字串的每一個字元 i 為基準
        for (int i = 0; i < strs[0].length(); i++) {
            char ch = strs[0].charAt(i); // 拿到點名標準：第 i 個字元是什麼？

            // 內層迴圈：檢查其他所有字串 (從第 2 個字串開始)
            for (int j = 1; j < strs.length; j++) {
                // 檢查點：
                // 1. i 是不是已經超過第 j 個字串的長度了？ (代表有人比較短，點名點完了)
                // 2. 第 j 個字串在第 i 個位置的字元，是否跟標準 c 不一樣？
                if (i == strs[j].length() || strs[j].charAt(i) != ch) {
                    // 只要一對不上，立刻收工！
                    // 截取 strs[0] 從 0 到 i 之前的內容
                    return strs[0].substring(0, i);
                }
            }
        }

        // 如果全部跑完都沒在中途回傳，代表第一個字串本身就是最長前綴
        return strs[0];
    }

    // ------------------------------- Trie Solution -----------------------------------

    private static class TrieNode {
        TrieNode[] children = new TrieNode[26];
        int childCount = 0; // 記錄當前節點有多少個子節點 (用來判斷是否分叉)
        boolean isEnd = false; // 記錄是否有字串在此結束
    }

    private void insert(TrieNode root, String word) {
        TrieNode curr = root;
        for (char ch : word.toCharArray()) {
            int idx = ch - 'a';
            if (curr.children[idx] == null) {
                curr.children[idx] = new TrieNode();
                curr.childCount++;
            }
            curr = curr.children[idx];
        }
        curr.isEnd = true;
    }

    /**
     * 解題思路 (Trie 字典樹)
     */
    public String longestCommonPrefix_trie(String[] strs) {
        if (strs == null || strs.length == 0)
            return "";
        if (strs.length == 1)
            return strs[0];

        TrieNode root = new TrieNode();
        // 1. 將所有字串插入 Trie
        for (String s : strs) {
            if (s.isEmpty())
                return ""; // 只要有一個空字串，LCP 必為空
            insert(root, s);
        }

        // 2. 從根節點開始遍歷，找尋不分叉的路徑
        StringBuilder sb = new StringBuilder();
        TrieNode curr = root;

        // 使用第一個字串作為導引路徑向下搜尋
        for (char ch : strs[0].toCharArray()) {
            // 條件：只有一個子節點，且目前節點不是某個短字串的結尾
            if (curr.childCount == 1 && !curr.isEnd) {
                sb.append(ch);
                curr = curr.children[ch - 'a'];
            } else {
                break;
            }
        }
        return sb.toString();
    }

    // ------------------------------- Test Cases -----------------------------------

    public static void main(String[] args) {
        LongestCommonPrefix solver = new LongestCommonPrefix();

        // Test Case 1: 範例中的測試
        String[] strs1 = {"flower", "flow", "flight"};
        String result1 = solver.longestCommonPrefix_vertical(strs1);
        System.out.println("Test Case 1: [\"flower\", \"flow\", \"flight\"] -> LCP: " + result1);
        // Expected: "fl"

        // Test Case 2: 沒有共同前綴
        String[] strs2 = {"dog", "racecar", "car"};
        String result2 = solver.longestCommonPrefix_vertical(strs2);
        System.out.println("Test Case 2: [\"dog\", \"racecar\", \"car\"] -> LCP: " + result2);
        // Expected: ""

        // Test Case 3: 所有字串都相同
        String[] strs3 = {"apple", "apple", "apple"};
        String result3 = solver.longestCommonPrefix_vertical(strs3);
        System.out.println("Test Case 3: [\"apple\", \"apple\", \"apple\"] -> LCP: " + result3);
        // Expected: "apple"

        // Test Case 4: 只有一個字串
        String[] strs4 = {"hello"};
        String result4 = solver.longestCommonPrefix_vertical(strs4);
        System.out.println("Test Case 4: [\"hello\"] -> LCP: " + result4);
        // Expected: "hello"

        // Test Case 5: 空陣列
        String[] strs5 = {};
        String result5 = solver.longestCommonPrefix_vertical(strs5);
        System.out.println("Test Case 5: [] -> LCP: " + result5);
        // Expected: ""

        // Test Case 6: 包含空字串
        String[] strs6 = {"flower", "", "flight"};
        String result6 = solver.longestCommonPrefix_vertical(strs6);
        System.out.println("Test Case 6: [\"flower\", \"\", \"flight\"] -> LCP: " + result6);
        // Expected: ""
    }
}
