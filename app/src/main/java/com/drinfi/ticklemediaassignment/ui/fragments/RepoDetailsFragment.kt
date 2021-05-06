package com.drinfi.ticklemediaassignment.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.drinfi.ticklemediaassignment.R
import com.drinfi.ticklemediaassignment.databinding.FragmentDetailsBinding
import com.drinfi.ticklemediaassignment.utils.isNetworkAvailable
import com.drinfi.ticklemediaassignment.viewmodels.GithubServiceViewModel
import kotlinx.android.synthetic.main.fragment_details.*

class RepoDetailsFragment : Fragment() {

    companion object {

        fun newInstance(loginId: String) = RepoDetailsFragment().apply {
            arguments = Bundle().apply {
                putString("LOGIN_ID", loginId)
            }
        }
    }

    lateinit var fragmentDetailsBinding: FragmentDetailsBinding
    lateinit var githubServiceViewModel: GithubServiceViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentDetailsBinding = FragmentDetailsBinding.bind(view)
        getRepoDetail()
    }

    private fun getRepoDetail() {
        val args = arguments
        val userName = args!!.getString("LOGIN_ID")
        if (isNetworkAvailable(activity!!)) {
            fetchRepoDetail(userName = userName ?: "")
        } else {
            Toast.makeText(activity, "Please check your internet connection!", Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun fetchRepoDetail(userName: String) {
        githubServiceViewModel = ViewModelProvider(this).get(GithubServiceViewModel::class.java)
        detail_progress.visibility = View.VISIBLE
        githubServiceViewModel.getRepoDetailService(userName)!!
            .observe(this, Observer { repoDetailResponse ->
                detail_progress.visibility = View.GONE
                fragmentDetailsBinding.repoDetail = repoDetailResponse
            })
    }
}