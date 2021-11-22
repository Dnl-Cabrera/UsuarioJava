package manejoDB;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DB.Conexion;
import modelo.Paciente;

public class operacionesDB{
    

    //Este es un metodo que retorna un boolean para determinar si se realizo el registro.
    public boolean registrarPaciente(Paciente registro){

        //PrepareStatemen es para poder guardar las consultas que se enviaran a la basde de datos.
        PreparedStatement insertar=null;
        try {
            insertar=Conexion.SQlite_conexion().prepareStatement("INSERT INTO registros (nombre,cedula,genero,muestra1,muestra2,muestra3)VALUES (?,?,?,?,?,?)");

            //Recuerde que los ? sirven para agregar los datos despues, en las siguiente se esta agregando los valores segun los datos en los botones.
            insertar.setString(1,registro.getNombre());
            insertar.setInt(2, registro.getCedula());
            insertar.setString(3, registro.getGenero());

            for(int i=4;i<7;i++){
                insertar.setDouble(i, registro.getDatosParcial(i-4));
            }

            //Estamos preguntando si se realizo la consulta adecuadamente, y preguntamos si el valor es 1, la consulta se realizo, si es diferente, no.
            return insertar.executeUpdate()==1?true:false;
        
        } catch (Exception e) {
            //Importante esta linea nos permite identificar cualquier tipo de error que suceda con el codigo segun lo ejecutado en el try. Se puede simplificar utilizando las exepciones de mysql o bases de datos.
            e.printStackTrace();
            //TODO: handle exception
            System.out.println("Falla en el registro, consulta no realizada");
            return false;
        }
        finally{
            try {
                // Siempre debemos cerrar la conexion con la DB despues de cada consulta.
                insertar.close();
                Conexion.SQlite_conexion().close();
            } catch (Exception e) {
                //TODO: handle exception
                System.out.println("Falla al cerrar la base de datos");
            }
        }
    }

    //Proceso para saber si se elimino el registro adecuadamente.
    public boolean eliminarRegistro(int cedula){

        PreparedStatement eliminar=null;

        try {
            eliminar=Conexion.SQlite_conexion().prepareStatement("DELETE FROM registros WHERE cedula=?");
            eliminar.setInt(1, cedula);
            return eliminar.executeUpdate()==1?true:false;
            
        } catch (Exception e) {
            e.printStackTrace();
            //TODO: handle exception
            System.out.println("Error al eliminar registro");
            return false;
        }
        finally{
            try {
                eliminar.close();
                Conexion.SQlite_conexion().close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                System.out.println("Falla al cerrar la base de datos");
            }

        }

    }

    //Proceso para realizar si se realizo la actualización del registro.
    public boolean actualizarRegistro(Paciente actualizarPaciente){
        
        PreparedStatement actualizar=null;

        try {
            actualizar=Conexion.SQlite_conexion().prepareStatement("UPDATE registros set nombre=?,genero=?,muestra1=?,muestra2=?,muestra3=? where cedula=?");
            
            actualizar.setString(1, actualizarPaciente.getNombre());
            actualizar.setString(2, actualizarPaciente.getGenero());
            actualizar.setDouble(3, actualizarPaciente.getDatosParcial(0));
            actualizar.setDouble(4, actualizarPaciente.getDatosParcial(1));
            actualizar.setDouble(5, actualizarPaciente.getDatosParcial(2));
            actualizar.setInt(6, actualizarPaciente.getCedula());

            return actualizar.executeUpdate()==1?true:false;

        } catch (Exception e) {
            e.printStackTrace();
            //TODO: handle exception
            System.out.println("Falla en la actualización, consulta no realizada");
            return false;
        }
        finally{
            try {
                actualizar.close();
                Conexion.SQlite_conexion().close();
            } catch (Exception e) {
                //TODO: handle exception
                System.out.println("Falla al cerrar la base de datos");
            }
        }
        
    }


    //Proceso para obtener los datos de la tabla registro de la DB para guardarlos en un array y poderlos manipular en el codigo.
    public ArrayList<Paciente> getPacientes(){

        PreparedStatement registros=null;

        //Array que almacenara los datos de la DB tabla registros.
        ArrayList<Paciente> pacientes=new ArrayList<Paciente>();

        try {
            registros=Conexion.SQlite_conexion().prepareStatement("SELECT nombre,cedula,genero,muestra1,muestra2,muestra3 FROM registros");

            // Resulset nos permite realizar la consulta y ademas obtener los datos de dicha consulta.
            ResultSet resultado=registros.executeQuery();

            //resultado.next() es verdadero si se encuentra un dato mas adelante. Esto se ejecutara la cantidad de datos obtenidos.
            while(resultado.next()){

                //Estamos obteniendo los datos de la consulta y los estamos guardando en variables para utilizarlos despues.
                String nombre=resultado.getString("nombre");
                int cd=resultado.getInt("cedula");
                String genero=resultado.getString("genero");
                double[] muestras=new double[3];
                muestras[0]=resultado.getDouble("muestra1");
                muestras[1]=resultado.getDouble("muestra2");
                muestras[2]=resultado.getDouble("muestra3");

                // Estamos guardando los datos en el objeto paciente.
                Paciente paciente=new Paciente(nombre, cd, genero, muestras);
                //Agregamos el paciente a la lista de pacientes para despues manipularlos.
                pacientes.add(paciente);

            }
            return pacientes;
            
        } catch (Exception e) {
            e.printStackTrace();
            //TODO: handle exception
            System.out.println("Falla en getPacientes");
            return null;
        }
        finally{
            try {
                registros.close();
                Conexion.SQlite_conexion().close();
            } catch (Exception e) {
                //TODO: handle exception
                System.out.println("Falla al cerrar la base de datos");
            }

        }
    }

    //Proceso para realizar consulta de un registro en la DB tabla registros.
    public Paciente consultarRegistro(int cedula){

        Paciente paciente=null;
        PreparedStatement consulta=null;
        try {
            consulta=Conexion.SQlite_conexion().prepareStatement("SELECT * FROM registros where cedula=?");
            consulta.setInt(1, cedula);

            ResultSet resultado=consulta.executeQuery();

            while(resultado.next()){
                String nombre=resultado.getString("nombre");
                int cd=resultado.getInt("cedula");
                String genero=resultado.getString("genero");
                double[] muestras=new double[3];
                muestras[0]=resultado.getDouble("muestra1");
                muestras[1]=resultado.getDouble("muestra2");
                muestras[2]=resultado.getDouble("muestra3");

                paciente=new Paciente(nombre, cd, genero, muestras);

            }
            return paciente;

        } catch (Exception e) {
            e.printStackTrace();
            //TODO: handle exception
            System.out.println("Falla en la consulta, consulta no realizada");
            return null;
        }
        finally{
            try {
                consulta.close();
                Conexion.SQlite_conexion().close();
            } catch (Exception e) {
                //TODO: handle exception
                System.out.println("Falla al cerrar la base de datos");
            }
        }

    }

}
