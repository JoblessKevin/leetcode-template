package templates.binarySearch;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class TimeMap {
    private Map<String, List<Pair<Integer, String>>> keyStore;

    public TimeMap() {
        keyStore = new HashMap<>();
    }
    
    public void set(String key, String value, int timestamp) {
        keyStore.computeIfAbsent(key, k -> new ArrayList<>())
                .add(new Pair<>(timestamp, value));
    }
    
    public String get(String key, int timestamp) {
        List<Pair<Integer, String>> values = keyStore.getOrDefault(key, new ArrayList<>());
        int left = 0, right = values.size() - 1;
        String result = "";

        while (left <= right) {
            int mid = left + (right - left)/2;
            if (values.get(mid).getKey() <= timestamp) {
                result = values.get(mid).getValue();
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return result;
    }

    private static class Pair<K, V> {
        private final K key;
        private final V value;

        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }
    }
}

/**
 * Your TimeMap object will be instantiated and called as such:
 * TimeMap obj = new TimeMap();
 * obj.set(key,value,timestamp);
 * String param_2 = obj.get(key,timestamp);
 */

/** ----------- TreeMap ---------- */
//  class TimeMap {
//     private Map<String, TreeMap<Integer, String>> m;

//     public TimeMap() {
//         m = new HashMap<>();    
//     }
    
//     public void set(String key, String value, int timestamp) {
//         m.computeIfAbsent(key, k -> new TreeMap<>()).put(timestamp, value);
//     }
    
//     public String get(String key, int timestamp) {
//         if (!m.containsKey(key)) return "";
//         TreeMap<Integer, String> timestamps = m.get(key);
//         Map.Entry<Integer, String> entry = timestamps.floorEntry(timestamp);

//         return entry == null ? "" : entry.getValue();
//     }

//     // public String get(String key, int timestamp) {
//     //     return Optional.ofNullable(m.get(key))
//     //                     .map(t -> t.floorEntry(timestamp))
//     //                     .map(Map.Entry::getValue)
//     //                     .orElse("");
//     // }
// }