package problems.greedy;

public class JumpGameII {
    public int jump(int[] nums) {
        int jumps = 0;
        int currentEnd = 0;
        int farthest = 0;

        for (int i = 0; i < nums.length - 1; i++) {
            farthest = Math.max(farthest, i + nums[i]);

            if (i == currentEnd) {
                jumps++;
                currentEnd = farthest;

                if (currentEnd >= nums.length - 1)
                    break;
            }
        }

        return jumps;
    }

    public int jump_slidingWindow(int[] nums) {
        int res = 0, l = 0, r = 0;

        while (r < nums.length - 1) {
            int farthest = 0;
            for (int i = l; i <= r; i++) {
                farthest = Math.max(farthest, i + nums[i]);
            }
            l = r + 1;
            r = farthest;
            res++;
        }
        return res;
    }

    public static void main(String[] args) {
        JumpGameII solver = new JumpGameII();

        int[] nums1 = {2, 3, 1, 1, 4};
        System.out.println("Test Case 1: " + java.util.Arrays.toString(nums1));
        System.out.println("Result: " + solver.jump(nums1)); // 應為 2

        int[] nums2 = {2, 3, 0, 1, 4};
        System.out.println("Test Case 2: " + java.util.Arrays.toString(nums2));
        System.out.println("Result: " + solver.jump(nums2)); // 應為 2
    }
}
