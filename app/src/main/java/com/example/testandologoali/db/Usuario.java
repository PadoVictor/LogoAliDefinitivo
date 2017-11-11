package com.example.testandologoali.db;

import android.util.Log;

public class Usuario {

    @com.google.gson.annotations.SerializedName("id")
    private String mId;

    @com.google.gson.annotations.SerializedName("nome")
    private String mNome;

    @com.google.gson.annotations.SerializedName("email")
    private String mEmail;

    @com.google.gson.annotations.SerializedName("acesso")
    private String mAcesso;

    public Usuario(String id, String nome, String email, String acesso) {
        setId(id);
        setNome(nome);
        setEmail(email);
        setAcesso(acesso);
    }

    public Usuario() {
        Log.d(Usuario.class.getName(),"Usuario criado");
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        this.mId = id;
    }

    public String getNome() {
        return mNome;
    }

    public void setNome(String nome) {
        this.mNome = nome;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        this.mEmail = email;
    }

    public String getAcesso() {
        return mAcesso;
    }

    public void setAcesso(String acesso) {
        this.mAcesso = acesso;
    }



    @Override
    public boolean equals(Object o) {
        return o instanceof Usuario && ((Usuario) o).getId().equals(mId);
    }
}
