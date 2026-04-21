package origins;

public class AvlTree {
    class Node {
        int key, height;
        Node left, right;

        Node(int d) {
            key = d;
            height = 1;
        }
    }

    private Node root;

    // 1. 取得高度的輔助方法 (空節點高度為 0)
    private int height(Node n) {
        return n == null ? 0 : n.height;
    }

    // 2. 取得平衡因子 (左高 - 右高)
    private int getBalance(Node n) {
        return n == null ? 0 : height(n.left) - height(n.right);
    }

    // 3. 右旋 (用於處理 LL 型不平衡)
    private Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        // 進行旋轉
        x.right = y;
        y.left = T2;

        // 更新高度
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x; // 返回新的根節點
    }

    // 4. 左旋 (用於處理 RR 型不平衡)
    private Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        y.left = x;
        x.right = T2;

        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }

    public void insert(int key) {
        root = insertRecursive(root, key);
    }

    private Node insertRecursive(Node node, int key) {
        // 1. 標準 BST 插入
        if (node == null)
            return new Node(key);

        if (key < node.key)
            node.left = insertRecursive(node.left, key);
        else if (key > node.key)
            node.right = insertRecursive(node.right, key);
        else
            return node; // 不允許重複的 key

        // 2. 更新當前節點高度
        node.height = 1 + Math.max(height(node.left), height(node.right));

        // 3. 檢查是否失去平衡
        int balance = getBalance(node);

        // Case 1: LL 型 (左左) -> 只要一次右旋
        if (balance > 1 && key < node.left.key)
            return rightRotate(node);

        // Case 2: RR 型 (右右) -> 只要一次左旋
        if (balance < -1 && key > node.right.key)
            return leftRotate(node);

        // Case 3: LR 型 (左右) -> 先左旋再右旋
        if (balance > 1 && key > node.left.key) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // Case 4: RL 型 (右左) -> 先右旋再左旋
        if (balance < -1 && key < node.right.key) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }
}
