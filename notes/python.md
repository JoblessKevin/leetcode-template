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

#### ✗ 避免：用了 `enumerate`，卻沒用到 index

```python
for i, char in enumerate(s):
    count[ord(char) - ord("a")] += 1    # ✗ i 完全沒用到

for char in s:
    count[ord(char) - ord("a")] += 1    # ✓
```

> 判斷方式：寫完迴圈後檢查一下，`i` 有沒有出現在迴圈內容裡。沒有出現，就不需要 `enumerate`。實際案例見 [java-to-python.md](java-to-python.md) 第 11 節（Valid Anagram）。

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
import heapq
from collections import Counter, defaultdict, deque
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

> 兩個 `Counter` 可以直接用 `==` 比較，所以 Valid Anagram 可以寫成 `Counter(s) == Counter(t)`。完整的 `Counter` 說明見第 5 節。

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

---

## 4. `ord()` 與 `chr()`：字元與編號的轉換

### A. ordinal 是什麼

`ord` 是 **ordinal**（序數、編號）的縮寫。電腦裡的每個字元，在 **Unicode** 編碼表中都有一個固定的編號（code point），`ord()` 查的就是這個編號：

```python
ord('a')        # 97：'a' 在 Unicode 表中排第 97 號
chr(97)         # 'a'：反過來，第 97 號是哪個字元
```

| 函式 | 方向 | 輸入 | 輸出 |
| :--- | :--- | :--- | :--- |
| `ord(c)` | 字元 → 編號 | **長度剛好為 1** 的字串 | 整數 |
| `chr(n)` | 編號 → 字元 | `0` ~ `0x10FFFF` 的整數 | 長度為 1 的字串 |

兩者互為反函式：`chr(ord(c)) == c`、`ord(chr(n)) == n`。

> **為什麼 Java 不用？** Java 的 `char` 本身就是一個整數（UTF-16 編碼值），所以 `c - 'a'` 時會**自動**轉成數字。Python 沒有 `char`（見 [java-to-python.md](java-to-python.md) 第 3 節 A），字元就是字串，所以必須用 `ord()` **明確**轉成數字才能運算，算完再用 `chr()` 轉回來。

---

### B. 必背的編號

前 128 號和 ASCII 編碼表完全相同，刷題時記住這幾個就夠了：

| 字元 | 編號 | 範圍 |
| :--- | :--- | :--- |
| `' '`（空白） | 32 | |
| `'0'` | 48 | `'0'` ~ `'9'` → 48 ~ 57 |
| `'A'` | 65 | `'A'` ~ `'Z'` → 65 ~ 90 |
| `'a'` | 97 | `'a'` ~ `'z'` → 97 ~ 122 |

* 同一個字母的大小寫**相差 32**：`ord('a') - ord('A') == 32`。
* 實際寫程式時**不要直接寫數字**，用 `ord('a')` 取代 `97`，可讀性比較好，也不會記錯。

---

### C. 核心技巧：「減去起點」變成 0 起算的 index

幾乎所有 `ord` 的用法，都是同一個概念：**減去範圍的起點，就能把字元對應到 0 開始的 index**。

```python
ord(c) - ord('a')       # 'a' → 0, 'b' → 1, ..., 'z' → 25
ord(c) - ord('A')       # 'A' → 0, ..., 'Z' → 25
ord(c) - ord('0')       # '0' → 0, ..., '9' → 9
```

反過來，**加回起點**再用 `chr()` 就能從 index 轉回字元：

```python
chr(i + ord('a'))       # 0 → 'a', 1 → 'b', ..., 25 → 'z'
```

---

### D. 常見的刷題用法

#### 1. 26 格的計數陣列（Valid Anagram、Group Anagrams）

```python
count = [0] * 26
for c in "banana":
    count[ord(c) - ord('a')] += 1

# 從計數陣列還原成排序好的字串："aaabnn"
"".join(chr(i + ord('a')) * n for i, n in enumerate(count))
```

> 如果題目不限定小寫英文字母，直接用 `Counter(s)` 會比較簡單（見第 2 節 B）。

#### 2. 字元轉數字（String to Integer、Add Strings）

```python
ord('7') - ord('0')     # 7
int('7')                # 7：單一位數時，int() 也可以，而且更直觀
```

> 逐位計算時，兩種寫法都很常見。`ord(c) - ord('0')` 是 Java `c - '0'` 的直接翻譯，`int(c)` 則是比較 Python 的寫法。

#### 3. 字母位移（Caesar Cipher、Shifting Letters）

```python
def shift(c: str, k: int) -> str:
    return chr((ord(c) - ord('a') + k) % 26 + ord('a'))

shift('x', 3)           # 'a'：超過 'z' 會繞回 'a'
shift('a', -1)          # 'z'：Python 的 % 對負數也會回傳正數，不用額外處理
```

步驟拆解：**轉成 0 ~ 25 的 index → 加上位移 → `% 26` 繞回範圍內 → 加回起點 → 轉回字元**。

#### 4. 大小寫轉換

```python
chr(ord('a') - 32)      # 'A'
chr(ord('a') ^ 32)      # 'A'：用 XOR 切換第 5 個 bit，大小寫互換（見 bit.md）
'a'.upper()             # 'A'：實務上直接用字串方法最清楚
```

#### 5. 用 bitmask 記錄出現過的字母（Maximum Product of Word Lengths）

```python
mask = 0
for c in "abca":
    mask |= 1 << (ord(c) - ord('a'))    # 第 k 個 bit 代表第 k 個字母有出現
# mask == 0b111：a、b、c 都出現過
```

#### 6. 產生字母序列

```python
[chr(c) for c in range(ord('a'), ord('e'))]    # ['a', 'b', 'c', 'd']

import string
string.ascii_lowercase       # 'abcdefghijklmnopqrstuvwxyz'
string.digits                # '0123456789'
```

> 需要完整的 26 個字母時，用 `string.ascii_lowercase` 最簡單，不用自己寫迴圈。

---

### E. 陷阱 (Pitfalls)

#### 1. `ord()` 只接受「一個字元」

```python
ord('ab')       # ✗ TypeError: ord() expected a character, but string of length 2 found
ord('')         # ✗ TypeError: ord() expected a character, but string of length 0 found
```

傳整個字串進去是常見的錯誤，記得在迴圈裡**一次傳一個字元**。

#### 2. `chr()` 的範圍

```python
chr(-1)         # ✗ ValueError: chr() arg not in range(0x110000)
```

做位移運算時忘了 `% 26`，就有可能算出範圍外的數字，或是得到不是字母的字元。

#### 3. 不只是英文字母

`ord()` 支援所有 Unicode 字元，不限於 ASCII：

```python
ord('中')       # 20013
ord('😀')       # 128512
len('😀')       # 1：Python 把 emoji 當作一個字元
```

> 跟 Java 不同：Java 的 `char` 是 16 位元，`"😀".length()` 會得到 `2`，`charAt(0)` 只會拿到半個 emoji。Python 的字串則是以完整的字元為單位。

因此 `[0] * 26` 的計數陣列，只適用於題目**保證只有小寫英文字母**的情況。輸入可能包含其他字元時，`ord(c) - ord('a')` 會超出 0 ~ 25 的範圍，導致 `IndexError`，甚至是負數 index 默默存取到錯誤的位置。這時請改用 `Counter` 或 `dict`（見第 5 節）。

---

## 5. `Counter`：計數專用的 dict

### A. 概念

`Counter` 是 `collections` 模組裡的類別，專門用來**計算每個元素出現幾次**。它本身就是 `dict` 的子類別：key 是元素，value 是次數，所以 dict 能用的操作它都能用。

```python
from collections import Counter

c = Counter("banana")       # Counter({'a': 3, 'n': 2, 'b': 1})
c["a"]                      # 3
```

對照 Java 的寫法，同樣是計數：

```java
Map<Character, Integer> count = new HashMap<>();
for (char c : s.toCharArray()) {
    count.put(c, count.getOrDefault(c, 0) + 1);    // 或 count.merge(c, 1, Integer::sum)
}
```

```python
count = Counter(s)          # Python 一行就完成
```

---

### B. 建立方式

```python
Counter("banana")                   # 從字串：計算每個字元
Counter([1, 1, 2, 3])               # 從 list：計算每個元素
Counter({"a": 2, "b": 1})           # 從 dict：直接指定次數
Counter(a=2, b=1)                   # 用關鍵字參數指定次數（key 只能是合法的變數名稱）
Counter()                           # 空的，之後再逐一累加
```

---

### C. 最重要的特性：查不到的 key 回傳 0

```python
c = Counter("banana")
c["z"]              # 0：不會報錯
"z" in c            # False：查詢不會把 "z" 加進去

c["z"] += 1         # 可以直接累加，不用先檢查 key 存不存在
```

這讓計數的寫法非常簡潔。三種寫法的比較：

| 寫法 | 查不到的 key | 是否會新增 key |
| :--- | :--- | :--- |
| `dict`：`d[k]` | ❌ `KeyError` | — |
| `dict`：`d.get(k, 0)` | 回傳 `0` | 不會 |
| `defaultdict(int)`：`d[k]` | 回傳 `0` | **會**（查一次就多一個 key） |
| `Counter`：`c[k]` | 回傳 `0` | 不會 |

> `defaultdict(int)` 也能用來計數，但光是「查詢」就會新增 key，這在需要比較 `len(d)` 或走訪所有 key 時，可能造成 bug。

---

### D. 常用方法

| 方法 | 作用 | 範例 |
| :--- | :--- | :--- |
| `c.most_common(k)` | 次數最多的 k 個，依次數遞減 | `Counter("aabbbc").most_common(1)` → `[('b', 3)]` |
| `c.most_common()` | 全部依次數遞減排列 | |
| `c.update(x)` | **加上** x 的計數 | `Counter("ab")` 更新 `"abc"` → `a: 2, b: 2, c: 1` |
| `c.subtract(x)` | **減去** x 的計數（可以變成 0 或負數） | `Counter("abc")` 減去 `"aab"` → `a: -1, b: 0, c: 1` |
| `c.total()` | 所有次數的總和（Python 3.10+） | `Counter("banana").total()` → `6` |
| `c.elements()` | 依次數展開成元素 | `Counter(a=2, b=1)` → `a, a, b` |

---

### E. Counter 之間的運算

```python
Counter("ab") + Counter("bc")       # 相加：a: 1, b: 2, c: 1
Counter("aab") - Counter("abc")     # 相減：a: 1（⚠️ 結果是 0 或負數的 key 會被移除）
Counter("aab") & Counter("abb")     # 交集，取較小值：a: 1, b: 1
Counter("aab") | Counter("abb")     # 聯集，取較大值：a: 2, b: 2
Counter(s) == Counter(t)            # 比較內容：Valid Anagram
```

> `-` 運算子和 `subtract()` 的差別：`-` 會**移除** 0 和負數的結果，`subtract()` 則會**保留**。

---

### F. 刷題常見用法

#### 1. 比較兩個字串的組成（Valid Anagram）

```python
return Counter(s) == Counter(t)
```

#### 2. 判斷 A 能不能由 B 組成（Ransom Note）

```python
return not (Counter(ransomNote) - Counter(magazine))    # 相減後沒有剩下，代表 magazine 夠用
```

#### 3. 前 K 個高頻元素（Top K Frequent Elements）

```python
return [x for x, _ in Counter(nums).most_common(k)]
```

#### 4. 滑動視窗中的計數

```python
window = Counter()
window[s[right]] += 1               # 右端加入

window[s[left]] -= 1                # 左端移出
if window[s[left]] == 0:
    del window[s[left]]             # ⚠️ 次數歸零時要手動刪除，len(window) 才會正確
left += 1
```

> 用 `-=` 減到 0 時，key **不會自動消失**。如果題目用 `len(window)` 判斷「視窗內有幾種不同的元素」，就必須在歸零時 `del`。

---

### G. 效能

* 建立 `Counter(x)`：O(n)。
* 查詢、累加單一 key：O(1)，跟 dict 一樣。
* `most_common(k)`：O(n log k)；不給 k 時是完整排序，O(n log n)。

---

## 6. import 的寫法

### A. 四種基本寫法

```python
import math                              # 1. 匯入整個模組，使用時要加模組名稱
math.gcd(12, 18)

from collections import Counter, deque   # 2. 只匯入需要的名稱，使用時不用加模組名稱
Counter("abc")

import numpy as np                       # 3. 取別名（模組名稱太長時）

from math import *                       # 4. ✗ 匯入全部：不知道到底匯入了哪些名稱，容易跟自己的變數撞名
```

| 寫法 | 使用方式 | 適合的情況 |
| :--- | :--- | :--- |
| `import heapq` | `heapq.heappush(...)` | 函式名稱很一般，加上模組名稱比較清楚 |
| `from collections import Counter` | `Counter(...)` | 很常用的類別，每次都加前綴太冗長 |

> 刷題時的慣例：`heapq`、`math`、`bisect` 常用 `import 模組`；`Counter`、`defaultdict`、`deque`、`cache` 常用 `from ... import`。兩種都沒有對錯，同一個檔案內保持一致就好。

#### 與 Java 的差異

| | Java | Python |
| :--- | :--- | :--- |
| 匯入的單位 | 類別：`import java.util.HashMap;` | 模組（就是一個 `.py` 檔）或模組裡的名稱 |
| 全部匯入 | `import java.util.*;`（常見、可接受） | `from x import *`（**不建議**） |
| 內建、免 import 的東西 | `java.lang.*`（`String`、`Math`…） | `len`、`max`、`min`、`sum`、`sorted`、`abs`、`list`、`dict`、`set`… |

---

### B. import 的順序（ruff 會自動檢查）

專案的 ruff 設定開啟了 `I` 規則（import 排序）。順序規則是：

1. **標準函式庫**（`collections`、`heapq`…）
2. **第三方套件**（`pytest`、`sortedcontainers`…）
3. **專案內的模組**（`templates.binary_search…`）

不同群組之間空一行。同一個群組內，`import x` 排在 `from x import y` 前面，再各自依字母排序：

```python
import heapq
import math
from bisect import bisect_left
from collections import Counter, defaultdict, deque
from functools import cache

import pytest
```

> 不用手動排：執行 `ruff check --fix .` 會自動整理好。

---

### C. 刷題最常用的模組

| 模組 | 常用的名稱 | 用途 |
| :--- | :--- | :--- |
| `collections` | `Counter`、`defaultdict`、`deque` | 計數、自動初始化的 dict、佇列（BFS） |
| `heapq` | `heappush`、`heappop`、`heapify`、`nlargest` | 優先佇列、Top K |
| `bisect` | `bisect_left`、`bisect_right`、`insort` | 二分搜尋 |
| `functools` | `cache`、`lru_cache`、`cmp_to_key` | 記憶化、自訂排序 |
| `itertools` | `accumulate`、`combinations`、`permutations`、`product`、`pairwise` | 前綴和、排列組合 |
| `math` | `inf`、`gcd`、`lcm`、`isqrt`、`comb` | 數學運算 |
| `string` | `ascii_lowercase`、`digits` | 字母、數字的字元集合 |
| `typing` | `List`、`Optional` | 舊式型別標註（LeetCode 範本使用，見 D 小節） |
| `sortedcontainers`（第三方） | `SortedList`、`SortedDict` | 取代 Java 的 `TreeSet` / `TreeMap` |

---

### D. LeetCode 預先準備好的 import

在 LeetCode 上寫 Python3 時，**大部分常用模組不用自己 import**，直接寫 `Counter(...)`、`heappush(...)` 就能執行。

#### 官方說明的部分

LeetCode 官方的〈What are the environments for the programming languages?〉說明頁寫到：

* Python3 目前的版本是 **Python 3.14**。
* 「Most libraries are already imported automatically for your convenience, such as **array, bisect, collections**. If you need more libraries, you can import it yourself.」
* 需要 Java 的 `TreeMap` 這類資料結構時，可以使用 **`sortedcontainers`**。

官方**沒有公布完整的清單**，只舉了這幾個例子。

#### 社群觀察的部分（非官方）

根據社群的討論與實測，LeetCode 是用 `from 模組 import *` 的方式預先匯入，所以可以**不加模組名稱**直接使用裡面的函式。一般認為包含：

* `collections`、`heapq`、`bisect`、`functools`、`itertools`、`math`、`string`、`re`、`operator`、`sys`
* `typing`（所以範本裡的 `List[int]`、`Optional[TreeNode]` 不用 import 就能用）

> ⚠️ 這份清單可能隨著 LeetCode 更新環境而改變。不確定時，**自己寫上 import 一定不會錯**：重複 import 不會報錯，也沒有效能問題。

#### 本機練習時要自己 import

在本機（這個專案的 `python/`）執行時，**沒有任何預先匯入**。直接從 LeetCode 複製過來的程式碼，常會遇到：

```python
NameError: name 'Counter' is not defined
NameError: name 'List' is not defined
```

建議的做法：

1. **在檔案最上方寫好需要的 import**，貼回 LeetCode 時一起貼上也完全沒問題。
2. **型別標註改用新寫法**：`list[int]`、`TreeNode | None`，就不需要 `from typing import List, Optional`（見 [java-to-python.md](java-to-python.md) 第 9 節）。
3. `sortedcontainers` 是第三方套件，本機的 `.venv` 預設沒有安裝。需要時，請在 `python/pyproject.toml` 的 `dev` 群組加上 `sortedcontainers`，再重新安裝。

---

## 7. `zip`：把多個序列「並排」走訪

### A. 概念

`zip` 就像拉鍊：把好幾個序列**依照位置**兩兩（或三三…）扣在一起，每次產生一個 tuple：

```python
list(zip([1, 2, 3], "abc"))      # [(1, 'a'), (2, 'b'), (3, 'c')]
```

```
[1,   2,   3  ]
 |    |    |
['a', 'b', 'c']
 ↓    ↓    ↓
(1,'a') (2,'b') (3,'c')
```

Java 沒有 `zip`，要用同一個 index 去取兩個陣列：

```java
for (int i = 0; i < Math.min(a.length, b.length); i++) {
    int x = a[i];
    char y = b[i];
}
```

```python
for x, y in zip(a, b):           # Python：不用 index，也不用自己算最短長度
    ...
```

---

### B. 長度不同時：以最短的為準

```python
list(zip([1, 2, 3], "ab"))                       # [(1, 'a'), (2, 'b')]：3 被默默丟掉了
list(zip([1, 2, 3], "ab", strict=True))          # ✗ ValueError: zip() argument 2 is shorter than argument 1

from itertools import zip_longest
list(zip_longest([1, 2, 3], "ab", fillvalue="-"))  # [(1, 'a'), (2, 'b'), (3, '-')]：以最長的為準，不足的補值
```

| 寫法 | 長度不同時 | 適合的情況 |
| :--- | :--- | :--- |
| `zip(a, b)` / `zip(a, b, strict=False)` | 走到**最短**的結束 | 本來就只需要比較共同的部分（例如共同前綴） |
| `zip(a, b, strict=True)` | 拋出 `ValueError` | 長度**應該**相同，不同就代表有 bug |
| `zip_longest(a, b, fillvalue=x)` | 走到**最長**的結束，不足的補 `x` | 需要處理所有元素（例如 Add Strings 對齊位數） |

#### 為什麼程式裡要寫 `strict=False`？

專案的 ruff 開啟了 `B` 規則，其中的 **B905** 會要求 `zip()` 一定要明確寫出 `strict=`：

```
B905 `zip()` without an explicit `strict=` parameter
```

原因是「長度不同時默默截斷」很容易藏 bug。寫出 `strict=False` 等於告訴讀程式的人：「我知道長度可能不同，以最短的為準就是我要的行為」。

> `strict` 參數是 Python 3.10 加入的。LeetCode 目前的 Python3 是 3.14，可以放心使用。

---

### C. `zip(*strs)`：`*` 把 list 拆開傳進去

`*` 是**拆包運算子**（unpacking），會把 list 裡的每個元素拆開，當成**獨立的參數**傳進函式：

```python
strs = ["flower", "flow", "flight"]

zip(*strs)
# 等同於
zip("flower", "flow", "flight")
```

所以 `zip(*strs)` 會**同時走訪每個字串**，每次取出所有字串在**同一個位置**的字元，也就是一次取出「一直行」：

```
         第 0 行  第 1 行  第 2 行  第 3 行
"flower"   f       l       o       w      e  r
"flow"     f       l       o       w             ← 最短，只有 4 個字元
"flight"   f       l       i       g      h  t
           ↓       ↓       ↓       ↓
         ('f','f','f') ('l','l','l') ('o','o','i') ('w','w','g')
```

```python
list(zip(*["flower", "flow", "flight"]))
# [('f', 'f', 'f'), ('l', 'l', 'l'), ('o', 'o', 'i'), ('w', 'w', 'g')]
```

> 只有 4 組，因為最短的 `"flow"` 只有 4 個字元（見 B 小節）。

---

### D. 實戰拆解：`longestCommonPrefix_vertical`（LeetCode 14）

[p0014_longest_common_prefix.py](../python/problems/array/p0014_longest_common_prefix.py) 的垂直掃描寫法：

```python
def longestCommonPrefix_vertical(self, strs: list[str]) -> str:
    if not strs:
        return ""
    res = []
    for chars in zip(*strs, strict=False):    # 每次取出「一直行」的字元
        if len(set(chars)) == 1:               # 這一行的字元全部相同
            res.append(chars[0])
        else:
            break                              # 出現不同的字元，前綴到此為止
    return "".join(res)
```

以 `["flower", "flow", "flight"]` 為例，逐步執行：

| 第幾行 | `chars` | `set(chars)` | 長度 | 動作 | `res` |
| :--- | :--- | :--- | :--- | :--- | :--- |
| 0 | `('f', 'f', 'f')` | `{'f'}` | 1 | 加入 `'f'` | `['f']` |
| 1 | `('l', 'l', 'l')` | `{'l'}` | 1 | 加入 `'l'` | `['f', 'l']` |
| 2 | `('o', 'o', 'i')` | `{'o', 'i'}` | 2 | `break` | `['f', 'l']` |

結果：`"".join(['f', 'l'])` → `"fl"`。

這個寫法裡的三個關鍵：

1. **`zip(*strs)`** 一次取出所有字串同一個位置的字元。
2. **`len(set(chars)) == 1`** 判斷「這一行全部相同」：放進 set 會去除重複，全部相同時就只剩 1 個（見第 2 節 B）。
3. **以最短為準**正好符合題意：共同前綴不可能比最短的字串還長，所以 `zip` 自動停下來，不需要另外檢查長度。這裡如果改成 `strict=True`，遇到長度不同的字串反而會出錯。

對照 Java 的寫法，需要兩層迴圈和手動的邊界檢查：

```java
for (int i = 0; i < strs[0].length(); i++) {
    char c = strs[0].charAt(i);
    for (int j = 1; j < strs.length; j++) {
        if (i == strs[j].length() || strs[j].charAt(i) != c) {
            return strs[0].substring(0, i);
        }
    }
}
return strs[0];
```

> 小補充：`zip(*[])` 會得到空的結果，所以就算拿掉 `if not strs` 那兩行，空 list 也會正確回傳 `""`。保留下來也沒問題，意圖比較清楚。

---

### E. 常見的刷題用法

#### 1. 同時走訪兩個序列

```python
for a, b in zip(nums1, nums2): ...
for i, (a, b) in enumerate(zip(nums1, nums2)): ...   # 也需要 index（注意括號）
```

#### 2. 兩個 list 配成 dict

```python
dict(zip(["a", "b"], [1, 2]))       # {'a': 1, 'b': 2}
```

#### 3. 相鄰元素兩兩比較

```python
nums = [1, 3, 2, 5]
list(zip(nums, nums[1:]))           # [(1, 3), (3, 2), (2, 5)]

all(a <= b for a, b in zip(nums, nums[1:]))     # 判斷是否為遞增
```

> Python 3.10+ 也可以用 `itertools.pairwise(nums)`，效果相同，而且不用複製 `nums[1:]`。

#### 4. 計算兩個字串有幾個位置不同（Hamming Distance 的字串版）

```python
sum(a != b for a, b in zip("karolin", "kathrin"))   # 3：True 會被當成 1 相加
```

#### 5. 矩陣轉置與旋轉（Transpose Matrix、Rotate Image）

```python
matrix = [[1, 2, 3],
          [4, 5, 6],
          [7, 8, 9]]

[list(row) for row in zip(*matrix)]          # 轉置：[[1, 4, 7], [2, 5, 8], [3, 6, 9]]
[list(row)[::-1] for row in zip(*matrix)]    # 順時針轉 90 度：[[7, 4, 1], [8, 5, 2], [9, 6, 3]]
```

跟 C 小節的原理相同：`zip(*matrix)` 一次取出每一列在同一個位置的元素，也就是「一直行」。

#### 6. 反向操作：把配對拆回兩個序列（unzip）

```python
pairs = [(1, "a"), (2, "b")]
nums, letters = zip(*pairs)         # nums = (1, 2)，letters = ('a', 'b')
```

---

### F. 陷阱 (Pitfalls)

#### 1. `zip` 只能走訪一次

`zip` 回傳的是**迭代器**，走完就空了：

```python
z = zip([1, 2], "ab")
list(z)             # [(1, 'a'), (2, 'b')]
list(z)             # []：第二次已經沒有東西了
```

需要重複使用時，先用 `list(zip(...))` 存起來。

#### 2. 產生的是 tuple，不是 list

```python
rows = list(zip(*matrix))
rows[0][0] = 99     # ✗ TypeError：tuple 不能修改
```

需要修改時，要像 E 小節第 5 點一樣，用 `list(row)` 轉換。

#### 3. 長度不同時會默默截斷

這是 `zip` 最容易藏 bug 的地方（見 B 小節）。寫的時候想清楚：長度不同時，你想要的是截斷、報錯，還是補值？然後明確寫出 `strict=` 或改用 `zip_longest`。

---

### G. 一句話總結

> **`zip` 依照位置把多個序列並排走訪，以最短的為準；`zip(*rows)` 則是一次取出「一直行」。**
