package sec;

/**
 *
 * @author SalmaElshreif
 */
import java.math.BigInteger;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.lang.Math;
import static java.lang.Math.pow;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javax.swing.JOptionPane;

public class RsaController {

    @FXML
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private TextField qq;
    @FXML
    private TextField pp;
    @FXML
    private TextField ee;
    @FXML
    private TextField plchars;
    @FXML
    private TextArea ci;
    @FXML
    private TextArea pln;
    @FXML
    private RadioButton D1;
    @FXML
    private RadioButton E1;

    public long gcd(long a, long b) {
        long GCD = 1;
        for (int i = 1; i <= a; i++) {
            if (a % i == 0 && b % i == 0) {
                GCD = i;
            }
        }
        return GCD;
    }

    public int mmi(long a, long m) {
        for (int x = 1; x < m; x++) {
            if (((a % m) * (x % m)) % m == 1) {
                return x;
            }
        }
        return 0;
    }

    int dflag = 0, eflag = 0, b1 = 0, b2 = 0;

    public void select(ActionEvent E) throws IOException {
        if (D1.isSelected()) {
            dflag = 1;
            eflag = 0;
        } else if (E1.isSelected()) {
            eflag = 1;
            dflag = 0;
        }
    }

    public void encrypt(ActionEvent E) throws IOException {
        String p1 = pp.getText();
        String q1 = qq.getText();
        String innum1 = ee.getText();
        String plainT = plchars.getText();
        long p = Long.parseLong(p1);
        long q = Long.parseLong(q1);
        long innum = Long.parseLong(innum1);
        long n = p * q;//calculate n
        long z = (p - 1) * (q - 1);//calculate z
        System.out.println("Z = " + z);
        if (gcd(innum, z) != 1) {
            JOptionPane.showMessageDialog(null, "GCD between your input and z should be = 1");
            System.out.println("GCD between your input and z should be = 1");
        }
        int outnum = mmi(innum, z);

        String input = "", output = "";

        long aa = Character.getNumericValue('a');
        for (int i = 0; i < plainT.length(); i++) {
            char tmp = plainT.charAt(i);
            long plain = Character.getNumericValue(tmp);
            plain = plain - aa;
            input += (Long.toString(plain));
            input += " , ";
            long c = 0;
            if (eflag == 1) {
                c = (long) pow(plain, innum);
                c = c % n;
            } else if (dflag == 1) {
                c = (long) pow(plain, outnum);
                c = c % n;

            }
            output += (Long.toString(c));
            output += " , ";
        }
        pln.setText(input);
        ci.setText(output);
        System.out.println(outnum);
    }
    
    

    public void back(ActionEvent E) throws IOException {
        System.out.println("Back from RSA");
        Parent root = FXMLLoader.load(getClass().getResource("FIRST.fxml"));
        stage = (Stage) ((Node) E.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
