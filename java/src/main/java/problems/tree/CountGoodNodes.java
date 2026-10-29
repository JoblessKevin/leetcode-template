package problems.tree;

public class CountGoodNodes {
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int val) {
            this.val = val;
        }
    }

    public int goodNodes(TreeNode root) {
        return dfs(root, Integer.MIN_VALUE);
    }

    private int dfs(TreeNode node, int maxVal) {
        if (node == null)
            return 0;

        int result = (node.val >= maxVal) ? 1 : 0;
        maxVal = Math.max(maxVal, node.val);

        result += dfs(node.left, maxVal);
        result += dfs(node.right, maxVal);

        return result;
    }

    public static void main(String[] args) {
        CountGoodNodes solution = new CountGoodNodes();
        System.out.println("=== 測試開始: Count Good Nodes ===\n");

        // ==========================================
        // Test Case 1: 題目標準範例
        // ==========================================
        // @formatter:off
        //       3
        //     /   \
        //    1     4
        //   /     / \
        //  3     1   5
        
        // 好節點分析：
        // 3 (Root) -> Good (3 >= min)
        // 3->1     -> Bad  (1 < 3)
        // 3->1->3  -> Good (3 >= 3)
        // 3->4     -> Good (4 >= 3)
        // 3->4->1  -> Bad  (1 < 4)
        // 3->4->5  -> Good (5 >= 4)
        // 總共: 4 個
        // @formatter:on

        TreeNode root1 = new TreeNode(3);
        root1.left = new TreeNode(1);
        root1.right = new TreeNode(4);
        root1.left.left = new TreeNode(3);
        root1.right.left = new TreeNode(1);
        root1.right.right = new TreeNode(5);

        System.out.println("Case 1: 標準範例");
        System.out.println("預期結果: 4");
        System.out.println("實際結果: " + solution.goodNodes(root1));
        System.out.println("--------------------------------------------------");

        // ==========================================
        // Test Case 2: 數值遞增 (全部都是好節點)
        // ==========================================
        // @formatter:off
        //      3
        //     /
        //    3
        //   /
        //  4
        //   \
        //    5
        // @formatter:on

        TreeNode root2 = new TreeNode(3);
        root2.left = new TreeNode(3);
        root2.left.left = new TreeNode(4);
        root2.left.left.right = new TreeNode(5);

        System.out.println("Case 2: 遞增路徑");
        System.out.println("預期結果: 4 (每個節點都 >= 前面路徑最大值)");
        System.out.println("實際結果: " + solution.goodNodes(root2));
        System.out.println("--------------------------------------------------");

        // ==========================================
        // Test Case 3: 包含負數
        // ==========================================
        // @formatter:off
        //       -1
        //      /  \
        //    -5   -2
        // @formatter:on
        // Root(-1) 是 Good
        // 左邊(-5) < -1 -> Bad
        // 右邊(-2) < -1 -> Bad
        // 只有 Root 是好節點

        TreeNode root3 = new TreeNode(-1);
        root3.left = new TreeNode(-5);
        root3.right = new TreeNode(-2);

        System.out.println("Case 3: 負數測試");
        System.out.println("預期結果: 1");
        System.out.println("實際結果: " + solution.goodNodes(root3));
    }
}