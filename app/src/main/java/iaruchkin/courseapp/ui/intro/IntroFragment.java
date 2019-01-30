package iaruchkin.courseapp.ui.intro;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import iaruchkin.courseapp.R;
import iaruchkin.courseapp.ui.MessageFragmentListener;
import me.relex.circleindicator.CircleIndicator;

import static iaruchkin.courseapp.ui.MainActivity.NEWS_LIST_TAG;

public class IntroFragment extends Fragment {

    private static final int NUM_PAGES = 3;
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private Button btnSkip, btnNext;
    private MessageFragmentListener listener;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.intro_layout, container, false);

        mPager = view.findViewById(R.id.pager);
        mPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.addOnPageChangeListener(viewPagerPageChangeListener);

        btnSkip = view.findViewById(R.id.btn_skip);
        btnNext = view.findViewById(R.id.btn_next);

        btnSkip.setOnClickListener(v -> startNews());

        btnNext.setOnClickListener(v -> {

            int current = mPager.getCurrentItem()+1;
            if (current < NUM_PAGES) {
                mPager.setCurrentItem(current);
            } else {
                startNews();
            }
        });

        CircleIndicator indicator = view.findViewById(R.id.indicator);
        indicator.setViewPager(mPager);

    return view;
    }

    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {

            if (position == NUM_PAGES - 1) {
                btnNext.setText(getString(R.string.start));
                btnSkip.setVisibility(View.GONE);
            } else {
                btnNext.setText(getString(R.string.next));
                btnSkip.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    private void startNews() {
        if (listener != null) {
            listener.onActionClicked(NEWS_LIST_TAG, null);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e("intro", "onStop");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MessageFragmentListener) {
            listener = (MessageFragmentListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    public static class ViewPagerAdapter extends FragmentStatePagerAdapter {
        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return IntroPage.newInstance(R.drawable.intro_screen_01, R.string.intro_page1_title, R.color.bg_screen1);
                case 1:
                    return IntroPage.newInstance(R.drawable.intro_screen_02, R.string.intro_page2_title, R.color.bg_screen2);
                case 2:
                    return IntroPage.newInstance(R.drawable.intro_screen_03, R.string.intro_page3_title, R.color.bg_screen3);
            }
            return null;
        }

    }

}