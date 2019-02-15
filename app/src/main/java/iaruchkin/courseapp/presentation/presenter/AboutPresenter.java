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

//    private Context context = App.INSTANCE.getApplicationContext();
//    private Resources resources = App.INSTANCE.getResources();

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

//    public void openLink(@NonNull final String url){
//        Intent intent = new Intent()
//                .setAction(Intent.ACTION_VIEW)
//                .addCategory(Intent.CATEGORY_BROWSABLE)
//                .setData(Uri.parse(url));
//
//        if (intent.resolveActivity(context.getPackageManager()) != null) {
//            context.startActivity(intent);
//        } else {
//            Toast.makeText(context, resources.getString(R.string.error_no_browser), Toast.LENGTH_SHORT).show();
//        }    }
//
//    public void sendMessage(@NonNull final String messageEmail) {
//
//        Log.i(ABOUT_TAG, "composeEmail");
//
//        Intent intent = new Intent(Intent.ACTION_SENDTO);
//        intent.setData(Uri.parse(String.format("mailto:%s", resources.getString(R.string.email_adress)))); // only email apps should handle this
//        intent.putExtra(Intent.EXTRA_SUBJECT,  resources.getString(R.string.subject));
//        intent.putExtra(Intent.EXTRA_TEXT, messageEmail);
//        if (intent.resolveActivity(context.getPackageManager()) != null) {
//            context.startActivity(intent);
//        } else {
//            Toast.makeText(context, R.string.error_no_email_app, Toast.LENGTH_LONG).show();
//        }
//    }
}
