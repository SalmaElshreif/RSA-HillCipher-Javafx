package sec;

/**
 *
 * @author SalmaElshreif
 */
import java.io.IOException;
import java.util.ArrayList;
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

public class ThreeController {

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
    private TextField k2;

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
        //int len = key.length();  
        int[][] keyMatrix = new int[3][3];
        int k = 0;
        if (keyChars == 1) {

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
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
                    if (ii % 3 == 0) {
                        jj++;
                        ii = 0;
                    }
                } else {
                    keyMatrix[jj][ii] = keyMatrix[jj][ii] * 10 + Character.getNumericValue(tmp);
                }
            }
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(keyMatrix[i][j] + " ");
            }
            System.out.println("");
        }
        int det = 0;// Calc det  
        for (int i = 0; i < 3; i++) {
            det += (keyMatrix[0][i] * (keyMatrix[1][(i + 1) % 3] * keyMatrix[2][(i + 2) % 3]
                    - keyMatrix[1][(i + 2) % 3] * keyMatrix[2][(i + 1) % 3]));
        }
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

    // 1 2 3 
    @FXML
    private static int[][] reverseMatrix(int[][] keyMatrix) {

        int det = 0;// Calc det  
        for (int i = 0; i < 3; i++) {
            det += (keyMatrix[0][i] * (keyMatrix[1][(i + 1) % 3] * keyMatrix[2][(i + 2) % 3]
                    - keyMatrix[1][(i + 2) % 3] * keyMatrix[2][(i + 1) % 3]));
        }
        det = det % 26;
        if (det < 0) {
            det += 26;
        }

        int mmi;
        for (mmi = 1; mmi < 26; mmi++) {
            if ((det * mmi) % 26 == 1) {
                break;
            }
        }

        int[][] coef = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                coef[i][j] = (keyMatrix[(i + 1) % 3][(j + 1) % 3] * keyMatrix[(i + 2) % 3][(j + 2) % 3]
                        - keyMatrix[(i + 1) % 3][(j + 2) % 3] * keyMatrix[(i + 2) % 3][(j + 1) % 3]);
            }
        }

        int[][] reverseMatrix = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                reverseMatrix[i][j] = coef[j][i] * mmi;
                System.out.print(reverseMatrix[i][j] + " ");
            }
            System.out.println("");
        }
        System.out.println(mmi);

        return reverseMatrix;
    }

    @FXML
    void encrypt(ActionEvent E) {
        // For calclulations depending on the alphabet  
        int[][] keyMatrix = new int[3][3];
        keyMatrix = getKeyMatrix();

        String plainInput = input.getText();
        plainInput = plainInput.replaceAll("[^a-zA-Z]", "").toUpperCase();

        while (plainInput.length() % 3 != 0) {
            plainInput += "Q";

        }

        ArrayList<Integer> plainInputToNum = new ArrayList<>();
        for (int i = 0; i < plainInput.length(); i++) {
            plainInputToNum.add(plainInput.charAt(i) - 65);
        }

        String finnums = "";
        String finchars = "";
        for (int i = 0; i < plainInputToNum.size(); i += 3) {
            long x = (keyMatrix[0][0] * plainInputToNum.get(i)
                    + keyMatrix[0][1] * plainInputToNum.get(i + 1)
                    + keyMatrix[0][2] * plainInputToNum.get(i + 2)) % 26;
            long y = (keyMatrix[1][0] * plainInputToNum.get(i)
                    + keyMatrix[1][1] * plainInputToNum.get(i + 1)
                    + keyMatrix[1][2] * plainInputToNum.get(i + 2)) % 26;
            long z = (keyMatrix[2][0] * plainInputToNum.get(i)
                    + keyMatrix[2][1] * plainInputToNum.get(i + 1)
                    + keyMatrix[2][2] * plainInputToNum.get(i + 2)) % 26;

            if (x < 0) {
                x += 26;
            }
            if (y < 0) {
                y += 26;
            }
            if (z < 0) {
                z += 26;
            }

            finnums += (Long.toString(x)) + " ";
            finnums += (Long.toString(y)) + " ";
            finnums += (Long.toString(z)) + " ";
            finchars += Character.toString((char) (x + 65));
            finchars += Character.toString((char) (y + 65));
            finchars += Character.toString((char) (z + 65));
        }
        output.setText(finnums);
        ouputchars.setText(finchars);

    }

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
        for (i = 0; i < cipherInputToNum.size(); i += 3) {
            int x = ((revKeyMatrix[0][0] * cipherInputToNum.get(i)
                    + revKeyMatrix[0][1] * cipherInputToNum.get(i + 1)
                    + revKeyMatrix[0][2] * cipherInputToNum.get(i + 2)) % 26);
            int y = ((revKeyMatrix[1][0] * cipherInputToNum.get(i)
                    + revKeyMatrix[1][1] * cipherInputToNum.get(i + 1)
                    + revKeyMatrix[1][2] * cipherInputToNum.get(i + 2)) % 26);
            int z = ((revKeyMatrix[2][0] * cipherInputToNum.get(i)
                    + revKeyMatrix[2][1] * cipherInputToNum.get(i + 1)
                    + revKeyMatrix[2][2] * cipherInputToNum.get(i + 2)) % 26);

            if (x < 0) {
                x += 26;
            }
            if (y < 0) {
                y += 26;
            }
            if (z < 0) {
                z += 26;
            }

            finnums += String.valueOf(x) + " ";
            finnums += String.valueOf(y) + " ";
            finnums += String.valueOf(z) + " ";
            finchars += Character.toString((char) (x + 65));
            finchars += Character.toString((char) (y + 65));
            finchars += Character.toString((char) (z + 65));
        }
        output.setText(finnums);
        ouputchars.setText(finchars);
    }

    @FXML
    void back(ActionEvent E) throws IOException {
        System.out.println("back from 3*3");
        Parent root = FXMLLoader.load(getClass().getResource("HILL.fxml"));
        stage = (Stage) ((Node) E.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
