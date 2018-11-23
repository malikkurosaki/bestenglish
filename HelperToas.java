package com.malik.bestenglish;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

public class HelperToas {


    private Context context;
    private Activity activity;
    public HelperToas(Context context) {
        this.context = context;
        this.activity = (Activity)context;
    }

    void infoNya(String info){
        Toast.makeText(activity.getApplicationContext(),info,Toast.LENGTH_LONG).show();
    }
}
