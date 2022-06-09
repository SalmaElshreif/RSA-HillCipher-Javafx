package sec;

/**
 *
 * @author SalmaElshreif
 */
import java.util.ArrayList;
import java.io.IOException;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class TwoController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Button back;

    @FXML
    private Button dec;

    @FXML
    private Button enc;

    @FXML
    private TextArea output;

    @FXML
    private TextField input;

    @FXML
    private TextArea ouputchars;

    @FXML
    private TextField k;

    @FXML
    private RadioButton kn;

    @FXML
    private RadioButton kc;

    public long gcd(long a, long b) {
        long GCD = 1;
        for (int i = 1; i <= a; i++) {
            if (a % i == 0 && b % i == 0) {
                GCD = i;
            }
        }
        return GCD;
    }

    int keyChars = 0, keyNums = 0;

    public void select(ActionEvent E) throws IOException {
        if (kn.isSelected()) {
            keyNums = 1;
            keyChars = 0;
        } else if (kc.isSelected()) {
            keyChars = 1;
            keyNums = 0;
        }
    }

    int[][] getKeyMatrix() {
        String key = k.getText();
        int[][] keyMatrix = new int[2][2];
        int k = 0;
        if (keyChars == 1) {

            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 2; j++) {
                    keyMatrix[i][j] = ((int) key.charAt(k)) - 65;
                    k++;
                }
            }

        } else if (keyNums == 1) {
            int ii = 0, jj = 0;
            k = 0;
            for (int i = 0; i < key.length(); i++) {
                char tmp = key.charAt(i);
                if (tmp == ' ') {
                    ii++;
                    if (ii % 2 == 0) {
                        jj++;
                        ii = 0;
                    }
                } else {
                    keyMatrix[jj][ii] = keyMatrix[jj][ii] * 10 + Character.getNumericValue(tmp);
                }
            }
        }
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                System.out.print(keyMatrix[i][j]);
            }
            System.out.println("");
        }
        int det = (keyMatrix[0][0] * keyMatrix[1][1] - keyMatrix[0][1] * keyMatrix[1][0]) % 26; // Calc det  
        det = det % 26;
        if (det < 0) {
            det += 26;
        }
        if (gcd(det, 26) != 1) {
            JOptionPane.showMessageDialog(null, "GCD between your input and z should be = 1");
            System.out.println("Joption");
        }
        return keyMatrix;
    }

    private static int[][] reverseMatrix(int[][] keyMatrix) {
        int det = (keyMatrix[0][0] * keyMatrix[1][1] - keyMatrix[0][1] * keyMatrix[1][0]) % 26; // Calc det  
        det = det % 26;
        if (det < 0) {
            det += 26;
        }

        int mmi;
        int[][] reverseMatrix = new int[2][2];

        for (mmi = 1; mmi < 26; mmi++) {
            if ((det * mmi) % 26 == 1) {
                break;
            }
        }
        reverseMatrix[0][0] = keyMatrix[1][1] * mmi % 26;
        reverseMatrix[0][1] = (26 - keyMatrix[0][1]) * mmi % 26;
        reverseMatrix[1][0] = (26 - keyMatrix[1][0]) * mmi % 26;
        reverseMatrix[1][1] = keyMatrix[0][0] * mmi % 26;

        return reverseMatrix;
    }

    @FXML
    void encrypt(ActionEvent E) {
        // For calclulations depending on the alphabet  
        int[][] keyMatrix = new int[2][2];
        keyMatrix = getKeyMatrix();

        String plainInput = input.getText();
        plainInput = plainInput.replaceAll("[^a-zA-Z]", "").toUpperCase();

        if (plainInput.length() % 2 == 1) {
            plainInput += "Q";
        }

        ArrayList<Integer> plainInputToNum = new ArrayList<>();
        for (int i = 0; i < plainInput.length(); i++) {
            plainInputToNum.add(plainInput.charAt(i) - 65);
        }

        String finnums = "";
        String finchars = "";
        for (int i = 0; i < plainInputToNum.size(); i += 2) {
            long x = (keyMatrix[0][0] * plainInputToNum.get(i) + keyMatrix[0][1] * plainInputToNum.get(i + 1)) % 26;
            long y = (keyMatrix[1][0] * plainInputToNum.get(i) + keyMatrix[1][1] * plainInputToNum.get(i + 1)) % 26;
            if (x < 0) {
                x += 26;
            }
            if (y < 0) {
                y += 26;
            }
            finnums += (Long.toString(x)) + " ";
            finnums += (Long.toString(y)) + " ";
            finchars += Character.toString((char) (x + 65));
            finchars += Character.toString((char) (y + 65));
        }
        output.setText(finnums);
        ouputchars.setText(finchars);
    }

    // 1 2 3  7
    // 4 5 6  6
    // 7 8 9  9
    @FXML
    void decrypt(ActionEvent E) {
        int i;
        int[][] keyMatrix, revKeyMatrix;
        String cipherInput = input.getText();
        cipherInput = cipherInput.replaceAll("[^a-zA-Z]", "").toUpperCase();

        keyMatrix = getKeyMatrix();

        ArrayList<Integer> cipherInputToNum = new ArrayList<>();
        for (i = 0; i < cipherInput.length(); i++) {
            cipherInputToNum.add(cipherInput.charAt(i) - 65);
        }
        revKeyMatrix = reverseMatrix(keyMatrix);

        String finnums = "";
        String finchars = "";
        for (i = 0; i < cipherInputToNum.size(); i += 2) {
            int x = ((revKeyMatrix[0][0] * cipherInputToNum.get(i) + revKeyMatrix[0][1] * cipherInputToNum.get(i + 1)) % 26);
            int y = ((revKeyMatrix[1][0] * cipherInputToNum.get(i) + revKeyMatrix[1][1] * cipherInputToNum.get(i + 1)) % 26);
            if (x < 0) {
                x += 26;
            }
            if (y < 0) {
                y += 26;
            }
            finnums += String.valueOf(x) + " ";
            finnums += String.valueOf(y) + " ";
            finchars += Character.toString((char) (x + 65));
            finchars += Character.toString((char) (y + 65));
        }
        output.setText(finnums);
        ouputchars.setText(finchars);
    }

    @FXML
    void back(ActionEvent E) throws IOException {
        System.out.println("back from 2*2");
        Parent root = FXMLLoader.load(getClass().getResource("HILL.fxml"));
        stage = (Stage) ((Node) E.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
