package com.kelompok5.ecotech.data.model.response.kolektor

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class GetAllKolektorResponse(
    @SerializedName("statusCode")
    val statusCode: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: List<allKolektor>
)

data class allKolektor(
    val id: String,
    val name: String,
    val alamat: String,
    val nohp: String,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(alamat)
        parcel.writeString(nohp)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<allKolektor> {
        override fun createFromParcel(parcel: Parcel): allKolektor {
            return allKolektor(parcel)
        }

        override fun newArray(size: Int): Array<allKolektor?> {
            return arrayOfNulls(size)
        }
    }

}
