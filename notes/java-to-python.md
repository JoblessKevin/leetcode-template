# Java → Python 轉換筆記

給已經會用 Java 刷題、想改用 Python 練習時的對照與陷阱整理。

---

## 1. 基本語法 (Basic Syntax)

| 概念 | Java | Python |
| :--- | :--- | :--- |
| 程式區塊 | `{ }` | **縮排**（4 個空格），行尾的 `:` 開始一個區塊 |
| 敘述結尾 | `;` | 不用寫 |
| 變數宣告 | `int x = 0;` | `x = 0`（不用宣告型別） |
| 型別標註 | 必須 | 可選：`x: int = 0`，只是提示，執行時不檢查 |
| 空值 | `null` | `None` |
| 布林 | `true` / `false` | `True` / `False` |
| 邏輯運算 | `&&` `\|\|` `!` | `and` `or` `not` |
| 三元運算 | `a > b ? a : b` | `a if a > b else b` |
| 遞增 | `i++` | **沒有**，要寫 `i += 1` |
| 註解 | `//`、`/* */` | `#`，多行說明用 `"""docstring"""` |
| 交換變數 | 需要 `tmp` | `a, b = b, a` |
| 連續比較 | `0 <= i && i < n` | `0 <= i < n` |

> **作用域差異**：Python 沒有區塊作用域。在 `if` / `for` 裡面宣告的變數，離開區塊後還能使用；但函式之間的作用域還是分開的。

---

## 2. 數字 (Numbers)

### A. 整數沒有溢位

Python 的 `int` 是**任意精度**，不會有 Java 的 `int` / `long` 溢位問題，也不用擔心 `mid = (l + r) / 2` 溢位。

| Java | Python |
| :--- | :--- |
| `Integer.MAX_VALUE` | `float('inf')` 或 `math.inf`（當作「無限大」的初始值） |
| `Integer.MIN_VALUE` | `float('-inf')` 或 `-math.inf` |
| `Long` | 不需要，`int` 就夠了 |

### B. 除法與取餘數（⚠️ 負數時結果不同）

| 運算 | Java | Python |
| :--- | :--- | :--- |
| `7 / 2` | `3`（整數除法） | `3.5`（**一定是浮點數**） |
| 整數除法 | `7 / 2` → `3` | `7 // 2` → `3` |
| `-7 / 2` | `-3`（**向 0 取整**） | `-7 // 2` → `-4`（**向下取整**） |
| `-7 % 2` | `-1`（正負號跟被除數） | `1`（正負號跟**除數**） |

* 想要跟 Java 一樣「向 0 取整」：`int(-7 / 2)` → `-3`
* 好處：Python 的 `%` 永遠回傳非負數（除數為正時），像環狀陣列 `(i - 1) % n` 不用再另外加 `n`。

### C. 位元運算（搭配 [bit.md](bit.md)）

Python 的負數可以想成「左邊有無限多個 1」，所以處理題目要求的 32 位元整數時，要自己加上遮罩：

```python
MASK = 0xFFFFFFFF

x & MASK                                     # 轉成 32 位元的無號整數
x if x <= 0x7FFFFFFF else ~(x ^ MASK)        # 把 32 位元的結果轉回有號整數
(x & MASK) >> k                              # 相當於 Java 的 x >>> k（Python 沒有 >>>）
```

* `bin(5)` → `'0b101'`；`bin(-5)` → `'-0b101'`（**不是**補數表示法）
* `n.bit_count()` 計算 1 的個數，相當於 Java 的 `Integer.bitCount(n)`（Python 3.10+）

---

## 3. 字串 (Strings)

| 操作 | Java | Python |
| :--- | :--- | :--- |
| 長度 | `s.length()` | `len(s)` |
| 取字元 | `s.charAt(i)` | `s[i]`（Python **沒有 char 型別**，取出的是長度 1 的字串） |
| 字元運算 | `c - 'a'` | `ord(c) - ord('a')` |
| 數字轉字元 | `(char) ('a' + k)` | `chr(ord('a') + k)` |
| 子字串 | `s.substring(i, j)` | `s[i:j]` |
| 反轉 | `new StringBuilder(s).reverse()` | `s[::-1]` |
| 比較內容 | `s.equals(t)` | `s == t` |
| 拼接大量字串 | `StringBuilder` | 先放進 list，最後 `''.join(parts)` |
| 轉字元陣列 | `s.toCharArray()` | `list(s)` |
| 分割 | `s.split(" ")` | `s.split()`（不給參數時，會自動處理連續空白） |
| 格式化 | `String.format("%d", x)` | `f"{x}"` |
| 判斷字元類型 | `Character.isDigit(c)` | `c.isdigit()`、`c.isalpha()`、`c.isalnum()` |

> 字串跟 Java 一樣是**不可變的**。在迴圈裡寫 `s += c` 每次都會建立新字串，整體可能變成 O(n²)，請改用 `''.join()`。

---

## 4. 集合對照表 (Collections)

| Java | Python | 備註 |
| :--- | :--- | :--- |
| `int[]`、`ArrayList` | `list` | `arr.append(x)`、`arr.pop()` |
| `HashMap` | `dict` | `d.get(k, 0)` 相當於 `getOrDefault` |
| `HashSet` | `set` | `s.add(x)`、`x in s` |
| `Stack` | `list` | `append` 推入、`pop()` 取出、`stack[-1]` 查看頂端 |
| `ArrayDeque` / `Queue` | `collections.deque` | `append` / `popleft` / `appendleft` |
| `PriorityQueue` | `heapq` | 只有**最小堆**，見下方說明 |
| `TreeMap` / `TreeSet` | 沒有內建 | 可用 `sortedcontainers.SortedList`（LeetCode 有提供） |
| 計數用的 `HashMap<K, Integer>` | `collections.Counter` | `Counter(nums)` |
| `computeIfAbsent(k, x -> new ArrayList<>())` | `collections.defaultdict(list)` | 取用不存在的 key 時，自動建立空 list |

### A. 建立二維陣列（⚠️ 經典陷阱）

```python
grid = [[0] * n] * m                  # ✗ m 列其實是「同一個」list，改一列會改到全部
grid = [[0] * n for _ in range(m)]    # ✓ 每一列都是獨立的 list
```

### B. heapq（取代 PriorityQueue）

```python
import heapq

heap = []
heapq.heappush(heap, 3)
smallest = heapq.heappop(heap)

heapq.heappush(heap, -x)              # 最大堆：存入負數，取出時再加負號
heapq.heappush(heap, (dist, node))    # 用 tuple 自訂優先順序：先比 dist，相同再比 node
heapq.heapify(nums)                   # 把現有的 list 原地轉成堆，O(n)
```

### C. dict 的 key 必須是不可變的

`list` 不能當作 dict 的 key，要先轉成 `tuple`：

```python
groups = defaultdict(list)
groups[tuple(sorted(s))].append(s)    # Group Anagrams：Java 常用 String 當 key，Python 可以直接用 tuple
```

### D. 常用操作的時間複雜度

| 操作 | 複雜度 | 建議 |
| :--- | :--- | :--- |
| `x in list` | **O(n)** | 需要頻繁查詢時改用 `set` |
| `list.pop(0)`、`list.insert(0, x)` | **O(n)** | 當作佇列使用時改用 `deque` |
| `list.pop()`、`list.append(x)` | O(1) | |
| `x in set`、`x in dict` | O(1) | |
| `arr[i:j]` 切片 | O(j - i) | 切片會**複製**一份，遞迴時傳切片容易拖慢速度，改傳 index |

---

## 5. 迴圈 (Loops)

Python 沒有 `for (int i = 0; i < n; i++)` 這種寫法，要改用 `range` 或直接迭代：

```python
for i in range(n): ...                  # 0, 1, ..., n-1
for i in range(n - 1, -1, -1): ...      # 倒著走：n-1, ..., 0
for i in range(0, n, 2): ...            # 每次跳 2

for x in nums: ...                      # 只需要值
for i, x in enumerate(nums): ...        # 同時需要 index 和值
for a, b in zip(nums1, nums2): ...      # 同時走兩個陣列
for x in reversed(nums): ...            # 倒著走，不會複製陣列
for k, v in d.items(): ...              # 走訪 dict
```

### 推導式 (Comprehension)

```python
squares = [x * x for x in nums]
evens = [x for x in nums if x % 2 == 0]
index_of = {x: i for i, x in enumerate(nums)}
```

---

## 6. 函式與參考 (Functions & References)

### A. 參數傳遞跟 Java 一樣

Python 傳的也是「參考的複本」，所以概念跟 [traps/PassByValue.java](../java/src/main/java/traps/PassByValue.java) 完全相同：

* 在函式內**修改**物件內容（`lst.append(x)`），外部看得到。
* 在函式內**重新賦值**（`lst = [...]`），外部看不到。

### B. 巢狀函式修改外部變數要用 `nonlocal`

刷 DFS / 回溯題時，常把輔助函式寫在主函式裡面：

```python
def maxDepth(self, root):
    best = 0

    def dfs(node, depth):
        nonlocal best             # 沒寫這行，best = ... 會建立一個新的區域變數
        if not node:
            return
        best = max(best, depth)
        dfs(node.left, depth + 1)
        dfs(node.right, depth + 1)

    dfs(root, 1)
    return best
```

> 如果只是**修改**外部的 list / dict 內容（例如 `res.append(path[:])`），不用 `nonlocal`；只有**重新賦值**時才需要。

### C. 回溯時記得複製

```python
res.append(path)       # ✗ 存的是同一個 list，後續修改會影響已經存進去的結果
res.append(path[:])    # ✓ 存一份複本（相當於 Java 的 new ArrayList<>(path)）
```

### D. 預設參數不要用可變物件

```python
def f(x, memo={}): ...      # ✗ 這個 dict 只會建立一次，會被所有呼叫共用
def f(x, memo=None): ...    # ✓ 在函式內再判斷：if memo is None: memo = {}
```

---

## 7. 排序 (Sorting)

| Java | Python |
| :--- | :--- |
| `Arrays.sort(arr)` | `arr.sort()`（原地排序） |
| — | `sorted(arr)`（回傳新的 list，原本的不變） |
| `Arrays.sort(a, (x, y) -> x[0] - y[0])` | `a.sort(key=lambda x: x[0])` |
| 多條件比較 | `a.sort(key=lambda x: (x[0], -x[1]))`（第一欄遞增，第二欄遞減） |
| `Collections.reverseOrder()` | `arr.sort(reverse=True)` |
| 複雜的 `Comparator` | `functools.cmp_to_key(cmp)`，例如 Largest Number |

* Python 的排序是**穩定的**（相同 key 會保持原本的順序）。
* Python 用 `key` 而不是 `Comparator`：告訴它「用什麼來比」，而不是「怎麼比」。

### 二分搜尋：內建的 bisect

```python
from bisect import bisect_left, bisect_right

bisect_left(nums, target)     # 第一個 >= target 的 index，等於 templates 裡的 lowerBound
bisect_right(nums, target)    # 第一個 > target 的 index（upper bound）
```

---

## 8. 遞迴與記憶化 (Recursion & Memoization)

### A. 遞迴深度上限

Python 預設的遞迴深度上限大約是 **1000**，比 Java 的 stack 淺很多。像鏈結串列或退化成一條線的樹，遞迴很容易超過上限：

```python
import sys
sys.setrecursionlimit(10**6)    # 放寬上限；或者改用迭代 + 自己維護 stack
```

### B. 用 `@cache` 取代手寫的 memo

```python
from functools import cache

@cache
def dp(i, j):
    ...
```

* 參數必須是**不可變的**（int、str、tuple），不能傳 list。
* 同一個 `Solution` 被多次呼叫時，要注意快取不會自動清除。可以把 `@cache` 函式寫在方法裡面，每次呼叫都會重新建立。

---

## 9. LeetCode 的類別寫法 (Class Template)

```python
class ListNode:
    def __init__(self, val=0, next=None):    # 建構子，相當於 Java 的 ListNode(int val)
        self.val = val                       # 所有實例變數都要加 self.
        self.next = next


class Solution:
    def reverseList(self, head: ListNode | None) -> ListNode | None:
        prev = None
        while head:                          # 等同於 head is not None
            head.next, prev, head = prev, head, head.next
        return prev
```

* `self` 相當於 Java 的 `this`，但**必須**明確寫在參數列的第一個。
* 沒有 `public` / `private`。慣例上，名稱用底線開頭（`_helper`）代表內部使用。
* LeetCode 範本裡的 `Optional[ListNode]`、`List[int]` 是舊寫法，在本機請改成 `ListNode | None`、`list[int]`（不用 import，ruff 也不會報錯）。

---

## 10. 真值判斷 (Truthiness)

以下這些值在 `if` 判斷中都視為 `False`：`None`、`0`、`""`、`[]`、`{}`、`set()`

```python
if not stack: ...       # 相當於 Java 的 stack.isEmpty()
if node: ...            # 相當於 node != null
```

> ⚠️ 陷阱：`if not x:` 在 `x == 0` 時也會成立。如果 `x` 可能是 `0`，而你只想判斷 `None`，請寫 `if x is None:`。

### `==` 與 `is`

| Python | 意義 | 對應 Java |
| :--- | :--- | :--- |
| `a == b` | 比較**值** | `a.equals(b)` |
| `a is b` | 比較是否為**同一個物件** | `a == b`（物件之間比較時） |

> 規則：`is` 只用在 `None` 的判斷（`x is None`），其他情況一律用 `==`。

---

## 11. 轉換檢查清單 (Checklist)

寫完 Python 解法後，可以快速對照：

- [ ] 整數除法用的是 `//`，不是 `/`
- [ ] 負數的除法、取餘數，結果符合題目預期
- [ ] 位元題有處理 32 位元遮罩
- [ ] 二維陣列用推導式建立，沒有用 `[[0] * n] * m`
- [ ] 佇列用 `deque`，沒有用 `list.pop(0)`
- [ ] 需要頻繁查詢時用 `set`，不是在 list 上用 `in`
- [ ] 回溯時存的是 `path[:]`，不是 `path`
- [ ] 巢狀函式重新賦值外部變數時有寫 `nonlocal`
- [ ] 遞迴深度可能超過 1000 時，有放寬上限或改成迭代
- [ ] 判斷 `None` 用的是 `is None`，不是 `not x`
