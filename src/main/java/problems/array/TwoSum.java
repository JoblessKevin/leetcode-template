package problems.array;

import java.util.HashMap;
import java.util.Map;

public class TwoSum {
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (map.containsKey(complement)) {
                return new int[] {map.get(complement), i};
            }
            map.put(nums[i], i);
        }

        return new int[] {};
    }

    public static void main(String[] args) {
        TwoSum solver = new TwoSum();

        // 測試案例
        int[] nums = {2, 7, 11, 15};
        int target = 9;
        System.out.println("測試輸入: nums = " + java.util.Arrays.toString(nums) + ", target = "
                                        + target);
        System.out.println("期待結果: [0, 1]");
        System.out.println("-------------------------------------");

        // 測試
        int[] result = solver.twoSum(nums, target);
        System.out.println("方法 1 (桶子) 結果: " + java.util.Arrays.toString(result));

        System.out.println("-------------------------------------");
        System.out.println("驗證完成。");
    }
}
