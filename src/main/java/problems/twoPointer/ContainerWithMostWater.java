package problems.twoPointer;

/**
 * LeetCode 11: Container With Most Water
 * O(n) two-pointer solution with a small optimization:
 * skip over consecutive bars that are not taller than the current side.
 */
public class ContainerWithMostWater {
    public int maxArea(int[] height) {
        if (height == null || height.length < 2) return 0;

        int l = 0, r = height.length - 1;
        int result = 0;

        while (l < r) {
            int left = height[l], right = height[r];
            int width = r - l;

            if (left < right) {
                result = Math.max(result, left * width);
                while (l < r && height[l] <= left) l++;
            } else {
                result = Math.max(result, right * width);
                while (l < r && height[r] <= right) r--;
            }
        }

        return result;
    }

    public static void main(String[] args) {
        ContainerWithMostWater sol = new ContainerWithMostWater();

        int[][] testCases = {
                {1, 1},                         // 1
                {1, 8, 6, 2, 5, 4, 8, 3, 7},    // 49
                {2, 3, 4, 5, 18, 17, 6},        // 17
                {2, 2, 2, 2, 2},                // 8
                {1, 2, 1}                       // 2
        };

        for (int[] heights : testCases) {
            int result = sol.maxArea(heights);
            System.out.print("Input: [");
            for (int i = 0; i < heights.length; i++) {
                System.out.print(heights[i]);
                if (i < heights.length - 1) System.out.print(", ");
            }
            System.out.println("] => Max Area = " + result);
        }
    }
}
