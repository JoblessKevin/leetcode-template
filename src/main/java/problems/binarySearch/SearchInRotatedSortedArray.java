package problems.binarySearch;

public class SearchInRotatedSortedArray {
    class BinarySearch {
        public int search(int[] nums, int target) {
            int l = 0, r = nums.length - 1;

            while (l <= r) {
                int mid = l + (r - l) / 2;

                if (nums[mid] == target) {
                    return mid;
                } else if (nums[mid] >= nums[l]) {
                    if (target >= nums[l] && target < nums[mid]) {
                        r = mid - 1;
                    } else {
                        l = mid + 1;
                    }
                } else {
                    if (target > nums[mid] && target <= nums[r]) {
                        l = mid + 1;
                    } else {
                        r = mid - 1;
                    }
                }
            }
            return -1;
        }
    }

    public static void main(String[] args) {
        SearchInRotatedSortedArray solution = new SearchInRotatedSortedArray();
        int[] nums = {4, 5, 6, 7, 0, 1, 2};
        int target = 0;
        int index = solution.new BinarySearch().search(nums, target);
        System.out.println("The index of the target " + target + " in the rotated sorted array is: "
                                        + index);
    }
}
