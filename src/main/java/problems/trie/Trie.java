package problems.trie;

class Trie {
    public class TrieNode {
        private TrieNode[] children;
        private boolean isEndOfWord;

        public TrieNode() {
            this.children = new TrieNode[26];
            this.isEndOfWord = false;
        }
    }

    private TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    public void insert(String word) {
        TrieNode current = root;
        for (char ch : word.toCharArray()) {
            int index = ch - 'a';
            if (current.children[index] == null) {
                current.children[index] = new TrieNode();
            }

            current = current.children[index];
        }

        current.isEndOfWord = true;
    }

    public boolean search(String word) {
        TrieNode node = searchPrefix(word);
        return node != null && node.isEndOfWord;
    }

    public boolean startsWith(String prefix) {
        TrieNode node = searchPrefix(prefix);
        return node != null;
    }

    private TrieNode searchPrefix(String word) {
        TrieNode current = root;
        for (char ch : word.toCharArray()) {
            int index = ch - 'a';
            if (current.children[index] == null)
                return null;
            current = current.children[index];
        }

        return current;
    }

    public static void main(String[] args) {
        // 1. 初始化 Trie
        Trie trie = new Trie();
        System.out.println("=== 開始測試 Trie ===");

        // 2. 測試插入與搜尋 (Insert & Search)
        trie.insert("apple");
        System.out.println("1. Insert 'apple'");

        boolean searchApple = trie.search("apple");
        System.out.println("   Search 'apple': " + searchApple + " (預期: true)");

        // 測試「存在前綴但不是完整單字」的情況
        boolean searchApp = trie.search("app");
        System.out.println("   Search 'app':   " + searchApp + " (預期: false)");

        // 3. 測試前綴 (StartsWith)
        boolean startApp = trie.startsWith("app");
        System.out.println("   StartsWith 'app': " + startApp + " (預期: true)");

        // 4. 補上 'app' 這個字，再搜一次
        trie.insert("app");
        System.out.println("2. Insert 'app'");

        searchApp = trie.search("app");
        System.out.println("   Search 'app':   " + searchApp + " (預期: true)");

        // 5. 測試不存在的字
        boolean searchBanana = trie.search("banana");
        System.out.println("   Search 'banana': " + searchBanana + " (預期: false)");

        boolean startB = trie.startsWith("b");
        System.out.println("   StartsWith 'b':  " + startB + " (預期: false)");

        // 6. 簡單的視覺化檢查 (驗證結果)
        if (searchApple && !searchApp && startApp && !searchBanana) {
            // 注意：這裡的 !searchApp 是指第一次 search 'app' 的結果
            // 為了方便演示 main 流程，這裡就不寫複雜的斷言邏輯，直接看 console 輸出最準
        }
        System.out.println("=== 測試結束 ===");
    }
}

/**
 * Your Trie object will be instantiated and called as such:
 * Trie obj = new Trie();
 * obj.insert(word);
 * boolean param_2 = obj.search(word);
 * boolean param_3 = obj.startsWith(prefix);
 */