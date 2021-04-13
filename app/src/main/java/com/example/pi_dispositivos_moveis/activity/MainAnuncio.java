package com.example.pi_dispositivos_moveis.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pi_dispositivos_moveis.Anuncio;
import com.example.pi_dispositivos_moveis.MainAnuncioViewModel;
import com.example.pi_dispositivos_moveis.R;

public class MainAnuncio extends AppCompatActivity {
    String email = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_anuncio);

        Intent i = getIntent();
        final String anuncioid = i.getStringExtra("idanuncio");

        MainAnuncioViewModel mainAnuncioViewModel = new ViewModelProvider(this,new MainAnuncioViewModel.MainAnuncioViewModelFactory(anuncioid)).get(MainAnuncioViewModel.class);

        Button btnEmailAnuncio =findViewById(R.id.btnEmailAnuncio);
        btnEmailAnuncio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] emails = new String[]{email};
                String assunto = "Contratação Music Station";

                String texto = "Olá, gostaria de contratar seus serviços";
                Intent i = new Intent(Intent.ACTION_SENDTO);
                i.setData(Uri.parse("mailto:"));
                i.putExtra(Intent.EXTRA_EMAIL, emails);
                i.putExtra(Intent.EXTRA_SUBJECT, assunto);
                i.putExtra(Intent.EXTRA_TEXT, texto);
                try {
                    startActivity(Intent.createChooser(i, "Escolha o APP"));
                }
                catch (ActivityNotFoundException e) {
                    Toast.makeText(MainAnuncio.this, "Não há nenhum app que posso realizar essa operação", Toast.LENGTH_LONG).show();
                }

                }
        });


        LiveData<Anuncio> anuncio = mainAnuncioViewModel.getAnuncio();
        anuncio.observe(this, new Observer<Anuncio>() {
            @Override
            public void onChanged(Anuncio anuncio) {
                email = anuncio.getEmail();
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