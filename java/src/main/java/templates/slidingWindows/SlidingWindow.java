package templates.slidingWindows;

import java.util.HashMap;

/** Sliding window template: longest substring without repeating characters. */
public class SlidingWindow {
    public static int longestNoRepeat(String s) {
        var last = new HashMap<Character, Integer>();
        int left = 0, ans = 0;
        for (int r = 0; r < s.length(); r++) {
            char c = s.charAt(r);
            if (last.containsKey(c) && last.get(c) >= left) {
                left = last.get(c) + 1;
            }
            last.put(c, r);
            ans = Math.max(ans, r - left + 1);
            }
        return ans;
    }
}
