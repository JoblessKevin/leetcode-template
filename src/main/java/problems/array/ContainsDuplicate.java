package problems.array;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ContainsDuplicate {
    public boolean containsDuplicate(int[] nums) {
        Set<Integer> seen = new HashSet<>();
        for (int num : nums) {
            if (seen.contains(num)) {
                return true;
            }
            seen.add(num);
        }

        return false;
    }

    /**
     * 如果不能使用額外空間，就要排序
     */
    public boolean containsDuplicate_optimized(int[] nums) {
        Arrays.sort(nums);
        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i] == nums[i + 1]) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        ContainsDuplicate solver = new ContainsDuplicate();

        // 測試案例
        int[] testInput = {1, 2, 3, 1};
        System.out.println("測試輸入陣列: " + Arrays.toString(testInput));
        System.out.println("期待結果: true");
        System.out.println("-------------------------------------");

        // 測試
        boolean result = solver.containsDuplicate(testInput);
        System.out.println("方法 1 (HashSet) 結果: " + result);

        System.out.println("-------------------------------------");
        System.out.println("驗證完成。");
    }
}
