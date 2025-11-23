package com.pethealth.vista;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.LocalDate;


public class CitasVista extends JFrame {
    private JTable tablaCitas;
    private DefaultTableModel modeloTabla;
    private JButton botonAgregar, botonCompletar, botonCancelar, botonMascotas, botonBuscar;
    private JTextField campoBusqueda;


    // Crea la ventana principal de gestion de citas
    public CitasVista() {
        setTitle("PetHealth - Gestion de citas");
        setSize(1200, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        setMinimumSize(new Dimension(1000, 450));
        inicializarComponentes();
    }


    // Organiza todos los elementos de la ventana
    private void inicializarComponentes() {
        // Panel superior con el boton de agregar y busqueda
        JPanel panelSuperior = new JPanel(new BorderLayout(10, 10));
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


        // Panel izquierdo con boton agregar
        JPanel panelIzquierdo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        botonAgregar = new JButton("Agregar cita");
        panelIzquierdo.add(botonAgregar);


        // Panel derecho con busqueda
        JPanel panelDerecho = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelDerecho.add(new JLabel("Buscar (mascota o dueño):"));
        campoBusqueda = new JTextField(20);
        panelDerecho.add(campoBusqueda);
        botonBuscar = new JButton("Buscar");
        panelDerecho.add(botonBuscar);


        panelSuperior.add(panelIzquierdo, BorderLayout.WEST);
        panelSuperior.add(panelDerecho, BorderLayout.EAST);


        // Prepara la tabla donde se van a ver las citas
        String[] columnas = { "ID", "Mascota", "Especie", "Dueño", "Veterinario", "Fecha y Hora", "Motivo", "Estado",
                "Costo" };
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tablaCitas = new JTable(modeloTabla);
        tablaCitas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaCitas.getTableHeader().setReorderingAllowed(false);


        // Centrar el texto de las celdas
        DefaultTableCellRenderer renderizadorCentrado = new DefaultTableCellRenderer();
        renderizadorCentrado.setHorizontalAlignment(JLabel.CENTER);


        // Aplicar el centrado a las columnas
        for (int indice = 0; indice < tablaCitas.getColumnCount(); indice++) {
            tablaCitas.getColumnModel().getColumn(indice).setCellRenderer(renderizadorCentrado);
        }


        // Define el ancho que tiene cada columna
        tablaCitas.setRowHeight(22);
        tablaCitas.getColumnModel().getColumn(0).setPreferredWidth(50);
        tablaCitas.getColumnModel().getColumn(1).setPreferredWidth(100);
        tablaCitas.getColumnModel().getColumn(2).setPreferredWidth(70);
        tablaCitas.getColumnModel().getColumn(3).setPreferredWidth(100);
        tablaCitas.getColumnModel().getColumn(4).setPreferredWidth(100);
        tablaCitas.getColumnModel().getColumn(5).setPreferredWidth(130);
        tablaCitas.getColumnModel().getColumn(6).setPreferredWidth(180);
        tablaCitas.getColumnModel().getColumn(7).setPreferredWidth(90);
        tablaCitas.getColumnModel().getColumn(8).setPreferredWidth(70);


        JScrollPane panelDesplazamiento = new JScrollPane(tablaCitas);
        panelDesplazamiento.setBorder(BorderFactory.createTitledBorder("Lista de Citas"));
        panelDesplazamiento.setPreferredSize(new Dimension(1150, 350)); // Aumentado el ancho


        // Panel inferior con botones de acciones
        JPanel panelInferior = new JPanel(new BorderLayout(10, 10));
        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));


        botonCompletar = new JButton("Marcar cita como completada");
        botonCompletar.setPreferredSize(new Dimension(220, 30));
        panelBotones.add(botonCompletar);


        botonCancelar = new JButton("Cancelar cita");
        botonCancelar.setPreferredSize(new Dimension(130, 30));
        panelBotones.add(botonCancelar);


        botonMascotas = new JButton("Gestion de Mascotas");
        botonMascotas.setPreferredSize(new Dimension(160, 30));
        panelBotones.add(botonMascotas);


        panelInferior.add(panelBotones, BorderLayout.CENTER);


        // Organiza todos los paneles en la ventana
        setLayout(new BorderLayout(10, 10));
        add(panelSuperior, BorderLayout.NORTH);
        add(panelDesplazamiento, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);
    }


    // Conecta el boton agregar con su accion
    public void setAgregarListener(ActionListener listener) {
        botonAgregar.addActionListener(listener);
    }


    // Conecta el boton completar con su accion
    public void setCompletarListener(ActionListener listener) {
        botonCompletar.addActionListener(listener);
    }


    // Conecta el boton cancelar con su accion
    public void setCancelarListener(ActionListener listener) {
        botonCancelar.addActionListener(listener);
    }


    // Conecta el boton mascotas con su accion
    public void setMascotasListener(ActionListener listener) {
        botonMascotas.addActionListener(listener);
    }


    // Conecta el boton buscar con su accion
    public void setBuscarListener(ActionListener listener) {
        botonBuscar.addActionListener(listener);
    }


    // Obtiene el texto que se ha puesto en el campo de busqueda
    public String getTextoBusqueda() {
        return campoBusqueda.getText().trim();
    }


    // Limpia el campo de busqueda
    public void limpiarBusqueda() {
        campoBusqueda.setText("");
    }


    // Dice que fila de la tabla esta seleccionada
    public int getFilaSeleccionada() {
        return tablaCitas.getSelectedRow();
    }


    // Obtiene el ID de la cita seleccionada
    public String getCitaIdSeleccionada() {
        int fila = getFilaSeleccionada();
        if (fila >= 0) {
            return modeloTabla.getValueAt(fila, 0).toString();
        }
        return null;
    }


    // Obtiene el nombre de la mascota de la cita seleccionada
    public String getMascotaSeleccionada() {
        int fila = getFilaSeleccionada();
        if (fila >= 0) {
            return modeloTabla.getValueAt(fila, 1).toString();
        }
        return null;
    }


    // Agrega una nueva fila a la tabla de citas
    public void agregarCitaATabla(Object[] fila) {
        modeloTabla.addRow(fila);
    }


    // Limpia toda la tabla de citas
    public void limpiarTabla() {
        modeloTabla.setRowCount(0);
    }


    // Pregunta si se quiere completar una cita
    public boolean mostrarConfirmacionCompletar(int idCita, String nombreMascota) {
        int respuesta = JOptionPane.showConfirmDialog(this,
                "¿Quieres marcar la cita como completada?\n" +
                        "Numero de cita: " + idCita + "\nNombre de la mascota: " + nombreMascota,
                "Completacion de cita",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);


        return respuesta == JOptionPane.YES_OPTION;
    }


    // Pregunta si se quiere cancelar una cita
    public boolean mostrarConfirmacionCancelar(int idCita, String nombreMascota) {
        int respuesta = JOptionPane.showConfirmDialog(this,
                "¿Quieres cancelar la cita?\n" +
                        "Numero de cita: " + idCita + "\nMascota: " + nombreMascota,
                "Cancelacion de cita",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);


        return respuesta == JOptionPane.YES_OPTION;
    }


    // Muestra una ventana para llenar los datos de una cita nueva
    public Object[] mostrarDialogoAgregarCita(java.util.List<String> listaMascotas,
            java.util.List<String> listaVeterinarios) {
        JDialog dialogo = new JDialog(this, "Agregar Nueva Cita", true);
        dialogo.setSize(450, 450);
        dialogo.setLocationRelativeTo(this);
        dialogo.setLayout(new BorderLayout(10, 10));


        // Panel con todos los campos del formulario
        JPanel panelFormulario = new JPanel(new GridLayout(7, 2, 10, 10));
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(20, 20, 15, 20));


        // Campos para llenar la informacion de la cita
        panelFormulario.add(new JLabel("Nombre mascota: *"));
        JComboBox<String> comboMascota = new JComboBox<>();
        cargarMascotasEnCombo(comboMascota, listaMascotas);
        panelFormulario.add(comboMascota);


        panelFormulario.add(new JLabel("Veterinario: *"));
        JComboBox<String> comboVeterinario = new JComboBox<>();
        cargarVeterinariosEnCombo(comboVeterinario, listaVeterinarios);
        panelFormulario.add(comboVeterinario);


        panelFormulario.add(new JLabel("Fecha: *"));
        JTextField campoFecha = new JTextField();
        panelFormulario.add(campoFecha);


        panelFormulario.add(new JLabel("Hora: *"));
        JComboBox<String> comboHora = new JComboBox<>(new String[] {
                "08:00", "08:30", "09:00", "09:30", "10:00", "10:30", "11:00",
                "11:30", "12:00", "16:00", "16:30", "17:00"
        });
        panelFormulario.add(comboHora);


        panelFormulario.add(new JLabel("Motivo: *"));
        JTextField campoMotivo = new JTextField();
        panelFormulario.add(campoMotivo);


        panelFormulario.add(new JLabel("Estado:"));
        JComboBox<String> comboEstado = new JComboBox<>(new String[] { "Programada" });
        panelFormulario.add(comboEstado);


        panelFormulario.add(new JLabel("Costo: *"));
        JTextField campoCosto = new JTextField("00.00");
        panelFormulario.add(campoCosto);


        final Object[] datosCita = new Object[6];
        final boolean[] guardadoExitoso = { false };


        // Botones para guardar o cancelar
        JPanel panelBotonesDialogo = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton botonGuardar = new JButton("Guardar Cita");
        JButton botonCancelarDialogo = new JButton("Cancelar");


        panelBotonesDialogo.add(botonGuardar);
        panelBotonesDialogo.add(botonCancelarDialogo);


        // Revisa que todos los campos obligatorios estan llenos
        botonGuardar.addActionListener(evento -> {
            if (comboMascota.getSelectedItem() == null ||
                    comboVeterinario.getSelectedItem() == null ||
                    campoFecha.getText().trim().isEmpty() ||
                    campoMotivo.getText().trim().isEmpty() ||
                    campoCosto.getText().trim().isEmpty()) {


                JOptionPane.showMessageDialog(dialogo,
                        "Completa todos los campos obligatorios",
                        "Error de validacion", JOptionPane.ERROR_MESSAGE);
                return;
            }


            // Revisa que el costo sea un numero valido
            try {
                Double.parseDouble(campoCosto.getText().trim());
            } catch (NumberFormatException excepcion) {
                JOptionPane.showMessageDialog(dialogo,
                        "El numero del costo debe ser valido",
                        "Error de formato", JOptionPane.ERROR_MESSAGE);
                return;
            }


            // Revisa que la fecha tenga el formato correcto
            try {
                LocalDate.parse(campoFecha.getText().trim());
            } catch (Exception excepcion) {
                JOptionPane.showMessageDialog(dialogo,
                        "El formato de fecha es invalido",
                        "Error de formato", JOptionPane.ERROR_MESSAGE);
                return;
            }


            // Guarda todos los datos ingresados
            datosCita[0] = comboMascota.getSelectedItem().toString();
            datosCita[1] = comboVeterinario.getSelectedItem().toString();
            datosCita[2] = campoFecha.getText().trim() + " " + comboHora.getSelectedItem().toString();
            datosCita[3] = campoMotivo.getText().trim();
            datosCita[4] = comboEstado.getSelectedItem().toString();
            datosCita[5] = Double.parseDouble(campoCosto.getText().trim());


            guardadoExitoso[0] = true;
            dialogo.dispose();
        });


        botonCancelarDialogo.addActionListener(evento -> {
            guardadoExitoso[0] = false;
            dialogo.dispose();
        });


        dialogo.add(panelFormulario, BorderLayout.CENTER);
        dialogo.add(panelBotonesDialogo, BorderLayout.SOUTH);
        dialogo.setVisible(true);


        return guardadoExitoso[0] ? datosCita : null;
    }


    // Llena el ComboBox con la lista de mascotas
    public void cargarMascotasEnCombo(JComboBox<String> combo, java.util.List<String> listaMascotas) {
        combo.removeAllItems();
        for (String mascota : listaMascotas) {
            combo.addItem(mascota);
        }
    }


    // Llena el ComboBox con la lista de veterinarios
    public void cargarVeterinariosEnCombo(JComboBox<String> combo, java.util.List<String> listaVeterinarios) {
        combo.removeAllItems();
        for (String veterinario : listaVeterinarios) {
            combo.addItem(veterinario);
        }
    }


    // Muestra un mensaje al usuario
    public void mostrarMensaje(String mensaje, String titulo, int tipo) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, tipo);
    }
}