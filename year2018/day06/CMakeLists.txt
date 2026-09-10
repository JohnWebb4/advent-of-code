cmake_minimum_required(VERSION 4.4)
set(CMAKE_C_STANDARD 23)

project(AdventDay06)

set(CMAKE_CXX_FLAGS_DEBUG_INIT "-g -Wall -Wextra -Werror -Wpedantic -Wshadow -Wconversion -fsanitize=address,undefined -std=c23")
set(CMAKE_CXX_FLAGS_RELEASE_INIT "-Wall -Wextra -Werror -Wpedantic -Wshadow -Wconversion -std=c23")

add_executable(day06)

target_compile_options(day06 PRIVATE -std=c23)

target_sources(day06
    PRIVATE
        ./src/main.c
        ./src/libcoord.c
        ./src/libcoord_point.c

    PUBLIC
        FILE_SET myHeaders
        TYPE HEADERS
        BASE_DIRS
            include
        FILES
            ./include/libcoord.h
            ./include/libcoord_point.h
)