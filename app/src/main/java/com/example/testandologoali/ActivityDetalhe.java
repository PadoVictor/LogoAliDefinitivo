package com.example.testandologoali;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.patinho.logoali.R;
import com.example.testandologoali.db.BancoDeDadosTeste;
import com.example.testandologoali.db.Estabelecimentos;

import java.util.Objects;

public class ActivityDetalhe extends AppCompatActivity {

    public final static String ID_ESTABELECIMENTO = "com.example.testandologoali.ActivityDetalhe.ID_ESTABELECIMENTO";

    private final int MenuItem_EditId = 1;

    private final int MenuItem_QRCamera = 2;

    Estabelecimentos estabelecimento;

    TextView nome, telefone, rua, numero, bairro, cidade, servicos, horario;

    ImageView imagem;

    static final int DIALOG_ID = 0;
    int hour_x, minute_x;

    RatingBar nota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe);

        final Intent intent = getIntent();
        final String idEstab = intent.getStringExtra(ID_ESTABELECIMENTO);
        refresh(idEstab);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh(estabelecimento.getmId());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if (!Objects.equals(estabelecimento.getmIdAdministrador(), LoginHandler.getUsuario().getId())) {
            getMenuInflater().inflate(R.menu.menu_details, menu);
        }

        if (Objects.equals(estabelecimento.getmIdAdministrador(), LoginHandler.getUsuario().getId())) {
            MenuItem edit_item_qr = menu.add(0, MenuItem_QRCamera, 0, "Câmera QR");
            edit_item_qr.setIcon(R.drawable.ic_qr_camera_24dp);
            edit_item_qr.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM);

            MenuItem edit_item = menu.add(0, MenuItem_EditId, 0, R.string.edit);
            edit_item.setIcon(R.drawable.ic_mode_edit_white_24dp);
            edit_item.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        }
        return true;
    }

    public void createAlarm(String message) {
        Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM)
                .putExtra(AlarmClock.EXTRA_MESSAGE, message)
                .putExtra(AlarmClock.EXTRA_HOUR, hour_x)
                .putExtra(AlarmClock.EXTRA_MINUTES, minute_x);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.adicionar_alarme:
                Toast.makeText(ActivityDetalhe.this, "Defina um alarme para visitar estabelecimento", Toast.LENGTH_LONG).show();
                showDialog(DIALOG_ID);
                break;
            case MenuItem_EditId:
                Intent intent = new Intent(ActivityDetalhe.this, ActivityEditEstab.class);
                String idEstabelecimento = estabelecimento.getmId();
                intent.putExtra(ActivityDetalhe.ID_ESTABELECIMENTO, idEstabelecimento);
                startActivity(intent);
                break;
            case MenuItem_QRCamera:
                int idEstabelecimentoQR = Integer.valueOf(estabelecimento.getmId());
                Intent intent1 = new Intent(ActivityDetalhe.this, QRReader.class);
                intent1.putExtra("idEstabelecimento", idEstabelecimentoQR);
                startActivity(intent1);
                break;
        }
        return true;
    }

    void refresh(String idEstab) {
        estabelecimento = BancoDeDadosTeste.getInstance().selectEstabelecimento(idEstab);

        imagem = (ImageView) findViewById(R.id.imagem_estabelecimento_detalhe);
        imagem.setImageResource(estabelecimento.getmImagemEstabelecimento());

        nome = (TextView) findViewById(R.id.nome_estabelecimento_detalhe);
        nome.setText(estabelecimento.getmNomeDoEstabelecimento());

        telefone = (TextView) findViewById(R.id.telefone_estabelecimento_detalhe);
        telefone.setText(estabelecimento.getmTelefoneDoEstabelecimento());

        rua = (TextView) findViewById(R.id.rua_estabelecimento_detalhe);
        rua.setText(estabelecimento.getmRuaDoEstabelecimento());

        numero = (TextView) findViewById(R.id.numero_estabelecimento_detalhe);
        numero.setText(String.valueOf(estabelecimento.getmNumeroDoEstabelecimento()));

        bairro = (TextView) findViewById(R.id.bairro_estabelecimento_detalhe);
        bairro.setText(estabelecimento.getmBairroDoEstabelecimento());

        cidade = (TextView) findViewById(R.id.cidade_estabelecimento_detalhe);
        cidade.setText(estabelecimento.getmCidadeDoEstabelecimento());

        servicos = (TextView) findViewById(R.id.serviços_estabelecimento_detalhe);
        servicos.setText(estabelecimento.getmServicos());

        horario = (TextView) findViewById(R.id.horario_estabelecimento_detalhe);
        horario.setText(estabelecimento.getmHorarioAtendimento());

        nota = (RatingBar) findViewById(R.id.rating_bar_detalhe);
        nota.setRating(estabelecimento.getmNotaEstabelecimento());
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG_ID)
            return new TimePickerDialog(ActivityDetalhe.this, kTimePickerListener, hour_x, minute_x, true);
        return null;
    }

    protected TimePickerDialog.OnTimeSetListener kTimePickerListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            hour_x = hourOfDay;
            minute_x = minute;
            createAlarm(nome.getText().toString());
        }
    };
}
