package me.shrikanthravi.india.covid19app.data.model
import com.google.gson.annotations.SerializedName


data class StateDistrictWise(
    @SerializedName("districtData")
    val districtData: List<DistrictData>,
    @SerializedName("state")
    val state: String
)

data class DistrictData(
    @SerializedName("confirmed")
    val confirmed: Int,
    @SerializedName("delta")
    val delta: Delta,
    @SerializedName("district")
    val district: String,
    @SerializedName("lastupdatedtime")
    val lastupdatedtime: String
)

data class Delta(
    @SerializedName("confirmed")
    val confirmed: Int
)