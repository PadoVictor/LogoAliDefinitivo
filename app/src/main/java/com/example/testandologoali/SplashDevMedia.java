package com.example.testandologoali;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.patinho.logoali.R;
import com.example.testandologoali.db.BancoDeDadosTeste;

public class SplashDevMedia extends Activity {

    private static int SPLASH_TIME_OUT = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_dev_media);

        //Inicializar BD
        BancoDeDadosTeste.getInstance(this);

        new Thread() {
            @Override
            public void run() {
                try {
//                    Usuario user = new Usuario();
//                    user.setNome("Marlene");
//                    user.setEmail("marlenesi2204@gmail.com");
//                    user.setAcesso(BancoDeDadosTeste.ADMIN);
//
//                    BancoDeDadosTeste.getInstance(null).insertUsuario(user, result -> {});
//
//                    BancoDeDadosTeste.getInstance().selectAdministrador("9603ff90-47f0-4d4b-bc4a-a92da5e9ecb2", result -> {
//                        Log.d(SplashDevMedia.class.getName(), result.toString());
//                    });
//
//                    BancoDeDadosTeste.getInstance().selectAdministradorByEmail("marlenesi2204@gmail.com", result -> {
//                        Log.d(SplashDevMedia.class.getName(), result.toString());
//                    });

//                    Estabelecimentos estab = new Estabelecimentos();
//                    estab.setmNomeDoEstabelecimento("Salão Fino Corte");
//                    estab.setmRuaDoEstabelecimento("Av. Juscelino Kubitschek");
//                    estab.setmNumeroDoEstabelecimento("2048");
//                    estab.setmBairroDoEstabelecimento("Vila Tatetuba");
//                    estab.setmCidadeDoEstabelecimento("São José dos Campos");
//                    estab.setmTelefoneDoEstabelecimento("(12) 1234-5678");
//                    estab.setmServicos("Corte Fino, Lavagem grossa, Tratamento anti frizz");
//                    estab.setmHorarioAtendimento("Das 9:00 às 18:00");
//                    estab.setmIdAdministrador("f8b33dc4-e0c0-4c86-b4dd-a27b60089f72");
//                    estab.setmDiasAtendimento("Terça a sábado");
//
//                    BancoDeDadosTeste.getInstance().insertEstabelecimento(estab, result -> {});

//                    List<Estabelecimentos> l1 = BancoDeDadosTeste.getInstance().selectEstabelecimentoByAdmin("9603ff90-47f0-4d4b-bc4a-a92da5e9ecb2");

//                    List<Estabelecimentos> l2 = BancoDeDadosTeste.getInstance().selectEstabelecimentoByCidade("S");

//                    Estabelecimentos estabelecimento = BancoDeDadosTeste.getInstance().selectEstabelecimento(l2.get(0).getmId());

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }.start();


        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent i = new Intent(SplashDevMedia.this, GPlusActivity.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
