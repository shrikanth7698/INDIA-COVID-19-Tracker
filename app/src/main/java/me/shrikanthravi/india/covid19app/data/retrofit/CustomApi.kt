package me.shrikanthravi.india.covid19app.data.retrofit

import me.shrikanthravi.india.covid19app.data.model.StateDistrictWise
import me.shrikanthravi.india.covid19app.data.model.Stats
import retrofit2.http.*

interface CustomApi {

    @GET("/data.json")
    suspend fun getStats(): Stats

    @GET("/v2/state_district_wise.json")
    suspend fun getDistrictStats(): List<StateDistrictWise>

}