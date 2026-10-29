package problems.bit;

public class BitSumTwoOfIntegers {
    public int getSum(int a, int b) {
        while (b != 0) {
            int carry = (a & b) << 1;

            a = a ^ b;
            b = carry;
        }

        return a;
    }

    public int getSum_recursive(int a, int b) {
        // 當沒有進位時，結果就是 a
        return (b == 0) ? a : getSum(a ^ b, (a & b) << 1);
    }

    public static void main(String[] args) {
        BitSumTwoOfIntegers solver = new BitSumTwoOfIntegers();

        // 測試案例
        int a = 1;
        int b = 2;
        System.out.println("測試輸入: a = " + a + ", b = " + b);
        System.out.println("期待結果: 3");
        System.out.println("-------------------------------------");

        // 測試
        System.out.println("方法 1 (Bitwise) 結果: " + solver.getSum(a, b));

        System.out.println("-------------------------------------");
        System.out.println("驗證完成。");
    }
}
