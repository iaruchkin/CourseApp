package iaruchkin.courseapp.presentation.presenter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import iaruchkin.courseapp.App;
import iaruchkin.courseapp.R;
import iaruchkin.courseapp.common.State;
import iaruchkin.courseapp.presentation.view.AboutView;

import static iaruchkin.courseapp.ui.MainActivity.ABOUT_TAG;

@InjectViewState
public class AboutPresenter extends MvpPresenter<AboutView> {

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
    }

    public void sendMessage(@NonNull final String message) {
        getViewState().composeEmail(message);
    }

    public void openLink(@NonNull final String url) {
        getViewState().openURL(url);
    }
}
