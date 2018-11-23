package com.malik.bestenglish;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LayoutProfile extends Fragment{



    OnUserProfileLoad userProfileLoad;
    interface OnUserProfileLoad{
        void propertyLoad(ImageView profilePhotoUserView, TextView profileUserName, TextView profileSaldo, TextView profileStatus, TextView profileInfoName, TextView profileInfoSex, TextView profileInfoAge, TextView profileInfoPhoneNumber);
    }


    private Context context;
    private Activity activity;
    private ImageView profilePhotoUserView;
    private TextView profileUserName,profileSaldo,profileStatus,profileInfoName,profileInfoSex,profileInfoAge,profileInfoPhoneNumber;
    private HelperGetProfileProperty profilePropertyProfile;


    public static LayoutProfile newInstance(){
        return new LayoutProfile();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        this.activity = (Activity)context;

        profilePropertyProfile = new HelperGetProfileProperty(context);

        userProfileLoad = (OnUserProfileLoad)activity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_profile,container,false);
        profileViewActivity(view);
        return view;
    }

    void profileViewActivity(View view){
        Button editProfile = view.findViewById(R.id.profileEditProfile);
        profilePhotoUserView = view.findViewById(R.id.profilePhotoUser);
        profileUserName = view.findViewById(R.id.profileUserName);
        profileSaldo = view.findViewById(R.id.profileSaldo);
        profileStatus =view.findViewById(R.id.profileStatus);
        profileInfoName = view.findViewById(R.id.profileInfoName);
        profileInfoSex = view.findViewById(R.id.profileInfoSex);
        profileInfoAge = view.findViewById(R.id.profileInfoAge);
        profileInfoPhoneNumber = view.findViewById(R.id.profileInfoPhoneNumber);

        userProfileLoad.propertyLoad(profilePhotoUserView,profileUserName,profileSaldo,profileStatus,profileInfoName,profileInfoSex,profileInfoAge,profileInfoPhoneNumber);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceViewToEditProfile();
            }
        });
    }

    void replaceViewToEditProfile(){
        FragmentTransaction profileTransaction = getFragmentManager().beginTransaction();
        profileTransaction.replace(R.id.mainContainer,LayoutEditProfile.newInstance());
        profileTransaction.commit();
    }



    @Override
    public void onDetach() {
        super.onDetach();
    }


}
