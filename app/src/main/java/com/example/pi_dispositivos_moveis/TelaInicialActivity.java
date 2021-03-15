package com.example.pi_dispositivos_moveis;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class TelaInicialActivity extends AppCompatActivity {

    static  int NEW_ITEM_REQUEST = 1;



    Myadapter myadapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_inicial);


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