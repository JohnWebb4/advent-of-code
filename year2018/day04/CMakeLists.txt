cmake_minimum_required(VERSION 4.4)
set(CMAKE_C_STANDARD 23)

project(AdventDay04)

set(CMAKE_CXX_FLAGS_DEBUG_INIT "-g -Wall -Wextra -Wpedantic -Wshadow -Wconversion -std=c23")
set(CMAKE_CXX_FLAGS_RELEASE_INIT "-Wall -Wextra -Wpedantic -Wshadow -Wconversion -std=c23")

add_executable(day04)

target_sources(day04
    PRIVATE
        ./src/main.c
        ./src/libguard.c
        ./src/libguardevent.c

    PUBLIC
        FILE_SET myHeaders
        TYPE HEADERS
        BASE_DIRS
            include
        FILES
            ./include/libguard.h
            ./include/libguardevent.h
)