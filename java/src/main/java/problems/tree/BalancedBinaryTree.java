package problems.tree;

public class BalancedBinaryTree {
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {};
        TreeNode(int val) {this.val = val;}
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    private int isBalanced = 1; // 1: 平衡, 0: 不平衡

    public boolean isBalanced(TreeNode root) {
        isBalanced = 1;
        
        checkBalance(root);
        return isBalanced == 1;
    }

    private int checkBalance(TreeNode node) {
        if (node == null || isBalanced == 0) return 0;

        int leftHeight = checkBalance(node.left);
        int rightHeight = checkBalance(node.right);

        if (isBalanced == 0) return 0;

        if (Math.abs(leftHeight - rightHeight) > 1) {
            isBalanced = 0;
            return 0;
        }

        return Math.max(leftHeight, rightHeight) + 1;
    }
    
    // ==========================================
    // 測試區 (Test Harness)
    // ==========================================
    public static void main(String[] args) {
        BalancedBinaryTree solver = new BalancedBinaryTree();

        System.out.println("--- 測試開始 ---");

        // 測資 1: 平衡樹 (LeetCode 範例)
        //      3
        //     / \
        //    9  20
        //      /  \
        //     15   7
        TreeNode root1 = new TreeNode(3);
        root1.left = new TreeNode(9);
        root1.right = new TreeNode(20, new TreeNode(15), new TreeNode(7));
        
        boolean res1 = solver.isBalanced(root1);
        System.out.println("測資 1 (預期 true): " + res1);


        // 測資 2: 不平衡樹 (左左子樹太深)
        //       1
        //      / \
        //     2   2
        //    / \
        //   3   3
        //  / \
        // 4   4
        TreeNode root2 = new TreeNode(1);
        root2.right = new TreeNode(2);
        TreeNode node2 = new TreeNode(2);
        root2.left = node2;
        TreeNode node3 = new TreeNode(3);
        node2.left = node3;
        node2.right = new TreeNode(3);
        node3.left = new TreeNode(4);
        node3.right = new TreeNode(4);

        boolean res2 = solver.isBalanced(root2);
        System.out.println("測資 2 (預期 false): " + res2);


        // 測資 3: 空樹 (根據定義，空樹是平衡的)
        boolean res3 = solver.isBalanced(null);
        System.out.println("測資 3 (預期 true): " + res3);
        
        
        // 測資 4: 重複使用 solver 檢查 (測試狀態重置是否有修好)
        // 再次檢查 root1，如果沒有重置 flag，這裡可能會錯誤地變成 false (因為 root2 是 false)
        boolean res4 = solver.isBalanced(root1);
        System.out.println("測資 4 (重測 root1, 預期 true): " + res4);
        
        if(res1 && !res2 && res3 && res4) {
             System.out.println("--- 所有測試通過 ---");
        } else {
             System.out.println("--- 測試失敗 ---");
        }
    }
}
