package com.example.testandologoali;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.patinho.logoali.R;

import java.util.ArrayList;

import static com.example.testandologoali.Usuario.Role.ADMIN;
import static com.example.testandologoali.Usuario.Role.USER;

public class MainActivity extends AppCompatActivity {

    EditText editTextNomeDaCidade;
    private final int MenuItem_MeusEstabelecimentos = 2;
    private final int MenuItem_QRWriter = 3;
    private final int MenuItem_Fidelidade = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Button mButtonBuscar = (Button) findViewById(R.id.botao_buscar_search_activity);

        mButtonBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextNomeDaCidade = (EditText) findViewById(R.id.edit_text_cidade_search_activity);
                ArrayList<Estabelecimento> arrayListEstabelecimento = BancoDeDadosTeste.selectEstabelecimentoByCidade(editTextNomeDaCidade.getText().toString());
                EstabelecimentoAdapter estAdapter = new EstabelecimentoAdapter(MainActivity.this, arrayListEstabelecimento);
                final ListView listView = (ListView) findViewById(R.id.list_view_busca);
                listView.setVisibility(View.VISIBLE);
                listView.setAdapter(estAdapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapter, View view, int position, long l) {
                        Intent intent = new Intent(MainActivity.this, ActivityDetalhe.class);
                        int idEstabelecimento = ((Estabelecimento) adapter.getItemAtPosition(position)).getmId();
                        intent.putExtra(ActivityDetalhe.ID_ESTABELECIMENTO, idEstabelecimento);
                        startActivity(intent);
                    }
                });
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if(USER == LoginHandler.getUsuario().getmRole()){
            MenuItem qrwriter_item = menu.add(0, MenuItem_QRWriter, 1, "Gerador de QR Code");
            qrwriter_item.setIcon(R.drawable.ic_qrwriter_24dp);
            qrwriter_item.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM);

            MenuItem fidelidade_item = menu.add(0, MenuItem_Fidelidade, 2, "Cartão Fidelidade");
            fidelidade_item.setIcon(R.drawable.ic_fidelidade_white_24dp);
            fidelidade_item.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        }

        if (ADMIN == LoginHandler.getUsuario().getmRole()) {
            MenuItem edit_item = menu.add(0, MenuItem_MeusEstabelecimentos, 0, "Meus Estabelecimentos");
            edit_item.setIcon(R.drawable.ic_meus_estabelecimentos_24dp);
            edit_item.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MenuItem_MeusEstabelecimentos:
                Intent intent = new Intent(MainActivity.this, ActivityEstabelecimentos.class);
                startActivity(intent);
                break;
            case MenuItem_QRWriter:
                String idCliente = LoginHandler.getUsuario().getmIdUsuario() + ":" + LoginHandler.getUsuario().getmNome();
                Intent intent1 = new Intent(MainActivity.this, QRWriter.class);
                intent1.putExtra("input", idCliente);
                startActivity(intent1);
        }
        return true;
    }
}

