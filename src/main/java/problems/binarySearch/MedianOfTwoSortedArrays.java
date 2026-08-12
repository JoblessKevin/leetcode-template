package problems.binarySearch;

public class MedianOfTwoSortedArrays {
    class BinarySearch {
        public double findMedianSortedArrays(int[] nums1, int[] nums2) {
            if (nums1.length > nums2.length) {
                return findMedianSortedArrays(nums2, nums1);
            }

            int x = nums1.length;
            int y = nums2.length;
            int l = 0;
            int r = x;

            while (l <= r) {
                int midX = l + (r - l) / 2;
                int midY = (x + y + 1) / 2 - midX;

                int maxLeftX = (midX == 0) ? Integer.MIN_VALUE : nums1[midX - 1];
                int minRightX = (midX == x) ? Integer.MAX_VALUE : nums1[midX];

                int maxLeftY = (midY == 0) ? Integer.MIN_VALUE : nums2[midY - 1];
                int minRightY = (midY == y) ? Integer.MAX_VALUE : nums2[midY];

                if (maxLeftX <= minRightY && maxLeftY <= minRightX) {
                    if ((x + y) % 2 == 0) {
                        return ((double) Math.max(maxLeftX, maxLeftY)
                                                        + Math.min(minRightX, minRightY)) / 2;
                    } else {
                        return (double) Math.max(maxLeftX, maxLeftY);
                    }
                } else if (maxLeftX > minRightY) {
                    r = midX - 1;
                } else {
                    l = midX + 1;
                }
            }

            throw new IllegalArgumentException("Input arrays are not sorted.");
        }
    }

    public static void main(String[] args) {
        MedianOfTwoSortedArrays solution = new MedianOfTwoSortedArrays();
        BinarySearch binarySearch = solution.new BinarySearch();

        int[] nums1 = {1, 3};
        int[] nums2 = {2};
        double result = binarySearch.findMedianSortedArrays(nums1, nums2);
        System.out.println(result); // Output: 2.0
    }
}

