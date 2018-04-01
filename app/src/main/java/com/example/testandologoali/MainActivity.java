package com.example.testandologoali;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;

import com.example.patinho.logoali.R;
import com.example.testandologoali.db.BancoDeDadosTeste;
import com.example.testandologoali.db.Estabelecimentos;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    AutoCompleteTextView editTextNomeDaCidade;
    private final int MenuItem_MeusEstabelecimentos = 2;
    private final int MenuItem_QRWriter = 3;
    private final int MenuItem_Fidelidade = 4;
    private final int MenuItem_Logout = 5;
    private GoogleApiClient mGoogleApiClient;

    View.OnClickListener listenerTabela;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, connectionResult -> {
                    if (!connectionResult.isSuccess())
                        throw new InternalError(connectionResult.getErrorMessage());
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API)
                .build();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Button mButtonBuscar = findViewById(R.id.botao_buscar_search_activity);
        editTextNomeDaCidade = findViewById(R.id.edit_text_cidade_search_activity);

        listenerTabela = v -> update();
        editTextNomeDaCidade.setOnClickListener(listenerTabela);
        editTextNomeDaCidade.setOnItemClickListener((parent, view, position, id) -> update());
        mButtonBuscar.setOnClickListener(listenerTabela);

        BancoDeDadosTeste.getInstance().selectCidades(result -> {
            List<String> cidades = new ArrayList<>();
            for (Estabelecimentos estabelecimento : (List<Estabelecimentos>) (result.getObjectsList())) {
                if (!cidades.contains(estabelecimento.getmCidadeDoEstabelecimento()))
                    cidades.add(estabelecimento.getmCidadeDoEstabelecimento());
            }
            ArrayAdapter<String> dropDownAdapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_dropdown_item_1line, cidades);
            editTextNomeDaCidade.setAdapter(dropDownAdapter);
        });
    }

    private void update() {
        BancoDeDadosTeste.getInstance().selectEstabelecimentoByCidade(editTextNomeDaCidade.getText().toString(), result -> {
            EstabelecimentoAdapter estAdapter = new EstabelecimentoAdapter(MainActivity.this, result.getObjectsList());
            final ListView listView = findViewById(R.id.list_view_busca);
            listView.setVisibility(View.VISIBLE);
            listView.setAdapter(estAdapter);

            listView.setOnItemClickListener((adapter, view, position, l) -> {
                Intent intent = new Intent(MainActivity.this, ActivityDetalhe.class);
                String idEstabelecimento = ((Estabelecimentos) adapter.getItemAtPosition(position)).getmId();
                intent.putExtra(ActivityDetalhe.ID_ESTABELECIMENTO, idEstabelecimento);
                startActivity(intent);
            });
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if (BancoDeDadosTeste.USER.equals(LoginHandler.getUsuario().getAcesso())) {
            MenuItem qrwriter_item = menu.add(0, MenuItem_QRWriter, 1, "Gerador de QR Code");
            qrwriter_item.setIcon(R.drawable.ic_qrwriter_24dp);
            qrwriter_item.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM);

            MenuItem fidelidade_item = menu.add(0, MenuItem_Fidelidade, 2, "Cartão Fidelidade");
            fidelidade_item.setIcon(R.drawable.ic_fidelidade_white_24dp);
            fidelidade_item.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        }

        if (BancoDeDadosTeste.ADMIN.equals(LoginHandler.getUsuario().getAcesso())) {
            MenuItem edit_item = menu.add(0, MenuItem_MeusEstabelecimentos, 0, "Meus Estabelecimentos");
            edit_item.setIcon(R.drawable.ic_meus_estabelecimentos_24dp);
            edit_item.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        }

        MenuItem fidelidade_item = menu.add(0, MenuItem_Logout, 3, "Sair");
        fidelidade_item.setIcon(R.drawable.ic_power_settings_new_white_24dp);
        fidelidade_item.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_NEVER);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MenuItem_MeusEstabelecimentos:
                Intent intent = new Intent(MainActivity.this, ActivityEstabelecimentos.class);
                startActivity(intent);
                finish();
                break;
            case MenuItem_QRWriter:
                String idCliente = LoginHandler.getUsuario().getId() + ":" + LoginHandler.getUsuario().getNome();
                Intent intent1 = new Intent(MainActivity.this, QRWriter.class);
                intent1.putExtra("input", idCliente);
                startActivity(intent1);
                break;
            case MenuItem_Fidelidade:
                Intent intent2 = new Intent(MainActivity.this, FidelidadeActivity.class);
                startActivity(intent2);
                break;
            case MenuItem_Logout:
                Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                        status -> {
                            LoginHandler.logout();
                            Intent intent3 = new Intent(MainActivity.this, GPlusActivity.class);
                            startActivity(intent3);
                            finish();
                        });
        }
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }
}

