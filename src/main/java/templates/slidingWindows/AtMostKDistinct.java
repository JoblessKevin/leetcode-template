package templates.slidingWindows;

public class AtMostKDistinct {
    public static int lengthOfLongestSubstringKDistinct(String s, int K) {
        var cnt = new java.util.HashMap<Character, Integer>();
        int left = 0, kinds = 0, ans = 0;
        for (int r = 0; r < s.length(); r++) {
            char c = s.charAt(r);
            cnt.put(c, cnt.getOrDefault(c, 0) + 1);
            if (cnt.get(c) == 1) kinds++;
            while (kinds > K) {
                char d = s.charAt(left++);
                cnt.put(d, cnt.get(d) - 1);
                if (cnt.get(d) == 0) kinds--;
            }
            ans = Math.max(ans, r - left + 1);
        }
        return ans;
    }
}
