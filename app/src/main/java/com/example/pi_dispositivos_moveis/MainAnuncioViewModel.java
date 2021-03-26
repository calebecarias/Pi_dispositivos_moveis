package com.example.pi_dispositivos_moveis;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

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
