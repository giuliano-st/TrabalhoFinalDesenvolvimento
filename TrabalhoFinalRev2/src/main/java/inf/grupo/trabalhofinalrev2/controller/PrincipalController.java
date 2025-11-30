package inf.grupo.trabalhofinalrev2.controller;

import inf.grupo.trabalhofinalrev2.db.Conexao;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class PrincipalController implements Initializable {

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

    public void initialize(URL location, ResourceBundle resources) {
        //Teste de Conexão com o Banco de Dados
        System.out.println("Testando a conexão com o banco de dados...");
        Connection conn = null;
        try {
            conn = Conexao.getConnection();
            // Se chegou aqui, a conexão foi bem-sucedida.
            System.out.println("✅ Status: Conexão bem-sucedida! Banco de dados pronto.");
        } catch (SQLException e) {
            // Se cair no catch, a conexão falhou.
            System.err.println("❌ Status: Falha na conexão com o banco de dados!");
            e.printStackTrace();
        }
    }

    @FXML
    private void onCadastroLivro() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/inf/grupo/trabalhofinalrev2/view/CadastroView.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Cadastro - Livro");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
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
    private void onConsultaJornal()  {
        System.out.println("Abrir tela de consulta de Jornal");
    }


    @FXML
    private void onSair() {
        Stage stage = (Stage) menuSair.getParentPopup().getOwnerWindow();
        stage.close();
    }

    @FXML
    private void onPesquisaGeral() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/inf/grupo/trabalhofinalrev2/view/PesquisaGeral.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Pesquisa Geral de Obras");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onConsultaRevista() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/inf/grupo/trabalhofinalrev2/view/ConsultaRevistaView.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Pesquisar Revistas no Acervo");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private  void onCadastroGeral() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/inf/grupo/trabalhofinalrev2/view/CadastroGeralView.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Cadastro Geral de Obras");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
