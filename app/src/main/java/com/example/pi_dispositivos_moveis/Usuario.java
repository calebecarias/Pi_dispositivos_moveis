package com.example.pi_dispositivos_moveis;

import android.graphics.Bitmap;

public class Usuario {
    String nome;
    String telefone;
    String email;
    String data_nascimento;

    public Usuario(String nome, String telefone, String email, String data_nascimento) {
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.data_nascimento = data_nascimento;
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

    public String getData(){return data_nascimento;}
}
