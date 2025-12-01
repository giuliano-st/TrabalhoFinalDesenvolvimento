package inf.grupo.trabalhofinalrev2.controller;

import inf.grupo.trabalhofinalrev2.dao.ObraDAO;
import inf.grupo.trabalhofinalrev2.model.Obra;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

public class RemocaoGeralController {

    @FXML private ComboBox<String> comboTipo;
    @FXML private TableView<Obra> tabelaObras;
    @FXML private TableColumn<Obra, Integer> colId;
    @FXML private TableColumn<Obra, String> colTitulo;
    @FXML private TableColumn<Obra, String> colTipo;

    private ObservableList<Obra> listaObras = FXCollections.observableArrayList();
    private ObraDAO obraDAO = new ObraDAO();

    @FXML
    public void initialize() {
        // Configura colunas com os getters da classe Obra
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTitulo.setCellValueFactory(new PropertyValueFactory<>("tituloPrincipal"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));

        tabelaObras.setItems(listaObras);

        // Tipos disponíveis
        comboTipo.getItems().addAll("Livro", "Revista", "Jornal");
        comboTipo.setOnAction(e -> carregarDados(comboTipo.getValue()));
    }

    private void carregarDados(String tipo) {
        listaObras.clear();
        listaObras.addAll(obraDAO.buscarPorTipo(tipo));
    }

    @FXML
    private void removerSelecionado() {
        Obra selecionada = tabelaObras.getSelectionModel().getSelectedItem();
        if (selecionada != null) {
            boolean sucesso = obraDAO.removerPorId(selecionada.getId());
            if (sucesso) {
                listaObras.remove(selecionada);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Obra removida com sucesso!");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Erro ao remover obra no banco.");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Nenhuma obra selecionada.");
            alert.showAndWait();
        }
    }

    @FXML
    private void atualizarTabela() {
        if (comboTipo.getValue() != null) {
            carregarDados(comboTipo.getValue());
        }
    }

    @FXML
    private void editarSelecionado() {
        Obra selecionada = tabelaObras.getSelectionModel().getSelectedItem();
        if (selecionada != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/inf/grupo/trabalhofinalrev2/view/EdicaoObraView.fxml"));
                Parent root = loader.load();

                EdicaoObraController controller = loader.getController();
                controller.setObraSelecionada(selecionada);

                Stage stage = new Stage();
                stage.setTitle("Editar Obra");
                stage.setScene(new Scene(root));
                stage.showAndWait();

                tabelaObras.refresh();

            } catch (IOException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR, "Erro ao abrir tela de edição.");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Nenhuma obra selecionada.");
            alert.showAndWait();
        }
    }

}
