package iaruchkin.courseapp.ui.intro

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

import iaruchkin.courseapp.R
import iaruchkin.courseapp.ui.MessageFragmentListener
import me.relex.circleindicator.CircleIndicator

import iaruchkin.courseapp.ui.MainActivity.Companion.NEWS_LIST_TAG

class IntroFragment : Fragment() {
    private var mPager: ViewPager? = null
    private var mPagerAdapter: PagerAdapter? = null
    private var btnSkip: Button? = null
    private var btnNext: Button? = null
    private var listener: MessageFragmentListener? = null

    internal var viewPagerPageChangeListener: ViewPager.OnPageChangeListener = object : ViewPager.OnPageChangeListener {

        override fun onPageSelected(position: Int) {

            if (position == NUM_PAGES - 1) {
                btnNext!!.text = getString(R.string.start)
                btnSkip!!.visibility = View.GONE
            } else {
                btnNext!!.text = getString(R.string.next)
                btnSkip!!.visibility = View.VISIBLE
            }
        }

        override fun onPageScrolled(arg0: Int, arg1: Float, arg2: Int) {

        }

        override fun onPageScrollStateChanged(arg0: Int) {

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        val view = inflater.inflate(R.layout.intro_layout, container, false)

        mPager = view.findViewById(R.id.pager)
        mPagerAdapter = ViewPagerAdapter(childFragmentManager)
        mPager!!.adapter = mPagerAdapter
        mPager!!.addOnPageChangeListener(viewPagerPageChangeListener)

        btnSkip = view.findViewById(R.id.btn_skip)
        btnNext = view.findViewById(R.id.btn_next)

        btnSkip?.setOnClickListener { v -> startNews() }

        btnNext!!.setOnClickListener { v ->

            val current = mPager!!.currentItem + 1
            if (current < NUM_PAGES) {
                mPager!!.currentItem = current
            } else {
                startNews()
            }
        }

        val indicator = view.findViewById<CircleIndicator>(R.id.indicator)
        indicator.setViewPager(mPager)

        return view
    }

    private fun startNews() {
            listener?.onActionClicked(NEWS_LIST_TAG, null)
    }

    override fun onStop() {
        super.onStop()
        Log.e("intro", "onStop")
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is MessageFragmentListener) {
            listener = context
        }
    }

    override fun onDetach() {
        super.onDetach()

    }

    class ViewPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

        override fun getCount(): Int {
            return NUM_PAGES
        }

        override fun getItem(position: Int): Fragment? {
            when (position) {
                0 -> return IntroPage.newInstance(R.drawable.intro_screen_01, R.string.intro_page1_title, R.color.bg_screen1)
                1 -> return IntroPage.newInstance(R.drawable.intro_screen_02, R.string.intro_page2_title, R.color.bg_screen2)
                2 -> return IntroPage.newInstance(R.drawable.intro_screen_03, R.string.intro_page3_title, R.color.bg_screen3)
            }
            return null
        }

    }

    companion object {

        private val NUM_PAGES = 3
    }

}