package problems.greedy;

public class LexicographicallySmallestStringAfterDeletingDuplicateCharacters {
    /**
     * Greedy + Monotonic Stack, Time Complexity: O(n) Space Complexity: O(n)
     */
    public String lexSmallestAfterDeletion(String s) {
        // 1. 統計每個字元的總出現次數
        int[] totalCount = new int[26];
        for (char ch : s.toCharArray()) {
            totalCount[ch - 'a']++;
        }

        StringBuilder stack = new StringBuilder();
        // 記錄目前堆疊中每個字元的數量
        int[] stackCount = new int[26];

        for (char ch : s.toCharArray()) {
            totalCount[ch - 'a']--; // 這個字元已經從輸入流中看過了

            // 單調堆疊邏輯：如果當前字元 c 比堆疊頂端 top 小，且 top 可以被丟棄，就丟掉 top
            while (stack.length() > 0) {
                char top = stack.charAt(stack.length() - 1);

                if (top > ch) {
                    // 關鍵判斷：能不能丟掉 top？
                    // 條件 A: 堆疊裡還有其他的 top (stackCount > 1)
                    // 條件 B: 輸入字串後面還有 top (totalCount > 0)
                    if (stackCount[top - 'a'] > 1 || totalCount[top - 'a'] > 0) {
                        stack.deleteCharAt(stack.length() - 1);
                        stackCount[top - 'a']--;
                    } else {
                        break; // top 是最後一個獨苗，不能丟，迴圈結束
                    }
                } else {
                    break; // top <= c，符合字典序，不丟
                }
            }

            stack.append(ch);
            stackCount[ch - 'a']++;
        }

        // 2. 尾部修剪 (Post-processing)
        // 移除堆疊尾部的重複字元，因為較短的前綴字典序更小
        // 例如 "aacaa" -> "aac"
        while (stack.length() > 0) {
            char top = stack.charAt(stack.length() - 1);
            if (stackCount[top - 'a'] > 1) {
                stack.deleteCharAt(stack.length() - 1);
                stackCount[top - 'a']--;
            } else {
                break; // 遇到唯一存在的字元，停止修剪
            }
        }

        return stack.toString();
    }

    // ==========================================
    // Test cases
    // ==========================================
    public static void main(String[] args) {
        LexicographicallySmallestStringAfterDeletingDuplicateCharacters solver =
                                        new LexicographicallySmallestStringAfterDeletingDuplicateCharacters();

        System.out.println("=== 測試開始: Lexicographically Smallest String ===\n");

        // Case 1: 題目範例 1
        // 'c' 在中間比 'a' 大，但因為是唯一的，不能刪。尾巴的 'b' 保留。
        runTest(solver, "aacb", "aacb", "Example 1");

        // Case 2: 題目範例 2
        // 只有一個字元，不能刪
        runTest(solver, "z", "z", "Example 2");

        // Case 3: 尾部修剪測試
        // 堆疊過程可能會保留 "aacaa"，最後修剪掉尾巴多餘的 'a'
        runTest(solver, "aacaa", "aac", "Tail Trimming");

        // Case 4: 複雜亂序
        // 預期邏輯：盡量讓小的字元在前面。
        // "dbca" -> b, c, a 都比 d 小，且 d 後面沒了(不能刪)。
        // 但如果有重複: "dbcad" -> 第一個 d 遇到 b 會被刪(因為後面還有 d)。
        runTest(solver, "dbcad", "bcad", "Complex Removal");

        // Case 5: 全部相同
        // "aaaa" -> 刪到剩一個 "a"
        runTest(solver, "aaaa", "a", "All Same");

        // Case 6: 遞增序列 (什麼都不會被刪，除了尾部重複)
        // "abbc" -> "abbc" (b 有重複但順序正確，不用刪前面)
        runTest(solver, "abbc", "abbc", "Increasing Order");
    }

    public static void runTest(LexicographicallySmallestStringAfterDeletingDuplicateCharacters solver,
                                    String input, String expected, String testName) {
        System.out.println("Running: " + testName);
        System.out.println("Input:    \"" + input + "\"");

        long startTime = System.nanoTime();
        String result = solver.lexSmallestAfterDeletion(input);
        long endTime = System.nanoTime();

        System.out.println("Expected: \"" + expected + "\"");
        System.out.println("Got:      \"" + result + "\"");

        if (result.equals(expected)) {
            double timeInMs = (endTime - startTime) / 1_000_000.0;
            System.out.println("PASS (Time: " + String.format("%.4f", timeInMs) + " ms)");
        } else {
            System.err.println("FAIL");
        }
        System.out.println("--------------------------------------------------");
    }
}
