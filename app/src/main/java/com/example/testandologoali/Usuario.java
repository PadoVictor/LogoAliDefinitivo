package com.example.testandologoali;

public class Usuario {

    enum Role {
        ADMIN,
        USER,
        SUPPORT
    }

    private int mIdUsuario;
    private String mNome;
    private String mSobrenome;
    private String mCadastroDePessoa;
    private String mEmail;
    private String mSenha;
    private final Role mRole;

    public Usuario(int vIdUsuario, String vNome, String vSobrenome, String vCadastroDePessoa, String vEmail, String vSenha, Role vRole) {
        mIdUsuario = vIdUsuario;
        mNome = vNome;
        mSobrenome = vSobrenome;
        mCadastroDePessoa = vCadastroDePessoa;
        mEmail = vEmail;
        mSenha = vSenha;
        mRole = vRole;
    }

    public int getmIdUsuario() {
        return mIdUsuario;
    }

    public String getmNome() {
        return mNome;
    }

    public String getmSobrenome() {
        return mSobrenome;
    }

    public String getmCadastroDePessoa() {
        return mCadastroDePessoa;
    }

    public String getmEmail() {
        return mEmail;
    }

    public String getmSenha() {
        return mSenha;
    }

    public Role getmRole() {
        return mRole;
    }
}
