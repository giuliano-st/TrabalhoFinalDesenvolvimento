package inf.grupo.trabalhofinalrev2.controller;

import inf.grupo.trabalhofinalrev2.model.ExemplarTabela;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell; // Import necessário
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback; // Import necessário

import java.util.List;

public class DisponibilidadeController {

    @FXML private TableView<ExemplarTabela> tabelaDisponibilidade;
    @FXML private TableColumn<ExemplarTabela, String> colunaNumero;
    @FXML private TableColumn<ExemplarTabela, String> colunaDisponibilidade;

    @FXML
    public void initialize() {
        // 1. Mapeamento padrão das colunas
        colunaNumero.setCellValueFactory(new PropertyValueFactory<>("numero"));

        // 2. Mapeamento da coluna de Disponibilidade (ainda usando a propriedade)
        colunaDisponibilidade.setCellValueFactory(new PropertyValueFactory<>("disponibilidade"));

        // 3. 🎨 APLICAÇÃO DO CELL FACTORY PARA ESTILIZAÇÃO
        colunaDisponibilidade.setCellFactory(new Callback<TableColumn<ExemplarTabela, String>, TableCell<ExemplarTabela, String>>() {
            @Override
            public TableCell<ExemplarTabela, String> call(TableColumn<ExemplarTabela, String> param) {
                return new TableCell<ExemplarTabela, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);

                        // 🧹 Limpa estilos anteriores para evitar acúmulo
                        getStyleClass().removeAll("disponivel", "emprestado");

                        if (empty || item == null) {
                            setText(null);
                        } else {
                            setText(item);

                            // 🔍 Aplica a classe CSS baseada no valor do item
                            if (item.equalsIgnoreCase("Disponivel")) {
                                getStyleClass().add("disponivel");
                            } else if (item.equalsIgnoreCase("Emprestado")) {
                                getStyleClass().add("emprestado");
                            }
                        }
                    }
                };
            }
        });
        // Fim da configuração do Cell Factory
    }

    public void setExemplares(List<ExemplarTabela> listaExemplares) {
        ObservableList<ExemplarTabela> dados = FXCollections.observableArrayList(listaExemplares);
        tabelaDisponibilidade.setItems(dados);
    }
}