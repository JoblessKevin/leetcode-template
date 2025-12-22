public class Diameter {
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {};
        TreeNode(int val) {this.val = val;}
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    private int diameter = 0;
    
    public int diameterOfBinaryTree(TreeNode root) {
        depth(root);
        return diameter;    
    }

    private int depth(TreeNode node) {
        if (node == null) return 0;

        int left = depth(node.left);
        int right = depth(node.right);

        diameter = Math.max(diameter, left + right);

        return Math.max(left, right) + 1;
    }

    // ==========================================
    //  Test Cases
    // ==========================================
    public static void main(String[] args) {
        System.out.println("=== 二元樹直徑測試 ===");

        // -------------------------------------------------
        // Test Case 1: 標準情況 (經過根節點)
        //       1
        //      / \
        //     2   3
        //    / \
        //   4   5
        // 路徑: 4->2->1->3 或 5->2->1->3 (長度 3)
        // -------------------------------------------------
        TreeNode root1 = new TreeNode(1);
        root1.left = new TreeNode(2);
        root1.right = new TreeNode(3);
        root1.left.left = new TreeNode(4);
        root1.left.right = new TreeNode(5);

        Diameter sol1 = new Diameter();
        int result1 = sol1.diameterOfBinaryTree(root1);
        System.out.println("Case 1 (標準): " + result1 + " (預期: 3)");


        // -------------------------------------------------
        // Test Case 2: 直徑不經過根節點 (都在左子樹)
        //        1
        //       /
        //      2   <-- 轉折點 (Anchor)
        //     / \
        //    3   4
        //   /     \
        //  5       6
        // 路徑: 5->3->2->4->6 (長度 4)
        // -------------------------------------------------
        TreeNode root2 = new TreeNode(1);
        root2.left = new TreeNode(2);
        root2.left.left = new TreeNode(3);
        root2.left.right = new TreeNode(4);
        root2.left.left.left = new TreeNode(5);   // 左邊深處
        root2.left.right.right = new TreeNode(6); // 右邊深處

        Diameter sol2 = new Diameter();
        int result2 = sol2.diameterOfBinaryTree(root2);
        System.out.println("Case 2 (不經Root): " + result2 + " (預期: 4)");


        // -------------------------------------------------
        // Test Case 3: 單一節點
        //       1
        // 直徑: 0 (沒有邊)
        // -------------------------------------------------
        TreeNode root3 = new TreeNode(1);

        Diameter sol3 = new Diameter();
        int result3 = sol3.diameterOfBinaryTree(root3);
        System.out.println("Case 3 (單節點): " + result3 + " (預期: 0)");
    }
}
