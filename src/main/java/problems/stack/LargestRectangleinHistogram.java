package problems.stack;

public class LargestRectangleinHistogram {



    /** Divide and Conquer*/
    class DivideConquer {
        public int largestRectangleArea(int[] heights) {
            return dac(heights, 0, heights.length - 1);
        }

        public int dac(int[] heights, int l, int r) {
            if (l > r) return 0;

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

    /** Brute Force */
    class BruteForce {
        public int largestRectangleArea(int[] heights) {

            int area = 0;

            for (int i = 0; i < heights.length; i++) {
                int r = i + 1;

                while (r < heights.length && heights[r] >= heights[i]) {
                    r++;
                }

                int l = i;

                while (l >= 0 && heights[l] >= heights[i]) {
                    l--;
                }

                r--;
                l++;

                area = Math.max(area, heights[i] * (r - l + 1));
            }

            return area;
        }
    }
}
