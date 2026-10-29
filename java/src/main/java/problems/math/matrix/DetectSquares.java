package problems.math.matrix;

import java.util.HashMap;
import java.util.Map;

public class DetectSquares {
    private Map<Integer, Map<Integer, Integer>> ptsCount;

    public DetectSquares() {
        ptsCount = new HashMap<>();
    }

    public void add(int[] point) {
        int x = point[0], y = point[1];
        ptsCount.putIfAbsent(x, new HashMap<>());
        ptsCount.get(x).put(y, ptsCount.get(x).getOrDefault(y, 0) + 1);
    }

    public int count(int[] point) {
        int res = 0, x1 = point[0], y1 = point[1];

        if (!ptsCount.containsKey(x1))
            return res;

        for (int y2 : ptsCount.get(x1).keySet()) {
            int side = y2 - y1;
            if (side == 0)
                continue;

            int x3 = x1 + side, x4 = x1 - side;
            res += ptsCount.get(x1).get(y2) * ptsCount.getOrDefault(x3, new HashMap<>())
                                            .getOrDefault(y1, 0)
                                            * ptsCount.getOrDefault(x3, new HashMap<>())
                                                                            .getOrDefault(y2, 0);

            res += ptsCount.get(x1).get(y2) * ptsCount.getOrDefault(x4, new HashMap<>())
                                            .getOrDefault(y1, 0)
                                            * ptsCount.getOrDefault(x4, new HashMap<>())
                                                                            .getOrDefault(y2, 0);
        }

        return res;
    }
}

