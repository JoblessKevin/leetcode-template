package templates.dp.tree;

/**
 * 樹形 DP (Tree DP) 模板
 * -------------------------------------------------------
 * 核心思想：利用 DFS 後序遍歷 (Post-order)，先拿到左右子樹的狀態，再計算當前節點。
 * 狀態定義：通常是一個陣列 int[]，包含 [選當前的收益, 不選當前的收益]。
 * 適用場景：最大獨立集 (Maximum Independent Set)、打家劫舍 III (House Robber III)、樹的直徑。
 * 複雜度：O(N) (每個節點只遍歷一次)
 */
public class TreeDp {

    // 定義二元樹節點
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    /**
     * 入口方法
     */
    public int solve(TreeNode root) {
        int[] result = dfs(root);
        // 最後取根節點「選」或「不選」的最大值
        return Math.max(result[0], result[1]);
    }

    /**
     * 核心 DFS 遞迴函數
     * @param node 當前節點
     * @return int[] {選當前節點的最大值, 不選當前節點的最大值}
     */
    private int[] dfs(TreeNode node) {
        // Base Case: 空節點回傳 {0, 0}
        if (node == null) return new int[]{0, 0};

        // 1. Post-order Traversal (先遞迴算子樹)
        // 這是 Tree DP 的靈魂：Bottom-Up 由下往上回傳資訊
        int[] left = dfs(node.left);
        int[] right = dfs(node.right);

        // 2. 狀態轉移 (根據子樹結果計算當前)
        
        // 情況 A: 選擇當前節點 (Select)
        // 限制：如果選了父節點，子節點一定不能選
        // 公式：當前值 + 左子樹不選 + 右子樹不選
        int selected = node.val + left[1] + right[1];

        // 情況 B: 不選當前節點 (Not Select)
        // 自由：如果沒選父節點，子節點「可以選」也「可以不選」，我們取最大的
        // 公式：max(左選, 左不選) + max(右選, 右不選)
        int notSelected = Math.max(left[0], left[1]) + Math.max(right[0], right[1]);

        return new int[]{selected, notSelected};
    }

    // ==========================================
    // Test Cases
    // ==========================================
    public static void main(String[] args) {
        TreeDp solver = new TreeDp();

        // 測試案例: House Robber III (LeetCode 337)
        //       3
        //      / \
        //     2   3
        //      \   \
        //       3   1
        // 最佳解: 3 (root) + 3 (left->right) + 1 (right->right) = 7
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.right = new TreeNode(3);
        root.right.right = new TreeNode(1);

        System.out.println("Max Value: " + solver.solve(root)); // Expected: 7
        
        // 測試案例 2: 
        //       3
        //      / \
        //     4   5
        //    / \   \
        //   1   3   1
        // 最佳解: 4 + 5 + ... (不選 root 3) => 4 + 5 = 9
        TreeNode root2 = new TreeNode(3);
        root2.left = new TreeNode(4);
        root2.right = new TreeNode(5);
        root2.left.left = new TreeNode(1);
        root2.left.right = new TreeNode(3);
        root2.right.right = new TreeNode(1);
        
        System.out.println("Max Value 2: " + solver.solve(root2)); // Expected: 9
    }
}