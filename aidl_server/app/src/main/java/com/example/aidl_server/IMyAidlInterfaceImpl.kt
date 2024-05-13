package com.example.aidl_server

import android.os.RemoteCallbackList
import android.util.Log

object IMyAidlInterfaceImpl : IMyAidlInterface.Stub() {

    private val TAG = javaClass.simpleName
    private val callbacks = RemoteCallbackList<IServiceStateCallback>()

    override fun ranRect(): CustomRect {
        Log.d(TAG, "ranRect requested.")
        return CustomRect().apply {
            left = (0..100).random()
            top = (0..100).random()
            right = (0..100).random()
            bottom = (0..100).random()
        }
    }

    // isAvailable을 외부에서 호출 시 어떻게 반환해줄지 구현
    override fun isAvailable(): Boolean {
        Log.d(TAG, "isAvailable requested.")
        return MyData.boolState
    }

    // 외부에서 콜백 등록 호출 시 RemoteCallbackList에 추가하도록 구현
    override fun registerCallback(callback: IServiceStateCallback?): Boolean {
        val ret = callbacks.register(callback)
        Log.d(TAG, "registerCallback: $ret")
        return ret
    }

    // 외부에서 콜백 해제 호출 시 RemoteCallbackList에서 제거하도록 구현
    override fun unregisterCallback(callback: IServiceStateCallback?): Boolean {
        val ret = callbacks.unregister(callback)
        Log.d(TAG, "unregisterCallback: $ret")
        return ret
    }

    // aidl에는 구현되어있지 않지만 Server app에서 clients들에게 콜백을 호출 하기 위한 함수.
    fun broadcastToCurrentStateToClients(status: Int) {
        val n = callbacks.beginBroadcast()
        Log.d(TAG, "broadcast size:$n")

        for (i in 0 until n) {
            callbacks.getBroadcastItem(i).run {
                Log.d(TAG, "status:$status")
                this.onServiceStateChanged(status)
            }
        }
        callbacks.finishBroadcast()
    }

    fun broadcastToCustomRectToClients() {
        val n = callbacks.beginBroadcast()
        Log.d(TAG, "broadcast size:$n")

        for (i in 0 until n) {
            callbacks.getBroadcastItem(i).run {
                val rect = ranRect()
                Log.d(TAG, "rect:${rect.left}, ${rect.top}, ${rect.right}, ${rect
                    .bottom}")
                this.makeRectByStatus(rect)
            }
        }
        callbacks.finishBroadcast()
    }
}