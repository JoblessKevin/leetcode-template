package problems.greedy;

/**
 * 因為這題有萬用符號'*'，所以不能用 Stack 去解，必須用 Greedy 去解
 */
public class ValidParenthesisString {
    public boolean checkValidString(String s) {
        int low = 0; // 記錄必須是 '(' 的最小數量
        int high = 0; // 記錄最多可以是 '(' 的最大數量

        for (char ch : s.toCharArray()) {
            if (ch == '(') {
                low++;
                high++;
            } else if (ch == ')') {
                low--;
                high--;
            } else { // ch == '*'
                low--; // '*' 當作 ')'，讓下限變小
                high++; // '*' 當作 '(', 讓上限變大
            }

            // 如果 high < 0，表示 '(' 數量遠遠不夠，直接失敗
            if (high < 0)
                return false;

            // low 不能是負數，因為 '(' 數量不可能小於 0
            // 如果算出來是負的，就把它重置為 0 (代表之前的 '*' 都當作空字串)
            low = Math.max(low, 0);
        }

        // 最後，如果 low 等於 0，表示我們有辦法把所有 '(' 都配對掉
        return low == 0;
    }

    public static void main(String[] args) {
        ValidParenthesisString solver = new ValidParenthesisString();

        String s1 = "()";
        System.out.println("Test Case 1: " + s1);
        System.out.println("Result: " + solver.checkValidString(s1)); // 應為 true

        String s2 = "(*)";
        System.out.println("Test Case 2: " + s2);
        System.out.println("Result: " + solver.checkValidString(s2)); // 應為 true

        String s3 = "(*)))";
        System.out.println("Test Case 3: " + s3);
        System.out.println("Result: " + solver.checkValidString(s3)); // 應為 true
    }
}
