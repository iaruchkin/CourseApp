package iaruchkin.courseapp.ui;

import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;

import iaruchkin.courseapp.R;

public class MainActivity extends AppCompatActivity {

    private FragmentManager mFragmentManager;
//    private int mFrameList;
//    private FrameLayout mFrameToolbar;
//    private ToolbarFragment mToolbarFragment;
    private NewsListFragment mNewsListFragment;
    private String mNewsId;
//    private boolean isTwoPanel;
    String TAG = "info";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);//todo удалить
        mFragmentManager = getSupportFragmentManager();

//        init();
//        if (savedInstanceState == null) {
//            initToolbar();

//         replaceFragment(new NewsListFragment(), R.id.frame_list, false, "TAG_LIST_NEWS");

//        }

//////
        mNewsListFragment = new NewsListFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frame_list, mNewsListFragment)
                .commit();
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

//    private void init(){
//        setContentView(R.layout.activity_main);
//        mFrameList = R.id.frame_list;
//        mFragmentManager = getSupportFragmentManager();
////        isTwoPanel = findViewById(R.id.frame_details) != null;
//    }

//    private void replaceFragment(Fragment fragment, int frame, boolean addToBackStack, String tag){
//        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction()
//                .replace(frame, fragment, tag);
//        if (addToBackStack) {
//            fragmentTransaction.addToBackStack(null);
//        }
//        fragmentTransaction.commit();
//    }
}
