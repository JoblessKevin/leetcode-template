package problems.stack;

import java.util.ArrayDeque;
import java.util.Deque;

public class DecodeString {
    public String decodeString(String s) {
        StringBuilder sb = new StringBuilder();
        Deque<Integer> mStack = new ArrayDeque<>();
        Deque<String> sStack = new ArrayDeque<>();
        int k = 0;

        for (char ch : s.toCharArray()) {
            if (Character.isDigit(ch)) {
                k = k * 10 + (ch - '0');
            } else if (ch == '[') {
                mStack.push(k);
                sStack.push(sb.toString());
                sb = new StringBuilder();
                k = 0;
            } else if (ch == ']') {
                StringBuilder temp = new StringBuilder(sStack.pop());
                int currentK = mStack.pop();
                for (int i = 0; i < currentK; i++) {
                    temp.append(sb);
                }
                sb = temp;
            } else {
                sb.append(ch);
            }
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        DecodeString ds = new DecodeString();
        String s = "3[a2[c]]";
        String decodedString = ds.decodeString(s);
        System.out.println("原始字串: " + s);
        System.out.println("解碼後的字串: " + decodedString);
    }
}
