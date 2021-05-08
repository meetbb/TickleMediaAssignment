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
import com.drinfi.ticklemediaassignment.utils.ILoadMore
import com.drinfi.ticklemediaassignment.utils.isNetworkAvailable
import com.drinfi.ticklemediaassignment.viewmodels.GithubServiceViewModel
import kotlinx.android.synthetic.main.fragment_repos_list.*
import java.util.*

class RepositoryListFragment : Fragment(), ILoadMore {

    companion object {

        fun newInstance(): RepositoryListFragment {
            return RepositoryListFragment()
        }
    }

    lateinit var githubServiceViewModel: GithubServiceViewModel
    var githubServicesList: MutableList<GithubServiceData?> = ArrayList()
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
        githubReposAdapter =
            GithubReposAdapter(
                activity!!,
                repo_recycler_view,
                githubServicesList,
                selectedRepo = { repo ->
                    Log.e("SELECTIOn", "Selected repo is: " + repo.login)
                    activity!!.supportFragmentManager
                        .beginTransaction()
                        .replace(
                            R.id.details_fragment,
                            RepoDetailsFragment.newInstance(repo.login),
                            "RepoDetails"
                        ).addToBackStack(RepoDetailsFragment::class.simpleName)
                        .commit()
                })
        githubReposAdapter!!.setLoadMore(this)
        repo_recycler_view.adapter = githubReposAdapter
        if (isNetworkAvailable(activity!!)) {
            fetchGithubServices(since = "10")
        } else {
            Toast.makeText(activity, "Please check your internet connection!", Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun fetchGithubServices(since: String) {
        githubServiceViewModel = ViewModelProvider(this).get(GithubServiceViewModel::class.java)
        if (githubServicesList.size == 0) {
            progress_circular.visibility = View.VISIBLE
        } else {
            githubServicesList.add(null)
            githubReposAdapter!!.notifyItemInserted(githubServicesList.size - 1)
        }
        githubServiceViewModel.getGithubServices(since)!!
            .observe(this, Observer { githubServiceData ->
                if (githubServicesList.size > 0) {
                    githubServicesList.removeAt(githubServicesList.size - 1)
                    githubReposAdapter!!.notifyItemRemoved(githubServicesList.size)
                } else {
                    progress_circular.visibility = View.GONE
                }
                for (githubData in githubServiceData) {
                    githubServicesList.add(githubData)
                }
                githubReposAdapter!!.updateData(githubServicesList)
            })
    }

    override fun onLoadMore(threshold: Int) {
        if (isNetworkAvailable(activity!!)) {
            fetchGithubServices(threshold.toString())
        } else {
            githubReposAdapter!!.setLoaded()
            Toast.makeText(activity, "Please check your internet connection!", Toast.LENGTH_LONG)
                .show()
        }
    }
}