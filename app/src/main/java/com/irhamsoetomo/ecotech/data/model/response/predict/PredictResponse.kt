package com.irhamsoetomo.ecotech.data.model.response.predict

import com.google.gson.annotations.SerializedName

data class PredictResponse(

	@field:SerializedName("probability")
	val probability: String,

	@field:SerializedName("prediction")
	val prediction: String,

	@field:SerializedName("statusCode")
	val statusCode: Int
)
