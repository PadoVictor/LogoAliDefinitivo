package com.example.testandologoali;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.patinho.logoali.R;
import com.example.testandologoali.db.Estabelecimento;
import com.example.testandologoali.db.Usuario;

import java.util.ArrayList;

public class ActivityEstabelecimentos extends AppCompatActivity {

    String adminID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estabelecimentos);

        adminID = LoginHandler.getUsuario().getIdUsuario();

        ArrayList<Estabelecimento> arrayListEstabelecimento = BancoDeDadosTeste.selectEstabelecimentoByAdmin(adminID);
        EstabelecimentoAdapter estAdapter = new EstabelecimentoAdapter(ActivityEstabelecimentos.this, arrayListEstabelecimento);
        final ListView listView = (ListView) findViewById(R.id.listview_activity_estabelecimentos);
        listView.setVisibility(View.VISIBLE);
        listView.setAdapter(estAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long l) {
                Intent intent = new Intent(ActivityEstabelecimentos.this, ActivityDetalhe.class);
                String idEstabelecimento = ((Estabelecimento) adapter.getItemAtPosition(position)).getmId();
                intent.putExtra(ActivityDetalhe.ID_ESTABELECIMENTO, idEstabelecimento);
                startActivity(intent);
            }
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
            if (Usuario.ADMIN.equals(LoginHandler.getUsuario().getAcesso())) {
                Intent intent = new Intent(ActivityEstabelecimentos.this, MainActivity.class);
                startActivity(intent);
            }
        }
        return true;
    }
}
