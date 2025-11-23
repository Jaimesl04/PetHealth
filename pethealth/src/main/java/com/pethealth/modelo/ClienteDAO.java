package com.pethealth.modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ClienteDAO {
    private Connection conexion = new Conexion().getConexion();


    // Trae todos los clientes de la base de datos
    public List<Cliente> findAll() {
        List<Cliente> listaClientes = new ArrayList<>();
        String consultaSQL = "SELECT * FROM Cliente";
        try (PreparedStatement sentenciaPreparada = conexion.prepareStatement(consultaSQL);
                ResultSet resultadoConsulta = sentenciaPreparada.executeQuery()) {
            while (resultadoConsulta.next()) {
                listaClientes.add(crearClienteDesdeResultSet(resultadoConsulta));
            }
        } catch (SQLException excepcion) {
            System.err.println("Error al obtener clientes: " + excepcion.getMessage());
        }
        return listaClientes;
    }


    // Busca un cliente especifico por su numero de ID
    public Cliente findById(int idCliente) {
        String consultaSQL = "SELECT * FROM Cliente WHERE id_cliente = ?";
        try (PreparedStatement sentenciaPreparada = conexion.prepareStatement(consultaSQL)) {
            sentenciaPreparada.setInt(1, idCliente);
            ResultSet resultadoConsulta = sentenciaPreparada.executeQuery();
            if (resultadoConsulta.next()) {
                return crearClienteDesdeResultSet(resultadoConsulta);
            }
        } catch (SQLException excepcion) {
            System.err.println("Error al buscar cliente: " + excepcion.getMessage());
        }
        return null;
    }


    // Toma los datos de la base de datos y crea un objeto Cliente
    private Cliente crearClienteDesdeResultSet(ResultSet resultadoConsulta) throws SQLException {
        Cliente cliente = new Cliente();
        cliente.setIdCliente(resultadoConsulta.getInt("id_cliente"));
        cliente.setNombre(resultadoConsulta.getString("nombre"));
        cliente.setTelefono(resultadoConsulta.getString("telefono"));
        cliente.setEmail(resultadoConsulta.getString("email"));
        cliente.setDireccion(resultadoConsulta.getString("direccion"));
        cliente.setFechaRegistro(resultadoConsulta.getDate("fecha_registro").toLocalDate());
        return cliente;
    }
}



