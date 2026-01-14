package problems.backtracking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Subsets {
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();

        backtrack(result, new ArrayList<>(), nums, 0);
        return result;
    }

    private void backtrack(List<List<Integer>> result, List<Integer> tempList, int[] nums,
                                    int start) {
        result.add(new ArrayList<>(tempList));

        for (int i = start; i < nums.length; i++) {
            tempList.add(nums[i]);

            backtrack(result, tempList, nums, i + 1);

            tempList.remove(tempList.size() - 1);
        }
    }

    public static void main(String[] args) {
        Subsets solution = new Subsets();

        // 測試案例 1: 標準題目範例 [1, 2, 3]
        // 預期結果個數: 2^3 = 8 個子集
        int[] input1 = {1, 2, 3};
        System.out.println("=== Test Case 1 ===");
        System.out.println("Input:  " + Arrays.toString(input1));
        List<List<Integer>> output1 = solution.subsets(input1);
        System.out.println("Output: " + output1);
        System.out.println("Total subsets: " + output1.size()); // 驗證數量
        System.out.println();

        // 測試案例 2: 單一元素 [0]
        // 預期結果個數: 2^1 = 2 個子集 ([], [0])
        int[] input2 = {0};
        System.out.println("=== Test Case 2 ===");
        System.out.println("Input:  " + Arrays.toString(input2));
        List<List<Integer>> output2 = solution.subsets(input2);
        System.out.println("Output: " + output2);
        System.out.println("Total subsets: " + output2.size());
        System.out.println();

        // 測試案例 3: 兩個元素 [10, 20]
        // 預期結果個數: 2^2 = 4 個子集
        int[] input3 = {10, 20};
        System.out.println("=== Test Case 3 ===");
        System.out.println("Input:  " + Arrays.toString(input3));
        List<List<Integer>> output3 = solution.subsets(input3);
        System.out.println("Output: " + output3);
        System.out.println("Total subsets: " + output3.size());
    }
}
