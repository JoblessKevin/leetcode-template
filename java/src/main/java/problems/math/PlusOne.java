package problems.math;

public class PlusOne {
    public int[] plusOne(int[] digits) {
        int n = digits.length;

        // 1. 從最右邊（個位數）開始檢查
        for (int i = n - 1; i >= 0; i--) {
            // 如果個位數 < 9，直接加 1，結束
            if (digits[i] < 9) {
                digits[i]++;
                return digits;
            }

            // 如果是 9，變為 0，繼續往左進位
            digits[i] = 0;
        }

        // 2. 如果迴圈跑完了（代表所有位數都是 9，例如 [9, 9, 9]）
        // 需要建立一個新的陣列，長度 +1，首位為 1，其餘為 0
        int[] newDigits = new int[n + 1];
        newDigits[0] = 1;
        // newDigits[1...n] 預設就是 0，符合 [1, 0, 0, 0]

        return newDigits;
    }

    // @formatter:off
    public static void main(String[] args) {
        PlusOne solver = new PlusOne();

        // Test Case 1: 基本情況
        int[] digits1 = {1, 2, 3};
        System.out.println("Original Digits 1:");
        printArray(digits1);
        int[] result1 = solver.plusOne(digits1);
        System.out.println("Modified Digits 1:");
        printArray(result1);
        // Expected: [1, 2, 4]

        System.out.println("--------------------------");

        // Test Case 2: 進位情況
        int[] digits2 = {4, 3, 2, 9};
        System.out.println("Original Digits 2:");
        printArray(digits2);
        int[] result2 = solver.plusOne(digits2);
        System.out.println("Modified Digits 2:");
        printArray(result2);
        // Expected: [4, 3, 3, 0]

        System.out.println("--------------------------");

        // Test Case 3: 全為 9 的情況
        int[] digits3 = {9, 9, 9};
        System.out.println("Original Digits 3:");
        printArray(digits3);
        int[] result3 = solver.plusOne(digits3);
        System.out.println("Modified Digits 3:");
        printArray(result3);
        // Expected: [1, 0, 0, 0]
    }

    // Helper function to print array
    public static void printArray(int[] arr) {
        System.out.print("[");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
            if (i < arr.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }
}
