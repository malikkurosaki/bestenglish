package com.malik.bestenglish;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.widget.ImageView;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

public class HelperCircleImage {

    private Context context;
    private Activity activity;

    HelperCircleImage(Context context) {
        this.context = context;
        this.activity = (Activity)context;
    }

    void onLoadCircleImage( Uri source, ImageView targetImageView){
        Transformation transformation = new RoundedTransformationBuilder()
                .borderColor(Color.BLACK)
                .borderWidthDp(1)
                .cornerRadiusDp(20)
                .oval(false)
                .build();
        Picasso.get()
                .load(source)
                .centerCrop()
                .transform(transformation)
                .into(targetImageView);
    }
}
