package sec;

/**
 *
 * @author SalmaElshreif
 */
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FirstController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    public void playfair(ActionEvent E) throws IOException {
        System.out.println("HillCipher");
        Parent root = FXMLLoader.load(getClass().getResource("HILL.fxml"));
        stage = (Stage) ((Node) E.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void rsa(ActionEvent E) throws IOException {
        System.out.println("RSA");
        Parent root = FXMLLoader.load(getClass().getResource("RSA.fxml"));
        stage = (Stage) ((Node) E.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
