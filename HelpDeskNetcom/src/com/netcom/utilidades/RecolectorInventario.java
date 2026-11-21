package com.netcom.utilidades;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class RecolectorInventario {

    /**
     * Ejecuta un comando de consulta de hardware en Windows (PowerShell CIM/WMI)
     * sin necesidad de permisos de administrador ni librerías externas.
     */
    private static List<String> ejecutarConsultaWindows(String comandoPowerShell) {
        List<String> lineas = new ArrayList<>();
        try {
            ProcessBuilder pb = new ProcessBuilder(
                    "powershell.exe", "-NoProfile", "-ExecutionPolicy", "Bypass", "-Command", comandoPowerShell);
            pb.redirectErrorStream(true);
            Process proceso = pb.start();

            try (BufferedReader lector = new BufferedReader(
                    new InputStreamReader(proceso.getInputStream(), StandardCharsets.UTF_8))) {
                String linea;
                while ((linea = lector.readLine()) != null) {
                    if (!linea.trim().isEmpty()) {
                        lineas.add(linea.trim());
                    }
                }
            }
            proceso.waitFor();
        } catch (Exception e) {
            lineas.add("NO DISPONIBLE");
        }
        return lineas;
    }

    /**
     * Valida si un dato de fábrica vino vacío o genérico (RF-45)
     */
    private static String validarDatoFabrica(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            return "NO DISPONIBLE";
        }
        String limpio = valor.trim();
        String lower = limpio.toLowerCase();
        if (lower.contains("to be filled") || lower.contains("default string")
                || lower.equals("none") || lower.equals("o.e.m.") || lower.equals("00000000")) {
            return "NO DISPONIBLE";
        }
        return limpio;
    }

    public static String obtenerPlacaBase() {
        List<String> res = ejecutarConsultaWindows(
                "Get-CimInstance Win32_BaseBoard | ForEach-Object { ($_.Manufacturer + ' ' + $_.Product).Trim() }");
        return res.isEmpty() ? "NO DISPONIBLE" : validarDatoFabrica(res.get(0));
    }

    public static String obtenerSerialPlaca() {
        List<String> res = ejecutarConsultaWindows("(Get-CimInstance Win32_BaseBoard).SerialNumber");
        return res.isEmpty() ? "NO DISPONIBLE" : validarDatoFabrica(res.get(0));
    }

    public static String obtenerUuidSistema() {
        List<String> res = ejecutarConsultaWindows("(Get-CimInstance Win32_ComputerSystemProduct).UUID");
        return res.isEmpty() ? "NO DISPONIBLE" : validarDatoFabrica(res.get(0));
    }

    public static String obtenerProcesador() {
        List<String> res = ejecutarConsultaWindows(
                "(Get-CimInstance Win32_Processor | Select-Object -First 1).Name");
        return res.isEmpty() ? "NO DISPONIBLE" : res.get(0).replaceAll("\\s+", " ");
    }

    public static String obtenerMemoriaRAM() {
        String cmd = "$m = Get-CimInstance Win32_PhysicalMemory; "
                + "$gb = [math]::Round(($m | Measure-Object Capacity -Sum).Sum / 1GB); "
                + "$c = ($m | Measure-Object).Count; "
                + "$v = ($m | Select-Object -First 1).Speed; "
                + "$t = ($m | Select-Object -First 1).SMBIOSMemoryType; "
                + "$d = if($t -eq 34){'DDR5'}elseif($t -eq 26){'DDR4'}elseif($t -eq 24){'DDR3'}else{'RAM'}; "
                + "'' + $gb + ' GB ' + $d + ' (' + $c + ' modulos @ ' + $v + ' MHz)'";
        List<String> res = ejecutarConsultaWindows(cmd);
        return res.isEmpty() ? "NO DISPONIBLE" : res.get(0);
    }

    public static List<String> obtenerDiscosAlmacenamiento() {
        String cmd = "$d = Get-PhysicalDisk -ErrorAction SilentlyContinue; "
                + "if (-not $d) { Get-CimInstance Win32_DiskDrive | ForEach-Object { $_.Model + ' (' + [math]::Round($_.Size/1GB) + ' GB)' } } "
                + "else { $d | ForEach-Object { $_.FriendlyName + ' - ' + [math]::Round($_.Size/1GB) + ' GB ' + $_.MediaType + ' (' + $_.BusType + ')' } }";
        List<String> res = ejecutarConsultaWindows(cmd);
        if (res.isEmpty()) {
            res.add("NO DISPONIBLE");
        }
        return res;
    }

    public static String obtenerSistemaOperativo() {
        List<String> res = ejecutarConsultaWindows(
                "Get-CimInstance Win32_OperatingSystem | ForEach-Object { $_.Caption + ' (' + $_.OSArchitecture + ')' }");
        return res.isEmpty() ? System.getProperty("os.name") : res.get(0);
    }

    public static String obtenerNombreEquipo() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (Exception e) {
            return "NO DISPONIBLE";
        }
    }

    /**
     * Obtiene la dirección IPv4 y MAC activas usando API nativa de Java (instantáneo)
     */
    public static String obtenerRedIpMac() {
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface ni = interfaces.nextElement();
                if (!ni.isUp() || ni.isLoopback() || ni.isVirtual()) {
                    continue;
                }
                byte[] macBytes = ni.getHardwareAddress();
                if (macBytes == null || macBytes.length == 0) {
                    continue;
                }
                Enumeration<InetAddress> direcciones = ni.getInetAddresses();
                while (direcciones.hasMoreElements()) {
                    InetAddress addr = direcciones.nextElement();
                    if (addr instanceof Inet4Address && !addr.isLoopbackAddress()) {
                        StringBuilder mac = new StringBuilder();
                        for (int i = 0; i < macBytes.length; i++) {
                            mac.append(String.format("%02X%s", macBytes[i], (i < macBytes.length - 1) ? ":" : ""));
                        }
                        return "IP: " + addr.getHostAddress() + " | MAC: " + mac.toString();
                    }
                }
            }
        } catch (Exception e) {
            // Si falla la lectura de adaptadores
        }
        return "IP: NO DISPONIBLE | MAC: NO DISPONIBLE";
    }

    /**
     * Permite ejecutar el agente recolector de forma INDEPENDIENTE desde consola (Run As -> Java Application)
     */
    public static void main(String[] args) {
        System.out.println("=== AGENTE DE AUDITORÍA DE INVENTARIO NETCOM (MODO INDEPENDIENTE) ===");
        System.out.println("Equipo (Hostname): " + obtenerNombreEquipo());
        System.out.println("Sistema Operativo: " + obtenerSistemaOperativo());
        System.out.println("Red:               " + obtenerRedIpMac());
        System.out.println("Placa Base:        " + obtenerPlacaBase());
        System.out.println("Serial Placa:      " + obtenerSerialPlaca());
        System.out.println("UUID Sistema:      " + obtenerUuidSistema());
        System.out.println("Procesador (CPU):  " + obtenerProcesador());
        System.out.println("Memoria RAM:       " + obtenerMemoriaRAM());
        System.out.println("Discos detectados:");
        for (String disco : obtenerDiscosAlmacenamiento()) {
            System.out.println("  -> " + disco);
        }
        System.out.println("=====================================================================");
    }
}