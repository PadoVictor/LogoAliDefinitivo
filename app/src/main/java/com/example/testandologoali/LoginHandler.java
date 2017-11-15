package com.example.testandologoali;

import com.example.testandologoali.db.Usuario;

/**
 * Created by Dener on 15/05/2017.
 */

public class LoginHandler {
    private static Usuario usuario;

    public static void setUsuario(Usuario usuario) {
        LoginHandler.usuario = usuario;
    }

    public static void logout() {
        usuario = null;
    }

    public static Usuario getUsuario() {
        return usuario;
    }
}