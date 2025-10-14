package com.example.Modelo;

import java.util.ArrayList;
import java.util.List;

public class Datos {

	private static ArrayList<Propietario> propietarios = new ArrayList<>();

	/**
	 * Carga (o recarga) los datos iniciales en memoria.
	 * Es idempotente: limpia la lista y vuelve a agregar los valores por defecto.
	 */
	public static void cargar() {
		propietarios.clear();
		propietarios.add(new Propietario(
				"Usuario", // nombre
				"Propietario", // apellido
				"prop.123", // contrasenia
				"23456789", // cedula
				2000.0, // saldoActual
				500.0, // saldoMinimo
				new Estado("ACTIVO"), // estado por defecto
				new ArrayList<>(), // vehiculos
				new ArrayList<>(), // notificaciones
				new ArrayList<>() // asignaciones
		));
	}

	public static List<Propietario> getPropietarios() {
		return propietarios;
	}

	public static Propietario getPropietarioPorCedula(String cedula) {
		for (Propietario p : propietarios) {
			if (p.getCedula() != null && p.getCedula().equals(cedula)) {
				return p;
			}
		}
		return null;
	}

}
