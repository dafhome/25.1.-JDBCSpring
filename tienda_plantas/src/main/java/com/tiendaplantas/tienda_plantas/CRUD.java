package com.tiendaplantas.tienda_plantas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import com.tiendaplantas.tienda_plantas.Metodos.Metodos;

public class CRUD {
    public static void mostrarUno(Connection conexion, int id) {
        try {
            String format = "%-5s | %-15s | %-4s | %-6s | %-6s | %-5s%n";
            System.out.printf(format, "Id", "Nombre", "Fam", "SubFam", "Precio", "IVA");
            System.out.println("-------------------------------------------------------");
            String query = "SELECT * FROM productos WHERE id = ?";
            PreparedStatement prep = conexion.prepareStatement(query);
            prep.setInt(1, id);
            ResultSet resultado = prep.executeQuery();
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
        } catch (Exception e) {
            System.out.println("Error");
        }

    }

    public static boolean ComprobarId(Connection conexion, int id) {
  
        try {
            String sql = "SELECT count(*) as resultados FROM productos WHERE id = ?";
            PreparedStatement prep = conexion.prepareStatement(sql);
            prep.setInt(1, id);
            ResultSet resultado = prep.executeQuery();
            int numResultados = 0;
            while (resultado.next()) {
                numResultados = resultado.getInt(1);
            }

            if (numResultados == 1) {
                mostrarUno(conexion, id);
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

    public static void Insert(Scanner scan, Connection connection) {
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
        String query = "INSERT INTO tienda_plantas.productos (id, nombre, id_familia, id_subfamilia, precio, iva) VALUES(?, ?, ?, ?, ?, ?);";
        try {
            PreparedStatement pStat = connection.prepareStatement(query);
            pStat.setInt(1, id);
            pStat.setString(2, nombre);
            pStat.setInt(3, familia);
            pStat.setInt(4, subFamilia);
            pStat.setDouble(5, precio);
            pStat.setDouble(6, iva);
            pStat.execute();
            System.out.println("Artículo insertado correctamente.");
        } catch (SQLException e) {
            System.out.println("Ha habido un error");
        }
        System.out.println();
        mostrarUno(connection, id);
    }

    public static void Update(Connection conexion, Scanner scan) {
        System.out.println("¿Que artículo quieres modificar?");
        System.out.print("Introduce el ID con valores numericos: ");
        int id = Metodos.validarInt(scan);
        System.out.println();
        boolean existe = ComprobarId(conexion, id);
        if (existe) {
            System.out.println("¿Que quieres modificar de este artículo?");
            int opcion;
            String campo = "";
            boolean validar = false;
            String nombre = "";
            int id_familia = 0;
            Double precio = 0.00;
            Double iva = 0.00;
            try {
                while (!validar) {
                    System.out.println("1-Nombre\n2.Familia\n3.Precio\n4.IVA\n0.No modificar\n");
                    System.out.print("Opcion: ");
                    opcion = Metodos.validarInt(scan);
                    switch (opcion) {
                        case 1:
                            campo = "nombre";
                            System.out.print("Nuevo nombre: ");
                            nombre = scan.nextLine();
                            break;
                        case 2:
                            campo = "id_familia";
                            System.out.print("Nueva familia: ");
                            id_familia = Metodos.validarInt(scan);

                            break;
                        case 3:
                            campo = "precio";
                            System.out.print("Nuevo precio: ");
                            precio = Metodos.validarDouble(scan);

                            break;
                        case 4:
                            campo = "iva";
                            System.out.print("Nuevo IVA: ");
                            iva = Metodos.validarDouble(scan);

                            break;
                        case 0:
                            System.out.println("Ya salimos sin modificar nada mas en este artículo!");
                            mostrarUno(conexion, id);
                            validar = true;
                            break;
                        default:
                            System.out.println("Selecciona una opción valida.");
                            break;
                    }
                    if (opcion > 0 && opcion < 5) {
                        String query = "UPDATE productos SET " + campo + " = ? WHERE id = ?";
                        PreparedStatement prep = conexion.prepareStatement(query);
                        if (opcion == 1) {
                            prep.setString(1, nombre);
                        } else if (opcion == 2) {
                            prep.setInt(1, id_familia);
                        } else if (opcion == 3) {
                            prep.setDouble(1, precio);
                        } else {
                            prep.setDouble(1, iva);
                        }
                        prep.setInt(2, id);
                        prep.execute();
                        System.out.println("Valor modificado con exito.");
                        System.out.println();
                    }

                }

            } catch (Exception e) {
                System.out.println("Error en el proceso");
            }
        }

    }



    public static void Delete(Scanner scan, Connection conexion) {
        System.out.println("¿Que artículo quieres modificar?");
        System.out.print("Introduce el ID con valores numericos: ");
        int id = Metodos.validarInt(scan);
        System.out.println();
        boolean existe = ComprobarId(conexion, id);
        if (existe) {
            System.out.println("¿Que quieres eliminar?");
            int opcion;
            String campo;
            boolean validar = false;
            String query = "DELETE FROM productos WHERE id = ? ";
            try {
                PreparedStatement prep = conexion.prepareStatement(query);
                System.out.println("Estas seguro de eliminar este articulo?");
                String pregunta = scan.nextLine();
                if (pregunta.equalsIgnoreCase("si")) {
                    prep.setInt(1, id);
                    prep.execute();
                    System.out.println("Articulo borrado.");
                } else {
                    System.out.println("Menos mal, no lo hemos borrado finalmente.");
                }
            } catch (Exception e) {
                System.out.println("Error en el proceso");
            }
        }

    }

}
