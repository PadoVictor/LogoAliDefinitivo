package com.example.testandologoali.db;

public class Fidelidade {

    @com.google.gson.annotations.SerializedName("id")
    private String id;

    @com.google.gson.annotations.SerializedName("idestabelecimento")
    private String idEstabelecimento;

    @com.google.gson.annotations.SerializedName("idusuario")
    private String idUsuario;

    @com.google.gson.annotations.SerializedName("contagem")
    private String contagem;

    public Fidelidade(String id, String idusuario, String idestabelecimento, String contagem) {
        this.id = id;
        this.idUsuario = idusuario;
        this.idEstabelecimento = idestabelecimento;
        this.contagem = contagem;
    }

    public String getmIdUsuario() {
        return idUsuario;
    }

    public String getmIdEstabelecimento() {
        return idEstabelecimento;
    }

    public int getmContagem() {
        return Integer.valueOf(contagem);
    }

    public int addContagem() {
        int contagemInt = Integer.valueOf(contagem);
        contagemInt++;
        contagem = String.valueOf(contagemInt);
        return contagemInt;
    }
}
