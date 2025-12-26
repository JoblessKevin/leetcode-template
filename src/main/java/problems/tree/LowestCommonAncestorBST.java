package problems.tree;

public class LowestCommonAncestorBST {
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

    public TreeNode lowestCommonAncestorRecursive(TreeNode root, TreeNode p, TreeNode q) {
        if (root.val > p.val && root.val > q.val) {
            return lowestCommonAncestorRecursive(root.left, p, q);
        }

        if (root.val < p.val && root.val < q.val) {
            return lowestCommonAncestorRecursive(root.right, p, q);
        }

        return root;
    }

    public TreeNode lowestCommonAncestorIterative(TreeNode root, TreeNode p, TreeNode q) {
        while (root != null) {
            if (root.val > p.val && root.val > q.val) {
                root = root.left;
            } else if (root.val < p.val && root.val < q.val) {
                root = root.right;
            } else {
                return root;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        LowestCommonAncestorBST solution = new LowestCommonAncestorBST();

        // @formatter:off
        //      6
        //    /   \
        //   2     8
        //  / \   / \
        // 0   4 7   9
        // @formatter:on

        // 建立節點
        TreeNode root = new TreeNode(6);
        TreeNode node2 = new TreeNode(2);
        TreeNode node8 = new TreeNode(8);
        TreeNode node0 = new TreeNode(0);
        TreeNode node4 = new TreeNode(4);
        TreeNode node7 = new TreeNode(7);
        TreeNode node9 = new TreeNode(9);
        TreeNode node3 = new TreeNode(3);
        TreeNode node5 = new TreeNode(5);

        // 建立連結 (Parent -> Child)
        root.left = node2;
        root.right = node8;

        node2.left = node0;
        node2.right = node4;

        node8.left = node7;
        node8.right = node9;

        node4.left = node3;
        node4.right = node5;

        System.out.println("=== BST 建構完成，開始測試邏輯 ===");

        // ==========================================
        // 2. 測試案例 A: 分岔點是 Root 的情況
        // ==========================================
        // p = 2, q = 8 -> 預期 LCA 是 6
        System.out.println("\n[Case A] 尋找 2 和 8 的 LCA (預期: 6)");

        TreeNode resultIterativeA = solution.lowestCommonAncestorIterative(root, node2, node8);
        System.out.println("迭代版結果: " + (resultIterativeA != null ? resultIterativeA.val : "null"));

        TreeNode resultRecursiveA = solution.lowestCommonAncestorRecursive(root, node2, node8);
        System.out.println("遞迴版結果: " + (resultRecursiveA != null ? resultRecursiveA.val : "null"));

        // ==========================================
        // 3. 測試案例 B: 其中一個節點是另一個的祖先
        // ==========================================
        // p = 2, q = 4 -> 預期 LCA 是 2 (因為 2 是 4 的祖先)
        System.out.println("\n[Case B] 尋找 2 和 4 的 LCA (預期: 2)");

        TreeNode resultIterativeB = solution.lowestCommonAncestorIterative(root, node2, node4);
        System.out.println("迭代版結果: " + (resultIterativeB != null ? resultIterativeB.val : "null"));

        TreeNode resultRecursiveB = solution.lowestCommonAncestorRecursive(root, node2, node4);
        System.out.println("遞迴版結果: " + (resultRecursiveB != null ? resultRecursiveB.val : "null"));

        // ==========================================
        // 4. 測試案例 C: 兩者都在左子樹深處
        // ==========================================
        // p = 3, q = 5 -> 預期 LCA 是 4
        System.out.println("\n[Case C] 尋找 3 和 5 的 LCA (預期: 4)");

        TreeNode resultIterativeC = solution.lowestCommonAncestorIterative(root, node3, node5);
        System.out.println("迭代版結果: " + (resultIterativeC != null ? resultIterativeC.val : "null"));

        TreeNode resultRecursiveC = solution.lowestCommonAncestorRecursive(root, node3, node5);
        System.out.println("遞迴版結果: " + (resultRecursiveC != null ? resultRecursiveC.val : "null"));
    }
}
