package problems.recursive;
import java.util.*;

class BraceExpansionII {
    private int i;

    public List<String> braceExpansionII(String expression) {
        i = 0;
        Set<String> ans = parseExpr(expression);
        List<String> res = new ArrayList<>(ans);
        Collections.sort(res);
        return res;
    }

    // expr := term (',' term)*
    private Set<String> parseExpr(String s) {
        Set<String> cur = parseTerm(s);           // 先讀一個 term
        while (i < s.length() && s.charAt(i) == ',') {
            i++;                                  // 吃掉逗號
            Set<String> next = parseTerm(s);      // 再讀下一個 term
            cur.addAll(next);                     // 聯集
        }
        return cur;
    }

    // term := factor+   （一個或多個相鄰的 factor 做連接）
    private Set<String> parseTerm(String s) {
        Set<String> cur = new HashSet<>();
        cur.add("");                               // 連接的「單位元」
        while (i < s.length() && s.charAt(i) != '}' && s.charAt(i) != ',') {
            Set<String> f = parseFactor(s);
            cur = product(cur, f);                 // 做笛卡兒積連接
        }
        return cur;
    }

    // factor := letters | '{' expr '}'
    private Set<String> parseFactor(String s) {
        Set<String> res = new HashSet<>();
        char c = s.charAt(i);
        if (c == '{') {
            i++;                                   // 吃 '{'
            res = parseExpr(s);
            i++;                                   // 吃 '}'
        } else {
            // 一段連續字母可一次吞完，避免 TLE
            StringBuilder sb = new StringBuilder();
            while (i < s.length() && Character.isLetter(s.charAt(i))) {
                sb.append(s.charAt(i));
                i++;
            }
            res.add(sb.toString());
        }
        return res;
    }

    private Set<String> product(Set<String> a, Set<String> b) {
        Set<String> res = new HashSet<>();
        for (String x : a) {
            for (String y : b) {
                res.add(x + y);
            }
        }
        return res;
    }
}

