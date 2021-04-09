package com.example.pi_dispositivos_moveis.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pi_dispositivos_moveis.Config;
import com.example.pi_dispositivos_moveis.ProfileViewModel;
import com.example.pi_dispositivos_moveis.R;
import com.example.pi_dispositivos_moveis.Usuario;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        String login = Config.getLogin(ProfileActivity.this);

        ProfileViewModel profileViewModel = new ViewModelProvider(this,new ProfileViewModel.ProfileViewModelFactory(login)).get(ProfileViewModel.class);
        Usuario u = profileViewModel.getUsuario();
        ImageView imvFotoPerfil = findViewById(R.id.imvPerfil);
        imvFotoPerfil.setImageBitmap(u.getFoto());

        TextView tvNomePerfil = findViewById(R.id.tvNomePerfil);
        tvNomePerfil.setText(u.getNome());

        TextView tvEmailPerfil = findViewById(R.id.tvEmailPerfil);
        tvEmailPerfil.setText(u.getEmail());

        TextView tvTelefonePerfil = findViewById(R.id.tvTelefonePerfil);
        tvTelefonePerfil.setText(u.getTelefone());
    }
}