package com.example.dileit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.dileit.databinding.ActivityAboutBinding
import com.example.dileit.viewmodel.NetworkViewModel

class AboutActivity : AppCompatActivity() {
    private lateinit var networkViewModel: NetworkViewModel
    private lateinit var mBinding: ActivityAboutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        networkViewModel = ViewModelProvider(this).get(NetworkViewModel::class.java)

        //call for getting data
        networkViewModel.getContributors()

        networkViewModel.liveContributorList.observe(this, {

        })
    }
}