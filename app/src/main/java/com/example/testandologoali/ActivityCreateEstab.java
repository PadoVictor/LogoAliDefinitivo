package com.example.testandologoali;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.patinho.logoali.R;
import com.example.testandologoali.db.Estabelecimento;

/**
 * Created by Dener on 23/05/2017.
 */

public class ActivityCreateEstab extends ActivityEditEstab {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        createMode = true;
        super.onCreate(savedInstanceState);
        estabelecimento = new Estabelecimento("", "", "", "", "", "", "", "", LoginHandler.getUsuario().getIdUsuario(), 3,
                R.drawable.barbearia1, R.drawable.barbearia1_thumb);
        imagem = (ImageView) findViewById(R.id.imageView);
        imagem.setImageResource(estabelecimento.getmImagemEstabelecimento());

        nome = (EditText) findViewById(R.id.edit_text_nome_estabelecimento);
        telefone = (EditText) findViewById(R.id.edit_text_telefone_estabelecimento);
        rua = (EditText) findViewById(R.id.edit_text_rua_estabelecimento);
        numero = (EditText) findViewById(R.id.edit_text_numero_estabelecimento);
        bairro = (EditText) findViewById(R.id.edit_text_bairro_estabelecimento);
        cidade = (EditText) findViewById(R.id.edit_text_cidade_estabelecimento);
        servicos = (EditText) findViewById(R.id.edit_text_servicos_estabelecimento);
        horario = (EditText) findViewById(R.id.edit_text_horario_estabelecimento);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.edit_stab_salvar) {
            criarEstab();
            this.finish();
        }
        return true;
    }

    private void criarEstab() {
        setValuesFromText();
        BancoDeDadosTeste.createEstabelecimento(estabelecimento);
    }
}
