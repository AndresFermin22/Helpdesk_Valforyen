package com.netcom.vista;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

public class PanelDetallesTicket extends JPanel {

    private static final long serialVersionUID = 1L;

    private BarraSuperior barraSuperior;
    private ventanaPrincipal marcoPadre;

    // Etiquetas dinámicas del encabezado del ticket
    private JLabel lblIdTicket;
    private JLabel lblBadgeEstado;
    private JLabel lblBadgePrioridad;
    private JLabel lblAsuntoTicket;
    private JLabel lblFechaReporte;
    private JButton btnMarcarResuelto;

    // Contenido izquierdo (Descripción e Historial de Comentarios)
    private JTextArea txtDescripcionLectura;
    private JTextArea txtHistorialComentarios;
    private JTextArea txtNuevoComentario;

    // Tarjeta lateral derecha (Datos del Solicitante)
    private JLabel lblInicialSolicitante;
    private JLabel lblNombreSolicitante;
    private JLabel lblAreaSolicitante;
    private JLabel lblCategoriaTicket;
    private JLabel lblAsignadoA;

    // Guardamos el ID actual para actualizar la tabla al resolver
    private String idTicketActual = "TK-101";

    public PanelDetallesTicket(ventanaPrincipal marcoPadre) {
        this.marcoPadre = marcoPadre;

        setBackground(Color.decode("#F4F9FB"));
        setSize(1100, 680);
        setLayout(null);

        // =========================================================================
        // 1. BARRA SUPERIOR CON TÍTULO "Detalles Tickets"
        // =========================================================================
        barraSuperior = new BarraSuperior(marcoPadre);
        barraSuperior.cambiarTitulo("Detalles Tickets", "Admin. TI");
        add(barraSuperior);

        // Enlace superior "← Volver a la Bandeja"
        JLabel lblVolverBandeja = new JLabel("← Volver a la Bandeja");
        lblVolverBandeja.setForeground(new Color(100, 116, 139));
        lblVolverBandeja.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblVolverBandeja.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblVolverBandeja.setBounds(155, 78, 160, 22);
        add(lblVolverBandeja);

        lblVolverBandeja.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (marcoPadre != null) {
                    marcoPadre.volverPantallaAnterior();
                }
            }
        });

        // =========================================================================
        // 2. CONTENEDOR PRINCIPAL DEL EXPEDIENTE DEL TICKET
        // =========================================================================
        JPanel panelContenedor = new JPanel();
        panelContenedor.setBackground(Color.WHITE);
        panelContenedor.setBounds(155, 105, 790, 515);
        panelContenedor.setBorder(new LineBorder(new Color(225, 230, 235), 1, true));
        panelContenedor.setLayout(null);
        add(panelContenedor);

        // -------------------------------------------------------------------------
        // A. ENCABEZADO DEL TICKET (ID, Estado, Prioridad, Asunto y Botón Resolver)
        // -------------------------------------------------------------------------
        JPanel panelEncabezadoTicket = new JPanel();
        panelEncabezadoTicket.setBackground(Color.decode("#FCFDFE"));
        panelEncabezadoTicket.setBounds(1, 1, 788, 108);
        panelEncabezadoTicket.setBorder(new MatteBorder(0, 0, 1, 0, new Color(235, 240, 245)));
        panelEncabezadoTicket.setLayout(null);
        panelContenedor.add(panelEncabezadoTicket);

        lblIdTicket = new JLabel("TK-101");
        lblIdTicket.setForeground(new Color(25, 25, 25));
        lblIdTicket.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblIdTicket.setBounds(25, 16, 95, 28);
        panelEncabezadoTicket.add(lblIdTicket);

        lblBadgeEstado = new JLabel("ABIERTO", SwingConstants.CENTER);
        lblBadgeEstado.setOpaque(true);
        lblBadgeEstado.setBackground(Color.decode("#DBEAFE"));
        lblBadgeEstado.setForeground(Color.decode("#2563EB"));
        lblBadgeEstado.setFont(new Font("Segoe UI", Font.BOLD, 10));
        lblBadgeEstado.setBounds(125, 20, 75, 22);
        panelEncabezadoTicket.add(lblBadgeEstado);

        lblBadgePrioridad = new JLabel("PRIORIDAD ALTA", SwingConstants.CENTER);
        lblBadgePrioridad.setOpaque(true);
        lblBadgePrioridad.setBackground(Color.decode("#FEE2E2"));
        lblBadgePrioridad.setForeground(Color.decode("#DC2626"));
        lblBadgePrioridad.setFont(new Font("Segoe UI", Font.BOLD, 10));
        lblBadgePrioridad.setBounds(210, 20, 110, 22);
        panelEncabezadoTicket.add(lblBadgePrioridad);

        lblAsuntoTicket = new JLabel("No enciende el computador del consultorio 3");
        lblAsuntoTicket.setForeground(new Color(45, 55, 72));
        lblAsuntoTicket.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblAsuntoTicket.setBounds(25, 52, 500, 22);
        panelEncabezadoTicket.add(lblAsuntoTicket);

        lblFechaReporte = new JLabel("🕒 Reportado el 25/09/2026 a las 08:30 AM");
        lblFechaReporte.setForeground(new Color(130, 140, 150));
        lblFechaReporte.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblFechaReporte.setBounds(25, 76, 350, 18);
        panelEncabezadoTicket.add(lblFechaReporte);

        // Único botón "✔ Marcar como Resuelto" (#8BC53F)
        btnMarcarResuelto = new JButton("✔  Marcar como Resuelto");
        btnMarcarResuelto.setBackground(Color.decode("#8BC53F"));
        btnMarcarResuelto.setForeground(Color.WHITE);
        btnMarcarResuelto.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnMarcarResuelto.setFocusPainted(false);
        btnMarcarResuelto.setBorderPainted(false);
        btnMarcarResuelto.setOpaque(true);
        btnMarcarResuelto.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnMarcarResuelto.setBounds(585, 18, 178, 36);
        panelEncabezadoTicket.add(btnMarcarResuelto);

        // -------------------------------------------------------------------------
        // B. COLUMNA IZQUIERDA: DESCRIPCIÓN DETALLADA Y ACTUALIZACIONES
        // -------------------------------------------------------------------------
        JLabel lblTitDescripcion = new JLabel("DESCRIPCIÓN DETALLADA");
        lblTitDescripcion.setForeground(Color.decode("#2B3990"));
        lblTitDescripcion.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblTitDescripcion.setBounds(25, 125, 250, 16);
        panelContenedor.add(lblTitDescripcion);

        txtDescripcionLectura = new JTextArea();
        txtDescripcionLectura.setEditable(false);
        txtDescripcionLectura.setLineWrap(true);
        txtDescripcionLectura.setWrapStyleWord(true);
        txtDescripcionLectura.setBackground(Color.decode("#F8FAFC"));
        txtDescripcionLectura.setForeground(new Color(60, 70, 80));
        txtDescripcionLectura.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtDescripcionLectura.setText("Al intentar encender el equipo esta mañana, la pantalla se queda completamente en negro. La torre hace ruido de ventiladores pero no da imagen. Ya revisé los cables de corriente y están bien conectados. Necesito soporte urgente para poder iniciar las consultas médicas.");
        txtDescripcionLectura.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(226, 232, 240), 1, true),
                BorderFactory.createEmptyBorder(10, 12, 10, 12)));
        txtDescripcionLectura.setBounds(25, 146, 495, 95);
        panelContenedor.add(txtDescripcionLectura);

        // Sección ACTUALIZACIONES (Historial + Caja para nuevo comentario)
        JLabel lblTitActualizaciones = new JLabel("ACTUALIZACIONES");
        lblTitActualizaciones.setForeground(Color.decode("#2B3990"));
        lblTitActualizaciones.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblTitActualizaciones.setBounds(25, 255, 250, 16);
        panelContenedor.add(lblTitActualizaciones);

        // Historial donde van apareciendo los comentarios añadidos
        txtHistorialComentarios = new JTextArea();
        txtHistorialComentarios.setEditable(false);
        txtHistorialComentarios.setLineWrap(true);
        txtHistorialComentarios.setWrapStyleWord(true);
        txtHistorialComentarios.setBackground(Color.decode("#F8FAFC"));
        txtHistorialComentarios.setForeground(new Color(45, 55, 72));
        txtHistorialComentarios.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        txtHistorialComentarios.setText("Sin comentarios previos. Escriba una actualización abajo.");
        txtHistorialComentarios.setBorder(BorderFactory.createEmptyBorder(6, 10, 6, 10));

        JScrollPane scrollHistorial = new JScrollPane(txtHistorialComentarios);
        scrollHistorial.setBounds(25, 275, 495, 75);
        scrollHistorial.setBorder(new LineBorder(new Color(226, 232, 240), 1, true));
        panelContenedor.add(scrollHistorial);

        // Caja de texto para escribir el nuevo comentario
        txtNuevoComentario = new JTextArea();
        txtNuevoComentario.setLineWrap(true);
        txtNuevoComentario.setWrapStyleWord(true);
        txtNuevoComentario.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtNuevoComentario.setToolTipText("Escriba una respuesta o nota interna...");
        txtNuevoComentario.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));

        JScrollPane scrollNuevoComentario = new JScrollPane(txtNuevoComentario);
        scrollNuevoComentario.setBounds(25, 358, 495, 82);
        scrollNuevoComentario.setBorder(new LineBorder(new Color(210, 215, 220), 1, true));
        panelContenedor.add(scrollNuevoComentario);

        // Botón "✈ Añadir Comentario" (#004A8F)
        JButton btnAnadirComentario = new JButton("✈  Añadir Comentario");
        btnAnadirComentario.setBackground(Color.decode("#004A8F"));
        btnAnadirComentario.setForeground(Color.WHITE);
        btnAnadirComentario.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnAnadirComentario.setFocusPainted(false);
        btnAnadirComentario.setBorderPainted(false);
        btnAnadirComentario.setOpaque(true);
        btnAnadirComentario.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAnadirComentario.setBounds(355, 452, 165, 36);
        panelContenedor.add(btnAnadirComentario);

        // -------------------------------------------------------------------------
        // C. TARJETA LATERAL DERECHA (SOLICITANTE, ÁREA, CATEGORÍA, ASIGNADO A)
        // -------------------------------------------------------------------------
        JPanel panelLateral = new JPanel();
        panelLateral.setBackground(Color.decode("#F8FAFC"));
        panelLateral.setBounds(545, 146, 220, 265);
        panelLateral.setBorder(new LineBorder(new Color(226, 232, 240), 1, true));
        panelLateral.setLayout(null);
        panelContenedor.add(panelLateral);

        JLabel lblTitSolicitante = new JLabel("SOLICITANTE");
        lblTitSolicitante.setForeground(new Color(148, 163, 184));
        lblTitSolicitante.setFont(new Font("Segoe UI", Font.BOLD, 10));
        lblTitSolicitante.setBounds(18, 18, 150, 14);
        panelLateral.add(lblTitSolicitante);

        lblInicialSolicitante = new JLabel("F", SwingConstants.CENTER);
        lblInicialSolicitante.setOpaque(true);
        lblInicialSolicitante.setBackground(Color.decode("#DBEAFE"));
        lblInicialSolicitante.setForeground(Color.decode("#2563EB"));
        lblInicialSolicitante.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblInicialSolicitante.setBounds(18, 38, 28, 28);
        panelLateral.add(lblInicialSolicitante);

        lblNombreSolicitante = new JLabel("Fermin López");
        lblNombreSolicitante.setForeground(new Color(30, 41, 59));
        lblNombreSolicitante.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblNombreSolicitante.setBounds(54, 41, 150, 22);
        panelLateral.add(lblNombreSolicitante);

        JLabel lblTitArea = new JLabel("ÁREA / SERVICIO");
        lblTitArea.setForeground(new Color(148, 163, 184));
        lblTitArea.setFont(new Font("Segoe UI", Font.BOLD, 10));
        lblTitArea.setBounds(18, 82, 150, 14);
        panelLateral.add(lblTitArea);

        lblAreaSolicitante = new JLabel("📍 Urgencias");
        lblAreaSolicitante.setForeground(new Color(30, 41, 59));
        lblAreaSolicitante.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblAreaSolicitante.setBounds(18, 100, 180, 20);
        panelLateral.add(lblAreaSolicitante);

        JLabel lblTitCategoria = new JLabel("CATEGORÍA");
        lblTitCategoria.setForeground(new Color(148, 163, 184));
        lblTitCategoria.setFont(new Font("Segoe UI", Font.BOLD, 10));
        lblTitCategoria.setBounds(18, 138, 150, 14);
        panelLateral.add(lblTitCategoria);

        lblCategoriaTicket = new JLabel("Hardware (PC)");
        lblCategoriaTicket.setForeground(new Color(30, 41, 59));
        lblCategoriaTicket.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblCategoriaTicket.setBounds(18, 156, 180, 20);
        panelLateral.add(lblCategoriaTicket);

        JLabel lblTitAsignado = new JLabel("ASIGNADO A");
        lblTitAsignado.setForeground(new Color(148, 163, 184));
        lblTitAsignado.setFont(new Font("Segoe UI", Font.BOLD, 10));
        lblTitAsignado.setBounds(18, 194, 150, 14);
        panelLateral.add(lblTitAsignado);

        lblAsignadoA = new JLabel("Sin asignar");
        lblAsignadoA.setForeground(new Color(100, 116, 139));
        lblAsignadoA.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lblAsignadoA.setBounds(18, 212, 180, 20);
        panelLateral.add(lblAsignadoA);

        // =========================================================================
        // 3. EVENTOS DE LA PANTALLA 9 (AÑADIR COMENTARIO Y MARCAR RESUELTO)
        // =========================================================================

        // Evento: Añadir Comentario y actualizar el historial en vivo (RF-26 y RF-35)
        btnAnadirComentario.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                agregarComentarioAlHistorial();
            }
        });

        // Evento: Único botón "Marcar como Resuelto" con confirmación (RF-36)
        btnMarcarResuelto.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                confirmarYResolverTicket();
            }
        });
    }

    private void agregarComentarioAlHistorial() {
        String comentario = txtNuevoComentario.getText().trim();
        if (comentario.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Por favor escriba un comentario o actualización antes de enviarlo.",
                    "Comentario Vacío", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String horaActual = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a"));
        String autor = (marcoPadre != null) ? marcoPadre.getNombreSesionActiva() : "Usuario";
        String nuevaLinea = "[" + horaActual + "] " + autor + ": " + comentario;

        if (txtHistorialComentarios.getText().startsWith("Sin comentarios")) {
            txtHistorialComentarios.setText(nuevaLinea);
        } else {
            txtHistorialComentarios.append("\n" + nuevaLinea);
        }

        txtNuevoComentario.setText("");
    }

    private void confirmarYResolverTicket() {
        // Si escribió la solución en la caja de comentario, la agregamos primero
        String textoEnCaja = txtNuevoComentario.getText().trim();

        int respuesta = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de que desea confirmar y marcar el ticket " + idTicketActual + " como RESUELTO?",
                "Confirmar Resolución de Ticket", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (respuesta == JOptionPane.YES_OPTION) {
            if (!textoEnCaja.isEmpty()) {
                agregarComentarioAlHistorial();
            } else if (txtHistorialComentarios.getText().startsWith("Sin comentarios")) {
                // RF-36: Exige que haya al menos un comentario de solución
                String solucion = JOptionPane.showInputDialog(this,
                        "Ingrese brevemente la solución aplicada para cerrar el ticket " + idTicketActual + " (RF-36):",
                        "Documentar Solución", JOptionPane.PLAIN_MESSAGE);
                if (solucion == null || solucion.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this,
                            "Debe registrar la solución aplicada para marcar el ticket como Resuelto.",
                            "Solución Requerida", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                txtNuevoComentario.setText("SOLUCIÓN: " + solucion.trim());
                agregarComentarioAlHistorial();
            }

            // Cambiamos la etiqueta visual de esta pantalla a RESUELTO en verde
            actualizarEtiquetaEstado("RESUELTO");
            btnMarcarResuelto.setEnabled(false);

            // Actualizamos el estado también en las tablas de las bandejas (Pantalla 6 y Pantalla 11)
            if (marcoPadre != null) {
                marcoPadre.sincronizarTicketResueltoEnTablas(idTicketActual);
            }

            JOptionPane.showMessageDialog(this,
                    "¡El ticket " + idTicketActual + " ha sido marcado como RESUELTO exitosamente!",
                    "Ticket Resuelto", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Carga dinámicamente todos los datos del ticket seleccionado en la Bandeja (Pantalla 6 o 11)
     */
    public void cargarDatosTicket(String idTicket, String fecha, String solicitante, String area,
            String categoria, String prioridad, String estado, String asignadoA) {

        this.idTicketActual = idTicket;
        lblIdTicket.setText(idTicket);
        lblFechaReporte.setText("🕒 Reportado el " + fecha);
        lblNombreSolicitante.setText(solicitante);
        lblInicialSolicitante.setText(solicitante.substring(0, 1).toUpperCase());
        lblAreaSolicitante.setText("📍 " + area);
        lblCategoriaTicket.setText(categoria);
        lblAsignadoA.setText(asignadoA);

        // Configuramos el color de la Prioridad
        lblBadgePrioridad.setText("PRIORIDAD " + prioridad.toUpperCase());
        if (prioridad.equalsIgnoreCase("ALTA") || prioridad.equalsIgnoreCase("CRÍTICA")) {
            lblBadgePrioridad.setBackground(Color.decode("#FEE2E2"));
            lblBadgePrioridad.setForeground(Color.decode("#DC2626"));
        } else if (prioridad.equalsIgnoreCase("MEDIA")) {
            lblBadgePrioridad.setBackground(Color.decode("#FFEDD5"));
            lblBadgePrioridad.setForeground(Color.decode("#D97706"));
        } else {
            lblBadgePrioridad.setBackground(Color.decode("#DCFCE7"));
            lblBadgePrioridad.setForeground(Color.decode("#16A34A"));
        }

        // Configuramos el color del Estado y si el botón Resolver está activo
        actualizarEtiquetaEstado(estado);
        btnMarcarResuelto.setEnabled(!estado.equalsIgnoreCase("RESUELTO"));

        txtHistorialComentarios.setText("Sin comentarios previos. Escriba una actualización abajo.");
        txtNuevoComentario.setText("");
    }

    private void actualizarEtiquetaEstado(String estado) {
        lblBadgeEstado.setText(estado.toUpperCase());
        if (estado.equalsIgnoreCase("RESUELTO")) {
            lblBadgeEstado.setBackground(Color.decode("#DCFCE7"));
            lblBadgeEstado.setForeground(Color.decode("#16A34A"));
        } else if (estado.equalsIgnoreCase("EN PROCESO")) {
            lblBadgeEstado.setBackground(Color.decode("#F3E8FF"));
            lblBadgeEstado.setForeground(Color.decode("#7E22CE"));
        } else {
            lblBadgeEstado.setBackground(Color.decode("#DBEAFE"));
            lblBadgeEstado.setForeground(Color.decode("#2563EB"));
        }
    }

    public BarraSuperior getBarraSuperior() {
        return barraSuperior;
    }
}