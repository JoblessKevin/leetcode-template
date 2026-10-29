package problems.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MajorityElementII {
    // Time: O(n), Space: O(n)
    public static List<Integer> majorityElement_map(int[] nums) {
        List<Integer> results = new ArrayList<>();
        int threshold = nums.length / 3;
        Map<Integer, Integer> map = new HashMap<>();

        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }

        for (int key : map.keySet()) {
            if (map.get(key) > threshold) {
                results.add(key);
            }
        }

        return results;
    }

    // Boyer-Moore Voting Algorithm, Time: O(n), Space: O(1)
    public static List<Integer> majorityElement(int[] nums) {
        int cand1 = 0, cand2 = 0, count1 = 0, count2 = 0;

        // 第一階段：找出兩位候選人
        for (int num : nums) {
            if (num == cand1) {
                count1++;
            } else if (num == cand2) {
                count2++;
            } else if (count1 == 0) {
                cand1 = num;
                count1 = 1;
            } else if (count2 == 0) {
                cand2 = num;
                count2 = 1;
            } else {
                count1--;
                count2--;
            }
        }

        // 第二階段：驗證候選人是否真的大於 n/3
        List<Integer> results = new ArrayList<>();
        count1 = 0;
        count2 = 0;
        for (int num : nums) {
            if (num == cand1)
                count1++;
            else if (num == cand2)
                count2++;
        }

        if (count1 > nums.length / 3)
            results.add(cand1);
        if (count2 > nums.length / 3)
            results.add(cand2);

        return results;
    }

    public static void main(String[] args) {
        // 測試案例 1：經典範例，1 和 2 出現頻率大於 1/3
        int[] nums1 = {3, 2, 3, 1, 2, 2, 1, 1};
        System.out.println("輸入: " + Arrays.toString(nums1));
        System.out.println("輸出: " + majorityElement(nums1));
        System.out.println("---------------------------");

        // 測試案例 2：只有一個數字超過 1/3
        int[] nums2 = {1, 1, 1, 2, 3};
        System.out.println("輸入: " + Arrays.toString(nums2));
        System.out.println("輸出: " + majorityElement(nums2));
        System.out.println("---------------------------");

        // 測試案例 3：沒有任何數字超過 1/3
        int[] nums3 = {1, 2, 3, 4, 5};
        System.out.println("輸入: " + Arrays.toString(nums3));
        System.out.println("輸出: " + majorityElement(nums3));
    }
}
