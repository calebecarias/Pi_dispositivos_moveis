package com.example.pi_dispositivos_moveis;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Cadastro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        Button btnRegister =  findViewById(R.id.btnCadastrar);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText etNewLogin =  findViewById(R.id.etLoginCadastro);
                final String newLogin = etNewLogin.getText().toString();
                if(newLogin.isEmpty()) {
                    Toast.makeText(Cadastro.this, "Campo de login não preenchido", Toast.LENGTH_LONG).show();
                    return;
                }

                EditText etNewPassword =  findViewById(R.id.etSenhaCadastro);
                final String newPassword = etNewPassword.getText().toString();
                if(newPassword.isEmpty()) {
                    Toast.makeText(Cadastro.this, "Campo de senha não preenchido", Toast.LENGTH_LONG).show();
                    return;
                }

                EditText etNewPasswordCheck =  findViewById(R.id.etConfirmarSenha);
                String newPasswordCheck = etNewPasswordCheck.getText().toString();
                if(newPasswordCheck.isEmpty()) {
                    Toast.makeText(Cadastro.this, "Campo de checagem de senha não preenchido", Toast.LENGTH_LONG).show();
                    return;
                }

                if(!newPassword.equals(newPasswordCheck)) {
                    Toast.makeText(Cadastro.this, "Senha não confere", Toast.LENGTH_LONG).show();
                    return;
                }
                final EditText etNewName =  findViewById(R.id.etNomeCadastro);
                final String NewName = etNewName.getText().toString();
                if(NewName.isEmpty()) {
                    Toast.makeText(Cadastro.this, "Campo de nome não preenchido", Toast.LENGTH_LONG).show();
                    return;
                }

                final EditText etNewDate =  findViewById(R.id.etDataNascimentoCadastro);
                final String newDate = etNewDate.getText().toString();
                if(newDate.isEmpty()) {
                    Toast.makeText(Cadastro.this, "Data de nascimento não preenchida", Toast.LENGTH_LONG).show();
                    return;
                }

                EditText etNewEmail =  findViewById(R.id.etEmailCadastro);
                final String newEmail = etNewEmail.getText().toString();
                if(newEmail.isEmpty()) {
                    Toast.makeText(Cadastro.this, "Campo de email não preenchido", Toast.LENGTH_LONG).show();
                    return;
                }
                EditText etNewTelefone =  findViewById(R.id.etTelefoneCadastro);
                final String newTelefone = etNewTelefone.getText().toString();
                if(newTelefone.isEmpty()) {
                    Toast.makeText(Cadastro.this, "Campo telefone não preenchido", Toast.LENGTH_LONG).show();
                    return;
                }



                ExecutorService executorService = Executors.newSingleThreadExecutor();
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        HttpRequest httpRequest = new HttpRequest(Config.SERVER_URL_BASE + "register.php", "POST", "UTF-8");
                        httpRequest.addParam("newLogin", newLogin);
                        httpRequest.addParam("newPassword", newPassword);
                        httpRequest.addParam("newName", NewName);
                        httpRequest.addParam("newDate", newDate);
                        httpRequest.addParam("newEmail", newEmail);
                        httpRequest.addParam("newTelefone", newTelefone);

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
                                        Toast.makeText(Cadastro.this, "Novo usuario registrado com sucesso", Toast.LENGTH_LONG).show();
                                        finish();
                                    }
                                });
                            }
                            else {
                                final String error = jsonObject.getString("error");
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(Cadastro.this, error, Toast.LENGTH_LONG).show();
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