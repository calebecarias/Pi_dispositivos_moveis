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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainAnuncioViewModel extends ViewModel {

    String anuncioid;
    MutableLiveData<Anuncio> anuncio;

    public MainAnuncioViewModel(String anuncioid) {
        this.anuncioid = anuncioid;
    }

    public LiveData<Anuncio> getAnuncio(){
        if (this.anuncio == null){
            anuncio = new MutableLiveData<>();
            loadAnuncio();


        }
        return anuncio;
    }


    void loadAnuncio(){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                HttpRequest httpRequest = new HttpRequest(Config.SERVER_URL_BASE + "php_action/detalhesAnuncio_mobile.php", "GET", "UTF-8");
                httpRequest.addParam("idanuncio", anuncioid);

                try {
                    InputStream is = httpRequest.execute();
                    String result = Util.inputStream2String(is,"UTF-8");
                    httpRequest.finish();

                    Log.d("HTTP_REQUEST_RESULT",result);


                    JSONObject jsonObject = new JSONObject(result);
                    int success = jsonObject.getInt("sucesso");
                    if (success == 1){
                        JSONArray jsonArray = jsonObject.getJSONArray("anuncio");
                        JSONObject jAnuncio = jsonArray.getJSONObject(0);

                        String nome = jAnuncio.getString("nome");
                        String descricao = jAnuncio.getString("descricao");
                        String estilo = jAnuncio.getString("estilo");
                        String spotify = jAnuncio.getString("spotify");
                        String cache = jAnuncio.getString("cache_minimo");
                        String email = jAnuncio.getString("email");

                        String imgBase64 = jAnuncio.getString("foto");
                        String pureBase64Encoded = imgBase64.substring(imgBase64.indexOf(",")+1);
                        Bitmap photo = Util.base642Bitmap(pureBase64Encoded);

                        Anuncio a = new Anuncio(photo,nome,estilo,spotify,descricao,cache,email);

                        anuncio.postValue(a);

                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    static public class MainAnuncioViewModelFactory implements ViewModelProvider.Factory{

        String anuncioid;

        public MainAnuncioViewModelFactory(String anuncioid) {
            this.anuncioid = anuncioid;
        }


        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new MainAnuncioViewModel(anuncioid);
        }
    }
}
