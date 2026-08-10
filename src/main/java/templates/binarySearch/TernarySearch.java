package templates.binarySearch;

public class TernarySearch {
    // 這是一個模擬的單峰函數 (Unimodal Function)
    // 假設它是一個開口向上的 U 型曲線（例如：f(x) = (x - 5)^2 + 3），最低點在 x = 5
    public double f(double x) {
        return (x - 5.0) * (x - 5.0) + 3.0;
    }

    /**
     * 三分搜尋法：尋找單峰函數在區間 [left, right] 內的極小值 (Minimum)
     */
    public double ternarySearch(double left, double right) {
        // 因為是連續空間 (double)，我們設定一個極小的誤差值 EPSILON 作為迴圈結束條件
        double EPSILON = 1e-7;

        // 當區間長度還大於誤差值時，繼續切分
        while (right - left > EPSILON) {

            // 將區間切成三等份，找出 mid1 和 mid2
            double mid1 = left + (right - left) / 3.0;
            double mid2 = right - (right - left) / 3.0;

            // 比較兩個中點的函數值
            if (f(mid1) < f(mid2)) {
                // 如果 mid1 比較低，代表極小值一定在 mid2 的「左邊」
                // 因此捨棄 mid2 右邊的區間，把 right 移過來
                right = mid2;
            } else {
                // 如果 mid2 比較低，或者兩者一樣高，代表極小值一定在 mid1 的「右邊」
                // 因此捨棄 mid1 左邊的區間，把 left 移過來
                left = mid1;
            }
        }

        // 迴圈結束時，left 和 right 已經無限逼近，回傳任意一個皆可得到極值發生的位置
        return left;
    }

    public static void main(String[] args) {
        TernarySearch ts = new TernarySearch();
        // 我們要在 0.0 到 10.0 的區間找極小值
        double bestX = ts.ternarySearch(0.0, 10.0);

        System.out.println("極小值發生在 x = " + bestX);
        System.out.println("該點的函數值 f(x) = " + ts.f(bestX));
    }
}
