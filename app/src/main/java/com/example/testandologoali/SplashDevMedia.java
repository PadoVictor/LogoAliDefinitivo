package com.example.testandologoali;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.patinho.logoali.R;
import com.example.testandologoali.db.Usuario;

import java.util.concurrent.ExecutionException;

public class SplashDevMedia extends Activity {

    private static int SPLASH_TIME_OUT = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_dev_media);

        BancoDeDadosTeste.getInstance(this);

//        new Thread() {
//            @Override
//            public void run() {
//                Usuario user = new Usuario();
//                user.setNome("Dener");
//                user.setEmail("dener.siros@gmail.com");
//                user.setAcesso(Usuario.ADMIN);
//
//                try {
//                    Usuario usuario2 = BancoDeDadosTeste.getInstance(null).insertUsuario(user);
//
//                    Usuario usuario3 = BancoDeDadosTeste.getInstance(null).selectAdministradorByEmail("p@to.com");
//
//                    Usuario usuario4 = BancoDeDadosTeste.getInstance(null).selectAdministrador(usuario2.getIdUsuario());
//                } catch (ExecutionException | InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }.start();


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
