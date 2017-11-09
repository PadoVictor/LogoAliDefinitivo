package com.example.testandologoali;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.patinho.logoali.R;
import com.example.testandologoali.db.BancoDeDadosTeste;
import com.example.testandologoali.db.Estabelecimentos;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class SplashDevMedia extends Activity {

    private static int SPLASH_TIME_OUT = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_dev_media);

        BancoDeDadosTeste.getInstance(this);

        new Thread() {
            @Override
            public void run() {
                try {
//                Usuario user = new Usuario();
//                user.setNome("Dener");
//                user.setEmail("dener.siros@gmail.com");
//                user.setAcesso(Usuario.ADMIN);
//
//                    Usuario usuario2 = BancoDeDadosTeste.getInstance(null).insertUsuario(user);
//
//                    Usuario usuario3 = BancoDeDadosTeste.getInstance(null).selectAdministradorByEmail("p@to.com");
//
//                    Usuario usuario4 = BancoDeDadosTeste.getInstance(null).selectAdministrador(usuario2.getIdUsuario());
                    Estabelecimentos estab = new Estabelecimentos();
                    estab.setmNomeDoEstabelecimento("Jebediah Corte e tosa");
                    estab.setmRuaDoEstabelecimento("Rua dos Astronautas");
                    estab.setmNumeroDoEstabelecimento("42");
                    estab.setmBairroDoEstabelecimento("Jd. Uirá");
                    estab.setmCidadeDoEstabelecimento("São José dos Campos");
                    estab.setmTelefoneDoEstabelecimento("(12) 9714-5666");
                    estab.setmServicos("Corte zero-g, gel anti-vácuo, comida de astronauta");
                    estab.setmHorarioAtendimento("Das 8:00 às 17:00");
                    estab.setmIdAdministrador("9603ff90-47f0-4d4b-bc4a-a92da5e9ecb2");
                    estab.setmDiasAtendimento("Terça a domingo");


                    Estabelecimentos estab2 = BancoDeDadosTeste.getInstance().insertEstabelecimento(estab);


                    List<Estabelecimentos> l1 = BancoDeDadosTeste.getInstance(null).selectEstabelecimentoByAdmin("9603ff90-47f0-4d4b-bc4a-a92da5e9ecb2");

                    List<Estabelecimentos> l2 = BancoDeDadosTeste.getInstance(null).selectEstabelecimentoByCidade("S");

                    Estabelecimentos estabelecimento = BancoDeDadosTeste.getInstance(null).selectEstabelecimento(estab.getmId());

                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };//.start();


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
