package problems.binarySearch;

public class SplitArrayLargestSum {
    class BinarySearch {
        public int splitArray(int[] nums, int k) {
            int l = 0, r = 0;
            for (int num : nums) {
                l = Math.max(l, num);
                r += num;
            }

            while (l < r) {
                int mid = l + (r - l) / 2;
                if (canSplit(nums, k, mid)) {
                    r = mid;
                } else {
                    l = mid + 1;
                }
            }

            return l;
        }

        private boolean canSplit(int[] nums, int k, int largestSum) {
            int splits = 1;
            int currentSum = 0;

            for (int num : nums) {
                currentSum += num;
                if (currentSum > largestSum) {
                    splits++;
                    currentSum = num;
                }
            }

            return splits <= k;
        }
    }

    public static void main(String[] args) {
        SplitArrayLargestSum solution = new SplitArrayLargestSum();
        BinarySearch binarySearch = solution.new BinarySearch();

        int[] nums = {7, 2, 5, 10, 8};
        int k = 2;
        int result = binarySearch.splitArray(nums, k);
        System.out.println(result); // Output: 18
    }
}
