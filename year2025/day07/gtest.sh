#!/usr/bin/env sh

python3 -m venv .venv
source ./.venv/bin/activate

pip install -r requirements.txt

conan install . --build=missing -s build_type=Debug -of build

cmake --preset conan-debug
cmake --build build --preset conan-debug

pushd ./build
ctest -C Debug $@
popd