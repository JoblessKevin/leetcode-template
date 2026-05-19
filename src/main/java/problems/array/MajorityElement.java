package problems.array;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MajorityElement {
    // Boyer-Moore Voting Algorithm
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

        for (int num : nums) {
            // 更新計數
            int count = counts.getOrDefault(num, 0) + 1;
            counts.put(num, count);

            // 提早結束：如果已經過半，直接回傳
            if (count > nums.length / 2)
                return num;
        }
        return -1;
    }

    public int majorityElement_hashmap_brutedforce(int[] nums) {
        HashMap<Integer, Integer> map = new HashMap<>();

        for (int num : nums) {
            if (map.containsKey(num)) {
                map.put(num, map.get(num) + 1);
            } else {
                map.put(num, 1);
            }
            if (map.get(num) > nums.length / 2)
                return num;

        }

        return -1;
    }

    public static void main(String[] args) {
        MajorityElement solver = new MajorityElement();

        // 測試案例
        int[] nums = {2, 2, 1, 1, 1, 2, 2};
        System.out.println("測試輸入: nums = " + Arrays.toString(nums));
        System.out.println("期待結果: 2");
        System.out.println("-------------------------------------");

        // 執行
        int result = solver.majorityElement(nums);
        System.out.println("實際結果: " + result);

        System.out.println("-------------------------------------");
        System.out.println("驗證完成。");
    }
}
