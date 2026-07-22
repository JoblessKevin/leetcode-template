package problems.slidingWindow;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LongestRepeatingCharacterReplacement {
    /** Sliding Window */
    public int characterReplacement(String s, int k) {
        int res = 0;
        Set<Character> set = new HashSet<>();
        for (char ch : s.toCharArray()) {
            set.add(ch);
        }

        for (char ch : set) {
            int count = 0, l = 0;
            for (int r = 0; r < s.length(); r++) {
                if (s.charAt(r) == ch) {
                    count++;
                }

                while ((r - l + 1) - count > k) {
                    if (s.charAt(l) == ch) {
                        count--;
                    }
                    l++;
                }

                res = Math.max(res, r - l + 1);
            }
        }
        return res;
    }

    /** Optimal */
    public int characterReplacement_optimal(String s, int k) {
        int[] count = new int[26];
        int l = 0, maxFreq = 0, res = 0;

        for (int r = 0; r < s.length(); r++) {
            char ch = s.charAt(r);
            count[ch - 'A']++;
            maxFreq = Math.max(maxFreq, count[ch - 'A']);

            while ((r - l + 1) - maxFreq > k) {
                count[s.charAt(l) - 'A']--;
                l++;
            }

            res = Math.max(res, r - l + 1);
        }

        return res;
    }

    /** Pure HashMap */
    public int characterReplacement_hashmap(String s, int k) {
        Map<Character, Integer> count = new HashMap<>();
        int l = 0, maxFreq = 0, res = 0;

        for (int r = 0; r < s.length(); r++) {
            count.put(s.charAt(r), count.getOrDefault(s.charAt(r), 0) + 1);
            maxFreq = Math.max(maxFreq, count.get(s.charAt(r)));

            while ((r - l + 1) - maxFreq > k) {
                count.put(s.charAt(l), count.get(s.charAt(l)) - 1);
                l++;
            }

            res = Math.max(res, r - l + 1);
        }

        return res;
    }

    public static void main(String[] args) {
        LongestRepeatingCharacterReplacement obj = new LongestRepeatingCharacterReplacement();
        System.out.println(obj.characterReplacement("ABAB", 2)); // Output: 4
        // System.out.println(obj.characterReplacement("AABABBA", 1)); // Output: 4
    }
}
