package problems.linkedlist;

public class MergeTwoSortedLists {
    public static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    static class Solution {
        public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
            if (list1 == null) return list2;
            if (list2 == null) return list1;

            if (list1.val <= list2.val) {
                list1.next = mergeTwoLists(list1.next, list2);
                return list1;
            } else {
                list2.next = mergeTwoLists(list1, list2.next);
                return list2;
            }
        }
    }

    // 印出 Linked List
    public static void printList(ListNode head) {
        while (head != null) {
            System.out.print(head.val);
            if (head.next != null) System.out.print(" -> ");
            head = head.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Solution sol = new Solution();

        // 測資 1：兩個普通遞增 list
        ListNode list1 = new ListNode(1, new ListNode(2, new ListNode(4)));
        ListNode list2 = new ListNode(1, new ListNode(3, new ListNode(4)));
        System.out.println("Test 1:");
        printList(sol.mergeTwoLists(list1, list2)); // ➜ 1 -> 1 -> 2 -> 3 -> 4 -> 4

        // 測資 2：一邊是空的
        list1 = null;
        list2 = new ListNode(0);
        System.out.println("Test 2:");
        printList(sol.mergeTwoLists(list1, list2)); // ➜ 0

        // 測資 3：完全交錯
        list1 = new ListNode(1, new ListNode(3, new ListNode(5)));
        list2 = new ListNode(2, new ListNode(4, new ListNode(6)));
        System.out.println("Test 3:");
        printList(sol.mergeTwoLists(list1, list2)); // ➜ 1 -> 2 -> 3 -> 4 -> 5 -> 6

        // 測資 4：一邊完全比另一邊大
        list1 = new ListNode(5, new ListNode(6));
        list2 = new ListNode(1, new ListNode(2, new ListNode(3)));
        System.out.println("Test 4:");
        printList(sol.mergeTwoLists(list1, list2)); // ➜ 1 -> 2 -> 3 -> 5 -> 6

        // 測資 5：兩邊都只有一個節點
        list1 = new ListNode(1);
        list2 = new ListNode(2);
        System.out.println("Test 5:");
        printList(sol.mergeTwoLists(list1, list2)); // ➜ 1 -> 2
    }
}
