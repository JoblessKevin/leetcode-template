package problems.linkedlist;

public class ReorderLinkdList {
    static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    public void reorderList(ListNode head) {
        if (head == null || head.next == null) return;

        // Find mid
        ListNode mid = findMiddle(head);

        // Reverse last
        ListNode second = reverse(mid.next);
        mid.next = null;

        // Combine the alternate
        mergeAlternate(head, second);
    }

    private ListNode findMiddle(ListNode head) {
        ListNode fast = head;
        ListNode slow = head;
        while (fast != null && fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }

        return slow;
    }

    private ListNode reverse(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;

        while (curr != null) {
            ListNode temp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = temp;
        }

        return prev;
    }

    private void mergeAlternate(ListNode list1, ListNode list2) {
        while (list2 != null) {
            ListNode list1Next = list1.next;
            ListNode list2Next = list2.next;

            list1.next = list2;
            list2.next = list1Next;

            list1 = list1Next;
            list2 = list2Next;
        }
    }

    /** ========== Test Case ========== */
    public static void main(String[] args) {
        ReorderLinkdList sol = new ReorderLinkdList();

        // 測資 1: 偶數長度
        ListNode head1 = buildList(new int[]{1, 2, 3, 4});
        System.out.print("Original List: ");
        printList(head1);
        sol.reorderList(head1);
        System.out.print("Reordered List: ");
        printList(head1);
        System.out.println();

        // 測資 2: 奇數長度
        ListNode head2 = buildList(new int[]{1, 2, 3, 4, 5});
        System.out.print("Original List: ");
        printList(head2);
        sol.reorderList(head2);
        System.out.print("Reordered List: ");
        printList(head2);
        System.out.println();

        // 測資 3: 兩個節點
        ListNode head3 = buildList(new int[]{10, 20});
        System.out.print("Original List: ");
        printList(head3);
        sol.reorderList(head3);
        System.out.print("Reordered List: ");
        printList(head3);
        System.out.println();

        // 測資 4: 單節點
        ListNode head4 = buildList(new int[]{99});
        System.out.print("Original List: ");
        printList(head4);
        sol.reorderList(head4);
        System.out.print("Reordered List: ");
        printList(head4);
    }

    /** 建立鏈結串列 */
    private static ListNode buildList(int[] nums) {
        ListNode dummy = new ListNode(0);
        ListNode curr = dummy;
        for (int num : nums) {
            curr.next = new ListNode(num);
            curr = curr.next;
        }
        return dummy.next;
    }

    /** 印出鏈結串列 */
    private static void printList(ListNode head) {
        while (head != null) {
            System.out.print(head.val);
            if (head.next != null) System.out.print(" -> ");
            head = head.next;
        }
        System.out.println();
    }
}
