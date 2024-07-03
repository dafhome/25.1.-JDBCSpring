package com.tiendaplantas.tienda_plantas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ConexionDB {
  public static Connection CreateDB() {
    Connection conexion1 = null; 
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      conexion1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/tienda_plantas", "root",
          "Andy5100");
      // System.out.println("Conexión realizada correctamente");



    } catch (Exception e) {
      System.out.println("no funciona");
    }
    return conexion1;
  }
}
