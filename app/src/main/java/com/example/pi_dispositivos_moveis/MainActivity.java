package com.example.pi_dispositivos_moveis;
import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
