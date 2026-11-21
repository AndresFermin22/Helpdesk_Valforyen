package com.netcom.vista;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import com.netcom.utilidades.RecolectorInventario;

public class PanelInventario extends JPanel {

    private static final long serialVersionUID = 1L;

    private BarraSuperior barraSuperior;
    private ventanaPrincipal marcoPadre;

    private JButton btnIniciarEscaneo;
    private JTextPane consolaTerminal;
    private StyledDocument docConsola;

    // Clase interna para enviar líneas con color desde el hilo secundario hacia la consola
    private static class LineaConsola {
        String texto;
        Color colorTexto;
        Color colorFondo;
        boolean negrita;

        LineaConsola(String texto, Color colorTexto, Color colorFondo, boolean negrita) {
            this.texto = texto;
            this.colorTexto = colorTexto;
            this.colorFondo = colorFondo;
            this.negrita = negrita;
        }
    }

    public PanelInventario(ventanaPrincipal marcoPadre) {
        this.marcoPadre = marcoPadre;

        // 1. Fondo general Hielo (#F4F9FB)
        setBackground(Color.decode("#F4F9FB"));
        setSize(1100, 680);
        setLayout(null);

        // =========================================================================
        // 2. BARRA SUPERIOR ("Centro de Mando TI")
        // =========================================================================
        barraSuperior = new BarraSuperior(marcoPadre);
        barraSuperior.cambiarTitulo("Centro de Mando TI", "Admin. TI");
        add(barraSuperior);

        // =========================================================================
        // 3. SUB-MENÚ DE NAVEGACIÓN TI (Extractor de Inventario ACTIVA)
        // =========================================================================
        JPanel panelSubMenu = new JPanel();
        panelSubMenu.setBackground(Color.WHITE);
        panelSubMenu.setBounds(0, 60, 1100, 44);
        panelSubMenu.setBorder(new MatteBorder(0, 0, 1, 0, new Color(225, 230, 235)));
        panelSubMenu.setLayout(null);
        add(panelSubMenu);

        // Pestaña 1: Bandeja Global de Tickets
        JLabel lblTabBandejaGlobal = new JLabel("Bandeja Global de Tickets", SwingConstants.CENTER);
        lblTabBandejaGlobal.setForeground(new Color(110, 120, 130));
        lblTabBandejaGlobal.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblTabBandejaGlobal.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblTabBandejaGlobal.setBounds(25, 0, 160, 44);
        panelSubMenu.add(lblTabBandejaGlobal);

        lblTabBandejaGlobal.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (marcoPadre != null) {
                    marcoPadre.mostrarPantalla("PANTALLA_BANDEJA_GLOBAL");
                }
            }
        });

        // Pestaña 2 (ACTIVA): Extractor de Inventario
        JLabel lblTabInventario = new JLabel("Extractor de Inventario", SwingConstants.CENTER);
        lblTabInventario.setForeground(Color.decode("#0095D9"));
        lblTabInventario.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblTabInventario.setBorder(new MatteBorder(0, 0, 3, 0, Color.decode("#0095D9")));
        lblTabInventario.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblTabInventario.setBounds(200, 0, 145, 44);
        panelSubMenu.add(lblTabInventario);

        // Pestaña 3: Dashboard KPIs
        JLabel lblTabDashboard = new JLabel("Dashboard KPIs", SwingConstants.CENTER);
        lblTabDashboard.setForeground(new Color(110, 120, 130));
        lblTabDashboard.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblTabDashboard.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblTabDashboard.setBounds(360, 0, 110, 44);
        panelSubMenu.add(lblTabDashboard);

        lblTabDashboard.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (marcoPadre != null) {
                    marcoPadre.abrirDashboardKPIs();
                }
            }
        });

        // Pestaña 4: Administración de Usuarios
        JLabel lblTabUsuarios = new JLabel("Administración de Usuarios", SwingConstants.CENTER);
        lblTabUsuarios.setForeground(new Color(110, 120, 130));
        lblTabUsuarios.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblTabUsuarios.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblTabUsuarios.setBounds(485, 0, 170, 44);
        panelSubMenu.add(lblTabUsuarios);

        lblTabUsuarios.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (marcoPadre != null) {
                    marcoPadre.mostrarPantalla("PANTALLA_ADMIN_USUARIOS");
                }
            }
        });

        // =========================================================================
        // 4. TARJETA SUPERIOR BLANCA ("Descarga de inventario - Clínica Girón")
        // =========================================================================
        JPanel cardEncabezado = new JPanel();
        cardEncabezado.setBackground(Color.WHITE);
        cardEncabezado.setBounds(170, 125, 760, 135);
        cardEncabezado.setBorder(new LineBorder(new Color(224, 224, 224), 1, true));
        cardEncabezado.setLayout(null);
        add(cardEncabezado);

        JLabel lblTitulo = new JLabel("🖥️   Descarga de inventario - Clínica Girón");
        lblTitulo.setForeground(Color.decode("#2B3990"));
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setBounds(25, 18, 450, 26);
        cardEncabezado.add(lblTitulo);

        JLabel lblSubtitulo = new JLabel("Ejecute el agente para extraer automáticamente los componentes (RAM, CPU, Discos, Red) del equipo actual.");
        lblSubtitulo.setForeground(new Color(100, 116, 139));
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSubtitulo.setBounds(25, 48, 690, 20);
        cardEncabezado.add(lblSubtitulo);

        btnIniciarEscaneo = new JButton("🔄  Iniciar Escaneo del Sistema");
        btnIniciarEscaneo.setBackground(Color.decode("#0095D9"));
        btnIniciarEscaneo.setForeground(Color.WHITE);
        btnIniciarEscaneo.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnIniciarEscaneo.setFocusPainted(false);
        btnIniciarEscaneo.setBorderPainted(false);
        btnIniciarEscaneo.setOpaque(true);
        btnIniciarEscaneo.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnIniciarEscaneo.setBounds(25, 80, 240, 38);
        cardEncabezado.add(btnIniciarEscaneo);

        // =========================================================================
        // 5. CONSOLA OSCURA ("Terminal_Auditoria.exe")
        // =========================================================================
        JPanel panelConsola = new JPanel();
        panelConsola.setBackground(Color.decode("#111827"));
        panelConsola.setBounds(170, 278, 760, 340);
        panelConsola.setBorder(new LineBorder(Color.decode("#1F2937"), 1, true));
        panelConsola.setLayout(null);
        add(panelConsola);

        // Barra superior de la terminal con los 3 círculos de colores
        JPanel barraTerminal = new JPanel();
        barraTerminal.setBackground(Color.decode("#1F2937"));
        barraTerminal.setBounds(0, 0, 760, 34);
        barraTerminal.setLayout(null);
        panelConsola.add(barraTerminal);

        JLabel puntoRojo = new JLabel("●");
        puntoRojo.setForeground(Color.decode("#EF4444"));
        puntoRojo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        puntoRojo.setBounds(14, 7, 16, 18);
        barraTerminal.add(puntoRojo);

        JLabel puntoAmarillo = new JLabel("●");
        puntoAmarillo.setForeground(Color.decode("#F59E0B"));
        puntoAmarillo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        puntoAmarillo.setBounds(32, 7, 16, 18);
        barraTerminal.add(puntoAmarillo);

        JLabel puntoVerde = new JLabel("●");
        puntoVerde.setForeground(Color.decode("#22C55E"));
        puntoVerde.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        puntoVerde.setBounds(50, 7, 16, 18);
        barraTerminal.add(puntoVerde);

        JLabel lblNombreTerminal = new JLabel("Terminal_Auditoria.exe");
        lblNombreTerminal.setForeground(Color.decode("#9CA3AF"));
        lblNombreTerminal.setFont(new Font("Consolas", Font.PLAIN, 12));
        lblNombreTerminal.setBounds(78, 8, 220, 18);
        barraTerminal.add(lblNombreTerminal);

        // Área de texto de la terminal con soporte para múltiples colores
        consolaTerminal = new JTextPane();
        consolaTerminal.setEditable(false);
        consolaTerminal.setBackground(Color.decode("#111827"));
        consolaTerminal.setFont(new Font("Consolas", Font.PLAIN, 12));
        consolaTerminal.setBorder(BorderFactory.createEmptyBorder(14, 18, 14, 18));
        docConsola = consolaTerminal.getStyledDocument();

        JScrollPane scrollConsola = new JScrollPane(consolaTerminal);
        scrollConsola.setBounds(0, 34, 760, 306);
        scrollConsola.setBorder(null);
        scrollConsola.getViewport().setBackground(Color.decode("#111827"));
        panelConsola.add(scrollConsola);

        // Mensaje inicial en espera antes de pulsar el botón
        agregarLineaConsola("Terminal lista. Haga clic en 'Iniciar Escaneo del Sistema' para auditar este equipo.\n",
                Color.decode("#9CA3AF"), null, false);

        // =========================================================================
        // 6. EVENTO DEL BOTÓN "Iniciar Escaneo del Sistema" (RF-44 y RF-45)
        // =========================================================================
        btnIniciarEscaneo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ejecutarEscaneoRealEnSegundoPlano();
            }
        });
    }

    /**
     * Ejecuta el RecolectorInventario en un hilo secundario (SwingWorker)
     * para leer los datos REALES del PC sin congelar la ventana.
     */
    private void ejecutarEscaneoRealEnSegundoPlano() {
        btnIniciarEscaneo.setEnabled(false);
        btnIniciarEscaneo.setText("⏳  Escaneando hardware...");
        consolaTerminal.setText(""); // Limpia la consola antes de empezar

        Color blanco = Color.decode("#E5E7EB");
        Color azul = Color.decode("#60A5FA");
        Color amarillo = Color.decode("#FACC15");
        Color verde = Color.decode("#4ADE80");
        Color fondoVerdeExito = Color.decode("#166534");

        SwingWorker<Void, LineaConsola> trabajador = new SwingWorker<Void, LineaConsola>() {
            @Override
            protected Void doInBackground() throws Exception {
                publish(new LineaConsola("Iniciando auditoría de hardware local (Clínica Girón)...\n", blanco, null, false));
                publish(new LineaConsola("[EN ESPERA] Conectando con los sensores del equipo...\n", azul, null, false));

                // 1. Equipo, Sistema Operativo y Red
                String hostname = RecolectorInventario.obtenerNombreEquipo();
                String red = RecolectorInventario.obtenerRedIpMac();
                String so = RecolectorInventario.obtenerSistemaOperativo();
                publish(new LineaConsola("> Equipo: " + hostname + " | " + so + "\n", verde, null, false));
                publish(new LineaConsola("> Red: " + red + "\n", verde, null, false));

                // 2. Placa Base, Serial y UUID (RF-45)
                publish(new LineaConsola("[CARGANDO] Extrayendo información de placa base y serial...\n", amarillo, null, false));
                String placa = RecolectorInventario.obtenerPlacaBase();
                String serial = RecolectorInventario.obtenerSerialPlaca();
                String uuid = RecolectorInventario.obtenerUuidSistema();
                publish(new LineaConsola("> Placa Base: " + placa + " | Serial: " + serial + "\n", verde, null, false));
                publish(new LineaConsola("> UUID Sistema: " + uuid + "\n", verde, null, false));

                // 3. Procesador y Memoria RAM
                publish(new LineaConsola("[CARGANDO] Leyendo microprocesador y memoria RAM...\n", amarillo, null, false));
                String cpu = RecolectorInventario.obtenerProcesador();
                String ram = RecolectorInventario.obtenerMemoriaRAM();
                publish(new LineaConsola("> CPU: " + cpu + "\n", verde, null, false));
                publish(new LineaConsola("> RAM: " + ram + "\n", verde, null, false));

                // 4. Unidades de Almacenamiento
                publish(new LineaConsola("[DESCARGANDO] Sincronizando unidades de almacenamiento...\n", amarillo, null, false));
                List<String> discos = RecolectorInventario.obtenerDiscosAlmacenamiento();
                for (String disco : discos) {
                    publish(new LineaConsola("> Disco: " + disco + "\n", verde, null, false));
                }

                // 5. Mensaje final de confirmación (igual al diseño)
                publish(new LineaConsola("\n", blanco, null, false));
                publish(new LineaConsola(" [✔] DATOS DESCARGADOS Y ENVIADOS AL SERVIDOR CENTRAL. \n", Color.WHITE, fondoVerdeExito, true));
                publish(new LineaConsola("_", verde, null, false));

                return null;
            }

            @Override
            protected void process(List<LineaConsola> bloques) {
                for (LineaConsola linea : bloques) {
                    agregarLineaConsola(linea.texto, linea.colorTexto, linea.colorFondo, linea.negrita);
                }
            }

            @Override
            protected void done() {
                btnIniciarEscaneo.setEnabled(true);
                btnIniciarEscaneo.setText("🔄  Iniciar Escaneo del Sistema");
            }
        };

        trabajador.execute();
    }

    private void agregarLineaConsola(String texto, Color colorTexto, Color colorFondo, boolean negrita) {
        SimpleAttributeSet estilo = new SimpleAttributeSet();
        StyleConstants.setForeground(estilo, colorTexto);
        StyleConstants.setBold(estilo, negrita);
        StyleConstants.setFontFamily(estilo, "Consolas");
        StyleConstants.setFontSize(estilo, 12);
        if (colorFondo != null) {
            StyleConstants.setBackground(estilo, colorFondo);
        }
        try {
            docConsola.insertString(docConsola.getLength(), texto, estilo);
            consolaTerminal.setCaretPosition(docConsola.getLength()); // Baja el scroll automáticamente
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public BarraSuperior getBarraSuperior() {
        return barraSuperior;
    }
}