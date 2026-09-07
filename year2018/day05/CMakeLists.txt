cmake_minimum_required(VERSION 4.4)
set(CMAKE_C_STANDARD 23)

project(AdventDay05)

set(CMAKE_CXX_FLAGS_DEBUG_INIT "-g -Wall -Wextra -Wpedantic -Wshadow -Wconversion -std=c23")
set(CMAKE_CXX_FLAGS_RELEASE_INIT "-Wall -Wextra -Wpedantic -Wshadow -Wconversion -std=c23")

add_executable(day05)

target_compile_options(day05 PRIVATE -std=c23)

target_sources(day05
    PRIVATE
        ./src/main.c
        ./src/libalchemy.c
        ./src/libalchemy_set.c

    PUBLIC
        FILE_SET myHeaders
        TYPE HEADERS
        BASE_DIRS
            include
        FILES
            ./include/libalchemy.h
            ./include/libalchemy_set.h
)