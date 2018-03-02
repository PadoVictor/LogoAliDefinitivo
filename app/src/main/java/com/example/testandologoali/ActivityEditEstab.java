package com.example.testandologoali;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.patinho.logoali.R;
import com.example.testandologoali.db.BancoDeDadosTeste;
import com.example.testandologoali.db.Estabelecimentos;

import static com.example.testandologoali.ActivityDetalhe.ID_ESTABELECIMENTO;

public class ActivityEditEstab extends AppCompatActivity {

    boolean createMode = false;

    protected Estabelecimentos estabelecimento;

    ImageView imagem;

    EditText nome, telefone, rua, numero, bairro, cidade, servicos, horario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_estab);

        final Intent intent = getIntent();

        //If no existing ID, create mode.
        //Else, edit mode
        String id = intent.getStringExtra(ID_ESTABELECIMENTO);
        if (id != null) {
            BancoDeDadosTeste.getInstance().selectEstabelecimento(id, result1 -> {
                if ((this.estabelecimento = (Estabelecimentos) result1.getSingleObject()) != null && !createMode) {
                    populateViews();
                }
            });
        } else {
            this.estabelecimento = new Estabelecimentos();
            createMode = true;
            populateViews();
        }
    }

    private void populateViews() {
        imagem = findViewById(R.id.imageView);
        imagem.setImageResource(estabelecimento.getmImagemEstabelecimento());

        nome = findViewById(R.id.edit_text_nome_estabelecimento);
        nome.setText(estabelecimento.getmNomeDoEstabelecimento());

        telefone = findViewById(R.id.edit_text_telefone_estabelecimento);
        telefone.setText(estabelecimento.getmTelefoneDoEstabelecimento());

        rua = findViewById(R.id.edit_text_rua_estabelecimento);
        rua.setText(estabelecimento.getmRuaDoEstabelecimento());

        numero = findViewById(R.id.edit_text_numero_estabelecimento);
        numero.setText(String.valueOf(estabelecimento.getmNumeroDoEstabelecimento()));

        bairro = findViewById(R.id.edit_text_bairro_estabelecimento);
        bairro.setText(estabelecimento.getmBairroDoEstabelecimento());

        cidade = findViewById(R.id.edit_text_cidade_estabelecimento);
        cidade.setText(estabelecimento.getmCidadeDoEstabelecimento());

        servicos = findViewById(R.id.edit_text_servicos_estabelecimento);
        servicos.setText(estabelecimento.getmServicos());

        horario = findViewById(R.id.edit_text_horario_estabelecimento);
        horario.setText(estabelecimento.getmHorarioAtendimento());
    }

    @Override
    protected void onPause() {

        setValuesFromText();

        if (createMode) {
            estabelecimento.setmIdAdministrador(LoginHandler.getUsuario().getId());
            BancoDeDadosTeste.getInstance().insertEstabelecimento(estabelecimento, result -> {
            });
        } else {
            BancoDeDadosTeste.getInstance().updateEstabelecimento(estabelecimento, result -> {
            });
        }

        super.onPause();
    }

    void setValuesFromText() {
        estabelecimento.setmNomeDoEstabelecimento(nome.getText() == null ? "" : nome.getText().toString());
        estabelecimento.setmTelefoneDoEstabelecimento(telefone.getText() == null ? "" : telefone.getText().toString());
        estabelecimento.setmRuaDoEstabelecimento(rua.getText() == null ? "" : rua.getText().toString());
        estabelecimento.setmNumeroDoEstabelecimento(numero.getText() == null ? "" : numero.getText().toString());
        estabelecimento.setmBairroDoEstabelecimento(bairro.getText() == null ? "" : bairro.getText().toString());
        estabelecimento.setmCidadeDoEstabelecimento(cidade.getText() == null ? "" : cidade.getText().toString());
        estabelecimento.setmServicos(servicos.getText() == null ? "" : servicos.getText().toString());
        estabelecimento.setmHorarioAtendimento(horario.getText() == null ? "" : horario.getText().toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_stab, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.edit_stab_salvar) {
            this.finish();
        }
        return true;
    }
}
