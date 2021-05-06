package com.drinfi.ticklemediaassignment.retrofit

import com.drinfi.ticklemediaassignment.data.GithubServiceData
import com.drinfi.ticklemediaassignment.data.RepoDetailResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {

    @GET("users?since=135")
    fun getServices(): Call<List<GithubServiceData>>

    @GET("users/{username}")
    fun getRepoDetail(@Path("username") username: String?): Call<RepoDetailResponse>
}