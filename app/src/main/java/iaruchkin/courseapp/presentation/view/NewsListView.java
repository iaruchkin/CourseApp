package iaruchkin.courseapp.presentation.view;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

import iaruchkin.courseapp.common.State;
import iaruchkin.courseapp.room.NewsEntity;

@StateStrategyType(value = SingleStateStrategy.class)
public interface NewsListView extends MvpView {

    void showNews(@NonNull List<NewsEntity> news);
    void showState(@NonNull State state);

}
