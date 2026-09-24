package problems.linkedlist;

public class MergeTwoSortedLists {
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

    /**
     * @formatter:off
     * 這個方法會直接修改原本 list1 和 list2 的內容
     * Input: list1 = [1,2,4], list2 = [1,3,4]
     * list1: 1 -> 2 -> 4
     * list2: 1 -> 3 -> 4
     * Output: 1 -> 1 -> 2 -> 3 -> 4 -> 4
     * list1: 1 -> 1 -> 2 -> 3 -> 4 -> 4
     * list2: 1 -> 2 -> 3 -> 4 -> 4
     * @formatter:on
     */
    class Recursive {
        public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
            if (list1 == null)
                return list2;
            if (list2 == null)
                return list1;

            if (list1.val <= list2.val) {
                list1.next = mergeTwoLists(list1.next, list2);
                return list1;
            } else {
                list2.next = mergeTwoLists(list1, list2.next);
                return list2;
            }
        }
    }

    class Iterative {
        public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
            ListNode dummy = new ListNode(-1);
            ListNode curr = dummy;

            while (list1 != null && list2 != null) {
                if (list1.val <= list2.val) {
                    curr.next = list1;
                    list1 = list1.next;
                } else {
                    curr.next = list2;
                    list2 = list2.next;
                }
                curr = curr.next;
            }

            if (list1 != null) {
                curr.next = list1;
            } else {
                curr.next = list2;
            }

            return dummy.next;
        }
    }

    public static void main(String[] args) {
        MergeTwoSortedLists mergeTwoSortedLists = new MergeTwoSortedLists();
        ListNode list1 = new ListNode(1, new ListNode(2, new ListNode(4)));
        ListNode list2 = new ListNode(1, new ListNode(3, new ListNode(4)));
        ListNode mergedHead = mergeTwoSortedLists.new Iterative().mergeTwoLists(list1, list2);
        // ListNode mergedHead = mergeTwoSortedLists.new Recursive().mergeTwoLists(list1, list2);
        while (mergedHead != null) {
            System.out.print(mergedHead.val + " ");
            mergedHead = mergedHead.next;
        }
    }
}
