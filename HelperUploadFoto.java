package com.malik.bestenglish;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.IntDef;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class HelperUploadFoto{

    @IntDef({PICTURE})
    @Retention(RetentionPolicy.SOURCE)
    @interface TAKE_PICTURE{}
    public static final int PICTURE = 654;


    private Context context;
    private Activity activity;
    private StorageReference uploadStorageReference;
    private HelperBitmapToUri uploadBitmapToUri;
    private HelperUserGetPhoneNumber uploadPhoneNumber;
    private HelperLoading uploadLoading;

    HelperUploadFoto(Context context) {
        this.context = context;
        this.activity = (Activity)context;
        uploadStorageReference = FirebaseStorage.getInstance().getReference();
        uploadBitmapToUri = new HelperBitmapToUri(context);
        uploadPhoneNumber = new HelperUserGetPhoneNumber(context);
        uploadLoading = new HelperLoading(context);
    }

    void tryTakePhoto(@TAKE_PICTURE int pictureCode){
        activity.startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE  ),pictureCode);
    }
    void tryUploadPhoto(Bitmap bitmapPhoto){
        uploadLoading.loadingShow("try upload your profile",R.color.colorTransparant);
        final Uri lokasiPhoto = uploadBitmapToUri.tryBitmapToUri(bitmapPhoto);
        uploadPhoneNumber.setOnUserGetPhoneNuber(new HelperUserGetPhoneNumber.OnUserGetPhoneNumber() {
            @Override
            public void phoneNumber(String phoneNumber) {
                final StorageReference lokasiStorage = uploadStorageReference.child("users/profile/"+phoneNumber+"/photoProfile.png");
                Picasso.get().load(lokasiPhoto).resize(200,200).centerCrop(Gravity.CENTER).into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        UploadTask tugasUpload = lokasiStorage.putFile(uploadBitmapToUri.tryBitmapToUri(bitmap));
                        tugasUpload.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Toast.makeText(activity.getApplicationContext(),"upoad your photo successfully",Toast.LENGTH_LONG).show();
                                uploadLoading.loadingHide();
                            }
                        });
                    }

                    @Override
                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });

            }
        });
    }


}
