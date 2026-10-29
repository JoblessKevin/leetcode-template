#pragma once

#include <vector>

namespace templates {

// lower_bound: return the first index >= target; or nums.size() if none.
inline int lowerBound(const std::vector<int>& nums, int target) {
    int l = 0, r = static_cast<int>(nums.size());
    while (l < r) {
        int m = l + (r - l) / 2;
        if (nums[m] < target)
            l = m + 1;
        else
            r = m;
    }
    return l;
}

// classic binary search; return index or -1
inline int binarySearch(const std::vector<int>& nums, int target) {
    int l = 0, r = static_cast<int>(nums.size()) - 1;
    while (l <= r) {
        int m = l + (r - l) / 2;
        if (nums[m] == target) return m;
        if (nums[m] < target)
            l = m + 1;
        else
            r = m - 1;
    }
    return -1;
}

}  // namespace templates
