package templates.treeTraverse;

public class TreeTraverse {
    public static class TreeNode { public int val; public TreeNode left,right; public TreeNode(int v){val=v;} }

    // Recursive
    public static void inorder(TreeNode r, java.util.List<Integer> out){
        if (r==null) return;
        inorder(r.left,out); out.add(r.val); inorder(r.right,out);
    }

    // Iterative In-order Traversal
    public static java.util.List<Integer> inorderIter(TreeNode root){
        var st = new java.util.ArrayDeque<TreeNode>();
        var res = new java.util.ArrayList<Integer>();
        TreeNode cur = root;
        while (cur!=null || !st.isEmpty()){
            while (cur!=null){ st.push(cur); cur=cur.left; }
            cur = st.pop(); res.add(cur.val); cur = cur.right;
        }
        return res;
    }
}
