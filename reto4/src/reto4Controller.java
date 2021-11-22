import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import manejoDB.operacionesDB;
import modelo.Paciente;

public class reto4Controller {

    double autoAjusteListIn; //Para ajustar las dimensiones del listview
    double autoAjusteListOut; 
    ObservableList<String> observableList; // Es una especie de array que almacena datos tipo String.
    
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField nombreIngresarID;

    @FXML
    private TextField cedulaIngresarID;

    @FXML
    private TextField generoIngresarID;

    @FXML
    private TextField muestra1IngresarID;

    @FXML
    private TextField muestra2IngresarID;

    @FXML
    private TextField muestra3IngresarID;

    @FXML
    private Button btnIngresarID;

    @FXML
    private ListView<String> txtDatosDB;

    @FXML
    private ListView<String> txtDatosProcesados;

    @FXML
    private Button btnProcesarDatosID;

    @FXML
    private Button btnObtenerDatosID;

    @FXML
    private TextField consultarID;

    @FXML
    private Button btnConsultarID;

    @FXML
    private TextField nombreRegistroID;

    @FXML
    private TextField generoRegistroID;

    @FXML
    private TextField muestra1RegistroID;

    @FXML
    private TextField muestra2RegistroID;

    @FXML
    private TextField muestra3RegistroID;

    @FXML
    private Button btnEliminarID;

    @FXML
    private Button btnEditarID;

    @FXML
    void btn_consultar(ActionEvent event) {

        //Este objeto contiene todas las operaciones que podemos realizar en la DB, que especificamos. Este metodo lo creamos.
        operacionesDB consultaPaciente=new operacionesDB();
        
        try {
            //Estamos consultando el registro con la cedula, si arroja error, se remitira al catch.
            Paciente consulta=consultaPaciente.consultarRegistro(Integer.parseInt(this.consultarID.getText()));

            //Estamos colocando los datos de la consulta en los campos correspondientes en la GUI.
            if(consulta!=null){
                //System.out.println("Consulta exitosa");
                //Metodo para mostrar una ventana emergente si el dato se consulto correctamente en la DB.
                inicializarVentanaEmergente("Dato consultado");

                //Se estan manipulando las variables de la interfaz grafica para enviarle los datos de la consulta.
                nombreRegistroID.setText(consulta.getNombre());
                generoRegistroID.setText(consulta.getGenero());
                muestra1RegistroID.setText(String.valueOf(consulta.getDatosParcial(0)));
                muestra2RegistroID.setText(String.valueOf(consulta.getDatosParcial(1)));
                muestra3RegistroID.setText(String.valueOf(consulta.getDatosParcial(2)));
            }
            else{
                inicializarVentanaEmergente("Dato no consultado");
                //System.out.println("Consulta fallida");
            }
        } catch (Exception e) {
            //TODO: handle exception
            inicializarVentanaEmergente("Dato no consultado");
        }
        

    }

    //Metodo para editar un dato en la DB tabla registros.
    @FXML
    void btn_editar(ActionEvent event) {

        try {
            double[] muestras=new double[3];
            muestras[0]=Double.parseDouble(muestra1RegistroID.getText());
            muestras[1]=Double.parseDouble(muestra2RegistroID.getText());
            muestras[2]=Double.parseDouble(muestra3RegistroID.getText());
        
            Paciente pacienteActualizado=new Paciente(nombreRegistroID.getText(),Integer.parseInt(consultarID.getText()),generoRegistroID.getText()
            , muestras);

            //Objeto para iniciar o conectar la DB para realizar la actualización del registro.
            operacionesDB actualizacion=new operacionesDB();

            boolean operacion=actualizacion.actualizarRegistro(pacienteActualizado);

            if(operacion){
                inicializarVentanaEmergente("Dato editado");
                //Metodo para elminar los datos de los campos del fieldtext del GUI.
                eliminarDatosLayout();
            }
            else{
                inicializarVentanaEmergente("Dato no editado");
            }
            
        } catch (Exception e) {
            //TODO: handle exception
            inicializarVentanaEmergente("Dato no editado");
        }
        
        
        
    }

    //Metodo para eliminar un dato en la DB.
    @FXML
    void btn_eliminar(ActionEvent event) {

        try {//Utilizamos el Try por si arroja un error en la consulta.
            
            //Estamos iniciando una variable para conectarnos y operar con la DB.
            operacionesDB eliminar=new operacionesDB();

            boolean operacion=eliminar.eliminarRegistro(Integer.parseInt(consultarID.getText()));

            if(operacion){
                inicializarVentanaEmergente("Dato eliminado");
                eliminarDatosLayout(); //Metodo para eliminar los datos del los campos de texto en la GUI.
            }
            else{
                inicializarVentanaEmergente("Dato no eliminado");
            }

        } catch (Exception e) {
            //TODO: handle exception
            inicializarVentanaEmergente("Dato no eliminado");
        }
        
        

    }


    //Metodo para ingresar datos nuevos en la DB tabla registros.
    @FXML
    void btn_ingresar(ActionEvent event) {

        //EStamos verificando si la totalidad de los campos estan digitados para realizar el registro en la DB.
        if(nombreIngresarID.getText().equals("") || cedulaIngresarID.getText().equals("") || generoIngresarID.getText().equals("") || muestra1IngresarID.getText().equals("") || muestra2IngresarID.getText().equals("") || muestra3IngresarID.getText().equals("")){
            
            inicializarVentanaEmergente("Complete los datos");
        }
        else{
            //EStamos agregando los datos de los campos de texto de la GUI en la DB tabla registros.
            double[] muestras=new double[3];
            muestras[0]=Double.parseDouble(muestra1IngresarID.getText());
            muestras[1]=Double.parseDouble(muestra2IngresarID.getText());
            muestras[2]=Double.parseDouble(muestra3IngresarID.getText());
        
            Paciente pacienteRegistro=new Paciente(nombreIngresarID.getText(),Integer.parseInt(cedulaIngresarID.getText()),generoIngresarID.getText()
            , muestras);

            operacionesDB registro=new operacionesDB();
            boolean operacion=registro.registrarPaciente(pacienteRegistro);

            if(operacion){
                inicializarVentanaEmergente("Registro exitoso");
                //System.out.println("Registro exitoso");
                eliminarDatosLayout();
                
            }
            else{
                inicializarVentanaEmergente("Registro no exitoso");
                //System.out.println("Registro no exitoso");
            }

        }

        
    }

    //Metodo para obtener todos los datos de la tabla registros de la DB.
    @FXML
    void btn_obtenerDatos(ActionEvent event) {

        operacionesDB pacientesRegistros=new operacionesDB();
        ArrayList<Paciente> pacientes=pacientesRegistros.getPacientes();

        if(pacientes.size()>0){
            setDatos(pacientes);
        }
        else{
            inicializarVentanaEmergente("No hay datos");
        }

    }

    // Boton para procesar los datos segun las condiciones del reto 4.
    @FXML
    void btn_procesarDatos(ActionEvent event) {
        try {
            getEvaluacionDatos(txtDatosDB.getItems());
            inicializarVentanaEmergente("Datos procesados");

        } catch (Exception e) {
            //TODO: handle exception
            inicializarVentanaEmergente("Datos no procesados");
        }
    }

    //Este metodo se crea con ayuda del programa scenebuilder para inicializar los ID y fuction o botones que se plantean en la GUI.
    @FXML
    void initialize() {
        assert nombreIngresarID != null : "fx:id=\"nombreIngresarID\" was not injected: check your FXML file 'reto4.fxml'.";
        assert cedulaIngresarID != null : "fx:id=\"cedulaIngresarID\" was not injected: check your FXML file 'reto4.fxml'.";
        assert generoIngresarID != null : "fx:id=\"generoIngresarID\" was not injected: check your FXML file 'reto4.fxml'.";
        assert muestra1IngresarID != null : "fx:id=\"muestra1IngresarID\" was not injected: check your FXML file 'reto4.fxml'.";
        assert muestra2IngresarID != null : "fx:id=\"muestra2IngresarID\" was not injected: check your FXML file 'reto4.fxml'.";
        assert muestra3IngresarID != null : "fx:id=\"muestra3IngresarID\" was not injected: check your FXML file 'reto4.fxml'.";
        assert btnIngresarID != null : "fx:id=\"btnIngresarID\" was not injected: check your FXML file 'reto4.fxml'.";
        assert txtDatosDB != null : "fx:id=\"txtDatosDB\" was not injected: check your FXML file 'reto4.fxml'.";
        assert txtDatosProcesados != null : "fx:id=\"txtDatosProcesados\" was not injected: check your FXML file 'reto4.fxml'.";
        assert btnProcesarDatosID != null : "fx:id=\"btnProcesarDatosID\" was not injected: check your FXML file 'reto4.fxml'.";
        assert btnObtenerDatosID != null : "fx:id=\"btnObtenerDatosID\" was not injected: check your FXML file 'reto4.fxml'.";
        assert consultarID != null : "fx:id=\"consultarID\" was not injected: check your FXML file 'reto4.fxml'.";
        assert btnConsultarID != null : "fx:id=\"btnConsultarID\" was not injected: check your FXML file 'reto4.fxml'.";
        assert nombreRegistroID != null : "fx:id=\"nombreRegistroID\" was not injected: check your FXML file 'reto4.fxml'.";
        assert generoRegistroID != null : "fx:id=\"generoRegistroID\" was not injected: check your FXML file 'reto4.fxml'.";
        assert muestra1RegistroID != null : "fx:id=\"muestra1RegistroID\" was not injected: check your FXML file 'reto4.fxml'.";
        assert muestra2RegistroID != null : "fx:id=\"muestra2RegistroID\" was not injected: check your FXML file 'reto4.fxml'.";
        assert muestra3RegistroID != null : "fx:id=\"muestra3RegistroID\" was not injected: check your FXML file 'reto4.fxml'.";
        assert btnEliminarID != null : "fx:id=\"btnEliminarID\" was not injected: check your FXML file 'reto4.fxml'.";
        assert btnEditarID != null : "fx:id=\"btnEditarID\" was not injected: check your FXML file 'reto4.fxml'.";

        //Estamos obteniendo las dimensiones del listview, para despues manipular sus dimensiones al momento de agregar datos.
        autoAjusteListIn=txtDatosDB.getHeight();
        autoAjusteListOut=txtDatosDB.getHeight();

    }

    //Metodo para eliminar los datos de los campos del GUI, como un reset.
    void eliminarDatosLayout(){
        txtDatosDB.getItems().clear();
        txtDatosProcesados.getItems().clear();
        consultarID.setText("");
        nombreRegistroID.setText("");
        generoRegistroID.setText("");
        muestra1RegistroID.setText("");
        muestra2RegistroID.setText("");
        muestra3RegistroID.setText("");
        nombreIngresarID.setText("");
        cedulaIngresarID.setText("");
        generoIngresarID.setText("");
        muestra1IngresarID.setText("");
        muestra2IngresarID.setText("");
        muestra3IngresarID.setText("");
    }


    //Metodo para colocar los datos obtenidos de la db en el ListText de la GUI.
    void setDatos(ArrayList<Paciente> datos){

        //Estamos eliminando los datos que quedaron en los campos del listview en la GUI.
        txtDatosDB.getItems().clear();
        txtDatosProcesados.getItems().clear();

        for(int i=0;i<datos.size();i++){
            String dato;
            dato=datos.get(i).getNombre()+"-"+datos.get(i).getCedula()+"-"+datos.get(i).getGenero()+"-"+datos.get(i).getDatosParcial(0)+"-"+datos.get(i).getDatosParcial(1)+"-"+datos.get(i).getDatosParcial(2);
            //Estamos agregando los datos en cada linea del textView
            txtDatosDB.getItems().add(dato);
            //Ajustamos la ventana del listview segun la cantidad de datos que se encuentren en el arrayList.
            autoAjusteListIn=autoAjusteListIn+30;
            txtDatosDB.setPrefHeight(autoAjusteListIn);
        }
    }
    
    //Metodo para inicializar la ventana emergente.
    void inicializarVentanaEmergente(String mensaje){

        //Cargamos el archivo que se conecta con la configuracion de scene builder y le indicamos el fxml que deseamos trabajar.
        FXMLLoader loader=new FXMLLoader(getClass().getResource("ventanaEmergente.fxml"));
        

        try {
            //Creamos un panel para que contenga el panel creado en scene builder
            Parent ventana = loader.load();
            //Este es el controlador de la ventana emergente que creamos. Estamos obteniendo su configuración.
            ventanaEmergenteController controlador=loader.getController();
            //Estamos cambiando el mensaje que arroja la ventana emergente.
            controlador.cambiarMensaje(mensaje);
            //Creamos una escena que se enviara a la ventana ya creada
            Scene scene = new Scene(ventana);

            //Stage es un metodo que nos permite manipular las caracteristicas de la Pagina. METODO IMPORTANTE PARA PROFUNDIZAR.
            Stage stage=new Stage();
            //Esto es como para abrir la ventana, no me dejara ir a la ventana anterior.
            stage.initModality(Modality.APPLICATION_MODAL);
            //agregamos titulo a la ventana
            stage.setTitle("Alerta");
            //Cargamos la escena
            stage.setScene(scene);
            //mostramos la escena y esperamos
            stage.showAndWait();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        
    }


    //Este metodo es para obtener los datos del listView, estos datos se obtienen con un array (ObservableList que es de JavaFX es como un ArrayList)
    void getEvaluacionDatos(ObservableList<String> observableList){

        int cantidad=observableList.size();//Tamaño de la cantidad de datos.

        Paciente[] pacientes=new Paciente[cantidad]; //Vector que contiene la cantidad de pacientess obtenidos en el listView.

        for(int i=0;i<cantidad;i++){

            String[] datos = observableList.get(i).split("-"); //Estamos separando los datos con el indicador "-", el cual se encuentra en cada item del listView. Obteniendo los datos separados.

            //Agregamos los datos de las muestras(datos de un registro) en un vector, debido a que asi se realizo el metodo.
            double[] puntaje=new double[3];
            for(int j=3;j<6;j++){
                puntaje[j-3]=Double.parseDouble(datos[j]);
            }
            //Estamos pasando los datos a un objeto pacientes.
            pacientes[i]=new Paciente(datos[0],Integer.parseInt(datos[1]),datos[2],puntaje);
            

        }

        //**********Evaluamos los datos de las muestras de cada paciente */

        int[] puntajes=new int[cantidad];

        for(int i=0;i<cantidad;i++){

            int puntaje=0;

            Paciente p=pacientes[i];

            if(p.getGenero().equals("M")){

                for(int j=0;j<3;j++){

                    if((p.getDatosParcial(j)>=0.74 && p.getDatosParcial(j)<=1.35) && j<2){
                        puntaje=puntaje+0;
                        p.puntajeMuestras[j]=0;
                    }
                    else{
                        if((p.getDatosParcial(j)>=14 && p.getDatosParcial(j)<=26) && j==2){
                            puntaje=puntaje+0;
                            p.puntajeMuestras[j]=0;
                        }
                        else{
                            puntaje=puntaje+10;
                            p.puntajeMuestras[j]=10;
                        }
                    }

                } 

                int x=0;
                x=p.getPuntajeParcial(0);
                x=x+p.getPuntajeParcial(1);
                x=x+p.getPuntajeParcial(2);
                puntajes[i] = x;

            }
            else if(p.getGenero().equals("F")){

                for(int j=0;j<3;j++){

                    if((p.getDatosParcial(j)>=0.59 && p.getDatosParcial(j)<=1.04) && j<2){
                        puntaje=puntaje+0;
                        p.puntajeMuestras[j]=0;
                    }
                    else{
                        if((p.getDatosParcial(j)>=11 && p.getDatosParcial(j)<=20) && j==2){
                            puntaje=puntaje+0;
                            p.puntajeMuestras[j]=0;
                        }
                        else{
                            puntaje=puntaje+10;
                            p.puntajeMuestras[j]=10;
                        }
                    }
                }

                int x=0;
                x=p.getPuntajeParcial(0);
                x=x+p.getPuntajeParcial(1);
                x=x+p.getPuntajeParcial(2);
                puntajes[i] = x;
                //puntajes[i] = puntaje; 

            }
        }

        String[] categoria=categoria(puntajes);

        int[] categoriaMasAlta=categoriaMasAlta(pacientes);

        int[] muestraMasAltaPorMuestra=muestraMasAltaPorMuestra(pacientes);

        imprimir(pacientes, puntajes, categoria, categoriaMasAlta, muestraMasAltaPorMuestra);


    }

    public static String[] categoria(int puntajes[]){

        String[] categoria=new String[puntajes.length];

        for(int i=0;i<puntajes.length;i++){

            if(puntajes[i]==0){

                categoria[i]="Sin riesgo";

            }
            else if(puntajes[i]==10){

                categoria[i]="Bajo";

            }
            else if(puntajes[i]==20){

                categoria[i]="Medio";

            }
            else if(puntajes[i]==30){

                categoria[i]="Alto";

            }

        }

        return categoria;

    }

    public static int[] categoriaMasAlta(Paciente vector[]){

        int cantidad=vector.length;

        int[] muestraAlta=new int[vector.length];

        for(int i=0;i<cantidad;i++){
            
            if((vector[i].getDatosParcial(0)>vector[i].getDatosParcial(1)) && vector[i].getDatosParcial(0)>vector[i].getDatosParcial(2)){

                muestraAlta[i]=1;
            }
            if((vector[i].getDatosParcial(1)>vector[i].getDatosParcial(0)) && vector[i].getDatosParcial(1)>vector[i].getDatosParcial(2)){

                muestraAlta[i]=2;
            }
            if(vector[i].getDatosParcial(2)>vector[i].getDatosParcial(1) && vector[i].getDatosParcial(2)>vector[i].getDatosParcial(0)){

                muestraAlta[i]=3;
            }

        }

        return muestraAlta;

    }

    public static int[] muestraMasAltaPorMuestra(Paciente vector[]){

        int cantidad=vector.length;

        int[] vectorMuestraAltaPorCategoria=new int[3];

        double ctg1=-9999;
        double ctg2=-9999;
        double ctg3=-9999;

        for(int i=0;i<cantidad;i++){

            if(vector[i].getDatosParcial(0)>ctg1){
                vectorMuestraAltaPorCategoria[0]=i;
                ctg1=vector[i].getDatosParcial(0);
            }
            if(vector[i].getDatosParcial(1)>ctg2){
                vectorMuestraAltaPorCategoria[1]=i;
                ctg2=vector[i].getDatosParcial(1);
            }
            if(vector[i].getDatosParcial(2)>ctg3){
                vectorMuestraAltaPorCategoria[2]=i;
                ctg3=vector[i].getDatosParcial(2);
            }

        }

        return vectorMuestraAltaPorCategoria;

    }

    void imprimir(Paciente vector[], int[] puntajeMuestra, String[] categoria, int[] categoriaMasAlta, int[] muestraMasAltaPorMuestra){

        for(int i=0;i<vector.length;i++){

            txtDatosProcesados.getItems().add("PACIENTE "+String.valueOf(i+1));
            txtDatosProcesados.getItems().add("Puntaje obtenido: "+String.valueOf(puntajeMuestra[i]));
            txtDatosProcesados.getItems().add("Categorizacion riesgo: "+String.valueOf(categoria[i]));
            txtDatosProcesados.getItems().add("El numero de la muestra mas alta es: "+String.valueOf(categoriaMasAlta[i]));
            //txtDatosProcesados.getItems().add();
            
            //Ajustando ventana de salida
            autoAjusteListOut=autoAjusteListOut+30*5;
            txtDatosProcesados.setPrefHeight(autoAjusteListOut);

        }

        txtDatosProcesados.getItems().add("El paciente que tiene la muestra 1 mas alta es: "+vector[muestraMasAltaPorMuestra[0]].getNombre());
        txtDatosProcesados.getItems().add("El paciente que tiene la muestra 2 mas alta es: "+vector[muestraMasAltaPorMuestra[1]].getNombre());
        txtDatosProcesados.getItems().add("El paciente que tiene la muestra 3 mas alta es: "+vector[muestraMasAltaPorMuestra[2]].getNombre());

        autoAjusteListOut=autoAjusteListOut+30*3;
        txtDatosProcesados.setPrefHeight(autoAjusteListOut);
    }
}
