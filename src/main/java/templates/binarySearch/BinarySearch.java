package templates.binarySearch;

/** lower_bound: return the first index >= target; or nums.length if none. */
public class BinarySearch {
    public static int lowerBound(int[] nums, int target) {
        int l = 0, r = nums.length;
        while (l < r) {
            int m = l + ((r - l) >>> 1);
            if (nums[m] < target) l = m + 1;
            else r = m;
        }
        return l;
    }

    /** classic binary search; return index or -1 */
    public static int binarySearch(int[] nums, int target) {
        int l = 0, r = nums.length - 1;
        while (l <= r) {
            int m = l + ((r - l) >>> 1);
            if (nums[m] == target) return m;
            if (nums[m] < target) l = m + 1;
            else r = m - 1;
        }
        return -1;
    }
}
