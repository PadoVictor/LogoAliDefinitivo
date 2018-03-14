package com.example.testandologoali.db;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import com.example.patinho.logoali.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class Estabelecimentos {
    @Override
    public String toString() {
        return "Estabelecimentos{" +
                "mId='" + mId + '\'' +
                ", mIdAdministrador='" + mIdAdministrador + '\'' +
                ", mNomeDoEstabelecimento='" + mNomeDoEstabelecimento + '\'' +
                ", mRuaDoEstabelecimento='" + mRuaDoEstabelecimento + '\'' +
                ", mNumeroDoEstabelecimento='" + mNumeroDoEstabelecimento + '\'' +
                ", mBairroDoEstabelecimento='" + mBairroDoEstabelecimento + '\'' +
                ", mCidadeDoEstabelecimento='" + mCidadeDoEstabelecimento + '\'' +
                ", mTelefoneDoEstabelecimento='" + mTelefoneDoEstabelecimento + '\'' +
                ", mServicos='" + mServicos + '\'' +
                ", mHorarioAtendimento='" + mHorarioAtendimento + '\'' +
                ", mDiasAtendimento='" + mDiasAtendimento + '\'' +
                '}';
    }

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

    @com.google.gson.annotations.SerializedName("imagem")
    private String mImagemEstabelecimento;

//    private float mNotaEstabelecimento;
//    private int mImagemEstabelecimento;
//    private int mImagemEstabelecimentoThumb;

    public Estabelecimentos(String id, String nome, String rua, String numero
            , String bairro, String cidade, String telefone, String servicos
            , String horas, String idadministrador, String dias, String imagem) {

        mId = id;
        mIdAdministrador = idadministrador;
        mNomeDoEstabelecimento = nome;
        mRuaDoEstabelecimento = rua;
        mNumeroDoEstabelecimento = numero;
        mBairroDoEstabelecimento = bairro;
        mCidadeDoEstabelecimento = cidade;
        mTelefoneDoEstabelecimento = telefone;
        mImagemEstabelecimento = imagem;
//        mNotaEstabelecimento = vNotaEstabelecimento;
//        mImagemEstabelecimentoThumb = vImagemEstabelecimentoThumb;
        mServicos = servicos;
        mHorarioAtendimento = horas;
        mDiasAtendimento = dias;
    }

    public Estabelecimentos() {
    }


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

    public Bitmap getmImagemEstabelecimento(Context context) {
        if (mImagemEstabelecimento != null && !mImagemEstabelecimento.isEmpty()) {
            Uri uri = Uri.fromFile(new File(mImagemEstabelecimento));
            try {
                InputStream imageStream = context.getContentResolver().openInputStream(uri);
                return BitmapFactory.decodeStream(imageStream);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        if (mId == null) {
            return BitmapFactory.decodeResource(context.getResources(), R.drawable.default_image);
        } else {
            if (mId.charAt(0) > '7')
                return BitmapFactory.decodeResource(context.getResources(), R.drawable.barbearia1);
            else return BitmapFactory.decodeResource(context.getResources(), R.drawable.barbearia2);
        }
    }

    public Bitmap getmImagemEstabelecimentoThumb(Context context) {
        if (mImagemEstabelecimento != null && !mImagemEstabelecimento.isEmpty()) {
            Uri uri = Uri.fromFile(new File(mImagemEstabelecimento));
            try {
                InputStream imageStream = context.getContentResolver().openInputStream(uri);
                return BitmapFactory.decodeStream(imageStream);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        if (mId.charAt(0) > '7')
            return BitmapFactory.decodeResource(context.getResources(), R.drawable.barbearia1_thumb);
        else
            return BitmapFactory.decodeResource(context.getResources(), R.drawable.barbearia2_thumb);
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

    public void setmImagemEstabelecimento(String mImagemEstabelecimento) {
        this.mImagemEstabelecimento = mImagemEstabelecimento;
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
