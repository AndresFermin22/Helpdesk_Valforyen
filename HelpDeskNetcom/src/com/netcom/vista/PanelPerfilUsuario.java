package com.netcom.vista;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class PanelPerfilUsuario extends JPanel {

    private static final long serialVersionUID = 1L;

    private BarraSuperior barraSuperior;
    private ventanaPrincipal marcoPadre;

    // Etiquetas dinámicas de la tarjeta izquierda (Datos personales)
    private JLabel lblNombreCompleto;
    private JLabel lblCargo;
    private JLabel lblValorCedula;
    private JLabel lblValorUsuarioRed;
    private JLabel lblValorArea;

    // Etiquetas dinámicas de los 3 contadores de solicitudes
    private JLabel lblNumAbiertos;
    private JLabel lblNumEnProceso;
    private JLabel lblNumCerrados;

    public PanelPerfilUsuario(ventanaPrincipal marcoPadre) {
        this.marcoPadre = marcoPadre;

        // 1. Fondo general Hielo (#F4F9FB)
        setBackground(Color.decode("#F4F9FB"));
        setSize(1100, 680);
        setLayout(null);

        // =========================================================================
        // 2. BARRA SUPERIOR ("Centro de Perfil")
        // =========================================================================
        barraSuperior = new BarraSuperior(marcoPadre);
        barraSuperior.cambiarTitulo("Centro de Perfil", "Admin. TI");
        add(barraSuperior);

        // Enlace rápido para regresar a la bandeja de tickets
        JLabel lblVolver = new JLabel("← Volver a mi Bandeja");
        lblVolver.setForeground(new Color(100, 116, 139));
        lblVolver.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblVolver.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblVolver.setBounds(150, 75, 160, 20);
        add(lblVolver);

        lblVolver.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (marcoPadre != null) {
                    marcoPadre.volverPantallaInicio();
                }
            }
        });

        // =========================================================================
        // 3. TARJETA IZQUIERDA: FICHA DE DATOS DEL COLABORADOR (RF-18)
        // =========================================================================
        JPanel cardPerfil = new JPanel();
        cardPerfil.setBackground(Color.WHITE);
        cardPerfil.setBounds(150, 102, 235, 435);
        cardPerfil.setBorder(new LineBorder(new Color(225, 230, 235), 1, true));
        cardPerfil.setLayout(null);
        add(cardPerfil);

        // Avatar circular azul claro con borde cyan
        JLabel lblAvatar = new JLabel("👨‍⚕️", SwingConstants.CENTER);
        lblAvatar.setOpaque(true);
        lblAvatar.setBackground(Color.decode("#E0F2FE"));
        lblAvatar.setForeground(Color.decode("#2B3990"));
        lblAvatar.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 42));
        lblAvatar.setBounds(68, 30, 100, 100);
        lblAvatar.setBorder(new LineBorder(Color.decode("#38BDF8"), 2, true));
        cardPerfil.add(lblAvatar);

        // Nombre Completo y Cargo
        lblNombreCompleto = new JLabel("Fermin López", SwingConstants.CENTER);
        lblNombreCompleto.setForeground(new Color(25, 25, 25));
        lblNombreCompleto.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblNombreCompleto.setBounds(15, 145, 205, 26);
        cardPerfil.add(lblNombreCompleto);

        lblCargo = new JLabel("Médico General", SwingConstants.CENTER);
        lblCargo.setForeground(new Color(115, 125, 135));
        lblCargo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblCargo.setBounds(15, 172, 205, 18);
        cardPerfil.add(lblCargo);

        // Línea divisoria
        JSeparator separador = new JSeparator();
        separador.setForeground(new Color(235, 240, 245));
        separador.setBounds(25, 208, 185, 2);
        cardPerfil.add(separador);

        // Dato 1: CÉDULA DE CIUDADANÍA
        JLabel lblTitCedula = new JLabel("CÉDULA DE CIUDADANÍA");
        lblTitCedula.setForeground(new Color(148, 163, 184));
        lblTitCedula.setFont(new Font("Segoe UI", Font.BOLD, 9));
        lblTitCedula.setBounds(25, 228, 185, 14);
        cardPerfil.add(lblTitCedula);

        lblValorCedula = new JLabel("🪪  1.098.765.432");
        lblValorCedula.setForeground(new Color(30, 41, 59));
        lblValorCedula.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblValorCedula.setBounds(25, 245, 185, 20);
        cardPerfil.add(lblValorCedula);

        // Dato 2: USUARIO DE RED
        JLabel lblTitUsuario = new JLabel("USUARIO DE RED");
        lblTitUsuario.setForeground(new Color(148, 163, 184));
        lblTitUsuario.setFont(new Font("Segoe UI", Font.BOLD, 9));
        lblTitUsuario.setBounds(25, 285, 185, 14);
        cardPerfil.add(lblTitUsuario);

        lblValorUsuarioRed = new JLabel("     f.lopez");
        lblValorUsuarioRed.setForeground(new Color(30, 41, 59));
        lblValorUsuarioRed.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblValorUsuarioRed.setBounds(25, 302, 185, 20);
        cardPerfil.add(lblValorUsuarioRed);

        // Dato 3: ÁREA / SERVICIO
        JLabel lblTitArea = new JLabel("ÁREA / SERVICIO");
        lblTitArea.setForeground(new Color(148, 163, 184));
        lblTitArea.setFont(new Font("Segoe UI", Font.BOLD, 9));
        lblTitArea.setBounds(25, 342, 185, 14);
        cardPerfil.add(lblTitArea);

        lblValorArea = new JLabel("🏥  Urgencias");
        lblValorArea.setForeground(new Color(30, 41, 59));
        lblValorArea.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblValorArea.setBounds(25, 359, 185, 20);
        cardPerfil.add(lblValorArea);

        // =========================================================================
        // 4. TARJETA DERECHA SUPERIOR: "Resumen de mis Solicitudes"
        // =========================================================================
        JPanel cardResumen = new JPanel();
        cardResumen.setBackground(Color.WHITE);
        cardResumen.setBounds(410, 102, 540, 210);
        cardResumen.setBorder(new LineBorder(new Color(225, 230, 235), 1, true));
        cardResumen.setLayout(null);
        add(cardResumen);

        JLabel lblTituloResumen = new JLabel("◔   Resumen de mis Solicitudes");
        lblTituloResumen.setForeground(new Color(30, 41, 59));
        lblTituloResumen.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblTituloResumen.setBounds(28, 24, 300, 24);
        cardResumen.add(lblTituloResumen);

        // Recuadro 1: ABIERTOS (Azul #EFF6FF / #1D4ED8)
        JPanel boxAbiertos = new JPanel();
        boxAbiertos.setBackground(Color.decode("#EFF6FF"));
        boxAbiertos.setBorder(new LineBorder(Color.decode("#DBEAFE"), 1, true));
        boxAbiertos.setBounds(28, 68, 148, 110);
        boxAbiertos.setLayout(null);
        cardResumen.add(boxAbiertos);

        lblNumAbiertos = new JLabel("1", SwingConstants.CENTER);
        lblNumAbiertos.setForeground(Color.decode("#004A8F"));
        lblNumAbiertos.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblNumAbiertos.setBounds(10, 22, 128, 36);
        boxAbiertos.add(lblNumAbiertos);

        JLabel lblTxtAbiertos = new JLabel("ABIERTOS", SwingConstants.CENTER);
        lblTxtAbiertos.setForeground(Color.decode("#004A8F"));
        lblTxtAbiertos.setFont(new Font("Segoe UI", Font.BOLD, 10));
        lblTxtAbiertos.setBounds(10, 62, 128, 18);
        boxAbiertos.add(lblTxtAbiertos);

        // Recuadro 2: EN PROCESO (Ámbar/Amarillo suave #FEFCE8 / #B45309)
        JPanel boxEnProceso = new JPanel();
        boxEnProceso.setBackground(Color.decode("#FEFCE8"));
        boxEnProceso.setBorder(new LineBorder(Color.decode("#FEF08A"), 1, true));
        boxEnProceso.setBounds(196, 68, 148, 110);
        boxEnProceso.setLayout(null);
        cardResumen.add(boxEnProceso);

        lblNumEnProceso = new JLabel("1", SwingConstants.CENTER);
        lblNumEnProceso.setForeground(Color.decode("#B45309"));
        lblNumEnProceso.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblNumEnProceso.setBounds(10, 22, 128, 36);
        boxEnProceso.add(lblNumEnProceso);

        JLabel lblTxtEnProceso = new JLabel("EN PROCESO", SwingConstants.CENTER);
        lblTxtEnProceso.setForeground(Color.decode("#B45309"));
        lblTxtEnProceso.setFont(new Font("Segoe UI", Font.BOLD, 10));
        lblTxtEnProceso.setBounds(10, 62, 128, 18);
        boxEnProceso.add(lblTxtEnProceso);

        // Recuadro 3: CERRADOS / RESUELTOS (Verde suave #F0FDF4 / #15803D)
        JPanel boxCerrados = new JPanel();
        boxCerrados.setBackground(Color.decode("#F0FDF4"));
        boxCerrados.setBorder(new LineBorder(Color.decode("#DCFCE7"), 1, true));
        boxCerrados.setBounds(364, 68, 148, 110);
        boxCerrados.setLayout(null);
        cardResumen.add(boxCerrados);

        lblNumCerrados = new JLabel("2", SwingConstants.CENTER);
        lblNumCerrados.setForeground(Color.decode("#16A34A"));
        lblNumCerrados.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblNumCerrados.setBounds(10, 22, 128, 36);
        boxCerrados.add(lblNumCerrados);

        JLabel lblTxtCerrados = new JLabel("CERRADOS", SwingConstants.CENTER);
        lblTxtCerrados.setForeground(Color.decode("#16A34A"));
        lblTxtCerrados.setFont(new Font("Segoe UI", Font.BOLD, 10));
        lblTxtCerrados.setBounds(10, 62, 128, 18);
        boxCerrados.add(lblTxtCerrados);

        // =========================================================================
        // 5. TARJETA DERECHA INFERIOR: "¿Necesita reportar un nuevo fallo?"
        // =========================================================================
        JPanel cardNuevoTicket = new JPanel();
        cardNuevoTicket.setBackground(Color.WHITE);
        cardNuevoTicket.setBounds(410, 332, 540, 92);
        cardNuevoTicket.setBorder(new LineBorder(new Color(225, 230, 235), 1, true));
        cardNuevoTicket.setLayout(null);
        add(cardNuevoTicket);

        JLabel lblPregunta = new JLabel("¿Necesita reportar un nuevo fallo?");
        lblPregunta.setForeground(new Color(25, 25, 25));
        lblPregunta.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblPregunta.setBounds(28, 24, 280, 20);
        cardNuevoTicket.add(lblPregunta);

        JLabel lblSubPregunta = new JLabel("Genere un ticket para recibir asistencia del equipo TI.");
        lblSubPregunta.setForeground(new Color(120, 130, 140));
        lblSubPregunta.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblSubPregunta.setBounds(28, 46, 300, 18);
        cardNuevoTicket.add(lblSubPregunta);

        // Botón "⊕ Crear Nuevo Ticket" (#0095D9) -> Conecta con la Pantalla 7
        JButton btnCrearTicket = new JButton("⊕  Crear Nuevo Ticket");
        btnCrearTicket.setBackground(Color.decode("#0095D9"));
        btnCrearTicket.setForeground(Color.WHITE);
        btnCrearTicket.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnCrearTicket.setFocusPainted(false);
        btnCrearTicket.setBorderPainted(false);
        btnCrearTicket.setOpaque(true);
        btnCrearTicket.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCrearTicket.setBounds(342, 25, 170, 42);
        cardNuevoTicket.add(btnCrearTicket);

        btnCrearTicket.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (marcoPadre != null) {
                    marcoPadre.mostrarPantalla("PANTALLA_REGISTRO_TICKET");
                }
            }
        });
    }

    /**
     * Actualiza todos los datos personales y el conteo de tickets del usuario conectado
     */
    public void actualizarDatosPerfil(String nombre, String cargo, String cedula, String usuarioRed,
            String area, int abiertos, int enProceso, int cerrados) {

        lblNombreCompleto.setText(nombre);
        lblCargo.setText(cargo);
        lblValorCedula.setText("🪪  " + cedula);
        lblValorUsuarioRed.setText("     " + usuarioRed);
        lblValorArea.setText("🏥  " + area);

        lblNumAbiertos.setText(String.valueOf(abiertos));
        lblNumEnProceso.setText(String.valueOf(enProceso));
        lblNumCerrados.setText(String.valueOf(cerrados));

        barraSuperior.cambiarTitulo("Centro de Perfil", nombre);
    }

    public BarraSuperior getBarraSuperior() {
        return barraSuperior;
    }
}