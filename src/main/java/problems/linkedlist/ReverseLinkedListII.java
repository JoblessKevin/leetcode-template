package problems.linkedlist;

public class ReverseLinkedListII {
    public static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    public ListNode reverseBetween(ListNode head, int left, int right) {
        if (head == null || left == right) return head;

        ListNode dummy = new ListNode(0);
        dummy.next = head;

        ListNode before = dummy;
        for (int i = 0; i < left - 1; i++) {
            before = before.next;
        }

        ListNode start = before.next;
        ListNode curr = start;
        ListNode prev = null;

        for (int i = left; i <= right; i++) {
            ListNode temp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = temp;
        }

        before.next = prev;
        start.next = curr;

        return dummy.next;
    }

    public static void main(String[] args) {
        ReverseLinkedListII solver = new ReverseLinkedListII();

        // ======== Test case 1 ========
        ListNode list1 = buildList(1, 2, 3, 4, 5);
        System.out.println("Before reverse:");
        printList(list1);

        ListNode result1 = solver.reverseBetween(list1, 2, 4);
        System.out.println("After reverse (left=2, right=4):");
        printList(result1);
        System.out.println("----------------------------");

        // ======== Test case 2 ========
        ListNode list2 = buildList(1, 2, 3);
        System.out.println("Before reverse:");
        printList(list2);

        ListNode result2 = solver.reverseBetween(list2, 1, 3);
        System.out.println("After reverse (left=1, right=3):");
        printList(result2);
    }

    /** Tools */

    public static ListNode buildList(int... vals) {
        ListNode dummy = new ListNode(0);
        ListNode curr = dummy;
        for (int v : vals) {
            curr.next = new ListNode(v);
            curr = curr.next;
        }
        return dummy.next;
    }

    public static void printList(ListNode head) {
        ListNode curr = head;
        while (curr != null) {
            System.out.print(curr.val);
            if (curr.next != null) System.out.print(" -> ");
            curr = curr.next;
        }
        System.out.println();
    }

    /** Deprecated */
    // public ListNode reverseBetween(ListNode head, int left, int right) {
//        ListNode dummy = new ListNode(0);
//        dummy.next = head;
//
//        ListNode prev = dummy;
//        for (int i = 0; i < left - 1; i++) {
//            prev = prev.next;
//        }
//
//        ListNode curr = prev.next;
//        ListNode next = null;
//
//        for (int i = 0; i < right - left; i++) {
//            next = curr.next;
//            curr.next = next.next;
//            next.next = prev.next;
//            prev.next = next;
//        }
//
//        return dummy.next;
//    }
}
