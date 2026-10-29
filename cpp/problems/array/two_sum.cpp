// LeetCode 1. Two Sum
#include <gtest/gtest.h>

#include <unordered_map>
#include <vector>

namespace problems::array::two_sum {

class Solution {
public:
    std::vector<int> twoSum(const std::vector<int>& nums, int target) {
        std::unordered_map<int, int> seen;
        for (int i = 0; i < static_cast<int>(nums.size()); i++) {
            int complement = target - nums[i];
            if (auto it = seen.find(complement); it != seen.end()) {
                return {it->second, i};
            }
            seen[nums[i]] = i;
        }
        return {};
    }
};

TEST(TwoSum, Examples) {
    Solution s;
    EXPECT_EQ(s.twoSum({2, 7, 11, 15}, 9), (std::vector<int>{0, 1}));
    EXPECT_EQ(s.twoSum({3, 2, 4}, 6), (std::vector<int>{1, 2}));
    EXPECT_EQ(s.twoSum({3, 3}, 6), (std::vector<int>{0, 1}));
}

}  // namespace problems::array::two_sum
