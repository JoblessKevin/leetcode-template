package problems.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @formatter:off
 * 題目要求： 給定一個字串陣列 strs，將它們按「字母異位詞」分組。 字母異位詞是指由相同字母組成，但順序不同的字串（例如 "eat", "tea", "ate" 互為字母異位詞）。
 * 
 * 核心概念： - 字母異位詞的特性：如果兩個字串互為字母異位詞，那麼它們排序後的結果一定相同。 - 雜湊表 (HashMap)：利用雜湊表來儲存分組結果，鍵 (Key) 是排序後的字串，值 (Value) 是對應的字母異位詞列表。
 * 
 * 解題思路： 1. 建立一個 HashMap，用來儲存分組結果。 2. 遍歷輸入的每個字串：
 *    a. 將當前字串轉換為字元陣列。 b. 對字元陣列進行排序，得到排序後的字串（作為雜湊表的 Key）。
 *    c. 檢查 HashMap 中是否已經存在這個 Key：
 *       - 如果存在，將當前字串加入對應的 Value 列表中。
 *       - 如果不存在，建立一個新的列表，將當前字串加入，並將該列表存入 HashMap。
 * 3. 遍歷完所有字串後，HashMap 的所有 Value 即為分組結果。 4. 返回 HashMap 的所有 Value 組成的列表。
 * 
 * 複雜度分析： - 時間複雜度：O(N * K log K)，其中 N 是字串的數量，K 是每個字串的最大長度。主要耗時在於對每個字串進行排序。
 * - 空間複雜度：O(N * K)，需要空間來儲存 HashMap 和結果列表。
 * @formatter:on
 */
public class GroupAnagrams {
    public List<List<String>> groupAnagrams(String[] strs) {
        if (strs == null || strs.length == 0)
            return new ArrayList<>();

        // Key: 排序後的字串, Value: 原始字串列表
        Map<String, List<String>> map = new HashMap<>();

        for (String s : strs) {
            // 1. 將字串轉為 char 陣列並排序
            char[] chars = s.toCharArray();
            Arrays.sort(chars);

            // 2. 排序後的結果作為 Key
            String key = String.valueOf(chars);

            // 3. Java 8+ 的優雅寫法：如果 key 不存在就建立新的 List
            map.computeIfAbsent(key, k -> new ArrayList<>()).add(s);
        }

        return new ArrayList<>(map.values());
    }

    public static void main(String[] args) {
        GroupAnagrams solver = new GroupAnagrams();

        // Test Case 1: 範例中的測試
        String[] strs1 = {"eat", "tea", "tan", "ate", "nat", "bat"};
        List<List<String>> result1 = solver.groupAnagrams(strs1);
        // 修正點：使用 \" 來轉義雙引號
        System.out.println("Test Case 1: [\"eat\", \"tea\", \"tan\", \"ate\", \"nat\", \"bat\"] -> Groups: "
                                        + result1);

        // Test Case 2: 空陣列
        String[] strs2 = {};
        List<List<String>> result2 = solver.groupAnagrams(strs2);
        System.out.println("Test Case 2: [] -> Groups: " + result2);

        // Test Case 3: 只有一個字串
        String[] strs3 = {"a"};
        List<List<String>> result3 = solver.groupAnagrams(strs3);
        System.out.println("Test Case 3: [\"a\"] -> Groups: " + result3);

        // Test Case 4: 所有字串都相同
        String[] strs4 = {"a", "a", "a"};
        List<List<String>> result4 = solver.groupAnagrams(strs4);
        System.out.println("Test Case 4: [\"a\", \"a\", \"a\"] -> Groups: " + result4);

        // Test Case 5: 包含空字串
        String[] strs5 = {"", "b", ""};
        List<List<String>> result5 = solver.groupAnagrams(strs5);
        System.out.println("Test Case 5: [\"\", \"b\", \"\"] -> Groups: " + result5);
    }
}
