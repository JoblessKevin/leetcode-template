package problems.array;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MajorityElement {
    public int majorityElement(int[] nums) {
        int count = 0;
        int candidate = 0;

        for (int num : nums) {
            if (count == 0) {
                candidate = num;
            }
            count += (num == candidate) ? 1 : -1;
        }

        return candidate;
    }

    public int majorityElement_sort(int[] nums) {
        // 使用 Java 內建的 Dual-Pivot Quicksort
        Arrays.sort(nums);
        // 直接拿中間那個數字
        return nums[nums.length / 2];
    }

    public int majorityElement_hashmap(int[] nums) {
        Map<Integer, Integer> counts = new HashMap<>();
        int n = nums.length;

        for (int num : nums) {
            // 更新計數
            int count = counts.getOrDefault(num, 0) + 1;
            counts.put(num, count);

            // 提早結束：如果已經過半，直接回傳
            if (count > n / 2)
                return num;
        }
        return -1;
    }
}
