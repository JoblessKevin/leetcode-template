# Python 學習筆記

刷題時常用的 Python 語法整理。與 Java 的差異請見 [java-to-python.md](java-to-python.md)。

---

## 1. `enumerate` vs `range`

### A. 它們各自在做什麼

| | `range(n)` | `enumerate(iterable)` |
| :--- | :--- | :--- |
| 產生的東西 | 整數 `0, 1, ..., n-1` | `(index, 值)` 的配對 |
| 需要的輸入 | 數字 | 任何可迭代的東西（list、字串、tuple…） |
| 適合的情境 | 只需要 **index**，或需要**操控 index** | 同時需要 **index 和值** |
| 對應的 Java 寫法 | `for (int i = 0; i < n; i++)` | 同上，再加一行 `int x = nums[i];` |

```python
nums = [10, 20, 30]

for i in range(len(nums)):
    print(i, nums[i])        # 0 10 / 1 20 / 2 30

for i, x in enumerate(nums):
    print(i, x)              # 0 10 / 1 20 / 2 30（結果相同）
```

兩種寫法的輸出完全一樣，但 `enumerate` 省掉了 `nums[i]` 這個取值動作，意圖也更明確：「我要走訪每個元素，順便知道它的位置」。

---

### B. `enumerate` 的拆解原理

`enumerate` 每次會產生一個 tuple，`for i, x in ...` 則是把這個 tuple **拆開**（unpacking）成兩個變數：

```python
list(enumerate("abc"))       # [(0, 'a'), (1, 'b'), (2, 'c')]

for pair in enumerate("abc"):
    print(pair)              # (0, 'a')，沒有拆開時拿到的是整個 tuple

for i, ch in enumerate("abc"):
    print(i, ch)             # 0 a，拆開成兩個變數
```

#### 指定起始編號：`start`

```python
for rank, name in enumerate(["Amy", "Bob"], start=1):
    print(rank, name)        # 1 Amy / 2 Bob
```

> ⚠️ `start` 只會改變**編號**，不會跳過元素。`enumerate(nums, start=1)` 還是從 `nums[0]` 開始走，只是編號從 1 開始算。想跳過第一個元素，要寫 `enumerate(nums[1:], start=1)`。

---

### C. 什麼時候用哪一個

#### ✓ 用 `enumerate`：需要 index，也需要值

```python
# Two Sum：值拿來計算，index 拿來回傳答案
def twoSum(nums: list[int], target: int) -> list[int]:
    seen = {}
    for i, x in enumerate(nums):
        if target - x in seen:
            return [seen[target - x], i]
        seen[x] = i
    return []
```

```python
# 建立「值 → index」的對照表
index_of = {x: i for i, x in enumerate(nums)}
```

#### ✓ 用 `range`：需要「操控」index

只要 index 不是單純的 `0, 1, 2, ...`，就該用 `range`：

```python
for i in range(1, n): ...              # 從 1 開始（例如 dp[i] 依賴 dp[i - 1]）
for i in range(n - 1, -1, -1): ...     # 倒著走
for i in range(0, n, 2): ...           # 每次跳 2
for i in range(n - 1): ...             # 需要存取 nums[i + 1]，所以只走到 n-2
for i in range(k, n): ...              # 滑動視窗：右端點從 k 開始
```

```python
# 需要同時看相鄰元素時，用 range 比較直覺
for i in range(len(nums) - 1):
    if nums[i] > nums[i + 1]:
        ...
```

#### ✓ 用 `range`：只需要重複 n 次，跟陣列無關

```python
grid = [[0] * n for _ in range(m)]     # _ 表示「這個變數我不會用到」
for _ in range(k):
    heapq.heappop(heap)
```

#### ✗ 避免：`range(len())` 卻只用到值

```python
for i in range(len(nums)):
    total += nums[i]         # ✗ 完全沒用到 i 本身

for x in nums:
    total += x               # ✓ 不需要 index 時，直接走訪值
```

---

### D. 決策流程

```
需要 index 嗎？
├─ 不需要 → for x in nums
└─ 需要
   ├─ index 是單純的 0, 1, 2, ... 而且也需要值 → for i, x in enumerate(nums)
   └─ 要從中間開始 / 倒著走 / 跳著走 / 存取 i±1 → for i in range(...)
```

---

### E. 常見組合

#### 倒著走，同時需要 index 和值

```python
# 方法一：range 倒著走（最常用，index 是原本陣列的位置）
for i in range(len(nums) - 1, -1, -1):
    x = nums[i]

# 方法二：reversed + enumerate（⚠️ 這樣拿到的 i 是倒過來之後的編號，不是原本的 index）
for i, x in enumerate(reversed(nums)):
    ...                      # nums = [10, 20, 30] 時，i=0 對應的是 30
```

#### 同時走兩個陣列：`zip`

```python
for a, b in zip(nums1, nums2): ...                   # 只要值
for i, (a, b) in enumerate(zip(nums1, nums2)): ...   # 也要 index（注意括號）
```

> `zip` 會在**最短**的陣列走完時停止，不會報錯。

#### 二維陣列

```python
for r, row in enumerate(grid):
    for c, val in enumerate(row):
        if val == 1:
            ...              # 同時拿到座標 (r, c) 和格子的值
```

---

### F. 陷阱 (Pitfalls)

#### 1. 迴圈中修改 `i` 沒有效果

```python
for i in range(5):
    if i == 1:
        i += 2               # ✗ 沒有效果：下一輪 i 還是會變成 2
    print(i)                 # 0 3 2 3 4
```

Java 的 `i++` 會影響下一輪，但 Python 每一輪都會從 `range` **重新取下一個值**，覆蓋掉你的修改。需要跳著走（例如雙指標、跳過重複元素）時，請改用 `while`：

```python
i = 0
while i < n:
    if ...:
        i += 2               # ✓ 跳過
    else:
        i += 1
```

#### 2. 走訪時刪除元素

```python
for i, x in enumerate(nums):
    if x == 0:
        nums.pop(i)          # ✗ 刪除後元素會往前移，下一個元素會被跳過
```

改成建立新的 list，或是倒著走：

```python
nums = [x for x in nums if x != 0]              # ✓ 建立新的 list

for i in range(len(nums) - 1, -1, -1):          # ✓ 倒著刪，不影響還沒走到的 index
    if nums[i] == 0:
        nums.pop(i)
```

#### 3. `range` 不包含結尾

```python
list(range(1, 5))            # [1, 2, 3, 4]，不包含 5
list(range(5, 0, -1))        # [5, 4, 3, 2, 1]，不包含 0
list(range(n - 1, -1, -1))   # 要倒著走到 0，結尾要寫 -1
```

#### 4. 迴圈結束後的變數值

```python
for i in range(3):
    pass
print(i)                     # 2（Python 沒有區塊作用域，i 在迴圈外還存在）

for i in range(0):
    pass
print(i)                     # 還是 2：range(0) 一圈都沒跑，i 不會被重新賦值
```

---

### G. 效能

* `range` 和 `enumerate` 都是**惰性**的：不會先建立完整的 list，而是需要時才產生下一個值。所以 `range(10**9)` 也不會吃掉大量記憶體。
* 兩者的速度差異很小，**選擇的依據應該是可讀性**，不是效能。
* 想把結果看成 list（例如除錯時），可以用 `list(range(5))`、`list(enumerate(nums))`。

---

### H. 一句話總結

> **需要值就直接走訪，需要 index 和值就用 `enumerate`，需要操控 index 就用 `range`。**

---

## 2. list 的轉換 (Conversions)

### A. 轉換的寫法：型別名稱當函式用

Python 沒有 `nums.set()`、`nums.toSet()` 這種方法。`set`、`tuple`、`dict`、`list` 這些型別本身就能當函式呼叫，把資料丟進去，就會建立一個新的物件：

```python
nums = [3, 1, 3, 2]

set(nums)        # ✓ {1, 2, 3}
nums.set()       # ✗ AttributeError: 'list' object has no attribute 'set'
```

> 轉換一定會**建立新的物件**，原本的 `nums` 不會改變。

---

### B. list → 其他型別

| 轉換成 | 寫法 | 結果（`nums = [3, 1, 3, 2]`） | 用途 |
| :--- | :--- | :--- | :--- |
| `set` | `set(nums)` | `{1, 2, 3}` | 去除重複、O(1) 查詢 |
| `tuple` | `tuple(nums)` | `(3, 1, 3, 2)` | 當作 dict 的 key 或放進 set |
| `frozenset` | `frozenset(nums)` | `frozenset({1, 2, 3})` | 不可變的 set，可以當作 dict 的 key |
| `dict`（值 → index） | `{x: i for i, x in enumerate(nums)}` | `{3: 2, 1: 1, 2: 3}` | 查值的位置（重複的值會保留最後一個 index） |
| `dict`（兩個 list 配對） | `dict(zip(keys, values))` | — | 建立對照表 |
| `dict`（成對的 list） | `dict([["a", 1], ["b", 2]])` | `{'a': 1, 'b': 2}` | 每個元素剛好是 `[key, value]` 時 |
| `dict`（全部給預設值） | `dict.fromkeys(nums, 0)` | `{3: 0, 1: 0, 2: 0}` | 初始化計數或狀態 |
| `Counter` | `Counter(nums)` | `Counter({3: 2, 1: 1, 2: 1})` | 計算每個值出現幾次 |
| `deque` | `deque(nums)` | `deque([3, 1, 3, 2])` | 當作佇列，頭尾都能 O(1) 存取 |
| `str` | `"".join(map(str, nums))` | `"3132"` | 數字 list 接成字串 |
| 堆 | `heapq.heapify(nums)` | ⚠️ 原地修改，回傳 `None` | 建立最小堆，O(n) |

```python
from collections import Counter, defaultdict, deque
import heapq
```

#### 去除重複，但保留原本的順序

```python
list(set(nums))              # ✗ set 沒有順序，結果的順序不一定
list(dict.fromkeys(nums))    # ✓ [3, 1, 2]，dict 會保留插入順序
```

#### 字串 list 接成字串

```python
"".join(["a", "b", "c"])     # "abc"
" ".join(["a", "b", "c"])    # "a b c"
"".join([1, 2, 3])           # ✗ TypeError：join 只接受字串，數字要先用 map(str, ...) 轉換
"".join(map(str, [1, 2, 3])) # ✓ "123"
str([1, 2, 3])               # ✗ "[1, 2, 3]"：連中括號、逗號、空白都變成字串的一部分
```

#### `Counter`：查不到的 key 回傳 0

`Counter` 相當於 Java 裡用 `HashMap` 搭配 `getOrDefault(x, 0) + 1` 計數，但寫起來更短：

```python
count = Counter([1, 1, 2, 3, 3, 3])    # Counter({3: 3, 1: 2, 2: 1})
count[3]                               # 3
count[99]                              # 0：不存在的 key 回傳 0，不會報錯，也不會把 99 加進去

d = {}
d[99]                                  # ✗ KeyError：一般的 dict 會報錯
d.get(99, 0)                           # ✓ 0
```

> 兩個 `Counter` 可以直接用 `==` 比較，所以 Valid Anagram 可以寫成 `Counter(s) == Counter(t)`。

#### `deque`：頭尾操作都是 O(1)

對應 Java 的 `ArrayDeque` / `LinkedList`。寫 BFS 時一定要用它，因為 list 的 `pop(0)` 是 O(n)：

```python
q = deque([1, 2, 3])
q.popleft()                  # O(1) 移除並回傳最左邊的 1
q.append(4)                  # O(1) 加到最右邊：deque([2, 3, 4])
q.appendleft(0)              # O(1) 加到最左邊：deque([0, 2, 3, 4])
q.pop()                      # O(1) 移除並回傳最右邊的 4
```

#### `heapq.heapify`：原地轉成最小堆

對應 Java 的 `PriorityQueue`。跟前面幾種不同，`heapify` **不會建立新物件**，而是直接重新排列原本的 list：

```python
nums = [3, 1, 4, 1, 5]
heapq.heapify(nums)          # O(n)，nums 本身變成最小堆，回傳 None
heapq.heappop(nums)          # O(log n) 取出最小值 1
nums[0]                      # 查看目前的最小值（不取出）：1
```

> `heapify` 後的 `nums` **不是排序好的 list**，只保證 `nums[0]` 是最小值。想要排序結果請用 `sorted()`（見第 3 節）。

---

### C. 其他型別 → list

| 來源 | 寫法 | 結果 |
| :--- | :--- | :--- |
| 字串 | `list("abc")` | `['a', 'b', 'c']` |
| 用空白分隔的字串 | `"a b  c".split()` | `['a', 'b', 'c']` |
| 用指定符號分隔的字串 | `"1,2,3".split(",")` | `['1', '2', '3']` |
| `range` | `list(range(3))` | `[0, 1, 2]` |
| `set` | `list({3, 1, 2})` | 順序不一定，需要排序時用 `sorted({3, 1, 2})` |
| `dict` 的 key | `list(d)` 或 `list(d.keys())` | key 的 list |
| `dict` 的 value | `list(d.values())` | value 的 list |
| `dict` 的配對 | `list(d.items())` | `[(key, value), ...]` |
| `tuple` | `list((1, 2))` | `[1, 2]` |

#### 數字和每一位數的互轉

```python
list(map(int, str(1234)))           # [1, 2, 3, 4]，整數拆成每一位數
int("".join(map(str, [1, 2, 3])))   # 123，每一位數組回整數（Plus One、Add to Array-Form）
list(map(int, "1 2 3".split()))     # [1, 2, 3]，字串轉成數字 list
```

> `map` 會回傳惰性的迭代器，需要 list 時要再包一層 `list(...)`。

---

### D. list → list（複製與變形）

#### 複製

```python
copy1 = nums[:]                # 淺複製（最常見的寫法）
copy2 = list(nums)             # 淺複製
copy3 = nums.copy()            # 淺複製

import copy
grid_copy = copy.deepcopy(grid)   # 深複製：二維陣列要用這個
```

> ⚠️ **淺複製只複製最外層**。二維陣列用 `grid[:]` 複製時，裡面每一列仍然是同一個 list，修改 `grid_copy[0][0]` 會改到原本的 `grid`。只需要複製兩層時，也可以寫 `[row[:] for row in grid]`。

#### 反轉

```python
nums[::-1]                     # 回傳反轉後的新 list
list(reversed(nums))           # 同上；reversed 回傳的是迭代器
nums.reverse()                 # 原地反轉，回傳 None
```

#### 二維陣列

```python
matrix = [[1, 2, 3], [4, 5, 6]]

[list(row) for row in zip(*matrix)]     # 轉置：[[1, 4], [2, 5], [3, 6]]
[x for row in matrix for x in row]      # 攤平成一維：[1, 2, 3, 4, 5, 6]
```

---

### E. 陷阱 (Pitfalls)

#### 1. list 不能放進 set，也不能當 dict 的 key

```python
seen = set()
seen.add([1, 2])          # ✗ TypeError: unhashable type: 'list'
seen.add((1, 2))          # ✓ 先轉成 tuple
```

只有**不可變**的型別（`int`、`str`、`tuple`、`frozenset`）才能放進 set 或當作 dict 的 key。

#### 2. `set("abc")` 和 `{"abc"}` 不一樣

```python
set("abc")                # {'a', 'b', 'c'}：把字串拆成字元
{"abc"}                   # {'abc'}：只有一個元素的 set
set()                     # 空的 set
{}                        # ⚠️ 這是空的 dict，不是空的 set
```

#### 3. 轉成 set 會失去順序和重複的資訊

`len(set(nums)) != len(nums)` 可以判斷「有沒有重複」（Contains Duplicate），但沒辦法知道「哪個值重複了幾次」。需要次數時改用 `Counter`。

#### 4. `dict.fromkeys` 的預設值不要用可變物件

```python
groups = dict.fromkeys(["a", "b"], [])
groups["a"].append(1)
groups                    # ✗ {'a': [1], 'b': [1]}：所有 key 共用「同一個」list

groups = {k: [] for k in ["a", "b"]}     # ✓ 每個 key 各自建立一個新的 list
groups = defaultdict(list)               # ✓ 或用 defaultdict，用到時才建立
```

這跟 `[[0] * n] * m` 是同一種問題（見 [java-to-python.md](java-to-python.md) 第 4 節 A）：預設值只建立了一次，然後被重複使用。`0`、`""` 這類不可變的值則沒有這個問題。

---

## 3. 排序：`sort()` 與 `sorted()`

### A. 兩種基本寫法

```python
nums = [3, 1, 2]

nums.sort()              # 原地排序，nums 變成 [1, 2, 3]，回傳 None
new = sorted(nums)       # 回傳新的 list，原本的 nums 不變
```

| | `nums.sort()` | `sorted(x)` |
| :--- | :--- | :--- |
| 修改原本的資料 | ✓ 原地修改 | ✗ 回傳新的 list |
| 回傳值 | `None` | 排好的新 list |
| 能用在哪些型別 | 只有 list | 任何可迭代的東西：字串、tuple、set、dict… |
| 額外空間 | 不用複製 | 會複製一份 |
| 對應的 Java 寫法 | `Arrays.sort(nums)` | 先複製一份再排序 |

> ⚠️ 經典陷阱：`nums = nums.sort()` 會讓 `nums` 變成 `None`。用 `sort()` 時不要接回傳值。

---

### B. 遞減排序：`reverse=True`

```python
nums.sort(reverse=True)            # [3, 2, 1]
sorted(nums, reverse=True)
```

---

### C. 自訂排序依據：`key`

`key` 是一個函式：對每個元素算出「要拿來比較的值」，再依照這個值排序。

```python
words = ["banana", "kiwi", "apple"]
words.sort(key=len)                         # 依長度：['kiwi', 'apple', 'banana']

nums = [-4, 1, -2, 3]
nums.sort(key=abs)                          # 依絕對值：[1, -2, 3, -4]

intervals = [[3, 5], [1, 4], [2, 6]]
intervals.sort(key=lambda x: x[0])          # 依起點排序（Merge Intervals）
```

`lambda x: x[0]` 是匿名函式，等同於：

```python
def get_start(x):
    return x[0]
```

---

### D. 多條件排序：`key` 回傳 tuple

tuple 的比較方式是**先比第一個元素，相同才比第二個**，所以讓 `key` 回傳 tuple，就能做多條件排序：

```python
people = [[7, 0], [4, 4], [7, 1], [5, 0]]

people.sort(key=lambda p: (p[0], p[1]))     # 第一欄遞增，相同時第二欄遞增
people.sort(key=lambda p: (-p[0], p[1]))    # 第一欄遞減，相同時第二欄遞增（Queue Reconstruction by Height）
```

對應的 Java 寫法：

```java
Arrays.sort(people, (a, b) -> a[0] != b[0] ? b[0] - a[0] : a[1] - b[1]);
```

> 用負號做遞減只適用於**數字**。字串沒辦法加負號，請見 F 小節。

---

### E. 常見的刷題用法

```python
# 字串排序：sorted 會回傳字元的 list，要用 join 接回字串
key = "".join(sorted("eat"))                # "aet"（Group Anagrams）

# dict 依 value 排序
count = {"a": 3, "b": 1, "c": 2}
sorted(count.items(), key=lambda kv: kv[1])                 # [('b', 1), ('c', 2), ('a', 3)]
sorted(count, key=count.get, reverse=True)                  # 只取 key，依次數遞減：['a', 'c', 'b']

# 依「次數遞減，次數相同時依字母遞增」
sorted(count.items(), key=lambda kv: (-kv[1], kv[0]))

# 只需要前 k 個時，不用整個排序
import heapq
heapq.nlargest(2, nums)                     # 最大的 2 個，O(n log k)

from collections import Counter
Counter("aabbbc").most_common(2)            # [('b', 3), ('a', 2)]（Top K Frequent）
```

---

### F. 無法用 `key` 表達時：`cmp_to_key`

有些排序規則沒辦法寫成「每個元素算出一個值」，必須**兩兩比較**才能決定順序。這時用 `cmp_to_key`，寫法最接近 Java 的 `Comparator`：

```python
from functools import cmp_to_key

def compare(a: str, b: str) -> int:
    # 回傳負數：a 排前面；正數：b 排前面；0：一樣
    if a + b > b + a:
        return -1
    if a + b < b + a:
        return 1
    return 0

nums = ["3", "30", "34", "5", "9"]
nums.sort(key=cmp_to_key(compare))          # ['9', '5', '34', '3', '30']（Largest Number）
```

---

### G. 排序的特性

* **穩定排序**：key 相同的元素，會保持原本的先後順序。所以可以分成兩次排序來達到多條件：**先依次要條件排，再依主要條件排**。這也是字串遞減時的解法：

  ```python
  pairs = [("b", 1), ("a", 2), ("c", 1)]
  pairs.sort(key=lambda p: p[0], reverse=True)    # 先依字母遞減（次要條件）
  pairs.sort(key=lambda p: p[1])                  # 再依數字遞增（主要條件）
  # [('c', 1), ('b', 1), ('a', 2)]
  ```

* **時間 O(n log n)，空間 O(n)**：Python 用的是 Timsort，就算是 `nums.sort()` 也需要額外空間。所以「排序後比較相鄰元素」的解法，在 Python 裡不是 O(1) 空間。
* **型別要能互相比較**：`[1, "a"]` 這種混合型別的 list，排序時會拋出 `TypeError`。

---

### H. `sort()` 還是 `sorted()`？

| 情境 | 選擇 |
| :--- | :--- |
| 排序後不再需要原本的順序 | `nums.sort()`：省下複製的空間 |
| 原本的順序之後還會用到 | `sorted(nums)` |
| 資料不是 list（字串、set、dict） | `sorted(x)`：只有它能用 |
| 函式的參數是外面傳進來的 | `sorted(nums)`：避免改到呼叫端的資料 |

最後一種情況的例子：在 [p0217_contains_duplicate.py](../python/problems/array/p0217_contains_duplicate.py) 裡用 `parametrize` 測試多種寫法時，每種寫法拿到的是**同一個 list**。如果其中一種寫法用 `nums.sort()` 原地排序，後面的寫法拿到的就是已經排過的資料。

```python
def containsDuplicate_sort(self, nums: list[int]) -> bool:
    """排序後比較相鄰元素。時間 O(n log n)。"""
    nums = sorted(nums)                     # 不改到呼叫端傳進來的 list
    for i in range(len(nums) - 1):          # 要存取 nums[i + 1]，所以用 range（見第 1 節 C）
        if nums[i] == nums[i + 1]:
            return True
    return False
```
