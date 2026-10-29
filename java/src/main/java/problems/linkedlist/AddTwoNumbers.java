package problems.linkedlist;

public class AddTwoNumbers {
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

    public ListNode addTwoNumbers_optimize(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(-1);
        ListNode curr = dummy;

        int carry = 0;

        while (l1 != null || l2 != null || carry > 0) {
            int val1 = (l1 != null) ? l1.val : 0;
            int val2 = (l2 != null) ? l2.val : 0;
            int sum = val1 + val2 + carry;

            carry = sum / 10;
            sum = sum % 10;
            curr.next = new ListNode(sum);
            curr = curr.next;

            l1 = (l1 != null) ? l1.next : null;
            l2 = (l2 != null) ? l2.next : null;
        }

        return dummy.next;
    }

    /** Recursive version */

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        return addRecursive(l1, l2, 0);
    }

    private ListNode addRecursive(ListNode l1, ListNode l2, int carry) {
        if (l1 == null && l2 == null && carry == 0) {
            return null;
        }

        int sum = carry;
        if (l1 != null)
            sum += l1.val;
        if (l2 != null)
            sum += l2.val;

        ListNode node = new ListNode(sum % 10);

        node.next = addRecursive((l1 != null) ? l1.next : null, (l2 != null) ? l2.next : null,
                                        sum / 10);

        return node;
    }

    /** ---------- Test case ---------- */
    public static void main(String[] args) {
        AddTwoNumbers sol = new AddTwoNumbers();

        // l1 = [2,4,3], l2 = [5,6,4]
        ListNode l1 = new ListNode(2, new ListNode(4, new ListNode(3)));
        ListNode l2 = new ListNode(5, new ListNode(6, new ListNode(4)));

        System.out.println("=== Iterative version ===");
        ListNode result1 = sol.addTwoNumbers_optimize(l1, l2);
        printList(result1);

        l1 = new ListNode(2, new ListNode(4, new ListNode(3)));
        l2 = new ListNode(5, new ListNode(6, new ListNode(4)));

        System.out.println("=== Recursive version ===");
        ListNode result2 = sol.addTwoNumbers(l1, l2);
        printList(result2);
    }

    private static void printList(ListNode head) {
        while (head != null) {
            System.out.print(head.val);
            if (head.next != null)
                System.out.print(" -> ");
            head = head.next;
        }
        System.out.println();
    }
}
