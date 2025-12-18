package problems.tree;

import java.util.Queue;

public class maxDepth {
    static TreeNode {
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

    // public int maxDepth(TreeNode root) {
    //     if (root == null) return 0;
    //     return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
    // }

    public int maxDepth_DFS(TreeNode root) {
        return root == null ? 0 :
            Math.max(maxDepth_DFS(root.left), maxDepth_DFS(root.right)) + 1;
    }

    public int maxDepth_BFS(TreeNode root) {
        if (root == null) return 0;

        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);

        int depth = 0;
        while(!queue.isEmpty()) {
            int size = queue.size();

            for (int i=0; i<size; i++) {
                TreeNode curr = queue.poll();

                if (curr.left != null) queue.offer(curr.left);
                if (curr.right != null) queue.offer(curr.right);
            }

            depth++;
        }

        return depth;
    }

    public static void main(String[] args) {
        // 3. 建立測試案例 (Test Case)
        // 目標樹結構:
        //      3
        //     / \
        //    9  20
        //      /  \
        //     15   7
        // 預期深度: 3

        // 手動建立節點
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(9);
        root.right = new TreeNode(20);
        
        // 建立第三層
        root.right.left = new TreeNode(15);
        root.right.right = new TreeNode(7);

        // 4. 執行測試
        Solution solution = new Solution();
        int result = solution.maxDepth_DFS(root);

        // 5. 輸出結果
        System.out.println("測試案例: [3, 9, 20, null, null, 15, 7]");
        System.out.println("預期深度: 3");
        System.out.println("實際計算深度: " + result);

        if (result == 3) {
            System.out.println("✅ 測試通過！");
        } else {
            System.out.println("❌ 測試失敗！");
        }
        
        // 額外測試: 空樹的情況
        System.out.println("\n--- 額外測試: 空樹 ---");
        System.out.println("深度: " + solution.maxDepth(null)); // 應該印出 0
    }

    public int maxDepth_doubleStack(TreeNode root) {
        if (root == null) return 0;

        Deque<TreeNode> nodeStack = new ArrayDeque<>();
        Deque<Integer> depthStack = new ArrayDeque<>();

        nodeStack.push(root);
        depthStack.push(1);
        int max = 0;

        while(!nodeStack.isEmpty()) {
            TreeNode node = nodeStack.pop();
            int currentDepth = depthStack.pop();
            max = Math.max(max, currentDepth);

            if (node.right != null) {
                nodeStack.push(node.right);
                depthStack.push(currentDepth + 1);
            }

            if (node.left != null) {
                nodeStack.push(node.left);
                depthStack.push(currentDepth + 1);
            }
        }

        return max;
    }
}
