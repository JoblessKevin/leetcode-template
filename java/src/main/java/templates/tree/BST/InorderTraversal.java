package templates.tree.BST;

import java.util.ArrayDeque;
import java.util.Deque;

public class InorderTraversal {

    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int val) {
            this.val = val;
        }
    }

    public void inorderTraversal(TreeNode root) {
        // 1. 準備 Stack 和 指針
        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode curr = root;

        // 2. 外層迴圈：只要還有節點沒處理，或 Stack 不空，就繼續
        while (curr != null || !stack.isEmpty()) {
            // 3. 內層迴圈：無腦往左鑽到底 (鑽到最小值)
            while (curr != null) {
                stack.push(curr);
                curr = curr.left;
            }

            // 4. 彈出節點 (這裡就是「處理」節點的時刻！)
            curr = stack.pop();

            // ============================================
            // ⭐️ 業務邏輯寫在這裡 (Business Logic) ⭐️
            // ============================================
            // 例如：
            // 1. 驗證 BST: check if (curr.val > prev.val)
            // 2. 找第 K 小: k--; if (k == 0) return curr.val;
            // 3. 印出數值: list.add(curr.val);
            // ============================================

            // 5. 轉向右子樹 (處理完中間，換右邊)
            curr = curr.right;
        }
    }
}