package templates.segmentTree;

/**
 * @formatter:off
 * Fenwick Tree (Binary Indexed Tree) 是一種用於高效計算前綴和的資料結構，特別適合於頻繁更新和查詢的情況。
 * 它的核心思想是利用二進位表示法來快速定位需要更新或查詢的區間，從而達到 O(log n) 的時間複雜度。
 * 是線段樹（Segment Tree）的「極簡版」。
 * @formatter:on
 */
public class FenwickTree {
    private int[] tree;

    // 初始化：建立一個長度為 n 的樹狀陣列
    public FenwickTree(int n) {
        // 從 1 開始編號，所以長度開 n + 1
        this.tree = new int[n + 1];
    }

    // 1. add(index, delta)：在某個位置加上一個數值 (單點更新)
    // 時間複雜度：O(log n)
    public void add(int index, int delta) {
        // 因為是單點更新，所以要往上更新所有覆蓋到這個點的父節點
        for (; index < tree.length; index += index & -index) {
            tree[index] += delta;
        }
    }

    // 2. sumPrefix(index)：取得從 1 到 index 的前綴和
    // 時間複雜度：O(log n)
    public int sumPrefix(int index) {
        int sum = 0;
        // 不斷跳格子，累積所有負責區間的總和
        for (; index > 0; index -= index & -index) {
            sum += tree[index];
        }
        return sum;
    }

    // 3. rangeSum(left, right)：取得 [left, right] 區間的和
    // 時間複雜度：O(log n)
    public int rangeSum(int left, int right) {
        // 利用前綴和相減：[1, right] - [1, left-1]
        return sumPrefix(right) - sumPrefix(left - 1);
    }
}
