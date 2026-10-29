package problems.stack;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class MaximumFrequencyStack {
    /**
     * @formatter:off
     * Time push: O(1)
     * Time pop: O(n)
     * Space: O(n)
     * @formatter:on
     */
    public class FreqStack_BruteForce {
        private Map<Integer, Integer> map;
        private List<Integer> list;

        public FreqStack_BruteForce() {
            map = new HashMap<>();
            list = new ArrayList<>();
        }

        public void push(int val) {
            list.add(val);
            map.put(val, map.getOrDefault(val, 0) + 1);
        }

        public int pop() {
            int maxCount = Collections.max(map.values());
            int i = list.size() - 1;
            while (map.get(list.get(i)) != maxCount) {
                i--;
            }
            int val = list.remove(i);
            map.put(val, map.get(val) - 1);
            return val;
        }
    }

    /**
     * @formatter:off
     * 1. a[0] 和 b[0] 代表這兩個數字的 「頻率 (Frequency)」
     * 2. a[1] 和 b[1] 代表這兩個數字的 「流水號/加入順序 (Index)」
     * 3. a[2] 或 b[2]：實際的數字本身 (Value)
     * Time push: O(log n)
     * Time pop: O(log n)
     * Space: O(n)
     * @formatter:on
     */
    public class FreqStack_Heap {
        private PriorityQueue<int[]> heap;
        private Map<Integer, Integer> count;
        private int index;

        public FreqStack_Heap() {
            heap = new PriorityQueue<>((a, b) -> a[0] != b[0] ? Integer.compare(b[0], a[0])
                                            : Integer.compare(b[1], a[1]));
            count = new HashMap<>();
            index = 0;
        }

        public void push(int val) {
            count.put(val, count.getOrDefault(val, 0) + 1);
            heap.offer(new int[] {count.get(val), index++, val});
        }

        public int pop() {
            int[] top = heap.poll();
            int val = top[2];
            count.put(val, count.get(val) - 1);
            return val;
        }
    }

    /**
     * @formatter:off
     * Time push: O(1)
     * Time pop: O(1)
     * Space: O(n)
     * @formatter:on
     */
    class FreqStack_Map {
        private Map<Integer, Integer> count;
        private Map<Integer, Deque<Integer>> stacks;
        private int maxCount;

        public FreqStack_Map() {
            count = new HashMap<>();
            stacks = new HashMap<>();
            maxCount = 0;
        }

        public void push(int val) {
            int valCount = count.getOrDefault(val, 0) + 1;
            count.put(val, valCount);
            if (valCount > maxCount) {
                maxCount = valCount;
                stacks.putIfAbsent(valCount, new ArrayDeque<>());
            }
            stacks.get(valCount).push(val);
        }

        public int pop() {
            int res = stacks.get(maxCount).pop();
            count.put(res, count.get(res) - 1);
            if (stacks.get(maxCount).isEmpty()) {
                maxCount--;
            }
            return res;
        }
    }

    /**
     * @formatter:off
     * Time push: O(1)
     * Time pop: O(1)
     * Space: O(n)
     * @formatter:on
     */
    public class FreqStack {
        private Map<Integer, Integer> count;
        private List<Deque<Integer>> stacks;

        public FreqStack() {
            count = new HashMap<>();
            stacks = new ArrayList<>();
            stacks.add(new ArrayDeque<>());
        }

        public void push(int val) {
            int valCount = count.getOrDefault(val, 0) + 1;
            count.put(val, valCount);
            if (valCount == stacks.size()) {
                stacks.add(new ArrayDeque<>());
            }
            stacks.get(valCount).push(val);
        }

        public int pop() {
            Deque<Integer> topStack = stacks.get(stacks.size() - 1);
            int res = topStack.pop();
            count.put(res, count.get(res) - 1);
            if (topStack.isEmpty()) {
                stacks.remove(stacks.size() - 1);
            }
            return res;
        }
    }

    /**
     * Your FreqStack object will be instantiated and called as such: FreqStack obj = new
     * FreqStack(); obj.push(val); int param_2 = obj.pop();
     */

    public static void main(String[] args) {
        MaximumFrequencyStack mfs = new MaximumFrequencyStack();
        FreqStack freqStack = mfs.new FreqStack();
        freqStack.push(5);
        freqStack.push(7);
        freqStack.push(5);
        freqStack.push(7);
        freqStack.push(4);
        freqStack.push(5);
        System.out.println(freqStack.pop()); // 返回 5
        System.out.println(freqStack.pop()); // 返回 7
        System.out.println(freqStack.pop()); // 返回 5
        System.out.println(freqStack.pop()); // 返回 4
    }
}
