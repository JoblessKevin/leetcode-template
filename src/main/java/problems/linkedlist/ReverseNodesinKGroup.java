// package main.java.problems.linkedlist;

public class ReverseNodesinKGroup {
    static class ListNode {
        int val;
        ListNode next;
        ListNode() {};
        ListNode(int val) {
            this.val = val;
        }
        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    public ListNode reverseKGroup_recursive(ListNode head, int k) {
        ListNode cur = head;
        int group = 0;
        while(cur != null && group < k) {
            cur = cur.next;
            group++;
        }
        if (group == k) {
            cur = reverseKGroup_recursive(cur, k);
            while(group-- > 0) {
                ListNode temp = head.next;
                head.next = cur;
                cur = head;
                head = temp;
            }
            head = cur;
        }

        return head;
    }

    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode dummy = new ListNode(0, head);
        ListNode groupPrev = dummy;

        while (true) {
            ListNode kth = getKth(groupPrev, k);
            if (kth == null) break;
            ListNode groupNext = kth.next;
            ListNode prev = kth.next;
            ListNode cur = groupPrev.next;

            while (cur != groupNext) {
                ListNode temp = cur.next;
                cur.next = prev;
                prev = cur;
                cur = temp;
            }
            ListNode temp = groupPrev.next;
            groupPrev.next = kth;
            groupPrev = temp;
        }

        return dummy.next;
    }

    private ListNode getKth(ListNode cur, int k) {
        while (cur != null && k > 0) {
            cur = cur.next;
            k--;
        }

        return cur;
    }

    // ================= 測試主程式 (Main) =================
    public static void main(String[] args) {
        ReverseNodesinKGroup solver = new ReverseNodesinKGroup();

        System.out.println("====== 測試 1: 迭代法 (Iterative) ======");
        // 測資: 1->2->3->4->5, k = 2
        // 預期: 2->1->4->3->5
        int[] data1 = {1, 2, 3, 4, 5};
        int k1 = 2;
        ListNode head1 = solver.buildList(data1);

        System.out.print("原本: ");
        solver.printList(head1);
        
        ListNode result1 = solver.reverseKGroup(head1, k1);
        
        System.out.print("翻轉後 (k=" + k1 + "): ");
        solver.printList(result1);


        System.out.println("\n====== 測試 2: 遞迴法 (Recursive) ======");
        // 測資: 1->2->3->4->5, k = 3
        // 預期: 3->2->1->4->5
        int[] data2 = {1, 2, 3, 4, 5};
        int k2 = 3;
        ListNode head2 = solver.buildList(data2);

        System.out.print("原本: ");
        solver.printList(head2);

        ListNode result2 = solver.reverseKGroup_recursive(head2, k2);

        System.out.print("翻轉後 (k=" + k2 + "): ");
        solver.printList(result2);
        
        
        System.out.println("\n====== 測試 3: 邊界測試 (k > 長度) ======");
        // 測資: 1->2->3, k = 5
        // 預期: 1->2->3 (不變)
        int[] data3 = {1, 2, 3};
        int k3 = 5;
        ListNode head3 = solver.buildList(data3);
        
        System.out.print("原本: ");
        solver.printList(head3);
        
        ListNode result3 = solver.reverseKGroup(head3, k3);
        
        System.out.print("翻轉後 (k=" + k3 + "): ");
        solver.printList(result3);
    }

    // ================= Helpers =================
    public ListNode buildList(int[] nums) {
        if (nums == null || nums.length == 0) return null;
        ListNode dummy = new ListNode(-1);
        ListNode cur = dummy;
        for (int num : nums) {
            cur.next = new ListNode(num);
            cur = cur.next;
        }
        return dummy.next;
    }

    public void printList(ListNode head) {
        ListNode cur = head;
        while (cur != null) {
            System.out.print(cur.val);
            if (cur.next != null) System.out.print(" -> ");
            cur = cur.next;
        }
        System.out.println();
    }
}
