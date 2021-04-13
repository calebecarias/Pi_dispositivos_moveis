package com.example.pi_dispositivos_moveis.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import com.example.pi_dispositivos_moveis.Anuncio;
import com.example.pi_dispositivos_moveis.Config;
import com.example.pi_dispositivos_moveis.Login;
import com.example.pi_dispositivos_moveis.Myadapter;
import com.example.pi_dispositivos_moveis.R;
import com.example.pi_dispositivos_moveis.TelaInicialViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class TelaInicialActivity extends AppCompatActivity {


    static  int NEW_ITEM_REQUEST = 1;



    Myadapter myadapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FloatingActionButton fabAddItem = findViewById(R.id.floatingActionButton);
        fabAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TelaInicialActivity.this, Anuncie.class);
                startActivityForResult(i,NEW_ITEM_REQUEST);
            }
        });
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_inicial);
        Toolbar toolbar = findViewById(R.id.tbTelaInicial);
        setSupportActionBar(toolbar);
        final TelaInicialViewModel vm = new ViewModelProvider(this).get(TelaInicialViewModel.class);
        Spinner spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                vm.loadAnuncios(String.valueOf(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final RecyclerView rvAnuncios = findViewById(R.id.rvAnuncios);
        rvAnuncios.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvAnuncios.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvAnuncios.getContext(),DividerItemDecoration.VERTICAL);
        rvAnuncios.addItemDecoration(dividerItemDecoration);



        LiveData<List<Anuncio>> anuncios = vm.getAnuncios();
        anuncios.observe(this, new Observer<List<Anuncio>>() {
            @Override
            public void onChanged(List<Anuncio> anuncios) {
                Myadapter myAdapter = new Myadapter(TelaInicialActivity.this, anuncios);
                rvAnuncios.setAdapter(myAdapter);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.tb_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent i;
        switch (item.getItemId()) {

            case R.id.perfilTbOp:
                i = new Intent(TelaInicialActivity.this, ProfileActivity.class);
                startActivity(i);
                break;
            case R.id.logoutTbOp:
                Config.setLogin(TelaInicialActivity.this, "");
                Config.setPassword(TelaInicialActivity.this, "");
                i = new Intent(TelaInicialActivity.this, Login.class);
                startActivity(i);
                break;

            default:
                return super.onOptionsItemSelected(item);

        }
        return true;
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