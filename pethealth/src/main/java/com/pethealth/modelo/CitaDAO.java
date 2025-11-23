package com.pethealth.modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class CitaDAO {
    private Connection conexion = new Conexion().getConexion();


    // Trae todas las citas de la base de datos ordenadas por fecha
    public List<Cita> findAll() {
        List<Cita> listaCitas = new ArrayList<>();
        String consultaSQL = "SELECT * FROM Cita ORDER BY fecha_hora DESC";
        try (PreparedStatement sentenciaPreparada = conexion.prepareStatement(consultaSQL);
                ResultSet resultadoConsulta = sentenciaPreparada.executeQuery()) {
            while (resultadoConsulta.next()) {
                Cita cita = new Cita();
                cita.setIdCita(resultadoConsulta.getInt("id_cita"));
                cita.setIdMascota(resultadoConsulta.getInt("id_mascota"));
                cita.setIdVeterinario(resultadoConsulta.getInt("id_veterinario"));
                cita.setFechaHora(resultadoConsulta.getTimestamp("fecha_hora").toLocalDateTime());
                cita.setMotivo(resultadoConsulta.getString("motivo"));
                cita.setDiagnostico(resultadoConsulta.getString("diagnostico"));
                cita.setEstado(resultadoConsulta.getString("estado"));
                cita.setCosto(resultadoConsulta.getDouble("costo"));
                listaCitas.add(cita);
            }
        } catch (SQLException excepcion) {
            System.err.println("Error al obtener citas: " + excepcion.getMessage());
        }
        return listaCitas;
    }


    // Cambia el estado de una cita
    public boolean updateEstado(int idCita, String nuevoEstado) {
        String consultaSQL = "UPDATE Cita SET estado = ? WHERE id_cita = ?";
        try (PreparedStatement sentenciaPreparada = conexion.prepareStatement(consultaSQL)) {
            sentenciaPreparada.setString(1, nuevoEstado);
            sentenciaPreparada.setInt(2, idCita);
            return sentenciaPreparada.executeUpdate() > 0;
        } catch (SQLException excepcion) {
            System.err.println("Error al actualizar estado: " + excepcion.getMessage());
        }
        return false;
    }


    // Guarda una nueva cita en la base de datos
    public boolean create(Cita cita) {
        // Busca el siguiente numero disponible para la cita
        int proximoId = obtenerProximoId();
        String consultaSQL = "INSERT INTO Cita (id_cita, id_mascota, id_veterinario, fecha_hora, motivo, diagnostico, estado, costo) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";


        try (PreparedStatement sentenciaPreparada = conexion.prepareStatement(consultaSQL)) {
            sentenciaPreparada.setInt(1, proximoId);
            sentenciaPreparada.setInt(2, cita.getIdMascota());
            sentenciaPreparada.setInt(3, cita.getIdVeterinario());
            sentenciaPreparada.setTimestamp(4, Timestamp.valueOf(cita.getFechaHora()));
            sentenciaPreparada.setString(5, cita.getMotivo());
            sentenciaPreparada.setString(6, cita.getDiagnostico() != null ? cita.getDiagnostico() : "");
            sentenciaPreparada.setString(7, cita.getEstado());
            sentenciaPreparada.setDouble(8, cita.getCosto());


            int filasAfectadas = sentenciaPreparada.executeUpdate();
            if (filasAfectadas > 0) {
                cita.setIdCita(proximoId);
                return true;
            }
        } catch (SQLException excepcion) {
            System.err.println("Error al crear cita: " + excepcion.getMessage());
        }
        return false;
    }


    // Busca el numero de cita mas alto que existe, le suma 1 para obtener el
    // siguiente numero disponible, si no hay citas, empieza con el numero 1
    private int obtenerProximoId() {
        String consultaSQL = "SELECT MAX(id_cita) as max_id FROM Cita";
        try (PreparedStatement sentenciaPreparada = conexion.prepareStatement(consultaSQL);
                ResultSet resultadoConsulta = sentenciaPreparada.executeQuery()) {
            if (resultadoConsulta.next()) {
                return resultadoConsulta.getInt("max_id") + 1;
            }
        } catch (SQLException excepcion) {
            System.err.println("Error al obtener próximo ID: " + excepcion.getMessage());
        }
        return 1;
    }
}