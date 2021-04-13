package com.example.pi_dispositivos_moveis;

import android.graphics.Bitmap;
import android.net.Uri;

public class Anuncio {
    String idanuncio;
    Bitmap photo;
    String nome;
    String estilo;
    String spotify;
    String descricao;
    String cache;
    String email;

    public Anuncio(String idanuncio, Bitmap photo, String nome, String estilo, String cache) {
        this.idanuncio = idanuncio;
        this.photo = photo;
        this.nome = nome;
        this.estilo = estilo;
        this.cache = cache;
    }

    public Anuncio( Bitmap photo, String nome, String estilo, String spotify, String descricao, String cache, String email) {
        this.photo = photo;
        this.nome = nome;
        this.estilo = estilo;
        this.spotify = spotify;
        this.descricao = descricao;
        this.cache = cache;
        this.email = email;
    }

    public String getIdanuncio() {
        return idanuncio;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public String getNome() {
        return nome;
    }

    public String getEstilo() {
        return estilo;
    }

    public String getSpotify() {
        return spotify;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getCache() {
        return cache;
    }

    public String getEmail() {
        return email;
    }


}
