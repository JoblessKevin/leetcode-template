package problems.tree;

import java.util.ArrayDeque;
import java.util.Deque;

public class IsValidBST {
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int val) {
            this.val = val;
        }
    }

    public boolean isValidBST(TreeNode root) {
        return isValidBST(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    private boolean isValidBST(TreeNode node, long minVal, long maxVal) {
        if (node == null)
            return true;

        if (node.val <= minVal || node.val >= maxVal)
            return false;

        return isValidBST(node.left, minVal, node.val) && isValidBST(node.right, node.val, maxVal);
    }

    public boolean isValidBST_iterative(TreeNode root) {
        if (root == null)
            return true;

        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode curr = root;
        TreeNode prev = null;

        while (curr != null || !stack.isEmpty()) {
            while (curr != null) {
                stack.push(curr);
                curr = curr.left;
            }

            curr = stack.pop();

            if (prev != null && curr.val <= prev.val) {
                return false;
            }

            prev = curr;
            curr = curr.right;
        }

        return true;
    }

    public static void main(String[] args) {
        IsValidBST solution = new IsValidBST();
        System.out.println("=== 測試開始: Validate Binary Search Tree ===\n");

        // ==========================================
        // Test Case 1: 標準範例 (LeetCode Example)
        // ==========================================
        // @formatter:off
        //      2
        //     / \
        //    1   3
        // @formatter:on

        TreeNode root1 = new TreeNode(2);
        root1.left = new TreeNode(1);
        root1.right = new TreeNode(3);

        System.out.println("Case 1: 標準範例");
        System.out.println("預期結果: true");
        System.out.println("遞迴版結果: " + solution.isValidBST(root1));
        System.out.println("迭代版結果: " + solution.isValidBST_iterative(root1));
        System.out.println("--------------------------------------------------");

        // ==========================================
        // Test Case 2: 不是 BST (違反 BST 定義)
        // ==========================================
        // @formatter:off
        //      5
        //     / \
        //    1   4
        //       / \
        //      3   6
        // @formatter:on
        // 4->3 < 4 -> Bad (但 3 < 5 才是這題的關鍵錯誤點)

        TreeNode root2 = new TreeNode(5);
        root2.left = new TreeNode(1);
        root2.right = new TreeNode(4);
        root2.right.left = new TreeNode(3);
        root2.right.right = new TreeNode(6);

        System.out.println("Case 2: 不是 BST (陷阱題)");
        System.out.println("預期結果: false");
        System.out.println("遞迴版結果: " + solution.isValidBST(root2));
        System.out.println("迭代版結果: " + solution.isValidBST_iterative(root2));
        System.out.println("--------------------------------------------------");

        // ==========================================
        // Test Case 3: 空樹 (Empty Tree)
        // ==========================================
        // 輸入: null
        // 預期: true (空樹是 BST)

        TreeNode root3 = null;

        System.out.println("Case 3: 空樹 (null)");
        System.out.println("預期結果: true");
        System.out.println("遞迴版結果: " + solution.isValidBST(root3));
        System.out.println("迭代版結果: " + solution.isValidBST_iterative(root3));
    }
}
