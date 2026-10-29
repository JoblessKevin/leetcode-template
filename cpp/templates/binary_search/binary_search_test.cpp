#include "templates/binary_search/binary_search.hpp"

#include <gtest/gtest.h>

namespace templates {

TEST(BinarySearch, LowerBound) {
    EXPECT_EQ(lowerBound({1, 2, 2, 4}, 2), 1);
    EXPECT_EQ(lowerBound({1, 2, 2, 4}, 3), 3);
    EXPECT_EQ(lowerBound({1, 2, 2, 4}, 5), 4);
}

TEST(BinarySearch, Classic) {
    EXPECT_EQ(binarySearch({1, 3, 5, 7}, 5), 2);
    EXPECT_EQ(binarySearch({1, 3, 5, 7}, 4), -1);
}

}  // namespace templates
