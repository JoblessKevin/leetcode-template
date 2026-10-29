package templates.prefix;

public class SubarraySumK {
    public static int count(int[] a, int k) {
        var map = new java.util.HashMap<Integer,Integer>();
        map.put(0, 1);
        int sum = 0, ans = 0;
        for (int v : a) {
            sum += v;
            ans += map.getOrDefault(sum - k, 0);
            map.put(sum, map.getOrDefault(sum, 0) + 1);
        }
        return ans;
    }
}
