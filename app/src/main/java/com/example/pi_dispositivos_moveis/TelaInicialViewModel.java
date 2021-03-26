package com.example.pi_dispositivos_moveis;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class   TelaInicialViewModel extends ViewModel {
    List<Anuncio> anuncios  = new ArrayList<>();

    public List<Anuncio> getAnuncios() {
        return anuncios;
    }
}
