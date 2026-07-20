package problems.slidingWindow;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LongestSubstringWithoutRepeatingCharacters {
    /** Optimal Sliding Window using hashMap */
    public int lengthOfLongestSubstring(String s) {
        Map<Character, Integer> map = new HashMap<>();
        int l = 0, maxLen = 0;

        for (int r = 0; r < s.length(); r++) {
            char ch = s.charAt(r);

            if (map.containsKey(ch)) {
                l = Math.max(l, map.get(ch) + 1);
            }

            map.put(ch, r);
            maxLen = Math.max(maxLen, r - l + 1);
        }

        return maxLen;
    }

    /** HashSet */
    public int lengthOfLongestSubstring_set(String s) {
        Set<Character> window = new HashSet<>();
        int l = 0, maxLen = 0;

        for (int r = 0; r < s.length(); r++) {
            char ch = s.charAt(r);

            while (window.contains(ch)) {
                window.remove(s.charAt(l));
                l++;
            }

            window.add(ch);
            maxLen = Math.max(maxLen, r - l + 1);
        }

        return maxLen;
    }

    public int lengthOfLongestSubstring_dp(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        int n = s.length();
        int[] dp = new int[n]; // dp[i] 表示以 s[i] 結尾的最長不重複子字串長度
        int[] lastOccurred = new int[128]; // 記錄字元最後一次出現的索引
        Arrays.fill(lastOccurred, -1); // 初始化為 -1，代表還沒出現過

        // 初始化第 0 個字元的狀態
        dp[0] = 1;
        lastOccurred[s.charAt(0)] = 0;
        int maxLength = 1;

        for (int i = 1; i < n; i++) {
            char c = s.charAt(i);
            int lastIdx = lastOccurred[c];

            // 判斷上一次出現的位置是否在當前有效的子字串長度之外
            if (lastIdx == -1 || i - lastIdx > dp[i - 1]) {
                dp[i] = dp[i - 1] + 1;
            } else {
                dp[i] = i - lastIdx;
            }

            // 更新字元最後出現的位置，以及全域最大長度
            lastOccurred[c] = i;
            maxLength = Math.max(maxLength, dp[i]);
        }

        return maxLength;
    }

    public static void main(String[] args) {
        LongestSubstringWithoutRepeatingCharacters solver =
                                        new LongestSubstringWithoutRepeatingCharacters();

        String[] testCases = {"abcabcbb", // 3 ("abc")
                                        "bbbbb", // 1 ("b")
                                        "pwwkew", // 3 ("wke")
                                        "", // 0
                                        "au", // 2 ("au")
                                        "dvdf" // 3 ("vdf")
        };

        for (String s : testCases) {
            int ans1 = solver.lengthOfLongestSubstring(s);
            int ans2 = solver.lengthOfLongestSubstring_set(s);
            int ans3 = solver.lengthOfLongestSubstring_dp(s);
            System.out.println("Input: \"" + s + "\"");
            System.out.println("HashMap solution: " + ans1);
            System.out.println("HashSet solution: " + ans2);
            System.out.println("Dynamic Programming solution: " + ans3);
            System.out.println("-----------------------");
        }
    }
}
