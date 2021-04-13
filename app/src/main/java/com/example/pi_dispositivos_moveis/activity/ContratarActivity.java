package com.example.pi_dispositivos_moveis.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pi_dispositivos_moveis.Config;
import com.example.pi_dispositivos_moveis.HttpRequest;
import com.example.pi_dispositivos_moveis.R;
import com.example.pi_dispositivos_moveis.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ContratarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contratar);

        Intent i = getIntent();
        final String anuncioid = i.getStringExtra("anuncioid");


        Button btnRegister =  findViewById(R.id.btnCriarContrato);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText etCache = findViewById(R.id.etCacheContrato);
                final String newCache = etCache.getText().toString();
                if (newCache.isEmpty()) {
                    Toast.makeText(ContratarActivity.this, "Campo de Cache não preenchido", Toast.LENGTH_LONG).show();
                    return;
                }

                final EditText etNewDate = findViewById(R.id.etDataContrato);
                final String newDate = etNewDate.getText().toString();
                if (newDate.isEmpty()) {
                    Toast.makeText(ContratarActivity.this, "Data do evento não preenchida", Toast.LENGTH_LONG).show();
                    return;
                }

                EditText etLocal = findViewById(R.id.etLocalContrato);
                final String newLocal = etLocal.getText().toString();
                if (newLocal.isEmpty()) {
                    Toast.makeText(ContratarActivity.this, "Campo de email não preenchido", Toast.LENGTH_LONG).show();
                    return;
                }



                ExecutorService executorService = Executors.newSingleThreadExecutor();
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        HttpRequest httpRequest = new HttpRequest(Config.SERVER_URL_BASE + "php_action/Contratar_mobile.php", "POST", "UTF-8");
                        httpRequest.addParam("idanuncio",anuncioid);
                        httpRequest.addParam("login",Config.getLogin(ContratarActivity.this));
                        httpRequest.addParam("cache", newCache);
                        httpRequest.addParam("data", newDate);
                        httpRequest.addParam("local", newLocal);


                        try {
                            InputStream is = httpRequest.execute();
                            String result = Util.inputStream2String(is, "UTF-8");
                            httpRequest.finish();

                            JSONObject jsonObject = new JSONObject(result);
                            final int success = jsonObject.getInt("sucesso");
                            if (success == 1) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(ContratarActivity.this, "Contratado com sucesso", Toast.LENGTH_LONG).show();
                                        finish();
                                    }
                                });
                            } else {
                                final String error = jsonObject.getString("error");
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(ContratarActivity.this, error, Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        } catch (IOException | JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
            });

    }
}