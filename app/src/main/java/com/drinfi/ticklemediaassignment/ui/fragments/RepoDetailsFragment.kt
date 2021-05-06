package com.drinfi.ticklemediaassignment.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.drinfi.ticklemediaassignment.R
import com.drinfi.ticklemediaassignment.databinding.FragmentDetailsBinding

class RepoDetailsFragment : Fragment() {

    companion object {

        fun newInstance(detailUrl: String) = RepoDetailsFragment().apply {
            arguments = Bundle().apply {
                putString("DETAIL_URL", detailUrl)
            }
        }
    }

    lateinit var fragmentDetailBinding: FragmentDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentDetailBinding = FragmentDetailsBinding.inflate(inflater, container, false)
        return inflater.inflate(R.layout.fragment_details, container, false)
    }


}