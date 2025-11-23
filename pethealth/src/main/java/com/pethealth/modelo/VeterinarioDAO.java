package com.pethealth.modelo;

import java.sql.*;
import java.util.*;


public class VeterinarioDAO {
    private Connection conexion = new Conexion().getConexion();


    // Revisa si el nombre, correo y telefono coinciden con un veterinario activo
    public boolean validarCredenciales(String nombre, String correo, String contrasena) {
        String consultaSQL;
        PreparedStatement sentenciaPreparada;


        try {
            // Dependiendo de que campos se han rellenado, busca de diferente forma
            if (!nombre.isEmpty() && !correo.isEmpty()) {
                // Si llenaron nombre y correo, usa los tres campos
                consultaSQL = "SELECT * FROM Veterinario WHERE nombre = ? AND email = ? AND telefono = ? AND estado = 'Activo'";
                sentenciaPreparada = conexion.prepareStatement(consultaSQL);
                sentenciaPreparada.setString(1, nombre.trim());
                sentenciaPreparada.setString(2, correo.trim());
                sentenciaPreparada.setString(3, contrasena.trim());
            } else if (!nombre.isEmpty()) {
                consultaSQL = "SELECT * FROM Veterinario WHERE nombre = ? AND telefono = ? AND estado = 'Activo'";
                sentenciaPreparada = conexion.prepareStatement(consultaSQL);
                sentenciaPreparada.setString(1, nombre.trim());
                sentenciaPreparada.setString(2, contrasena.trim());
            } else {
                // Si solo llenaron correo, usa correo y telefono
                consultaSQL = "SELECT * FROM Veterinario WHERE email = ? AND telefono = ? AND estado = 'Activo'";
                sentenciaPreparada = conexion.prepareStatement(consultaSQL);
                sentenciaPreparada.setString(1, correo.trim());
                sentenciaPreparada.setString(2, contrasena.trim());
            }


            // Si encuentra algun resultado, las credenciales son correctas
            return sentenciaPreparada.executeQuery().next();
        } catch (SQLException excepcion) {
            System.err.println("Error al validar credenciales: " + excepcion.getMessage());
        }
        return false;
    }


    // Trae todos los veterinarios que están activos en el sistema
    public List<Veterinario> findAll() {
        List<Veterinario> listaVeterinarios = new ArrayList<>();
        String consultaSQL = "SELECT * FROM Veterinario WHERE estado = 'Activo'";
        try (PreparedStatement sentenciaPreparada = conexion.prepareStatement(consultaSQL);
                ResultSet resultadoConsulta = sentenciaPreparada.executeQuery()) {
            while (resultadoConsulta.next()) {
                Veterinario veterinario = new Veterinario();
                veterinario.setIdVeterinario(resultadoConsulta.getInt("id_veterinario"));
                veterinario.setNombre(resultadoConsulta.getString("nombre"));
                veterinario.setEspecialidad(resultadoConsulta.getString("especialidad"));
                veterinario.setTelefono(resultadoConsulta.getString("telefono"));
                veterinario.setEmail(resultadoConsulta.getString("email"));
                veterinario.setHorarioTrabajo(resultadoConsulta.getString("horario_trabajo"));
                veterinario.setEstado(resultadoConsulta.getString("estado"));
                listaVeterinarios.add(veterinario);
            }
        } catch (SQLException excepcion) {
            System.err.println("Error al obtener veterinarios: " + excepcion.getMessage());
        }
        return listaVeterinarios;
    }
}