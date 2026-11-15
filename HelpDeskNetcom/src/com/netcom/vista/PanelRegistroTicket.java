package com.netcom.vista;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

public class PanelRegistroTicket extends JPanel {

    private static final long serialVersionUID = 1L;

    private BarraSuperior barraSuperior;
    private ventanaPrincipal marcoPadre;

    // 1. Campos de Datos del Solicitante (Se autocompletan con la sesión activa)
    private JTextField txtNombreSolicitante;
    private JComboBox<String> cmbAreaSolicitante;
    private JComboBox<String> cmbCargoSolicitante;

    // 2. Campos de Detalles del Incidente (Los llena el usuario)
    private JComboBox<String> cmbCategoria;
    private JComboBox<String> cmbPrioridad;
    private JTextField txtAsuntoBreve;
    private JTextArea txtDescripcionFallo;

    public PanelRegistroTicket(ventanaPrincipal marcoPadre) {
        this.marcoPadre = marcoPadre;

        // Fondo general Hielo (#F4F9FB)
        setBackground(Color.decode("#F4F9FB"));
        setSize(1100, 680);
        setLayout(null);

        // =========================================================================
        // 1. BARRA SUPERIOR ("Centro de Registro Incidencias")
        // =========================================================================
        barraSuperior = new BarraSuperior(marcoPadre);
        barraSuperior.cambiarTitulo("Centro de Registro Incidencias", "Admin. TI");
        add(barraSuperior);

        // =========================================================================
        // 2. SUB-BARRA DE NAVEGACIÓN (Bandeja de Tickets | Registrar Incidencia [ACTIVA])
        // =========================================================================
        JPanel panelSubMenu = new JPanel();
        panelSubMenu.setBackground(Color.WHITE);
        panelSubMenu.setBounds(0, 60, 1100, 44);
        panelSubMenu.setBorder(new MatteBorder(0, 0, 1, 0, new Color(225, 230, 235)));
        panelSubMenu.setLayout(null);
        add(panelSubMenu);

        // Pestaña 1: Bandeja de Tickets (Regresa a la Pantalla 6)
        JLabel lblTabBandeja = new JLabel("Bandeja de Tickets", SwingConstants.CENTER);
        lblTabBandeja.setForeground(new Color(110, 120, 130));
        lblTabBandeja.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblTabBandeja.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblTabBandeja.setBounds(30, 0, 120, 44);
        panelSubMenu.add(lblTabBandeja);

        lblTabBandeja.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (marcoPadre != null) {
                    marcoPadre.mostrarPantalla("PANTALLA_BANDEJA_TICKETS");
                }
            }
        });

        // Pestaña 2 (ACTIVA): Registrar Incidencia
        JLabel lblTabRegistrar = new JLabel("Registrar Incidencia", SwingConstants.CENTER);
        lblTabRegistrar.setForeground(Color.decode("#0095D9"));
        lblTabRegistrar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblTabRegistrar.setBorder(new MatteBorder(0, 0, 3, 0, Color.decode("#0095D9")));
        lblTabRegistrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblTabRegistrar.setBounds(165, 0, 130, 44);
        panelSubMenu.add(lblTabRegistrar);

        // =========================================================================
        // 3. TARJETA 1: "1. Datos del Solicitante" (Autocompletado por Sesión)
        // =========================================================================
        JPanel cardSolicitante = new JPanel();
        cardSolicitante.setBackground(Color.WHITE);
        cardSolicitante.setBounds(160, 120, 780, 115);
        cardSolicitante.setBorder(new LineBorder(new Color(225, 230, 235), 1, true));
        cardSolicitante.setLayout(null);
        add(cardSolicitante);

        JLabel lblTitulo1 = new JLabel("👤   1. Datos del Solicitante");
        lblTitulo1.setForeground(Color.decode("#2B3990"));
        lblTitulo1.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTitulo1.setBounds(25, 15, 300, 22);
        cardSolicitante.add(lblTitulo1);

        // Campo 1: Nombre Completo
        JLabel lblNombre = new JLabel("Nombre Completo");
        lblNombre.setForeground(new Color(66, 66, 66));
        lblNombre.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblNombre.setBounds(25, 45, 200, 16);
        cardSolicitante.add(lblNombre);

        txtNombreSolicitante = new JTextField();
        txtNombreSolicitante.setEditable(false); // Bloqueado: se llena solo con el usuario activo
        txtNombreSolicitante.setBackground(Color.decode("#F8FAFC"));
        txtNombreSolicitante.setFont(new Font("Segoe UI", Font.BOLD, 12));
        txtNombreSolicitante.setBounds(25, 64, 230, 34);
        txtNombreSolicitante.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(215, 220, 225), 1, true),
                BorderFactory.createEmptyBorder(4, 10, 4, 10)));
        cardSolicitante.add(txtNombreSolicitante);

        // Campo 2: Área / Servicio
        JLabel lblArea = new JLabel("Área / Servicio");
        lblArea.setForeground(new Color(66, 66, 66));
        lblArea.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblArea.setBounds(275, 45, 200, 16);
        cardSolicitante.add(lblArea);

        cmbAreaSolicitante = new JComboBox<String>();
        cmbAreaSolicitante.setModel(new DefaultComboBoxModel<String>(new String[] {
            "-- Seleccione --", "Urgencias", "Consulta Externa", "Laboratorio", "Farmacia", "Administración", "Soporte TI"
        }));
        cmbAreaSolicitante.setEnabled(false); // Seleccionado automáticamente según perfil
        cmbAreaSolicitante.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        cmbAreaSolicitante.setBounds(275, 64, 230, 34);
        cardSolicitante.add(cmbAreaSolicitante);

        // Campo 3: Rol / Cargo
        JLabel lblCargo = new JLabel("Rol / Cargo");
        lblCargo.setForeground(new Color(66, 66, 66));
        lblCargo.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblCargo.setBounds(525, 45, 200, 16);
        cardSolicitante.add(lblCargo);

        cmbCargoSolicitante = new JComboBox<String>();
        cmbCargoSolicitante.setModel(new DefaultComboBoxModel<String>(new String[] {
            "-- Seleccione --", "Médico General", "Enfermería", "Recepción", "Ingeniero de Soporte", "Administrador de TI"
        }));
        cmbCargoSolicitante.setEnabled(false); // Seleccionado automáticamente según perfil
        cmbCargoSolicitante.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        cmbCargoSolicitante.setBounds(525, 64, 230, 34);
        cardSolicitante.add(cmbCargoSolicitante);

        // =========================================================================
        // 4. TARJETA 2: "2. Detalles del Incidente"
        // =========================================================================
        JPanel cardDetalles = new JPanel();
        cardDetalles.setBackground(Color.WHITE);
        cardDetalles.setBounds(160, 250, 780, 310);
        cardDetalles.setBorder(new LineBorder(new Color(225, 230, 235), 1, true));
        cardDetalles.setLayout(null);
        add(cardDetalles);

        JLabel lblTitulo2 = new JLabel("📋   2. Detalles del Incidente");
        lblTitulo2.setForeground(Color.decode("#2B3990"));
        lblTitulo2.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTitulo2.setBounds(25, 15, 300, 22);
        cardDetalles.add(lblTitulo2);

        // Fila 1: Categoría y Prioridad
        JLabel lblCategoria = new JLabel("Categoría");
        lblCategoria.setForeground(new Color(66, 66, 66));
        lblCategoria.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblCategoria.setBounds(25, 48, 250, 16);
        cardDetalles.add(lblCategoria);

        cmbCategoria = new JComboBox<String>();
        cmbCategoria.setModel(new DefaultComboBoxModel<String>(new String[] {
            "Hardware (PC, Impresoras)", "Software (Historia Clínica)", "Redes (Sin Internet)", "Periféricos / Telefonía"
        }));
        cmbCategoria.setBackground(Color.WHITE);
        cmbCategoria.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        cmbCategoria.setBounds(25, 67, 350, 34);
        cardDetalles.add(cmbCategoria);

        JLabel lblPrioridad = new JLabel("Prioridad");
        lblPrioridad.setForeground(new Color(66, 66, 66));
        lblPrioridad.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblPrioridad.setBounds(405, 48, 250, 16);
        cardDetalles.add(lblPrioridad);

        cmbPrioridad = new JComboBox<String>();
        cmbPrioridad.setModel(new DefaultComboBoxModel<String>(new String[] {
            "ALTA (Crítico - Urgente)", "MEDIA (Normal)", "BAJA (Consulta)"
        }));
        cmbPrioridad.setBackground(Color.WHITE);
        cmbPrioridad.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        cmbPrioridad.setBounds(405, 67, 350, 34);
        cardDetalles.add(cmbPrioridad);

        // Fila 2: Asunto Breve
        JLabel lblAsunto = new JLabel("Asunto Breve");
        lblAsunto.setForeground(new Color(66, 66, 66));
        lblAsunto.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblAsunto.setBounds(25, 115, 300, 16);
        cardDetalles.add(lblAsunto);

        txtAsuntoBreve = new JTextField();
        txtAsuntoBreve.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtAsuntoBreve.setToolTipText("Ej: No enciende el computador del consultorio 3");
        txtAsuntoBreve.setBounds(25, 134, 730, 34);
        txtAsuntoBreve.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(215, 220, 225), 1, true),
                BorderFactory.createEmptyBorder(4, 10, 4, 10)));
        cardDetalles.add(txtAsuntoBreve);

        // Fila 3: Descripción Detallada del Fallo
        JLabel lblDescripcion = new JLabel("Descripción Detallada del Fallo");
        lblDescripcion.setForeground(new Color(66, 66, 66));
        lblDescripcion.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblDescripcion.setBounds(25, 182, 300, 16);
        cardDetalles.add(lblDescripcion);

        txtDescripcionFallo = new JTextArea();
        txtDescripcionFallo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtDescripcionFallo.setLineWrap(true);
        txtDescripcionFallo.setWrapStyleWord(true);
        txtDescripcionFallo.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));

        JScrollPane scrollDescripcion = new JScrollPane(txtDescripcionFallo);
        scrollDescripcion.setBounds(25, 202, 730, 88);
        scrollDescripcion.setBorder(new LineBorder(new Color(215, 220, 225), 1, true));
        cardDetalles.add(scrollDescripcion);

        // =========================================================================
        // 5. BOTÓN INFERIOR "✈ Generar Ticket de Soporte" (#0095D9)
        // =========================================================================
        JButton btnGenerarTicket = new JButton("✈  Generar Ticket de Soporte");
        btnGenerarTicket.setBackground(Color.decode("#0095D9"));
        btnGenerarTicket.setForeground(Color.WHITE);
        btnGenerarTicket.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnGenerarTicket.setFocusPainted(false);
        btnGenerarTicket.setBorderPainted(false);
        btnGenerarTicket.setOpaque(true);
        btnGenerarTicket.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnGenerarTicket.setBounds(430, 578, 240, 42);
        add(btnGenerarTicket);

        btnGenerarTicket.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                procesarNuevoTicket();
            }
        });
    }

    /**
     * Autocompleta automáticamente Nombre, Área y Cargo según el usuario que inició sesión (RF-19)
     */
    public void autocompletarSolicitante(String nombreCompleto, String area, String cargo) {
        txtNombreSolicitante.setText(nombreCompleto);
        cmbAreaSolicitante.setSelectedItem(area);
        cmbCargoSolicitante.setSelectedItem(cargo);
    }

    /**
     * Valida los detalles, envía el ticket a la tabla de la Pantalla 6 y prepara el paso a la Pantalla 8
     */
    private void procesarNuevoTicket() {
        String asunto = txtAsuntoBreve.getText().trim();
        String descripcion = txtDescripcionFallo.getText().trim();

        // Validación de campos obligatorios (RF-20)
        if (asunto.isEmpty() || descripcion.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Por favor diligencie el Asunto Breve y la Descripción Detallada del Fallo.",
                    "Campos Obligatorios", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String solicitante = txtNombreSolicitante.getText();
        String area = cmbAreaSolicitante.getSelectedItem().toString();
        String categoriaCompleta = cmbCategoria.getSelectedItem().toString();
        String prioridadCompleta = cmbPrioridad.getSelectedItem().toString();

        // Extraemos la palabra clave corta para la tabla (ej. "Hardware" y "ALTA")
        String categoriaCorta = categoriaCompleta.split(" ")[0];
        String prioridadCorta = prioridadCompleta.split(" ")[0];

        // Fecha actual en formato DD/MM/AAAA (RNF-29)
        String fechaHoy = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        // Limpiamos los campos de incidente para el próximo reporte (RF-22)
        txtAsuntoBreve.setText("");
        txtDescripcionFallo.setText("");
        cmbCategoria.setSelectedIndex(0);
        cmbPrioridad.setSelectedIndex(0);

        // Enviamos el ticket a ventanaPrincipal para que actualice la Pantalla 6 y pase a confirmación
        if (marcoPadre != null) {
            marcoPadre.registrarTicketYMostrarConfirmacion(fechaHoy, solicitante, area, categoriaCorta, prioridadCorta);
        }
    }

    public BarraSuperior getBarraSuperior() {
        return barraSuperior;
    }
}