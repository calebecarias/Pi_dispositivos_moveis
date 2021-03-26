package com.example.pi_dispositivos_moveis.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.pi_dispositivos_moveis.Anuncio;
import com.example.pi_dispositivos_moveis.Myadapter;
import com.example.pi_dispositivos_moveis.R;
import com.example.pi_dispositivos_moveis.TelaInicialViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class TelaInicialActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    static  int NEW_ITEM_REQUEST = 1;



    Myadapter myadapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_inicial);

        bottomNavigationView = findViewById(R.id.btNavInicial);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.perfilViewOp:
                        Intent i = new Intent(TelaInicialActivity.this, ProfileActivity.class);
                        startActivity(i);
                    case R.id.BuscarViewOp:
                        break;
                }
                return true;
            }
        });


        TelaInicialViewModel vm = new ViewModelProvider(this).get(TelaInicialViewModel.class);

        List<Anuncio> anuncios = vm.getAnuncios();

        myadapter = new Myadapter(this, anuncios);

        RecyclerView rvAnuncios = findViewById(R.id.rvAnuncios);
        rvAnuncios.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvAnuncios.setLayoutManager(layoutManager);

        rvAnuncios.setAdapter(myadapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvAnuncios.getContext(),DividerItemDecoration.VERTICAL);
        rvAnuncios.addItemDecoration(dividerItemDecoration);
    }



    protected void onStart() {
        super.onStart();
    }


    protected void onRestart() {
        super.onRestart();
    }


    protected void onResume() {
        super.onResume();
    }


    protected void onPause() {
        super.onPause();
    }

    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}