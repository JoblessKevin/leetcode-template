package problems.backtracking;

import java.util.ArrayList;
import java.util.List;

public class Permutations {
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        backtrack(result, nums, 0);
        return result;
    }

    public void backtrack(List<List<Integer>> result, int[] nums, int idx) {
        if (idx == nums.length) {
            List<Integer> tempList = new ArrayList<>();
            for (int num : nums)
                tempList.add(num);
            result.add(tempList);
            return;
        }
        for (int i = idx; i < nums.length; i++) {
            swap(nums, idx, i);
            backtrack(result, nums, idx + 1);
            swap(nums, idx, i);
        }
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    // public List<List<Integer>> permute(int[] nums) {
    // List<List<Integer>> result = new ArrayList<>();
    // backtrack(result, new ArrayList<>(), nums, new boolean[nums.length]);
    // return result;
    // }

    // public void backtrack(List<List<Integer>> result, List<Integer> tempList, int[] nums,
    // boolean[] pick) {
    // if (tempList.size() == nums.length) {
    // result.add(new ArrayList<>(tempList));
    // return;
    // }
    // for (int i = 0; i < nums.length; i++) {
    // if (!pick[i]) {
    // tempList.add(nums[i]);
    // pick[i] = true;
    // backtrack(result, tempList, nums, pick);
    // tempList.remove(tempList.size() - 1);
    // pick[i] = false;
    // }
    // }
    // }
}
