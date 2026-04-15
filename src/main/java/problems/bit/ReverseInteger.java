package problems.bit;

/**
 * 這題通常會要求不能使用 long，因為核心考點"在有限的容器內偵測即將發生的溢位"，等於是繞過去
 */
public class ReverseInteger {
    public int reverse(int x) {
        long res = 0;
        while (x != 0) {
            // 1. 拿取最後一位
            int pop = x % 10;
            // 2. 滾動推入結果
            res = res * 10 + pop;
            // 3. 檢查是否溢位 (超過 32 位元範圍)
            if (res > Integer.MAX_VALUE || res < Integer.MIN_VALUE) {
                return 0;
            }
            // 4. x 縮小
            x /= 10;
        }
        return (int) res;
    }

    public int reverse_int(int x) {
        int res = 0;
        while (x != 0) {
            int pop = x % 10;

            // 核心：在 res * 10 之前檢查
            // 檢查正數溢位
            if (res > Integer.MAX_VALUE / 10 || (res == Integer.MAX_VALUE / 10 && pop > 7)) {
                return 0;
            }
            // 檢查負數溢位
            if (res < Integer.MIN_VALUE / 10 || (res == Integer.MIN_VALUE / 10 && pop < -8)) {
                return 0;
            }

            res = res * 10 + pop;
            x /= 10;
        }

        return res;
    }

    public static void main(String[] args) {
        ReverseInteger solver = new ReverseInteger();

        // 測試案例
        int testInput = 123;
        System.out.println("測試輸入: " + testInput);
        System.out.println("期待結果: 321");
        System.out.println("-------------------------------------");

        // 測試
        System.out.println("方法 1 (Bitwise) 結果: " + solver.reverse(testInput));

        System.out.println("-------------------------------------");
        System.out.println("驗證完成。");
    }
}
