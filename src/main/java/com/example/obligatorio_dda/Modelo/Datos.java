package com.example.obligatorio_dda.Modelo;

public class Datos {
    public Datos() {
    }

    public static void cargar() throws PeajeException {
        Fachada fachada = Fachada.getInstancia();

        // #region usuarios
        // Administradores
        fachada.agregarAdministrador("Prueba", "Uno", "1", "a");
        fachada.agregarAdministrador("Admin", "Principal", "0001", "admin123");
        fachada.agregarAdministrador("Usuario", "Administrador", "12345678", "admin.123");

        // Propietarios
        fachada.agregarPropietario("Juan", "Perez", "12345678", "juanpass", 5000.0, 1000.0, new Estado("Habilitado"));
        fachada.agregarPropietario("Maria", "Gomez", "87654321", "mariapass", 3000.0, 500.0, new Estado("Habilitado"));
        fachada.agregarPropietario("Usuario", "Propietario", "23456789", "prop.123", 2000, 500.0,
                new Estado("Habilitado"));
        // #endregion

        // #region Categorías
        fachada.agregarCategoria("Camión");
        fachada.agregarCategoria("Automóvil");
        fachada.agregarCategoria("Moto");
        fachada.agregarCategoria("Omnibus");
        // #endregion

        // #region Peajes y sus tarifas
        fachada.agregarPuesto("Peaje de Pando Interbalnearia", "Km 32.500 Ruta Interbalnearia");
        fachada.agregarTarifa("Peaje de Pando Interbalnearia", "Automóvil", 110.0);
        fachada.agregarTarifa("Peaje de Pando Interbalnearia", "Moto", 70.0);
        fachada.agregarTarifa("Peaje de Pando Interbalnearia", "Camión", 330.0);
        fachada.agregarTarifa("Peaje de Pando Interbalnearia", "Omnibus", 330.0);

        fachada.agregarPuesto("Peaje Solís", "Km 80.000 Ruta Interbalnearia");
        fachada.agregarTarifa("Peaje Solís", "Automóvil", 110.0);
        fachada.agregarTarifa("Peaje Solís", "Moto", 70.0);
        fachada.agregarTarifa("Peaje Solís", "Camión", 330.0);
        fachada.agregarTarifa("Peaje Solís", "Omnibus", 330.0);

        fachada.agregarPuesto("Peaje Garzón", "Km 187.500 Ruta 9");
        fachada.agregarTarifa("Peaje Garzón", "Automóvil", 125.0);
        fachada.agregarTarifa("Peaje Garzón", "Moto", 85.0);
        fachada.agregarTarifa("Peaje Garzón", "Camión", 375.0);
        fachada.agregarTarifa("Peaje Garzón", "Omnibus", 375.0);

        fachada.agregarPuesto("Peaje Cebollatí", "Km 302.000 Ruta 8");
        fachada.agregarTarifa("Peaje Cebollatí", "Automóvil", 115.0);
        fachada.agregarTarifa("Peaje Cebollatí", "Moto", 75.0);
        fachada.agregarTarifa("Peaje Cebollatí", "Camión", 345.0);
        fachada.agregarTarifa("Peaje Cebollatí", "Omnibus", 345.0);

        fachada.agregarPuesto("Peaje Paso del Puerto", "Km 244.500 Ruta 3");
        fachada.agregarTarifa("Peaje Paso del Puerto", "Automóvil", 110.0);
        fachada.agregarTarifa("Peaje Paso del Puerto", "Moto", 70.0);
        fachada.agregarTarifa("Peaje Paso del Puerto", "Camión", 330.0);
        fachada.agregarTarifa("Peaje Paso del Puerto", "Omnibus", 330.0);

        fachada.agregarPuesto("Peaje Mercedes", "Km 278.000 Ruta 2");
        fachada.agregarTarifa("Peaje Mercedes", "Automóvil", 120.0);
        fachada.agregarTarifa("Peaje Mercedes", "Moto", 80.0);
        fachada.agregarTarifa("Peaje Mercedes", "Camión", 360.0);
        fachada.agregarTarifa("Peaje Mercedes", "Omnibus", 360.0);

        fachada.agregarPuesto("Peaje Queguay", "Km 440.500 Ruta 3");
        fachada.agregarTarifa("Peaje Queguay", "Automóvil", 115.0);
        fachada.agregarTarifa("Peaje Queguay", "Moto", 75.0);
        fachada.agregarTarifa("Peaje Queguay", "Camión", 345.0);
        fachada.agregarTarifa("Peaje Queguay", "Omnibus", 345.0);

        fachada.agregarPuesto("Peaje Paso de los Toros", "Km 249.500 Ruta 5");
        fachada.agregarTarifa("Peaje Paso de los Toros", "Automóvil", 110.0);
        fachada.agregarTarifa("Peaje Paso de los Toros", "Moto", 70.0);
        fachada.agregarTarifa("Peaje Paso de los Toros", "Camión", 330.0);
        fachada.agregarTarifa("Peaje Paso de los Toros", "Omnibus", 330.0);

        fachada.agregarPuesto("Peaje Baygorria", "Km 317.500 Ruta 4");
        fachada.agregarTarifa("Peaje Baygorria", "Automóvil", 120.0);
        fachada.agregarTarifa("Peaje Baygorria", "Moto", 80.0);
        fachada.agregarTarifa("Peaje Baygorria", "Camión", 360.0);
        fachada.agregarTarifa("Peaje Baygorria", "Omnibus", 360.0);

        fachada.agregarPuesto("Peaje Manuel Díaz", "Km 390.000 Ruta 5");
        fachada.agregarTarifa("Peaje Manuel Díaz", "Automóvil", 115.0);
        fachada.agregarTarifa("Peaje Manuel Díaz", "Moto", 75.0);
        fachada.agregarTarifa("Peaje Manuel Díaz", "Camión", 345.0);
        fachada.agregarTarifa("Peaje Manuel Díaz", "Omnibus", 345.0);

        fachada.agregarPuesto("Peaje Cufré", "Km 108.500 Ruta 1");
        fachada.agregarTarifa("Peaje Cufré", "Automóvil", 110.0);
        fachada.agregarTarifa("Peaje Cufré", "Moto", 70.0);
        fachada.agregarTarifa("Peaje Cufré", "Camión", 330.0);
        fachada.agregarTarifa("Peaje Cufré", "Omnibus", 330.0);

        fachada.agregarPuesto("Peaje Capilla de Cella", "Km 146.500 Ruta 11");
        fachada.agregarTarifa("Peaje Capilla de Cella", "Automóvil", 115.0);
        fachada.agregarTarifa("Peaje Capilla de Cella", "Moto", 75.0);
        fachada.agregarTarifa("Peaje Capilla de Cella", "Camión", 345.0);
        fachada.agregarTarifa("Peaje Capilla de Cella", "Omnibus", 345.0);

        fachada.agregarPuesto("Peaje Santa Lucía", "Km 63.500 Ruta 11");
        fachada.agregarTarifa("Peaje Santa Lucía", "Automóvil", 110.0);
        fachada.agregarTarifa("Peaje Santa Lucía", "Moto", 70.0);
        fachada.agregarTarifa("Peaje Santa Lucía", "Camión", 330.0);
        fachada.agregarTarifa("Peaje Santa Lucía", "Omnibus", 330.0);

        fachada.agregarPuesto("Peaje Ruta 8 Km 69", "Km 69.000 Ruta 8");
        fachada.agregarTarifa("Peaje Ruta 8 Km 69", "Automóvil", 110.0);
        fachada.agregarTarifa("Peaje Ruta 8 Km 69", "Moto", 70.0);
        fachada.agregarTarifa("Peaje Ruta 8 Km 69", "Camión", 330.0);
        fachada.agregarTarifa("Peaje Ruta 8 Km 69", "Omnibus", 330.0);

        fachada.agregarPuesto("Peaje Ciclopista Montevideo-Las Piedras", "Km 22.500 Ruta 5");
        fachada.agregarTarifa("Peaje Ciclopista Montevideo-Las Piedras", "Automóvil", 105.0);
        fachada.agregarTarifa("Peaje Ciclopista Montevideo-Las Piedras", "Moto", 65.0);
        fachada.agregarTarifa("Peaje Ciclopista Montevideo-Las Piedras", "Camión", 315.0);
        fachada.agregarTarifa("Peaje Ciclopista Montevideo-Las Piedras", "Omnibus", 315.0);
        // #endregion

        // #region Vehículos
        // Autos
        fachada.agregarVehiculo("SAB1234", "Gris", "Toyota Corolla", "Automóvil", "12345678");
        fachada.agregarVehiculo("ABC1234", "Negro", "Volkswagen Golf", "Automóvil", "87654321");
        fachada.agregarVehiculo("DEF5678", "Blanco", "Honda Civic", "Automóvil", "23456789");
        fachada.agregarVehiculo("GHI9012", "Rojo", "Ford Focus", "Automóvil", "34567890");
        fachada.agregarVehiculo("JKL3456", "Azul", "Chevrolet Cruze", "Automóvil", "45678901");

        // Motos
        fachada.agregarVehiculo("MOT1234", "Negro", "Honda CB 250", "Moto", "12345678");
        fachada.agregarVehiculo("BIK5678", "Rojo", "Yamaha YBR 125", "Moto", "87654321");
        fachada.agregarVehiculo("CYC9012", "Azul", "Kawasaki Ninja", "Moto", "23456789");
        fachada.agregarVehiculo("MTR3456", "Verde", "Suzuki GSX", "Moto", "34567890");
        fachada.agregarVehiculo("VES7890", "Blanco", "BMW G310", "Moto", "45678901");

        // Camiones
        fachada.agregarVehiculo("CAM1234", "Blanco", "Volkswagen Worker", "Camión", "12345678");
        fachada.agregarVehiculo("TRK5678", "Rojo", "Mercedes-Benz Atego", "Camión", "87654321");
        fachada.agregarVehiculo("HVY9012", "Verde", "Scania P410", "Camión", "23456789");
        fachada.agregarVehiculo("BIG3456", "Azul", "Volvo FH", "Camión", "34567890");
        fachada.agregarVehiculo("LOD7890", "Gris", "Ford Cargo", "Camión", "45678901");

        // Omnibus
        fachada.agregarVehiculo("BUS1234", "Blanco", "Mercedes-Benz OH 1621", "Omnibus", "12345678");
        fachada.agregarVehiculo("COA5678", "Azul", "Volvo B420R", "Omnibus", "87654321");
        fachada.agregarVehiculo("TRP9012", "Verde", "Scania K400", "Omnibus", "23456789");
        fachada.agregarVehiculo("PTR3456", "Gris", "Marcopolo Paradiso", "Omnibus", "34567890");
        fachada.agregarVehiculo("LIN7890", "Rojo", "Irizar i6", "Omnibus", "45678901");
        // #endregion

    }

}
