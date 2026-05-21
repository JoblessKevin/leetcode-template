package templates.segmentTree;

public class SegmentTree {
    private int[] tree;
    private int n;

    public SegmentTree(int[] nums) {
        if (nums.length > 0) {
            n = nums.length;
            // 業界公式：線段樹的陣列大小開 4 倍絕對夠用
            tree = new int[n * 4];
            build(nums, 0, 0, n - 1); // 從大老闆 (index 0) 開始建樹
        }
    }

    // 🏗️ 建立階層 (Build)
    private void build(int[] nums, int node, int start, int end) {
        // 終止條件：切到只剩一個員工，直接把業績填入
        if (start == end) {
            tree[node] = nums[start];
            return;
        }

        // 分治法：把區間對半切，交給左右兩個主管
        int mid = start + (end - start) / 2;
        int leftChild = 2 * node + 1;
        int rightChild = 2 * node + 2;

        build(nums, leftChild, start, mid);
        build(nums, rightChild, mid + 1, end);

        // 向上通報：主管的業績 = 左邊員工 + 右邊員工
        tree[node] = tree[leftChild] + tree[rightChild];
    }

    // ✏️ 更新業績 (Update)
    public void update(int node, int start, int end, int targetIndex, int val) {
        if (start == end) {
            tree[node] = val; // 找到那個基層員工，改寫業績
            return;
        }

        int mid = start + (end - start) / 2;
        int leftChild = 2 * node + 1;
        int rightChild = 2 * node + 2;

        // 判斷目標在哪個主管的管區，就派那個主管去查
        if (targetIndex <= mid) {
            update(leftChild, start, mid, targetIndex, val);
        } else {
            update(rightChild, mid + 1, end, targetIndex, val);
        }

        // 查完之後，沿路往上更新總和
        tree[node] = tree[leftChild] + tree[rightChild];
    }

    // 🔍 查詢區間 (Query)
    public int query(int node, int start, int end, int left, int right) {
        // 情況 A：查詢範圍完全覆蓋這個主管的管區 -> 直接拿數字，不往下問了！
        if (left <= start && right >= end) {
            return tree[node];
        }
        // 情況 B：完全沒交集 -> 回傳 0，不影響總和
        if (left > end || right < start) {
            return 0;
        }

        // 情況 C：部分交集 -> 兩邊的主管都去問問看
        int mid = start + (end - start) / 2;
        int leftSum = query(2 * node + 1, start, mid, left, right);
        int rightSum = query(2 * node + 2, mid + 1, end, left, right);

        return leftSum + rightSum;
    }
}
