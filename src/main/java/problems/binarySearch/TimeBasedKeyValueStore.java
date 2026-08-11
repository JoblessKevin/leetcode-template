package problems.binarySearch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TimeBasedKeyValueStore {
    class TimeMap {
        private class Node {
            int timestamp;
            String value;

            public Node(int timestamp, String value) {
                this.timestamp = timestamp;
                this.value = value;
            }
        }

        private Map<String, List<Node>> map;

        public TimeMap() {
            map = new HashMap<>();
        }

        public void set(String key, String value, int timestamp) {
            map.putIfAbsent(key, new ArrayList<>());
            map.get(key).add(new Node(timestamp, value));
        }

        public String get(String key, int timestamp) {
            if (!map.containsKey(key)) {
                return "";
            }

            List<Node> list = map.get(key);

            // 利用二分搜尋尋找 <= timestamp 的最大時間戳
            int l = 0, r = list.size() - 1;
            String res = "";

            while (l <= r) {
                int mid = l + (r - l) / 2;
                if (list.get(mid).timestamp <= timestamp) {
                    res = list.get(mid).value; // 記錄合法的值，然後繼續往右找看有沒有更接近的
                    l = mid + 1;
                } else {
                    r = mid - 1;
                }
            }

            return res;
        }
    }

    public static void main(String[] args) {
        TimeBasedKeyValueStore solution = new TimeBasedKeyValueStore();
        TimeMap timeMap = solution.new TimeMap();

        timeMap.set("foo", "bar", 1);
        System.out.println(timeMap.get("foo", 1)); // 返回 "bar"
        System.out.println(timeMap.get("foo", 3)); // 返回 "bar"，因為在時間戳 3 時，最近的時間戳是 1
        timeMap.set("foo", "bar2", 4);
        System.out.println(timeMap.get("foo", 4)); // 返回 "bar2"
        System.out.println(timeMap.get("foo", 5)); // 返回 "bar2"，因為在時間戳 5 時，最近的時間戳是 4
    }
}
