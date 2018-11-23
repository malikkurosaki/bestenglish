package com.malik.bestenglish;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.ByteArrayOutputStream;

public class HelperBitmapToUri {


    private Context context;
    private Activity activity;

    public HelperBitmapToUri(Context context) {
        this.context = context;
        this.activity = (Activity)context;
    }

    Uri tryBitmapToUri(Bitmap bitmapData){
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmapData.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmapData, "Title", null);
        return Uri.parse(path);
    }
}
