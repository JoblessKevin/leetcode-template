package problems.twoPointer;

public class RemoveDuplicatesFromSortedArrayII {
    public int removeDuplicates(int[] nums) {
        int l = 1;
        int r = l + 1;

        while (r < nums.length) {
            if (nums[r] != nums[l - 1]) {
                l++;
                nums[l] = nums[r];
            }
            r++;
        }

        return l + 1;
    }
}
