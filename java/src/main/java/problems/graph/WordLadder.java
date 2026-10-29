package problems.graph;

import java.util.*;

/**
 * 雙向 BFS 是本題的最佳解，時間複雜度為 O(N * L^2)，其中 N 是單字的數量，L 是單字的長度。
 */
public class WordLadder {
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        // 1. 把字典轉成 HashSet，不僅查詢快 (O(1))，還能當作 visited 陣列使用
        Set<String> dict = new HashSet<>(wordList);

        // 如果終點根本不在字典裡，直接判死刑
        if (!dict.contains(endWord))
            return 0;

        // 2. 初始化 BFS 的 Queue
        Queue<String> queue = new ArrayDeque<>();
        queue.offer(beginWord);
        int level = 1; // 包含起點自己，所以初始步數是 1

        // 3. 開始 BFS (水波紋擴張)
        while (!queue.isEmpty()) {
            int size = queue.size(); // 當前這一層有幾個單字

            for (int i = 0; i < size; i++) {
                String currentWord = queue.poll();

                // 如果已經變身成終點，直接回傳當前的層數 (最短路徑)
                if (currentWord.equals(endWord))
                    return level;

                // 嘗試改變目前單字的每一個字母
                char[] wordChars = currentWord.toCharArray();
                for (int j = 0; j < wordChars.length; j++) {
                    char originalChar = wordChars[j]; // 先記住原本的字母

                    // 從 'a' 嘗試到 'z'
                    for (char ch = 'a'; ch <= 'z'; ch++) {
                        if (wordChars[j] == ch)
                            continue; // 跟原本一樣就跳過

                        wordChars[j] = ch; // 替換字母
                        String newWord = new String(wordChars);

                        // 4. 如果變出來的新字在字典裡
                        if (dict.contains(newWord)) {
                            queue.offer(newWord); // 加入 Queue，準備下一層的接龍
                            dict.remove(newWord); // 🌟 關鍵：從字典移除！代表我們走過它了，避免形成環或重複走訪
                        }
                    }
                    // 恢復原本的字母，準備替換下一個位置
                    wordChars[j] = originalChar;
                }
            }
            level++; // 這一層全部找完了，步數 +1
        }

        // Queue 空了都沒找到，代表變不過去
        return 0;
    }

    public int ladderLength_bidirectional(String beginWord, String endWord, List<String> wordList) {
        Set<String> dict = new HashSet<>(wordList);
        if (!dict.contains(endWord))
            return 0; // 終點不在字典裡，直接判死刑

        // 🌟 使用 Set 來代替 Queue，方便快速檢查「交集」
        Set<String> beginSet = new HashSet<>();
        Set<String> endSet = new HashSet<>();

        beginSet.add(beginWord);
        endSet.add(endWord);

        int step = 1;

        // 只要兩邊都還有字可以變，就繼續找
        while (!beginSet.isEmpty() && !endSet.isEmpty()) {
            // 🌟 神級優化：永遠從單字數量比較少的那個 Set 開始擴展
            if (beginSet.size() > endSet.size()) {
                Set<String> temp = beginSet;
                beginSet = endSet;
                endSet = temp;
            }

            // 用來裝這一層變出來的新單字
            Set<String> nextSet = new HashSet<>();

            // 遍歷比較小的那個 Set 裡的所有單字
            for (String word : beginSet) {
                char[] wordChars = word.toCharArray();

                // 嘗試改變每個字母
                for (int i = 0; i < wordChars.length; i++) {
                    char originalChar = wordChars[i];

                    for (char c = 'a'; c <= 'z'; c++) {
                        if (originalChar == c)
                            continue;

                        wordChars[i] = c;
                        String newWord = new String(wordChars);

                        // 🌟 關鍵判斷：如果變出來的新字，剛好在「對面」的 Set 裡！
                        // 代表兩端的水波紋相遇了，立刻回傳總步數！
                        if (endSet.contains(newWord)) {
                            return step + 1;
                        }

                        // 如果對面沒有，但字典裡有，就把它加入下一層的 Set，並從字典剃除
                        if (dict.contains(newWord)) {
                            nextSet.add(newWord);
                            dict.remove(newWord);
                        }
                    }
                    wordChars[i] = originalChar; // 復原字母
                }
            }
            // 準備進入下一層
            beginSet = nextSet;
            step++;
        }

        // 兩邊的水波紋斷掉了，沒有相遇
        return 0;
    }

    public static void main(String[] args) {
        WordLadder solution = new WordLadder();

        System.out.println("=== 測試案例 1：標準接龍 ===");
        // 路徑：hit -> hot -> dot -> dog -> cog (總共 5 個單字)
        String beginWord1 = "hit";
        String endWord1 = "cog";
        List<String> wordList1 = Arrays.asList("hot", "dot", "dog", "lot", "log", "cog");
        int result1 = solution.ladderLength(beginWord1, endWord1, wordList1);
        System.out.println("最短路徑長度: " + result1 + "\n");

        System.out.println("=== 測試案例 2：終點不在字典裡 ===");
        // 說明：cog 不在字典裡，直接回傳 0
        String beginWord2 = "hit";
        String endWord2 = "cog";
        List<String> wordList2 = Arrays.asList("hot", "dot", "dog", "lot", "log");
        int result2 = solution.ladderLength(beginWord2, endWord2, wordList2);
        System.out.println("最短路徑長度: " + result2 + "\n");
    }
}
