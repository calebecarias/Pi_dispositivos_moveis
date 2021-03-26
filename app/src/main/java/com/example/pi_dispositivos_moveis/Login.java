package com.example.pi_dispositivos_moveis;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pi_dispositivos_moveis.activity.TelaInicialActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText etLogin = findViewById(R.id.etLogin);
                final String login = etLogin.getText().toString();

                EditText etPassword = findViewById(R.id.etSenha);
                final String password = etPassword.getText().toString();



                ExecutorService executorService = Executors.newSingleThreadExecutor();
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        HttpRequest httpRequest = new HttpRequest(Config.SERVER_URL_BASE + "login.php", "POST", "UTF-8");
                        httpRequest.addParam("login", login);
                        httpRequest.addParam("senha", password);

                        try {
                            InputStream is = httpRequest.execute();
                            String result = Util.inputStream2String(is, "UTF-8");
                            httpRequest.finish();

                            JSONObject jsonObject = new JSONObject(result);
                            final int success = jsonObject.getInt("success");
                            if(success == 1) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Config.setLogin(Login.this, login);
                                        Config.setPassword(Login.this, password);
                                        Toast.makeText(Login.this, "Login realizado com sucesso", Toast.LENGTH_LONG).show();
                                        Intent i = new Intent(Login.this, TelaInicialActivity.class);
                                        startActivity(i);
                                    }
                                });
                            }
                            else {
                                final String error = jsonObject.getString("error");
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(Login.this, error, Toast.LENGTH_LONG).show();
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

        Button btnRegisterNewUser = findViewById(R.id.btnCadastro);
        btnRegisterNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this, Cadastro.class);
                startActivity(i);
            }
        });
    }
}