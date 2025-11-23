package com.pethealth.modelo;

import java.time.LocalDate;


public class Mascota {
    private int idMascota;
    private int idCliente;
    private String nombre;
    private String especie;
    private String raza;
    private LocalDate fechaNacimiento;
    private double peso;
    private String color;
    private String historialMedico;


    public Mascota() {
    }


    public Mascota(int idCliente, String nombre, String especie, String raza,
            LocalDate fechaNacimiento, double peso, String color, String historialMedico) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.especie = especie;
        this.raza = raza;
        this.fechaNacimiento = fechaNacimiento;
        this.peso = peso;
        this.color = color;
        this.historialMedico = historialMedico;
    }


    // Getters y Setters
    public int getIdMascota() {
        return idMascota;
    }


    public void setIdMascota(int idMascota) {
        this.idMascota = idMascota;
    }


    public int getIdCliente() {
        return idCliente;
    }


    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }


    public String getNombre() {
        return nombre;
    }


    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public String getEspecie() {
        return especie;
    }


    public void setEspecie(String especie) {
        this.especie = especie;
    }


    public String getRaza() {
        return raza;
    }


    public void setRaza(String raza) {
        this.raza = raza;
    }


    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }


    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }


    public double getPeso() {
        return peso;
    }


    public void setPeso(double peso) {
        this.peso = peso;
    }


    public String getColor() {
        return color;
    }


    public void setColor(String color) {
        this.color = color;
    }


    public String getHistorialMedico() {
        return historialMedico;
    }


    public void setHistorialMedico(String historialMedico) {
        this.historialMedico = historialMedico;
    }


}