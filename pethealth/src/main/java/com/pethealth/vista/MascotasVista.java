package com.pethealth.vista;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;


public class MascotasVista extends JFrame {
    private JTable tablaMascotas;
    private DefaultTableModel modeloTabla;


    // Crea la ventana para ver las mascotas
    public MascotasVista() {
        setTitle("PetHealth - Gestión de mascotas");
        setSize(800, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        setMinimumSize(new Dimension(700, 250));
        inicializarComponentes();
    }


    // Organiza todos los elementos de la ventana
    private void inicializarComponentes() {
        // Prepara la tabla donde se veran las mascotas
        String[] columnas = { "ID", "Nombre", "Especie", "Raza", "Fecha Nacimiento", "Peso", "Color", "Dueño" };
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };


        tablaMascotas = new JTable(modeloTabla);
        tablaMascotas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaMascotas.getTableHeader().setReorderingAllowed(false);


        // Centra el texto en todas las celdas
        DefaultTableCellRenderer renderizadorCentrado = new DefaultTableCellRenderer();
        renderizadorCentrado.setHorizontalAlignment(JLabel.CENTER);


        // Aplicar el centrado a todas las columnas
        for (int indice = 0; indice < tablaMascotas.getColumnCount(); indice++) {
            tablaMascotas.getColumnModel().getColumn(indice).setCellRenderer(renderizadorCentrado);
        }


        // Define el ancho que va a tener cada columna
        tablaMascotas.setRowHeight(22);
        tablaMascotas.getColumnModel().getColumn(0).setPreferredWidth(40);
        tablaMascotas.getColumnModel().getColumn(1).setPreferredWidth(70);
        tablaMascotas.getColumnModel().getColumn(2).setPreferredWidth(60);
        tablaMascotas.getColumnModel().getColumn(3).setPreferredWidth(70);
        tablaMascotas.getColumnModel().getColumn(4).setPreferredWidth(90);
        tablaMascotas.getColumnModel().getColumn(5).setPreferredWidth(50);
        tablaMascotas.getColumnModel().getColumn(6).setPreferredWidth(60);
        tablaMascotas.getColumnModel().getColumn(7).setPreferredWidth(80);


        JScrollPane panelDesplazamiento = new JScrollPane(tablaMascotas);
        panelDesplazamiento.setBorder(BorderFactory.createTitledBorder("Lista de mascotas"));
        panelDesplazamiento.setPreferredSize(new Dimension(750, 250));


        // Muestra la tabla
        setLayout(new BorderLayout());
        add(panelDesplazamiento, BorderLayout.CENTER);
    }


    // Agrega una nueva fila a la tabla de mascotas
    public void agregarMascotaATabla(Object[] fila) {
        modeloTabla.addRow(fila);
    }


    // Limpia toda la tabla de mascotas
    public void limpiarTabla() {
        modeloTabla.setRowCount(0);
    }


    // Muestra un mensaje al usuario
    public void mostrarMensaje(String mensaje, String titulo, int tipo) {
        switch (tipo) {
            case 1:
                JOptionPane.showMessageDialog(this, mensaje, titulo, JOptionPane.ERROR_MESSAGE);
                break;
            case 2:
                JOptionPane.showMessageDialog(this, mensaje, titulo, JOptionPane.INFORMATION_MESSAGE);
                break;
            case 3:
                JOptionPane.showMessageDialog(this, mensaje, titulo, JOptionPane.WARNING_MESSAGE);
                break;
            default:
                JOptionPane.showMessageDialog(this, mensaje, titulo, JOptionPane.PLAIN_MESSAGE);
        }
    }
}