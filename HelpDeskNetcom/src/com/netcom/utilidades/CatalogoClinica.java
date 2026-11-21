package com.netcom.utilidades;

import java.util.ArrayList;
import java.util.List;

public class CatalogoClinica {

    /**
     * Devuelve el listado oficial de áreas y consultorios de las 3 sedes.
     * Si incluirTodas es true, agrega "Todas las áreas" al inicio (para filtros de exportación).
     */
    public static String[] obtenerAreasClinicas(boolean incluirTodas) {
        List<String> areas = new ArrayList<>();

        if (incluirTodas) {
            areas.add("Todas las áreas");
        }

        // --- URGENCIAS ---
        areas.add("Urgencias - Hospitalización");
        areas.add("Urgencias - Observación Adultos");
        areas.add("Urgencias - Sala ERA");
        areas.add("Urgencias - Trauma");
        areas.add("Urgencias - Triage");
        areas.add("Urgencias - Parto");
        areas.add("Urgencias - Pediatría");

        // --- CONSULTA EXTERNA ---
        areas.add("Consulta Externa - Vacunación");
        areas.add("Consulta Externa - Laboratorio");
        areas.add("Consulta Externa - Estadística");
        areas.add("Consulta Externa - Nutrición");
        areas.add("Consulta Externa - Prenatal");
        areas.add("Consulta Externa - Psicología");
        areas.add("Consulta Externa - Fisioterapia");
        for (int i = 1; i <= 10; i++) {
            areas.add("Consulta Externa - Consultorio " + i);
        }
        areas.add("Consulta Externa - Cartera");

        // --- ALMACÉN ---
        areas.add("Almacén");

        // --- ADMINISTRATIVO ---
        areas.add("Administrativo - Gerencia");
        areas.add("Administrativo - Financiera");
        areas.add("Administrativo - Contratación");
        areas.add("Administrativo - Calidad");
        areas.add("Administrativo - SST");
        areas.add("Administrativo - Control Interno");
        areas.add("Administrativo - Tesorería");

        // --- PAS MALPASO ---
        areas.add("PAS Malpaso - Vacunación");
        areas.add("PAS Malpaso - Consultorio");
        areas.add("PAS Malpaso - Facturación");

        // --- PAS NUEVO GIRÓN ---
        areas.add("PAS Nuevo Girón - Vacunación");
        areas.add("PAS Nuevo Girón - Laboratorio");
        areas.add("PAS Nuevo Girón - Odontología");
        for (int i = 1; i <= 3; i++) {
            areas.add("PAS Nuevo Girón - Consultorio " + i);
        }
        areas.add("PAS Nuevo Girón - Facturación");

        // --- TI ---
        areas.add("Soporte TI");

        return areas.toArray(new String[0]);
    }
}