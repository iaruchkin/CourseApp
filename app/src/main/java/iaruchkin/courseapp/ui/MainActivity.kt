package iaruchkin.courseapp.ui

import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity

import iaruchkin.courseapp.R
import iaruchkin.courseapp.ui.intro.IntroFragment
import iaruchkin.courseapp.ui.intro.Storage

class MainActivity : AppCompatActivity(), MessageFragmentListener {

    private var mFragmentManager: FragmentManager? = null
    private var mNewsListFragment: NewsListFragment? = null
    private var mIntroFragment: IntroFragment? = null
    private var mAboutFragment: AboutFragment? = null
    private var mNewsDetailsFragment: NewsDetailsFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()

        if (savedInstanceState == null) {
            if (Storage.needToShowIntro(this)) {
                startIntro()
            } else {
                startNewsList()
            }
        }
    }

    private fun startIntro() {
        mIntroFragment = IntroFragment()
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.frame_list, mIntroFragment!!)
                .commit()
    }

    private fun startNewsList() {
        mNewsListFragment = NewsListFragment()
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.frame_list, mNewsListFragment!!)
                .commit()
    }

    private fun startNewsDetails(message: String) {
        mNewsDetailsFragment = NewsDetailsFragment.newInstance(message)
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.frame_list, mNewsDetailsFragment!!)
                .addToBackStack(null)
                .commit()
    }

    private fun startAbout() {
        mAboutFragment = AboutFragment()
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.frame_list, mAboutFragment!!)
                .addToBackStack(null)
                .commit()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun init() {
        setContentView(R.layout.activity_main)
        mFragmentManager = supportFragmentManager
    }

    override fun onActionClicked(fragmentTag: String, message: String) {
        when (fragmentTag) {
            NEWS_LIST_TAG -> startNewsList()
            NEWS_DETAILS_TAG -> startNewsDetails(message)
            ABOUT_TAG -> startAbout()
            INTRO_TAG -> startIntro()
        }

    }

    companion object {

        val NEWS_LIST_TAG = "NEWS_LIST"
        val NEWS_DETAILS_TAG = "NEWS_DETAILS"
        val ABOUT_TAG = "ABOUT"
        val INTRO_TAG = "INTRO"
    }
}