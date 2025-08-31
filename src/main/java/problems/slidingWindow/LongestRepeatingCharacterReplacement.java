package problems.slidingWindow;

import java.util.HashMap;

public class LongestRepeatingCharacterReplacement {
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
        HashMap<Character, Integer> count = new HashMap<>();
        int l = 0, maxFreq = 0, res = 0;

        for (int r = 0; r < s.length(); r++) {
            count.put(s.charAt(r),
                    count.getOrDefault(s.charAt(r), 0) + 1);
            maxFreq = Math.max(maxFreq, count.get(s.charAt(r)));

            while ((r - l + 1) - maxFreq > k) {
                count.put(s.charAt(l),
                        count.get(s.charAt(l)) - 1);
                l++;
            }

            res = Math.max(res, r - l + 1);
        }

        return res;
    }

}
