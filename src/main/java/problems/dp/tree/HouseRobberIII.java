package problems.dp.tree;

public class HouseRobberIII {

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {}

        TreeNode(int val) {
            this.val = val;
        }
    }

    public int rob(TreeNode root) {
        // 拿到根節點回傳的陣列，裡面裝著「不偷根節點」和「偷根節點」的最大值
        int[] result = dfs(root);

        // 最終大對決：看哪個賺得多！
        return Math.max(result[0], result[1]);
    }

    // 這個 helper 方法會回傳一個長度為 2 的陣列：[不偷的最大值, 偷的最大值]
    private int[] dfs(TreeNode node) {
        // Base Case：如果走到盡頭 (空節點)，不管是偷或不偷，收益都是 0
        if (node == null) {
            return new int[] {0, 0};
        }

        // 1. 派小弟去左邊探路 (Post-order Traversal 後序遍歷)
        int[] left = dfs(node.left);
        // 2. 派小弟去右邊探路
        int[] right = dfs(node.right);

        // 3. 收集完情報，身為父節點的我，開始計算自己的兩個狀態：
        int[] res = new int[2];

        // 狀態 A：【我不偷自己】
        // 既然我不偷自己，那我的左右小孩「偷或不偷都可以」！
        // 身為貪心的小偷，我當然是看左小孩哪個方案賺、右小孩哪個方案賺，把它們加起來。
        res[0] = Math.max(left[0], left[1]) + Math.max(right[0], right[1]);

        // 狀態 B：【我要偷自己】
        // 既然我要偷自己 (node.val)，那我的左右小孩「絕對不能偷」！
        // 所以我只能拿左小孩「不偷」的收益 (left[0])，加上右小孩「不偷」的收益 (right[0])。
        res[1] = node.val + left[0] + right[0];

        // 4. 把我的計算結果，繼續往上呈報給我的父節點
        return res;
    }

    // @formatter:off
    // ==========================================
    // 測試環境 (Main Method)
    // ==========================================
    public static void main(String[] args) {
        HouseRobberIII solution = new HouseRobberIII();

        // --------------------------------------------------
        // 測試案例 1：經典範例 (偷爺爺和孫子)
        //       3
        //      / \
        //     2   3
        //      \   \
        //       3   1
        // 預期結果：3 + 3 + 1 = 7
        // --------------------------------------------------
        TreeNode root1 = new TreeNode(3);
        root1.left = new TreeNode(2);
        root1.right = new TreeNode(3);
        root1.left.right = new TreeNode(3);
        root1.right.right = new TreeNode(1);

        int result1 = solution.rob(root1);
        System.out.println("Test Case 1 (偷爺爺與孫子):");
        System.out.println("  你的答案: " + result1);
        System.out.println("  狀態: " + (result1 == 7 ? "正確" : "錯誤"));
        System.out.println("--------------------------------------------------");

        // --------------------------------------------------
        // 測試案例 2：經典範例 (偷爸爸們)
        //       3
        //      / \
        //     4   5
        //    / \   \
        //   1   3   1
        // 預期結果：4 + 5 = 9
        // --------------------------------------------------
        TreeNode root2 = new TreeNode(3);
        root2.left = new TreeNode(4);
        root2.right = new TreeNode(5);
        root2.left.left = new TreeNode(1);
        root2.left.right = new TreeNode(3);
        root2.right.right = new TreeNode(1);

        int result2 = solution.rob(root2);
        System.out.println("Test Case 2 (偷強大的爸爸們):");
        System.out.println("  你的答案: " + result2);
        System.out.println("  狀態: " + (result2 == 9 ? "正確" : "錯誤"));
        System.out.println("--------------------------------------------------");

        // --------------------------------------------------
        // 測試案例 3：邊界條件 (只有一間別墅)
        //       10
        // 預期結果：10
        // --------------------------------------------------
        TreeNode root3 = new TreeNode(10);
        int result3 = solution.rob(root3);
        System.out.println("Test Case 3 (只有一間別墅):");
        System.out.println("  你的答案: " + result3);
        System.out.println("  狀態: " + (result3 == 10 ? "正確" : "錯誤"));
        System.out.println("--------------------------------------------------");
        
        // --------------------------------------------------
        // 測試案例 4：邊界條件 (空社區)
        //       null
        // 預期結果：0
        // --------------------------------------------------
        TreeNode root4 = null;
        int result4 = solution.rob(root4);
        System.out.println("Test Case 4 (空社區):");
        System.out.println("  你的答案: " + result4);
        System.out.println("  狀態: " + (result4 == 0 ? "正確" : "錯誤"));
        System.out.println("--------------------------------------------------");
    }
    // @formatter:on
}
