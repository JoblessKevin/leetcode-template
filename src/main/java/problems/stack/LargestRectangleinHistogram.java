package problems.stack;

import java.util.ArrayDeque;
import java.util.Deque;

public class LargestRectangleinHistogram {
    /** Brute Force */
    class BruteForce {
        public int largestRectangleArea(int[] heights) {

            int area = 0;

            for (int i = 0; i < heights.length; i++) {
                int minHeight = Integer.MAX_VALUE;

                for (int j = i; j < heights.length; j++) {
                    minHeight = Math.min(minHeight, heights[j]);
                    area = Math.max(area, minHeight * (j - i + 1));
                }
            }

            return area;
        }
    }


    /** Divide and Conquer */
    class DivideConquer {
        public int largestRectangleArea(int[] heights) {
            return dac(heights, 0, heights.length - 1);
        }

        public int dac(int[] heights, int l, int r) {
            if (l > r)
                return 0;

            int minIndex = l;
            for (int i = l; i <= r; i++) {
                if (heights[i] < heights[minIndex]) {
                    minIndex = i;
                }
            }

            int areaWithMin = heights[minIndex] * (r - l + 1);
            int areaLeft = dac(heights, l, minIndex - 1);
            int areaRight = dac(heights, minIndex + 1, r);

            return Math.max(areaWithMin, Math.max(areaLeft, areaRight));
        }
    }

    /** Stack */
    class Stack {
        public int largestRectangleArea(int[] heights) {
            int n = heights.length;
            int maxArea = 0;
            Deque<Integer> stack = new ArrayDeque<>();

            for (int i = 0; i <= n; i++) {
                while (!stack.isEmpty() && (i == n || heights[stack.peek()] >= heights[i])) {
                    int height = heights[stack.pop()];
                    int width = stack.isEmpty() ? i : i - stack.peek() - 1;
                    maxArea = Math.max(maxArea, height * width);
                }
                stack.push(i);
            }
            return maxArea;
        }
    }

    public static void main(String[] args) {
        LargestRectangleinHistogram obj = new LargestRectangleinHistogram();
        // int[] heights = {2, 1, 5, 6, 2, 3};
        int[] heights = {7, 1, 7, 2, 2, 4};
        System.out.println(obj.new BruteForce().largestRectangleArea(heights));
        System.out.println(obj.new Stack().largestRectangleArea(heights));
        System.out.println(obj.new DivideConquer().largestRectangleArea(heights));
    }
}
