package problems.twoPointer;

import java.util.HashMap;
import java.util.Map;

public class FourSumII {
    public int fourSumCount(int[] nums1, int[] nums2, int[] nums3, int[] nums4) {
        Map<Integer, Integer> map = new HashMap<>();

        // 1. 處理前兩個陣列：將和及其出現次數存入 map
        for (int a : nums1) {
            for (int b : nums2) {
                int sum = a + b;
                map.put(sum, map.getOrDefault(sum, 0) + 1);
            }
        }

        int count = 0;
        // 2. 處理後兩個陣列：尋找負數補數
        for (int c : nums3) {
            for (int d : nums4) {
                int target = -(c + d);
                if (map.containsKey(target)) {
                    count += map.get(target);
                }
            }
        }

        return count;
    }

    public static void main(String[] args) {
        // 定義四個陣列
        int[] nums1 = {1, 2};
        int[] nums2 = {-2, -1};
        int[] nums3 = {-1, 2};
        int[] nums4 = {0, 2};

        FourSumII sol = new FourSumII();
        int count = sol.fourSumCount(nums1, nums2, nums3, nums4);

        System.out.println("總共找到的組合數量: " + count);
    }
}
