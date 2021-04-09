package com.example.pi_dispositivos_moveis;

import android.graphics.Bitmap;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProfileViewModel extends ViewModel {
    String login;
    Usuario usuario;

    public ProfileViewModel(String login) {
        this.login = login;
    }

    public Usuario getUsuario(){
        loadPerfil();
        return usuario;
    }


    public void loadPerfil() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                HttpRequest httpRequest = new HttpRequest(Config.SERVER_URL_BASE + "perfil.php", "GET", "UTF-8");
                httpRequest.addParam("login", login);

                try {
                    InputStream is = httpRequest.execute();
                    String result = Util.inputStream2String(is, "UTF-8");
                    httpRequest.finish();

                    Log.d("HTTP_REQUEST_RESULT", result);


                    JSONObject jsonObject = new JSONObject(result);
                    int success = jsonObject.getInt("success");
                    if (success == 1) {
                        JSONArray jsonArray = jsonObject.getJSONArray("perfil");
                        JSONObject jAnuncio = jsonArray.getJSONObject(0);

                        String nome = jAnuncio.getString("nome");
                        String email = jAnuncio.getString("email");
                        String telefone = jAnuncio.getString("telefone");


                        String imgBase64 = jAnuncio.getString("foto");
                        String pureBase64Encoded = imgBase64.substring(imgBase64.indexOf(",") + 1);
                        Bitmap photo = Util.base642Bitmap(pureBase64Encoded);

                        Usuario u = new Usuario(nome, telefone, email, photo);
                        usuario = u;

                                           }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    static public class ProfileViewModelFactory implements ViewModelProvider.Factory {

        String login;

        public  ProfileViewModelFactory (String login) {
            this.login = login;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return null;
        }
    }
}
