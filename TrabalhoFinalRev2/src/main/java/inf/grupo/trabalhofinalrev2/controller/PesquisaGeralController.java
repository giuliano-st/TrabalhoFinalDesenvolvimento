package inf.grupo.trabalhofinalrev2.controller;

import inf.grupo.trabalhofinalrev2.dao.ObraDAO;
import inf.grupo.trabalhofinalrev2.model.Obra;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class PesquisaGeralController {

    @FXML
    private ComboBox<String> comboTipo;

    @FXML
    private TableView<Obra> tabelaObras;

    private final ObraDAO obraDAO = new ObraDAO();

    @FXML
    public void initialize() {

        comboTipo.getItems().addAll("LIVRO", "REVISTA", "JORNAL");

        comboTipo.setOnAction(e -> carregarTabela());
    }

    private void carregarTabela() {

        String tipo = comboTipo.getValue();
        if (tipo == null) return;

        tabelaObras.getColumns().clear();

        // Carrega do banco
        List<Obra> lista = obraDAO.buscarPorTipo(tipo);

        // Define as colunas dependendo do tipo
        switch (tipo) {

            case "LIVRO" -> setColunasLivro();
            case "REVISTA" -> setColunasRevista();
            case "JORNAL" -> setColunasJornal();
        }

        tabelaObras.getItems().setAll(lista);
    }

    private void setColunasLivro() {
        addColuna("Título", "tituloPrincipal");
        addColuna("Local", "local");
        addColuna("Data", "data");
        addColuna("Título Uniforme", "tituloUniforme");
        addColuna("ISBN", "isbn");
        addColuna("Série", "serie");
        addColuna("Edição", "edicao");
        addColuna("Coleção", "colecao");
        addColuna("Volume", "volume");
    }

    private void setColunasRevista() {
        addColuna("Título", "tituloPrincipal");
        addColuna("Local", "local");
        addColuna("Data", "data");
        addColuna("Série", "serie");
        addColuna("Edição", "edicao");
        addColuna("Coleção", "colecao");
        addColuna("ISSN", "issn");
        addColuna("Volume", "volume");
        addColuna("Periodicidade", "periodicidade");
    }

    private void setColunasJornal() {
        addColuna("Título", "tituloPrincipal");
        addColuna("Local", "local");
        addColuna("Data", "data");
        addColuna("Periodicidade", "periodicidade");
        addColuna("Nome", "nome");
        addColuna("Tipo", "tipo");
    }

    private void addColuna(String titulo, String atributo) {
        TableColumn<Obra, Object> col = new TableColumn<>(titulo);
        col.setCellValueFactory(new PropertyValueFactory<>(atributo));
        tabelaObras.getColumns().add(col);
    }
}
