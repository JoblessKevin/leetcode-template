package problems.string;

import java.util.ArrayList;
import java.util.List;

public class EncodeAndDecodeStrings {
    // 編碼：把 List<String> 變成一個單一字串
    public String encode(List<String> strs) {
        StringBuilder sb = new StringBuilder();
        for (String s : strs) {
            // 格式：長度 + '#' + 內容
            sb.append(s.length()).append('#').append(s); // 5#hello, 5#world, 0# (空字串)
        }
        return sb.toString();
    }

    // 解碼：把單一字串還原回 List<String>
    public List<String> decode(String s) {
        List<String> result = new ArrayList<>();
        int i = 0;

        while (i < s.length()) {
            // 1. 先找到 '#' 的位置，判斷長度是多少
            int j = s.indexOf('#', i);
            int len = Integer.parseInt(s.substring(i, j));

            // 2. 從 '#' 的下一位開始，讀取 len 長度的內容
            i = j + 1; // 跳過 '#'
            result.add(s.substring(i, i + len));

            // 3. 跳轉到下一個區塊的開頭
            i += len;
        }
        return result;
    }
}
