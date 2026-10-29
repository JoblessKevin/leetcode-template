package problems.tree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class BinaryTreeInorderTraversal {

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {}

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    public static class DFS {
        public List<Integer> inorderTraversal(TreeNode root) {
            List<Integer> result = new ArrayList<>();
            Deque<TreeNode> stack = new ArrayDeque<>();

            TreeNode curr = root;

            while (curr != null || !stack.isEmpty()) {
                while (curr != null) {
                    stack.push(curr);
                    curr = curr.left;
                }

                curr = stack.pop();
                result.add(curr.val);

                curr = curr.right;
            }

            return result;
        }
    }

    public static class Recursive {
        public List<Integer> inorderTraversal(TreeNode root) {
            List<Integer> result = new ArrayList<>();
            inorderHelper(root, result);
            return result;
        }

        private void inorderHelper(TreeNode node, List<Integer> result) {
            if (node == null) {
                return;
            }

            inorderHelper(node.left, result);
            result.add(node.val);
            inorderHelper(node.right, result);
        }
    }

    public static void main(String[] args) {
        BinaryTreeInorderTraversal.DFS dfsSolution = new BinaryTreeInorderTraversal.DFS();
        BinaryTreeInorderTraversal.Recursive recursiveSolution =
                                        new BinaryTreeInorderTraversal.Recursive();

        System.out.println("=== 測試開始: Binary Tree Inorder Traversal ===\n");

        // ==========================================
        TreeNode root = new TreeNode(1);
        root.right = new TreeNode(2);
        root.right.left = new TreeNode(3);

        System.out.println("DFS Solution: " + dfsSolution.inorderTraversal(root));
        System.out.println("Recursive Solution: " + recursiveSolution.inorderTraversal(root));
    }
}
