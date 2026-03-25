package problems.dp;

/**
 * @formatter:off
 * 5. Longest Palindromic Substring
 * 題目描述：
 * 給定一個字串 s，找出 s 中最長的回文子字串 (Palindromic Substring)。
 * 回文定義：正讀反讀都一樣的字串 (例如: "aba", "racecar")。
 * 子字串：連續的字元序列。
 * 範例：
 * Input: s = "babad"
 * Output: "bab" (或 "aba")
 * Input: s = "cbbd"
 * Output: "bb"
 * * 核心概念：
 * 這是一個典型的「區間型動態規劃 (Interval DP)」問題。
 * 我們需要判斷「從索引 i 到 j 的子字串是否為回文」，並利用這個結果推導出更大的區間。
 * * 思考方式：
 * 1. 遞迴關係式：
 * 子字串 s[i...j] 是否為回文？
 * 條件 A：首尾字符必須相同 (s.charAt(i) == s.charAt(j))
 * 條件 B：中間的子字串 s[i+1...j-1] 也必須是回文。
 * 遞迴式：isPalindrome(i, j) = (s.charAt(i) == s.charAt(j)) AND isPalindrome(i+1, j-1)
 * 2. Base Cases (邊界條件)：
 * 單一字符：長度為 1 的字串 (i == j) 永遠是回文。
 * 雙字符：長度為 2 的字串 (j == i+1) 只要兩個字符相同就是回文。
 * 3. 演算法 (Bottom-Up DP)：
 * 我們需要先計算「長度較短」的子字串，才能用來推導「長度較長」的子字串。
 * 因此，我們需要一個巢狀迴圈：
 * 外層迴圈：控制子字串的長度 (length)，從 1 跑遍到 n。
 * 內層迴圈：控制子字串的起始點 (i)，根據長度計算結束點 j = i + length - 1。
 * 4. 空間優化：
 * 雖然標準解法需要 O(N^2) 的二維陣列來記錄 isPalindrome[i][j]，但我們只需要知道「當前長度」和「前一個長度」的結果。然而，由於區間 DP 的依賴性 (i+1, j-1)，直接優化到 O(1) 比較困難，通常使用 O(N^2) 陣列是最直觀的解法。
 * * 時間複雜度：O(N^2)。
 * - 巢狀迴圈會遍歷所有可能的子字串 (大約 N^2/2 個)。
 * - 每次檢查都是常數時間 O(1)。
 * 空間複雜度：O(N^2)。
 * - 用來儲存 isPalindrome[i][j] 的二維陣列。
 * @formatter:on
 */
public class LongestPalindromicSubstring {
    /**
     * @formatter:off
     * Manacher's Algorithm (馬拉車演算法) - 線性時間
     * 核心思想：
     * 1. 預處理：在每個字符之間插入特殊符號（如 #），將所有回文（奇數長度與偶數長度）統一處理為奇數長度回文。
     * 2. 擴展：遍歷新的字串，對每個位置 i 計算其最長回文半徑 p[i]。
     * 3. 優化：利用已計算的結果。如果當前位置 i 在已知最長回文的右邊界 R 內，我們可以利用其對稱位置 mirror 的半徑 p[mirror] 來初始化 p[i]，避免從頭計算。
     * 4. 更新邊界：如果 i 擴展後超過了 R，更新 C（中心）和 R（右邊界）。
     * 5. 提取結果：找到最大的 p[i]，從預處理後的字串還原到原字串。
     * 時間複雜度：O(N)。雖然有巢狀迴圈，但 R 只會單調增加，總擴展次數是線性的。
     * 空間複雜度：O(N)。需要額外空間儲存預處理後的字串和半徑陣列。
     * @formatter:on
     */
    public String longestPalindrome_manacher(String s) {
        // 1. 預處理
        StringBuilder sb = new StringBuilder("#");
        for (char c : s.toCharArray()) {
            sb.append(c).append("#");
        }
        String t = sb.toString();
        int n = t.length();
        int[] p = new int[n];
        int C = 0, R = 0;
        int maxLen = 0, centerIndex = 0;

        for (int i = 0; i < n; i++) {
            int mirror = 2 * C - i;

            // 核心優化：如果 i 在 R 以內，先給它一個初始值
            if (i < R) {
                p[i] = Math.min(R - i, p[mirror]);
            }

            // 嘗試往外擴散 (基於初始值繼續)
            int r = i + (1 + p[i]);
            int l = i - (1 + p[i]);
            while (r < n && l >= 0 && t.charAt(r) == t.charAt(l)) {
                p[i]++;
                r++;
                l--;
            }

            // 如果 i 擴散後的右邊界超過了目前的 R，更新 C 和 R
            if (i + p[i] > R) {
                C = i;
                R = i + p[i];
            }

            // 紀錄最長半徑
            if (p[i] > maxLen) {
                maxLen = p[i];
                centerIndex = i;
            }
        }

        // 從原字串切出結果
        int start = (centerIndex - maxLen) / 2;
        return s.substring(start, start + maxLen);
    }

    /**
     * 動態規劃 (Dynamic Programming) - 中心擴散法
     */
    public String longestPalindrome(String s) {
        if (s == null || s.length() < 1)
            return "";
        int start = 0, end = 0;

        for (int i = 0; i < s.length(); i++) {
            // 情況 1：中心是單個字元 (如 aba)
            int len1 = expandAroundCenter(s, i, i);
            // 情況 2：中心是兩個字元的中間 (如 abba)
            int len2 = expandAroundCenter(s, i, i + 1);

            int len = Math.max(len1, len2);
            // 如果這次擴散出來的長度比紀錄的還要長，更新起點跟終點
            if (len > end - start) {
                start = i - (len - 1) / 2;
                end = i + len / 2;
            }
        }
        return s.substring(start, end + 1);
    }

    private int expandAroundCenter(String s, int left, int right) {
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }
        // 注意：跳出迴圈時，left 和 right 已經是不符合條件的位置了
        // 長度公式為：(right - 1) - (left + 1) + 1 = right - left - 1
        return right - left - 1;
    }

    /**
     * 動態規劃 (Dynamic Programming) - standard
     */
    public String longestPalindrome_standard(String s) {
        if (s == null || s.length() < 1)
            return "";

        int n = s.length();
        // dp[i][j] = true 表示 s[i...j] 是回文
        boolean[][] dp = new boolean[n][n];

        int start = 0; // 最長回文的起始索引
        int maxLength = 1; // 最長回文的長度 (至少單一字符長度為 1)

        // Base Case 1: 所有長度為 1 的子字串都是回文
        for (int i = 0; i < n; i++) {
            dp[i][i] = true;
        }

        // Base Case 2: 檢查長度為 2 的子字串
        for (int i = 0; i < n - 1; i++) {
            if (s.charAt(i) == s.charAt(i + 1)) {
                dp[i][i + 1] = true;
                start = i;
                maxLength = 2;
            }
        }

        // 檢查長度 >= 3 的子字串
        // 必須從長度 3 開始往上推，因為長度 3 的回文需要知道長度 1 的結果
        for (int len = 3; len <= n; len++) {
            // 遍歷所有可能的起始點 i
            for (int i = 0; i <= n - len; i++) {
                // 計算結束點 j
                int j = i + len - 1;

                // 核心判斷：
                // 1. 首尾字符相同 (s.charAt(i) == s.charAt(j))
                // 2. 中間部分是回文 (dp[i+1][j-1] == true)
                if (s.charAt(i) == s.charAt(j) && dp[i + 1][j - 1]) {
                    dp[i][j] = true;

                    // 如果發現更長的回文，更新結果
                    if (len > maxLength) {
                        maxLength = len;
                        start = i;
                    }
                }
            }
        }

        return s.substring(start, start + maxLength);
    }

    /**
     * 暴力解法 (Brute Force) ❌ 缺點：時間複雜度高達 O(N^3)。 想法：列出所有子字串，逐一檢查是否為回文。
     */
    public String longestPalindrome_bruteForce(String s) {
        if (s == null || s.length() < 1)
            return "";

        String longest = "";

        // 1. 遍歷所有可能的起始點 i
        for (int i = 0; i < s.length(); i++) {
            // 2. 遍歷所有可能的結束點 j
            for (int j = i; j < s.length(); j++) {
                // 3. 檢查 s[i...j] 是否為回文
                if (isPalindrome(s, i, j)) {
                    // 如果是回文，且長度比目前找到的還長，就更新
                    if (j - i + 1 > longest.length()) {
                        longest = s.substring(i, j + 1);
                    }
                }
            }
        }
        return longest;
    }

    // 輔助函式：檢查 s[left...right] 是否為回文
    private boolean isPalindrome(String s, int left, int right) {
        while (left < right) {
            if (s.charAt(left) != s.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    public static void main(String[] args) {
        LongestPalindromicSubstring solution = new LongestPalindromicSubstring();

        // --------------------------------------------------
        // 測試案例 1：標準情況
        // 預期："bab" 或 "aba"
        // --------------------------------------------------
        String s1 = "babad";
        System.out.println("Test Case 1:");
        System.out.println("Input: " + s1);
        System.out.println("DP 答案: " + solution.longestPalindrome(s1));
        System.out.println("暴力解: " + solution.longestPalindrome_bruteForce(s1));
        System.out.println("--------------------------------------------------");

        // --------------------------------------------------
        // 測試案例 2：中間是偶數長度回文
        // 預期："bb"
        // --------------------------------------------------
        String s2 = "cbbd";
        System.out.println("Test Case 2:");
        System.out.println("Input: " + s2);
        System.out.println("DP 答案: " + solution.longestPalindrome(s2));
        System.out.println("暴力解: " + solution.longestPalindrome_bruteForce(s2));
        System.out.println("--------------------------------------------------");

        // --------------------------------------------------
        // 測試案例 3：全部同一個字元
        // 預期: "aaaaa"
        // --------------------------------------------------
        String s3 = "aaaaa";
        System.out.println("Test Case 3:");
        System.out.println("Input: " + s3);
        System.out.println("DP 答案: " + solution.longestPalindrome(s3));
        System.out.println("暴力解: " + solution.longestPalindrome_bruteForce(s3));
        System.out.println("--------------------------------------------------");

        // --------------------------------------------------
        // 測試案例 4：沒有回文 (除了單一字符)
        // 預期: "a" (或 "b", "c", "d")
        // --------------------------------------------------
        String s4 = "abcd";
        System.out.println("Test Case 4:");
        System.out.println("Input: " + s4);
        System.out.println("DP 答案: " + solution.longestPalindrome(s4));
        System.out.println("暴力解: " + solution.longestPalindrome_bruteForce(s4));
        System.out.println("--------------------------------------------------");
    }
}
