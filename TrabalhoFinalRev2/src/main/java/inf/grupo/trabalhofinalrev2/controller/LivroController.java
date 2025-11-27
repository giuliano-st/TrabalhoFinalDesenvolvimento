package inf.grupo.trabalhofinalrev2.controller;

import inf.grupo.trabalhofinalrev2.dao.LivroDAO;
import inf.grupo.trabalhofinalrev2.model.Livro;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.SQLException;

public class LivroController {

    @FXML private TextField txtTitulo;
    @FXML private TextField txtAutor;
    @FXML private TextField txtIsbn;

    private LivroDAO livroDAO;

    public LivroController() {
        // Aqui você pode inicializar a conexão
        //Connection conn = ConnectionFactory.getConnection();
        //livroDAO = new LivroDAO(conn);
    }

    @FXML
    public void onNovo() {
        txtTitulo.clear();
        txtAutor.clear();
        txtIsbn.clear();
    }

    @FXML
    public void onSalvar() {
        Livro livro = new Livro();
        livro.setTitulo(txtTitulo.getText());
        livro.setAutor(txtAutor.getText());
        livro.setIsbn(txtIsbn.getText());

        try {
            livroDAO.salvar(livro);
            mostrarMensagem("Livro cadastrado com sucesso!");
            onNovo();
        } catch (SQLException e) {
            mostrarMensagem("Erro ao salvar livro: " + e.getMessage());
        }
    }

    private void mostrarMensagem(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, msg);
        alert.showAndWait();
    }
}
