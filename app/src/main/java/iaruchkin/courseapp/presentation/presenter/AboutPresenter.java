package iaruchkin.courseapp.presentation.presenter;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import iaruchkin.courseapp.common.State;
import iaruchkin.courseapp.presentation.view.AboutView;

@InjectViewState
public class AboutPresenter extends MvpPresenter<AboutView> {

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
//        getViewState().showState(State.Loading);
    }

    public void sendMessage(@NonNull final String message) {
        getViewState().composeEmail(message);
    }

    public void openLink(@NonNull final String url) {
        getViewState().openURL(url);
    }

}
