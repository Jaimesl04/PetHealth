package com.pethealth.modelo;

public class Veterinario {
    private int idVeterinario;
    private String nombre;
    private String especialidad;
    private String telefono;
    private String email;
    private String horarioTrabajo;
    private String estado;


    public Veterinario() {
    }


    public Veterinario(String nombre, String especialidad, String telefono,
            String email, String horarioTrabajo, String estado) {
        this.nombre = nombre;
        this.especialidad = especialidad;
        this.telefono = telefono;
        this.email = email;
        this.horarioTrabajo = horarioTrabajo;
        this.estado = estado;
    }


    // Getters y Setters
    public int getIdVeterinario() {
        return idVeterinario;
    }


    public void setIdVeterinario(int idVeterinario) {
        this.idVeterinario = idVeterinario;
    }


    public String getNombre() {
        return nombre;
    }


    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public String getEspecialidad() {
        return especialidad;
    }


    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }


    public String getTelefono() {
        return telefono;
    }


    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }


    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }


    public String getHorarioTrabajo() {
        return horarioTrabajo;
    }


    public void setHorarioTrabajo(String horarioTrabajo) {
        this.horarioTrabajo = horarioTrabajo;
    }


    public String getEstado() {
        return estado;
    }


    public void setEstado(String estado) {
        this.estado = estado;
    }


}