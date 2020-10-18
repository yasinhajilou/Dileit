package com.yasinhajilou.dileit.view.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.yasinhajilou.dileit.constant.UrlsConstants
import com.yasinhajilou.dileit.databinding.ActivityAboutBinding
import com.yasinhajilou.dileit.view.adapter.recycler.ContributorsRecyclerAdapter
import com.yasinhajilou.dileit.viewmodel.NetworkViewModel

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

        mBinding.ivShare.setOnClickListener {
            shareApp()
        }

        mBinding.ivGithub.setOnClickListener { openBrowser(UrlsConstants.githubUrl) }
    }

    private fun shareApp() {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        val shareContent = "Hey\nDileit is a dictionary app with Leitner system. you can download it from here: \nhttps://cafebazaar.ir/app/${packageName}"
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Share Dileit")
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareContent)
        startActivity(Intent.createChooser(shareIntent, "Share App Via: "))
    }

    private fun openBrowser(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }
}