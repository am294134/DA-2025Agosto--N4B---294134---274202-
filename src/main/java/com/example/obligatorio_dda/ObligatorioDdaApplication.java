package com.example.obligatorio_dda;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.obligatorio_dda.Modelo.Datos;
import com.example.obligatorio_dda.Modelo.PeajeException;

@SpringBootApplication
public class ObligatorioDdaApplication {

	public static void main(String[] args) throws PeajeException {
		SpringApplication.run(ObligatorioDdaApplication.class, args);
		Datos.cargar();
	}
}
