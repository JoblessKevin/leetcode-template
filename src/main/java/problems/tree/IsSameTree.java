package problems.tree;

public class IsSameTree {
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        
        TreeNode() {}
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null) return true;
        if (p == null || q == null) return false;
        if (p.val != q.val) return false;

        return isSameTree(p.left, q.left) &&
                isSameTree(p.right, q.right);
    }

    public static void main(String[] args) {
        IsSameTree solution = new IsSameTree();

        System.out.println("=== 開始測試 Same Tree ===");

        // --- 測試案例 1: 兩棵樹完全相同 ---
        // Tree P:   1      Tree Q:   1
        //          / \              / \
        //         2   3            2   3
        TreeNode p1 = new TreeNode(1, new TreeNode(2), new TreeNode(3));
        TreeNode q1 = new TreeNode(1, new TreeNode(2), new TreeNode(3));
        
        boolean result1 = solution.isSameTree(p1, q1);
        printResult(1, true, result1);


        // --- 測試案例 2: 結構不同 ---
        // Tree P:   1      Tree Q:   1
        //          /                  \
        //         2                    2
        TreeNode p2 = new TreeNode(1);
        p2.left = new TreeNode(2); // 2 在左邊
        
        TreeNode q2 = new TreeNode(1);
        q2.right = new TreeNode(2); // 2 在右邊

        boolean result2 = solution.isSameTree(p2, q2);
        printResult(2, false, result2);


        // --- 測試案例 3: 結構相同但數值不同 ---
        // Tree P:   1      Tree Q:   1
        //          / \              / \
        //         2   1            1   2
        TreeNode p3 = new TreeNode(1, new TreeNode(2), new TreeNode(1));
        TreeNode q3 = new TreeNode(1, new TreeNode(1), new TreeNode(2));

        boolean result3 = solution.isSameTree(p3, q3);
        printResult(3, false, result3);
        
        
        // --- 測試案例 4: 兩棵空樹 ---
        boolean result4 = solution.isSameTree(null, null);
        printResult(4, true, result4);
    }

    // 輔助方法：用來印出漂亮的測試結果
    private static void printResult(int testId, boolean expected, boolean actual) {
        String status = (expected == actual) ? "PASS ✅" : "FAIL ❌";
        System.out.printf("Test Case %d: Expected [%b], Actual [%b] -> %s%n", 
                          testId, expected, actual, status);
    }
}
