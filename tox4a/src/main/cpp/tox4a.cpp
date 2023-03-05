#include <cstdlib>
#include <jni.h>
#include <tox/tox.h>

extern "C"
JNIEXPORT jint JNICALL
Java_ru_fredboy_tox4a_jni_ToxCoreJni_toxNew(JNIEnv *env, jclass clazz, jboolean ipv6_enabled,
                                            jboolean udp_enabled, jboolean local_discovery_enabled,
                                            jint proxy_type, jstring proxy_address, jint proxy_port,
                                            jint start_port, jint end_port, jint tcp_port,
                                            jint save_data_type, jbyteArray save_data) {
    // TODO: implement toxNew()
}