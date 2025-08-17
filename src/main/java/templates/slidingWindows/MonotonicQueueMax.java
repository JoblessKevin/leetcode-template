package templates.slidingWindows;

public class MonotonicQueueMax {
    /** Return the maximum value of each window of length k */
    public static int[] maxSlidingWindow(int[] a, int k) {
        int n = a.length; if (n == 0 || k == 0) return new int[0];
        int[] ans = new int[n - k + 1]; int idx = 0;
        java.util.Deque<Integer> dq = new java.util.ArrayDeque<>(); // 存索引，值遞減
        for (int i = 0; i < n; i++) {
            while (!dq.isEmpty() && a[dq.peekLast()] <= a[i]) dq.pollLast();
            dq.offerLast(i);
            if (dq.peekFirst() <= i - k) dq.pollFirst();
            if (i >= k - 1) ans[idx++] = a[dq.peekFirst()];
        }
        return ans;
    }
}
