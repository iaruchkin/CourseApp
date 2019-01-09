package iaruchkin.courseapp.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import iaruchkin.courseapp.R;
import io.reactivex.Completable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

import static android.content.Context.MODE_PRIVATE;

public class IntroActivity extends AppCompatActivity {

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        if (Storage.needToShowIntro(this.getApplicationContext())) {
            setContentView(R.layout.activity_intro);
            Disposable disposable = Completable.complete()
                    .delay(3, TimeUnit.SECONDS)
                    .subscribe(this::startSecondActivity);
            compositeDisposable.add(disposable);
        } else {
            startSecondActivity();
        }
    }

    private void startSecondActivity() {
        startActivity(new Intent(this, NewsListActivity.class));
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("intro", "onStop");
        compositeDisposable.dispose();
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

        return counter;
    }
}