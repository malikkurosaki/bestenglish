package com.malik.bestenglish;

import android.widget.ImageView;
import android.widget.TextView;

public class HolderPilihmentorObject {

    public String nama;
    public String foto;

    //for firebase
    public HolderPilihmentorObject() {
    }

    public HolderPilihmentorObject(String nama, String foto) {
        this.nama = nama;
        this.foto = foto;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
