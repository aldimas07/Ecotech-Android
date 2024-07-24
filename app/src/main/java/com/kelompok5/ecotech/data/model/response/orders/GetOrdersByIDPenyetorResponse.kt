package com.kelompok5.ecotech.data.model.response.orders

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class GetOrdersByIDPenyetorResponse(
    @SerializedName("statusCode")
    val statusCode: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: List<orderDataByIDPenyetor>
)

data class orderDataByIDPenyetor(
    val id: String,
    val kolektor_id: String,
    val kolektor_name: String,
    val alamat: String,
    val nohp: String,
    val item_image: String,
    val status: String,
    val created_at: String,
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(kolektor_name)
        parcel.writeString(alamat)
        parcel.writeString(nohp)
        parcel.writeString(item_image)
        parcel.writeString(status)
        parcel.writeString(created_at)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<orderDataByIDPenyetor> {
        override fun createFromParcel(parcel: Parcel): orderDataByIDPenyetor {
            return orderDataByIDPenyetor(parcel)
        }

        override fun newArray(size: Int): Array<orderDataByIDPenyetor?> {
            return arrayOfNulls(size)
        }
    }
}
