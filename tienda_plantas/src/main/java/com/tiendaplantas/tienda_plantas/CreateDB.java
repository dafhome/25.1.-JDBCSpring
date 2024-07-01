package com.tiendaplantas.tienda_plantas;

import java.sql.DriverManager;

import org.springframework.boot.autoconfigure.amqp.RabbitProperties.Cache.Connection;

public class CreateDB {

	public static void main(String[] args){
        try{
            // Aquí haremos la conexión
            // Indicamos el driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Hacer la conexión
            Connection conexion1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/tienda_plantas","root","Andy5100");

        }
        
        catch(Exception e){

            // Mensaje error o acciones en caso de error
            System.out.println("*** oh, nooo, error!!!");
        } 
    }
    

}
