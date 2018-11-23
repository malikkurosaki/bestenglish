package com.malik.bestenglish;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.IntDef;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnSuccessListener;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Collections;
import java.util.List;

public class HelperLoginActivity {

    @IntDef({USER_LOGIN_CODE})
    @Retention(RetentionPolicy.SOURCE)
    @interface LOGIN_CODE{}
    static final int USER_LOGIN_CODE = 332;

    interface OnSucsessLogOut{
        void hasLogout();
    }
    private Context context;
    private Activity activity;
    private List<AuthUI.IdpConfig> configs;
    private HelperToas toas;
    public HelperLoginActivity(Context context) {
        this.context = context;
        this.activity = (Activity)context;
        configs = Collections.singletonList(new AuthUI.IdpConfig.PhoneBuilder().build());
        toas = new HelperToas(context);
    }

    void tryLogin(@LOGIN_CODE int loginCode){
        try {
            activity.startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(configs).build(),loginCode);
        }catch (Exception e){
            e.printStackTrace();
            toas.infoNya("gagal mencoba login");
        }
    }
    void tryLogOut(final OnSucsessLogOut logOut){
        AuthUI.getInstance().signOut(context).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                logOut.hasLogout();
            }
        });
    }


}
