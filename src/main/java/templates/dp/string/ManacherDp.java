package templates.dp.string;

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
public class ManacherDp {
    public String longestPalindrome(String s) {
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

            if (i < R) {
                p[i] = Math.min(R - i, p[mirror]);
            }

            int r = i + (1 + p[i]);
            int l = i - (1 + p[i]);
            while (r < n && l >= 0 && t.charAt(r) == t.charAt(l)) {
                p[i]++;
                r++;
                l--;
            }

            if (i + p[i] > R) {
                C = i;
                R = i + p[i];
            }

            if (p[i] > maxLen) {
                maxLen = p[i];
                centerIndex = i;
            }
        }

        int start = (centerIndex - maxLen) / 2;
        return s.substring(start, start + maxLen);
    }

    public static void main(String[] args) {
        ManacherDp solution = new ManacherDp();

        // 推薦觀測字串： "abaaba"
        // 預處理後會變成： #a#b#a#a#b#a#
        String testStr = "abaaba";

        System.out.println("--- 開始 Manacher 演算法觀測 ---");
        System.out.println("原始字串: " + testStr);

        String result = solution.longestPalindrome(testStr);

        System.out.println("------------------------------");
        System.out.println("最終最長回文子字串: " + result);
    }
}
