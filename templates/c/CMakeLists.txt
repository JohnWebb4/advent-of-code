cmake_minimum_required(VERSION 4.4)
set(CMAKE_C_STANDARD 23)

project(Advent20XXDayXX)

set(CMAKE_CXX_FLAGS_DEBUG_INIT "-g -Wall -Wextra -Wpedantic -Wshadow -Wconversion -std=c23")
set(CMAKE_CXX_FLAGS_RELEASE_INIT "-Wall -Wextra -Wpedantic -Wshadow -Wconversion -std=c23")

add_executable(dayXX)

target_compile_options(dayXX PRIVATE -std=c23)

target_sources(dayXX
    PRIVATE
        ./src/main.c

    # PUBLIC
    #     FILE_SET myHeaders
    #     TYPE HEADERS
    #     BASE_DIRS
    #         include
    #     FILES
    #         ./include/libfile.h
)
