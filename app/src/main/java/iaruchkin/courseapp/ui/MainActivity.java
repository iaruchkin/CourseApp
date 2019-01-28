package iaruchkin.courseapp.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import iaruchkin.courseapp.R;
import iaruchkin.courseapp.ui.intro.IntroFragment;
import iaruchkin.courseapp.ui.intro.Storage;

public class MainActivity extends AppCompatActivity implements MessageFragmentListener {

    private FragmentManager mFragmentManager;
    private NewsListFragment mNewsListFragment;
    private IntroFragment mIntroFragment;

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
                .add(R.id.frame_list, mIntroFragment)
                .commit();
    }

    private void startNewsList(){
        mNewsListFragment = new NewsListFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_list, mNewsListFragment)
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
    public void onNextMessageClicked() {
        startNewsList();
    }
}

//TODO Добвавить тэги(посмотреть в лекции)
//TODO добавить связь со страничкой about и newsDetails
//TODO избавиться от большого числа запросов на сервер
