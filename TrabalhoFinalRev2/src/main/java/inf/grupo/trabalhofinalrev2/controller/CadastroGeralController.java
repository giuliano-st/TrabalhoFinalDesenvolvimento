package inf.grupo.trabalhofinalrev2.controller;

import inf.grupo.trabalhofinalrev2.dao.ObraDAO;
import inf.grupo.trabalhofinalrev2.db.Conexao;
import inf.grupo.trabalhofinalrev2.model.Assunto;
import inf.grupo.trabalhofinalrev2.model.Autor;
import inf.grupo.trabalhofinalrev2.model.Editora;
import inf.grupo.trabalhofinalrev2.model.Obra;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CadastroGeralController {

    @FXML private ComboBox<String> comboTipo;
    @FXML private VBox paneLivro;
    @FXML private VBox paneRevista;
    @FXML private VBox paneJornal;

    // Campos comuns
    @FXML private TextField txtTituloPrincipal;
    @FXML private TextField txtCapa;
    @FXML private TextField txtLocal;
    @FXML private TextField txtData;
    @FXML private TextField txtDescFisica;

    // Combos de FKs (com objetos)
    @FXML private ComboBox<Assunto> comboAssunto;
    @FXML private ComboBox<Editora> comboEditora;
    @FXML private ComboBox<Autor> comboAutor;

    // Campos Livro
    @FXML private TextField txtNumeroChamada;
    @FXML private TextField txtChamadaLocal;
    @FXML private TextField txtTituloUniforme;
    @FXML private TextField txtIsbn;
    @FXML private TextField txtSerie;
    @FXML private TextField txtEdicao;
    @FXML private TextField txtColecao;
    @FXML private TextField txtNotasGerais;
    @FXML private TextField txtVolumeLivro;

    // Campos Revista
    @FXML private TextField txtIssnRevista;
    @FXML private TextField txtVolumeRevista;
    @FXML private TextField txtPeriodicidadeRevista;

    // Campos Jornal
    @FXML private TextField txtIssnJornal;
    @FXML private TextField txtPeriodicidadeJornal;
    @FXML private TextField txtNomeJornal;

    private final ObraDAO obraDAO = new ObraDAO();

    @FXML
    public void initialize() {
        comboTipo.getItems().addAll("LIVRO", "REVISTA", "JORNAL");
        comboTipo.setOnAction(e -> atualizarCampos());

        carregarAssuntos();
        carregarEditoras();
        carregarAutores();
    }

    private void carregarAssuntos() {
        String sql = "SELECT * FROM ASSUNTOS";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                comboAssunto.getItems().add(new Assunto(rs.getInt("ID"), rs.getString("Assunto")));
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    private void carregarEditoras() {
        String sql = "SELECT * FROM EDITORA";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                comboEditora.getItems().add(new Editora(rs.getInt("ID"), rs.getString("Nome")));
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    private void carregarAutores() {
        String sql = "SELECT * FROM AUTORES";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            comboAutor.getItems().clear();
            while (rs.next()) {
                Autor autor = new Autor(
                        rs.getInt("ID"),
                        rs.getString("Nome"),
                        rs.getString("Nacionalidade")
                );
                comboAutor.getItems().add(autor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void atualizarCampos() {
        paneLivro.setVisible(false); paneLivro.setManaged(false);
        paneRevista.setVisible(false); paneRevista.setManaged(false);
        paneJornal.setVisible(false); paneJornal.setManaged(false);

        switch (comboTipo.getValue()) {
            case "LIVRO" -> { paneLivro.setVisible(true); paneLivro.setManaged(true); }
            case "REVISTA" -> { paneRevista.setVisible(true); paneRevista.setManaged(true); }
            case "JORNAL" -> { paneJornal.setVisible(true); paneJornal.setManaged(true); }
        }
    }

    @FXML
    private void salvarObra() {
        try {
            Obra obra = new Obra();

            // Comuns
            obra.setTituloPrincipal(txtTituloPrincipal.getText());
            obra.setCapa(txtCapa.getText());
            obra.setLocal(txtLocal.getText());
            obra.setData(txtData.getText());
            obra.setDescFisica(txtDescFisica.getText());
            obra.setTipo(comboTipo.getValue());

            // FKs (pegando IDs dos objetos selecionados)
            obra.setIdAssunto(comboAssunto.getValue().getId());
            obra.setIdEditora(comboEditora.getValue().getId());
            obra.setIdAutor(comboAutor.getValue().getId());

            // Específicos
            switch (comboTipo.getValue()) {
                case "LIVRO" -> {
                    obra.setNumeroChamada(txtNumeroChamada.getText());
                    obra.setChamadaLocal(txtChamadaLocal.getText());
                    obra.setTituloUniforme(txtTituloUniforme.getText());
                    obra.setIsbn(txtIsbn.getText());
                    obra.setSerie(Integer.parseInt(txtSerie.getText()));
                    obra.setEdicao(Integer.parseInt(txtEdicao.getText()));
                    obra.setColecao(txtColecao.getText());
                    obra.setNotasGerais(txtNotasGerais.getText());
                    obra.setVolume(Integer.parseInt(txtVolumeLivro.getText()));
                }
                case "REVISTA" -> {
                    obra.setIssn(txtIssnRevista.getText());
                    obra.setVolume(Integer.parseInt(txtVolumeRevista.getText()));
                    obra.setPeriodicidade(txtPeriodicidadeRevista.getText());
                }
                case "JORNAL" -> {
                    obra.setIssn(txtIssnJornal.getText());
                    obra.setPeriodicidade(txtPeriodicidadeJornal.getText());
                    obra.setNome(txtNomeJornal.getText());
                }
            }

            obraDAO.inserir(obra);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Cadastro");
            alert.setHeaderText(null);
            alert.setContentText("Obra cadastrada com sucesso!");
            alert.showAndWait();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText(null);
            alert.setContentText("Erro ao cadastrar obra: " + e.getMessage());
            alert.showAndWait();
        }
    }
}
