package com.pethealth.app;

import com.pethealth.controlador.LoginControlador;
import com.pethealth.vista.LoginVista;


public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {


            LoginVista loginView = new LoginVista();
            new LoginControlador(loginView);


            loginView.setVisible(true);
        });
    }
}
