package templates.monotonicStack;

/** Returns the index of the next greater element for each position on the right side (-1 if no such element exists). */
public class MonotonicStack {
    public static int[] nextGreaterIndex(int[] a) {
        int n = a.length; int[] res = new int[n];
        java.util.Arrays.fill(res, -1);
        java.util.Deque<Integer> st = new java.util.ArrayDeque<>(); // Stores indices and maintains a monotonically decreasing order.
        for (int i = 0; i < n; i++) {
            while (!st.isEmpty() && a[st.peek()] < a[i]) res[st.pop()] = i;
            st.push(i);
        }
        return res;
    }
}
