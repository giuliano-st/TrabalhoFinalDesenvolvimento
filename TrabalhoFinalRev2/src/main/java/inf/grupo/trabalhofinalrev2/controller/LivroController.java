package inf.grupo.trabalhofinalrev2.controller;

import inf.grupo.trabalhofinalrev2.dao.LivroDAO;
import inf.grupo.trabalhofinalrev2.db.Conexao;
import inf.grupo.trabalhofinalrev2.model.Livro;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LivroController {

    @FXML private TextField txtTitulo;
    @FXML private TextField txtAutor;
    @FXML private TextField txtIsbn;

    private LivroDAO livroDAO;

    public LivroController() {
        Connection conn = null;
        try {
            conn = Conexao.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        livroDAO = new LivroDAO(conn);
    }

    @FXML
    public void onNovo() {
        txtTitulo.clear();
        txtAutor.clear();
        txtIsbn.clear();
    }

    @FXML
    public void onSalvar() {
        /*Livro livro = new Livro();
        livro.setTitulo(txtTitulo.getText());
        livro.setAutor(txtAutor.getText());
        livro.setIsbn(txtIsbn.getText());
        String nome = "Teste da Silva";
        String nac = "BRA";

        try {
            //livroDAO.salvar(livro);
            //id, nome, nacionalidade(3)

            mostrarMensagem("Livro cadastrado com sucesso!");
            onNovo();
        } catch (SQLException e) {
            mostrarMensagem("Erro ao salvar livro: " + e.getMessage());
        }*/
        try {
            Connection conn = Conexao.getConnection();
            // INSERT INTO obras (x, y ,z, TIPO) -> comboboxTipodeTroço(Livro)
            //SELECT * FROM obras WHERE tipo = Livro;
            //SELECT * FROM obras WHERE tipo = Livro AND autor = ?;
            // (Livro, Pescaria com Seu zé, Seu Zé)
            // SQL com parâmetros (placeholders ?)
            String sql = "INSERT INTO autores (nome, nacionalidade) VALUES (?, ?)";

            PreparedStatement stmt = conn.prepareStatement(sql);

            // Definindo os valores
            stmt.setString(1, "Sir Arthur Conan Doyle");
            stmt.setString(2, "GBR");

            // Executando a inserção
            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Autor inserido com sucesso!");
            }

            // Fechar recursos
            stmt.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao inserir autor", e);
        }
    }

    private void mostrarMensagem(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, msg);
        alert.showAndWait();
    }
}
