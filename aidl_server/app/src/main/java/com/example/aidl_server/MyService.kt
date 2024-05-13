package com.example.aidl_server

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class MyService : Service() {

    companion object {
        private const val TAG = "Server app service"
    }

    // 바인더 객체
    private var iMyAidlInterface: IMyAidlInterfaceImpl? = null

    override fun onCreate() {
        super.onCreate()
        iMyAidlInterface = IMyAidlInterfaceImpl // 서비스 생성 시 바인더 객체 생성
        Log.d(TAG, "onCreate")
    }

    // 외부에서 서비스 바인딩 시 바인딩 리턴
    override fun onBind(intent: Intent?): IBinder? {
        return iMyAidlInterface
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }

}