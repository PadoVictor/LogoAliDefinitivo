package com.example.testandologoali;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.patinho.logoali.R;

import static com.example.testandologoali.ActivityDetalhe.ID_ESTABELECIMENTO;

public class ActivityEditEstab extends AppCompatActivity {

    boolean createMode = false;

    protected Estabelecimento estabelecimento;

    ImageView imagem;

    EditText nome, telefone, rua, numero, bairro, cidade, servicos, horario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_estab);

        final Intent intent = getIntent();
        estabelecimento = BancoDeDadosTeste.selectEstabelecimento(intent.getIntExtra(ID_ESTABELECIMENTO, -1));

        if (estabelecimento != null && !createMode) {
            populateViews();
        }
    }

    private void populateViews() {
        imagem = (ImageView) findViewById(R.id.imageView);
        imagem.setImageResource(estabelecimento.getmImagemEstabelecimento());

        nome = (EditText) findViewById(R.id.edit_text_nome_estabelecimento);
        nome.setText(estabelecimento.getmNomeDoEstabelecimento());

        telefone = (EditText) findViewById(R.id.edit_text_telefone_estabelecimento);
        telefone.setText(estabelecimento.getmTelefoneDoEstabelecimento());

        rua = (EditText) findViewById(R.id.edit_text_rua_estabelecimento);
        rua.setText(estabelecimento.getmRuaDoEstabelecimento());

        numero = (EditText) findViewById(R.id.edit_text_numero_estabelecimento);
        numero.setText(String.valueOf(estabelecimento.getmNumeroDoEstabelecimento()));

        bairro = (EditText) findViewById(R.id.edit_text_bairro_estabelecimento);
        bairro.setText(estabelecimento.getmBairroDoEstabelecimento());

        cidade = (EditText) findViewById(R.id.edit_text_cidade_estabelecimento);
        cidade.setText(estabelecimento.getmCidadeDoEstabelecimento());

        servicos = (EditText) findViewById(R.id.edit_text_servicos_estabelecimento);
        servicos.setText(estabelecimento.getmServicos());

        horario = (EditText) findViewById(R.id.edit_text_horario_estabelecimento);
        horario.setText(estabelecimento.getmHorarioAtendimento());
    }

    @Override
    protected void onPause() {

        setValuesFromText();

        if (!createMode) {
            BancoDeDadosTeste.updateEstabelecimento(estabelecimento);
        }

        super.onPause();
    }

    void setValuesFromText(){
        estabelecimento.setmNomeDoEstabelecimento(nome.getText().toString());
        estabelecimento.setmTelefoneDoEstabelecimento(telefone.getText().toString());
        estabelecimento.setmRuaDoEstabelecimento(rua.getText().toString());
        estabelecimento.setmNumeroDoEstabelecimento(numero.getText().toString());
        estabelecimento.setmBairroDoEstabelecimento(bairro.getText().toString());
        estabelecimento.setmCidadeDoEstabelecimento(cidade.getText().toString());
        estabelecimento.setmServicos(servicos.getText().toString());
        estabelecimento.setmHorarioAtendimento(horario.getText().toString());
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
