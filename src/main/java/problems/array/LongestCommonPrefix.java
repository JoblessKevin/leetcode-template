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
    /**
     * 找出所有字串的最長共同前綴 (橫向掃描)
     * 
     * @param strs 輸入的字串陣列
     * @return 最長共同前綴，如果沒有則返回 ""
     */
    public String longestCommonPrefix(String[] strs) {
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

    public static void main(String[] args) {
        LongestCommonPrefix solver = new LongestCommonPrefix();

        // Test Case 1: 範例中的測試
        String[] strs1 = {"flower", "flow", "flight"};
        String result1 = solver.longestCommonPrefix(strs1);
        System.out.println("Test Case 1: [\"flower\", \"flow\", \"flight\"] -> LCP: " + result1);
        // Expected: "fl"

        // Test Case 2: 沒有共同前綴
        String[] strs2 = {"dog", "racecar", "car"};
        String result2 = solver.longestCommonPrefix(strs2);
        System.out.println("Test Case 2: [\"dog\", \"racecar\", \"car\"] -> LCP: " + result2);
        // Expected: ""

        // Test Case 3: 所有字串都相同
        String[] strs3 = {"apple", "apple", "apple"};
        String result3 = solver.longestCommonPrefix(strs3);
        System.out.println("Test Case 3: [\"apple\", \"apple\", \"apple\"] -> LCP: " + result3);
        // Expected: "apple"

        // Test Case 4: 只有一個字串
        String[] strs4 = {"hello"};
        String result4 = solver.longestCommonPrefix(strs4);
        System.out.println("Test Case 4: [\"hello\"] -> LCP: " + result4);
        // Expected: "hello"

        // Test Case 5: 空陣列
        String[] strs5 = {};
        String result5 = solver.longestCommonPrefix(strs5);
        System.out.println("Test Case 5: [] -> LCP: " + result5);
        // Expected: ""

        // Test Case 6: 包含空字串
        String[] strs6 = {"flower", "", "flight"};
        String result6 = solver.longestCommonPrefix(strs6);
        System.out.println("Test Case 6: [\"flower\", \"\", \"flight\"] -> LCP: " + result6);
        // Expected: ""
    }
}
