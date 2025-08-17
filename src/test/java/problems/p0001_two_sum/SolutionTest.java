package problems.p0001_two_sum;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SolutionTest {
    @Test
    void sample() {
        var s = new Solution();
        assertArrayEquals(new int[]{0,1}, s.twoSum(new int[]{2,7,11,15}, 9));
        assertArrayEquals(new int[]{1,2}, s.twoSum(new int[]{3,2,4}, 6));
    }
}
