package problems.greedy;

public class JumpGame {
    public boolean canJump(int[] nums) {
        int farthest = 0;

        for (int i = 0; i < nums.length; i++) {
            if (i > farthest)
                return false;

            farthest = Math.max(farthest, i + nums[i]);
        }

        return true;
    }

    public boolean canJump_backward(int[] nums) {
        int goal = nums.length - 1;

        for (int i = nums.length - 2; i >= 0; i--) {
            if (i + nums[i] >= goal) {
                goal = i;
            }
        }

        return goal == 0;
    }

    public static void main(String[] args) {
        JumpGame solver = new JumpGame();

        int[] nums1 = {2, 3, 1, 1, 4};
        System.out.println("Test Case 1: " + java.util.Arrays.toString(nums1));
        System.out.println("Result: " + solver.canJump_backward(nums1)); // 應為 true

        int[] nums2 = {3, 2, 1, 0, 4};
        System.out.println("Test Case 2: " + java.util.Arrays.toString(nums2));
        System.out.println("Result: " + solver.canJump_backward(nums2)); // 應為 false
    }
}
