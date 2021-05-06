package com.drinfi.ticklemediaassignment.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.drinfi.ticklemediaassignment.data.GithubServiceData
import com.drinfi.ticklemediaassignment.data.RepoDetailResponse
import com.drinfi.ticklemediaassignment.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object GithubRepository {

    val githubServiceData = MutableLiveData<List<GithubServiceData>>()

    val repoDetailServiceData = MutableLiveData<RepoDetailResponse>()

    fun getGithubServicesApiCall(): MutableLiveData<List<GithubServiceData>> {

        val call = RetrofitClient.apiInterface.getServices()

        call.enqueue(object : Callback<List<GithubServiceData>> {
            override fun onFailure(call: Call<List<GithubServiceData>>, t: Throwable) {
                // TODO("Not yet implemented")
                Log.v("DEBUG : ", t.message.toString())
            }

            override fun onResponse(
                call: Call<List<GithubServiceData>>,
                response: Response<List<GithubServiceData>>
            ) {
                // TODO("Not yet implemented")
                Log.i("DEBUG : ", response.body().toString())

                githubServiceData.value = response.body()
            }
        })
        return githubServiceData
    }

    fun getRepoDetailApiCall(username: String): MutableLiveData<RepoDetailResponse> {

        val call = RetrofitClient.apiInterface.getRepoDetail(username)

        call.enqueue(object : Callback<RepoDetailResponse> {
            override fun onFailure(call: Call<RepoDetailResponse>, t: Throwable) {
                // TODO("Not yet implemented")
                Log.v("DEBUG : ", t.message.toString())
            }

            override fun onResponse(
                call: Call<RepoDetailResponse>,
                response: Response<RepoDetailResponse>
            ) {
                // TODO("Not yet implemented")
                Log.i("DEBUG : ", response.body().toString())

                repoDetailServiceData.value = response.body()
            }
        })
        return repoDetailServiceData
    }
}