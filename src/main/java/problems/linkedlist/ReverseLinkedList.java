package problems.linkedlist;

public class ReverseLinkedList {

    public static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    // Recursive version
    public ListNode reverseList(ListNode head) {
        if (head == null) return null;

        ListNode newHead = head;
        if (head.next != null) {
            newHead = reverseList(head.next);
            head.next.next = head;
        }
        head.next = null;
        return newHead;
    }

    // Iterative version (two pointers)
    public ListNode reverseList_LinkedList(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;
        while (curr != null) {
            ListNode nextTemp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = nextTemp;
        }
        return prev;
    }

    // Helpers
    public ListNode buildList(int... vals) {
        if (vals == null || vals.length == 0) return null;
        ListNode dummy = new ListNode(-1);
        ListNode cur = dummy;
        for (int v : vals) {
            cur.next = new ListNode(v);
            cur = cur.next;
        }
        return dummy.next;
    }

    public void printList(String label, ListNode head) {
        System.out.print(label + ": ");
        ListNode cur = head;
        while (cur != null) {
            System.out.print("[" + cur.val + "]");
            cur = cur.next;
            if (cur != null) System.out.print(" -> ");
        }
        System.out.println(" -> null");
    }

    public static void main(String[] args) {
        ReverseLinkedList rl = new ReverseLinkedList();

        // Test data
        ListNode head1 = rl.buildList(1, 2, 3, 4, 5);
        rl.printList("Original list", head1);

        // Recursive
//        ListNode reversed1 = rl.reverseList(head1);
//        rl.printList("Reversed (recursive)", reversed1);

        // Iterative
        ListNode head2 = rl.buildList(1, 2, 3, 4, 5);
        ListNode reversed2 = rl.reverseList_LinkedList(head2);
        rl.printList("Reversed (iterative)", reversed2);

        // Edge cases
        ListNode empty = rl.buildList();
        ListNode reversedEmpty = rl.reverseList(empty);
        rl.printList("Empty list reversed", reversedEmpty);

        ListNode single = rl.buildList(42);
        rl.printList("Single-node original", single);
        ListNode reversedSingle = rl.reverseList(single);
        rl.printList("Single-node reversed", reversedSingle);
    }
}
