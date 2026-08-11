package problems.binarySearch;

public class BinarySearch {
    public int search(int[] nums, int target) {
        int l = 0;
        int r = nums.length - 1;

        while (l <= r) {
            int mid = l + (r - l) / 2;

            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < target) {
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }

        return -1;
    }

    public static void main(String[] args) {
        BinarySearch solution = new BinarySearch();
        int[] nums = {-1, 0, 3, 5, 9, 12};
        int target = 9;
        int result = solution.search(nums, target);
        System.out.println(result); // Output: 4
    }
}
