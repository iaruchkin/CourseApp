package iaruchkin.courseapp.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import iaruchkin.courseapp.R;
import iaruchkin.courseapp.ui.intro.IntroFragment;
import iaruchkin.courseapp.ui.intro.Storage;

public class MainActivity extends AppCompatActivity implements MessageFragmentListener {

    public final static String NEWS_LIST_TAG = "NEWS_LIST";
    public final static String NEWS_DETAILS_TAG = "NEWS_DETAILS";
    public final static String ABOUT_TAG = "ABOUT";
    public final static String INTRO_TAG = "INTRO";

    private FragmentManager mFragmentManager;
    private NewsListFragment mNewsListFragment;
    private IntroFragment mIntroFragment;
    private AboutFragment mAboutFragment;
    private NewsDetailsFragment mNewsDetailsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();

        if (savedInstanceState == null){
            if (Storage.needToShowIntro(this)) {
                startIntro();
            } else {
                startNewsList();
            }
        }
    }

    private void startIntro(){
        mIntroFragment = new IntroFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_list, mIntroFragment)
                .commit();
    }

    private void startNewsList(){
        mNewsListFragment = new NewsListFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_list, mNewsListFragment)
                .commit();
    }

    private void startNewsDetails(String message){
        mNewsDetailsFragment = NewsDetailsFragment.newInstance(message);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_list, mNewsDetailsFragment)
                .addToBackStack(null)
                .commit();
    }

    private void startAbout(){
        mAboutFragment = new AboutFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_list, mAboutFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void init(){
        setContentView(R.layout.activity_main);
        mFragmentManager = getSupportFragmentManager();
    }

    @Override
    public void onActionClicked(String fragmentTag, String message) {
        switch (fragmentTag){
            case NEWS_LIST_TAG:
                startNewsList();
                break;
            case NEWS_DETAILS_TAG:
                startNewsDetails(message);
                break;
            case ABOUT_TAG:
                startAbout();
                break;
            case INTRO_TAG:
                startIntro();
                break;
        }

    }
}
//TODO подумать над уменьшением числа запросов к серверу