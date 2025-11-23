package com.pethealth.controlador;

import com.pethealth.modelo.VeterinarioDAO;
import com.pethealth.vista.LoginVista;
import com.pethealth.vista.CitasVista;

public class LoginControlador {
    private LoginVista vistaLogin;
    private VeterinarioDAO accesoVeterinarioDAO;

    // Prepara el controlador del login y conecta el boton
    public LoginControlador(LoginVista vistaLogin) {
        this.vistaLogin = vistaLogin;
        this.accesoVeterinarioDAO = new VeterinarioDAO();
        vistaLogin.setLoginListener(evento -> validarLogin());
    }

    // Revisa si los datos de login son correctos
    private void validarLogin() {
        String nombreUsuario = vistaLogin.getNombre();
        String correoUsuario = vistaLogin.getCorreo();
        String telefonoUsuario = vistaLogin.getTelefono();

        // Si las credenciales que se han puesto son correctas se abre la ventana
        // principal
        if ((nombreUsuario.isEmpty() && correoUsuario.isEmpty()) || telefonoUsuario.isEmpty()) {
            vistaLogin.mostrarError("Complete los campos requeridos");
            return;
        }

        if (accesoVeterinarioDAO.validarCredenciales(nombreUsuario, correoUsuario, telefonoUsuario)) {
            vistaLogin.dispose();
            abrirVentanaCitas();
        } else {
            vistaLogin.mostrarError("Las credenciales son incorrectas");
        }
    }

    // Abre la ventana principal de citas despues del login
    private void abrirVentanaCitas() {
        javax.swing.SwingUtilities.invokeLater(() -> {
            CitasVista vistaCitas = new CitasVista();
            new CitasControlador(vistaCitas);
            vistaCitas.setVisible(true);
        });
    }
}