package problems.linkedlist;

public class ReverseLinkedList {
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

    class Recursive {
        public ListNode reverseList(ListNode head) {
            if (head == null)
                return null;

            ListNode newHead = head;
            if (head.next != null) {
                newHead = reverseList(head.next);
                head.next.next = head;
            }
            head.next = null;
            return newHead;
        }
    }

    class Iterative {
        public ListNode reverseList(ListNode head) {
            ListNode prev = null;
            ListNode curr = head;
            while (curr != null) {
                ListNode nextTemp = curr.next;
                curr.next = prev;
                prev = curr;
                curr = nextTemp;
            }
            return prev;
        }
    }

    public static void main(String[] args) {
        ReverseLinkedList reverseLinkedList = new ReverseLinkedList();
        ListNode head = new ListNode(1, new ListNode(2,
                                        new ListNode(3, new ListNode(4, new ListNode(5)))));
        ListNode reversedHead = reverseLinkedList.new Iterative().reverseList(head);
        while (reversedHead != null) {
            System.out.print(reversedHead.val + " ");
            reversedHead = reversedHead.next;
        }
    }
}
