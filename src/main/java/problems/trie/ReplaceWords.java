package problems.trie;

import java.util.Arrays;
import java.util.List;

public class ReplaceWords {
    private static class TrieNode {
        private TrieNode[] children;
        private boolean isEndOfWord;

        public TrieNode() {
            this.children = new TrieNode[26];
            this.isEndOfWord = false;
        }
    }

    private TrieNode root;

    public ReplaceWords() {
        this.root = new TrieNode();
    }

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

    private String searchShortestPrefix(String word) {
        TrieNode curr = root;
        for (int i = 0; i < word.length(); i++) {
            int index = word.charAt(i) - 'a';

            if (curr.children[index] == null)
                return word;

            curr = curr.children[index];

            if (curr.isEndOfWord)
                return word.substring(0, i + 1);
        }

        return word;
    }

    public String replaceWords(List<String> dictionary, String sentence) {
        root = new TrieNode();

        for (String word : dictionary) {
            insert(word);
        }

        StringBuilder result = new StringBuilder();
        String[] words = sentence.split(" ");

        for (int i = 0; i < words.length; i++) {
            if (i > 0)
                result.append(" ");

            String replacement = searchShortestPrefix(words[i]);
            result.append(replacement);
        }

        return result.toString();
    }

    // --- 測試區 ---
    public static void main(String[] args) {
        ReplaceWords solver = new ReplaceWords();

        // 測試案例 1
        List<String> dict1 = Arrays.asList("cat", "bat", "rat");
        String sentence1 = "the cattle was rattled by the battery";

        System.out.println("Original: " + sentence1);
        System.out.println("Result:   " + solver.replaceWords(dict1, sentence1));
        // 預期: "the cat was rat by the bat"

        System.out.println("--------------------------------------------------");

        // 測試案例 2 (最短原則測試)
        // 字典有 "a", "aa", "aaa"。
        // 輸入 "aaaaa"，應該要被換成 "a" (因為它最短)
        List<String> dict2 = Arrays.asList("a", "aa", "aaa");
        String sentence2 = "a aa aaa aaaaa";

        System.out.println("Original: " + sentence2);
        System.out.println("Result:   " + solver.replaceWords(dict2, sentence2));
        // 預期: "a a a a"
    }
}
