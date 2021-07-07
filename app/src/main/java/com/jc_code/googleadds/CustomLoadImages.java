package com.jc_code.googleadds;

import android.util.Log;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

public class CustomLoadImages {
    private static final String TAG = "CustomLoadImages";

    @BindingAdapter("android:loadCustomImage")
    public static void loadHomeRecImage(ImageView imageView, int imageUrl) {
        imageView.setImageResource(imageUrl);
    }

    @BindingAdapter("android:loadCustomImageUrl")
    public static void loadCustomTeacherImage(ImageView imageView, String imagePath) {
        if (null != imagePath && !imagePath.isEmpty()) {
            try {
                Glide.with(App.context)
                        .load(imagePath)
                        .centerCrop()
                        .placeholder(R.drawable.ic_launcher_background)
                        .into(imageView);

            } catch (Exception e) {
                e.printStackTrace();
                Log.d(TAG, "loadImage: " + e.getLocalizedMessage());
                imageView.setImageResource(R.drawable.ic_launcher_foreground);
            }
        } else imageView.setImageResource(R.drawable.ic_launcher_foreground);

    }
}
