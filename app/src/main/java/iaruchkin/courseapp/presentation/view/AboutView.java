package iaruchkin.courseapp.presentation.view;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(value = SkipStrategy.class)
public interface AboutView extends MvpView {

    void openURL(@NonNull String url);
    void composeEmail(@NonNull String message);

}


