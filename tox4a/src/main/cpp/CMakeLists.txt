cmake_minimum_required(VERSION 3.22.1)

project("tox4a")

include(ExternalProject)

set(THIRDPARTY_SOURCE_DIR ${CMAKE_CURRENT_SOURCE_DIR}/thirdparty)
set(THIRDPARTY_INSTALL_DIR ${CMAKE_CURRENT_BINARY_DIR}/install)
set(THIRDPARTY_INCLUDE_DIR ${THIRDPARTY_INSTALL_DIR}/include)
set(THIRDPARTY_LINK_DIR ${THIRDPARTY_INSTALL_DIR}/lib)

set(SODIUM_SOURCE_DIR ${THIRDPARTY_SOURCE_DIR}/libsodium)
set(OPUS_SOURCE_DIR ${THIRDPARTY_SOURCE_DIR}/opus)
set(VPX_SOURCE_DIR ${THIRDPARTY_SOURCE_DIR}/libvpx)
set(TOXCORE_SOURCE_DIR ${THIRDPARTY_SOURCE_DIR}/c-toxcore)


if (${ANDROID_ABI} STREQUAL "armeabi-v7a")
    set(VPX_TARGET armv7-android-gcc)
    set(TARGET armv7a-linux-androideabi)
    set(BASE_TARGET arm-linux-androideabi)
elseif (${ANDROID_ABI} STREQUAL "arm64-v8a")
    set(VPX_TARGET arm64-android-gcc)
    set(TARGET aarch64-linux-android)
    set(BASE_TARGET ${TARGET})
elseif (${ANDROID_ABI} STREQUAL "x86")
    set(VPX_TARGET x86-android-gcc)
    set(TARGET i686-linux-android)
    set(BASE_TARGET ${TARGET})
    set(UNSET_AS_IF_NEED unset AS &&)
elseif (${ANDROID_ABI} STREQUAL "x86_64")
    set(VPX_TARGET x86_64-android-gcc)
    set(TARGET x86_64-linux-android)
    set(BASE_TARGET ${TARGET})
    set(UNSET_AS_IF_NEED unset AS &&)
endif()

set(TOOLCHAIN ${ANDROID_NDK}/toolchains/llvm/prebuilt/linux-x86_64)
set(TOOLCHAIN_CLANG_BIN ${TOOLCHAIN}/bin/${TARGET}${ANDROID_NATIVE_API_LEVEL})
set(TOOLCHAIN_BIN ${TOOLCHAIN}/bin/${BASE_TARGET})

set(ENV_VARS export CC=${CMAKE_C_COMPILER}\ --target=${TARGET}${ANDROID_NATIVE_API_LEVEL}\ -fPIC && export CXX=${CMAKE_CXX_COMPILER}\ --target=${TARGET}${ANDROID_NATIVE_API_LEVEL}\ -fPIC && export AR=${TOOLCHAIN}/bin/llvm-ar && export LD=${TOOLCHAIN}/bin/ld && export AS=${TOOLCHAIN}/bin/${BASE_TARGET}-as && export STRIP=${TOOLCHAIN}/bin/llvm-strip && export NM=${TOOLCHAIN}/bin/llvm-nm && export RANLIB=${TOOLCHAIN}/bin/llvm-ranlib &&)

ExternalProject_Add(
        libsodium
        PREFIX ${THIRDPARTY_INSTALL_DIR}
        SOURCE_DIR ${SODIUM_SOURCE_DIR}
        CONFIGURE_COMMAND ${ENV_VARS} autoreconf -fi ${SODIUM_SOURCE_DIR} && ${SODIUM_SOURCE_DIR}/configure --srcdir=${SODIUM_SOURCE_DIR} --prefix=${THIRDPARTY_INSTALL_DIR} --host=${TARGET}${ANDROID_NATIVE_API_LEVEL} --disable-shared
        BUILD_COMMAND make
        INSTALL_COMMAND make install
)

ExternalProject_Add(
        libopus
        PREFIX ${THIRDPARTY_INSTALL_DIR}
        SOURCE_DIR ${OPUS_SOURCE_DIR}
        CONFIGURE_COMMAND ${ENV_VARS} autoreconf -fi ${OPUS_SOURCE_DIR} && ${OPUS_SOURCE_DIR}/configure --srcdir=${OPUS_SOURCE_DIR} --prefix=${THIRDPARTY_INSTALL_DIR} --host=${TARGET}${ANDROID_NATIVE_API_LEVEL} --disable-shared
        BUILD_COMMAND make
        INSTALL_COMMAND make install
)

ExternalProject_Add(
        libvpx
        PREFIX ${THIRDPARTY_INSTALL_DIR}
        SOURCE_DIR ${VPX_SOURCE_DIR}
        PATCH_COMMAND git checkout . && patch -p1 < ${THIRDPARTY_SOURCE_DIR}/libvpx.patch
        CONFIGURE_COMMAND ${ENV_VARS} ${UNSET_AS_IF_NEED} ${VPX_SOURCE_DIR}/configure --prefix=${THIRDPARTY_INSTALL_DIR} --libc=${CMAKE_SYSROOT} --target=${VPX_TARGET} --disable-examples --disable-unit-tests --enable-pic
        BUILD_COMMAND make
        INSTALL_COMMAND make install
)


ExternalProject_Add(
        libtoxcore
        PREFIX ${THIRDPARTY_INSTALL_DIR}
        SOURCE_DIR ${TOXCORE_SOURCE_DIR}
        CONFIGURE_COMMAND ${ENV_VARS} export CFLAGS=-I${THIRDPARTY_INSTALL_DIR}/include && export LDFLAGS=-L${THIRDPARTY_INSTALL_DIR}/lib && autoreconf -fi ${TOXCORE_SOURCE_DIR} && ${TOXCORE_SOURCE_DIR}/configure --prefix=${THIRDPARTY_INSTALL_DIR} --srcdir=${TOXCORE_SOURCE_DIR} --host=${TARGET}${ANDROID_NATIVE_API_LEVEL} --disable-rt --disable-shared
        BUILD_COMMAND make
        INSTALL_COMMAND make install
)

add_dependencies(libtoxcore libsodium libopus libvpx)

add_library(tox4a SHARED tox4a.cpp)

add_dependencies(tox4a libtoxcore)

target_include_directories(tox4a PRIVATE ${THIRDPARTY_INCLUDE_DIR})
target_link_directories(tox4a PRIVATE ${THIRDPARTY_LINK_DIR})

target_link_libraries(tox4a toxcore sodium)
