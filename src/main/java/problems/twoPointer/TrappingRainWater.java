package problems.twoPointer;

public class TrappingRainWater {
    public int trap(int[] height) {
        int l = 0, r = height.length - 1;
        int lMax = 0, rMax = 0;
        int res = 0;

        while (l < r) {
            if (height[l] < height[r]) {
                if (height[l] >= lMax) {
                    lMax = height[l];
                } else {
                    res += lMax - height[l];
                }
                l++;
            } else {
                if (height[r] >= rMax) {
                    rMax = height[r];
                } else {
                    res += rMax - height[r];
                }
                r--;
            }
        }

        return res;
    }

    public static void main(String[] args) {
        TrappingRainWater solver = new TrappingRainWater();

        // 測試案例 1：經典凹槽
        int[] height1 = {0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1};
        // 預期結果: 6
        System.out.println("測試案例 1: " + solver.trap(height1) + " (預期: 6)");

        // 測試案例 2：單調遞增/遞減 (存不到水)
        int[] height2 = {1, 2, 3, 4, 5};
        // 預期結果: 0
        System.out.println("測試案例 2: " + solver.trap(height2) + " (預期: 0)");

        // 測試案例 3：兩邊高中間低
        int[] height3 = {5, 0, 5};
        // 預期結果: 5
        System.out.println("測試案例 3: " + solver.trap(height3) + " (預期: 5)");

        // 測試案例 4：邊界為空或不足
        int[] height4 = {1};
        // 預期結果: 0
        System.out.println("測試案例 4: " + solver.trap(height4) + " (預期: 0)");
    }
}
