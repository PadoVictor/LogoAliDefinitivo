package com.example.testandologoali;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.patinho.logoali.R;
import com.example.testandologoali.db.BancoDeDadosTeste;
import com.example.testandologoali.db.Estabelecimentos;

import java.io.FileNotFoundException;
import java.io.InputStream;

import static com.example.testandologoali.ActivityDetalhe.ID_ESTABELECIMENTO;

public class ActivityEditEstab extends AppCompatActivity {

    // Este é o código de ação que usamos no intent,
    // Desta forma sabemos que estamos a olhar para a resposta da nossa própria ação.
    private static final int SELECT_PICTURE = 123;
    protected Estabelecimentos estabelecimento;
    boolean createMode = false;
    ImageView imagem;
    EditText nome, telefone, rua, numero, bairro, cidade, servicos, horario;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 987;

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

        (findViewById(R.id.imageView))
                .setOnClickListener(arg0 -> {

                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_GRANTED) {
                        openGallery();
                    } else {
                        requestPermission();
                    }
                });
    }

    private void openGallery() {
        // No onCreate ou qualquer evento onde quiser selecionar um arquivo
        Intent intent1 = new Intent();
        intent1.setType("image/*");
        intent1.setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(intent1,
                "Select Picture"), SELECT_PICTURE);
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    openGallery();

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    private void populateViews() {
        imagem = findViewById(R.id.imageView);
        imagem.setImageBitmap(estabelecimento.getmImagemEstabelecimento(this));

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImage = data.getData();
                InputStream imageStream = null;
                try {
                    imageStream = getContentResolver().openInputStream(selectedImage);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                Bitmap yourSelectedImage = BitmapFactory.decodeStream(imageStream);
                imagem.setImageBitmap(yourSelectedImage);
                estabelecimento.setmImagemEstabelecimento(getRealPathFromURI(this, selectedImage));
            }
        }
    }

    /**
     * auxiliar para saber o caminho de uma imagem URI
     */
    public String getPath(Uri uri) {

        if (uri == null) {
            // TODO realizar algum log ou feedback do utilizador
            return null;
        }


        // Tenta recuperar a imagem da media store primeiro
        // Isto só irá funcionar para as imagens selecionadas da galeria

        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }

        return uri.getPath();
    }

    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
}
