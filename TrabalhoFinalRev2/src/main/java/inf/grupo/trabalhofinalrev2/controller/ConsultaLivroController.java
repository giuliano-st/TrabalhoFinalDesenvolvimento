package inf.grupo.trabalhofinalrev2.controller;

import inf.grupo.trabalhofinalrev2.dao.ObraDAO;
import inf.grupo.trabalhofinalrev2.model.Obra;
import inf.grupo.trabalhofinalrev2.model.ExemplarTabela;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class ConsultaLivroController {

    // --- FXML Fields ---
    @FXML private TextField txtTitulo;
    @FXML private TextField txtAutor;
    @FXML private TextField txtEditora;
    @FXML private TextField txtData;
    @FXML private TextField txtColecao;
    @FXML private TextField txtIsbn;

    @FXML private TableView<Obra> tabelaLivros;
    @FXML private TableColumn<Obra, Integer> colID;
    @FXML private TableColumn<Obra, String> colTitulo;
    @FXML private TableColumn<Obra, String> colAutor;
    @FXML private TableColumn<Obra, String> colEditora;
    @FXML private TableColumn<Obra, String> colColecao;
    @FXML private TableColumn<Obra, String> colIsbn;
    @FXML private TableColumn<Obra, String> colData;

    private ObraDAO obraDAO = new ObraDAO();
    private ObservableList<Obra> listaObras = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Inicialização das colunas
        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTitulo.setCellValueFactory(new PropertyValueFactory<>("tituloPrincipal"));
        // O autor é mapeado para a propriedade 'nome' no mapeamento da consulta,
        // mas você pode preferir mapear para 'autorNome' se tiver essa propriedade no model
        colAutor.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colEditora.setCellValueFactory(new PropertyValueFactory<>("editoraNome"));
        colColecao.setCellValueFactory(new PropertyValueFactory<>("colecao"));
        colIsbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        colData.setCellValueFactory(new PropertyValueFactory<>("data"));

        tabelaLivros.setItems(listaObras);

        // 1. Carrega todos os livros inicialmente
        aplicarFiltros();

        // 2. Adiciona listeners a todos os campos de texto para chamar aplicarFiltros()
        // sempre que o texto for alterado, garantindo o filtro em tempo real.
        txtTitulo.textProperty().addListener((obs, oldValue, newValue) -> aplicarFiltros());
        txtAutor.textProperty().addListener((obs, oldValue, newValue) -> aplicarFiltros());
        txtEditora.textProperty().addListener((obs, oldValue, newValue) -> aplicarFiltros());
        txtData.textProperty().addListener((obs, oldValue, newValue) -> aplicarFiltros());
        txtColecao.textProperty().addListener((obs, oldValue, newValue) -> aplicarFiltros());
        txtIsbn.textProperty().addListener((obs, oldValue, newValue) -> aplicarFiltros());
    }

    /**
     * Aplica os filtros atuais dos campos de texto na busca ao banco de dados
     * e atualiza a tabela. Este método substitui o handleBuscarLivros().
     */
    private void aplicarFiltros() {
        String titulo = txtTitulo.getText();
        String autor = txtAutor.getText();
        String editora = txtEditora.getText();
        String data = txtData.getText();
        String colecao = txtColecao.getText();
        String isbn = txtIsbn.getText();

        List<Obra> resultados = obraDAO.buscarLivrosFiltrados(titulo, autor, editora, data, colecao, isbn);
        listaObras.clear();
        listaObras.addAll(resultados);
    }

    /**
     * Abre a tela de detalhes para a obra selecionada.
     */
    @FXML
    private void handleVerDetalhes() {
        Obra obraSelecionada = tabelaLivros.getSelectionModel().getSelectedItem();

        if (obraSelecionada != null) {
            try {
                // Caminho atualizado para DetalhesLivroView.fxml (se for o caso)
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/inf/grupo/trabalhofinalrev2/view/DetalhesLivroView.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Detalhes do Livro");
                stage.setScene(new Scene(loader.load()));
                stage.initModality(Modality.APPLICATION_MODAL);

                DetalhesLivroController controller = loader.getController();
                controller.setObra(obraSelecionada); // Assumindo que você tem este método

                stage.showAndWait();
            } catch (IOException e) {
                exibirAlertaErro("Erro ao carregar detalhes", "Não foi possível carregar a tela de detalhes do livro.");
                e.printStackTrace();
            }
        } else {
            exibirAlertaAtencao("Nenhuma obra selecionada", "Por favor, selecione um livro na tabela para ver os detalhes.");
        }
    }

    /**
     * Abre a tela de Capa para a obra selecionada.
     */
    @FXML
    private void handleVisualizarCapa() {
        Obra obraSelecionada = tabelaLivros.getSelectionModel().getSelectedItem();

        if (obraSelecionada != null) {
            String caminhoCapa = obraSelecionada.getCapa();
            if (caminhoCapa == null || caminhoCapa.isEmpty()) {
                exibirAlertaAtencao("Capa não encontrada", "Não há um caminho de capa registrado para esta obra.");
                return;
            }

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/inf/grupo/trabalhofinalrev2/view/CapaVisualizacao.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Capa - " + obraSelecionada.getTituloPrincipal());
                stage.setScene(new Scene(loader.load()));
                stage.initModality(Modality.APPLICATION_MODAL);

                CapaVisualizacaoController controller = loader.getController();
                controller.setCapaImage(caminhoCapa);

                stage.showAndWait();
            } catch (IOException e) {
                exibirAlertaErro("Erro ao carregar capa", "Não foi possível carregar a tela de visualização da capa.");
                e.printStackTrace();
            }
        } else {
            exibirAlertaAtencao("Nenhuma obra selecionada", "Por favor, selecione um livro na tabela para visualizar a capa.");
        }
    }

    /**
     * Abre a tela de Disponibilidade para a obra selecionada.
     */
    @FXML
    private void handleVerificarDisponibilidade() {
        Obra obraSelecionada = tabelaLivros.getSelectionModel().getSelectedItem();

        if (obraSelecionada != null) {
            try {
                // 1. Busca os exemplares no banco
                // O método buscarExemplaresNoBanco é uma simulação, assegure-se que ele existe no seu ObraDAO
                List<ExemplarTabela> exemplares = obraDAO.buscarExemplaresNoBanco(obraSelecionada.getId());

                if (exemplares.isEmpty()) {
                    exibirAlertaAtencao("Sem Exemplares", "Não há exemplares cadastrados para esta obra no acervo.");
                    return;
                }

                // 2. Carrega a tela de Disponibilidade
                // Caminho atualizado para DisponibilidadeView.fxml (se for o caso)
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/inf/grupo/trabalhofinalrev2/view/DisponibilidadeView.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Disponibilidade de Exemplares");
                stage.setScene(new Scene(loader.load()));
                stage.initModality(Modality.APPLICATION_MODAL);

                // 3. Passa os dados para o controller e exibe
                DisponibilidadeController controller = loader.getController();
                controller.setExemplares(exemplares);

                stage.showAndWait();

            } catch (IOException e) {
                exibirAlertaErro("Erro de Tela", "Não foi possível carregar a tela de disponibilidade.");
                e.printStackTrace();
            } catch (Exception e) {
                exibirAlertaErro("Erro de Banco de Dados", "Ocorreu um erro ao buscar os exemplares.");
                e.printStackTrace();
            }
        } else {
            exibirAlertaAtencao("Nenhuma obra selecionada", "Por favor, selecione um livro na tabela para verificar a disponibilidade.");
        }
    }

    // --- Métodos de Utilitário para Alertas ---

    private void exibirAlertaAtencao(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.WARNING, mensagem, ButtonType.OK);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    private void exibirAlertaErro(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR, mensagem, ButtonType.OK);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}