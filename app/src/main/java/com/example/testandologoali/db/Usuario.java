package com.example.testandologoali.db;

import android.util.Log;

public class Usuario {

    @com.google.gson.annotations.SerializedName("id")
    private String id;

    @com.google.gson.annotations.SerializedName("nome")
    private String nome;

    @com.google.gson.annotations.SerializedName("email")
    private String email;

    @com.google.gson.annotations.SerializedName("acesso")
    private String acesso;

    public Usuario(String id, String nome, String email, String acesso) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.acesso = acesso;
    }

    public Usuario() {
        Log.d(Usuario.class.getName(),"Usuario criado");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAcesso() {
        return acesso;
    }

    public void setAcesso(String acesso) {
        this.acesso = acesso;
    }



    @Override
    public boolean equals(Object o) {
        return o instanceof Usuario && ((Usuario) o).getId().equals(id);
    }
}
