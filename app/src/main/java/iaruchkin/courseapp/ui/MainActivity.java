package iaruchkin.courseapp.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import iaruchkin.courseapp.R;
import iaruchkin.courseapp.room.NewsEntity;
import iaruchkin.courseapp.ui.intro.IntroFragment;
import iaruchkin.courseapp.ui.intro.Storage;

public class MainActivity extends AppCompatActivity implements MessageFragmentListener {

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
            case "NEWS_LIST":
                startNewsList();
                break;
            case "NEWS_DETAILS":
                startNewsDetails(message);
                break;
            case "ABOUT":
                startAbout();
                break;
            case "INTRO":
                startIntro();
                break;
        }

    }
}

//TODO Поправить тэги, исправить ошибки
//TODO избавиться от большого числа запросов на сервер
