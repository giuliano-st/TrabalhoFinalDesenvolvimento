package inf.grupo.trabalhofinalrev2.controller;

import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public class PrincipalController {

    // Se quiser manipular os itens diretamente, pode usar @FXML
    @FXML
    private MenuItem menuCadastroLivro;

    @FXML
    private MenuItem menuCadastroJornal;

    @FXML
    private MenuItem menuCadastroRevista;

    @FXML
    private MenuItem menuConsultaLivro;

    @FXML
    private MenuItem menuConsultaJornal;

    @FXML
    private MenuItem menuConsultaRevista;

    @FXML
    private MenuItem menuSair;

    // Métodos de ação
    @FXML
    private void onCadastroLivro() {
        System.out.println("Abrir tela de cadastro de Livro");
        // Aqui você pode carregar outro FXML com FXMLLoader
    }

    @FXML
    private void onCadastroJornal() {
        System.out.println("Abrir tela de cadastro de Jornal");
    }

    @FXML
    private void onCadastroRevista() {
        System.out.println("Abrir tela de cadastro de Revista");
    }

    @FXML
    private void onConsultaLivro() {
        System.out.println("Abrir tela de consulta de Livro");
    }

    @FXML
    private void onConsultaJornal() {
        System.out.println("Abrir tela de consulta de Jornal");
    }

    @FXML
    private void onConsultaRevista() {
        System.out.println("Abrir tela de consulta de Revista");
    }

    @FXML
    private void onSair() {
        Stage stage = (Stage) menuSair.getParentPopup().getOwnerWindow();
        stage.close(); // Fecha a aplicação
    }
}
