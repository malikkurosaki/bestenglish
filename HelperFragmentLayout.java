package com.malik.bestenglish;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

public class HelperFragmentLayout extends Fragment{
    private Context context;
    private Activity activity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        this.activity = (Activity)context;
    }

    void helperFragmentVew(int targetContainer,Fragment replacePragment){
         FragmentTransaction helperTransaction = getFragmentManager().beginTransaction();
         helperTransaction.replace(targetContainer,replacePragment);
         helperTransaction.commit();
    }
}
