package com.example.pi_dispositivos_moveis.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
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
        LiveData<Usuario> usuario = profileViewModel.getUsuario();
        usuario.observe(this, new Observer<Usuario>() {
            @Override
            public void onChanged(Usuario usuario) {
                TextView tvDataNascPerfil = findViewById(R.id.tvDataNascPerfil);
                tvDataNascPerfil.setText(usuario.getData());

                TextView tvLoginPerfil = findViewById(R.id.tvLoginPerfil);
                tvDataNascPerfil.setText(Config.getLogin(ProfileActivity.this));

                TextView tvNomePerfil = findViewById(R.id.tvNomePerfil);
                tvNomePerfil.setText(usuario.getNome());

                TextView tvEmailPerfil = findViewById(R.id.tvEmailPerfil);
                tvEmailPerfil.setText(usuario.getEmail());

                TextView tvTelefonePerfil = findViewById(R.id.tvTelefonePerfil);
                tvTelefonePerfil.setText(usuario.getTelefone());

            }
        });

    }
}