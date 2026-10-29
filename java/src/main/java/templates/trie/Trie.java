package templates.trie;

/**
 * @formatter:off
 * Trie 模組修改指南 (針對不同 LeetCode 題型的變形策略)
 *
 * -------------------------------------------------------------------------------------
 * 1. 標準 Trie (LeetCode 208)
 * - 修改: 什麼都不用改。
 * - 結構: 使用標準模板 (`boolean isEnd`)。
 *
 * 2. Trie II (LeetCode 1804 - 計數/刪除)
 * - 修改: 將 `boolean isEnd` 改為 `int wordCount`。
 * - 新增: 加上 `int prefixCount` (記錄路徑經過次數)。
 * - 邏輯: `insert` 時沿途 `prefixCount++`，結尾 `wordCount++`；`erase` 則反之。
 *
 * 3. Word Search II (LeetCode 212 - 網格找字)
 * - 修改: 將 `boolean isEnd` 改為 `String word`。
 * - 目的: 結尾節點直接儲存完整的單字字串。
 * - 好處: DFS 找到結尾時，可以直接把 `node.word` 加入結果，省去維護 StringBuilder 的成本。
 *
 * 4. Map Sum Pairs (LeetCode 677 - 鍵值對)
 * - 修改: 將 `boolean isEnd` 改為 `int value`。
 * - 目的: 用來儲存該單字對應的分數或權重。
 *
 * 5. 通用字元 (字元範圍不只 a-z)
 * - 修改: 將 `TrieNode[] children` 改為 `HashMap<Character, TrieNode> children`。
 * - 目的: 處理 Unicode 或非連續字元，避免陣列過大浪費空間。
 * -------------------------------------------------------------------------------------
 * @formatter:on
 */
public class Trie {
    private static class TrieNode {
        TrieNode[] children = new TrieNode[26];
        boolean isEnd = false;
    }

    private TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    public void insert(String word) {
        TrieNode node = root;
        for (int i = 0; i < word.length(); i++) {
            int idx = word.charAt(i) - 'a';
            if (node.children[idx] == null) {
                node.children[idx] = new TrieNode();
            }
            node = node.children[idx];
        }
        node.isEnd = true;
    }

    public boolean search(String word) {
        TrieNode node = searchPrefix(word);
        return node != null && node.isEnd;
    }

    public boolean startsWith(String prefix) {
        TrieNode node = searchPrefix(prefix);
        return node != null;
    }

    private TrieNode searchPrefix(String s) {
        TrieNode node = root;
        for (int i = 0; i < s.length(); i++) {
            int idx = s.charAt(i) - 'a';
            if (node.children[idx] == null) {
                return null;
            }
            node = node.children[idx];
        }
        return node;
    }
}