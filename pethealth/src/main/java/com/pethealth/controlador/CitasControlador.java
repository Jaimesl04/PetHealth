package com.pethealth.controlador;

import com.pethealth.modelo.Cita;
import com.pethealth.modelo.CitaDAO;
import com.pethealth.modelo.Mascota;
import com.pethealth.modelo.MascotaDAO;
import com.pethealth.modelo.Veterinario;
import com.pethealth.modelo.VeterinarioDAO;
import com.pethealth.modelo.Cliente;
import com.pethealth.modelo.ClienteDAO;
import com.pethealth.vista.CitasVista;
import com.pethealth.vista.MascotasVista;
import javax.swing.JOptionPane;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.*;

public class CitasControlador {
    private CitasVista vistaCitas;
    private CitaDAO citaDAO;
    private MascotaDAO mascotaDAO;
    private VeterinarioDAO veterinarioDAO;
    private ClienteDAO clienteDAO;

    // Para mostrar las fechas ordenadas en la tabla
    private DateTimeFormatter formateadorFechaHora = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    // Guarda todas las citas para poder filtrar sin ir a la base de datos cada vez
    private List<Cita> todasLasCitas;

    // Inicia el controlador y carga las citas que ya existen
    public CitasControlador(CitasVista vistaCitas) {
        this.vistaCitas = vistaCitas;
        this.citaDAO = new CitaDAO();
        this.mascotaDAO = new MascotaDAO();
        this.veterinarioDAO = new VeterinarioDAO();
        this.clienteDAO = new ClienteDAO();
        inicializarControlador();
        cargarTodasLasCitas();
    }

    // Conecta los botones con sus acciones
    private void inicializarControlador() {
        vistaCitas.setAgregarListener(evento -> agregarCita());
        vistaCitas.setCompletarListener(evento -> completarCita());
        vistaCitas.setCancelarListener(evento -> cancelarCita());
        vistaCitas.setMascotasListener(evento -> abrirGestionMascotas());
        vistaCitas.setBuscarListener(evento -> buscarCitas()); // NUEVO: Buscar citas
    }

    // Trae todas las citas de la base de datos y las guarda en memoria
    private void cargarTodasLasCitas() {
        this.todasLasCitas = citaDAO.findAll();
        mostrarCitasEnTabla(todasLasCitas);
    }

    // Muestra las citas en la tabla
    private void mostrarCitasEnTabla(List<Cita> citasAMostrar) {
        vistaCitas.limpiarTabla();

        for (Cita citaActual : citasAMostrar) {
            Mascota mascotaCita = mascotaDAO.findById(citaActual.getIdMascota());
            Veterinario veterinarioCita = obtenerVeterinarioPorId(citaActual.getIdVeterinario());

            if (mascotaCita != null && veterinarioCita != null) {
                Cliente duenoMascota = clienteDAO.findById(mascotaCita.getIdCliente());
                String nombreDueno = (duenoMascota != null) ? duenoMascota.getNombre() : "No encontrado";

                Object[] filaCita = {
                        citaActual.getIdCita(),
                        mascotaCita.getNombre(),
                        mascotaCita.getEspecie(),
                        nombreDueno,
                        veterinarioCita.getNombre(),
                        citaActual.getFechaHora().format(formateadorFechaHora),
                        citaActual.getMotivo(),
                        citaActual.getEstado(),
                        String.format("€%.2f", citaActual.getCosto())
                };
                vistaCitas.agregarCitaATabla(filaCita);
            }
        }
    }

    // Busca citas por nombre de mascota o dueño
    private void buscarCitas() {
        String textoBusqueda = vistaCitas.getTextoBusqueda().toLowerCase();

        if (textoBusqueda.isEmpty()) {
            // Si no hay texto de busqueda, mostrar todas las citas
            mostrarCitasEnTabla(todasLasCitas);
            return;
        }

        List<Cita> citasFiltradas = new ArrayList<>();

        for (Cita citaActual : todasLasCitas) {
            Mascota mascotaCita = mascotaDAO.findById(citaActual.getIdMascota());

            if (mascotaCita != null) {
                Cliente duenoMascota = clienteDAO.findById(mascotaCita.getIdCliente());
                String nombreDueno = (duenoMascota != null) ? duenoMascota.getNombre() : "";

                // Buscar en el nombre de la mascota o en el nombre del dueño
                boolean coincideMascota = mascotaCita.getNombre().toLowerCase().contains(textoBusqueda);
                boolean coincideDueno = nombreDueno.toLowerCase().contains(textoBusqueda);

                if (coincideMascota || coincideDueno) {
                    citasFiltradas.add(citaActual);
                }
            }
        }

        if (citasFiltradas.isEmpty()) {
            vistaCitas.mostrarMensaje("No se encontraron citas para: " + textoBusqueda, "Busqueda sin resultados",
                    JOptionPane.INFORMATION_MESSAGE);
        }

        mostrarCitasEnTabla(citasFiltradas);
    }

    // Abre la ventana para agregar una cita nueva
    private void agregarCita() {
        List<String> listaMascotasCombo = obtenerMascotasParaCombo();
        List<String> listaVeterinariosCombo = obtenerVeterinariosParaCombo();

        Object[] datosNuevaCita = vistaCitas.mostrarDialogoAgregarCita(listaMascotasCombo, listaVeterinariosCombo);

        if (datosNuevaCita != null) {
            if (guardarCitaEnBaseDeDatos(datosNuevaCita)) {
                // Recargar todas las citas despues de agregar una nueva
                cargarTodasLasCitas();
                // Dejar la busqueda limpia para mostrar todas las citas
                vistaCitas.limpiarBusqueda();
                vistaCitas.mostrarMensaje("Cita agregada correctamente a la base de datos", "Exito",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                vistaCitas.mostrarMensaje("Error al guardar la cita en la base de datos", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Toma los datos del formulario y los guarda en la base de datos
    private boolean guardarCitaEnBaseDeDatos(Object[] datosCita) {
        try {
            int idMascotaSeleccionada = extraerIdDeCombo(datosCita[0].toString());
            int idVeterinarioSeleccionado = extraerIdDeCombo(datosCita[1].toString());

            // Convierte el texto de fecha a un formato que entiende Java
            String fechaHoraTexto = datosCita[2].toString();
            DateTimeFormatter formateadorEntrada = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime fechaHoraCita = LocalDateTime.parse(fechaHoraTexto, formateadorEntrada);

            Cita nuevaCita = new Cita();
            nuevaCita.setIdMascota(idMascotaSeleccionada);
            nuevaCita.setIdVeterinario(idVeterinarioSeleccionado);
            nuevaCita.setFechaHora(fechaHoraCita);
            nuevaCita.setMotivo(datosCita[3].toString());
            nuevaCita.setDiagnostico("");
            nuevaCita.setEstado(datosCita[4].toString());
            nuevaCita.setCosto((Double) datosCita[5]);

            boolean resultadoGuardado = citaDAO.create(nuevaCita);
            return resultadoGuardado;

        } catch (Exception excepcion) {
            System.err.println("Error al procesar los datos de la cita");
            excepcion.printStackTrace();
            return false;
        }
    }

    // Extrae solo el numero de ID del texto del ComboBox
    private int extraerIdDeCombo(String textoCombo) {
        try {
            String[] partesTexto = textoCombo.split(" - ");
            return Integer.parseInt(partesTexto[0].trim());
        } catch (Exception excepcion) {
            System.err.println("Error al extraer el ID de: " + textoCombo);
            return -1;
        }
    }

    // Prepara la lista de mascotas para mostrar en el ComboBox
    private List<String> obtenerMascotasParaCombo() {
        List<String> opcionesMascotas = new java.util.ArrayList<>();
        List<Mascota> listaMascotas = mascotaDAO.findAll();

        for (Mascota mascotaActual : listaMascotas) {
            opcionesMascotas.add(mascotaActual.getIdMascota() + " - " + mascotaActual.getNombre() + " ("
                    + mascotaActual.getEspecie() + ")");
        }

        return opcionesMascotas;
    }

    // Prepara la lista de veterinarios para mostrar en el ComboBox
    private List<String> obtenerVeterinariosParaCombo() {
        List<String> opcionesVeterinarios = new java.util.ArrayList<>();
        List<Veterinario> listaVeterinarios = veterinarioDAO.findAll();

        for (Veterinario veterinarioActual : listaVeterinarios) {
            opcionesVeterinarios.add(veterinarioActual.getIdVeterinario() + " - " + veterinarioActual.getNombre() + " ("
                    + veterinarioActual.getEspecialidad() + ")");
        }

        return opcionesVeterinarios;
    }

    // Marca una cita como completada
    private void completarCita() {
        int filaSeleccionada = vistaCitas.getFilaSeleccionada();

        if (filaSeleccionada < 0) {
            JOptionPane.showMessageDialog(vistaCitas,
                    "Selecciona una cita para completar",
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String idCitaTexto = vistaCitas.getCitaIdSeleccionada();
        String nombreMascota = vistaCitas.getMascotaSeleccionada();

        if (idCitaTexto == null || nombreMascota == null) {
            JOptionPane.showMessageDialog(vistaCitas,
                    "Error al obtener datos de la cita seleccionada",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int idCita = Integer.parseInt(idCitaTexto);

        boolean confirmacion = vistaCitas.mostrarConfirmacionCompletar(idCita, nombreMascota);

        if (confirmacion) {
            if (citaDAO.updateEstado(idCita, "Completada")) {
                cargarTodasLasCitas(); // Recargar todas las citas
                vistaCitas.mostrarMensaje("Cita con id " + idCita + " marcada como completada",
                        "Cita Completada", JOptionPane.INFORMATION_MESSAGE);
            } else {
                vistaCitas.mostrarMensaje("Error al completar la cita", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Cancela una cita seleccionada
    private void cancelarCita() {
        int filaSeleccionada = vistaCitas.getFilaSeleccionada();

        if (filaSeleccionada < 0) {
            JOptionPane.showMessageDialog(vistaCitas,
                    "Selecciona una cita para cancelar",
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String idCitaTexto = vistaCitas.getCitaIdSeleccionada();
        String nombreMascota = vistaCitas.getMascotaSeleccionada();

        if (idCitaTexto == null || nombreMascota == null) {
            JOptionPane.showMessageDialog(vistaCitas,
                    "Error al obtener datos de la cita seleccionada",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int idCita = Integer.parseInt(idCitaTexto);

        boolean confirmacion = vistaCitas.mostrarConfirmacionCancelar(idCita, nombreMascota);

        if (confirmacion) {
            if (citaDAO.updateEstado(idCita, "Cancelada")) {
                cargarTodasLasCitas(); // Recargar todas las citas
                vistaCitas.mostrarMensaje("Cita con id " + idCita + " cancelada",
                        "Cita Cancelada", JOptionPane.INFORMATION_MESSAGE);
            } else {
                vistaCitas.mostrarMensaje("Error al cancelar la cita", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Abre la ventana para ver la gestion de mascotas
    private void abrirGestionMascotas() {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    MascotasVista vistaMascotas = new MascotasVista();
                    new MascotasControlador(vistaMascotas);
                    vistaMascotas.setVisible(true);
                } catch (Exception excepcion) {
                    System.err.println("Error al abrir la ventana de gestion de mascotas:");
                    excepcion.printStackTrace();
                }
            }
        });
    }

    // Busca un veterinario por su número de ID
    private Veterinario obtenerVeterinarioPorId(int idVeterinario) {
        List<Veterinario> listaVeterinarios = veterinarioDAO.findAll();
        for (Veterinario veterinarioActual : listaVeterinarios) {
            if (veterinarioActual.getIdVeterinario() == idVeterinario) {
                return veterinarioActual;
            }
        }
        return null;
    }
}