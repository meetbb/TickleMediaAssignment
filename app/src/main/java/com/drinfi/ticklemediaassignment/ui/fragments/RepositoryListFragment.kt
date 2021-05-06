package com.drinfi.ticklemediaassignment.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.drinfi.ticklemediaassignment.R
import com.drinfi.ticklemediaassignment.adapter.GithubReposAdapter
import com.drinfi.ticklemediaassignment.data.GithubServiceData
import com.drinfi.ticklemediaassignment.utils.isNetworkAvailable
import com.drinfi.ticklemediaassignment.viewmodels.GithubServiceViewModel
import kotlinx.android.synthetic.main.fragment_repos_list.*

class RepositoryListFragment : Fragment() {

    companion object {

        fun newInstance(): RepositoryListFragment {
            return RepositoryListFragment()
        }
    }

    lateinit var githubServiceViewModel: GithubServiceViewModel
    var githubServicesList: List<GithubServiceData> = emptyList()
    var githubReposAdapter: GithubReposAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_repos_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
    }

    private fun initAdapter() {
        progress_circular.visibility = View.GONE
        repo_recycler_view.layoutManager = LinearLayoutManager(activity)
        repo_recycler_view.hasFixedSize()
        githubReposAdapter = GithubReposAdapter(githubServicesList, selectedRepo = { repo ->
            Log.e("SELECTIOn", "Selected repo is: " + repo.login)
            activity!!.supportFragmentManager
                .beginTransaction()
                .add(R.id.details_fragment, RepoDetailsFragment.newInstance(), "RepoDetails")
                .commit()
        })
        repo_recycler_view.adapter = githubReposAdapter
        if (isNetworkAvailable(activity!!)) {
            fetchGithubServices()
        } else {
            Toast.makeText(activity, "Please check your internet connection!", Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun fetchGithubServices() {
        githubServiceViewModel = ViewModelProvider(this).get(GithubServiceViewModel::class.java)
        progress_circular.visibility = View.VISIBLE
        githubServiceViewModel.getGithubServices()!!.observe(this, Observer { githubServiceData ->
            progress_circular.visibility = View.GONE
            githubServicesList = githubServiceData
            githubReposAdapter!!.updateData(githubServicesList)
        })
    }
}