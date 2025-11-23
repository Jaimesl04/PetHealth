package com.pethealth.modelo;

import java.time.LocalDateTime;


public class Cita {
    private int idCita;
    private int idMascota;
    private int idVeterinario;
    private LocalDateTime fechaHora;
    private String motivo;
    private String diagnostico;
    private String estado;
    private double costo;


    public Cita() {
    }


    public Cita(int idMascota, int idVeterinario, LocalDateTime fechaHora,
            String motivo, String diagnostico, String estado, double costo) {
        this.idMascota = idMascota;
        this.idVeterinario = idVeterinario;
        this.fechaHora = fechaHora;
        this.motivo = motivo;
        this.diagnostico = diagnostico;
        this.estado = estado;
        this.costo = costo;
    }


    // Getters y Setters
    public int getIdCita() {
        return idCita;
    }


    public void setIdCita(int idCita) {
        this.idCita = idCita;
    }


    public int getIdMascota() {
        return idMascota;
    }


    public void setIdMascota(int idMascota) {
        this.idMascota = idMascota;
    }


    public int getIdVeterinario() {
        return idVeterinario;
    }


    public void setIdVeterinario(int idVeterinario) {
        this.idVeterinario = idVeterinario;
    }


    public LocalDateTime getFechaHora() {
        return fechaHora;
    }


    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }


    public String getMotivo() {
        return motivo;
    }


    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }


    public String getDiagnostico() {
        return diagnostico;
    }


    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }


    public String getEstado() {
        return estado;
    }


    public void setEstado(String estado) {
        this.estado = estado;
    }


    public double getCosto() {
        return costo;
    }


    public void setCosto(double costo) {
        this.costo = costo;
    }


}