package com.drinfi.ticklemediaassignment.retrofit

import com.drinfi.ticklemediaassignment.data.GithubServiceData
import com.drinfi.ticklemediaassignment.data.RepoDetailResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {

    @GET("users?")
    fun getServices(@Query("since") since: String?): Call<MutableList<GithubServiceData>>

    @GET("users/{username}")
    fun getRepoDetail(@Path("username") username: String?): Call<RepoDetailResponse>
}