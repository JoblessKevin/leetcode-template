package problems.backtracking;

import java.util.ArrayList;
import java.util.List;

public class WordSearchII {
    // 1. 特製的 TrieNode
    private static class TrieNode {
        TrieNode[] children = new TrieNode[26];
        String word; // 存單字本身，代替 isEndOfWord
    }

    private void insert(TrieNode root, String word) {
        TrieNode curr = root;
        for (char c : word.toCharArray()) {
            int index = c - 'a';
            if (curr.children[index] == null) {
                curr.children[index] = new TrieNode();
            }
            curr = curr.children[index];
        }
        curr.word = word;
    }

    public List<String> findWords(char[][] board, String[] words) {
        // 2. 建構 Trie
        TrieNode root = new TrieNode();
        for (String w : words) {
            insert(root, w);
        }

        List<String> result = new ArrayList<>();
        int m = board.length;
        int n = board[0].length;

        // 3. 遍歷網格每一個點作為起點
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                // 只有當 Trie 裡有這個開頭的字元時，才啟動 DFS
                if (root.children[board[i][j] - 'a'] != null) {
                    dfs(board, i, j, root, result);
                }
            }
        }
        return result;
    }

    private void dfs(char[][] board, int i, int j, TrieNode node, List<String> result) {
        char c = board[i][j];

        // 檢查 Trie 路徑是否存在
        // (因為我們是在上一層檢查過才進來的，或者遞迴前檢查，這裡再檢查一次比較保險)
        int index = c - 'a';
        if (node.children[index] == null) {
            return;
        }

        // 移動 Trie 指標到子節點
        TrieNode curr = node.children[index];

        // --- 關鍵優化：找到單字 ---
        if (curr.word != null) {
            result.add(curr.word);
            curr.word = null; // 【重要】去重技巧！設為 null 代表這個單字已經找過了，避免重複加入
        }

        // --- Backtracking 標準動作 ---
        board[i][j] = '#'; // 標記已訪問

        // 往四個方向走
        int[][] dirs = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };
        for (int[] d : dirs) {
            int newRow = i + d[0];
            int newCol = j + d[1];

            // 邊界檢查 + 是否訪問過
            if (newRow >= 0 && newRow < board.length &&
                    newCol >= 0 && newCol < board[0].length &&
                    board[newRow][newCol] != '#') {

                // 遞迴下去
                dfs(board, newRow, newCol, curr, result);
            }
        }

        board[i][j] = c; // 還原現場
    }
}
