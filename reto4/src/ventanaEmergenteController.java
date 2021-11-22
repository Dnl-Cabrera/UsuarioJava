import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ventanaEmergenteController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label txtMensajeID;

    @FXML
    private Button btnAceptarMID;

    @FXML
    void btn_aceptarM(ActionEvent event) {
        //Esta linea nos permite cerrar la ventana con el boton aceptar.
        ((Stage)this.btnAceptarMID.getScene().getWindow()).close();
    }

    @FXML
    void initialize() {
        assert txtMensajeID != null : "fx:id=\"txtMensajeID\" was not injected: check your FXML file 'ventanaEmergente.fxml'.";
        assert btnAceptarMID != null : "fx:id=\"btnAceptarMID\" was not injected: check your FXML file 'ventanaEmergente.fxml'.";

    }

    void cambiarMensaje(String mensaje){
        this.txtMensajeID.setText(mensaje);
    }
}
