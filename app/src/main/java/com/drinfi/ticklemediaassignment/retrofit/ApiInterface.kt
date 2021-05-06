package com.drinfi.ticklemediaassignment.retrofit

import com.drinfi.ticklemediaassignment.data.GithubServiceData
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {

    @GET("users?since=135")
    fun getServices(): Call<List<GithubServiceData>>

}