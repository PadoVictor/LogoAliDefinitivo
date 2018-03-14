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
import com.example.testandologoali.db.Nota;

import java.util.List;
import java.util.Objects;

public class ActivityDetalhe extends AppCompatActivity {

    public final static String ID_ESTABELECIMENTO = "com.example.testandologoali.ActivityDetalhe.ID_ESTABELECIMENTO";

    private final int MenuItem_EditId = 1;

    private final int MenuItem_QRCamera = 2;

    private Menu menu;

    volatile Estabelecimentos estabelecimento;
    volatile Nota nota;

    TextView nome, telefone, rua, numero, bairro, cidade, servicos, horario, notaMedia;

    ImageView imagem;

    static final int DIALOG_ID = 0;
    int hour_x, minute_x;

    RatingBar ratingBarNota;

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
        if (estabelecimento != null)
            refresh(estabelecimento.getmId());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
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
                String idEstabelecimentoQR = estabelecimento.getmId();
                Intent intent1 = new Intent(ActivityDetalhe.this, QRReader.class);
                intent1.putExtra("idEstabelecimento", idEstabelecimentoQR);
                startActivity(intent1);
                break;
        }
        return true;
    }

    void refresh(String idEstab) {

        ratingBarNota = findViewById(R.id.rating_bar_detalhe);
        ratingBarNota.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (nota != null)
                    nota.setNota(rating);
            }
        });

        BancoDeDadosTeste.getInstance().selectEstabelecimento(idEstab, result -> {
            if ((this.estabelecimento = (Estabelecimentos) result.getSingleObject()) != null) {
                imagem = findViewById(R.id.imagem_estabelecimento_detalhe);
                imagem.setImageBitmap(estabelecimento.getmImagemEstabelecimento(this));

                nome = findViewById(R.id.nome_estabelecimento_detalhe);
                nome.setText(estabelecimento.getmNomeDoEstabelecimento());

                telefone = findViewById(R.id.telefone_estabelecimento_detalhe);
                telefone.setText(estabelecimento.getmTelefoneDoEstabelecimento());

                rua = findViewById(R.id.rua_estabelecimento_detalhe);
                rua.setText(estabelecimento.getmRuaDoEstabelecimento());

                numero = findViewById(R.id.numero_estabelecimento_detalhe);
                numero.setText(String.valueOf(estabelecimento.getmNumeroDoEstabelecimento()));

                bairro = findViewById(R.id.bairro_estabelecimento_detalhe);
                bairro.setText(estabelecimento.getmBairroDoEstabelecimento());

                cidade = findViewById(R.id.cidade_estabelecimento_detalhe);
                cidade.setText(estabelecimento.getmCidadeDoEstabelecimento());

                servicos = findViewById(R.id.serviços_estabelecimento_detalhe);
                servicos.setText(estabelecimento.getmServicos());

                servicos = findViewById(R.id.dias_estabelecimento_detalhe);
                servicos.setText(estabelecimento.getmDiasAtendimento());

                horario = findViewById(R.id.horario_estabelecimento_detalhe);
                horario.setText(estabelecimento.getmHorarioAtendimento());

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
            }
        });

        BancoDeDadosTeste.getInstance().getNota(LoginHandler.getUsuario().getId(), idEstab, result -> {
            if ((this.nota = (Nota) result.getSingleObject()) != null) {
                ratingBarNota.setRating(nota.getNota());
            } else {
                this.nota = new Nota(null, LoginHandler.getUsuario().getId(), idEstab, "-1");
            }
        });

        BancoDeDadosTeste.getInstance().getNotasDoEstabelecimento(idEstab, result -> {
            List<Nota> notas = result.getObjectsList();
            notaMedia = findViewById(R.id.textViewNotaMedia);
            if (notas != null && !notas.isEmpty()) {
                float soma = 0f;
                for (Nota nota : notas) {
                    soma += nota.getNota();
                }
                notaMedia.setText("Média: " + (soma / (float) notas.size()));
            } else {
                notaMedia.setText("Não há avaliações deste estabelecimento ainda.");
            }
        });
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

    @Override
    protected void onPause() {
        if (nota != null && nota.getNota() >= 0)
            BancoDeDadosTeste.getInstance().createOrUpdateNota(nota, result -> {
            });

        super.onPause();
    }
}
