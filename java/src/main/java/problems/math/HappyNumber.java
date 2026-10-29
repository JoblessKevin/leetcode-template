package problems.math;

/**
 * 當一個系統的狀態是有限且確定的（Deterministic），它最終一定會產生循環。 Brent 比 Floyd 快，因為呼叫比較少次 sumOfSquares
 */
public class HappyNumber {
    // -------------Floyd's Cycle-Finding Algorithm---------------

    public boolean isHappy_floyd(int n) {
        int slow = n, fast = n;
        do {
            slow = sumOfSquares(slow);
            fast = sumOfSquares(sumOfSquares(fast));
        } while (slow != fast);
        return slow == 1;
    }

    // -------------Brent's Cycle-Finding Algorithm---------------

    public boolean isHappy_brent(int n) {
        int slow = n, fast = sumOfSquares(n);
        int power = 1, lam = 1;
        while (slow != fast) {
            if (power == lam) {
                slow = fast;
                power *= 2;
                lam = 0;
            }
            lam++;
            fast = sumOfSquares(fast);
        }

        return fast == 1;
    }

    // -------------Common function for both algorithms---------------

    private int sumOfSquares(int n) {
        int output = 0;
        while (n != 0) {
            output += (n % 10) * (n % 10);
            n /= 10;
        }
        return output;
    }

    // -------------Test Cases---------------
    // @formatter:off
    public static void main(String[] args) {
        HappyNumber solver = new HappyNumber();

        // Test Case 1: 快樂數
        int n1 = 19;
        System.out.println("[Floyd] Is " + n1 + " a happy number? " + solver.isHappy_floyd(n1));
        System.out.println("[Brent] Is " + n1 + " a happy number? " + solver.isHappy_brent(n1));
        // Expected: true
        // 1^2 + 9^2 = 82
        // 8^2 + 2^2 = 68
        // 6^2 + 8^2 = 100
        // 1^2 + 0^2 + 0^2 = 1

        System.out.println("--------------------------");

        // Test Case 2: 非快樂數 (陷入循環)
        int n2 = 2;
        System.out.println("[Floyd] Is " + n2 + " a happy number? " + solver.isHappy_floyd(n2));
        System.out.println("[Brent] Is " + n2 + " a happy number? " + solver.isHappy_brent(n2));
        // Expected: false
        // 2^2 = 4
        // 4^2 = 16
        // 1^2 + 6^2 = 37
        // 3^2 + 7^2 = 58
        // 5^2 + 8^2 = 89
        // 8^2 + 9^2 = 145
        // 1^2 + 4^2 + 5^2 = 42
        // 4^2 + 2^2 = 20
        // 2^2 + 0^2 = 4 (循環開始)
    }
}
