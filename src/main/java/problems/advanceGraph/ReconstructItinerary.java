package problems.advanceGraph;

import java.util.*;

public class ReconstructItinerary {
    // 建立航班圖：Map<出發地, PriorityQueue<目的地>>
    // 使用 PriorityQueue 是因為題目要求字母序最小的優先飛
    private Map<String, PriorityQueue<String>> flightMap = new HashMap<>();
    // 使用 LinkedList 方便我們每次都把無路可走的節點塞到「最前面」
    private LinkedList<String> itinerary = new LinkedList<>();

    public List<String> findItinerary(List<List<String>> tickets) {
        // 1. 建立航班圖
        for (List<String> ticket : tickets) {
            String from = ticket.get(0);
            String to = ticket.get(1);
            flightMap.putIfAbsent(from, new PriorityQueue<>());
            flightMap.get(from).add(to);
        }

        // 2. 題目規定永遠從 "JFK" 出發
        dfs("JFK");

        return itinerary;
    }

    private void dfs(String departure) {
        PriorityQueue<String> arrivals = flightMap.get(departure);

        // 只要這個機場還有機票可以飛，就一直貪心地往下飛
        while (arrivals != null && !arrivals.isEmpty()) {
            // poll() 會自動拿出字母序最小的目的地，並且把它從 Queue 中刪除 (代表機票用掉了)
            String nextDestination = arrivals.poll();
            dfs(nextDestination);
        }

        // 🌟 Hierholzer's 演算法的核心：
        // 當 while 迴圈結束，代表從這個 departure 出發的機票全用光了 (死胡同)
        // 把它塞到行程表的最前面！
        itinerary.addFirst(departure);
    }

    public static void main(String[] args) {
        ReconstructItinerary solution = new ReconstructItinerary();

        System.out.println("=== 測試案例 1：正常一筆畫 ===");
        // 預期輸出: ["JFK", "MUC", "LHR", "SFO", "SJC"]
        List<List<String>> tickets1 = Arrays.asList(Arrays.asList("MUC", "LHR"),
                                        Arrays.asList("JFK", "MUC"), Arrays.asList("SFO", "SJC"),
                                        Arrays.asList("LHR", "SFO"));
        System.out.println("行程表: " + solution.findItinerary(tickets1) + "\n");

        System.out.println("=== 測試案例 2：字母序陷阱與死胡同 ===");
        // 說明：從 JFK 出發，可以去 ATL 或 SFO。
        // ATL 字母序比較小，如果單純 Greedy 先飛 ATL -> JFK -> SFO -> ATL -> SFO，
        // 你會在最後一個 SFO 卡死，因為票用光了，但這是正確的。
        // 如果這個圖長得更刁鑽，Hierholzer 會透過 addFirst 神奇地把死路排到最後！
        // 預期輸出: ["JFK", "ATL", "JFK", "SFO", "ATL", "SFO"]
        solution = new ReconstructItinerary(); // 重置物件
        List<List<String>> tickets2 = Arrays.asList(Arrays.asList("JFK", "SFO"),
                                        Arrays.asList("JFK", "ATL"), Arrays.asList("SFO", "ATL"),
                                        Arrays.asList("ATL", "JFK"), Arrays.asList("ATL", "SFO"));
        System.out.println("行程表: " + solution.findItinerary(tickets2) + "\n");
    }
}
