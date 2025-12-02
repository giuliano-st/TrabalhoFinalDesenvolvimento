package inf.grupo.trabalhofinalrev2.controller;

import inf.grupo.trabalhofinalrev2.dao.ObraDAO;
import inf.grupo.trabalhofinalrev2.model.Obra;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class ConsultaRevistaController {

    @FXML private TextField campoTitulo;
    @FXML private TextField campoData;
    @FXML private TextField campoIssn;
    @FXML private TextField campoEditora;
    @FXML private TextField campoAssunto;
    @FXML private ComboBox<String> comboPeriodicidade;

    @FXML private TableView<Obra> tabelaRevistas;
    @FXML private Button btnDetalhes;

    private final ObraDAO obraDAO = new ObraDAO();

    @FXML
    public void initialize() {

        comboPeriodicidade.getItems().addAll("QUALQUER","SEMANAL", "MENSAL", "BIMESTRAL", "ANUAL");
        comboPeriodicidade.setValue("QUALQUER");
        configurarColunas();
        atualizarTabela();
        campoTitulo.textProperty().addListener((obs, oldV, newV) -> atualizarTabela());
        campoData.textProperty().addListener((obs, oldV, newV) -> atualizarTabela());
        campoIssn.textProperty().addListener((obs, oldV, newV) -> atualizarTabela());
        campoEditora.textProperty().addListener((obs, oldV, newV) -> atualizarTabela());
        campoAssunto.textProperty().addListener((obs, oldV, newV) -> atualizarTabela());
        comboPeriodicidade.valueProperty().addListener((obs, oldV, newV) -> atualizarTabela());
    }

    private void configurarColunas() {

        tabelaRevistas.getColumns().clear();

        TableColumn<Obra, String> colTitulo = new TableColumn<>("Título");
        colTitulo.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getTituloPrincipal())
        );

        TableColumn<Obra, String> colData = new TableColumn<>("Data");
        colData.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getData())
        );

        TableColumn<Obra, String> colIssn = new TableColumn<>("ISSN");
        colIssn.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getIssn())
        );

        TableColumn<Obra, String> colEditora = new TableColumn<>("Editora");
        colEditora.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getEditoraNome())
        );

        TableColumn<Obra, String> colAssunto = new TableColumn<>("Assunto");
        colAssunto.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getAssuntoNome())
        );

        TableColumn<Obra, String> colPeriodicidade = new TableColumn<>("Periodicidade");
        colPeriodicidade.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getPeriodicidade())
        );

        tabelaRevistas.getColumns().addAll(
                colTitulo, colData, colIssn, colEditora, colAssunto, colPeriodicidade
        );
    }

    private void atualizarTabela() {
        String periodicidade;
        if(comboPeriodicidade.getValue().equals("QUALQUER")) {
            periodicidade = null;
        }else{
            periodicidade = comboPeriodicidade.getValue();
        }

        List<Obra> revistas = obraDAO.buscarRevistasFiltradas(
                campoTitulo.getText(),
                campoData.getText(),
                campoIssn.getText(),
                campoEditora.getText(),
                campoAssunto.getText(),
                periodicidade
        );

        tabelaRevistas.getItems().setAll(revistas);
    }

    @FXML
    private void onVerDetalhes() {

        Obra selecionada = tabelaRevistas.getSelectionModel().getSelectedItem();

        if (selecionada == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Selecione uma revista primeiro.");
            alert.show();
            return;
        }

        try {

            System.out.println("FXML = " +
                    getClass().getResource("/inf/grupo/trabalhofinalrev2/view/DetalhesRevistaView.fxml"));

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/inf/grupo/trabalhofinalrev2/view/DetalhesRevistaView.fxml"));
            Parent root = loader.load();

            DetalhesRevistaController controller = loader.getController();
            controller.exibirDetalhes(selecionada);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Detalhes da Revista");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
