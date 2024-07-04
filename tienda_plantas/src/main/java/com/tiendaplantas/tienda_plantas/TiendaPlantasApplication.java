package com.tiendaplantas.tienda_plantas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Scanner;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.tiendaplantas.tienda_plantas.Metodos.Metodos;

@SpringBootApplication
public class TiendaPlantasApplication {

	public static void main(String[] args) {
		// Resumen de la Lección :pencil2:
		// - En vez de poner comillas dentro de comillas para insertar datos, es más actual, claro, seguro y eficiente el usar la plantilla para consultas llamada PreparedStatement
		// En la consulta, para los datos variables, en vez de la variable entre comillas, simplemente insertamos un signo de interrogación (?), de esta manera:
		// String actualizarTitulo = "UPDATE discos SET titulo = ? WHERE id = ?";
		// En el orden que aparecen los ? podemos insertar los datos necesarios, previa declaración de PreparedStatement:
		// PreparedStatement prep = conexion1.prepareStatement(actualizar);
		//  prep.setInt(1, lanza);                   // orden ? y año lanzamiento
		//  prep.setInt(2, idLanza);              // orden ? y num id
		// y entonces, ejecutarlo con:
		// prep.execute();
		


		// SpringApplication.run(TiendaPlantasApplication.class, args);
		System.out.println("Bienvenido a la tienda de plantas!");
		Connection conexion = ConexionDB.CreateDB();
		Statement stat = null;
		PreparedStatement prepStat = null;
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
				CRUD.Update(conexion, scan);
					break;
				case 3:
				CRUD.Insert(scan,conexion);
					break;
				case 4:
				CRUD.Delete(scan, conexion);
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
