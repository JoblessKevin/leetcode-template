package problems.recursive;
import java.util.*;


/**
 * 最佳解更快主要是因為避免了 Hash 的使用，加上避免多餘掃描 (splits 線性一次)，在時間複雜度上兩者都是 O(n + M · L + M log M · L) 
 * 目前做法每層都要判斷：(是否有逗號 + 是否有括號 + 並遞迴到下一層)，最佳解則是直接線性掃描一次，並在遇到逗號時切割字串，減少不必要的判斷次數
 * 目前解: Set + Parse
 * 最佳解: List + Split
 */
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


/** ========== Optimize solution ========== */
/**
 * splits(expression) 會把最外層以 , 切成多段 split，每段各自代表一個 term
 * 如果只有一段 split，代表 expression 沒有逗號，可以直接處理乘法 (concatenation)
 * 處理乘法時，從左到右掃描 expression，遇到字母就直接加入結果，遇到 { 就找出對應的 }，把中間的子 expression 遞迴處理後再做乘法
 * 如果有多段 split，代表 expression 有逗號，可以把每段 split 遞迴處理後合併結果 (union)
 * ============= Explanation =============
 * braceExpansionII：決定走「連接」或「聯集」；前者線性掃描因子、後者遞迴合併結果。
 * splits：一次線性掃描找最外層逗號切段，不切括號內。
 * doMulti：兩集合的笛卡兒積（連接），並排序。
 * findClose：從 st 的 { 出發，找對應的 }，只在當層匹配。
 */
class Solution {
    public List<String> braceExpansionII(String expression) {
        List<String> splits = splits(expression);
        if (splits.size() == 1)
        {
            int i = 0;
            List<String> multi = new ArrayList<>();
            while (i < expression.length())
            {
                char c = expression.charAt(i);
                if (c == '{')
                {
                    int j = findClose(expression, i);
                    String sub = expression.substring(i+1, j);
                    List<String> res = braceExpansionII(sub);
                    multi = multi.isEmpty() ? res : doMulti(multi, res);
                    i = j + 1;
                }
                else
                {
                    int j = i + 1;
                    while (j < expression.length())
                    {
                        if (expression.charAt(j) >= 'a' && expression.charAt(j) <= 'z')
                            j ++;
                        else
                            break;
                    }
                    List<String> res = Collections.singletonList(expression.substring(i, j));
                    multi = multi.isEmpty() ? res : doMulti(multi, res);
                    i = j;
                }
            }
            return multi;

        }
        List<String> merge = new ArrayList<>();
        for (String split : splits) {
            List<String> subs = braceExpansionII(split);
            for (String sub : subs) {
                if (!merge.contains(sub)) {
                    merge.add(sub);
                }
            }
        }
        Collections.sort(merge);
        return merge;
    }

    public List<String> splits(String exp) {
        int i=0;
        List<String> res = new ArrayList<>();
        while (i < exp.length())
        {
            int j = i;
            int open = 0;

            while (j < exp.length())
            {
                char c = exp.charAt(j);
                if (c == ',')
                {
                    if (open == 0) break;
                }
                else if (c == '{')
                {
                    open ++;
                }
                else if (c == '}')
                {
                    open --;
                }
                j ++;
            }
            res.add(exp.substring(i, j));
            i = j + 1;
        }
        Collections.sort(res);
        return res;
    }

    private List<String> doMulti(List<String> multi, List<String> res) {
        List<String> product = new ArrayList<>();
        for (String m : multi) {
            for (String re : res) {
                product.add(m + re);
            }
        }
        Collections.sort(product);
        return product;
    }

    public int findClose(String exp, int st)
    {
        int i = st+1;
        int open = 0;
        while (i < exp.length()) {
            if (exp.charAt(i) == '}') {
                if (open > 0) {
                    open--;
                } else if (open == 0) {
                    return i;
                }
            } else if (exp.charAt(i) == '{') {
                open++;
            }
            i++;
        }
        return i;
    }

    /** Test Data */
    public static void main(String[] args) {
        Solution sol = new Solution();

        // 測試組 1：基本功能測試
        test(sol, "{a,b}{c,{d,e}}");          // 基本巢狀
        test(sol, "{{a,z},a{b,c},{ab,z}}");   // LeetCode 經典題目案例
        test(sol, "{a,b}c{d,e}f");            // 混合字母 + 括號
        test(sol, "abcd");                    // 單純字母串
        test(sol, "{a,b,c}");                 // 單層 union

        // 測試組 2：巢狀層級深一點
        test(sol, "{a,{b,{c,{d,e}}}}");
        test(sol, "{x,{a,b}{c,{d,e}}}");
        test(sol, "{a,b}{c,d}{e,f}");

        // 測試組 3：多層連接 + union
        test(sol, "{a,b}{c,{d,e}}{f,g}");
        test(sol, "{a,b}{c,d}{e,f}{g,h}");

        // 測試組 4：大型組合測試（性能壓力）
        String big = "{" + "a,b,c,d,e,f,g,h,i,j" + "}{"
                   + "k,l,m,n,o,p,q,r,s,t" + "}{"
                   + "u,v,w,x,y,z" + "}";
        test(sol, big); // 10 * 10 * 6 = 600 組合

        // 測試組 5：超大型壓測（慎用）
        // 可開測看看 runtime 差距
        // String huge = "{a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t}"
        //              + "{a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t}";
        // test(sol, huge); // 20 * 20 = 400 組合
    }

    private static void test(Solution sol, String exp) {
        long start = System.nanoTime();
        List<String> res = sol.braceExpansionII(exp);
        long end = System.nanoTime();
        System.out.println("Expression: " + exp);
        System.out.println("Result: " + res);
        System.out.println("Count: " + res.size());
        System.out.printf("Time: %.3f ms%n", (end - start) / 1_000_000.0);
        System.out.println("=".repeat(60));
    }
}