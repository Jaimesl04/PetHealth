package com.pethealth.controlador;

import com.pethealth.modelo.Mascota;
import com.pethealth.modelo.MascotaDAO;
import com.pethealth.modelo.Cliente;
import com.pethealth.modelo.ClienteDAO;
import com.pethealth.vista.MascotasVista;
import java.util.List;

public class MascotasControlador {
    private MascotasVista vistaMascotas;
    private MascotaDAO accesoMascotaDAO;
    private ClienteDAO accesoClienteDAO;

    // Inicia el controlador y carga la lista de mascotas
    public MascotasControlador(MascotasVista vistaMascotas) {
        this.vistaMascotas = vistaMascotas;
        this.accesoMascotaDAO = new MascotaDAO();
        this.accesoClienteDAO = new ClienteDAO();
        inicializarControlador();
        cargarMascotas();
    }

    // Prepara el controlador
    // Solo se muestra la tabla
    private void inicializarControlador() {

    }

    // Trae todas las mascotas de la base de datos y las muestra
    private void cargarMascotas() {
        vistaMascotas.limpiarTabla();
        List<Mascota> listaMascotas = accesoMascotaDAO.findAll();

        for (Mascota mascota : listaMascotas) {
            // Busca el dueño de cada mascota
            Cliente clientePropietario = accesoClienteDAO.findById(mascota.getIdCliente());

            if (clientePropietario != null) {
                Object[] filaMascota = {
                        mascota.getIdMascota(),
                        mascota.getNombre(),
                        mascota.getEspecie(),
                        mascota.getRaza(),
                        mascota.getFechaNacimiento().toString(),
                        String.format("%.2f kg", mascota.getPeso()),
                        mascota.getColor(),
                        clientePropietario.getNombre()
                };
                vistaMascotas.agregarMascotaATabla(filaMascota);
            }
        }
    }
}