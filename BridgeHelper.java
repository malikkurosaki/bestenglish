package com.malik.bestenglish;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;

public class BridgeHelper {

    private OnBridgePhotoProfileView photoProfileView;
    interface OnBridgePhotoProfileView{
        void profileView(Bitmap bitmapPhoto);
    }

    private DapatkanPosisi dapatkanPosisi;
    interface DapatkanPosisi{
        void posisi(int adapterPosition);
    }

    OnItemAdapterClick adapterClick;
    interface OnItemAdapterClick{
        void posisi(int posisinya);
    }

    private OnJikaSudah sudahkah;
    interface OnJikaSudah{
        void sudah();
    }
    private Context context;
    private Activity activity;

    BridgeHelper(Context context) {
        this.context = context;
        this.activity = (Activity)context;
    }

    void setOnGetView(Bitmap bitmapPhoto){
        if (photoProfileView != null){
            photoProfileView.profileView(bitmapPhoto);
        }
    }
    void addOnGetView(OnBridgePhotoProfileView viewProfile){
        photoProfileView = viewProfile;
    }

    void addOnJikaSudah(){
        if (sudahkah != null){
            sudahkah.sudah();
        }
    }
    void setOnMaka(OnJikaSudah makaSudah){
        sudahkah = makaSudah;
    }

    void ketikaDapatkanPosisi(int adapterPosition){
        if (dapatkanPosisi !=null){
            dapatkanPosisi.posisi(adapterPosition);
        }
    }
    void hasilDapatkanPosisi(DapatkanPosisi posisi1){
        dapatkanPosisi = posisi1;
    }

    void ketikaDiKlick(int posisinya){
        if (adapterClick!=null){
            adapterClick.posisi(posisinya);
        }
    }
    void hasilDariKlickNya(OnItemAdapterClick onItemAdapterClick){
        adapterClick = onItemAdapterClick;
    }
}
