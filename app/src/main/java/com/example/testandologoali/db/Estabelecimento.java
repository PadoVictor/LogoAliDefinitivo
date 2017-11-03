package com.example.testandologoali.db;

public class Estabelecimento {

    private String mId;
    private String mIdAdministrador;
    private String mNomeDoEstabelecimento;
    private String mRuaDoEstabelecimento;
    private String mNumeroDoEstabelecimento;
    private String mBairroDoEstabelecimento;
    private String mCidadeDoEstabelecimento;
    private String mTelefoneDoEstabelecimento;
    private String mServicos;
    private String mHorarioAtendimento;
    private float mNotaEstabelecimento;
    private int mImagemEstabelecimento;
    private int mImagemEstabelecimentoThumb;

    public Estabelecimento(String id, String vNomeDoEstabelecimento, String vRuaDoEstabelecimento, String vNumeroDoEstabelecimento
            , String vBairroDoEstabelecimento, String vCidadeDoEstabelecimento, String vTelefoneDoEstabelecimento, String vServicos
            , String vHorarioAtendimento, String vIdAdministrador, float vNotaEstabelecimento, int vImagemEstabelecimento, int vImagemEstabelecimentoThumb) {

        mId = id;
        mIdAdministrador = vIdAdministrador;
        mNomeDoEstabelecimento = vNomeDoEstabelecimento;
        mRuaDoEstabelecimento = vRuaDoEstabelecimento;
        mNumeroDoEstabelecimento = vNumeroDoEstabelecimento;
        mBairroDoEstabelecimento = vBairroDoEstabelecimento;
        mCidadeDoEstabelecimento = vCidadeDoEstabelecimento;
        mTelefoneDoEstabelecimento = vTelefoneDoEstabelecimento;
        mImagemEstabelecimento = vImagemEstabelecimento;
        mNotaEstabelecimento = vNotaEstabelecimento;
        mImagemEstabelecimentoThumb = vImagemEstabelecimentoThumb;
        mServicos = vServicos;
        mHorarioAtendimento = vHorarioAtendimento;
    }

    public Estabelecimento(String vNomeDoEstabelecimento, String vRuaDoEstabelecimento, String vNumeroDoEstabelecimento
            , String vBairroDoEstabelecimento, String vCidadeDoEstabelecimento, String vTelefoneDoEstabelecimento, String vServicos
            , String vHorarioAtendimento, String vIdAdministrador, float vNotaEstabelecimento, int vImagemEstabelecimento, int vImagemEstabelecimentoThumb) {
        mIdAdministrador = vIdAdministrador;
        mNomeDoEstabelecimento = vNomeDoEstabelecimento;
        mRuaDoEstabelecimento = vRuaDoEstabelecimento;
        mNumeroDoEstabelecimento = vNumeroDoEstabelecimento;
        mBairroDoEstabelecimento = vBairroDoEstabelecimento;
        mCidadeDoEstabelecimento = vCidadeDoEstabelecimento;
        mTelefoneDoEstabelecimento = vTelefoneDoEstabelecimento;
        mImagemEstabelecimento = vImagemEstabelecimento;
        mNotaEstabelecimento = vNotaEstabelecimento;
        mImagemEstabelecimentoThumb = vImagemEstabelecimentoThumb;
        mServicos = vServicos;
        mHorarioAtendimento = vHorarioAtendimento;
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

    public float getmNotaEstabelecimento() {
        return mNotaEstabelecimento;
    }

    public int getmImagemEstabelecimento() {
        return mImagemEstabelecimento;
    }

    public int getmImagemEstabelecimentoThumb() {
        return mImagemEstabelecimentoThumb;
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
        this.mNotaEstabelecimento = mNotaEstabelecimento;
    }

    public void setmImagemEstabelecimento(int mImagemEstabelecimento) {
        this.mImagemEstabelecimento = mImagemEstabelecimento;
    }

    public void setmImagemEstabelecimentoThumb(int mImagemEstabelecimentoThumb) {
        this.mImagemEstabelecimentoThumb = mImagemEstabelecimentoThumb;
    }
}
