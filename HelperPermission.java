package com.malik.bestenglish;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.IntDef;
import android.support.v4.app.ActivityCompat;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

public class HelperPermission {

    @IntDef({PERMISSION_CODE})
    @Retention(RetentionPolicy.SOURCE)
    @interface PERMISSION{}
    public static final int PERMISSION_CODE = 121;

    private String[] jeniPermisi = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.RECEIVE_SMS,
            Manifest.permission.READ_SMS,
            Manifest.permission.SEND_SMS,
    };

    private Context context;
    private Activity activity;
    public HelperPermission(Context context) {
        this.context = context;
        this.activity = (Activity)context;
    }

    void tryRequestPermission(@PERMISSION int permission){
        List<String> listPermission = new ArrayList<>();
        Boolean kosong = true;
        int hasil;
        for (String minta:jeniPermisi){
            hasil = ActivityCompat.checkSelfPermission(context,minta);
            if (hasil != PackageManager.PERMISSION_GRANTED){
                listPermission.add(minta);
                kosong = false;
            }
        }
        if (!kosong){
            if ((!listPermission.isEmpty())){
                ActivityCompat.requestPermissions(activity,listPermission.toArray(new String[listPermission.size()]),permission);
            }
        }
    }
}
