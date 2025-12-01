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
    private void onConsultaJornal()  {
        try {
            // 1. Aponta para o FXML da consulta de Jornal
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/inf/grupo/trabalhofinalrev2/view/ConsultaJornalView.fxml"));
            Parent root = loader.load();

            // 2. Cria e exibe o novo Stage
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Pesquisar Jornais no Acervo");
            stage.show();

        } catch (IOException e) {
            System.err.println("Erro ao carregar a tela de Consulta de Jornais.");
            e.printStackTrace();
        }
    }


    @FXML
    private void onSair() {
        Stage stage = (Stage) menuSair.getParentPopup().getOwnerWindow();
        stage.close();
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

    @FXML
    private void onConsultaLivro() {
        try {
            // 1. Carregar o FXML da Consulta de Livro
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/inf/grupo/trabalhofinalrev2/view/ConsultaLivroView.fxml"));
            Parent root = loader.load();

            // 2. Configurar e exibir a nova janela
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Pesquisar Livros no Acervo");
            stage.show();

        } catch (IOException e) {
            System.err.println("Erro ao carregar a tela de Consulta de Livros.");
            e.printStackTrace();
        }
    }

    @FXML
    private  void onRemoverGeral() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/inf/grupo/trabalhofinalrev2/view/RemoverGeralView.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Remover Geral");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private  void onAtualizarGeral() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/inf/grupo/trabalhofinalrev2/view/AtualizacaoGeralView.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Atualizar Geral");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}