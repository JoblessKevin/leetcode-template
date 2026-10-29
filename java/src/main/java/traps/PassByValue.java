package traps;

import java.util.ArrayList;
import java.util.List;

/**
 * @formatter:off
 * Java 只有 Pass By Value，沒有 Pass By Reference
 * 我的理解是 test 方法必須 return String 而不是 void，想要達成的效果才能生效
 * @formatter:on
 */
public class PassByValue {
    public static void test(List<String> list) {
        // 這裡的 list 是影印出來的遙控器
        list = new ArrayList<>(); // 我們把影印的遙控器改指向一台「全新的電視」
        list.add("Modified!"); // 對新電視轉台
    }

    public static void main(String[] args) {
        List<String> mainList = new ArrayList<>();
        mainList.add("Original");

        test(mainList);

        System.out.println(mainList); // 結果還是 ["Original"]！
    }
}
