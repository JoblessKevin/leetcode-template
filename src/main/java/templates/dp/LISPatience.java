package templates.dp;

public class LISPatience {
    public static int lengthOfLIS(int[] a) {
        int[] tail = new int[a.length]; int len = 0;
        for (int x : a) {
            int i = java.util.Arrays.binarySearch(tail, 0, len, x);
            if (i < 0) i = -i - 1;
            tail[i] = x;
            if (i == len) len++;
        }
        return len;
    }
}
