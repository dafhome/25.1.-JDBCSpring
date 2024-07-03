package com.tiendaplantas.tienda_plantas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import com.tiendaplantas.tienda_plantas.Metodos.Metodos;

import ch.qos.logback.core.joran.spi.RuleStore;

public class CRUD {
    public static void mostrarUno(Statement statement, int id) {
        try{
            String format = "%-5s | %-15s | %-4s | %-6s | %-6s | %-5s%n";
        System.out.printf(format, "Id", "Nombre", "Fam", "SubFam", "Precio", "IVA");
        System.out.println("-------------------------------------------------------");
        ResultSet resultado = statement.executeQuery("SELECT * FROM productos WHERE id = " + id + ";");
        // Filas de la tabla
        while (resultado.next()) {

            System.out.printf(format,
                    resultado.getInt(1),
                    resultado.getString(2),
                    resultado.getString(3),
                    resultado.getString(4),
                    resultado.getDouble(5),
                    resultado.getDouble(6));
        }
        System.out.println();
        }
        catch(Exception e){
            System.out.println("Error");
        }
        
    }

    public static boolean ComprobarId(Statement statement, int id) {
        // try{

        // ResultSet resultado = statement.executeQuery("SELECT * FROM
        // tienda_plantas.productos");
        // System.out.println("Id | Nombre | Familia | Subfamilia | Precio | IVA");
        // while (resultado.next()) {
        // System.out.println(resultado.getString(1)+" | "+ resultado.getString(2)+" |
        // "+resultado.getInt(3)+" | "+resultado.getInt(4)+" |
        // "+resultado.getDouble(5)+" | "+resultado.getDouble(6));
        // } }
        // catch (Exception e){
        // System.out.println("Error en la consulta.");
        // }
        try {
            ResultSet resultado = statement
                    .executeQuery("SELECT count(*) as resultados FROM productos WHERE id = " + id + ";");
            int numResultados = 0;
            while (resultado.next()) {
                numResultados = resultado.getInt(1);
            }
            // int numResultados = 0;
            // while (resultado.next()) {
            // numResultados++;
            // }

            // Título de la tabla

            if (numResultados == 1) {
                mostrarUno(statement, id);
                return true;
            } else {
                System.out.println("Lo siento, no existe ningún artículo con este id");
                return false;
            }

        } catch (Exception e) {
            System.out.println("Error en la consulta.");
            return false;
        }

    }

    public static void ReadTodo(Statement statement) {
        try {
            ResultSet resultado = statement.executeQuery("SELECT * FROM productos");

            // Título de la tabla
            String format = "%-5s | %-15s | %-4s | %-6s | %-6s | %-5s%n";
            System.out.printf(format, "Id", "Nombre", "Fam", "SubFam", "Precio", "IVA");
            System.out.println("-------------------------------------------------------");

            // Filas de la tabla
            while (resultado.next()) {
                System.out.printf(format,
                        resultado.getInt(1),
                        resultado.getString(2),
                        resultado.getString(3),
                        resultado.getString(4),
                        resultado.getDouble(5),
                        resultado.getDouble(6));
            }
        } catch (Exception e) {
            System.out.println("Error en la consulta.");
        }

    }

    public static void Insert(Scanner scan, Statement statement) {
        System.out.println("Necesito que me indique los valores del nuevo articulo");
        System.out.print("Id: ");
        int id = Metodos.validarInt(scan);
        System.out.print("Nombre: ");
        String nombre = scan.nextLine();
        System.out.print("Familia: ");
        int familia = Metodos.validarInt(scan);
        System.out.print("SubFamilia: ");
        int subFamilia = Metodos.validarInt(scan);
        System.out.print("Precio: ");
        double precio = Metodos.validarDouble(scan);
        System.out.print("IVA: ");
        double iva = Metodos.validarDouble(scan);
        String query = "INSERT INTO tienda_plantas.productos (id, nombre, id_familia, id_subfamilia, precio, iva) VALUES('"+id+"', '"+nombre+"', '"+familia+"', '"+subFamilia+"', '"+precio+"', '"+iva+"');";
        execUpdate(statement, query);
        mostrarUno(statement, id);
    }

    public static void Update(Statement statement, Scanner scan) {
        System.out.println("¿Que artículo quieres modificar?");
        System.out.print("Introduce el ID con valores numericos: ");
        int id = Metodos.validarInt(scan);
        // System.out.println("Estas seguro que quieres modificar este artículo?");
        System.out.println();
        boolean existe = ComprobarId(statement, id);
        if (existe) {
            System.out.println("¿Que quieres modificar de este artículo?");
            int opcion;
            String campo;
            boolean validar = false;
            String query1 = "UPDATE productos SET ";
            String query2 = " WHERE id = " + id;
            String query;
            String nombre;
            int id_familia;
            Double precio;
            Double iva;
            while (!validar) {
                System.out.println("1-Nombre\n2.Familia\n3.Precio\n4.IVA\n0.No modificar\n");
                System.out.print("Opcion: ");
                opcion = Metodos.validarInt(scan);
                switch (opcion) {
                    case 1:
                        campo = "nombre";
                        System.out.print("Nuevo nombre: ");
                        nombre = scan.nextLine();
                        query = query1 + campo + " = " +"'"+ nombre+"'" + query2;
                        execUpdate(statement, query);
                        break;
                    case 2:
                        campo = "id_familia";
                        System.out.print("Nueva familia: ");
                        id_familia = Metodos.validarInt(scan);
                        query = query1 + campo + " = '" + id_familia+"'" + query2;
                        execUpdate(statement, query);
                        break;
                    case 3:
                        campo = "precio";
                        System.out.print("Nuevo precio: ");
                        precio = Metodos.validarDouble(scan);
                        query = query1 + campo + " = '" + precio+"'"  + query2;
                        execUpdate(statement, query);
                        break;
                    case 4:
                        campo = "iva";
                        System.out.print("Nuevo IVA: ");
                        iva = Metodos.validarDouble(scan);
                        query = query1 + campo + " = '" + iva+"'" + query2;
                        execUpdate(statement, query);
                        break;
                    case 0:
                        System.out.println("Ya salimos sin modificar nada mas en este artículo!");
                        mostrarUno(statement, id);
                        validar = true;
                        break;
                    default:
                        System.out.println("Selecciona una opción valida.");
                        break;
                }
            }
        }

    }

    public static void execUpdate(Statement statement, String query) {
        try {
            statement.executeUpdate(query);
            System.out.println("Se ha ejecutado correctamente.");
        } catch (Exception e) {
            System.out.println("Error en el proceso.");
        }

    }

    public static void Delete(Scanner scan, Statement statement) {
        System.out.println("¿Que artículo quieres modificar?");
        System.out.print("Introduce el ID con valores numericos: ");
        int id = Metodos.validarInt(scan);
        // System.out.println("Estas seguro que quieres modificar este artículo?");
        System.out.println();
        boolean existe = ComprobarId(statement, id);
        if (existe) {
            System.out.println("¿Que quieres eliminar?");
            int opcion;
            String campo;
            boolean validar = false;
            String query = "DELETE FROM productos WHERE id = "+id;
            System.out.println("Estas seguro de eliminar este articulo?");
            String pregunta = scan.nextLine();
            if (pregunta.equalsIgnoreCase("si")) {
                execUpdate(statement, query);     
                System.out.println("Articulo borrado.");           
            }
            else{
                System.out.println("Menos mal, no lo hemos borrado finalmente.");
            }
        }
        

    }

}
