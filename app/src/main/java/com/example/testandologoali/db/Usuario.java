package com.example.testandologoali.db;

public class Usuario {

    public static final String ADMIN = "ADMIN";
    public static final String USER = "USER";

    @com.google.gson.annotations.SerializedName("id")
    private String mIdUsuario;

    @com.google.gson.annotations.SerializedName("nome")
    private String mNome;

    @com.google.gson.annotations.SerializedName("email")
    private String mEmail;

    @com.google.gson.annotations.SerializedName("acesso")
    private String mAcesso;

    public Usuario(String id, String nome, String email, String acesso) {
        setIdUsuario(id);
        setNome(nome);
        setEmail(email);
        setAcesso(acesso);
    }

    public Usuario() {}

    public String getIdUsuario() {
        return mIdUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.mIdUsuario = idUsuario;
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
        if (ADMIN.equals(acesso) || USER.equals(acesso)) {
            this.mAcesso = acesso;
        } else {
            throw new IllegalArgumentException("Acesso deve ser " + ADMIN + " ou " + USER + "!");
        }
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Usuario && ((Usuario) o).getIdUsuario().equals(mIdUsuario);
    }
}
