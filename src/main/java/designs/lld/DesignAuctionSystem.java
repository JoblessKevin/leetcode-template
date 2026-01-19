package designs.lld;

import java.util.*;

/**
 * TreeSet + HashMap
 */
class DesignAuctionSystem {

    // 1. 定義一個內部類別來管理「單一商品」的拍賣狀態
    private class ItemAuction {
        // 記錄每個用戶目前的出價 (userId -> price)，為了之後能快速找到並刪除舊價格
        Map<Integer, Integer> userBids = new HashMap<>();

        // 記錄排名 (Price, UserId)。TreeSet 支援 O(log N) 的 remove 和 add
        // 排序邏輯：價格高者排前；價格相同，userId 大者排前 (題目要求)
        TreeSet<int[]> ranking = new TreeSet<>((a, b) -> {
            if (a[0] != b[0])
                return Integer.compare(b[0], a[0]); // 價格降序
            return Integer.compare(b[1], a[1]); // userId 降序
        });

        public void update(int userId, int price) {
            // 如果這個人之前出過價，必須先從 TreeSet 中移除舊的紀錄
            if (userBids.containsKey(userId)) {
                int oldPrice = userBids.get(userId);
                ranking.remove(new int[] {oldPrice, userId}); // 關鍵：TreeSet 可以精準移除
            }
            // 更新 Map 和 TreeSet
            userBids.put(userId, price);
            ranking.add(new int[] {price, userId});
        }

        public void remove(int userId) {
            if (userBids.containsKey(userId)) {
                int oldPrice = userBids.get(userId);
                ranking.remove(new int[] {oldPrice, userId});
                userBids.remove(userId);
            }
        }

        public int getTop() {
            if (ranking.isEmpty())
                return -1;
            return ranking.first()[1]; // 取出第一名 (最高價) 的 userId
        }
    }

    // 2. 總管：管理所有 itemId 的拍賣
    private Map<Integer, ItemAuction> auctionMap;

    public DesignAuctionSystem() {
        auctionMap = new HashMap<>();
    }

    public void addBid(int userId, int itemId, int bidAmount) {
        // computeIfAbsent: 如果 itemId 不存在就 new 一個，然後執行 update
        auctionMap.computeIfAbsent(itemId, k -> new ItemAuction()).update(userId, bidAmount);
    }

    public void updateBid(int userId, int itemId, int newAmount) {
        // 題目保證 update 時 bid 一定存在，所以直接 get
        if (auctionMap.containsKey(itemId)) {
            auctionMap.get(itemId).update(userId, newAmount);
        }
    }

    public void removeBid(int userId, int itemId) {
        if (auctionMap.containsKey(itemId)) {
            auctionMap.get(itemId).remove(userId);
        }
    }

    public int getHighestBidder(int itemId) {
        if (!auctionMap.containsKey(itemId))
            return -1;
        return auctionMap.get(itemId).getTop();
    }
}
