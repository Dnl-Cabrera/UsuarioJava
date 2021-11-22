package DB;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;

//import utilidades.Globales;

public class Conexion {

    public static Connection SQlite_conexion(){

        try {
            
            Class.forName("org.sqlite.JDBC"); // Es como obtener el driver que conecta con la base de datos, este varia dependiendo de la base de datos utilizada.
            //Si es mysql se debe implementar otro, esta libreria se debe adjuntar en el proyecto en referenced

            //Estamos mirando la direccion en la que se encuentra la base de datos, esta direccion es desde cualquier computador.
            System.out.println("jdbc:sqlite:" + System.getProperty("user.dir")+File.separator+"database"+File.separator+"usuario_DB.db" );

            //Estamos realizando la conexión con el driver indicandole la ubicacion de la base de datos.
            Connection connection = DriverManager.getConnection("jdbc:sqlite:" + System.getProperty("user.dir")+File.separator+"database"
            +File.separator+"usuario_DB.db");
            System.out.println("Funciona");
            return connection;

        } catch (Exception e) {
            //TODO: handle exception
            System.out.println("No Funciona");
            return null;
        }

    }
  
}
