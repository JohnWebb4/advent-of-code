cmake_minimum_required(VERSION 4.4)

project(AdventDay02)

set(CMAKE_CXX_FLAGS_DEBUG_INIT "-g -Wall -Wextra")
set(CMAKE_CXX_FLAGS_RELEASE_INIT "-Wall -Wextra")

add_executable(day02)

target_sources(day02
    PRIVATE
       ./src/main.c
        ./src/libims.c
        ./src/imsset.c
    
    PUBLIC
        FILE_SET myHeaders
        TYPE HEADERS
        BASE_DIRS
            include
        FILES
            include/libims.h
            include/imsset.h

)