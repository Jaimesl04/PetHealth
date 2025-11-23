package com.pethealth.vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;


public class LoginVista extends JFrame {
    private JTextField campoNombre, campoCorreo, campoTelefono;
    private JButton botonLogin;


    // Crea la ventana de inicio de sesion
    public LoginVista() {
        setTitle("PetHealth - Login");
        setSize(450, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        inicializarComponentes();
    }


    // Organiza todos los elementos de la ventana de login
    private void inicializarComponentes() {
        JPanel panelPrincipal = new JPanel(new BorderLayout(15, 15));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));


        // Titulo
        JLabel etiquetaTitulo = new JLabel("PETHEALTH", SwingConstants.CENTER);
        etiquetaTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        etiquetaTitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        panelPrincipal.add(etiquetaTitulo, BorderLayout.NORTH);


        // Formulario
        JPanel panelFormulario = new JPanel(new GridLayout(4, 2, 12, 15));
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));


        panelFormulario.add(new JLabel("Nombre:"));
        campoNombre = new JTextField();
        panelFormulario.add(campoNombre);


        panelFormulario.add(new JLabel("Correo:"));
        campoCorreo = new JTextField();
        panelFormulario.add(campoCorreo);


        panelFormulario.add(new JLabel("Teléfono:"));
        campoTelefono = new JTextField();
        panelFormulario.add(campoTelefono);


        panelFormulario.add(new JLabel(""));
        botonLogin = new JButton("Iniciar Sesión");
        panelFormulario.add(botonLogin);


        panelPrincipal.add(panelFormulario, BorderLayout.CENTER);


        add(panelPrincipal);
        getRootPane().setDefaultButton(botonLogin);
    }


    // Obtiene el nombre ingresado
    public String getNombre() {
        return campoNombre.getText().trim();
    }


    // Obtiene el correo ingresado
    public String getCorreo() {
        return campoCorreo.getText().trim();
    }


    // Obtiene el telefono ingresado
    public String getTelefono() {
        return campoTelefono.getText().trim();
    }


    // Conecta el boton de login con su accion
    public void setLoginListener(ActionListener listener) {
        botonLogin.addActionListener(listener);
    }


    // Muestra un mensaje de error
    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }


}