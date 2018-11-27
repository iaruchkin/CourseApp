package iaruchkin.courseapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AboutActivity extends AppCompatActivity {

    private static final String TAG = "AboutActivity";
    private EditText mMessageEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }

        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");

        setContentView(R.layout.about_activity);

        mMessageEditText = findViewById(R.id.et_message);
        TextView mSendButton = findViewById(R.id.b_send);
        ImageView mTelegramLogo = findViewById(R.id.telegram_link);
        ImageView mInsagramLogo = findViewById(R.id.instagram_link);
        ImageView mVkLogo = findViewById(R.id.vk_link);

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                composeEmail(mMessageEditText.getText().toString());
            }
        });

        mTelegramLogo.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse(getString(R.string.telegram_link)));
                startActivity(intent);
            }
        });

        mInsagramLogo.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse(getString(R.string.instagram_link)));
                startActivity(intent);
            }
        });

        mVkLogo.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse(getString(R.string.vk_link)));
                startActivity(intent);
            }
        });
    }

    public void composeEmail(String messageEmail) {

        Log.i(TAG, "composeEmail");

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse(String.format("mailto:%s", getString(R.string.email_adress)))); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT,  getString(R.string.subject));
        intent.putExtra(Intent.EXTRA_TEXT, messageEmail);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(this, R.string.error_no_email_app, Toast.LENGTH_LONG).show();
        }
    }
}

