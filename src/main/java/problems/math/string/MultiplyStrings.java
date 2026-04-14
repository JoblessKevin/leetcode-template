package problems.math.string;

/**
 * 後端開發中處理「超大數（超出 Long 範圍）」的標準做法
 */
public class MultiplyStrings {
    public String multiply(String num1, String num2) {
        // 1. 處理邊界情況：任一為 "0" 則結果必為 "0"
        if (num1.equals("0") || num2.equals("0")) {
            return "0";
        }

        int m = num1.length();
        int n = num2.length();
        // 2. 建立結果陣列：最大長度為 m + n (例如 99 * 99 = 9801，長度為 4)
        int[] pos = new int[m + n];

        // 3. 核心邏輯：模擬直式乘法
        // 外層迴圈：從 num1 的個位數開始 (i = m-1)
        for (int i = m - 1; i >= 0; i--) {
            // 內層迴圈：從 num2 的個位數開始 (j = n-1)
            for (int j = n - 1; j >= 0; j--) {
                // 取得數字值
                int mul = (num1.charAt(i) - '0') * (num2.charAt(j) - '0');

                // 確定結果陣列中的位置
                // p1 是高位 (百位、千位...)
                // p2 是低位 (個位、十位...)
                int p1 = i + j;
                int p2 = i + j + 1;

                // 進行加法與進位處理
                int sum = mul + pos[p2]; // 加上原本 p2 的值 (可能是前一輪的進位)

                pos[p2] = sum % 10; // p2 只保留個位數
                pos[p1] += sum / 10; // p1 加上進位
            }
        }

        // 4. 構建字串結果
        StringBuilder sb = new StringBuilder();
        for (int p : pos) {
            // 跳過開頭的 0 (例如 00123 -> 123)
            if (sb.length() == 0 && p == 0) {
                continue;
            }
            sb.append(p);
        }

        return sb.toString();
    }

    // @formatter:off
    public static void main(String[] args) {
        MultiplyStrings solver = new MultiplyStrings();

        // Test Case 1: 基本情況
        String num1_1 = "2";
        String num2_1 = "3";
        System.out.println(num1_1 + " * " + num2_1 + " = " + solver.multiply(num1_1, num2_1));
        // Expected: "6"

        System.out.println("--------------------------");

        // Test Case 2: 包含進位
        String num1_2 = "123";
        String num2_2 = "456";
        System.out.println(num1_2 + " * " + num2_2 + " = " + solver.multiply(num1_2, num2_2));
        // Expected: "56088"

        System.out.println("--------------------------");

        // Test Case 3: 包含 0
        String num1_3 = "999";
        String num2_3 = "0";
        System.out.println(num1_3 + " * " + num2_3 + " = " + solver.multiply(num1_3, num2_3));
        // Expected: "0"

        System.out.println("--------------------------");

        // Test Case 4: 較長數字
        String num1_4 = "9133";
        String num2_4 = "0";
        System.out.println(num1_4 + " * " + num2_4 + " = " + solver.multiply(num1_4, num2_4));
        // Expected: "0"
    }
}