package problems.heap;

import java.util.*;

/**
 * Pull Model (Fan-out on Read): Write-Light, Read-Heavy (need to aggregate data)
 */
class Twitter {
    private static int timestamp = 0;

    private static class Tweet {
        int id;
        int time;
        Tweet next;

        public Tweet(int id, int time) {
            this.id = id;
            this.time = time;
            this.next = null;
        }
    }

    private static class User {
        int id;
        Set<Integer> followed;
        Tweet tweetHead;

        public User(int id) {
            this.id = id;
            followed = new HashSet<>();
            follow(id);
            tweetHead = null;
        }

        public void follow(int userId) {
            followed.add(userId);
        }

        public void unfollow(int userId) {
            followed.remove(userId);
        }

        public void post(int tweetId) {
            Tweet t = new Tweet(tweetId, timestamp++);
            t.next = tweetHead;
            tweetHead = t;
        }
    }

    private Map<Integer, User> userMap;

    public Twitter() {
        userMap = new HashMap<>();
    }

    public void postTweet(int userId, int tweetId) {
        if (!userMap.containsKey(userId)) {
            userMap.put(userId, new User(userId));
        }
        userMap.get(userId).post(tweetId);
    }

    public List<Integer> getNewsFeed(int userId) {
        List<Integer> result = new ArrayList<>();

        if (!userMap.containsKey(userId))
            return result;

        Set<Integer> users = userMap.get(userId).followed;

        PriorityQueue<Tweet> maxHeap = new PriorityQueue<>((a, b) -> b.time - a.time);

        for (int followeeId : users) {
            User u = userMap.get(followeeId);
            if (u != null && u.tweetHead != null) {
                maxHeap.offer(u.tweetHead);
            }
        }

        int count = 0;
        while (!maxHeap.isEmpty() && count < 10) {
            Tweet t = maxHeap.poll();
            result.add(t.id);
            count++;

            if (t.next != null) {
                maxHeap.offer(t.next);
            }
        }

        return result;
    }

    public void follow(int followerId, int followeeId) {
        if (!userMap.containsKey(followerId))
            userMap.put(followerId, new User(followerId));
        if (!userMap.containsKey(followeeId))
            userMap.put(followeeId, new User(followeeId));

        userMap.get(followerId).follow(followeeId);
    }

    public void unfollow(int followerId, int followeeId) {
        if (userMap.containsKey(followerId) && followerId != followeeId) {
            userMap.get(followerId).unfollow(followeeId);
        }
    }

    // ==========================================
    // Test cases
    // ==========================================
    public static void main(String[] args) {
        System.out.println("=== 開始測試 Twitter 系統 ===");
        Twitter twitter = new Twitter();

        // 1. User 1 發布推文
        System.out.println("\n[Action] User 1 post tweet 5");
        twitter.postTweet(1, 5);

        // 測試 User 1 的 Feed (應該看到 5)
        System.out.println("User 1 Feed (Expect [5]): " + twitter.getNewsFeed(1));

        // 2. User 1 關注 User 2
        System.out.println("\n[Action] User 1 follows User 2");
        twitter.follow(1, 2);

        // 3. User 2 發布推文
        System.out.println("[Action] User 2 post tweet 6");
        twitter.postTweet(2, 6);

        // 測試 User 1 的 Feed (應該看到 6, 5 -> 因為 6 比較新)
        System.out.println("User 1 Feed (Expect [6, 5]): " + twitter.getNewsFeed(1));

        // 4. User 1 取消關注 User 2
        System.out.println("\n[Action] User 1 unfollows User 2");
        twitter.unfollow(1, 2);

        // 測試 User 1 的 Feed (應該只剩下 5)
        System.out.println("User 1 Feed (Expect [5]): " + twitter.getNewsFeed(1));

        // 5. 測試大量推文排序與數量限制 (Limit 10)
        System.out.println("\n[Test] 測試 Feed 限制 10 篇與時間排序");
        Twitter t2 = new Twitter(); // 新的實例
        t2.follow(1, 2); // 1 追蹤 2

        // User 2 發布 11 篇推文 (ID: 101 ~ 111)
        for (int i = 1; i <= 11; i++) {
            t2.postTweet(2, 100 + i);
        }
        // User 1 發布 1 篇很新的推文 (ID: 999)
        t2.postTweet(1, 999);

        // 預期結果: 999 (最新), 然後是 111, 110 ... 103 (共 10 篇)
        // 101 和 102 應該被踢出
        List<Integer> feed = t2.getNewsFeed(1);
        System.out.println("Feed size: " + feed.size());
        System.out.println("Feed content: " + feed);

        boolean isCorrect = feed.get(0) == 999 && feed.get(1) == 111
                                        && feed.get(feed.size() - 1) == 103;
        System.out.println("測試結果: " + (isCorrect ? "PASS" : "FAIL"));
    }
}
