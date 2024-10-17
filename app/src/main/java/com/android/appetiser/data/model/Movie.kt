package com.android.appetiser.data.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_movies")
data class Movie(
    @PrimaryKey val trackId: Int,
    val trackName: String?,
    val artworkUrl100: String?,
    val trackPrice: Double?,
    val primaryGenreName: String?,
    val longDescription: String?,
    val releaseDate: String?,
    val currency: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(trackId)
        parcel.writeString(trackName)
        parcel.writeString(artworkUrl100)
        parcel.writeValue(trackPrice)
        parcel.writeString(primaryGenreName)
        parcel.writeString(longDescription)
        parcel.writeString(releaseDate)
        parcel.writeString(currency)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Movie> {
        override fun createFromParcel(parcel: Parcel): Movie {
            return Movie(parcel)
        }

        override fun newArray(size: Int): Array<Movie?> {
            return arrayOfNulls(size)
        }
    }
}