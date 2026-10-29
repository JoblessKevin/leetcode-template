package problems.backtracking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PermutationsII {

    public List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums);
        backtrack(nums, 0, result);

        return result;
    }

    private void backtrack(int[] nums, int start, List<List<Integer>> result) {
        if (start == nums.length) {
            List<Integer> current = new ArrayList<>(nums.length);
            for (int num : nums) {
                current.add(num);
            }
            result.add(current);

            return;
        }

        int seen = 0;

        for (int i = start; i < nums.length; i++) {
            int bitIndex = nums[i] + 10;

            if ((seen & (1 << bitIndex)) != 0) {
                continue;
            }

            seen |= (1 << bitIndex);

            swap(nums, start, i);
            backtrack(nums, start + 1, result);
            swap(nums, start, i);
        }
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    /**
     * @formatter:off
     * 
    public List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();

        Arrays.sort(nums);

        boolean[] used = new boolean[nums.length];

        backtrack(result, new ArrayList<>(), nums, used);
        return result;
    }

    private void backtrack(List<List<Integer>> result, List<Integer> tempList, int[] nums,
                                    boolean[] used) {

        if (tempList.size() == nums.length) {
            result.add(new ArrayList<>(tempList));
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            if (used[i]) {
                continue;
            }

            if (i > 0 && nums[i] == nums[i - 1] && !used[i - 1]) {
                continue;
            }

            used[i] = true;
            tempList.add(nums[i]);

            backtrack(result, tempList, nums, used);

            used[i] = false;
            tempList.remove(tempList.size() - 1);
        }
    }

    * @formatter:on
     */
}
