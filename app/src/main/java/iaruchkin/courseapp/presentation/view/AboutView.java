package iaruchkin.courseapp.presentation.view;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import iaruchkin.courseapp.common.State;

public interface AboutView extends MvpView {

    @StateStrategyType(value = SkipStrategy.class)
    void openURL(@NonNull String url);

    @StateStrategyType(value = SkipStrategy.class)
    void composeEmail(@NonNull String message);

//    void showState(@NonNull State state);

}


