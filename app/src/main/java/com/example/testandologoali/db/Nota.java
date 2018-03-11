package com.example.testandologoali.db;

/**
 * Created by dener on 06/03/2018.
 */

public class Nota {
    @Override
    public String toString() {
        return "Nota{" +
                "id='" + id + '\'' +
                ", idEstabelecimento='" + idEstabelecimento + '\'' +
                ", idUsuario='" + idUsuario + '\'' +
                ", nota='" + nota + '\'' +
                '}';
    }

    @com.google.gson.annotations.SerializedName("id")
    private String id;

    @com.google.gson.annotations.SerializedName("idestabelecimento")
    private String idEstabelecimento;

    @com.google.gson.annotations.SerializedName("idcliente")
    private String idUsuario;

    @com.google.gson.annotations.SerializedName("nota")
    private String nota;

    public Nota(String id, String idcliente, String idestabelecimento, String nota) {
        this.id = id;
        this.idUsuario = idcliente;
        this.idEstabelecimento = idestabelecimento;
        this.nota = nota;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public String getIdEstabelecimento() {
        return idEstabelecimento;
    }

    public float getNota() {
        return Float.valueOf(nota);
    }

    public void setNota(float nota) {
        this.nota = String.valueOf(nota);
    }
}
