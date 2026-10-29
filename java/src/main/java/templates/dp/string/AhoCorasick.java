package templates.dp.string;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * AC 演算法 (Trie + KMP)
 */
public class AhoCorasick {
    class Node {
        Node[] children = new Node[26];
        Node fail; // 失敗指針 (類似 KMP 的 next/lps)
        List<String> output = new ArrayList<>(); // 匹配成功的字串清單
    }

    Node root = new Node();

    // 1. 標準 Trie 插入
    public void insert(String word) {
        Node curr = root;
        for (char c : word.toCharArray()) {
            int idx = c - 'a';
            if (curr.children[idx] == null)
                curr.children[idx] = new Node();
            curr = curr.children[idx];
        }
        curr.output.add(word);
    }

    // 2. 核心：構建 Fail 指針 (BFS)
    public void build() {
        Queue<Node> queue = new LinkedList<>();
        // 將第一層加入佇列
        for (Node child : root.children) {
            if (child != null) {
                child.fail = root;
                queue.add(child);
            }
        }
        while (!queue.isEmpty()) {
            Node u = queue.poll();
            for (int i = 0; i < 26; i++) {
                if (u.children[i] != null) {
                    // 關鍵轉移：v 的 fail = u 的 fail 往下一格
                    u.children[i].fail = (u.fail.children[i] != null) ? u.fail.children[i] : root;
                    queue.add(u.children[i]);
                } else {
                    // 為了查詢方便，直接把路徑補齊 (Trie 圖優化)
                    u.children[i] = u.fail.children[i];
                }
            }
        }
    }
}
