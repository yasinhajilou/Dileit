package com.example.dileit.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dileit.databinding.ActivityAboutBinding
import com.example.dileit.view.adapter.recycler.ContributorsRecyclerAdapter
import com.example.dileit.viewmodel.NetworkViewModel

class AboutActivity : AppCompatActivity() {
    private lateinit var networkViewModel: NetworkViewModel
    private lateinit var mBinding: ActivityAboutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        networkViewModel = ViewModelProvider(this).get(NetworkViewModel::class.java)

        val rvAdapter = ContributorsRecyclerAdapter()
        //call for getting data
        networkViewModel.getContributors()

        mBinding.rvContributor.apply {
            adapter = rvAdapter
            layoutManager = GridLayoutManager(context, 3)
            setHasFixedSize(true)
        }
        networkViewModel.liveContributorList.observe(this, {
            rvAdapter.setData(it)
        })
    }
}