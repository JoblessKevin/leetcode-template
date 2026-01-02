package problems.trie;

public class ImplementTrieII {
    private static class TrieNode {
        TrieNode[] children = new TrieNode[26];
        int wordCount = 0; // 記錄完整單字的數量
        int prefixCount = 0; // 記錄經過此節點的單字數量 (前綴數
    }

    private TrieNode root;

    public ImplementTrieII() {
        root = new TrieNode();
    }

    public void insert(String word) {
        TrieNode current = root;
        for (int i = 0; i < word.length(); i++) {
            int index = word.charAt(i) - 'a';
            if (current.children[index] == null) {
                current.children[index] = new TrieNode();
            }
            current = current.children[index];
            current.prefixCount++; // 路徑上的計數 +1
        }
        current.wordCount++; // 結尾處計數 +1
    }

    /**
     * 刪除單字 (LeetCode 1804 必備)
     * 假設該單字一定存在於 Trie 中
     */
    public void erase(String word) {
        TrieNode current = root;
        for (int i = 0; i < word.length(); i++) {
            int index = word.charAt(i) - 'a';
            current = current.children[index];
            current.prefixCount--; // 路徑上的計數 -1
        }
        current.wordCount--; // 結尾處計數 -1
    }

    // --- 查詢功能 (支援 Trie I 與 Trie II) ---

    // 用於 Trie I: 是否存在該單字
    public boolean search(String word) {
        return countWordsEqualTo(word) > 0;
    }

    // 用於 Trie I: 是否存在該前綴
    public boolean startsWith(String prefix) {
        return countWordsStartingWith(prefix) > 0;
    }

    // 用於 Trie II: 計算該單字出現幾次
    public int countWordsEqualTo(String word) {
        TrieNode node = searchPrefix(word);
        return (node != null) ? node.wordCount : 0;
    }

    // 用於 Trie II: 計算以該前綴開頭的單字有幾個
    public int countWordsStartingWith(String prefix) {
        TrieNode node = searchPrefix(prefix);
        return (node != null) ? node.prefixCount : 0;
    }

    // --- 輔助方法 ---

    private TrieNode searchPrefix(String word) {
        TrieNode current = root;
        for (int i = 0; i < word.length(); i++) {
            int index = word.charAt(i) - 'a';
            if (current.children[index] == null) {
                return null;
            }
            current = current.children[index];
        }
        return current;
    }

    // --- 測試區 ---
    public static void main(String[] args) {
        ImplementTrieII trie = new ImplementTrieII();

        trie.insert("apple");
        trie.insert("apple"); // 插入兩次
        trie.insert("app");

        System.out.println("Search 'apple': " + trie.search("apple")); // true
        System.out.println("Count 'apple': " + trie.countWordsEqualTo("apple")); // 2

        System.out.println("StartsWith 'app': " + trie.startsWith("app")); // true
        System.out.println("Count StartsWith 'app': " + trie.countWordsStartingWith("app")); // 3 ("apple", "apple",
                                                                                             // "app")

        trie.erase("apple");
        System.out.println("Count 'apple' after erase: " + trie.countWordsEqualTo("apple")); // 1
    }
}