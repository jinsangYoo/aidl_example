package com.example.aidl_client;

import android.os.Parcel;
import android.os.Parcelable;

public final class CustomRect implements Parcelable {
    public int left;
    public int top;
    public int right;
    public int bottom;

    public static final Parcelable.Creator<CustomRect> CREATOR = new Parcelable.Creator<CustomRect>() {
        public CustomRect createFromParcel(Parcel in) {
            return new CustomRect(in);
        }

        public CustomRect[] newArray(int size) {
            return new CustomRect[size];
        }
    };

    public CustomRect() {
    }

    private CustomRect(Parcel in) {
        readFromParcel(in);
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(left);
        out.writeInt(top);
        out.writeInt(right);
        out.writeInt(bottom);
    }

    public void readFromParcel(Parcel in) {
        left = in.readInt();
        top = in.readInt();
        right = in.readInt();
        bottom = in.readInt();
    }

    public int describeContents() {
        return 0;
    }
}