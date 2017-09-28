package com.zjy.kotlinnote.koitlinnote.bean

import android.os.Parcel
import android.os.Parcelable
import org.litepal.crud.DataSupport

/**
 * Created by zjy on QE.
 */
class NoteBean() : DataSupport(),Parcelable {
    var NoteName: String? = null
    var NoteType: String? = null
    var NoteContent: String? = null
    var NoteCreateTime: String? = null
    var NoteLastChangeTime: String? = null

    constructor(parcel: Parcel) : this() {
        NoteName = parcel.readString()
        NoteType = parcel.readString()
        NoteContent = parcel.readString()
        NoteCreateTime = parcel.readString()
        NoteLastChangeTime = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(NoteName)
        parcel.writeString(NoteType)
        parcel.writeString(NoteContent)
        parcel.writeString(NoteCreateTime)
        parcel.writeString(NoteLastChangeTime)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NoteBean> {
        override fun createFromParcel(parcel: Parcel): NoteBean {
            return NoteBean(parcel)
        }

        override fun newArray(size: Int): Array<NoteBean?> {
            return arrayOfNulls(size)
        }
    }
}