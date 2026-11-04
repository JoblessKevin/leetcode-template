# Java → Python 轉換筆記

給已經會用 Java 刷題、想改用 Python 練習時的對照與陷阱整理。

> ⚠️ **Python 沒有這些 Java 的概念，寫 Python 時請完全忘掉它們：**
>
> | Java 的概念 | Python 的情況 | 改用 | 詳見 |
> | :--- | :--- | :--- | :--- |
> | `char` 型別 | **沒有 char**，`'a'` 和 `"a"` 都是長度 1 的 `str` | `ord()` / `chr()` 轉換成數字再運算 | 第 3 節 |
> | `++` / `--` 運算子 | **沒有 `++` / `--`**，`i++` 會語法錯誤，`++i` 則是不會改變值 | `i += 1` / `i -= 1` | 第 1 節 |
> | `null` | **沒有 `null`**，寫了會 `NameError` | `None`（大寫 N），判斷用 `is None` | 第 1 節 |

---

## 1. 基本語法 (Basic Syntax)

| 概念 | Java | Python |
| :--- | :--- | :--- |
| 程式區塊 | `{ }` | **縮排**（4 個空格），行尾的 `:` 開始一個區塊 |
| 敘述結尾 | `;` | 不用寫 |
| 變數宣告 | `int x = 0;` | `x = 0`（不用宣告型別） |
| 型別標註 | 必須 | 可選：`x: int = 0`，只是提示，執行時不檢查 |
| 空值 | `null` | `None`（⚠️ 見下方說明） |
| 布林 | `true` / `false` | `True` / `False` |
| 邏輯運算 | `&&` `\|\|` `!` | `and` `or` `not` |
| 三元運算 | `a > b ? a : b` | `a if a > b else b` |
| 遞增 | `i++`、`++i` | **沒有**，要寫 `i += 1`（⚠️ 見下方說明） |
| 條件式的括號 | `if (a == b) { ... }` | `if a == b:`（不用括號） |
| 回傳布林 | `return a == b;` | `return a == b`（不用寫成 `if ...: return True`） |
| 註解 | `//`、`/* */` | `#`，多行說明用 `"""docstring"""` |
| 交換變數 | 需要 `tmp` | `a, b = b, a` |
| 連續比較 | `0 <= i && i < n` | `0 <= i < n` |

> **作用域差異**：Python 沒有區塊作用域。在 `if` / `for` 裡面宣告的變數，離開區塊後還能使用；但函式之間的作用域還是分開的。

### ⚠️ Python 沒有 `++` / `--`

Python **沒有**遞增、遞減運算子。不同的寫法，下場也不一樣：

| 寫法 | 結果 | 危險程度 |
| :--- | :--- | :--- |
| `i++`、`arr[k]++`、`i--` | `SyntaxError`，程式直接無法執行 | 低：馬上就會發現 |
| `++i`、`--i` | **不會報錯，但值完全不會改變** | **高：bug 很難找** |

`++i` 不會報錯，是因為 Python 把它解讀成「正號的正號」`+(+i)`，`--i` 則是「負號的負號」`-(-i)`。兩者算出來都等於原本的 `i`，而且結果沒有存回任何變數，算完就丟掉了。

```python
i = 5
++i              # 不會報錯
print(i)         # 還是 5
```

> 一律寫成 `i += 1`、`arr[k] += 1`、`i -= 1`。

### ⚠️ Python 沒有 `null`：改用 `None`

Python **沒有** `null` 這個關鍵字，對應的是 **`None`**（第一個字母大寫）：

```python
null        # ✗ NameError: name 'null' is not defined
none        # ✗ NameError: name 'none' is not defined
None        # ✓
```

#### A. `None` 和 `null` 的本質不同

| | Java `null` | Python `None` |
| :--- | :--- | :--- |
| 是什麼 | 一個特殊的**值**，代表「沒有指向任何物件」 | 一個真正的**物件**，型別是 `NoneType` |
| 有幾個 | — | 整個程式**只有一個** `None`（單例） |
| 誰可以是空值 | 只有物件型別；`int`、`boolean` 這些基本型別**不能**是 `null` | **任何變數**都可以是 `None`，包括原本放數字的變數 |
| 判斷寫法 | `x == null` | `x is None`（見下方 C） |
| 轉成字串 | `"a" + null` → `"anull"` | `"a" + None` → ❌ `TypeError`；`str(None)` → `"None"` |

> 因為 Python 只有一個 `None` 物件，所以用 `is`（是不是同一個物件）來判斷最準確，也最快。

#### B. 錯誤訊息對照：Python 沒有 `NullPointerException`

在 Java 對 `null` 呼叫方法，會出現 `NullPointerException`。Python 則依照你**對 `None` 做了什麼**，出現不同的錯誤。刷鏈結串列、樹的題目時，看到 **`'NoneType'`** 這個字，就代表某個變數是 `None`：

| 你寫的程式 | Python 的錯誤訊息 |
| :--- | :--- |
| `node.val`、`node.next` | `AttributeError: 'NoneType' object has no attribute 'val'` |
| `node[0]` | `TypeError: 'NoneType' object is not subscriptable` |
| `len(node)` | `TypeError: object of type 'NoneType' has no len()` |
| `node + 1` | `TypeError: unsupported operand type(s) for +: 'NoneType' and 'int'` |
| `None < 1`、`sorted([1, None])` | `TypeError: '<' not supported between instances of 'NoneType' and 'int'` |
| `node()` | `TypeError: 'NoneType' object is not callable` |

#### C. 判斷寫法

```python
if node is None: ...          # ✓ 相當於 Java 的 node == null
if node is not None: ...      # ✓ 相當於 Java 的 node != null
if node == None: ...          # ⚠️ 可以執行，但 ruff 會報 E711：Comparison to `None` should be `cond is None`
if not node: ...              # ⚠️ 要小心：0、""、[] 也會成立（見第 10 節）
```

| 寫法 | 什麼時候用 |
| :--- | :--- |
| `if node is None` | 只想判斷 `None`，或變數可能是 `0`、空字串、空 list 時 |
| `if not node` | 變數只可能是「物件」或 `None` 時，例如 `TreeNode`、`ListNode`。刷題時很常見，也比較簡潔 |

#### D. 會產生 `None` 的地方

這些情況常常讓人「不知道 `None` 從哪裡冒出來的」：

```python
def f():
    pass
f()                       # None：沒有 return 的函式會回傳 None（相當於 Java 的 void）

nums = nums.sort()        # ✗ nums 變成 None：sort()、reverse()、append() 這類原地修改的方法都回傳 None

d = {"a": 1}
d.get("z")                # None：查不到的 key 回傳 None（相當於 Java 的 map.get() 回傳 null）

root.left                 # None：LeetCode 輸入 [1,null,2] 中的 null，在 Python 裡會變成 None
```

> ⚠️ `d.get(k)` 回傳 `None` 時，可能是「key 不存在」，也可能是「key 存在，但 value 就是 `None`」。需要區分時，請用 `k in d`。

#### E. 刷題常用的寫法

```python
# 預設值為 None，在函式內再建立物件（避免可變預設參數的陷阱，見第 6 節 D）
def __init__(self, val=0, next=None):
    ...

# 型別標註：可能是 None 時，寫成 X | None（相當於 Java 中「這個參考可能是 null」）
def reverseList(self, head: ListNode | None) -> ListNode | None:
    ...

# 短路求值：node 是 None 時直接得到 None，不會去存取 .val
val = node and node.val

# 用 None 當「還沒有值」的標記，比用 -1、Integer.MIN_VALUE 更明確
best = None
for x in nums:
    if best is None or x > best:
        best = x
```

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

### A. ⚠️ Python 沒有 `char`

Java 有 `char`（單一字元，本質上是一個整數）和 `String` 兩種型別。Python **只有 `str`**，所謂的「字元」其實就是**長度為 1 的字串**：

```python
type('a')          # <class 'str'>
'a' == "a"         # True：單引號和雙引號完全一樣，不像 Java 有區別
type("abc"[0])     # <class 'str'>：取出來的還是字串
for c in "abc":    # c 依序是 'a'、'b'、'c'，都是 str
    ...
```

因為不是整數，Java 裡把 `char` 當數字用的寫法，在 Python 都要改：

| 用途 | Java（`char` 會自動轉成整數） | Python |
| :--- | :--- | :--- |
| 字元 → 數字 | `c - 'a'`、`(int) c` | `ord(c) - ord('a')`、`ord(c)` |
| 數字 → 字元 | `(char) ('a' + k)` | `chr(ord('a') + k)` |
| 下一個字元 | `(char) (c + 1)` | `chr(ord(c) + 1)` |
| 計數陣列 | `int[] count = new int[26];` 搭配 `count[c - 'a']++` | `count = [0] * 26` 搭配 `count[ord(c) - ord('a')] += 1` |
| 字元陣列 | `char[] arr = s.toCharArray();` | `arr = list(s)`（得到的是 `list[str]`） |
| 判斷範圍 | `c >= 'a' && c <= 'z'` | `'a' <= c <= 'z'`（字串可以比大小，這個寫法沒問題） |

```python
'a' - 'a'          # ✗ TypeError: unsupported operand type(s) for -: 'str' and 'str'
'a' + 1            # ✗ TypeError: can only concatenate str (not "int") to str
'a' * 3            # 'aaa'：字串乘以數字是「重複」，不是數學運算
```

> `ord()` / `chr()` 的完整說明（ordinal 的意思、必背的編號、常見用法）見 [python.md](python.md) 第 4 節。

> 注意：字串比大小是**逐字比較**（字典順序），所以 `'a' <= 'ab' <= 'z'` 也會成立。只有在確定 `c` 是單一字元時，`'a' <= c <= 'z'` 才等同於 Java 的範圍判斷；也可以改用 `c.islower()`。

### B. 字串操作對照

| 操作 | Java | Python |
| :--- | :--- | :--- |
| 長度 | `s.length()` | `len(s)` |
| 取字元 | `s.charAt(i)` | `s[i]`（取出的是長度 1 的字串，見 A 小節） |
| 子字串 | `s.substring(i, j)` | `s[i:j]` |
| 反轉 | `new StringBuilder(s).reverse()` | `s[::-1]` |
| 比較內容 | `s.equals(t)` | `s == t`（⚠️ `==` 的意義跟 Java 相反，見第 10 節） |
| 拼接大量字串 | `StringBuilder` | 先放進 list，最後 `''.join(parts)` |
| 轉字元陣列 | `s.toCharArray()` | `list(s)` |
| 分割 | `s.split(" ")` | `s.split()`（不給參數時，會自動處理連續空白） |
| 格式化 | `String.format("%d", x)` | `f"{x}"` |
| 判斷字元類型 | `Character.isDigit(c)` | `c.isdigit()`、`c.isalpha()`、`c.isalnum()` |

### C. 字串不可變

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

### `==` 與 `is`：⚠️ Python 和 Java 的 `==` 意義剛好相反

> **同一個 `==` 符號，在兩個語言裡比較的東西不一樣：**
>
> * **Java** 的 `==` 比較的是**是不是同一個物件**（記憶體位址）；要比較**內容**得用 `.equals()`。
> * **Python** 的 `==` 比較的是**內容**；要比較**是不是同一個物件**得用 `is`。

| 想比較的東西 | Java | Python |
| :--- | :--- | :--- |
| **內容**是否相同 | `a.equals(b)` | **`a == b`** |
| 是否為**同一個物件** | **`a == b`** | `a is b` |
| 是否為空值 | `a == null` | `a is None` |

```java
// Java
String s1 = new String("abc");
String s2 = new String("abc");
s1 == s2;          // false：不同物件
s1.equals(s2);     // true：內容相同
```

```python
# Python
a = [1, 2]
b = [1, 2]
a == b             # True：內容相同（相當於 Java 的 equals）
a is b             # False：不同物件（相當於 Java 的 ==）

c = a
c is a             # True：c 和 a 指向同一個 list
```

> 補充：Java 的**基本型別**（`int`、`char`、`double`…）用 `==` 比較的就是值，跟 Python 的 `==` 一樣。意義相反的情況，只發生在**物件**（`String`、`Integer`、`List`…）之間的比較。

#### 用錯的後果：兩邊都是「有時對、有時錯」

兩個語言都有快取機制，所以用錯運算子時，**小數字常常會碰巧正確**，讓 bug 更難發現：

| | Java：誤用 `==` 比較 `Integer` | Python：誤用 `is` 比較 `int` |
| :--- | :--- | :--- |
| 快取範圍 | `-128` ~ `127` | `-5` ~ `256` |
| 範圍內 | `Integer a = 127, b = 127; a == b` → `true` | `int("256") is int("256")` → `True` |
| 範圍外 | `Integer a = 1000, b = 1000; a == b` → **`false`** | `int("1000") is int("1000")` → **`False`** |

Python 的情況甚至更難預測：`x = 1000` 之後馬上判斷 `x is 1000`，在同一段程式裡可能會得到 `True`，因為直譯器有時會重複使用同一個常數物件。Python 3.8 之後，用 `is` 比較數字或字串常數時，會出現 `SyntaxWarning: "is" with 'int' literal. Did you mean "=="?` 的警告。

> **規則**：
>
> * **Java**：物件比內容一律用 `.equals()`，`==` 只用在基本型別和 `null`。
> * **Python**：比內容一律用 `==`，`is` **只用在 `None`**（`x is None`、`x is not None`）。

---

## 11. 實戰修正：Valid Anagram (LeetCode 242)

用 Java 的思路直接寫成 Python，常會寫出這樣的程式碼：

```python
class Solution:
    def isAnagram(self, s: str, t: str) -> bool:
        scount = [0] * 26
        tcount = [0] * 26
        for i, char in enumerate(s):
            scount[char - 'a']++          # ✗ SyntaxError
        for i, char in enumerate(t):
            tcount[char - 'a']++
        if (scount == tcount):
            return True
        return False
```

### A. 逐行檢查

| 寫法 | 問題 | 嚴重程度 | 修正 |
| :--- | :--- | :--- | :--- |
| `scount[...]++` | Python 沒有 `++` | ❌ 無法執行（`SyntaxError`） | `scount[...] += 1` |
| `char - 'a'` | 字串不能相減 | ❌ 執行時錯誤（`TypeError`） | `ord(char) - ord('a')` |
| `for i, char in enumerate(s)` | `i` 完全沒用到 | ⚠️ 可以執行，但多餘 | `for char in s` |
| `if (scount == tcount):` | 括號是 Java 的習慣 | 💡 可以執行，風格問題 | `if scount == tcount:` |
| `if ...: return True` + `return False` | 比較的結果本身就是布林值 | 💡 可以執行，風格問題 | `return scount == tcount` |

> 好消息：`scount == tcount` 在 Python 裡是**比較兩個 list 的內容**，這行本身是對的。如果是 Java，比較兩個陣列的內容得用 `Arrays.equals(scount, tcount)`，直接用 `==` 會比較成「是不是同一個陣列」（見第 10 節）。

### B. 修正版本

#### 寫法 1：直接修正（保留原本的思路）

```python
class Solution:
    def isAnagram(self, s: str, t: str) -> bool:
        scount = [0] * 26
        tcount = [0] * 26
        for char in s:
            scount[ord(char) - ord("a")] += 1
        for char in t:
            tcount[ord(char) - ord("a")] += 1
        return scount == tcount
```

#### 寫法 2：只用一個陣列（加上長度檢查，可以提早結束）

```python
class Solution:
    def isAnagram(self, s: str, t: str) -> bool:
        if len(s) != len(t):
            return False
        count = [0] * 26
        for a, b in zip(s, t):              # 長度相同，所以可以用 zip 一起走
            count[ord(a) - ord("a")] += 1
            count[ord(b) - ord("a")] -= 1
        return all(c == 0 for c in count)
```

#### 寫法 3：Python 慣用寫法

```python
from collections import Counter

class Solution:
    def isAnagram(self, s: str, t: str) -> bool:
        return Counter(s) == Counter(t)     # 時間 O(n)，也能處理 Unicode（Follow-up）

    def isAnagram_sort(self, s: str, t: str) -> bool:
        return sorted(s) == sorted(t)       # 時間 O(n log n)，寫法最短
```

| 寫法 | 時間 | 空間 | 備註 |
| :--- | :--- | :--- | :--- |
| 1. 兩個計數陣列 | O(n) | O(1)（固定 26 格） | 最接近 Java 的寫法 |
| 2. 一個計數陣列 | O(n) | O(1) | 長度不同時可以提早結束 |
| 3. `Counter` | O(n) | O(k)（k 為不同字元數） | 不限於小寫英文字母 |
| 3. `sorted` | O(n log n) | O(n) | 寫法最短，但最慢 |

> 相關筆記：`Counter` 的用法見 [python.md](python.md) 第 2 節 B；什麼時候該用 `enumerate`，見 [python.md](python.md) 第 1 節 C。

---

## 12. 複製：Shallow Copy、Deep Copy、Zero Copy（Python / Java / C++）

### A. 四個層級的概念

以二維陣列 `a = [[1, 2], [3, 4]]` 為例：

| 層級 | 做了什麼 | 外層容器 | 內層元素 | 修改 `b[0][0]` 會影響 `a` 嗎 |
| :--- | :--- | :--- | :--- | :--- |
| **賦值**（不是複製） | 多一個名字指向**同一個**物件 | 共用 | 共用 | 會 |
| **Shallow copy**（淺複製） | 建立新的**外層**容器，裡面放的還是**原本那些**元素 | 新的 | **共用** | **會** |
| **Deep copy**（深複製） | 一路往下，**每一層**都複製一份 | 新的 | 新的 | 不會 |
| **Zero copy**（零複製） | **完全不複製資料**，用「視圖」或「轉移所有權」的方式存取原本的記憶體 | 不複製 | 不複製 | 會（共用同一塊記憶體） |

```
賦值：       a ──┐
            b ──┴──► [ ● , ● ]            同一個外層
                       │   │
                    [1,2] [3,4]

淺複製：     a ──► [ ● , ● ]
                     │ ╲ ╱ │              內層被兩個外層共用
            b ──► [ ● , ● ]
                    [1,2] [3,4]

深複製：     a ──► [ ● , ● ] ──► [1,2] [3,4]
            b ──► [ ● , ● ] ──► [1,2] [3,4]   完全獨立的兩份
```

> **一維、元素是數字的陣列**：淺複製和深複製的結果一樣，因為數字本身不會被修改（Python 的 `int` 不可變，Java / C++ 的 `int` 則是直接存值）。淺、深複製的差別，只有在**容器裡面還有容器或物件**時才會出現。

#### Zero copy 的兩種意思

* **作業系統層級**（原本的意思）：資料在檔案、網路之間傳輸時，避免在「核心空間」和「使用者空間」之間來回複製，例如 Linux 的 `sendfile`、`mmap`。常見於高效能伺服器、Kafka 這類系統。
* **語言層級**（刷題、寫程式時比較常碰到）：用**視圖**（view）、**引用**（reference）或**移動**（move）來取代複製，例如 C++ 的 `string_view`、Java 的 `subList`、Python 的 `memoryview`。

---

### B. 三種語言的總覽

| | Python | Java | C++ |
| :--- | :--- | :--- | :--- |
| **`b = a` 的預設行為** | 共用同一個物件 | 共用同一個物件（物件型別） | ⚠️ **複製一份**（值語意） |
| **淺複製** | `a[:]`、`a.copy()`、`list(a)`、`copy.copy(a)` | `a.clone()`、`Arrays.copyOf`、`new ArrayList<>(a)` | 含原始指標的 struct 用預設複製、複製 `shared_ptr` |
| **深複製** | `copy.deepcopy(a)` | **沒有通用的內建方法**，要自己逐層複製 | 標準容器的預設複製**就是深複製** |
| **Zero copy** | `memoryview`、迭代器、numpy 的 view | `subList`、`Arrays.asList`、`ByteBuffer.slice` | 引用 `&`、`std::move`、`string_view`、`span` |

> **最大的差異在 C++**：Python 和 Java 的 `b = a` 只是多一個名字，**C++ 的 `b = a` 會真的複製整個物件**。所以在 C++，「不小心複製」是效能問題；在 Python / Java，「以為複製了其實沒有」則是正確性問題。

---

### C. Python

```python
import copy

a = [[1, 2], [3, 4]]

b = a                              # 賦值：同一個物件，b is a → True

s = a[:]                           # 淺複製（四種寫法效果相同）
s = a.copy()
s = list(a)
s = copy.copy(a)
s[0][0] = 99                       # ⚠️ a[0][0] 也變成 99：內層的 list 是共用的

d = copy.deepcopy(a)               # 深複製：每一層都是新的
d = [row[:] for row in a]          # 只有兩層時，這樣寫比 deepcopy 快
```

| 型別 | 淺複製 | 深複製 |
| :--- | :--- | :--- |
| `list` | `a[:]`、`a.copy()`、`list(a)` | `copy.deepcopy(a)` |
| `dict` | `d.copy()`、`dict(d)`、`{**d}` | `copy.deepcopy(d)` |
| `set` | `s.copy()`、`set(s)` | `copy.deepcopy(s)` |
| `str`、`int`、`tuple`（內容不可變時） | 不需要複製：`copy.copy(t) is t` → `True` | 同左 |

#### Python 的 zero copy

```python
buf = bytearray(b"hello")
view = memoryview(buf)[1:3]        # 不複製，直接指向 buf 的第 1~2 個 byte
view[0] = ord("E")                 # buf 變成 bytearray(b'hEllo')

for x in reversed(nums): ...       # 迭代器：不建立新的 list
for a, b in zip(x, y): ...         # 同上（見 python.md 第 7 節）
```

> ⚠️ **Python 的切片會複製**：`nums[1:]`、`s[i:j]` 都會建立新的 list / 字串，花費 O(k) 的時間和空間。遞迴時傳 `nums[1:]` 會讓整體複雜度多乘上 n，請改成傳 index（見第 4 節 D）。

---

### D. Java

```java
int[][] a = {{1, 2}, {3, 4}};

int[][] b = a;                     // 賦值：同一個陣列

int[][] s = a.clone();             // ⚠️ 淺複製：s != a，但 s[0] == a[0]（同一列）
s[0][0] = 99;                      // a[0][0] 也變成 99

int[][] d = new int[a.length][];   // 深複製：Java 沒有通用的內建方法，要逐層複製
for (int i = 0; i < a.length; i++) {
    d[i] = a[i].clone();           // 一維 int[] 的 clone 是完整的複製
}
```

| 型別 | 淺複製 | 深複製 |
| :--- | :--- | :--- |
| 陣列 | `a.clone()`、`Arrays.copyOf(a, n)`、`System.arraycopy` | 逐層 `clone()` |
| `List` | `new ArrayList<>(list)`、`List.copyOf(list)`（不可修改） | 逐一複製元素 |
| `Map` | `new HashMap<>(map)` | 逐一複製 value |
| 自訂物件 | 實作 `Cloneable` 的 `clone()` | 寫複製建構子（copy constructor），或用序列化（慢，不建議刷題時使用） |

> 一維的 `int[]` 用 `clone()` 就等於深複製，因為裡面存的是數字本身。二維陣列 `int[][]` 的 `clone()` 則**只複製外層**，這是 Java 最常見的複製 bug。

#### Java 的 zero copy（視圖）

```java
List<Integer> base = new ArrayList<>(List.of(1, 2, 3, 4));
List<Integer> sub = base.subList(1, 3);     // 視圖：修改 sub 會改到 base
sub.set(0, 20);                             // base 變成 [1, 20, 3, 4]

Integer[] arr = {1, 2, 3};
List<Integer> view = Arrays.asList(arr);    // 視圖：底層就是 arr，修改會互相影響

ByteBuffer buf = ByteBuffer.wrap(bytes);    // 包裝現有的 byte[]，不複製
ByteBuffer part = buf.slice();              // 共用同一個 byte[]
```

* 作業系統層級：`FileChannel.transferTo`（底層使用 `sendfile`）、`MappedByteBuffer`（底層使用 `mmap`）。
* 歷史小知識：Java 7u6 以前，`String.substring` 會跟原字串共用同一個 `char[]`（zero copy），但這會讓很大的原字串無法被回收，所以之後改成會複製。

---

### E. C++

C++ 預設是**值語意**（value semantics）：賦值、傳參數、`push_back` 都會**複製整個物件**，包括裡面的每一層。

```cpp
std::vector<std::vector<int>> a{{1, 2}, {3, 4}};

auto b = a;                        // ⚠️ 複製整份（而且是深複製）：修改 b 不會影響 a
auto& r = a;                       // 引用：r 就是 a 的另一個名字，修改 r 會改到 a
auto* p = &a;                      // 指標：透過 p 修改也會改到 a
```

#### 淺複製在 C++ 什麼時候出現？

標準容器（`vector`、`string`、`map`…）的複製都是深複製。淺複製只會出現在**物件裡存的是指標**的時候：

```cpp
struct Node { int* data; };
Node n1{&x};
Node n2 = n1;                      // 預設的複製只會複製指標本身：n1.data == n2.data，指向同一個 x

auto sp1 = std::make_shared<std::vector<int>>(v);
auto sp2 = sp1;                    // 複製 shared_ptr：兩個指標共用同一個 vector（use_count == 2）
```

> 類別裡如果有自己管理的原始指標，就要自己寫複製建構子做深複製，這就是 C++ 的 **Rule of Three / Five**。

#### C++ 的 zero copy

```cpp
// 1. 傳參數用 const 引用，避免複製（LeetCode 的 C++ 範本也是這樣寫）
int sum(const std::vector<int>& nums);       // ✓ 不複製
int sum(std::vector<int> nums);              // ✗ 每次呼叫都複製整個 vector

// 2. std::move：轉移所有權，不複製元素
std::vector<int> big(1000000);
std::vector<int> owner = std::move(big);     // O(1)：只交換內部指標，big 之後處於「有效但未指定」的狀態

// 3. 非擁有的視圖（C++17 / C++20）
std::string s = "hello world";
std::string_view sv = std::string_view(s).substr(0, 5);   // 不複製，sv.data() == s.data()
std::span<int> part(v.data() + 1, 2);                      // C++20：陣列的一段視圖，修改會改到 v
```

> ⚠️ 視圖不擁有資料：原本的 `s` 或 `v` 被銷毀、或是 `vector` 擴容搬家之後，`string_view` / `span` 就會變成**懸空**（dangling）的，讀到的是無效的記憶體。

C++ 也有編譯器層級的 zero copy：**RVO / copy elision**。函式回傳區域變數時，編譯器會直接在呼叫端建立物件，不會真的複製；C++17 起，部分情況是語言保證的。

更多 C++ 引用的實驗，見 [cpp/traps/pass_by_reference.cpp](../cpp/traps/pass_by_reference.cpp)。

---

### F. 刷題時最常遇到的情境

#### 1. 回溯法：把目前的路徑存進答案

| 語言 | 正確寫法 | 常見錯誤 |
| :--- | :--- | :--- |
| Python | `res.append(path[:])` | `res.append(path)`：存的是同一個 list，之後的修改會影響答案 |
| Java | `res.add(new ArrayList<>(path))` | `res.add(path)`：同上 |
| C++ | `res.push_back(path)` | 沒有這個問題：`push_back` 本來就會複製一份 |

#### 2. 複製二維的格子（模擬題、Game of Life）

| 語言 | 寫法 |
| :--- | :--- |
| Python | `[row[:] for row in grid]` 或 `copy.deepcopy(grid)`（⚠️ 不能用 `grid[:]`） |
| Java | 逐列 `grid[i].clone()`（⚠️ 不能只用 `grid.clone()`） |
| C++ | `auto copy = grid;` 就是深複製 |

#### 3. 遞迴時傳遞子陣列

| 語言 | 會複製的寫法 | 不複製的寫法 |
| :--- | :--- | :--- |
| Python | `solve(nums[1:])`：每層 O(n) | `solve(nums, start)`：傳 index |
| Java | `Arrays.copyOfRange(nums, 1, n)` | 傳 index |
| C++ | `void solve(std::vector<int> nums)`：傳值 | `void solve(const std::vector<int>& nums, int start)`，或傳 `std::span` |

---

### G. 一句話總結

> * **Python / Java**：預設**共用**，要小心「以為複製了，其實沒有」，尤其是二維陣列的淺複製。
> * **C++**：預設**複製**，要小心「不小心複製了」，用 `&`、`std::move`、`string_view` 避免。

---

## 13. 轉換檢查清單 (Checklist)

寫完 Python 解法後，可以快速對照：

- [ ] 沒有 `++` / `--`，一律寫成 `+= 1` / `-= 1`（特別注意 `++i` 不會報錯，但不會改變值）
- [ ] 字元運算用 `ord()` / `chr()`，沒有直接寫 `c - 'a'`
- [ ] `if` / `while` 的條件沒有多加 Java 式的括號
- [ ] 布林結果直接 `return`，沒有寫成 `if ...: return True` / `return False`
- [ ] 整數除法用的是 `//`，不是 `/`
- [ ] 負數的除法、取餘數，結果符合題目預期
- [ ] 位元題有處理 32 位元遮罩
- [ ] 二維陣列用推導式建立，沒有用 `[[0] * n] * m`
- [ ] 佇列用 `deque`，沒有用 `list.pop(0)`
- [ ] 需要頻繁查詢時用 `set`，不是在 list 上用 `in`
- [ ] 回溯時存的是 `path[:]`，不是 `path`
- [ ] 複製二維陣列用 `[row[:] for row in grid]` 或 `copy.deepcopy`，不是 `grid[:]`（見第 12 節）
- [ ] 巢狀函式重新賦值外部變數時有寫 `nonlocal`
- [ ] 遞迴深度可能超過 1000 時，有放寬上限或改成迭代
- [ ] 沒有寫成 Java 的 `null`，空值一律用 `None`（大寫 N）
- [ ] 判斷 `None` 用的是 `is None`，不是 `not x`
- [ ] 比較內容用的是 `==`，沒有把 Java 的習慣帶過來而寫成 `is`
