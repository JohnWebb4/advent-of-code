#!/usr/bin/env sh

cmake --build build --preset conan-debug

pushd ./build
ctest -C Debug -V
popd
