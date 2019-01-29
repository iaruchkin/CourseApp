package iaruchkin.courseapp.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import iaruchkin.courseapp.R;

public class AboutFragment extends Fragment {

    private static final String TAG = "ABOUT";
    private EditText mMessageEditText;
    private MessageFragmentListener listener;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.about_activity, container, false);

        setActionBar(view);
        findViews(view);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MessageFragmentListener){
            listener = (MessageFragmentListener) context;
        }
    }

    @Override
    public void onDetach() {
        listener = null;
        super.onDetach();
    }

    private void findViews(View view) {
        mMessageEditText = view.findViewById(R.id.et_message);
        TextView mSendButton = view.findViewById(R.id.b_send);
        ImageView mTelegramLogo = view.findViewById(R.id.telegram_link);
        ImageView mInsagramLogo = view.findViewById(R.id.instagram_link);
        ImageView mVkLogo = view.findViewById(R.id.vk_link);

        mSendButton.setOnClickListener(v -> composeEmail(mMessageEditText.getText().toString()));
        mTelegramLogo.setOnClickListener(v -> openURL(getString(R.string.telegram_link)));
        mInsagramLogo.setOnClickListener(v -> openURL(getString(R.string.instagram_link)));
        mVkLogo.setOnClickListener(v -> openURL(getString(R.string.vk_link)));
    }

    private void openURL(String url){
        Intent intent = new Intent()
                .setAction(Intent.ACTION_VIEW)
                .addCategory(Intent.CATEGORY_BROWSABLE)
                .setData(Uri.parse(url));

        if (intent.resolveActivity(getContext().getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(getContext(), getString(R.string.error_no_browser), Toast.LENGTH_SHORT).show();
        }    }

    private void setActionBar(View view) {
        setHasOptionsMenu(true);
        ((AppCompatActivity) getContext()).setSupportActionBar(view.findViewById(R.id.toolbar));
        ActionBar actionBar = ((AppCompatActivity) getContext()).getSupportActionBar();
        actionBar.setTitle(getString(R.string.name_res));
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }

    public void composeEmail(String messageEmail) {

        Log.i(TAG, "composeEmail");

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse(String.format("mailto:%s", getString(R.string.email_adress)))); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT,  getString(R.string.subject));
        intent.putExtra(Intent.EXTRA_TEXT, messageEmail);
        if (intent.resolveActivity(getContext().getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(getContext(), R.string.error_no_email_app, Toast.LENGTH_LONG).show();
        }
    }
}

