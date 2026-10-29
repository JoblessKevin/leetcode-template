package problems.tree;

import java.util.List;
import java.util.ArrayList;
import java.util.Deque;
import java.util.ArrayDeque;

public class BinaryTreePostorderTraversal {
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
        public List<Integer> postorderTraversal(TreeNode root) {
            List<Integer> result = new ArrayList<>();
            helper(root, result);

            return result;
        }

        public void helper(TreeNode node, List<Integer> result) {
            if (node == null)
                return;

            helper(node.left, result);
            helper(node.right, result);
            result.add(node.val);
        }
    }

    public static class DFS {
        public List<Integer> postorderTraversal(TreeNode root) {
            List<Integer> result = new ArrayList<>();
            if (root == null)
                return result;

            Deque<TreeNode> stack = new ArrayDeque<>();
            stack.push(root);

            while (!stack.isEmpty()) {
                TreeNode curr = stack.pop();
                result.add(0, curr.val);

                if (curr.left != null)
                    stack.push(curr.left);
                if (curr.right != null)
                    stack.push(curr.right);
            }

            return result;
        }
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.right = new TreeNode(2);
        root.right.left = new TreeNode(3);

        Recursive recursive = new Recursive();
        List<Integer> recursiveResult = recursive.postorderTraversal(root);
        System.out.println("Recursive Postorder Traversal: " + recursiveResult);

        DFS dfs = new DFS();
        List<Integer> dfsResult = dfs.postorderTraversal(root);
        System.out.println("DFS Postorder Traversal: " + dfsResult);
    }
}
