package com.tiendaplantas.tienda_plantas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Scanner;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.tiendaplantas.tienda_plantas.Metodos.Metodos;

@SpringBootApplication
public class TiendaPlantasApplication {

	public static void main(String[] args) {
		// SpringApplication.run(TiendaPlantasApplication.class, args);
		System.out.println("Bienvenido a la tienda de plantas!");
		Connection conexion = ConexionDB.CreateDB();
		Statement stat = null;
		Scanner scan = new Scanner(System.in);
		try {
			stat = conexion.createStatement();

			// System.out.println("Statement creado correctamente");
		} catch (Exception e) {
			System.out.println("No ha funcionado: Statement");
		}
		// CRUD.ReadTodo(stat);
		int opcion;
		boolean validar = false;
		while (!validar) {
			System.out.println();
			System.out.println("1-Ver Articulos\n2-Modificar articulo\n3-Insertar articulo\n4-Eliminar Articulo\n0-Salir");
			System.out.println("-------------------------------------------------------");
			System.out.print("¿Que quieres hacer? ");
			opcion = Metodos.validarInt(scan);
			switch (opcion) {
				case 1:
					System.out.println("Nuestros articulos son:");
					System.out.println();
					CRUD.ReadTodo(stat);
					break;
				case 2:
				CRUD.Update(stat, scan);
					break;
				case 3:
				CRUD.Insert(scan, stat);
					break;
				case 4:
				CRUD.Delete(scan, stat);
					break;
				case 0:
					validar = true;
					System.out.println("Gracias por venir!");
					break;
				default:
					System.out.println("Introduce una opción disponible");
					break;
			}
		}
		

	}

}
