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

public class HillController {

    @FXML
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void two(ActionEvent E) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("TWO.fxml"));
        stage = (Stage) ((Node) E.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void three(ActionEvent E) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("THREE.fxml"));
        stage = (Stage) ((Node) E.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void back(ActionEvent E) throws IOException {
        System.out.println("back from hill");
        Parent root = FXMLLoader.load(getClass().getResource("FIRST.fxml"));
        stage = (Stage) ((Node) E.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
