package com.drinfi.ticklemediaassignment.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.drinfi.ticklemediaassignment.data.GithubServiceData
import com.drinfi.ticklemediaassignment.repository.GithubRepository

class GithubServiceViewModel : ViewModel() {

    var githubServiceLiveData: MutableLiveData<List<GithubServiceData>>? = null

    fun getGithubServices(): LiveData<List<GithubServiceData>>? {
        githubServiceLiveData = GithubRepository.getGithubServicesApiCall()
        return githubServiceLiveData
    }

    val selectedRepo = MutableLiveData<GithubServiceData>()

    fun selectRepo(repo: GithubServiceData) {
        selectedRepo.value = repo
    }
}