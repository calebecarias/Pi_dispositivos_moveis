package com.example.pi_dispositivos_moveis;

import android.graphics.Bitmap;

public class Usuario {
    String nome;
    String telefone;
    String email;
    Bitmap foto;

    public Usuario(String nome, String telefone, String email, Bitmap foto) {
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.foto = foto;
    }

    public String getNome() {
        return nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEmail() {
        return email;
    }

    public Bitmap getFoto() {
        return foto;
    }
}
