package com.example.testandologoali.db;

import com.example.patinho.logoali.R;

public class Estabelecimentos {

    @com.google.gson.annotations.SerializedName("id")
    private String mId;

    @com.google.gson.annotations.SerializedName("idadministrador")
    private String mIdAdministrador;

    @com.google.gson.annotations.SerializedName("nome")
    private String mNomeDoEstabelecimento;

    @com.google.gson.annotations.SerializedName("rua")
    private String mRuaDoEstabelecimento;

    @com.google.gson.annotations.SerializedName("numero")
    private String mNumeroDoEstabelecimento;

    @com.google.gson.annotations.SerializedName("bairro")
    private String mBairroDoEstabelecimento;

    @com.google.gson.annotations.SerializedName("cidade")
    private String mCidadeDoEstabelecimento;

    @com.google.gson.annotations.SerializedName("telefone")
    private String mTelefoneDoEstabelecimento;

    @com.google.gson.annotations.SerializedName("servicos")
    private String mServicos;

    @com.google.gson.annotations.SerializedName("horas")
    private String mHorarioAtendimento;

    @com.google.gson.annotations.SerializedName("dias")
    private String mDiasAtendimento;

//    private float mNotaEstabelecimento;
//    private int mImagemEstabelecimento;
//    private int mImagemEstabelecimentoThumb;

    public Estabelecimentos(String id, String nome, String rua, String numero
            , String bairro, String cidade, String telefone, String servicos
            , String horas, String idadministrador, String dias) {

        mId = id;
        mIdAdministrador = idadministrador;
        mNomeDoEstabelecimento = nome;
        mRuaDoEstabelecimento = rua;
        mNumeroDoEstabelecimento = numero;
        mBairroDoEstabelecimento = bairro;
        mCidadeDoEstabelecimento = cidade;
        mTelefoneDoEstabelecimento = telefone;
//        mImagemEstabelecimento = vImagemEstabelecimento;
//        mNotaEstabelecimento = vNotaEstabelecimento;
//        mImagemEstabelecimentoThumb = vImagemEstabelecimentoThumb;
        mServicos = servicos;
        mHorarioAtendimento = horas;
        mDiasAtendimento = dias;
    }

    public Estabelecimentos() {}


    public String getmId() {
        return mId;
    }

    public String getmNomeDoEstabelecimento() {
        return mNomeDoEstabelecimento;
    }

    public String getmRuaDoEstabelecimento() {
        return mRuaDoEstabelecimento;
    }

    public String getmTelefoneDoEstabelecimento() {
        return mTelefoneDoEstabelecimento;
    }

    public String getmIdAdministrador() {
        return mIdAdministrador;
    }

    public String getmNumeroDoEstabelecimento() {
        return mNumeroDoEstabelecimento;
    }

    public String getmBairroDoEstabelecimento() {
        return mBairroDoEstabelecimento;
    }

    public String getmCidadeDoEstabelecimento() {
        return mCidadeDoEstabelecimento;
    }

    public float getmNotaEstabelecimento() {
        return 5f;
    }

    public int getmImagemEstabelecimento() {
        if ((mId != null && mId.charAt(0) > '7') || Math.random() > 0.5d)
            return R.drawable.barbearia1;
        else return R.drawable.barbearia2;
    }

    public int getmImagemEstabelecimentoThumb() {
        return 0;
    }

    public String getmServicos() {
        return mServicos;
    }

    public String getmHorarioAtendimento() {
        return mHorarioAtendimento;
    }

    public void setmNomeDoEstabelecimento(String mNomeDoEstabelecimento) {
        this.mNomeDoEstabelecimento = mNomeDoEstabelecimento;
    }

    public void setmRuaDoEstabelecimento(String mRuaDoEstabelecimento) {
        this.mRuaDoEstabelecimento = mRuaDoEstabelecimento;
    }

    public void setmNumeroDoEstabelecimento(String mNumeroDoEstabelecimento) {
        this.mNumeroDoEstabelecimento = mNumeroDoEstabelecimento;
    }

    public void setmBairroDoEstabelecimento(String mBairroDoEstabelecimento) {
        this.mBairroDoEstabelecimento = mBairroDoEstabelecimento;
    }

    public void setmCidadeDoEstabelecimento(String mCidadeDoEstabelecimento) {
        this.mCidadeDoEstabelecimento = mCidadeDoEstabelecimento;
    }

    public void setmTelefoneDoEstabelecimento(String mTelefoneDoEstabelecimento) {
        this.mTelefoneDoEstabelecimento = mTelefoneDoEstabelecimento;
    }

    public void setmServicos(String mServicos) {
        this.mServicos = mServicos;
    }

    public void setmHorarioAtendimento(String mHorarioAtendimento) {
        this.mHorarioAtendimento = mHorarioAtendimento;
    }

    public void setmNotaEstabelecimento(float mNotaEstabelecimento) {
//        this.mNotaEstabelecimento = mNotaEstabelecimento;
    }

    public void setmImagemEstabelecimento(int mImagemEstabelecimento) {
//        this.mImagemEstabelecimento = mImagemEstabelecimento;
    }

    public void setmImagemEstabelecimentoThumb(int mImagemEstabelecimentoThumb) {
//        this.mImagemEstabelecimentoThumb = mImagemEstabelecimentoThumb;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public void setmIdAdministrador(String mIdAdministrador) {
        this.mIdAdministrador = mIdAdministrador;
    }

    public String getmDiasAtendimento() {
        return mDiasAtendimento;
    }

    public void setmDiasAtendimento(String mDiasAtendimento) {
        this.mDiasAtendimento = mDiasAtendimento;
    }
}
