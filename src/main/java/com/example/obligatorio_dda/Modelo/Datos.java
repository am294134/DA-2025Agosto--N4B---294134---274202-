package com.example.obligatorio_dda.Modelo;



public class Datos {
   public Datos() {
   }

   public static void cargar() {
        Fachada fachada = Fachada.getInstancia();
		fachada.agregarAdministrador("Prueba", "Uno", "1", "a");
        fachada.agregarAdministrador("Admin", "Principal", "0001", "admin123");
        fachada.agregarAdministrador("Usuario", "Administrador", "12345678", "admin.123");
        fachada.agregarPropietario("Juan", "Perez", "12345678", "juanpass", 5000.0, 1000.0, new Estado("Habilitado"));
         fachada.agregarPropietario("Maria", "Gomez", "87654321", "mariapass", 3000.0, 500.0, new Estado("Habiilitado"));
      }
}

