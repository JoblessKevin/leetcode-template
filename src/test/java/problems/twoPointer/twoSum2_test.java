package problems.twoPointer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class twoSum2_test {
    @Test
    void sample() {
        var s = new twoSum2();
        // Example 1
        assertArrayEquals(new int[]{1,2}, s.twoSum(new int[]{2,7,11,15}, 9));
        // Example 2
        assertArrayEquals(new int[]{2,3}, s.twoSum(new int[]{2,3,4}, 6));
        // Example 3
        assertArrayEquals(new int[]{1,2}, s.twoSum(new int[]{-1,0}, -1));
    }
}
