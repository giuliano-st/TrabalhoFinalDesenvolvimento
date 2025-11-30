// Salvar como ConsultaJornalController.java
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

public class ConsultaJornalController {

    @FXML private TextField campoTitulo;
    @FXML private TextField campoData;
    @FXML private TextField campoIssn;
    @FXML private TextField campoNomeJornal; // ALTERADO: Era campoEditora
    @FXML private TextField campoAssunto;
    @FXML private ComboBox<String> comboPeriodicidade;

    @FXML private TableView<Obra> tabelaJornais; // ALTERADO: Nome da Tabela
    @FXML private Button btnDetalhes;

    private final ObraDAO obraDAO = new ObraDAO();

    @FXML
    public void initialize() {

        comboPeriodicidade.getItems().addAll("QUALQUER","DIÁRIA", "SEMANAL", "QUINZENAL", "MENSAL");
        comboPeriodicidade.setValue("QUALQUER");
        configurarColunas();

        campoTitulo.textProperty().addListener((obs, oldV, newV) -> atualizarTabela());
        campoData.textProperty().addListener((obs, oldV, newV) -> atualizarTabela());
        campoIssn.textProperty().addListener((obs, oldV, newV) -> atualizarTabela());
        campoNomeJornal.textProperty().addListener((obs, oldV, newV) -> atualizarTabela()); // ALTERADO
        campoAssunto.textProperty().addListener((obs, oldV, newV) -> atualizarTabela());
        comboPeriodicidade.valueProperty().addListener((obs, oldV, newV) -> atualizarTabela());

        // Carrega a tabela inicialmente
        atualizarTabela();
    }

    private void configurarColunas() {

        tabelaJornais.getColumns().clear(); // ALTERADO: Nome da Tabela

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

        TableColumn<Obra, String> colNome = new TableColumn<>("Nome Jornal"); // ALTERADO: Coluna Nome
        colNome.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getNome()) // Mapeia para o campo 'Nome' do Jornal
        );

        TableColumn<Obra, String> colAssunto = new TableColumn<>("Assunto");
        colAssunto.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getAssuntoNome())
        );

        TableColumn<Obra, String> colPeriodicidade = new TableColumn<>("Periodicidade");
        colPeriodicidade.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getPeriodicidade())
        );

        tabelaJornais.getColumns().addAll(
                colTitulo, colData, colIssn, colNome, colAssunto, colPeriodicidade
        );
    }

    private void atualizarTabela() {
        String periodicidade;
        if(comboPeriodicidade.getValue().equals("QUALQUER")) {
            periodicidade = null;
        } else {
            periodicidade = comboPeriodicidade.getValue();
        }

        // ALTERADO: Chamada ao método de busca de Jornais
        List<Obra> jornais = obraDAO.buscarJornaisFiltrados(
                campoTitulo.getText(),
                campoData.getText(),
                campoIssn.getText(),
                campoNomeJornal.getText(), // ALTERADO: Passando o Nome do Jornal
                campoAssunto.getText(),
                periodicidade
        );

        tabelaJornais.getItems().setAll(jornais); // ALTERADO: Nome da Tabela
    }

    @FXML
    private void onVerDetalhes() {

        Obra selecionada = tabelaJornais.getSelectionModel().getSelectedItem(); // ALTERADO: Nome da Tabela

        if (selecionada == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Selecione um jornal primeiro.");
            alert.show();
            return;
        }

        try {
            // ALTERADO: Abre a tela de DetalhesJornalView
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/inf/grupo/trabalhofinalrev2/view/DetalhesJornalView.fxml"));
            Parent root = loader.load();

            DetalhesJornalController controller = loader.getController();
            controller.exibirDetalhes(selecionada);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Detalhes do Jornal");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}