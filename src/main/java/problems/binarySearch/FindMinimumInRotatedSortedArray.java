package problems.binarySearch;

public class FindMinimumInRotatedSortedArray {
    class BinarySearch {
        public int findMin(int[] nums) {
            int l = 0, r = nums.length - 1;

            while (l < r) {
                int mid = l + (r - l) / 2;

                if (nums[mid] < nums[r]) {
                    r = mid;
                } else {
                    l = mid + 1;
                }
            }

            return nums[l];
        }
    }


    public static void main(String[] args) {
        FindMinimumInRotatedSortedArray solution = new FindMinimumInRotatedSortedArray();
        int[] nums = {3, 4, 5, 1, 2};
        int min = solution.new BinarySearch().findMin(nums);
        System.out.println("The minimum element in the rotated sorted array is: " + min);
    }
}
