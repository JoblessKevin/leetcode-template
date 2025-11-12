package problems.linkedlist;

public class RemoveNodeFromEndofLinkedList {
    public static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    public ListNode removeNthFromEnd(ListNode head, int n) {
        if (head == null) return null;

        int count = 0;
        ListNode curr = head;

        while (curr != null) {
            count++;
            curr = curr.next;
        }

        int indexToDelete = count - n;

        if (indexToDelete == 0) {
            return head.next;
        }

        curr = head;

        for (int i = 0; i < indexToDelete - 1; i++) {
            curr = curr.next;
        }

        curr.next = curr.next.next;

        return head;
    }
}
