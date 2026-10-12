package problems.tree;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

public class InvertBinaryTree {
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {};

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    public static class BFS {
        public TreeNode invertTree(TreeNode root) {
            if (root == null)
                return null;
            // Queue<TreeNode> queue = new LinkedList<>();
            Queue<TreeNode> queue = new ArrayDeque<>();
            queue.offer(root);
            while (!queue.isEmpty()) {
                TreeNode curr = queue.poll();
                TreeNode temp = curr.left;
                curr.left = curr.right;
                curr.right = temp;
                if (curr.left != null)
                    queue.offer(curr.left);
                if (curr.right != null)
                    queue.offer(curr.right);
            }

            return root;
        }
    }

    public static class DFS {
        public TreeNode invertTree(TreeNode root) {
            if (root == null)
                return null;
            Deque<TreeNode> stack = new ArrayDeque<>();
            stack.push(root);
            while (!stack.isEmpty()) {
                TreeNode curr = stack.pop();
                TreeNode temp = curr.left;
                curr.left = curr.right;
                curr.right = temp;

                if (curr.left != null)
                    stack.push(curr.left);
                if (curr.right != null)
                    stack.push(curr.right);
            }

            return root;
        }
    }

    public static class Recursive {
        public TreeNode invertTree(TreeNode root) {
            if (root == null)
                return null;
            TreeNode temp = root.left;
            root.left = root.right;
            root.right = temp;

            invertTree(root.left);
            invertTree(root.right);

            return root;
        }
    }

    // ==========================================
    // Test case
    // ==========================================
    public static void main(String[] args) {
        TreeNode root = new TreeNode(4);
        root.left = new TreeNode(2);
        root.right = new TreeNode(7);
        root.left.left = new TreeNode(1);
        root.left.right = new TreeNode(3);
        root.right.left = new TreeNode(6);
        root.right.right = new TreeNode(9);

        BFS bfs = new BFS();
        TreeNode invertedBFS = bfs.invertTree(root);
        System.out.println("Inverted Binary Tree (BFS): " + invertedBFS.val);

        DFS dfs = new DFS();
        TreeNode invertedDFS = dfs.invertTree(root);
        System.out.println("Inverted Binary Tree (DFS): " + invertedDFS.val);

        Recursive recursive = new Recursive();
        TreeNode invertedRecursive = recursive.invertTree(root);
        System.out.println("Inverted Binary Tree (Recursive): " + invertedRecursive.val);
    }
}
