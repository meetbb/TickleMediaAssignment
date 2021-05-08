package com.drinfi.ticklemediaassignment.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.drinfi.ticklemediaassignment.R
import com.drinfi.ticklemediaassignment.ui.fragments.RepositoryListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Default Fragment Added in frame layout.
        supportFragmentManager
            .beginTransaction()
            .add(
                R.id.details_fragment,
                RepositoryListFragment.newInstance(),
                "RepositoryList"
            ).addToBackStack(RepositoryListFragment::class.simpleName)
            .commit()
    }

    override fun onBackPressed() {
        //Fragment removed on BackPress else default behaviour.
        if (supportFragmentManager.backStackEntryCount == 1) {
            finish()
        } else {
            super.onBackPressed()
        }
    }
}
