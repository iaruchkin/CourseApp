package iaruchkin.courseapp.ui.intro;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import iaruchkin.courseapp.R;

public class IntroFragment extends Fragment {

    private int title, color, image;

    // newInstance constructor for creating fragment with arguments
    public static IntroFragment newInstance(int image, int title, int color) {
        IntroFragment fragmentFirst = new IntroFragment();
        Bundle args = new Bundle();
        args.putInt("image", image);
        args.putInt("title", title);
        args.putInt("pageColor", color);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        image = getArguments().getInt("image", 0);
        title = getArguments().getInt("title",0);
        color = getArguments().getInt("pageColor", R.color.bg_screen1);
    }

        @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.intro_fragment, container, false);
            TextView tvLabel = view.findViewById(R.id.page_title);
            ImageView imageView = view.findViewById(R.id.screenshot_img);

            view.setBackgroundColor(getResources().getColor(color));
            imageView.setImageResource(image);
            tvLabel.setText(getResources().getText(title));

        return view;
    }
}