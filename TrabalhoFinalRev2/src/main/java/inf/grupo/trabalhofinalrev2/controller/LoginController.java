package inf.grupo.trabalhofinalrev2.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label messageLabel;

    @FXML
    protected void onLoginClick() {
        String user = usernameField.getText().trim();
        String pass = passwordField.getText().trim();

        if (user.isEmpty() || pass.isEmpty()) {
            messageLabel.setText("Por favor, preencha todos os campos.");
            messageLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        if (("admin".equals(user) && "123".equals(pass)) ||
                ("usuario".equals(user) && "456".equals(pass))) {
            try {
                // Carrega a tela Principal
                Stage stage = (Stage) messageLabel.getScene().getWindow();
                Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/inf/grupo/trabalhofinalrev2/view/PrincipalView.fxml")));
                stage.setScene(scene);
                stage.setTitle("Sistema - Tela Principal");
            } catch (IOException e) {
                e.printStackTrace();
                messageLabel.setText("Erro ao carregar a tela principal.");
            }
        } else {
            messageLabel.setText("Usuário ou senha inválidos.");
            messageLabel.setStyle("-fx-text-fill: red;");
        }
    }

    @FXML
    protected void onCancelClick() {
        usernameField.clear();
        passwordField.clear();
        messageLabel.setText("");
    }
}
