package problems.linkedlist;

public class LinkedListCyclell {
    public static class ListNode {
        int val;
        ListNode next;
        ListNode(int x) {
            val = x;
            next = null;
        }
    }

    public static class Solution {
        public ListNode detectCycle(ListNode head) {
            if (head == null || head.next == null) return null;

            ListNode slow = head;
            ListNode fast = head;

            // 第一階段：偵測是否有環
            while (fast != null && fast.next != null) {
                slow = slow.next;
                fast = fast.next.next;

                if (slow == fast) {
                    // 第二階段：尋找環起點
                    fast = head;
                    while (fast != slow) {
                        fast = fast.next;
                        slow = slow.next;
                    }
                    return slow; // 或 fast
                }
            }

            return null;
        }
    }

    public static void main(String[] args) {
        // 建立測資：3 -> 2 -> 0 -> -4 -> 指回 2
        ListNode n1 = new ListNode(3);
        ListNode n2 = new ListNode(2);
        ListNode n3 = new ListNode(0);
        ListNode n4 = new ListNode(-4);

        n1.next = n2;
        n2.next = n3;
        n3.next = n4;
        n4.next = n2; // 💡 建立 cycle：尾端指回第二個節點 (值=2)

        Solution sol = new Solution();
        ListNode cycleStart = sol.detectCycle(n1);

        if (cycleStart != null) {
            System.out.println("Cycle starts at node with value: " + cycleStart.val);
        } else {
            System.out.println("No cycle detected.");
        }
    }
}

