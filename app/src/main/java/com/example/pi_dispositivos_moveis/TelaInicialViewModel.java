package com.example.pi_dispositivos_moveis;

import android.app.Application;
import android.graphics.Bitmap;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class   TelaInicialViewModel extends AndroidViewModel {
    MutableLiveData<List<Anuncio>> anuncios;

    public TelaInicialViewModel(@NonNull Application application) {
        super(application);

    }

    public MutableLiveData<List<Anuncio>> getAnuncios() {
           if (anuncios == null){
                anuncios = new  MutableLiveData<List<Anuncio>>();
            }
        return anuncios;
    }

    public void loadAnuncios(final String estilo) {
        final String login = Config.getLogin(getApplication());
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                List<Anuncio>  anunciosList = new ArrayList<>();
                HttpRequest httpRequest = new HttpRequest(Config.SERVER_URL_BASE + "php_action/anuncios_mobile.php","GET","UTF-8");
                httpRequest.addParam("login", login);
                if(!estilo.equals("0")){
                    httpRequest.addParam("estilo", estilo);
                }
                try {
                    InputStream is = httpRequest.execute();
                    String result = Util.inputStream2String(is,"UTF-8");
                    httpRequest.finish();

                    Log.d("HTTP_REQUEST_RESULT",result);


                    JSONObject jsonObject = new JSONObject(result);
                    int success = jsonObject.getInt("sucesso");
                    if (success == 1){
                        JSONArray jsonArray = jsonObject.getJSONArray("anuncios");
                        for(int i=0;i < jsonArray.length();i++){
                            JSONObject jAnuncio = jsonArray.getJSONObject(i);

                            String Idanuncio = jAnuncio.getString("idanuncio");
                            String nome = jAnuncio.getString("nome");
                            String estilo = jAnuncio.getString("estilo");
                            String cache = jAnuncio.getString("cache_minimo");


                            String imgBase64 = jAnuncio.getString("foto");
                            String pureBase64Encoded = imgBase64.substring(imgBase64.indexOf(",")+1);
                            Bitmap photo = Util.base642Bitmap(pureBase64Encoded);

                            Anuncio anuncio = new Anuncio(Idanuncio,photo,nome,estilo,cache);
                            anunciosList.add(anuncio);
                        }
                        anuncios.postValue(anunciosList);
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
