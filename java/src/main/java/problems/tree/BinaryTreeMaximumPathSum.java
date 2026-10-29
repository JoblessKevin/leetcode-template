package problems.tree;

public class BinaryTreeMaximumPathSum {
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int val) {
            this.val = val;
        }
    }

    private int globalMaxSum = Integer.MIN_VALUE;

    public int maxPathSum(TreeNode root) {
        maxGain(root);
        return globalMaxSum;
    }

    private int maxGain(TreeNode node) {
        if (node == null)
            return 0;

        int leftGain = Math.max(maxGain(node.left), 0);
        int rightGain = Math.max(maxGain(node.right), 0);
        int currentPathSum = node.val + leftGain + rightGain;

        globalMaxSum = Math.max(globalMaxSum, currentPathSum);

        return node.val + Math.max(leftGain, rightGain);
    }

    // ==========================================
    // Test cases
    // ==========================================
    public static void main(String[] args) {
        // @formatter:off
        // 測試案例 1: 基本正數樹
        //       1
        //      / \
        //     2   3
        // 路徑: 2 -> 1 -> 3, 和 = 6
        BinaryTreeMaximumPathSum solver1 = new BinaryTreeMaximumPathSum();
        TreeNode root1 = new TreeNode(1);
        root1.left = new TreeNode(2);
        root1.right = new TreeNode(3);
        System.out.println("Test Case 1 (Basic): " + solver1.maxPathSum(root1)); // Expected: 6


        // 測試案例 2: 含負數節點 (LeetCode 範例)
        //      -10
        //      /  \
        //     9   20
        //        /  \
        //       15   7
        // 路徑: 15 -> 20 -> 7, 和 = 42 (不經過 -10)
        BinaryTreeMaximumPathSum solver2 = new BinaryTreeMaximumPathSum();
        TreeNode root2 = new TreeNode(-10);
        root2.left = new TreeNode(9);
        root2.right = new TreeNode(20);
        root2.right.left = new TreeNode(15);
        root2.right.right = new TreeNode(7);
        System.out.println("Test Case 2 (With Negatives): " + solver2.maxPathSum(root2)); // Expected: 42


        // 測試案例 3: 全部都是負數
        //      -3
        // ==================
        // 路徑: -3 (單一節點本身就是最大路徑), 和 = -3
        BinaryTreeMaximumPathSum solver3 = new BinaryTreeMaximumPathSum();
        TreeNode root3 = new TreeNode(-3);
        System.out.println("Test Case 3 (Single Negative): " + solver3.maxPathSum(root3)); // Expected: -3


        // 測試案例 4: 需要捨棄子樹的情況
        //       2
        //      /
        //    -1
        // 路徑: 2 (因為左子樹 -1 會讓總和變小，邏輯中 Math.max(..., 0) 會將其視為 0)
        BinaryTreeMaximumPathSum solver4 = new BinaryTreeMaximumPathSum();
        TreeNode root4 = new TreeNode(2);
        root4.left = new TreeNode(-1);
        System.out.println("Test Case 4 (Discard Child): " + solver4.maxPathSum(root4)); // Expected: 2
        
        
        // 測試案例 5: 較複雜的結構
        //        5
        //       / \
        //      4   8
        //     /   / \
        //   11   13  4
        //   / \       \
        //  7   2       1
        // 路徑: 7 -> 11 -> 2 無法比 4 -> 8 -> 13 大嗎？
        // 實際最大路徑: 7 -> 11 -> 4 -> 5 -> 8 -> 13 (總和: 48)
        BinaryTreeMaximumPathSum solver5 = new BinaryTreeMaximumPathSum();
        TreeNode root5 = new TreeNode(5);
        root5.left = new TreeNode(4);
        root5.left.left = new TreeNode(11);
        root5.left.left.left = new TreeNode(7);
        root5.left.left.right = new TreeNode(2);
        
        root5.right = new TreeNode(8);
        root5.right.left = new TreeNode(13);
        root5.right.right = new TreeNode(4);
        root5.right.right.right = new TreeNode(1);
        
        System.out.println("Test Case 5 (Complex): " + solver5.maxPathSum(root5)); // Expected: 48
        // @formatter:on
    }
}
