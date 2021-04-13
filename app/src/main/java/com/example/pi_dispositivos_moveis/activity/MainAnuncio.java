package com.example.pi_dispositivos_moveis.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pi_dispositivos_moveis.Anuncio;
import com.example.pi_dispositivos_moveis.MainAnuncioViewModel;
import com.example.pi_dispositivos_moveis.R;

public class MainAnuncio extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_anuncio);

        Intent i = getIntent();
        final String anuncioid = i.getStringExtra("anuncioid");

        MainAnuncioViewModel mainAnuncioViewModel = new ViewModelProvider(this,new MainAnuncioViewModel.MainAnuncioViewModelFactory(anuncioid)).get(MainAnuncioViewModel.class);

        Button btnContratar =findViewById(R.id.btnCadastrar);
        btnContratar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainAnuncio.this,ContratarActivity.class);
                i.putExtra("idanuncio",anuncioid);

                }
        });


        LiveData<Anuncio> anuncio = mainAnuncioViewModel.getAnuncio();
        anuncio.observe(this, new Observer<Anuncio>() {
            @Override
            public void onChanged(Anuncio anuncio) {
                ImageView imvFotoMainAnuncio = findViewById(R.id.imgvFotoMainAnuncio);
                imvFotoMainAnuncio.setImageBitmap(anuncio.getPhoto());

                TextView tvNomeMainAnuncio = findViewById(R.id.tvNomeMainAnuncio);
                tvNomeMainAnuncio.setText(anuncio.getNome());

                TextView tvEstilosMainAnuncio = findViewById(R.id.tvEstilosMainAnuncio);
                tvEstilosMainAnuncio.setText(anuncio.getEstilo());

                TextView tvCacheMinMainAnuncio = findViewById(R.id.tvCacheMinMainAnuncio);
                tvCacheMinMainAnuncio.setText(anuncio.getCache());

                TextView tvDescricaoMainAnuncio = findViewById(R.id.tvDescricaoMainAnuncio);
                tvDescricaoMainAnuncio.setText(anuncio.getDescricao());

                TextView tvSpotifyMainAnuncio = findViewById(R.id.tvSpotifyMainAnuncio);
                tvSpotifyMainAnuncio.setText(anuncio.getSpotify());
            }
        });

    }

}