#include <algorithm>
#include <climits>
#include <vector>

class BestTimetoBuyandSellStock {
public:
  int maxProfit(const std::vector<int> &prices) {
    int minPrice = INT_MAX;
    int maxProfit = 0;

    for (int price : prices) {
      if (price < minPrice) [[unlikely]] {
        minPrice = price;
      } else {
        maxProfit = std::max(maxProfit, price - minPrice);
      }
    }

    return maxProfit;
  }
};