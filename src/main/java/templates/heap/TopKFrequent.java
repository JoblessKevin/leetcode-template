package templates.heap;


/** Top K Frequent Elements */
public class TopKFrequent {
    public static int[] topK(int[] a, int k) {
        var cnt = new java.util.HashMap<Integer,Integer>();
        for (int v : a) cnt.put(v, cnt.getOrDefault(v, 0) + 1);
        java.util.PriorityQueue<int[]> pq =
                new java.util.PriorityQueue<>((x,y) -> x[1]-y[1]); // 小根堆 [數值, 次數]
        for (var e : cnt.entrySet()) {
            pq.offer(new int[]{e.getKey(), e.getValue()});
            if (pq.size() > k) pq.poll();
        }
        int[] ans = new int[pq.size()];
        for (int i = pq.size()-1; i >= 0; i--) ans[i] = pq.poll()[0];
        return ans;
    }
}
