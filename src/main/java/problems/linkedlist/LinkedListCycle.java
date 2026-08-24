package problems.linkedlist;

import java.util.Set;
import java.util.HashSet;

public class LinkedListCycle {
    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }

    public static class SolutionWithSet {
        public boolean detectCycle(ListNode head) {
            Set<ListNode> visited = new HashSet<>();
            ListNode curr = head;

            while (curr != null) {
                if (visited.contains(curr)) {
                    return true;
                }
                visited.add(curr);
                curr = curr.next;
            }

            return false;
        }
    }

    public static class SolutionWithTwoPointers {
        public boolean detectCycle(ListNode head) {
            ListNode slow = head;
            ListNode fast = head;

            while (fast != null && fast.next != null) {
                slow = slow.next;
                fast = fast.next.next;

                if (slow == fast) {
                    return true;
                }
            }

            return false;
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
        n4.next = n2; // create cycle

        SolutionWithSet solSet = new SolutionWithSet();
        boolean hasCycle = solSet.detectCycle(n1);

        SolutionWithTwoPointers solTwoPointers = new SolutionWithTwoPointers();
        boolean hasCycleTwoPointers = solTwoPointers.detectCycle(n1);

        if (hasCycle) {
            System.out.println("Cycle detected (Set).");
        } else {
            System.out.println("No cycle detected (Set).");
        }

        if (hasCycleTwoPointers) {
            System.out.println("Cycle detected (Two Pointers).");
        } else {
            System.out.println("No cycle detected (Two Pointers).");
        }
    }
}
