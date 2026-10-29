cmake_minimum_required(VERSION 3.20)
project(leetcode_template CXX)

set(CMAKE_CXX_STANDARD 20)
set(CMAKE_CXX_STANDARD_REQUIRED ON)
set(CMAKE_EXPORT_COMPILE_COMMANDS ON)  # for clangd

# GoogleTest
include(FetchContent)
FetchContent_Declare(
  googletest
  URL https://github.com/google/googletest/archive/refs/tags/v1.15.2.zip
  DOWNLOAD_EXTRACT_TIMESTAMP TRUE
)
set(gtest_force_shared_crt ON CACHE BOOL "" FORCE)  # MSVC: match the runtime library
FetchContent_MakeAvailable(googletest)

enable_testing()
include(GoogleTest)

# problems/ and templates/: solution + TEST() in the same file, all built into one test binary
file(GLOB_RECURSE TEST_SOURCES CONFIGURE_DEPENDS
  ${CMAKE_CURRENT_SOURCE_DIR}/problems/*.cpp
  ${CMAKE_CURRENT_SOURCE_DIR}/templates/*.cpp
)
add_executable(lc_tests ${TEST_SOURCES})
target_include_directories(lc_tests PRIVATE ${CMAKE_CURRENT_SOURCE_DIR})
target_link_libraries(lc_tests PRIVATE GTest::gtest_main)
gtest_discover_tests(lc_tests)

# traps/: standalone experiments with their own main(), one executable each
file(GLOB TRAP_SOURCES CONFIGURE_DEPENDS ${CMAKE_CURRENT_SOURCE_DIR}/traps/*.cpp)
foreach(src ${TRAP_SOURCES})
  get_filename_component(name ${src} NAME_WE)
  add_executable(trap_${name} ${src})
endforeach()
