package com.example.testandologoali;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.example.patinho.logoali.R;
import com.example.testandologoali.db.BancoDeDadosTeste;
import com.example.testandologoali.db.Fidelidade;

public class FidelidadeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fidelidade);
        updateList();
    }

    private void updateList() {
        BancoDeDadosTeste.getInstance().selectFidelidadeByUsuario(LoginHandler.getUsuario().getId(), (BancoDeDadosTeste.QueryResult result) -> {
            FidelidadeAdapter fidelidadeAdapter = new FidelidadeAdapter(FidelidadeActivity.this, result.getObjectsList());
            final ListView listView = findViewById(R.id.listViewFidelidade);
            listView.setVisibility(View.VISIBLE);
            listView.setAdapter(fidelidadeAdapter);

            listView.setOnItemClickListener((adapter, view, position, l) -> {
                Intent intent = new Intent(FidelidadeActivity.this, ActivityDetalhe.class);
                String idEstabelecimento = ((Fidelidade) adapter.getItemAtPosition(position)).getmIdEstabelecimento();
                intent.putExtra(ActivityDetalhe.ID_ESTABELECIMENTO, idEstabelecimento);
                startActivity(intent);
            });
        });
    }
}
