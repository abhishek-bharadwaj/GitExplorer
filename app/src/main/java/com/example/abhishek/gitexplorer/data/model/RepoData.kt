package com.example.abhishek.gitexplorer.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class RepoData(@SerializedName("full_name") val fullName: String,
               @SerializedName("name") val name: String,
               @SerializedName("language") val language: String) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString(),
        source.readString(),
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(fullName)
        writeString(name)
        writeString(language)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<RepoData> = object : Parcelable.Creator<RepoData> {
            override fun createFromParcel(source: Parcel): RepoData =
                RepoData(source)
            override fun newArray(size: Int): Array<RepoData?> = arrayOfNulls(size)
        }
    }
}