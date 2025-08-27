package problems.slidingWindow;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LongestSubstringWithoutRepeatingCharacters {
    /** Optimal HashMap */
    public int lengthOfLongestSubstring_hashmap(String s) {
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

    public static void main(String[] args) {
        LongestSubstringWithoutRepeatingCharacters solver =
                new LongestSubstringWithoutRepeatingCharacters();

        String[] testCases = {
                "abcabcbb", // 3 ("abc")
                "bbbbb",    // 1 ("b")
                "pwwkew",   // 3 ("wke")
                "",         // 0
                "au",       // 2 ("au")
                "dvdf"      // 3 ("vdf")
        };

        for (String s : testCases) {
            int ans1 = solver.lengthOfLongestSubstring_hashmap(s);
            int ans2 = solver.lengthOfLongestSubstring_set(s);
            System.out.println("Input: \"" + s + "\"");
            System.out.println("HashMap solution: " + ans1);
            System.out.println("HashSet solution: " + ans2);
            System.out.println("-----------------------");
        }
    }
}
