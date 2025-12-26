#!/usr/bin/env sh

pip install -r requirements.txt

conan install . --build=missing -s build_type=Debug -of build
cmake --preset conan-debug
