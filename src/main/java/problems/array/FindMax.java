package problems.array;

public class FindMax {
    public int findMax(int[] nums) {
        int max = Integer.MIN_VALUE;
        for (int n : nums) max = Math.max(max, n);
        return max;
    }
}
