package com.fyp.models

import android.os.Parcel
import android.os.Parcelable

class MyData() : Parcelable {
    var key=""
    var value=0

    constructor(parcel: Parcel) : this() {
        key = parcel.readString().toString()
        value = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(key)
        parcel.writeInt(value)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MyData> {
        override fun createFromParcel(parcel: Parcel): MyData {
            return MyData(parcel)
        }

        override fun newArray(size: Int): Array<MyData?> {
            return arrayOfNulls(size)
        }
    }
}