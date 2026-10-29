package problems.tree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class BinaryTreeLevelOrderTraversal {
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();

        if (root == null)
            return result;

        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            List<Integer> currentLevel = new ArrayList<>();
            for (int i = 0; i < levelSize; i++) {
                TreeNode currentNode = queue.poll();
                currentLevel.add(currentNode.val);

                if (currentNode.left != null)
                    queue.offer(currentNode.left);
                if (currentNode.right != null)
                    queue.offer(currentNode.right);
            }

            result.add(currentLevel);
        }

        return result;
    }

    public static void main(String[] args) {
        BinaryTreeLevelOrderTraversal solution = new BinaryTreeLevelOrderTraversal();

        System.out.println("=== 測試開始: Binary Tree Level Order Traversal ===\n");

        // ==========================================
        // Test Case 1: 標準範例 (LeetCode Example)
        // ==========================================
        // @formatter:off
        //      3
        //    /   \
        //   9    20
        //       /  \
        //      15   7
        // @formatter:on

        TreeNode root1 = new TreeNode(3);
        root1.left = new TreeNode(9);
        root1.right = new TreeNode(20);
        root1.right.left = new TreeNode(15);
        root1.right.right = new TreeNode(7);

        System.out.println("Case 1: 標準範例");
        System.out.println("預期結果: [[3], [9, 20], [15, 7]]");
        List<List<Integer>> result1 = solution.levelOrder(root1);
        System.out.println("實際結果: " + result1);
        System.out.println("--------------------------------------------------");

        // ==========================================
        // Test Case 2: 空樹 (Empty Tree)
        // ==========================================
        // 輸入: null
        // 預期: [] (空列表)

        TreeNode root2 = null;

        System.out.println("Case 2: 空樹 (null)");
        System.out.println("預期結果: []");
        List<List<Integer>> result2 = solution.levelOrder(root2);
        System.out.println("實際結果: " + result2);
        // 注意：如果你沒有修正上一題提到的 return null 問題，這裡印出來會是 "null"
        System.out.println("--------------------------------------------------");

        // ==========================================
        // Test Case 3: 只有一個節點
        // ==========================================
        // 1

        TreeNode root3 = new TreeNode(1);

        System.out.println("Case 3: 單一節點");
        System.out.println("預期結果: [[1]]");
        List<List<Integer>> result3 = solution.levelOrder(root3);
        System.out.println("實際結果: " + result3);
        System.out.println("--------------------------------------------------");

        // ==========================================
        // Test Case 4: 不規則/歪斜樹 (Skewed Tree)
        // ==========================================
        // @formatter:off
        //      1
        //     /
        //    2
        //     \
        //      3
        //     /
        //    4
        // @formatter:on
        // 這種結構用來測試 Queue 是否能正確處理單邊小孩

        TreeNode root4 = new TreeNode(1);
        root4.left = new TreeNode(2);
        root4.left.right = new TreeNode(3);
        root4.left.right.left = new TreeNode(4);

        System.out.println("Case 4: 歪斜樹 (Z字型)");
        System.out.println("預期結果: [[1], [2], [3], [4]]");
        List<List<Integer>> result4 = solution.levelOrder(root4);
        System.out.println("實際結果: " + result4);
        System.out.println("--------------------------------------------------");
    }
}
