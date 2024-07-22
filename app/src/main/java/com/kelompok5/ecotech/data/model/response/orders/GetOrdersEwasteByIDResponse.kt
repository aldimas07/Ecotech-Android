package com.kelompok5.ecotech.data.model.response.orders

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.kelompok5.ecotech.data.model.response.kolektor.allKolektor

data class GetOrdersEwasteByIDResponse(
    @SerializedName("statusCode")
    val statusCode: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: List<orderDataByID>
)

data class orderDataByID(
    val penyetor_id: String,
    val penyetor_name: String,
    val item_image: String,
    val status: String,
    val created_at: String,
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(penyetor_id)
        parcel.writeString(penyetor_name)
        parcel.writeString(item_image)
        parcel.writeString(status)
        parcel.writeString(created_at)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<orderDataByID> {
        override fun createFromParcel(parcel: Parcel): orderDataByID {
            return orderDataByID(parcel)
        }

        override fun newArray(size: Int): Array<orderDataByID?> {
            return arrayOfNulls(size)
        }
    }
}

