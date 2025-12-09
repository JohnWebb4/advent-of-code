#!/usr/bin/env sh

cmake --preset conan-debug
cmake --build build --preset conan-debug

pushd ./build
ctest -C Debug -V
popd
