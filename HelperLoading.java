package com.malik.bestenglish;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import java.util.Objects;

public class HelperLoading {


    private Context context;
    private Activity activity;
    private View view;
    private TextView infonya;
    private Dialog dialog;
    HelperLoading(Context context) {
        this.context = context;
        this.activity = (Activity)context;

        view = activity.getLayoutInflater().inflate(R.layout.layout_helper_loading,null,false);
        infonya = view.findViewById(R.id.loadingInfo);
        dialog = new Dialog(context,R.style.NewDialog);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(false);
    }

    void loadingShow(String info, int background){
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(background);
        infonya.setText(info);
        dialog.show();
    }

    void loadingHide(){
        dialog.dismiss();
    }
}
