package com.example.Modelo;

import java.util.ArrayList;
import java.util.List;

public class Datos {

	private static ArrayList<Propietario> propietarios = new ArrayList<>();

	private static ArrayList<Puesto> puestos = new ArrayList<>();

	private static ArrayList<Categoria> categorias = new ArrayList<>();

	private static ArrayList<Vehiculo> vehiculos = new ArrayList<>();
    
	private static ArrayList<Administrador> administradores = new ArrayList<>();

	/**
	 * Carga (o recarga) los datos iniciales en memoria.
	 * Es idempotente: limpia la lista y vuelve a agregar los valores por defecto.
	 */
	public static void cargar() {
		propietarios.clear();
		propietarios.add(new Propietario(
				"Usuario",
				"Propietario",
				"prop.123",
				"23456789",
				2000.0,
				500.0,
				new Estado("ACTIVO"), // estado por defecto
				new ArrayList<>(), // vehiculos
				new ArrayList<>(), // notificaciones
				new ArrayList<>() // asignaciones
		));

		// Cargar administradores de precarga
		administradores.clear();
		administradores.add(new Administrador("Usuario",
		 "Administrador",
		  "12345678",
		   "admin.123"));

		// Cargar puestos (peajes de Uruguay - lista representativa)
		puestos.clear();
		puestos.add(new Puesto("Peaje Colonia", "Ruta 1 - Colonia"));
		puestos.add(new Puesto("Peaje Carmelo", "Ruta 21 - Carmelo"));
		puestos.add(new Puesto("Peaje Nueva Palmira", "Ruta 21 - Nueva Palmira"));
		puestos.add(new Puesto("Peaje Paysandú", "Ruta 3 - Paysandú"));
		puestos.add(new Puesto("Peaje Salto Grande", "Ruta 3 - Salto"));
		puestos.add(new Puesto("Peaje Río Branco", "Ruta 8 - Río Branco"));
		puestos.add(new Puesto("Peaje Artigas", "Ruta 30 - Artigas"));
		puestos.add(new Puesto("Peaje Pando", "Ruta Interbalnearia - Pando"));
		puestos.add(new Puesto("Peaje Maldonado", "Ruta Interbalnearia - Maldonado"));
		puestos.add(new Puesto("Peaje Montevideo - Acceso", "Accesos a Montevideo"));

		// Cargar categorías de vehículos
		categorias.clear();
		categorias.add(new Categoria("Auto"));
		categorias.add(new Categoria("Moto"));
		categorias.add(new Categoria("Camión"));
		categorias.add(new Categoria("Omnibus"));

		// Añadir tarifas solicitadas
		agregarTarifa("Peaje Colonia", "Auto", 160.0);
		agregarTarifa("Peaje Colonia", "Moto", 90.0);
		agregarTarifa("Peaje Colonia", "Camión", 215.0);
		agregarTarifa("Peaje Colonia", "Omnibus", 290.0);

		agregarTarifa("Peaje Carmelo", "Auto", 135.0);
		agregarTarifa("Peaje Carmelo", "Moto", 78.0);
		agregarTarifa("Peaje Carmelo", "Camión", 190.0);
		agregarTarifa("Peaje Carmelo", "Omnibus", 250.0);
        
		agregarTarifa("Peaje Nueva Palmira", "Auto", 150.0);
		agregarTarifa("Peaje Nueva Palmira", "Moto", 85.0);
		agregarTarifa("Peaje Nueva Palmira", "Camión", 180.0);
		agregarTarifa("Peaje Nueva Palmira", "Omnibus", 220.0);

		agregarTarifa("Peaje Paysandú", "Auto", 140.0);
		agregarTarifa("Peaje Paysandú", "Moto", 80.0);
		agregarTarifa("Peaje Paysandú", "Camión", 210.0);
		agregarTarifa("Peaje Paysandú", "Omnibus", 225.0);

		agregarTarifa("Peaje Salto Grande", "Auto", 145.0);
		agregarTarifa("Peaje Salto Grande", "Moto", 82.0);
		agregarTarifa("Peaje Salto Grande", "Camión", 205.0);
		agregarTarifa("Peaje Salto Grande", "Omnibus", 240.0);

		agregarTarifa("Peaje Río Branco", "Auto", 155.0);
		agregarTarifa("Peaje Río Branco", "Moto", 88.0);
		agregarTarifa("Peaje Río Branco", "Camión", 220.0);
		agregarTarifa("Peaje Río Branco", "Omnibus", 240.0);

		agregarTarifa("Peaje Artigas", "Auto", 150.0);
		agregarTarifa("Peaje Artigas", "Moto", 85.0);
		agregarTarifa("Peaje Artigas", "Camión", 215.0);
		agregarTarifa("Peaje Artigas", "Omnibus", 270.0);

		agregarTarifa("Peaje Pando", "Auto", 130.0);
		agregarTarifa("Peaje Pando", "Moto", 75.0);
		agregarTarifa("Peaje Pando", "Camión", 195.0);
		agregarTarifa("Peaje Pando", "Omnibus", 290.0);

		agregarTarifa("Peaje Maldonado", "Auto", 170.0);
		agregarTarifa("Peaje Maldonado", "Moto", 95.0);
		agregarTarifa("Peaje Maldonado", "Camión", 240.0);
		agregarTarifa("Peaje Maldonado", "Omnibus", 290.0);

		agregarTarifa("Peaje Montevideo - Acceso", "Auto", 120.0);
		agregarTarifa("Peaje Montevideo - Acceso", "Moto", 70.0);
		agregarTarifa("Peaje Montevideo - Acceso", "Camión", 180.0);
		agregarTarifa("Peaje Montevideo - Acceso", "Omnibus", 250.0);

		// Cargar vehículos de precarga y asignarlos al propietario precargado
		vehiculos.clear();
		Categoria catAuto = getCategoriaPorNombre("Auto");
		Categoria catMoto = getCategoriaPorNombre("Moto");
        Categoria catCamion = getCategoriaPorNombre("Camión");
		Categoria catOmnibus = getCategoriaPorNombre("Omnibus");
		if (catAuto != null) {
			Vehiculo v1 = new Vehiculo("ABC1234", "Blanco", "Toyota Corolla", catAuto);
			Vehiculo v2 = new Vehiculo("DEF5678", "Azul", "Ford F-150", catAuto);
            Vehiculo cam2 = new Vehiculo("TRK777", "Blanco", "Volvo FH (2014) - 420000km", catCamion);
			Vehiculo cam3 = new Vehiculo("TRK888", "Negro", "Iveco Stralis (2012) - 350000km", catCamion);
            Vehiculo bus2 = new Vehiculo("BUS101", "Azul", "Mercedes O500 (2010) - 500000km", catOmnibus);
			Vehiculo bus3 = new Vehiculo("BUS202", "Blanco/Azul", "Volvo B12 (2008) - 600000km", catOmnibus);
			Vehiculo m1 = new Vehiculo("MOTO001", "Rojo", "Yamaha YBR", catMoto);

			vehiculos.add(v1);
			vehiculos.add(v2);
            vehiculos.add(cam2);
            vehiculos.add(cam3);
            vehiculos.add(bus2);
            vehiculos.add(bus3);
            vehiculos.add(m1);
			
            // asignar al propietario con cédula 23456789
			Propietario prop = getPropietarioPorCedula("23456789");
			if (prop != null) {
				prop.getVehiculos().add(v1);
				prop.getVehiculos().add(m1);
			}
		}
	}

	public static List<Categoria> getCategorias() {

		return categorias;
	}

	// Helpers para manejo interno de datos (tarifas)
	private static Categoria cat(String nombre) {
		return getCategoriaPorNombre(nombre);
	}

	private static Puesto pst(String nombre) {
		return getPuestoPorNombre(nombre);
	}

	private static void agregarTarifa(String nombrePuesto, String nombreCategoria, double monto) {
		Puesto p = pst(nombrePuesto);
		Categoria c = cat(nombreCategoria);
		if (p != null && c != null) {
			p.getTarifas().add(new Tarifa(monto, c));
		}
	}

	public static Categoria getCategoriaPorNombre(String nombre) {
		for (Categoria c : categorias) {
			if (c.getNombre() != null && c.getNombre().equalsIgnoreCase(nombre)) {
				return c;
			}
		}
		return null;
	}

	public static List<Administrador> getAdministradores() {
		return administradores;
	}

	public static Administrador getAdministradorPorCedula(String cedula) {
		for (Administrador a : administradores) {
			if (a.getCedula() != null && a.getCedula().equals(cedula)) {
				return a;
			}
		}
		return null;
	}

	public static List<Puesto> getPuestos() {
		return puestos;
	}

	public static Puesto getPuestoPorNombre(String nombre) {
		for (Puesto p : puestos) {
			if (p.getNombre() != null && p.getNombre().equalsIgnoreCase(nombre)) {
				return p;
			}
		}
		return null;
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
