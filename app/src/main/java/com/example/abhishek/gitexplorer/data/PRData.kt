package com.example.abhishek.gitexplorer.data

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class PRData(@SerializedName("url") val url: String,
             @SerializedName("html_url") val htmlUrl: String,
             @SerializedName("diff_url") val diffUrl: String,
             @SerializedName("number") val prNumber: Int,
             @SerializedName("title") val title: String,
             @SerializedName("state") val state: String,
             @SerializedName("user") val user: User,
             @SerializedName("head") val head: Head,
             @SerializedName("created_at") val createdAt: String,
             @SerializedName("updated_at") val updatedAt: String) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString(),
        source.readString(),
        source.readString(),
        source.readInt(),
        source.readString(),
        source.readString(),
        source.readParcelable<User>(User::class.java.classLoader),
        source.readParcelable<Head>(Head::class.java.classLoader),
        source.readString(),
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(url)
        writeString(htmlUrl)
        writeString(diffUrl)
        writeInt(prNumber)
        writeString(title)
        writeString(state)
        writeParcelable(user, 0)
        writeParcelable(head, 0)
        writeString(createdAt)
        writeString(updatedAt)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<PRData> = object : Parcelable.Creator<PRData> {
            override fun createFromParcel(source: Parcel): PRData = PRData(source)
            override fun newArray(size: Int): Array<PRData?> = arrayOfNulls(size)
        }
    }
}

class Head(@SerializedName("user") val user: User,
           @SerializedName("repo") val repo: Repo?) : Parcelable {
    constructor(source: Parcel) : this(
        source.readParcelable<User>(User::class.java.classLoader),
        source.readParcelable<Repo>(Repo::class.java.classLoader)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeParcelable(user, 0)
        writeParcelable(repo, 0)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Head> = object : Parcelable.Creator<Head> {
            override fun createFromParcel(source: Parcel): Head = Head(source)
            override fun newArray(size: Int): Array<Head?> = arrayOfNulls(size)
        }
    }
}

class User(@SerializedName("login") val login: String,
           @SerializedName("id") val id: Long,
           @SerializedName("avatar_url") val avatarUrl: String,
           @SerializedName("html_url") val profileUrl: String,
           @SerializedName("body") val body: String) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString(),
        source.readLong(),
        source.readString(),
        source.readString(),
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(login)
        writeLong(id)
        writeString(avatarUrl)
        writeString(profileUrl)
        writeString(body)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<User> = object : Parcelable.Creator<User> {
            override fun createFromParcel(source: Parcel): User = User(source)
            override fun newArray(size: Int): Array<User?> = arrayOfNulls(size)
        }
    }
}

class Repo(@SerializedName("id") val id: Long,
           @SerializedName("name") val name: String,
           @SerializedName("html_url") val htmlUrl: String,
           @SerializedName("description") val description: String,
           @SerializedName("owner") val owner: Owner) : Parcelable {
    constructor(source: Parcel) : this(
        source.readLong(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readParcelable<Owner>(Owner::class.java.classLoader)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeLong(id)
        writeString(name)
        writeString(htmlUrl)
        writeString(description)
        writeParcelable(owner, 0)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Repo> = object : Parcelable.Creator<Repo> {
            override fun createFromParcel(source: Parcel): Repo = Repo(source)
            override fun newArray(size: Int): Array<Repo?> = arrayOfNulls(size)
        }
    }
}

class Owner(@SerializedName("id") val id: Long,
            @SerializedName("login") val login: String,
            @SerializedName("avatar_url") val avatarUrl: String,
            @SerializedName("html_url") val htmlUrl: String) : Parcelable {
    constructor(source: Parcel) : this(
        source.readLong(),
        source.readString(),
        source.readString(),
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeLong(id)
        writeString(login)
        writeString(avatarUrl)
        writeString(htmlUrl)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Owner> = object : Parcelable.Creator<Owner> {
            override fun createFromParcel(source: Parcel): Owner = Owner(source)
            override fun newArray(size: Int): Array<Owner?> = arrayOfNulls(size)
        }
    }
}