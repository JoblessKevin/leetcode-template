package problems.linkedlist;

import java.util.PriorityQueue;

public class MergeKSortedLists {
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

    public static class MinHeap {
        public static ListNode mergeKLists(ListNode[] lists) {
            if (lists == null || lists.length == 0)
                return null;

            ListNode dummy = new ListNode(0);
            ListNode curr = dummy;

            PriorityQueue<ListNode> minHeap = new PriorityQueue<>((a, b) -> a.val - b.val);

            for (ListNode node : lists) {
                if (node != null) {
                    minHeap.offer(node);
                }
            }

            while (!minHeap.isEmpty()) {
                ListNode smallest = minHeap.poll();

                curr.next = smallest;
                curr = curr.next;

                if (smallest.next != null) {
                    minHeap.offer(smallest.next);
                }
            }

            return dummy.next;
        }
    }

    public static class Iteration {
        public static ListNode mergeKLists(ListNode[] lists) {
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
    }

    public static void main(String[] args) {
        ListNode list1 = new ListNode(1, new ListNode(4, new ListNode(5)));
        ListNode list2 = new ListNode(1, new ListNode(3, new ListNode(4)));
        ListNode list3 = new ListNode(2, new ListNode(6));

        ListNode[] lists = {list1, list2, list3};

        // Merge using MinHeap
        ListNode mergedMinHeap = MinHeap.mergeKLists(lists);
        System.out.print("Merged using MinHeap: ");
        printList(mergedMinHeap);

        // Reset the lists for the next merge
        list1 = new ListNode(1, new ListNode(4, new ListNode(5)));
        list2 = new ListNode(1, new ListNode(3, new ListNode(4)));
        list3 = new ListNode(2, new ListNode(6));
        lists = new ListNode[] {list1, list2, list3};

        // Merge using Iteration
        ListNode mergedIteration = Iteration.mergeKLists(lists);
        System.out.print("Merged using Iteration: ");
        printList(mergedIteration);
    }

    private static void printList(ListNode head) {
        while (head != null) {
            System.out.print(head.val + " ");
            head = head.next;
        }
        System.out.println();
    }
}
