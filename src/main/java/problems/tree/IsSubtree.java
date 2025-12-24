package problems.tree;

public class IsSubtree {

    // 1. TreeNode 定義
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

    // 2. 解題核心邏輯
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

    // 3. Main 測試區
    public static void main(String[] args) {
        IsSubtree solution = new IsSubtree();
        System.out.println("====== Subtree of Another Tree 測試 ======");

        // [測試案例 1] 標準成功案例
        TreeNode sub1 = new TreeNode(4, new TreeNode(1), new TreeNode(2));
        TreeNode root1 = new TreeNode(3, sub1, new TreeNode(5));

        System.out.println("\n[Case 1] 結構視覺化:");
        System.out.println("--- Root Tree ---");
        printTree(root1);
        System.out.println("--- Sub Tree ---");
        printTree(sub1);

        boolean res1 = solution.isSubtree(root1, sub1);
        printResult(1, true, res1, "標準包含案例");

        // [測試案例 2] 陷阱題 (結構不乾淨)
        TreeNode sub2 = new TreeNode(4, new TreeNode(1), new TreeNode(2));
        TreeNode root2 = new TreeNode(3,
                new TreeNode(4,
                        new TreeNode(1),
                        new TreeNode(2, new TreeNode(0), null)), // 這裡多個 0
                new TreeNode(5));

        System.out.println("\n[Case 2] 結構視覺化:");
        System.out.println("--- Root Tree ---");
        printTree(root2);

        boolean res2 = solution.isSubtree(root2, sub2);
        printResult(2, false, res2, "陷阱題：雖然數值包含，但結構沒斷乾淨");

        // [測試案例 3] 重複數值
        TreeNode root3 = new TreeNode(1, new TreeNode(1), null);
        TreeNode sub3 = new TreeNode(1);

        System.out.println("\n[Case 3] 結構視覺化:");
        printTree(root3);

        boolean res3 = solution.isSubtree(root3, sub3);
        printResult(3, true, res3, "重複數值：Root 不匹配但 Root.left 匹配");
    }

    // 4. 輔助顯示方法 (純文字版)
    private static void printResult(int id, boolean expected, boolean actual, String note) {
        String status = (expected == actual) ? "PASS [OK]" : "FAIL [XX]";
        System.out.printf("Test Case %d [%s]:\n", id, note);
        System.out.printf("   Expected: %b, Actual: %b -> %s\n", expected, actual, status);
        System.out.println("------------------------------------------------");
    }

    // ==========================================
    // 5. 漂亮印出樹狀結構的 Helper Function (Trunk Based)
    // ==========================================
    static class Trunk {
        Trunk prev;
        String str;

        Trunk(Trunk prev, String str) {
            this.prev = prev;
            this.str = str;
        }
    }

    public static void printTree(TreeNode root) {
        System.out.println(); // 排版空行
        printTreeTrunk(root, null, false);
        System.out.println(); // 排版空行
    }

    public static void printTreeTrunk(TreeNode node, Trunk prev, boolean isLeft) {
        if (node == null) {
            return;
        }

        String prev_str = "    ";
        Trunk trunk = new Trunk(prev, prev_str);

        // 先印右子樹
        printTreeTrunk(node.right, trunk, true);

        // 設定當前層級符號
        if (prev == null) {
            trunk.str = "───"; // Root
        } else if (isLeft) {
            trunk.str = "┌──"; // 右小孩 (在上方)
            prev_str = "│   ";
        } else {
            trunk.str = "└──"; // 左小孩 (在下方)
            prev.str = prev_str;
        }

        showTrunks(trunk);
        System.out.println(" " + node.val);

        // 修正點 1：防止右小孩 (5) 修改 Root (3) 的縮排
        // 只有當「父節點不是 Root」時 (prev.prev != null)，才畫連接線
        if (prev != null && prev.prev != null) {
            prev.str = prev_str;
        }

        // 修正點 2：Root 往左邊長的時候，不需要直條，只需要空白
        trunk.str = (prev == null) ? "    " : "│   ";

        // 印左子樹
        printTreeTrunk(node.left, trunk, false);
    }

    private static void showTrunks(Trunk p) {
        if (p == null) {
            return;
        }
        showTrunks(p.prev);
        System.out.print(p.str);
    }
}