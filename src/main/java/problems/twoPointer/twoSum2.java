package problems.twoPointer;

import java.util.Arrays;

public class TwoSum2 {
    public int[] twoSum(int[] numbers, int target) {
        int n = numbers.length;
        for (int i = 0; i < n; i++) {
            int want = target - numbers[i];
            int j = Arrays.binarySearch(numbers, i + 1, n, want);
            if (j >= 0) return new int[] {i + 1, j + 1};
        }

        return null;
    }

    // Test Data
    public static void main(String[] args) {
        TwoSum2 s = new TwoSum2();

        // Test 1
        int[] ans1 = s.twoSum(new int[]{2,7,11,15}, 9);
        System.out.println("Test 1: " + Arrays.toString(ans1)); // [1, 2]

        // Test 2
        int[] ans2 = s.twoSum(new int[]{2,3,4}, 6);
        System.out.println("Test 2: " + Arrays.toString(ans2)); // [1, 3]

        // Test 3
        int[] ans3 = s.twoSum(new int[]{-1,0}, -1);
        System.out.println("Test 3: " + Arrays.toString(ans3)); // [1, 2]
    }
}
