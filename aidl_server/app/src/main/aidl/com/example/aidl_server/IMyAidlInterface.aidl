// IMyAidlInterface.aidl
package com.example.aidl_server;

// Declare any non-default types here with import statements
import com.example.aidl_server.IServiceStateCallback;
import com.example.aidl_server.CustomRect;

interface IMyAidlInterface {
    CustomRect ranRect();
    boolean isAvailable(); // 서비스 상태 체크
    boolean registerCallback(IServiceStateCallback callback); // 서비스 상태 변경 콜백 등록
    boolean unregisterCallback(IServiceStateCallback callback); // 서비스 상태 변경 콜백 등록 해제       double aDouble, String aString);
}