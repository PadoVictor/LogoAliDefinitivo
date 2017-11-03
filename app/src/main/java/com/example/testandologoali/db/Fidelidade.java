package com.example.testandologoali.db;

/**
 * Created by Dener on 24/05/2017.
 */

public class Fidelidade {
    int mIdUsuario;
    int mIdEstabelecimento;
    int mContagem = 1;

    public Fidelidade(int vIdUsuario, int vIdEstabelecimento) {
        mIdUsuario = vIdUsuario;
        mIdEstabelecimento = vIdEstabelecimento;
    }

    public int getmIdUsuario() {
        return mIdUsuario;
    }

    public int getmIdEstabelecimento() {
        return mIdEstabelecimento;
    }

    public int getmContagem() {
        return mContagem;
    }

    public int addContagem() {
        return ++mContagem;
    }
}
