package inf.grupo.trabalhofinalrev2.controller;

import inf.grupo.trabalhofinalrev2.dao.ObraDAO;
import inf.grupo.trabalhofinalrev2.model.Obra;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class ConsultaLivroController {

    @FXML private TextField txtFiltroTitulo;
    @FXML private TextField txtFiltroAutor;
    @FXML private TextField txtFiltroEditora;
    @FXML private TextField txtFiltroData;
    @FXML private TextField txtFiltroColecao;
    @FXML private TextField txtFiltroISBN;

    @FXML private TableView<Obra> tabelaLivros;
    @FXML private Button btnDetalhes;

    private final ObraDAO obraDAO = new ObraDAO();

    @FXML
    public void initialize() {
        configurarColunas();

        // Adiciona listeners para atualizar a tabela automaticamente ao digitar
        txtFiltroTitulo.textProperty().addListener((obs, oldV, newV) -> atualizarTabela());
        txtFiltroAutor.textProperty().addListener((obs, oldV, newV) -> atualizarTabela());
        txtFiltroEditora.textProperty().addListener((obs, oldV, newV) -> atualizarTabela());
        txtFiltroData.textProperty().addListener((obs, oldV, newV) -> atualizarTabela());
        txtFiltroColecao.textProperty().addListener((obs, oldV, newV) -> atualizarTabela());
        txtFiltroISBN.textProperty().addListener((obs, oldV, newV) -> atualizarTabela());

        atualizarTabela();
    }

    private void configurarColunas() {

        tabelaLivros.getColumns().clear();

        // ID
        TableColumn<Obra, Number> colId = new TableColumn<>("ID");
        // CORREÇÃO: Usando SimpleIntegerProperty, assumindo getId() retorna int primitivo.
        colId.setCellValueFactory(c ->
                new SimpleIntegerProperty(c.getValue().getId())
        );
        colId.setPrefWidth(50);

        // Título Principal
        TableColumn<Obra, String> colTitulo = new TableColumn<>("Título Principal");
        colTitulo.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getTituloPrincipal())
        );
        colTitulo.setPrefWidth(200);

        // Autor (Assumindo que getNome() foi mapeado para o nome do autor principal no DAO)
        TableColumn<Obra, String> colAutor = new TableColumn<>("Autor");
        colAutor.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getNome())
        );
        colAutor.setPrefWidth(150);

        // Editora
        TableColumn<Obra, String> colEditora = new TableColumn<>("Editora");
        colEditora.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getEditoraNome())
        );
        colEditora.setPrefWidth(120);

        // COLEÇÃO
        TableColumn<Obra, String> colColecao = new TableColumn<>("Coleção");
        colColecao.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getColecao())
        );
        colColecao.setPrefWidth(120);

        // ISBN
        TableColumn<Obra, String> colISBN = new TableColumn<>("ISBN");
        colISBN.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getIsbn())
        );
        colISBN.setPrefWidth(120);

        // Data
        TableColumn<Obra, String> colData = new TableColumn<>("Data");
        colData.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getData())
        );
        colData.setPrefWidth(80);


        tabelaLivros.getColumns().addAll(
                colId, colTitulo, colAutor, colEditora, colColecao, colISBN, colData
        );
    }

    private void atualizarTabela() {

        List<Obra> livros = obraDAO.buscarLivrosFiltrados(
                txtFiltroTitulo.getText(),
                txtFiltroAutor.getText(),
                txtFiltroEditora.getText(),
                txtFiltroData.getText(),
                txtFiltroColecao.getText(),
                txtFiltroISBN.getText()
        );

        tabelaLivros.getItems().setAll(livros);
    }

    @FXML
    private void onVerDetalhes() {

        Obra selecionada = tabelaLivros.getSelectionModel().getSelectedItem();

        if (selecionada == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Selecione um livro primeiro.");
            alert.show();
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/inf/grupo/trabalhofinalrev2/view/DetalhesLivroView.fxml"));
            Parent root = loader.load();

            DetalhesLivroController controller = loader.getController();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Detalhes do Livro: " + selecionada.getTituloPrincipal());

            // A LINHA ABAIXO FOI REMOVIDA
            // controller.setStage(stage);

            controller.setObra(selecionada);

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Não foi possível abrir a tela de detalhes: " + e.getMessage()).show();
        }
    }
}