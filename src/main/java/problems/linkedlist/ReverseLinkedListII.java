package problems.linkedlist;

public class ReverseLinkedListII {
    public static class ListNode {
        int val;
        ListNode next;

        ListNode() {}

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    public class Iteration {
        public ListNode reverseBetween(ListNode head, int left, int right) {
            ListNode dummy = new ListNode(0);
            dummy.next = head;
            ListNode leftPrev = dummy;
            ListNode curr = head;

            for (int i = 0; i < left - 1; i++) {
                leftPrev = curr;
                curr = curr.next;
            }

            ListNode prev = null;
            for (int i = 0; i < right - left + 1; i++) {
                ListNode tmpNext = curr.next;
                curr.next = prev;
                prev = curr;
                curr = tmpNext;
            }

            leftPrev.next.next = curr;
            leftPrev.next = prev;

            return dummy.next;
        }
    }

    public static void main(String[] args) {
        ReverseLinkedListII reverseLinkedListII = new ReverseLinkedListII();
        Iteration solution = reverseLinkedListII.new Iteration();

        ListNode head = new ListNode(1, new ListNode(2,
                                        new ListNode(3, new ListNode(4, new ListNode(5)))));
        int left = 2;
        int right = 4;

        ListNode result = solution.reverseBetween(head, left, right);
        printList(result);
    }

    private static void printList(ListNode head) {
        while (head != null) {
            System.out.print(head.val + " ");
            head = head.next;
        }
        System.out.println();
    }
}
