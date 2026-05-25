package problems.twoPointer;

public class ValidPalindrome {
    public boolean isPalindrome(String s) {
        StringBuilder sb = new StringBuilder();

        for (char ch : s.toCharArray()) {
            if (Character.isLetterOrDigit(ch)) {
                sb.append(Character.toLowerCase(ch));
            }
        }

        return sb.toString().equals(sb.reverse().toString());
    }

    public boolean isPalindrome_twoPointer(String s) {
        int l = 0, r = s.length() - 1;

        while (l < r) {
            while (l < r && !check(s.charAt(l))) {
                l++;
            }
            while (r > l && !check(s.charAt(r))) {
                r--;
            }
            if (Character.toLowerCase(s.charAt(l)) != Character.toLowerCase(s.charAt(r))) {
                return false;
            }
            l++;
            r--;
        }
        return true;
    }

    public boolean check(char c) {
        return (c >= 'A' && c <= 'Z' || c >= 'a' && c <= 'z' || c >= '0' && c <= '9');
    }
}
