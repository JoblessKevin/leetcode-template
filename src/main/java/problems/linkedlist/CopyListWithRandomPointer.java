package problems.linkedlist;

import java.util.HashMap;
import java.util.Map;

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

    Map<Node, Node> map = new HashMap<>();

    public Node copyRandomList(Node head) {
        if (head == null) return null;
        if (map.containsKey(head)) return map.get(head);

        Node copy = new Node(head.val);
        map.put(head, copy);

        copy.next = copyRandomList(head.next);
        copy.random = copyRandomList(head.random);

        return copy;
    }

    public static void main(String[] args) {
        CopyListWithRandomPointer solution = new CopyListWithRandomPointer();

        // 建立節點
        Node a = new Node(1);
        Node b = new Node(2);
        Node c = new Node(3);

        // 建立 next 關係
        a.next = b;
        b.next = c;

        // 建立 random 關係
        a.random = c;  // a -> c
        b.random = a;  // b -> a
        c.random = b;  // c -> b

        // 複製
        Node clonedHead = solution.copyRandomList(a);

        // 印出原始與複製結果
        System.out.println("=== Original List ===");
        printList(a);

        System.out.println("\n=== Copy List ===");
        printList(clonedHead);
    }

    private static void printList(Node head) {
        Node curr = head;
        while (curr != null) {
            String nextVal = (curr.next != null) ? String.valueOf(curr.next.val) : "null";
            String randomVal = (curr.random != null) ? String.valueOf(curr.random.val) : "null";

            System.out.printf(
                    "Node@%s  val=%d,  next=%s,  random=%s\n",
                    Integer.toHexString(System.identityHashCode(curr)),
                    curr.val,
                    nextVal,
                    randomVal
            );
            curr = curr.next;
        }
    }

}
