package com.example.pi_dispositivos_moveis.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.pi_dispositivos_moveis.AnuncieViewModel;
import com.example.pi_dispositivos_moveis.R;
import com.example.pi_dispositivos_moveis.Util;

public class Anuncie extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anuncie);

        AnuncieViewModel anuncieViewModel = new ViewModelProvider(this).get(AnuncieViewModel.class);
        String currentPhotoPath = anuncieViewModel.getCurrentPhotoPath();
        if(!currentPhotoPath.isEmpty()){
            ImageView imvPhoto = findViewById(R.id.imvPhotoAnuncie);
            Bitmap bitmap = Util.getBitmap(currentPhotoPath, imvPhoto.getWidth(), imvPhoto.getHeight());
            imvPhoto.setImageBitmap(bitmap);
        }
    }
}