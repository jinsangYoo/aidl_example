// IServiceStateCallback.aidl
package com.example.aidl_server;

// Declare any non-default types here with import statements
import com.example.aidl_server.CustomRect;

interface IServiceStateCallback {
    void onServiceStateChanged(int status);
    void makeRectByStatus(in CustomRect ranRect);
}