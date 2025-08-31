package problems.slidingWindow;

import java.util.Arrays;

public class PermutationInString {
    /** My solution */
    public boolean checkInclusion(String s1, String s2) {
        if (s1.length() > s2.length()) return false;

        int[] count1 = new int[26];
        int[] count2 = new int[26];

        for (char c : s1.toCharArray()) {
            count1[c - 'a']++;
        }

        for (int i = 0; i < s2.length(); i++) {
            count2[s2.charAt(i) - 'a']++;

            if (i >= s1.length()) {
                count2[s2.charAt(i - s1.length()) - 'a']--;
            }

            if (Arrays.equals(count1, count2)) return true;
        }

        return false;
    }

    /** Limit 26 characters */
    public boolean checkInclusion_26(String s1, String s2) {
        if (s1.length() > s2.length()) return false;

        int[] count1 = new int[26];
        int[] count2 = new int[26];

        for (char c : s1.toCharArray()) {
            count1[c - 'a']++;
        }

        int matches = 0; // 計算多少字母次數完全對上
        for (int i = 0; i < 26; i++) {
            if (count1[i] == count2[i]) {
                matches++;
            }
        }

        int L = s1.length();
        for (int r = 0; r < s2.length(); r++) {
            int idxR = s2.charAt(r) - 'a';
            count2[idxR]++;

            if (count2[idxR] == count1[idxR]) {
                matches++;
            } else if (count2[idxR] - 1 == count1[idxR]) {
                matches--;
            }

            if (r >= L) { // 移出左邊字元
                int idxL = s2.charAt(r - L) - 'a';
                count2[idxL]--;

                if (count2[idxL] == count1[idxL]) {
                    matches++;
                } else if (count2[idxL] + 1 == count1[idxL]) {
                    matches--;
                }
            }

            if (matches == 26) return true;
        }

        return false;
    }

    public static void main(String[] args) {
        PermutationInString sol = new PermutationInString();

        // true
        System.out.println(sol.checkInclusion("ab", "eidbaooo"));

//        // false
//        System.out.println(sol.checkInclusion("ab", "eidboaoo"));
//
//        // true
//        System.out.println(sol.checkInclusion("abc", "abc"));
//
//        // true
//        System.out.println(sol.checkInclusion("xyz", "aaaxyz"));
//
//        // true
//        System.out.println(sol.checkInclusion("a", "aaaaa"));
    }
}
