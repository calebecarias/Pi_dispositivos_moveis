package com.example.pi_dispositivos_moveis.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.pi_dispositivos_moveis.AnuncieViewModel;
import com.example.pi_dispositivos_moveis.Config;
import com.example.pi_dispositivos_moveis.HttpRequest;
import com.example.pi_dispositivos_moveis.R;
import com.example.pi_dispositivos_moveis.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Anuncie extends AppCompatActivity {
    static int PHOTO_PICKER_REQUEST = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anuncie);
        ImageButton imgbtn = findViewById(R.id.imgbtnFoto);
        imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, PHOTO_PICKER_REQUEST);
                            }
        });

        final AnuncieViewModel anuncieViewModel = new ViewModelProvider(this).get(AnuncieViewModel.class);
        String currentPhotoPath = anuncieViewModel.getCurrentPhotoPath();
        if(!currentPhotoPath.isEmpty()){
            ImageView imvPhoto = findViewById(R.id.imvPhotoAnuncie);
            Bitmap bitmap = Util.getBitmap(currentPhotoPath, 150, 150);
            imvPhoto.setImageBitmap(bitmap);
        }
        Button btnanuncie = findViewById(R.id.btnanuncie);
        btnanuncie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                v.setEnabled(false);

                Spinner spEstilos = findViewById(R.id.spEstilos);
                final String estilo = String.valueOf(spEstilos.getSelectedItemPosition());
                if(estilo.equals("0")){
                    Toast.makeText(Anuncie.this,"O estilo musical não foi selecionado",Toast.LENGTH_LONG).show();
                    v.setEnabled(true);
                    return;
                }

                EditText etDescricao = findViewById(R.id.etdescricaoanuncie);
                final String descricao = etDescricao.getText().toString();
                if(descricao.isEmpty()){
                    Toast.makeText(Anuncie.this,"A descrição não foi preenchida",Toast.LENGTH_LONG).show();
                    v.setEnabled(true);
                    return;
                }

                EditText etCache = findViewById(R.id.etValor);
                final String cache = etCache.getText().toString();
                if(cache.isEmpty()){
                    Toast.makeText(Anuncie.this,"O cache minimo não foi preenchido",Toast.LENGTH_LONG).show();
                    v.setEnabled(true);
                    return;
                }

                EditText etSpotify = findViewById(R.id.etSpotifyAnuncie);
                final String spotify = etSpotify.getText().toString();
                if(spotify.isEmpty()){
                    Toast.makeText(Anuncie.this,"O campo do Spotify não foi preenchido",Toast.LENGTH_LONG).show();
                    v.setEnabled(true);
                    return;
                }
                EditText etInstrumento = findViewById(R.id.etInstrumentosAnuncie);
                final String instrumento = etInstrumento.getText().toString();
                if(instrumento.isEmpty()){
                    Toast.makeText(Anuncie.this,"O campo do Instrumento não foi preenchido",Toast.LENGTH_LONG).show();
                    v.setEnabled(true);
                    return;
                }
                final String currentPhotoPath = anuncieViewModel.getCurrentPhotoPath();
                if (currentPhotoPath.isEmpty()){
                    Toast.makeText(Anuncie.this,"Selecione uma foto",Toast.LENGTH_LONG).show();
                    v.setEnabled(true);
                    return;
                }
                ExecutorService executorService = Executors.newSingleThreadExecutor();
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        HttpRequest httpRequest = new HttpRequest(Config.SERVER_URL_BASE + "php_action/createAnuncio_mobile.php","POST","UTF-8");

                        httpRequest.addParam("estilo",estilo);
                        httpRequest.addParam("cache",cache);
                        httpRequest.addParam("instrumento",instrumento);
                        httpRequest.addParam("spotify",spotify);
                        httpRequest.addParam("descricao",descricao);
                        httpRequest.addFile("foto",new File(currentPhotoPath));

                        try {
                            InputStream is = httpRequest.execute();
                            String result = Util.inputStream2String(is,"UTF-8");
                            httpRequest.finish();

                            Log.d("HTTP_REQUEST_RESULT",result);
                            JSONObject jsonObject = new JSONObject(result);
                            final int success = jsonObject.getInt("sucesso");
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (success == 1){
                                        Toast.makeText(Anuncie.this,"Anuncio adicionado com sucesso",Toast.LENGTH_LONG).show();
                                        Intent i = new Intent(Anuncie.this,TelaInicialActivity.class);
                                        startActivity(i);

                                    }
                                    else{
                                        Toast.makeText(Anuncie.this,"Anuncio não adicionado",Toast.LENGTH_LONG).show();

                                    }
                                }
                            });

                        } catch (IOException | JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==PHOTO_PICKER_REQUEST){
            if (resultCode== Activity.RESULT_OK){
                Uri selectedPhotoUri = data.getData();
                try {
                    Bitmap bitmap = Util.getBitmap(Anuncie.this,selectedPhotoUri,4);
                    File f = createImageFile();
                    Util.saveBitmap(f.getAbsolutePath(),bitmap);
                    AnuncieViewModel anuncieViewModel = new ViewModelProvider(this).get(AnuncieViewModel.class);
                    anuncieViewModel.setCurrentPhotoPath(f.getAbsolutePath());
                    ImageView imvPhoto = findViewById(R.id.imvPhotoAnuncie);
                    imvPhoto.setImageBitmap(bitmap);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }
    private File createImageFile() throws IOException {  // função para criar a imagem
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());  // transformar a data em uma string
        String imageFileName = "JPEG" + timeStamp;  // nome do arquivo
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File f = File.createTempFile(imageFileName, ".jpg", storageDir);
        return f;
    }
}