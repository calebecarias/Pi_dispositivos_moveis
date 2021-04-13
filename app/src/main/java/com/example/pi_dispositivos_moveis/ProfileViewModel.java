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
    MutableLiveData<Usuario> usuario;

    public ProfileViewModel(String login) {
        this.login = login;
    }

    public LiveData<Usuario> getUsuario(){
        if(usuario == null){
            usuario = new MutableLiveData<>();
            loadPerfil();
        }
        return usuario;
    }


    public void loadPerfil() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                HttpRequest httpRequest = new HttpRequest(Config.SERVER_URL_BASE + "/php_action/usuario_mobile.php", "GET", "UTF-8");
                httpRequest.addParam("login", login);

                try {
                    InputStream is = httpRequest.execute();
                    String result = Util.inputStream2String(is, "UTF-8");
                    httpRequest.finish();

                    Log.d("HTTP_REQUEST_RESULT", result);


                    JSONObject jsonObject = new JSONObject(result);
                    int success = jsonObject.getInt("sucesso");
                    if (success == 1) {
                        JSONArray jsonArray = jsonObject.getJSONArray("usuario");
                        JSONObject jUsuario = jsonArray.getJSONObject(0);

                        String nome = jUsuario.getString("nome");
                        String email = jUsuario.getString("email");
                        String telefone = jUsuario.getString("telefone");
                        String data_nascimento = jUsuario.getString("data_nascimento");



                        Usuario u = new Usuario(nome, telefone, email, data_nascimento);
                        usuario.postValue(u);

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
            return (T) new ProfileViewModel(login);
        }
    }
}
