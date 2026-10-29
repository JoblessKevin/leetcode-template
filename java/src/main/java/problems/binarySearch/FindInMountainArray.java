package problems.binarySearch;

public class FindInMountainArray {
    interface MountainArray {
        public int get(int index);

        public int length();
    }

    public int findInMountainArray(int target, MountainArray mountainArr) {
        int n = mountainArr.length();

        // 步驟 1：尋找山頂 (Peak Index)
        int peakIndex = findPeak(mountainArr, n);

        // 步驟 2：在左半坡（遞增區間）尋找 target
        // 注意：區間包含 peakIndex，也就是 [0, peakIndex]
        int leftResult = binarySearch(mountainArr, target, 0, peakIndex, true);

        // 如果左半邊找到了，立刻回傳（保證是最小索引）
        if (leftResult != -1) {
            return leftResult;
        }

        // 步驟 3：如果左邊沒有，才去右半坡（遞減區間）尋找
        // 區間是 [peakIndex + 1, n - 1]
        return binarySearch(mountainArr, target, peakIndex + 1, n - 1, false);
    }

    // Helper 1: 尋找山頂的二分搜尋
    private int findPeak(MountainArray mountainArr, int n) {
        int l = 0, r = n - 1;
        while (l < r) {
            int mid = l + (r - l) / 2;
            // 比較 mid 和 mid + 1 來判斷坡度
            if (mountainArr.get(mid) < mountainArr.get(mid + 1)) {
                // 上坡，山頂一定在右邊
                l = mid + 1;
            } else {
                // 下坡（或已經是山頂），山頂在左邊包含自己
                r = mid;
            }
        }
        return l; // 當 l == r 時，就是山頂
    }

    // Helper 2: 通用的二分搜尋（支援遞增或遞減陣列）
    private int binarySearch(MountainArray mountainArr, int target, int l, int r,
                                    boolean isAscending) {
        while (l <= r) {
            int mid = l + (r - l) / 2;
            int midVal = mountainArr.get(mid);

            if (midVal == target) {
                return mid;
            }

            // 核心差異：根據是遞增還是遞減，決定邊界怎麼縮
            if (isAscending) {
                // 左坡（遞增）：一般邏輯
                if (midVal < target) {
                    l = mid + 1;
                } else {
                    r = mid - 1;
                }
            } else {
                // 右坡（遞減）：邏輯反轉
                if (midVal > target) {
                    // 目前的值比 target 大，因為是遞減，所以 target 一定在更右邊（較小的區域）
                    l = mid + 1;
                } else {
                    r = mid - 1;
                }
            }
        }
        return -1; // 找不到回傳 -1
    }
}
