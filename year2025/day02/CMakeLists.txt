cmake_minimum_required(VERSION 4.1.0)

# I am being extremly lazy with the naming so I can copy and paste each day

project(project_lib VERSION 1.0)

set(CMAKE_CXX_STANDARD 23)
set(CMAKE_CXX_STANDARD_REQUIRED True)
set(CMAKE_EXPORT_COMPILE_COMMANDS ON)

find_package(GTest REQUIRED gtest gmock gtest_main gmock_main)

enable_testing()

add_library(lib STATIC
    src/lib.cpp
)
if (MSVC)
target_compile_options(lib INTERFACE
	/Zi
	/permissive-
	/W4
	/WX
)
set(gtest_force_shared_crt ON CACHE BOOL "" FORCE)
else ()
target_compile_options(lib INTERFACE
	-ggdb
	-pedantic-errors
	-Wall
	-Weffc++
	-Wextra
	-Wconversion
	-Wsign-conversion
	-Werror
)
endif()

target_include_directories(lib PUBLIC "${PROJECT_BINARY_DIR}" "./src")

add_executable(lib_test
    test/lib_test.cpp
)
target_link_libraries(lib_test PUBLIC lib)
target_link_libraries(lib_test PUBLIC gtest::gtest)

include(GoogleTest)
gtest_discover_tests(lib_test DISCOVERY_MODE PRE_TEST)