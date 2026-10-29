package templates.tree.BST;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class PostorderTraversal {
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int val) {
            this.val = val;
        }
    }

    public List<Integer> postorderTraversal(TreeNode root) {
        // 注意：這裡用 LinkedList，因為我們要用 addFirst (插隊)
        LinkedList<Integer> result = new LinkedList<>();
        if (root == null)
            return result;

        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            TreeNode curr = stack.pop();

            // 1. 做事：每次都加在「最前面」(相當於反轉)
            result.addFirst(curr.val);

            // 2. 放入小孩
            // 因為是 Stack (後進先出)，我們先放左，再放右
            // 拿出來的時候就會變成：先右，再左
            // 配合 addFirst，最終結果就是：左 -> 右 -> 中
            if (curr.left != null)
                stack.push(curr.left);
            if (curr.right != null)
                stack.push(curr.right);
        }

        return result;
    }
}
