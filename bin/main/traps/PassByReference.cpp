#include <iostream>
#include <vector>
#include <string>

/**
 * C++ 版本的 Pass By Reference 實驗
 */

// 注意這個 & 符號：代表 Pass By Reference (傳引用)
// 這代表函式內的 list 跟外部的 mainList 共用同一個記憶體位址
void test(std::vector<std::string>& list) {
    // 在 C++ 中，這樣寫會直接「清空並重新賦值」原本的那台電視
    // 而不是只改掉影印的遙控器
    list = {"Modified!"}; 
}

int main() {
    // 相當於 Java 的 ArrayList<String>
    std::vector<std::string> mainList;
    mainList.push_back("Original");

    std::cout << "Before test: " << mainList[0] << std::endl;

    test(mainList);

    // 結果會是 ["Modified!"]！
    // 因為 test 函式直接修改了 mainList 的記憶體內容
    std::cout << "After test: " << mainList[0] << std::endl;

    return 0;
}