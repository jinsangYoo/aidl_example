package com.example.aidl_server

import android.os.Parcel
import android.os.Parcelable

class CustomRect() : Parcelable {
    var left: Int = 0
    var top: Int = 0
    var right: Int = 0
    var bottom: Int = 0

    companion object CREATOR : Parcelable.Creator<CustomRect> {
        override fun createFromParcel(parcel: Parcel): CustomRect {
            return CustomRect(parcel)
        }

        override fun newArray(size: Int): Array<CustomRect?> {
            return Array(size) { null }
        }
    }

    private constructor(inParcel: Parcel) : this() {
        readFromParcel(inParcel)
    }

    override fun writeToParcel(outParcel: Parcel, flags: Int) {
        outParcel.writeInt(left)
        outParcel.writeInt(top)
        outParcel.writeInt(right)
        outParcel.writeInt(bottom)
    }

    private fun readFromParcel(inParcel: Parcel) {
        left = inParcel.readInt()
        top = inParcel.readInt()
        right = inParcel.readInt()
        bottom = inParcel.readInt()
    }

    override fun describeContents(): Int {
        return 0
    }
}