package templates.kmp;

public class KMP {
    public static int indexOf(String s, String p) {
        if (p.isEmpty()) return 0;
        int n = s.length(), m = p.length();
        int[] lps = new int[m]; // Longest Proper Prefix Suffix
        for (int i=1, len=0; i<m; ) {
            if (p.charAt(i)==p.charAt(len)) lps[i++]=++len;
            else if (len>0) len = lps[len-1];
            else lps[i++] = 0;
        }
        for (int i=0,j=0; i<n; ) {
            if (s.charAt(i)==p.charAt(j)) { i++; j++; if (j==m) return i-m; }
            else if (j>0) j = lps[j-1];
            else i++;
        }
        return -1;
    }
}
