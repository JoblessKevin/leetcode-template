package problems.linkedlist;

import java.util.HashMap;

public class CopyListWithRandomPointer {
    public static class Node {
        int val;
        Node next;
        Node random;

        public Node(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }
    }

    public static class Recursive {
        HashMap<Node, Node> map = new HashMap<>();

        public Node copyRandomList(Node head) {
            if (head == null)
                return null;
            if (map.containsKey(head))
                return map.get(head);

            Node copy = new Node(head.val);
            map.put(head, copy);
            copy.next = copyRandomList(head.next);
            copy.random = map.get(head.random);
            return copy;
        }
    }

    public static class Interweaving {
        public Node copyRandomList(Node head) {
            if (head == null) {
                return null;
            }

            // -------------------------------------------------------------
            // 步驟 1：原地複製節點，並穿插在舊節點的旁邊
            // 例如：A -> B 變成 A -> A' -> B -> B'
            // -------------------------------------------------------------
            Node curr = head;
            while (curr != null) {
                Node nextTemp = curr.next;
                Node cloneNode = new Node(curr.val);

                curr.next = cloneNode; // 舊的指向新的
                cloneNode.next = nextTemp; // 新的指向下一個舊的

                curr = nextTemp; // 往下一個舊節點移動
            }

            // -------------------------------------------------------------
            // 步驟 2：設定所有新節點的 random 指標
            // 因為新節點就剛好黏在舊節點的隔壁，設定變得超級簡單！
            // -------------------------------------------------------------
            curr = head;
            while (curr != null) {
                if (curr.random != null) {
                    // 新節點的 random = 舊節點的 random 指向之目標的「下一個」(也就是其複製體)
                    curr.next.random = curr.random.next;
                }
                curr = curr.next.next; // 跳過新節點，看下一個舊節點
            }

            // -------------------------------------------------------------
            // 步驟 3：把交錯的兩條鏈結串列拆開，復原舊串列、獨立出新串列
            // -------------------------------------------------------------
            curr = head;
            Node dummyHead = head.next; // 記錄新串列的頭

            while (curr != null) {
                Node cloneNode = curr.next;
                Node nextTemp = cloneNode.next; // 記錄下一個舊節點

                // 恢復舊串列的 next
                curr.next = nextTemp;

                // 串接新串列的 next（如果還有下一個複製體的話）
                if (nextTemp != null) {
                    cloneNode.next = nextTemp.next;
                }

                curr = nextTemp; // 移動到下一個舊節點
            }

            return dummyHead;
        }
    }

    public static void main(String[] args) {
        // Example usage:
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        node1.next = node2;
        node1.random = node2;
        node2.random = node2;

        Interweaving interweavingSolution = new Interweaving();
        Node copiedList = interweavingSolution.copyRandomList(node1);

        // Print the copied list to verify
        Node curr = copiedList;
        while (curr != null) {
            System.out.println("Node val: " + curr.val + ", Random val: "
                                            + (curr.random != null ? curr.random.val : "null"));
            curr = curr.next;
        }
    }
}
