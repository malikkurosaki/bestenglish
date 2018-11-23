package com.malik.bestenglish;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class HelperGetPhotoProfileStorage {


    interface OnGetPhotoProfileStorage{
        void fotoProfile(Uri uri);
    }

    private Context context;
    private Activity activity;
    private StorageReference photoStorage;
    private HelperUserGetPhoneNumber photoPhoneNumber;
    HelperGetPhotoProfileStorage(Context context) {
        this.context = context;
        this.activity = (Activity)context;

        photoPhoneNumber = new HelperUserGetPhoneNumber(context);


    }

    void setOnGetPhotoProfileStorage(final OnGetPhotoProfileStorage storage){
        photoPhoneNumber.setOnUserGetPhoneNuber(new HelperUserGetPhoneNumber.OnUserGetPhoneNumber() {
            @Override
            public void phoneNumber(String phoneNumber) {
                photoStorage = FirebaseStorage.getInstance().getReference().child("users/profile/"+phoneNumber+"/photoProfile.png");
                photoStorage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        storage.fotoProfile(uri);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

            }
        });
    }
}
