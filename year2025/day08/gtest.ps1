python3 -m venv .venv
./.venv/Scripts/activate

pip install -r requirements.txt

conan install . --build=missing -s build_type=Debug -of build

cmake --preset conan-default
cmake --build build --preset conan-debug

pushd ./build
ctest -C Debug $@
popd