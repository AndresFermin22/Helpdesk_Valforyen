package com.netcom.vista;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class PanelUsuarioCreado extends JPanel {

    private static final long serialVersionUID = 1L;

    // 1. Reutilizamos nuestra BarraSuperior
    private BarraSuperior barraSuperior;

    // 2. Etiquetas dinámicas donde se mostrarán los datos enviados desde la Pantalla 2
    private JLabel lblValorNombre;
    private JLabel lblValorUsuario;

    private ventanaPrincipal marcoPadre;

    public PanelUsuarioCreado(ventanaPrincipal marcoPadre) {
        this.marcoPadre = marcoPadre;

        // Fondo general Hielo (#F4F9FB) y tamaño estándar
        setBackground(Color.decode("#F4F9FB"));
        setSize(1100, 680);
        setLayout(null);

        // =========================================================================
        // INSERTAMOS LA BARRA SUPERIOR REUTILIZABLE
        // =========================================================================
        barraSuperior = new BarraSuperior(marcoPadre);
        add(barraSuperior);

        // =========================================================================
        // TARJETA BLANCA CENTRAL (420x380 centrada en la pantalla)
        // =========================================================================
        JPanel panelTarjeta = new JPanel();
        panelTarjeta.setBackground(Color.WHITE);
        panelTarjeta.setBounds(340, 145, 420, 380);
        panelTarjeta.setBorder(new LineBorder(new Color(224, 224, 224), 1, true));
        panelTarjeta.setLayout(null);
        add(panelTarjeta);

        // Círculo azul claro superior con el icono de usuario verificado
        JLabel lblIconoExito = new JLabel("👤✓", SwingConstants.CENTER);
        lblIconoExito.setOpaque(true);
        lblIconoExito.setBackground(Color.decode("#EBF3FA"));
        lblIconoExito.setForeground(Color.decode("#004A8F"));
        lblIconoExito.setFont(new Font("Segoe UI Emoji", Font.BOLD, 24));
        lblIconoExito.setBounds(175, 30, 70, 70);
        lblIconoExito.setBorder(new LineBorder(Color.decode("#D6E6F5"), 1, true));
        panelTarjeta.add(lblIconoExito);

        // Título: "Usuario Registrado con Éxito"
        JLabel lblTitulo = new JLabel("Usuario Registrado con Éxito", SwingConstants.CENTER);
        lblTitulo.setForeground(new Color(33, 33, 33));
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitulo.setBounds(30, 115, 360, 28);
        panelTarjeta.add(lblTitulo);

        // Subtítulo descriptivo
        JLabel lblSubtitulo = new JLabel(
                "<html><center>El colaborador ya puede acceder al portal de soporte<br>con sus credenciales temporales.</center></html>",
                SwingConstants.CENTER);
        lblSubtitulo.setForeground(new Color(120, 130, 140));
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSubtitulo.setBounds(40, 148, 340, 36);
        panelTarjeta.add(lblSubtitulo);

        // =========================================================================
        // RECUADRO AZUL CLARO CON LOS DATOS DEL USUARIO CREADO
        // =========================================================================
        JPanel panelResumen = new JPanel();
        panelResumen.setBackground(Color.decode("#EEF5FC"));
        panelResumen.setBorder(new LineBorder(Color.decode("#DCE8F5"), 1, true));
        panelResumen.setBounds(40, 200, 340, 72);
        panelResumen.setLayout(null);
        panelTarjeta.add(panelResumen);

        // Fila 1 del recuadro: Nombre
        lblValorNombre = new JLabel("<html><font color='#2B3990'>Nombre: </font><font color='#111111'><b>Andrés Mendoza</b></font></html>");
        lblValorNombre.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblValorNombre.setBounds(18, 12, 305, 22);
        panelResumen.add(lblValorNombre);

        // Fila 2 del recuadro: Usuario de Red
        lblValorUsuario = new JLabel("<html><font color='#2B3990'>Usuario de Red: </font><font color='#111111'><b>a.mendoza</b></font></html>");
        lblValorUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblValorUsuario.setBounds(18, 38, 305, 22);
        panelResumen.add(lblValorUsuario);

        // =========================================================================
        // BOTONES INFERIORES ("Volver al Inicio" y "Crear Otro Usuario")
        // =========================================================================
        JButton btnVolverInicio = new JButton("Volver al Inicio");
        btnVolverInicio.setBackground(Color.WHITE);
        btnVolverInicio.setForeground(Color.decode("#004A8F"));
        btnVolverInicio.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnVolverInicio.setBorder(new LineBorder(Color.decode("#004A8F"), 1, true));
        btnVolverInicio.setFocusPainted(false);
        btnVolverInicio.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVolverInicio.setBounds(40, 295, 160, 40);
        panelTarjeta.add(btnVolverInicio);

        JButton btnCrearOtro = new JButton("Crear Otro Usuario");
        btnCrearOtro.setBackground(Color.decode("#004A8F"));
        btnCrearOtro.setForeground(Color.WHITE);
        btnCrearOtro.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnCrearOtro.setFocusPainted(false);
        btnCrearOtro.setBorderPainted(false);
        btnCrearOtro.setOpaque(true);
        btnCrearOtro.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCrearOtro.setBounds(220, 295, 160, 40);
        panelTarjeta.add(btnCrearOtro);

        // =========================================================================
        // EVENTOS DE NAVEGACIÓN (RF-13)
        // =========================================================================

        // Botón "Crear Otro Usuario": Regresa al formulario limpio de la Pantalla 2
        btnCrearOtro.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (marcoPadre != null) {
                    marcoPadre.mostrarPantalla("PANTALLA_CREACION_USUARIO");
                }
            }
        });

     // Botón "Volver al Inicio": Regresa a la pantalla principal de donde vino
        btnVolverInicio.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (marcoPadre != null) {
                    marcoPadre.volverPantallaInicio();
                }
            }
        });
    }

    /**
     * Este método recibe el nombre y el usuario digitados en la Pantalla 2
     * y los coloca dinámicamente dentro del recuadro azul claro.
     */
    public void cargarDatosUsuarioCreado(String nombre, String usuarioRed) {
        lblValorNombre.setText("<html><font color='#2B3990'>Nombre: </font><font color='#111111'><b>" + nombre + "</b></font></html>");
        lblValorUsuario.setText("<html><font color='#2B3990'>Usuario de Red: </font><font color='#111111'><b>" + usuarioRed + "</b></font></html>");
    }

    public BarraSuperior getBarraSuperior() {
        return barraSuperior;
    }
}