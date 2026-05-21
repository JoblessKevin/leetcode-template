package templates.segmentTree;

/**
 * @formatter:off
 * 動態線段樹 (Dynamic Segment Tree) 是一種特殊的線段樹，適用於座標範圍非常大，但實際更新和查詢的點相對較少的情況。 
 * 傳統的線段樹需要預先建立一個固定大小的陣列，這在座標範圍很大時會導致巨大的空間浪費。
 * 動態線段樹則是根據需要動態創建節點，只有在更新或查詢涉及到的區間才會創建對應的節點 (Lazy Initialization)，從而節省空間。
 * @formatter:on
 */
public class DynamicSegmentTree {

    // 定義節點結構
    static class Node {
        int sum;
        Node left, right;
    }

    private Node root = new Node();
    private final int N = 1_000_000_000; // 假設座標範圍是 0 到 10 億

    // ✏️ 更新：如果子節點不存在，就現場 new 出來 (Dynamic Creation)
    public void update(Node node, int start, int end, int target, int val) {
        if (start == end) {
            node.sum = val;
            return;
        }

        int mid = start + (end - start) / 2;

        // 如果子節點為 null，這就是關鍵：現場才 new 出來！
        if (target <= mid) {
            if (node.left == null)
                node.left = new Node();
            update(node.left, start, mid, target, val);
        } else {
            if (node.right == null)
                node.right = new Node();
            update(node.right, mid + 1, end, target, val);
        }

        // 更新當前節點的和 (注意：要處理 null 的狀況)
        node.sum = (node.left != null ? node.left.sum : 0)
                                        + (node.right != null ? node.right.sum : 0);
    }

    // 🔍 查詢：如果子節點不存在，代表該區間總和為 0
    public int query(Node node, int start, int end, int L, int R) {
        if (node == null || start > R || end < L)
            return 0;
        if (L <= start && end <= R)
            return node.sum;

        int mid = start + (end - start) / 2;
        return query(node.left, start, mid, L, R) + query(node.right, mid + 1, end, L, R);
    }
}
