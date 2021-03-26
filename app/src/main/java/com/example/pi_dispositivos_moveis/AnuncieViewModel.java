package com.example.pi_dispositivos_moveis;

import androidx.lifecycle.ViewModel;

public class AnuncieViewModel extends ViewModel {

    String currentPhotoPath = "";


    public String getCurrentPhotoPath() {
        return currentPhotoPath;
    }

    public void setCurrentPhotoPath(String currentPhotoPath) {
        this.currentPhotoPath = currentPhotoPath;
    }
}
