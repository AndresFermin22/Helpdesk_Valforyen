package com.netcom.vista;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class PanelEliminarUsuario extends JPanel {

    private static final long serialVersionUID = 1L;

    private ventanaPrincipal marcoPadre;
    private JLabel lblMensajeConfirmacion;

    // Guardamos la fila de la tabla de la Pantalla 17 que se va a inhabilitar
    private int filaSeleccionada = -1;

    public PanelEliminarUsuario(ventanaPrincipal marcoPadre) {
        this.marcoPadre = marcoPadre;

        // 1. Fondo general Hielo (#F4F9FB)
        setBackground(Color.decode("#F4F9FB"));
        setSize(1100, 680);
        setLayout(null);

        // 2. Tarjeta blanca central (400x315 centrada en la ventana)
        JPanel panelTarjeta = new JPanel();
        panelTarjeta.setBackground(Color.WHITE);
        panelTarjeta.setBounds(350, 175, 400, 315);
        panelTarjeta.setBorder(new LineBorder(new Color(224, 224, 224), 1, true));
        panelTarjeta.setLayout(null);
        add(panelTarjeta);

        // 3. Círculo rosado/rojo suave con signo de exclamación rojo (!)
        JLabel lblIconoAlerta = new JLabel("!", SwingConstants.CENTER);
        lblIconoAlerta.setOpaque(true);
        lblIconoAlerta.setBackground(Color.decode("#FEE2E2"));
        lblIconoAlerta.setForeground(Color.decode("#DC2626"));
        lblIconoAlerta.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblIconoAlerta.setBounds(176, 26, 48, 48);
        lblIconoAlerta.setBorder(new LineBorder(Color.decode("#FECACA"), 1, true));
        panelTarjeta.add(lblIconoAlerta);

        // 4. Título "¿Eliminar Usuario?" en Azul Índigo (#2B3990)
        JLabel lblTitulo = new JLabel("¿Eliminar Usuario?", SwingConstants.CENTER);
        lblTitulo.setForeground(Color.decode("#2B3990"));
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setBounds(30, 88, 340, 26);
        panelTarjeta.add(lblTitulo);

        // 5. Mensaje dinámico con el nombre del usuario seleccionado (RF-17)
        lblMensajeConfirmacion = new JLabel(
                "<html><center>Está a punto de inhabilitar/eliminar a <b>Fermin López</b> del<br>sistema. No podrá acceder de nuevo, pero su historial de<br>tickets se conservará.</center></html>",
                SwingConstants.CENTER);
        lblMensajeConfirmacion.setForeground(new Color(115, 125, 135));
        lblMensajeConfirmacion.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblMensajeConfirmacion.setBounds(35, 120, 330, 50);
        panelTarjeta.add(lblMensajeConfirmacion);

        // 6. Botón Rojo "Sí, Eliminar"
        JButton btnSiEliminar = new JButton("Sí, Eliminar");
        btnSiEliminar.setBackground(Color.decode("#E53935"));
        btnSiEliminar.setForeground(Color.WHITE);
        btnSiEliminar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnSiEliminar.setFocusPainted(false);
        btnSiEliminar.setBorderPainted(false);
        btnSiEliminar.setOpaque(true);
        btnSiEliminar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSiEliminar.setBounds(45, 188, 310, 40);
        panelTarjeta.add(btnSiEliminar);

        // 7. Botón Gris claro "Cancelar"
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBackground(Color.decode("#F1F5F9"));
        btnCancelar.setForeground(new Color(55, 65, 81));
        btnCancelar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnCancelar.setFocusPainted(false);
        btnCancelar.setBorderPainted(false);
        btnCancelar.setOpaque(true);
        btnCancelar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCancelar.setBounds(45, 240, 310, 40);
        panelTarjeta.add(btnCancelar);

        // =========================================================================
        // EVENTOS DE LOS BOTONES (RF-17)
        // =========================================================================

        // Al confirmar, inhabilita al usuario en la tabla y pasa a la Pantalla 20
        btnSiEliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (marcoPadre != null) {
                    marcoPadre.confirmarEliminacionUsuario(filaSeleccionada);
                }
            }
        });

        // Al cancelar, vuelve sin hacer cambios a Administración de Usuarios (Pantalla 17)
        btnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (marcoPadre != null) {
                    marcoPadre.mostrarPantalla("PANTALLA_ADMIN_USUARIOS");
                }
            }
        });
    }

    /**
     * Recibe la fila y el nombre real del colaborador al que se le pulsó "Eliminar"
     */
    public void cargarUsuarioAEliminar(int filaTabla, String nombreUsuario) {
        this.filaSeleccionada = filaTabla;
        lblMensajeConfirmacion.setText(
                "<html><center>Está a punto de inhabilitar/eliminar a <b>" + nombreUsuario
                        + "</b> del<br>sistema. No podrá acceder de nuevo, pero su historial de<br>tickets se conservará.</center></html>");
    }
}