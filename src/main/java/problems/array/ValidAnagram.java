package problems.array;

public class ValidAnagram {
    public boolean isAnagram(String s, String t) {
        // 如果長度不同，絕對不可能是 Anagram
        if (s.length() != t.length())
            return false;

        // 建立一個長度 26 的桶子 (代表 a-z)
        int[] counter = new int[26];

        for (int i = 0; i < s.length(); i++) {
            // s 的字元讓計數 +1
            counter[s.charAt(i) - 'a']++;
            // t 的字元讓計數 -1
            counter[t.charAt(i) - 'a']--;
        }

        // 最後檢查所有桶子是否都回到 0
        for (int count : counter) {
            if (count != 0)
                return false;
        }

        return true;
    }

    public boolean isAnagram_optimized(String s, String t) {
        if (s.length() != t.length())
            return false;

        char[] sChars = s.toCharArray();
        char[] tChars = t.toCharArray();

        int[] counter = new int[26];
        for (char c : sChars) {
            counter[c - 'a']++;
        }

        for (char c : tChars) {
            if (--counter[c - 'a'] < 0) {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        ValidAnagram solver = new ValidAnagram();

        // 測試案例
        String s = "anagram";
        String t = "nagaram";
        System.out.println("測試輸入: s = " + s + ", t = " + t);
        System.out.println("期待結果: true");
        System.out.println("-------------------------------------");

        // 測試
        boolean result = solver.isAnagram(s, t);
        System.out.println("方法 1 (桶子) 結果: " + result);

        System.out.println("-------------------------------------");
        System.out.println("驗證完成。");
    }
}
