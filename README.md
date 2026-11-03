# leetcode-template

[![java](https://github.com/JoblessKevin/leetcode-template/actions/workflows/java.yml/badge.svg)](https://github.com/JoblessKevin/leetcode-template/actions/workflows/java.yml)
[![python](https://github.com/JoblessKevin/leetcode-template/actions/workflows/python.yml/badge.svg)](https://github.com/JoblessKevin/leetcode-template/actions/workflows/python.yml)
[![cpp](https://github.com/JoblessKevin/leetcode-template/actions/workflows/cpp.yml/badge.svg)](https://github.com/JoblessKevin/leetcode-template/actions/workflows/cpp.yml)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
![Java](https://img.shields.io/badge/Java-21-blue.svg)
![Python](https://img.shields.io/badge/Python-3.13-blue.svg)
![C++](https://img.shields.io/badge/C++-20-blue.svg)

Preparing for future interviews with clean and reusable templates.

## 專案結構

```
java/      Gradle 專案（JUnit 5、Spotless）
python/    pytest + ruff，解法與測試寫在同一個檔案
cpp/       CMake + GoogleTest，解法與 TEST() 寫在同一個檔案
notes/     跟語言無關的筆記
```

每個語言底下都用相同的分類：`problems/<topic>/`、`templates/<topic>/`、`traps/`……
命名則依各語言慣例（Java `binarySearch/TwoSum.java`、Python `binary_search/two_sum.py`、C++ `binary_search/two_sum.cpp`）。

## 執行

```bash
# Java
cd java
./gradlew test

# Python
cd python
python -m venv .venv
.venv/Scripts/pip install --group dev   # macOS / Linux: .venv/bin/pip
.venv/Scripts/pytest

# C++ (需要 CMake 與 C++20 編譯器)
cd cpp
cmake -S . -B build
cmake --build build
ctest --test-dir build --output-on-failure
```

新增 Python 或 C++ 題目時，直接建立檔案即可，不用改設定：pytest 會掃描所有 `.py` 檔，CMake 也會自動 glob 新的 `.cpp` 檔（新增檔案後要重新執行 `cmake`）。

## 題目索引

| 題目 | Java | Python | C++ |
| :--- | :---: | :---: | :---: |
| 1. Two Sum | [✓](java/src/main/java/problems/array/TwoSum.java) | [✓](python/problems/array/two_sum.py) | [✓](cpp/problems/array/two_sum.cpp) |
| Binary Search (template) | [✓](java/src/main/java/templates/binarySearch/BinarySearch.java) | [✓](python/templates/binary_search/binary_search.py) | [✓](cpp/templates/binary_search/binary_search.hpp) |

## 筆記

- [Bit Manipulation](notes/bit.md)
- [Java → Python 轉換筆記](notes/java-to-python.md)
- [Python 學習筆記](notes/python.md)

## design pattern

Singleton Pattern(單例模式): @Bean

Strategy Pattern (策略模式): implements interface (Runtime)

Factory Pattern (工廠模式): implements interface (new Object())

Observer Pattern (觀察者模式): Pub / Sub

MVC Pattern (Model–View–Controller)

Utility Class Pattern (工具類別模式)
