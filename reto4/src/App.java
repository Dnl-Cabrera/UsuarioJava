
import DB.Conexion;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class App extends Application{

    public static void main(String[] args) throws Exception{

        launch(args); // Es para lanzar el programa y se ejecute.
    
    }

    public void start(Stage stage) throws Exception {

        //Cargamos el archivo que se conecta con la configuracion de scene builder
        FXMLLoader loader=new FXMLLoader(App.class.getResource("reto4.fxml"));
        //Creamos un panel para que contenga el panel creado en scene builder
        Pane ventana = (Pane) loader.load();
        //Creamos una escena que se enviara a la ventana ya creada
        Scene scene = new Scene(ventana);
        //agregamos titulo a la ventana
        stage.setTitle("Reto 4");
        //Cargamos la escena
        stage.setScene(scene);
        //mostramos la escena
        Conexion.SQlite_conexion();
        stage.show();
        
    }

}
