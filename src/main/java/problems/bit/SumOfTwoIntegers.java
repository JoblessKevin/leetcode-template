package problems.bit;

public class SumOfTwoIntegers {
    public int getSum(int a, int b) {
        while (b != 0) {
            int carry = (a & b) << 1;
            a = a ^ b;
            b = carry;
        }

        return a;
    }

    public static void main(String[] args) {
        SumOfTwoIntegers s = new SumOfTwoIntegers();

        // Test case 1: positive number
        System.out.println("2 + 3 = " + s.getSum(2, 3)); // 5

        // Test case 2: negative number
        System.out.println("1 + (-2) = " + s.getSum(1, -2)); // -1

        // Test case 3: Zero addition
        System.out.println("0 + 0 = " + s.getSum(0, 0)); // 0

        // Test case 4: Large numbers
        System.out.println("50 + 70 = " + s.getSum(50, 70)); // 120
    }
}
