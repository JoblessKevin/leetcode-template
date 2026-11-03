## install C++ compiler
1. 安裝 MSYS2
winget install MSYS2.MSYS2

2. 安裝編譯器和建置工具 (從開始選單打開「MSYS2 UCRT64」終端機，並執行以下)
pacman -S --needed mingw-w64-ucrt-x86_64-gcc mingw-w64-ucrt-x86_64-cmake mingw-w64-ucrt-x86_64-ninja mingw-w64-ucrt-x86_64-clang-tools-extra

## Protable
https://winlibs.com/
(往下滾，選擇 Win64 (without LLVM/Clang/LLD/LLDB) Zip archive)
## Remote
GitHub Codespaces
