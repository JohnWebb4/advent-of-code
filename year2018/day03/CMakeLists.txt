cmake_minimum_required(VERSION 4.4)

project(AdventDay03)

set(CMAKE_CXX_FLAGS_DEBUG_INIT "-g -Wall -Wextra -Wpedantic -Wshadow -Wconversion")
set(CMAKE_CXX_FLAGS_RELEASE_INIT "-Wall -Wextra -Wpedantic -Wshadow -Wconversion")

add_executable(day03)

target_sources(day03
    PRIVATE
        ./src/main.c
        ./src/libfabric.c
        ./src/libfabricclaim.c

    PUBLIC
        FILE_SET myHeaders
        TYPE HEADERS
        BASE_DIRS
            include
        FILES
            include/libfabric.h
            include/libfabricclaim.h
)