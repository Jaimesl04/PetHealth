package com.pethealth.modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class MascotaDAO {
    private Connection conexion = new Conexion().getConexion();


    // Trae todas las mascotas de la base de datos
    public List<Mascota> findAll() {
        List<Mascota> listaMascotas = new ArrayList<>();
        String consultaSQL = "SELECT * FROM Mascota";
        try (PreparedStatement sentenciaPreparada = conexion.prepareStatement(consultaSQL);
                ResultSet resultadoConsulta = sentenciaPreparada.executeQuery()) {
            while (resultadoConsulta.next()) {
                listaMascotas.add(crearMascotaDesdeResultSet(resultadoConsulta));
            }
        } catch (SQLException excepcion) {
            System.err.println("Error al obtener mascotas: " + excepcion.getMessage());
        }
        return listaMascotas;
    }


    // Busca una mascota especifica por su ID
    public Mascota findById(int idMascota) {
        String consultaSQL = "SELECT * FROM Mascota WHERE id_mascota = ?";
        try (PreparedStatement sentenciaPreparada = conexion.prepareStatement(consultaSQL)) {
            sentenciaPreparada.setInt(1, idMascota);
            ResultSet resultadoConsulta = sentenciaPreparada.executeQuery();
            if (resultadoConsulta.next()) {
                return crearMascotaDesdeResultSet(resultadoConsulta);
            }
        } catch (SQLException excepcion) {
            System.err.println("Error al buscar mascota: " + excepcion.getMessage());
        }
        return null;
    }


    // Toma los datos de la base de datos y crea un objeto Mascota
    private Mascota crearMascotaDesdeResultSet(ResultSet resultadoConsulta) throws SQLException {
        Mascota mascota = new Mascota();
        mascota.setIdMascota(resultadoConsulta.getInt("id_mascota"));
        mascota.setIdCliente(resultadoConsulta.getInt("id_cliente"));
        mascota.setNombre(resultadoConsulta.getString("nombre"));
        mascota.setEspecie(resultadoConsulta.getString("especie"));
        mascota.setRaza(resultadoConsulta.getString("raza"));
        mascota.setFechaNacimiento(resultadoConsulta.getDate("fecha_nacimiento").toLocalDate());
        mascota.setPeso(resultadoConsulta.getDouble("peso"));
        mascota.setColor(resultadoConsulta.getString("color"));
        mascota.setHistorialMedico(resultadoConsulta.getString("historial_medico"));
        return mascota;
    }
}