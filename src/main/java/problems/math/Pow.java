package problems.math;

/**
 * Binary Exponentiation
 */
public class Pow {
    // -------------Binary Exponentiation(Iterative)---------------
    public double myPow(double x, int n) {
        // 1. 處理 n 為負數的情況，並用 long 避免 Integer.MIN_VALUE 溢位
        long N = n;
        if (N < 0) {
            x = 1 / x;
            N = -N;
        }

        double result = 1.0;
        double currentProduct = x;

        // 2. 快速冪核心邏輯
        while (N > 0) {
            // 如果目前的 N 是奇數，就多乘一次底數
            if (N % 2 == 1) {
                result = result * currentProduct;
            }

            // 底數平方
            currentProduct = currentProduct * currentProduct;

            // 指數砍半
            N = N / 2;
        }

        return result;
    }

    // -------------Binary Exponentiation(Recursive)---------------
    public double myPow_recursive(double x, int n) {
        long N = n; // 照慣例，先用 long 處理 Integer.MIN_VALUE 的溢位
        if (N < 0) {
            x = 1 / x;
            N = -N;
        }
        return fastPow(x, N);
    }

    private double fastPow(double x, long n) {
        // 基底情況 (Base Case)：任何數的 0 次方都是 1
        if (n == 0)
            return 1.0;

        // 【折疊核心】：先遞迴計算一半的結果
        double half = fastPow(x, n / 2);

        // 如果 n 是偶數：x^n = (x^{n/2}) * (x^{n/2})
        if (n % 2 == 0) {
            return half * half;
        }
        // 如果 n 是奇數：x^n = (x^{n/2}) * (x^{n/2}) * x
        else {
            return half * half * x;
        }
    }

    // -------------Test Cases---------------
    // @formatter:off
    public static void main(String[] args) {
        Pow solver = new Pow();

        // Test Case 1: 基本情況
        double x1 = 2.0;
        int n1 = 10;
        System.out.println(x1 + "^" + n1 + " = " + solver.myPow(x1, n1));
        // Expected: 1024.0

        System.out.println("--------------------------");

        // Test Case 2: 負指數
        double x2 = 2.0;
        int n2 = -2;
        System.out.println(x2 + "^" + n2 + " = " + solver.myPow(x2, n2));
        // Expected: 0.25

        System.out.println("--------------------------");

        // Test Case 3: 複雜情況 (包含 0.5 的平方)
        double x3 = 0.5;
        int n3 = -2;
        System.out.println(x3 + "^" + n3 + " = " + solver.myPow(x3, n3));
        // Expected: 4.0

        System.out.println("--------------------------");

        // Test Case 4: 邊界情況 (Integer.MIN_VALUE)
        double x4 = 2.0;
        int n4 = Integer.MIN_VALUE;
        System.out.println(x4 + "^" + n4 + " = " + solver.myPow(x4, n4));
        // Expected: 0.0 (非常接近 0)
    }
}
