package problems.math;

public class Sqrt {
    public int mySqrt(int x) {
        if (x == 0)
            return 0;

        int left = 1;
        int right = x;
        int ans = 0;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            // 檢查 mid * mid 是否溢位
            // 方法 1: 使用 long
            // long square = (long)mid * mid;
            // if (square <= x) {

            // 方法 2: 避免溢位 (推薦)
            if (mid <= x / mid) {
                ans = mid; // mid 可能是答案，先存起來
                left = mid + 1; // 往右邊找更大的可能答案
            } else {
                right = mid - 1; // mid 太大了，往左邊找
            }
        }
        return ans;
    }

    // -------------Newton's Method--------------
    public int mySqrt_newton(int x) {
        if (x == 0)
            return 0;

        // 使用 long 避免溢位
        long r = x;

        // 當 r*r 比 x 大很多時，r 會快速下降
        // 當 r*r 接近 x 時，r 會慢慢逼近真實答案
        while (r * r > x) {
            r = (r + x / r) / 2;
        }

        return (int) r;
    }

    // @formatter:off
    public static void main(String[] args) {
        Sqrt solver = new Sqrt();

        // Test Case 1: 基本情況
        int x1 = 4;
        System.out.println("sqrt(" + x1 + ") = " + solver.mySqrt(x1));
        // Expected: 2

        System.out.println("--------------------------");

        // Test Case 2: 包含小數部分 (取整)
        int x2 = 8;
        System.out.println("sqrt(" + x2 + ") = " + solver.mySqrt(x2));
        // Expected: 2 (因為 2*2=4, 3*3=9)

        System.out.println("--------------------------");

        // Test Case 3: 較大數字
        int x3 = 2147395599;
        System.out.println("sqrt(" + x3 + ") = " + solver.mySqrt(x3));
        // Expected: 46339

        System.out.println("--------------------------");

        // Test Case 4: 邊界情況 (0)
        int x4 = 0;
        System.out.println("sqrt(" + x4 + ") = " + solver.mySqrt(x4));
        // Expected: 0
    }
}
