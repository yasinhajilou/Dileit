package com.yasinhajilou.dileit.view.activity

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.yasinhajilou.dileit.constant.URIConstants
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

        mBinding.ivGithub.setOnClickListener { openBrowser(URIConstants.githubUrl) }
        mBinding.ivInstagram.setOnClickListener { openInstagram() }
        mBinding.ivTelegram.setOnClickListener { openBrowser(URIConstants.telegramURL) }
        mBinding.lottieAnimRating.setOnClickListener { rateApp() }
        mBinding.message.setOnClickListener { sendEmail() }
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

    private fun openInstagram() {
        val uri = Uri.parse(URIConstants.instagramURL)
        val instaIntent = Intent(Intent.ACTION_VIEW, uri)
        instaIntent.setPackage(URIConstants.instagramPackageURI)

        if (isIntentAvailable(instaIntent)) {
            startActivity(instaIntent)
        } else {
            val newURI = Uri.parse(URIConstants.instagramURL)
            val newIntent = Intent(Intent.ACTION_VIEW, newURI)
            startActivity(newIntent)
        }
    }

    private fun isIntentAvailable(intent: Intent): Boolean {
        val packageManager: PackageManager = packageManager
        val list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
        return list.size > 0
    }

    private fun rateApp() {
        val packageManager = applicationContext.packageManager
        val intent = Intent(Intent.ACTION_EDIT)
        intent.data = Uri.parse("bazaar://details?id=" + applicationContext.packageName)
        intent.setPackage("com.farsitel.bazaar")
        if (intent.resolveActivity(packageManager) != null) startActivity(intent) else {
            Toast.makeText(this, "Didn't find appropriate application for rating!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun sendEmail() {
        val i = Intent(Intent.ACTION_SENDTO)
        i.type = "message/rfc822"
        i.putExtra(Intent.EXTRA_EMAIL, arrayOf(URIConstants.email))
        i.data = Uri.parse("mailto:");
        i.putExtra(Intent.EXTRA_SUBJECT, "Dileit Recommendations & Critics")
        try {
            startActivity(Intent.createChooser(i, "Send mail..."))
        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(this, "There are no email clients installed.", Toast.LENGTH_SHORT).show()
        }
    }
}