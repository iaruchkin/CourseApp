package iaruchkin.courseapp.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import iaruchkin.courseapp.R;
import iaruchkin.courseapp.ui.intro.IntroFragment;
import iaruchkin.courseapp.ui.intro.IntroFragment2;
import me.relex.circleindicator.CircleIndicator;

import static android.content.Context.MODE_PRIVATE;

public class IntroActivity extends FragmentActivity {

    private static final int NUM_PAGES = 3;
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private Button btnSkip, btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro_layout);

        if (!Storage.needToShowIntro(this.getApplicationContext())) {
            startSecondActivity();
        }

        mPager = findViewById(R.id.pager);
        mPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.addOnPageChangeListener(viewPagerPageChangeListener);

        btnSkip = findViewById(R.id.btn_skip);
        btnNext = findViewById(R.id.btn_next);

        btnSkip.setOnClickListener(v -> startSecondActivity());

        btnNext.setOnClickListener(v -> {

            int current = mPager.getCurrentItem()+1;
            if (current < NUM_PAGES) {
                mPager.setCurrentItem(current);
            } else {
                startSecondActivity();
            }
        });

        CircleIndicator indicator = findViewById(R.id.indicator);
        indicator.setViewPager(mPager);
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

    private void startSecondActivity() {
        startActivity(new Intent(this, NewsListActivity.class));
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("intro", "onStop");
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
                    return IntroFragment.newInstance(R.drawable.intro_screen_01, R.string.intro_page1_title, R.color.bg_screen1);
                case 1:
                    return IntroFragment.newInstance(R.drawable.intro_screen_02, R.string.intro_page2_title, R.color.bg_screen2);
                case 2:
                    return IntroFragment.newInstance(R.drawable.intro_screen_03, R.string.intro_page3_title, R.color.bg_screen3);
            }
            return null;
        }

    }

}

class Storage{

    private static final String SHARED_PREF = "INTRO_SHARED_PREF";
    private static final String SHARED_PREF_KEY_COUNTER = "SHOW_INTRO";
    private static boolean counter = false;

    private void saveCounter(Context context) {

        SharedPreferences sharedPref = context.getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(SHARED_PREF_KEY_COUNTER, counter);
        editor.apply();
    }
    private void initCounter(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
        counter = sharedPref.getBoolean(SHARED_PREF_KEY_COUNTER, false);
    }

    static boolean needToShowIntro(Context context){
        Storage storage = new Storage();
        storage.initCounter(context);
        counter = !counter;
        storage.saveCounter(context);

//        return counter;
        return true;
    }
}