package problems.tree;

public class IsSubtree {
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

    public boolean isSubtree(TreeNode root, TreeNode subRoot) {
        if (root == null)
            return subRoot == null;

        if (isSameTree(root, subRoot))
            return true;

        return isSubtree(root.left, subRoot) ||
                isSubtree(root.right, subRoot);
    }

    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null)
            return true;
        if (p == null || q == null)
            return false;

        if (p.val != q.val)
            return false;

        return isSameTree(p.left, q.left) &&
                isSameTree(p.right, q.right);
    }

    public static void main(String[] args) {
        IsSubtree solution = new IsSubtree();
        System.out.println("====== Subtree of Another Tree 測試 ======");

        // [測試案例 1]
        TreeNode sub1 = new TreeNode(4, new TreeNode(1), new TreeNode(2));
        TreeNode root1 = new TreeNode(3, sub1, new TreeNode(5));

        System.out.println("\n[Case 1] 結構視覺化:");
        System.out.println("--- Root Tree ---");
        printTree(root1);
        System.out.println("--- Sub Tree ---");
        printTree(sub1);

        boolean res1 = solution.isSubtree(root1, sub1);
        printResult(1, true, res1, "標準包含案例");

        // [測試案例 2]
        TreeNode sub2 = new TreeNode(4, new TreeNode(1), new TreeNode(2));
        TreeNode root2 = new TreeNode(3,
                new TreeNode(4,
                        new TreeNode(1),
                        new TreeNode(2, new TreeNode(0), null)),
                new TreeNode(5));

        System.out.println("\n[Case 2] 結構視覺化:");
        System.out.println("--- Root Tree ---");
        printTree(root2); // 這裡你會很清楚看到 2 下面多了一個 0

        boolean res2 = solution.isSubtree(root2, sub2);
        printResult(2, false, res2, "陷阱題");
    }

    // 輔助顯示方法
    private static void printResult(int id, boolean expected, boolean actual, String note) {
        String status = (expected == actual) ? "PASS [OK]" : "FAIL [XX]";
        System.out.printf("Test Case %d [%s]:\n", id, note);
        System.out.printf("   Expected: %b, Actual: %b -> %s\n", expected, actual, status);
        System.out.println("------------------------------------------------");
    }

    // ==========================================
    // 新增：印出樹狀結構的 Helper Function
    // ==========================================
    public static void printTree(TreeNode root) {
        System.out.println();
        if (root == null) {
            System.out.println("(Empty Tree)");
            return;
        }
        printTreePrettyHelper(root, "", true);
        System.out.println();
    }

    private static void printTreePrettyHelper(TreeNode node, String prefix, boolean isTail) {
        if (node == null) {
            return;
        }

        printTreePrettyHelper(node.right, prefix + (isTail ? "    " : "│   "), false);

        System.out.println(prefix + (isTail ? "└── " : "┌── ") + node.val);

        printTreePrettyHelper(node.left, prefix + (isTail ? "    " : "│   "), true);
    }
}
