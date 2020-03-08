package com.challenge.theScore.core.data

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TeamsModel(
    @SerializedName("wins")
    @Expose
    var wins: String,
    @SerializedName("losses")
    @Expose
    var losses: String,
    @SerializedName("full_name")
    @Expose
    var fullName: String,
    @SerializedName("id")
    @Expose
    var id: Int,
    @SerializedName("players")
    @Expose
    var players: List<Player>
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.createTypedArrayList(Player)!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel?, p1: Int) {
        parcel?.writeString(wins)
        parcel?.writeString(losses)
        parcel?.writeString(fullName)
        parcel?.writeInt(id)
        parcel?.writeTypedList(players)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<TeamsModel> {
        override fun createFromParcel(parcel: Parcel): TeamsModel {
            return TeamsModel(parcel)
        }

        override fun newArray(size: Int): Array<TeamsModel?> {
            return arrayOfNulls(size)
        }
    }

}

