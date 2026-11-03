#!/usr/bin/env bash
set -euo pipefail

# C++: compiler, CMake, Ninja, debugger, and clangd for IntelliSense
sudo apt-get update
sudo apt-get install -y --no-install-recommends build-essential cmake ninja-build gdb clangd

# Same clang-format version as CI (.github/workflows/cpp.yml)
pipx install uv
pipx install clang-format==23.1.3

# Python: create python/.venv with the dev group (pytest, ruff)
cd python
uv sync
