package problems.linkedlist;

public class mergeKSortedLists {
    public static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    public ListNode mergeKLists_iteration(ListNode[] lists) {
        ListNode res = new ListNode(0);
        ListNode cur = res;

        while (true) {
            int minNode = -1;
            for (int i = 0; i < lists.length; i++) {
                if (lists[i] == null) {
                    continue;
                }
                if (minNode == -1 || lists[minNode].val > lists[i].val) {
                    minNode = i;
                }
            }

            if (minNode == -1) {
                break;
            }
            cur.next = lists[minNode];
            lists[minNode] = lists[minNode].next;
            cur = cur.next;
        }

        return res.next;
    }

    public static void main(String[] args) {
        mergeKSortedLists s = new mergeKSortedLists();

        // 測試用 list
        // list1: 1 -> 4 -> 5
        // list2: 1 -> 3 -> 4
        // list3: 2 -> 6
        // list4: null
        ListNode l1 = buildList(new int[]{1, 4, 5});
        ListNode l2 = buildList(new int[]{1, 3, 4});
        ListNode l3 = buildList(new int[]{2, 6});
        ListNode l4 = null; // 測試 null 處理

        ListNode[] lists = {l1, l2, l3, l4};

        ListNode result = s.mergeKLists_iteration(lists);
        printList(result);
    }


    // ----------------------------
    // 工具方法 - 建立鏈結串列
    // ----------------------------
    private static ListNode buildList(int[] arr) {
        if (arr == null || arr.length == 0) return null;
        ListNode head = new ListNode(arr[0]);
        ListNode cur = head;
        for (int i = 1; i < arr.length; i++) {
            cur.next = new ListNode(arr[i]);
            cur = cur.next;
        }
        return head;
    }

    // ----------------------------
    // 工具方法 - 列印鏈結串列
    // ----------------------------
    private static void printList(ListNode head) {
        while (head != null) {
            System.out.print(head.val + " -> ");
            head = head.next;
        }
        System.out.println("null");
    }
}
