package com.example.testandologoali;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.example.patinho.logoali.R;
import com.example.testandologoali.db.BancoDeDadosTeste;
import com.example.testandologoali.db.Estabelecimentos;

public class ActivityEstabelecimentos extends AppCompatActivity {

    String adminID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estabelecimentos);

        adminID = LoginHandler.getUsuario().getId();

        updateList();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabAdicionarEstab);
        fab.setOnClickListener(view -> {
            // Click action
            Intent intent = new Intent(ActivityEstabelecimentos.this, ActivityEditEstab.class);
            startActivity(intent);
        });
    }

    private void updateList() {
        BancoDeDadosTeste.getInstance().selectEstabelecimentoByAdmin(adminID, (BancoDeDadosTeste.QueryResult result) -> {
            EstabelecimentoAdapter estAdapter = new EstabelecimentoAdapter(ActivityEstabelecimentos.this, result.getObjectsList());
            final ListView listView = findViewById(R.id.listview_activity_estabelecimentos);
            listView.setVisibility(View.VISIBLE);
            listView.setAdapter(estAdapter);

            listView.setOnItemClickListener((adapter, view, position, l) -> {
                Intent intent = new Intent(ActivityEstabelecimentos.this, ActivityDetalhe.class);
                String idEstabelecimento = ((Estabelecimentos) adapter.getItemAtPosition(position)).getmId();
                intent.putExtra(ActivityDetalhe.ID_ESTABELECIMENTO, idEstabelecimento);
                startActivity(intent);
            });
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_meus_estabelecimentos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.pesquisar_estabelecimentos) {
            if (BancoDeDadosTeste.ADMIN.equals(LoginHandler.getUsuario().getAcesso())) {
                Intent intent = new Intent(ActivityEstabelecimentos.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateList();
    }
}
