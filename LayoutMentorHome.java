package com.malik.bestenglish;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class LayoutMentorHome extends Fragment{

   private Context context;
   private Activity activity;

   public static LayoutMentorHome newInstance(){
       return new LayoutMentorHome();
   }
   @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        this.activity = (Activity)context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View viewMentorHome =  inflater.inflate(R.layout.layout_mentor_home,container,false);
       mentorActivity(viewMentorHome);
       return viewMentorHome;
    }

    void mentorActivity(View viewMentorHome){

    }
    @Override
    public void onDetach() {
        super.onDetach();
    }
}
