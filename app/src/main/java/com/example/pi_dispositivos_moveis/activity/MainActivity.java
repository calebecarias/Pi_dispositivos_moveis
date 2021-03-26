package com.example.pi_dispositivos_moveis.activity;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.pi_dispositivos_moveis.Config;
import com.example.pi_dispositivos_moveis.Login;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(Config.getLogin(MainActivity.this).isEmpty()) {
            Intent i = new Intent(MainActivity.this, Login.class);
            startActivity(i);
            finish();
        }
        else {
            Intent i = new Intent(MainActivity.this, TelaInicialActivity.class);
            startActivity(i);
            finish();
        }
    }
}
