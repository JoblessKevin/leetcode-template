package problems.trie;

class WordDictionary {
    private static class TrieNode {
        private TrieNode[] children;
        private boolean isEndOfWord;

        public TrieNode() {
            this.children = new TrieNode[26];
            this.isEndOfWord = false;
        }
    }

    private TrieNode root;

    public WordDictionary() {
        root = new TrieNode();
    }

    public void addWord(String word) {
        TrieNode curr = root;
        for (char ch : word.toCharArray()) {
            if (curr.children[ch - 'a'] == null) {
                curr.children[ch - 'a'] = new TrieNode();
            }

            curr = curr.children[ch - 'a'];
        }

        curr.isEndOfWord = true;
    }

    public boolean search(String word) {
        return dfs(word, 0, root);
    }

    private boolean dfs(String word, int j, TrieNode node) {
        TrieNode curr = node;

        for (int i = j; i < word.length(); i++) {
            char ch = word.charAt(i);
            if (ch == '.') {
                for (TrieNode child : curr.children) {
                    if (child != null && dfs(word, i + 1, child)) {
                        return true;
                    }
                }

                return false;
            } else {
                if (curr.children[ch - 'a'] == null) {
                    return false;
                }

                curr = curr.children[ch - 'a'];
            }
        }

        return curr.isEndOfWord;
    }

    public static void main(String[] args) {
        WordDictionary wordDictionary = new WordDictionary();

        // 1. 加入單字測試
        System.out.println("--- 1. Adding Words ---");
        wordDictionary.addWord("bad");
        wordDictionary.addWord("dad");
        wordDictionary.addWord("mad");
        System.out.println("Added: bad, dad, mad");

        // 2. 基本搜尋測試 (精確匹配)
        System.out.println("\n--- 2. Exact Search ---");
        System.out.println("Search 'pad': " + wordDictionary.search("pad")); // Expected: false
        System.out.println("Search 'bad': " + wordDictionary.search("bad")); // Expected: true

        // 3. 通配符 '.' 測試
        System.out.println("\n--- 3. Wildcard Search (.) ---");
        System.out.println("Search '.ad': " + wordDictionary.search(".ad")); // Expected: true (matches bad, dad, mad)
        System.out.println("Search 'b..': " + wordDictionary.search("b..")); // Expected: true (matches bad)
        System.out.println("Search '...': " + wordDictionary.search("...")); // Expected: true (matches all length 3)
        System.out.println("Search '..d': " + wordDictionary.search("..d")); // Expected: true

        // 4. 混合測試與長度不符測試
        System.out.println("\n--- 4. Edge Cases ---");
        System.out.println("Search '.': " + wordDictionary.search(".")); // Expected: false (no word of length 1)
        System.out.println("Search 'bad.': " + wordDictionary.search("bad.")); // Expected: false (length 4)

        // 5. 複雜路徑測試
        wordDictionary.addWord("apple");
        System.out.println("\n--- 5. Complex Path ---");
        System.out.println("Search 'app.e': " + wordDictionary.search("app.e")); // Expected: true
        System.out.println("Search '.....': " + wordDictionary.search(".....")); // Expected: true (matches apple)
    }
}

/**
 * Your WordDictionary object will be instantiated and called as such:
 * WordDictionary obj = new WordDictionary();
 * obj.addWord(word);
 * boolean param_2 = obj.search(word);
 */
