package problems.tree;

import java.util.List;
import java.util.ArrayList;
import java.util.Deque;
import java.util.ArrayDeque;

public class BinaryTreePreorderTraversal {
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

    public static class Recursive {
        public List<Integer> preorderTraversal(TreeNode root) {
            List<Integer> result = new ArrayList<>();
            helper(root, result);

            return result;
        }

        public void helper(TreeNode node, List<Integer> result) {
            if (node == null)
                return;

            result.add(node.val);
            helper(node.left, result);
            helper(node.right, result);
        }
    }

    public static class DFS {
        public List<Integer> preorderTraversal(TreeNode root) {
            List<Integer> result = new ArrayList<>();
            if (root == null)
                return result;

            Deque<TreeNode> stack = new ArrayDeque<>();
            stack.push(root);

            while (!stack.isEmpty()) {
                TreeNode curr = stack.pop();
                result.add(curr.val);

                if (curr.right != null)
                    stack.push(curr.right);
                if (curr.left != null)
                    stack.push(curr.left);
            }

            return result;
        }
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.right = new TreeNode(2);
        root.right.left = new TreeNode(3);

        Recursive recursive = new Recursive();
        List<Integer> recursiveResult = recursive.preorderTraversal(root);
        System.out.println("Recursive Preorder Traversal: " + recursiveResult);

        DFS dfs = new DFS();
        List<Integer> dfsResult = dfs.preorderTraversal(root);
        System.out.println("DFS Preorder Traversal: " + dfsResult);
    }
}
