package com.netcom.vista;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

public class PanelDashboard extends JPanel {

    private static final long serialVersionUID = 1L;

    private BarraSuperior barraSuperior;
    private ventanaPrincipal marcoPadre;

    // Etiquetas dinámicas de Tarjeta 1 (Tiempo promedio) y Tarjeta 2 (Almacenamiento)
    private JLabel lblTiempoPromedio;
    private JLabel lblDetalleTiempo;
    private JLabel lblAlmacenamiento;

    // Arreglos de etiquetas para el Ranking Top 5 (Tarjeta 3)
    private JLabel[] lblRankingNombres = new JLabel[5];
    private JLabel[] lblRankingCantidades = new JLabel[5];

    // Selector de periodo y panel de dibujo de la gráfica quincenal
    private JComboBox<String> cmbPeriodoGrafica;
    private PanelGraficaQuincenal panelGrafica;

    // Control en memoria de tiempos de creación y resolución (sin BD todavía)
    private Map<String, LocalDateTime> horasCreacion = new HashMap<>();
    private List<Long> tiemposResueltosMinutos = new ArrayList<>();

    // Copia de los tickets actuales de la Bandeja Global para recalcular al cambiar de periodo
    private List<Object[]> ticketsGlobalesCache = new ArrayList<>();

    public PanelDashboard(ventanaPrincipal marcoPadre) {
        this.marcoPadre = marcoPadre;

        // Tiempos iniciales de los 2 tickets de ejemplo que ya vienen RESUELTOS (TK-101: 35 min, TK-013: 55 min -> Promedio: 45 Min)
        tiemposResueltosMinutos.add(35L);
        tiemposResueltosMinutos.add(55L);

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
        // 3. SUB-MENÚ UNIFICADO DE 4 PESTAÑAS TI (Dashboard KPIs ACTIVA)
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

        // Pestaña 2: Extractor de Inventario
        JLabel lblTabInventario = new JLabel("Extractor de Inventario", SwingConstants.CENTER);
        lblTabInventario.setForeground(new Color(110, 120, 130));
        lblTabInventario.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblTabInventario.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblTabInventario.setBounds(200, 0, 145, 44);
        panelSubMenu.add(lblTabInventario);

        lblTabInventario.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (marcoPadre != null) {
                    marcoPadre.mostrarPantalla("PANTALLA_INVENTARIO");
                }
            }
        });

        // Pestaña 3 (ACTIVA): Dashboard KPIs
        JLabel lblTabDashboard = new JLabel("Dashboard KPIs", SwingConstants.CENTER);
        lblTabDashboard.setForeground(Color.decode("#0095D9"));
        lblTabDashboard.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblTabDashboard.setBorder(new MatteBorder(0, 0, 3, 0, Color.decode("#0095D9")));
        lblTabDashboard.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblTabDashboard.setBounds(360, 0, 110, 44);
        panelSubMenu.add(lblTabDashboard);

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
        // 4. FILA SUPERIOR: 3 TARJETAS DE INDICADORES (KPIs)
        // =========================================================================

        // --- TARJETA 1: TIEMPOS DE RESOLUCIÓN (PROMEDIO) ---
        JPanel cardTiempo = new JPanel();
        cardTiempo.setBackground(Color.WHITE);
        cardTiempo.setBounds(30, 122, 315, 178);
        cardTiempo.setBorder(new LineBorder(new Color(225, 230, 235), 1, true));
        cardTiempo.setLayout(null);
        add(cardTiempo);

        JLabel lblTitTiempo = new JLabel("TIEMPOS DE RESOLUCIÓN (PROMEDIO)");
        lblTitTiempo.setForeground(new Color(100, 116, 139));
        lblTitTiempo.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblTitTiempo.setBounds(22, 20, 270, 16);
        cardTiempo.add(lblTitTiempo);

        lblTiempoPromedio = new JLabel("45 Min");
        lblTiempoPromedio.setForeground(Color.decode("#2B3990"));
        lblTiempoPromedio.setFont(new Font("Segoe UI", Font.BOLD, 34));
        lblTiempoPromedio.setBounds(22, 54, 270, 44);
        cardTiempo.add(lblTiempoPromedio);

        lblDetalleTiempo = new JLabel("Calculado sobre 2 tickets resueltos");
        lblDetalleTiempo.setForeground(Color.decode("#0095D9"));
        lblDetalleTiempo.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblDetalleTiempo.setBounds(22, 108, 270, 20);
        cardTiempo.add(lblDetalleTiempo);

        // --- TARJETA 2: ALMACENAMIENTO SERVIDOR (EVIDENCIAS) ---
        JPanel cardAlmacenamiento = new JPanel();
        cardAlmacenamiento.setBackground(Color.WHITE);
        cardAlmacenamiento.setBounds(365, 122, 315, 178);
        cardAlmacenamiento.setBorder(new LineBorder(new Color(225, 230, 235), 1, true));
        cardAlmacenamiento.setLayout(null);
        add(cardAlmacenamiento);

        JLabel lblTitAlmac = new JLabel("ALMACENAMIENTO SERVIDOR (EVIDENCIAS)");
        lblTitAlmac.setForeground(new Color(100, 116, 139));
        lblTitAlmac.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblTitAlmac.setBounds(22, 20, 275, 16);
        cardAlmacenamiento.add(lblTitAlmac);

        lblAlmacenamiento = new JLabel("0 MB");
        lblAlmacenamiento.setForeground(Color.decode("#2B3990"));
        lblAlmacenamiento.setFont(new Font("Segoe UI", Font.BOLD, 34));
        lblAlmacenamiento.setBounds(22, 54, 270, 44);
        cardAlmacenamiento.add(lblAlmacenamiento);

        JLabel lblDetalleAlmac = new JLabel("<html>100% disponible (Se activará al cargar<br>evidencias JPG / PNG / PDF)</html>");
        lblDetalleAlmac.setForeground(Color.decode("#0095D9"));
        lblDetalleAlmac.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblDetalleAlmac.setBounds(22, 104, 275, 34);
        cardAlmacenamiento.add(lblDetalleAlmac);

        // --- TARJETA 3: RANKING TOP 5 ÁREAS CON MÁS FALLOS ---
        JPanel cardRanking = new JPanel();
        cardRanking.setBackground(Color.WHITE);
        cardRanking.setBounds(700, 122, 363, 178);
        cardRanking.setBorder(new LineBorder(new Color(225, 230, 235), 1, true));
        cardRanking.setLayout(null);
        add(cardRanking);

        JLabel lblTitRanking = new JLabel("RANKING TOP 5: ÁREAS CON MÁS FALLOS");
        lblTitRanking.setForeground(new Color(100, 116, 139));
        lblTitRanking.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblTitRanking.setBounds(20, 16, 320, 16);
        cardRanking.add(lblTitRanking);

        for (int i = 0; i < 5; i++) {
            int posY = 40 + (i * 25);

            lblRankingNombres[i] = new JLabel((i + 1) + ". Sin registros aún");
            lblRankingNombres[i].setForeground(new Color(55, 65, 81));
            lblRankingNombres[i].setFont(new Font("Segoe UI", Font.PLAIN, 12));
            lblRankingNombres[i].setBounds(20, posY, 245, 20);
            cardRanking.add(lblRankingNombres[i]);

            lblRankingCantidades[i] = new JLabel("0 Tickets", SwingConstants.RIGHT);
            lblRankingCantidades[i].setForeground(new Color(25, 25, 25));
            lblRankingCantidades[i].setFont(new Font("Segoe UI", Font.BOLD, 12));
            lblRankingCantidades[i].setBounds(268, posY, 75, 20);
            cardRanking.add(lblRankingCantidades[i]);
        }

        // =========================================================================
        // 5. FILA INFERIOR: GRÁFICA QUINCENAL (ABIERTOS VS RESUELTOS)
        // =========================================================================
        JPanel cardGrafica = new JPanel();
        cardGrafica.setBackground(Color.WHITE);
        cardGrafica.setBounds(30, 318, 1033, 302);
        cardGrafica.setBorder(new LineBorder(new Color(225, 230, 235), 1, true));
        cardGrafica.setLayout(null);
        add(cardGrafica);

        JLabel lblTitGrafica = new JLabel("COMPARATIVA QUINCENAL: ABIERTOS VS RESUELTOS");
        lblTitGrafica.setForeground(new Color(71, 85, 105));
        lblTitGrafica.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblTitGrafica.setBounds(24, 18, 360, 22);
        cardGrafica.add(lblTitGrafica);

        // Leyenda de colores
        JLabel lblLeyendaAbiertos = new JLabel("■ Abiertos / Activos");
        lblLeyendaAbiertos.setForeground(Color.decode("#0095D9"));
        lblLeyendaAbiertos.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblLeyendaAbiertos.setBounds(410, 19, 130, 20);
        cardGrafica.add(lblLeyendaAbiertos);

        JLabel lblLeyendaResueltos = new JLabel("■ Resueltos");
        lblLeyendaResueltos.setForeground(Color.decode("#8BC53F"));
        lblLeyendaResueltos.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblLeyendaResueltos.setBounds(550, 19, 90, 20);
        cardGrafica.add(lblLeyendaResueltos);

        // Selector de Bimestre / Rotación Automática de Meses
        JLabel lblSelector = new JLabel("Periodo:", SwingConstants.RIGHT);
        lblSelector.setForeground(new Color(100, 116, 139));
        lblSelector.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblSelector.setBounds(715, 19, 55, 22);
        cardGrafica.add(lblSelector);

        cmbPeriodoGrafica = new JComboBox<String>();
        cmbPeriodoGrafica.setModel(new DefaultComboBoxModel<String>(new String[] {
            "Últimas 4 Quincenas (Automático)",
            "Sep - Oct 2026",
            "Nov - Dic 2026",
            "Ene - Feb 2027"
        }));
        cmbPeriodoGrafica.setBackground(Color.decode("#F8FAFC"));
        cmbPeriodoGrafica.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        cmbPeriodoGrafica.setBounds(778, 14, 232, 32);
        cardGrafica.add(cmbPeriodoGrafica);

        // Lienzo que dibuja las barras y rangos de fechas quincenales
        panelGrafica = new PanelGraficaQuincenal();
        panelGrafica.setBounds(24, 54, 985, 235);
        cardGrafica.add(panelGrafica);

        // Evento al cambiar de bimestre en el selector
        cmbPeriodoGrafica.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                recalcularGraficaQuincenal();
            }
        });
    }

    /**
     * Registra la hora exacta en que nace un ticket nuevo para calcular su duración al cerrarse
     */
    public void registrarHoraCreacionTicket(String idTicket) {
        horasCreacion.put(idTicket, LocalDateTime.now());
    }

    /**
     * Cuando el ticket se marca como RESUELTO, resta la hora actual menos su hora de creación
     */
    public void registrarHoraResolucionTicket(String idTicket) {
        LocalDateTime inicio = horasCreacion.get(idTicket);
        long minutosTranscurridos;

        if (inicio != null) {
            minutosTranscurridos = Duration.between(inicio, LocalDateTime.now()).toMinutes();
            if (minutosTranscurridos <= 0) {
                minutosTranscurridos = 1; // Mínimo 1 min para pruebas rápidas en sustentación
            }
        } else {
            // Si fue uno de los tickets precargados sin hora de inicio en esta sesión
            minutosTranscurridos = 30;
        }

        tiemposResueltosMinutos.add(minutosTranscurridos);
        actualizarTarjetaTiempoPromedio();
    }

    private void actualizarTarjetaTiempoPromedio() {
        if (tiemposResueltosMinutos.isEmpty()) {
            lblTiempoPromedio.setText("0 Min");
            lblDetalleTiempo.setText("Aún no hay tickets resueltos");
            return;
        }

        long suma = 0;
        for (Long min : tiemposResueltosMinutos) {
            suma += min;
        }
        long promedio = Math.round((double) suma / tiemposResueltosMinutos.size());

        lblTiempoPromedio.setText(promedio + " Min");
        lblDetalleTiempo.setText("Calculado sobre " + tiemposResueltosMinutos.size() + " tickets resueltos");
    }

    /**
     * Recibe todas las filas actuales de PanelBandejaGlobal y recalcula Top 5 + Gráfica + Promedio
     */
    public void actualizarMetricasDesdeBandeja(List<Object[]> ticketsGlobales) {
        this.ticketsGlobalesCache = ticketsGlobales;
        actualizarTarjetaTiempoPromedio();
        calcularRankingTop5();
        recalcularGraficaQuincenal();
    }

    /**
     * Agrupa y cuenta tickets por Sede / Área usando un HashMap y ordena el Top 5
     */
    private void calcularRankingTop5() {
        Map<String, Integer> conteoPorArea = new HashMap<>();

        for (Object[] fila : ticketsGlobalesCache) {
            String sedeArea = fila[3].toString().replace("|", "-").trim();
            conteoPorArea.put(sedeArea, conteoPorArea.getOrDefault(sedeArea, 0) + 1);
        }

        // Guardamos los nombres de las áreas en una lista simple y ordenamos de mayor a menor
        List<String> areasOrdenadas = new ArrayList<>(conteoPorArea.keySet());
        areasOrdenadas.sort((a, b) -> conteoPorArea.get(b).compareTo(conteoPorArea.get(a)));

        for (int i = 0; i < 5; i++) {
            if (i < areasOrdenadas.size()) {
                String nombreArea = areasOrdenadas.get(i);
                int cantidad = conteoPorArea.get(nombreArea);
                String etiquetaTicket = (cantidad == 1) ? " Ticket" : " Tickets";
                lblRankingNombres[i].setText((i + 1) + ". " + nombreArea);
                lblRankingCantidades[i].setText(cantidad + etiquetaTicket);
            } else {
                lblRankingNombres[i].setText((i + 1) + ". —");
                lblRankingCantidades[i].setText("0 Tickets");
            }
        }
    }

    /**
     * Construye las 4 quincenas según el periodo elegido y cuenta cuántos tickets reales caen en cada rango
     */
    private void recalcularGraficaQuincenal() {
        String opcion = cmbPeriodoGrafica.getSelectedItem().toString();
        List<DatoQuincena> quincenas = new ArrayList<>();

        if (opcion.equals("Sep - Oct 2026")) {
            quincenas.add(crearQuincena(2026, 9, true));
            quincenas.add(crearQuincena(2026, 9, false));
            quincenas.add(crearQuincena(2026, 10, true));
            quincenas.add(crearQuincena(2026, 10, false));
        } else if (opcion.equals("Nov - Dic 2026")) {
            quincenas.add(crearQuincena(2026, 11, true));
            quincenas.add(crearQuincena(2026, 11, false));
            quincenas.add(crearQuincena(2026, 12, true));
            quincenas.add(crearQuincena(2026, 12, false));
        } else if (opcion.equals("Ene - Feb 2027")) {
            quincenas.add(crearQuincena(2027, 1, true));
            quincenas.add(crearQuincena(2027, 1, false));
            quincenas.add(crearQuincena(2027, 2, true));
            quincenas.add(crearQuincena(2027, 2, false));
        } else {
            // MODO AUTOMÁTICO: toma la fecha actual del PC y retrocede 4 quincenas (rota solo de Dic a Ene)
            LocalDate hoy = LocalDate.now();
            YearMonth ymActual = YearMonth.from(hoy);
            boolean primeraMitad = hoy.getDayOfMonth() <= 15;

            for (int k = 0; k < 4; k++) {
                quincenas.add(0, crearQuincena(ymActual.getYear(), ymActual.getMonthValue(), primeraMitad));
                if (primeraMitad) {
                    ymActual = ymActual.minusMonths(1);
                    primeraMitad = false;
                } else {
                    primeraMitad = true;
                }
            }
        }

        // Recorremos los tickets reales y sumamos en la quincena correspondiente
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        for (Object[] fila : ticketsGlobalesCache) {
            try {
                String soloFecha = fila[1].toString().split(" ")[0].trim();
                LocalDate fechaTicket = LocalDate.parse(soloFecha, formatoFecha);
                String estado = fila[6].toString().toUpperCase();

                for (DatoQuincena q : quincenas) {
                    if (!fechaTicket.isBefore(q.inicio) && !fechaTicket.isAfter(q.fin)) {
                        if (estado.equals("RESUELTO") || estado.equals("CERRADO")) {
                            q.resueltos++;
                        } else if (!estado.equals("ANULADO")) {
                            q.abiertos++;
                        }
                        break;
                    }
                }
            } catch (Exception ex) {
                // Si una fecha no cumple formato se omite sin bloquear la gráfica
            }
        }

        panelGrafica.setDatos(quincenas);
    }

    private DatoQuincena crearQuincena(int anio, int mes, boolean esPrimeraQuincena) {
        YearMonth ym = YearMonth.of(anio, mes);
        LocalDate inicio = esPrimeraQuincena ? ym.atDay(1) : ym.atDay(16);
        LocalDate fin = esPrimeraQuincena ? ym.atDay(15) : ym.atEndOfMonth();

        DateTimeFormatter fmtDiaMes = DateTimeFormatter.ofPattern("dd MMM", new Locale("es", "CO"));
        DateTimeFormatter fmtMesAnio = DateTimeFormatter.ofPattern("MMMM yyyy", new Locale("es", "CO"));

        String rangoTexto = inicio.format(fmtDiaMes) + " - " + fin.format(fmtDiaMes);
        String mesTexto = inicio.format(fmtMesAnio);
        mesTexto = mesTexto.substring(0, 1).toUpperCase() + mesTexto.substring(1);

        return new DatoQuincena(inicio, fin, rangoTexto, mesTexto);
    }

    public BarraSuperior getBarraSuperior() {
        return barraSuperior;
    }

    // =========================================================================
    // CLASE AUXILIAR PARA CADA QUINCENA Y COMPONENTE GRÁFICO PERSONALIZADO
    // =========================================================================
    private static class DatoQuincena {
        LocalDate inicio;
        LocalDate fin;
        String rangoEtiqueta;
        String mesEtiqueta;
        int abiertos = 0;
        int resueltos = 0;

        DatoQuincena(LocalDate inicio, LocalDate fin, String rangoEtiqueta, String mesEtiqueta) {
            this.inicio = inicio;
            this.fin = fin;
            this.rangoEtiqueta = rangoEtiqueta;
            this.mesEtiqueta = mesEtiqueta;
        }
    }

    private class PanelGraficaQuincenal extends JPanel {
        private static final long serialVersionUID = 1L;
        private List<DatoQuincena> datos = new ArrayList<>();

        public PanelGraficaQuincenal() {
            setBackground(Color.WHITE);
        }

        public void setDatos(List<DatoQuincena> nuevosDatos) {
            this.datos = nuevosDatos;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (datos == null || datos.isEmpty()) {
                return;
            }

            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int anchoPanel = getWidth();
            int yBase = 175;          // Línea horizontal base de las barras
            int alturaMaxima = 140;   // Altura máxima en píxeles de la barra más alta

            // Buscamos el valor más alto para escalar las barras proporcionalmente
            int valorMaximo = 1;
            for (DatoQuincena q : datos) {
                valorMaximo = Math.max(valorMaximo, Math.max(q.abiertos, q.resueltos));
            }

            // Línea base gris suave
            g2.setColor(new Color(226, 232, 240));
            g2.drawLine(20, yBase, anchoPanel - 20, yBase);

            int espacioGrupo = (anchoPanel - 40) / datos.size();
            int anchoBarra = 48;
            int separacionEntreBarras = 14;

            for (int i = 0; i < datos.size(); i++) {
                DatoQuincena q = datos.get(i);
                int centroGrupo = 20 + (i * espacioGrupo) + (espacioGrupo / 2);

                int xBarraAbiertos = centroGrupo - anchoBarra - (separacionEntreBarras / 2);
                int xBarraResueltos = centroGrupo + (separacionEntreBarras / 2);

                int altoAbiertos = (q.abiertos == 0) ? 4 : (int) ((double) q.abiertos / valorMaximo * alturaMaxima);
                int altoResueltos = (q.resueltos == 0) ? 4 : (int) ((double) q.resueltos / valorMaximo * alturaMaxima);

                // 1. Barra Azul (Abiertos - #0095D9)
                g2.setColor(Color.decode("#0095D9"));
                g2.fillRoundRect(xBarraAbiertos, yBase - altoAbiertos, anchoBarra, altoAbiertos, 8, 8);

                // Número encima de la barra azul
                g2.setFont(new Font("Segoe UI", Font.BOLD, 12));
                g2.setColor(Color.decode("#004A8F"));
                String txtAb = String.valueOf(q.abiertos);
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(txtAb, xBarraAbiertos + (anchoBarra - fm.stringWidth(txtAb)) / 2, yBase - altoAbiertos - 6);

                // 2. Barra Verde (Resueltos - #8BC53F)
                g2.setColor(Color.decode("#8BC53F"));
                g2.fillRoundRect(xBarraResueltos, yBase - altoResueltos, anchoBarra, altoResueltos, 8, 8);

                // Número encima de la barra verde
                g2.setColor(Color.decode("#3F6212"));
                String txtRes = String.valueOf(q.resueltos);
                g2.drawString(txtRes, xBarraResueltos + (anchoBarra - fm.stringWidth(txtRes)) / 2, yBase - altoResueltos - 6);

                // 3. Etiqueta de Rango de Fechas (ej. "16 nov. - 30 nov.")
                g2.setFont(new Font("Segoe UI", Font.BOLD, 11));
                g2.setColor(new Color(51, 65, 85));
                FontMetrics fmRango = g2.getFontMetrics();
                g2.drawString(q.rangoEtiqueta, centroGrupo - (fmRango.stringWidth(q.rangoEtiqueta) / 2), yBase + 22);

                // 4. Etiqueta del Mes y Año abajo (ej. "Noviembre 2026")
                g2.setFont(new Font("Segoe UI", Font.PLAIN, 11));
                g2.setColor(new Color(120, 130, 145));
                FontMetrics fmMes = g2.getFontMetrics();
                g2.drawString(q.mesEtiqueta, centroGrupo - (fmMes.stringWidth(q.mesEtiqueta) / 2), yBase + 40);
            }
        }
    }
}