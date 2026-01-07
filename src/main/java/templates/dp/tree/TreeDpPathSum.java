package templates.dp.tree;

/**
 * 樹形 DP (Tree DP) 模板 - 路徑和型 (Path Sum)
 * -------------------------------------------------------
 * 核心思想：
 * 1. 遞迴函式 (DFS) 回傳的是「單邊」最大路徑 (給父節點用的)。
 * 2. 在遞迴過程中，同時計算「穿過當前節點」的拱形路徑 (左+根+右)，並更新全域最大值。
 * * 適用場景：二元樹最大路徑和 (LeetCode 124)、二元樹的直徑 (LeetCode 543)。
 * 複雜度：O(N)
 */
public class TreeDpPathSum {

    // 定義二元樹節點
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    // ★★★ Key 1: 全域變數，用於記錄遍歷過程中的最大值 ★★★
    private int globalMax = Integer.MIN_VALUE;

    /**
     * 入口方法
     */
    public int maxPathSum(TreeNode root) {
        globalMax = Integer.MIN_VALUE; // 重置
        dfs(root);
        return globalMax;
    }

    /**
     * 核心 DFS: 計算從當前節點「向下延伸」的最大路徑和
     * @return 給父節點用的最大貢獻值 (只能選左邊或右邊一條路)
     */
    private int dfs(TreeNode node) {
        if (node == null) return 0;

        // 1. Post-order: 先算左右子樹
        // ★★★ Key 2: 如果子樹路徑和是負數，不如不選 (取 0) ★★★
        int leftGain = Math.max(0, dfs(node.left));
        int rightGain = Math.max(0, dfs(node.right));

        // 2. 更新全域最大值 (Global Max Update)
        // 這是路徑的 "轉折點" (Turning Point)：左 + 根 + 右
        // 這條路徑不會回傳給父節點，因為它已經形成了一個完整的倒 V 型
        int currentPathSum = leftGain + node.val + rightGain;
        globalMax = Math.max(globalMax, currentPathSum);

        // 3. 回傳給父節點 (Return to Parent)
        // 父節點只能接上「左邊」或「右邊」其中一條比較大的路
        return node.val + Math.max(leftGain, rightGain);
    }

    // ==========================================
    // Test Cases
    // ==========================================
    public static void main(String[] args) {
        TreeDpPathSum solver = new TreeDpPathSum();

        // 測試案例 1: 簡單路徑
        //   1
        //  / \
        // 2   3
        // 全域最大: 2 -> 1 -> 3 = 6
        TreeNode root1 = new TreeNode(1);
        root1.left = new TreeNode(2);
        root1.right = new TreeNode(3);
        System.out.println("Max Path Sum 1: " + solver.maxPathSum(root1)); // Expected: 6

        // 測試案例 2: 含負數 (LeetCode 124 Example 2)
        //     -10
        //     /  \
        //    9   20
        //       /  \
        //      15   7
        // 
        // 步驟解析:
        // 1. Node 9: 回傳 9.
        // 2. Node 15: 回傳 15.
        // 3. Node 7: 回傳 7.
        // 4. Node 20: 
        //    - Update Global: 15 + 20 + 7 = 42 (目前最大)
        //    - Return Parent: 20 + max(15, 7) = 35
        // 5. Node -10 (Root):
        //    - Left Gain: 9
        //    - Right Gain: 35 (來自 Node 20)
        //    - Update Global: 9 + (-10) + 35 = 34. (沒比 42 大，不更新)
        //    - Return: -10 + 35 = 25
        // 結果: 42
        
        TreeNode root2 = new TreeNode(-10);
        root2.left = new TreeNode(9);
        root2.right = new TreeNode(20);
        root2.right.left = new TreeNode(15);
        root2.right.right = new TreeNode(7);

        System.out.println("Max Path Sum 2: " + solver.maxPathSum(root2)); // Expected: 42
    }
}