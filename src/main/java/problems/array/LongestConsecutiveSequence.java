package problems.array;

import java.util.HashSet;

public class LongestConsecutiveSequence {
    public int longestConsecutive(int[] nums) {
        HashSet<Integer> set = new HashSet<>();

        for (int num : nums) {
            set.add(num);
        }

        int count = 0;

        for (int num : set) {
            if (!set.contains(num - 1)) {
                int curr = num;
                int temp = 1;

                while (set.contains(curr + 1)) {
                    curr++;
                    temp++;
                }

                count = Math.max(count, temp);
            }
        }

        return count;
    }
}
