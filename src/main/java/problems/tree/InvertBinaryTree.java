package problems.tree;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

public class InvertBinaryTree {
    public class TreeNode {
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

    public TreeNode invertTree_BFS(TreeNode root) {
        if (root == null) return null;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            TreeNode curr = queue.poll();
            TreeNode temp = curr.left;
            curr.left = curr.right;
            curr.right = temp;
            if (curr.left != null) queue.offer(curr.left);
            if (curr.right != null) queue.offer(curr.right);
        }

        return root;
    }

    public TreeNode invertTree_recursive(TreeNode root) {
        if (root == null) return null;
        TreeNode temp = root.left;
        root.left = root.right;
        root.right = temp;

        invertTree_recursive(root.left);
        invertTree_recursive(root.right);

        return root;
    }

    public TreeNode invertTree_DFS(TreeNode root) {
        if (root == null) return null;
        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.push(root);
        while(!stack.isEmpty()) {
            TreeNode curr = stack.pop();
            TreeNode temp = curr.left;
            curr.left = curr.right;
            curr.right = temp;

            if (curr.left != null) stack.push(curr.left);
            if (curr.right != null) stack.push(curr.right);
        }

        return root;
    }

    // ==========================================
    // Test case
    // ==========================================
    public static void main(String[] args) {
        // 因為 TreeNode 是非靜態內部類別，需要先建立外部類別實例
        InvertBinaryTree solution = new InvertBinaryTree();

        // 建立測試資料: [4, 2, 7, 1, 3, 6, 9]
        //      4
        //    /   \
        //   2     7
        //  / \   / \
        // 1   3 6   9
        InvertBinaryTree.TreeNode root = solution.new TreeNode(4);
        root.left = solution.new TreeNode(2);
        root.right = solution.new TreeNode(7);
        root.left.left = solution.new TreeNode(1);
        root.left.right = solution.new TreeNode(3);
        root.right.left = solution.new TreeNode(6);
        root.right.right = solution.new TreeNode(9);

        System.out.println("原始二元樹 (Level Order):");
        printTree(root);

        System.out.println("\n--------------------------");

        // 測試方法 (這裡你可以切換測試 BFS, DFS 或 recursive)
        solution.invertTree_BFS(root);
        // solution.invertTree_recursive(root);
        // solution.invertTree_DFS(root);

        System.out.println("翻轉後二元樹 (Level Order):");
        // 預期結果: [4, 7, 2, 9, 6, 3, 1]
        printTree(root);
    }

    // 輔助方法：層序遍歷列印 (方便觀察樹的結構)
    public static void printTree(TreeNode root) {
        if (root == null) return;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            System.out.print(node.val + " ");
            if (node.left != null) queue.offer(node.left);
            if (node.right != null) queue.offer(node.right);
        }
        System.out.println();
    }
}
