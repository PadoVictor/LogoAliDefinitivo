package com.example.testandologoali;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.patinho.logoali.R;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;


public class QRReader extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView mScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        QRScanner();
    }

    public void QRScanner(){
        mScannerView = new ZXingScannerView(this);
        setContentView(mScannerView);
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result rawResult) {

        final String stringQR = rawResult.getText();
        String nomeCliente = stringQR.split(":")[1];
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("PONTOS FIDELIDADE");
        builder.setMessage("Gostaria de adicionar 1 ponto de fidelidade para " + nomeCliente + "?");
        builder.setPositiveButton(R.string.sim, new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id){
                int idCliente = Integer.valueOf(stringQR.split(":")[0]);
                int idEstabelecimentoQR = getIntent().getIntExtra("idEstabelecimento", -1);
                BancoDeDadosTeste.addFidelidade(idCliente, idEstabelecimentoQR);
                Log.i("fidelidade", "Pontuação:" + BancoDeDadosTeste.countFidelidade(idCliente, idEstabelecimentoQR));
                QRReader.this.finish();
            }
        });
        builder.setNegativeButton(R.string.nao, new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id){
                QRReader.this.finish();
            }
        });
        AlertDialog alert1 = builder.create();
        alert1.show();
    }
}

