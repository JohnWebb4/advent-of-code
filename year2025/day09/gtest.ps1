cmake --preset conan-default
cmake --build build --preset conan-debug

pushd ./build
ctest -C Debug -V
popd
