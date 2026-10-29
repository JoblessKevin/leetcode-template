package problems.math.string;

/**
 * 後端開發中處理「超大數（超出 Long 範圍）」的標準做法
 */
public class AddStrings {
    public String addStrings(String num1, String num2) {
        StringBuilder sb = new StringBuilder();
        int i = num1.length() - 1, j = num2.length() - 1, carry = 0;

        while (i >= 0 || j >= 0 || carry != 0) {
            int n1 = i >= 0 ? num1.charAt(i--) - '0' : 0;
            int n2 = j >= 0 ? num2.charAt(j--) - '0' : 0;

            int sum = n1 + n2 + carry;
            sb.append(sum % 10);
            carry = sum / 10;
        }
        return sb.reverse().toString();
    }

    // @formatter:off
    public static void main(String[] args) {
        AddStrings solver = new AddStrings();

        // Test Case 1: 基本情況
        String num1_1 = "123";
        String num2_1 = "456";
        System.out.println(num1_1 + " + " + num2_1 + " = " + solver.addStrings(num1_1, num2_1));
        // Expected: "579"

        System.out.println("--------------------------");

        // Test Case 2: 包含進位
        String num1_2 = "456";
        String num2_2 = "789";
        System.out.println(num1_2 + " + " + num2_2 + " = " + solver.addStrings(num1_2, num2_2));
        // Expected: "1245"

        System.out.println("--------------------------");

        // Test Case 3: 長度不同
        String num1_3 = "999";
        String num2_3 = "1";
        System.out.println(num1_3 + " + " + num2_3 + " = " + solver.addStrings(num1_3, num2_3));
        // Expected: "1000"

        System.out.println("--------------------------");

        // Test Case 4: 包含 0
        String num1_4 = "123";
        String num2_4 = "0";
        System.out.println(num1_4 + " + " + num2_4 + " = " + solver.addStrings(num1_4, num2_4));
        // Expected: "123"
    }
}
