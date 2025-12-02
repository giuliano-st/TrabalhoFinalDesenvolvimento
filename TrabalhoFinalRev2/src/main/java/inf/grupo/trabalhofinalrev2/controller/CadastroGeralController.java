package inf.grupo.trabalhofinalrev2.controller;

import inf.grupo.trabalhofinalrev2.dao.ObraDAO;
import inf.grupo.trabalhofinalrev2.db.Conexao;
import inf.grupo.trabalhofinalrev2.model.Assunto;
import inf.grupo.trabalhofinalrev2.model.Autor;
import inf.grupo.trabalhofinalrev2.model.Editora;
import inf.grupo.trabalhofinalrev2.model.Obra;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.IOException; // Import adicionado
import java.net.URL; // Import adicionado
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CadastroGeralController {

    @FXML private ComboBox<String> comboTipo;
    @FXML private VBox paneLivro;
    @FXML private GridPane paneRevista;
    @FXML private GridPane paneJornal;

    // CAMPOS COMUNS
    @FXML private TextField txtTituloPrincipal;
    @FXML private TextField txtLocal;
    @FXML private TextField txtData;
    @FXML private TextField txtDescFisica;

    // NOVO: Campos para Seleção de Capa
    @FXML private Label lblCaminhoCapa;
    private String caminhoCapaSelecionada = "";

    // COMBOS DE FKs
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

        // Verifica se o valor é nulo antes de chamar o switch
        String tipo = comboTipo.getValue();
        if (tipo == null) return;

        switch (tipo) {
            case "LIVRO" -> { paneLivro.setVisible(true); paneLivro.setManaged(true); }
            case "REVISTA" -> { paneRevista.setVisible(true); paneRevista.setManaged(true); }
            case "JORNAL" -> { paneJornal.setVisible(true); paneJornal.setManaged(true); }
        }
    }

    @FXML
    private void selecionarCapa() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecionar Arquivo de Capa");

        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Arquivos de Imagem", "*.jpg", "*.jpeg", "*.png", "*.gif");
        fileChooser.getExtensionFilters().add(imageFilter);

        Stage stage = (Stage) lblCaminhoCapa.getScene().getWindow();

        File arquivoSelecionado = fileChooser.showOpenDialog(stage);

        if (arquivoSelecionado != null) {
            String caminhoOriginal = arquivoSelecionado.getAbsolutePath();
            // Garante que o caminho seja formatado com barras duplas invertidas para salvar corretamente no banco (Windows)
            String caminhoFormatado = caminhoOriginal.replace("\\", "\\\\");

            this.caminhoCapaSelecionada = caminhoFormatado;

            lblCaminhoCapa.setText(arquivoSelecionado.getName());
            lblCaminhoCapa.setStyle("-fx-text-fill: green;");

            System.out.println("Caminho salvo: " + this.caminhoCapaSelecionada); // Debug
        } else {
            lblCaminhoCapa.setText("Nenhuma capa selecionada");
            lblCaminhoCapa.setStyle("-fx-text-fill: gray;");
            this.caminhoCapaSelecionada = "";
        }
    }

    @FXML
    private void salvarObra() {
        try {
            Obra obra = new Obra();

            // 1. Validação de ComboBoxes e obtenção de valores
            String tipoSelecionado = comboTipo.getValue();
            Assunto assuntoSelecionado = comboAssunto.getValue();
            Editora editoraSelecionada = comboEditora.getValue();
            Autor autorSelecionado = comboAutor.getValue();

            if (tipoSelecionado == null) {
                throw new IllegalArgumentException("Selecione o Tipo de Obra (LIVRO, REVISTA, JORNAL).");
            }
            if (assuntoSelecionado == null || editoraSelecionada == null || autorSelecionado == null) {
                throw new IllegalArgumentException("Selecione Assunto, Editora e Autor antes de salvar.");
            }

            // 2. Comuns e FKs
            obra.setTituloPrincipal(txtTituloPrincipal.getText());
            obra.setCapa(this.caminhoCapaSelecionada);
            obra.setLocal(txtLocal.getText());
            obra.setData(txtData.getText());
            obra.setDescFisica(txtDescFisica.getText());
            obra.setTipo(tipoSelecionado);

            // FKs (pegando IDs dos objetos selecionados)
            obra.setIdAssunto(assuntoSelecionado.getId());
            obra.setIdEditora(editoraSelecionada.getId());
            obra.setIdAutor(autorSelecionado.getId());

            // Função auxiliar para converter String vazia para 0
            // Usamos Integer para permitir null ou 0, dependendo da necessidade do DAO.
            java.util.function.Function<String, Integer> parseIfPresent = text -> {
                if (text == null || text.trim().isEmpty()) return 0; // Usando 0 para campos INT não obrigatórios.
                return Integer.parseInt(text.trim());
            };

            // 3. Específicos
            switch (tipoSelecionado) {
                case "LIVRO" -> {
                    obra.setNumeroChamada(txtNumeroChamada.getText());
                    obra.setChamadaLocal(txtChamadaLocal.getText());
                    obra.setTituloUniforme(txtTituloUniforme.getText());
                    obra.setIsbn(txtIsbn.getText());

                    // Tratamento de campos numéricos (evita NumberFormatException)
                    obra.setSerie(parseIfPresent.apply(txtSerie.getText()));
                    obra.setEdicao(parseIfPresent.apply(txtEdicao.getText()));
                    obra.setVolume(parseIfPresent.apply(txtVolumeLivro.getText()));

                    obra.setColecao(txtColecao.getText());
                    obra.setNotasGerais(txtNotasGerais.getText());
                }
                case "REVISTA" -> {
                    obra.setIssn(txtIssnRevista.getText());

                    // Tratamento de campo numérico
                    obra.setVolume(parseIfPresent.apply(txtVolumeRevista.getText()));

                    obra.setPeriodicidade(txtPeriodicidadeRevista.getText());
                }
                case "JORNAL" -> {
                    // Validação de Nome, se for obrigatório
                    if (txtNomeJornal.getText() == null || txtNomeJornal.getText().trim().isEmpty()) {
                        throw new IllegalArgumentException("O campo Nome do Jornal não pode estar vazio.");
                    }
                    String issn = txtIssnJornal.getText();
                    if (issn != null && issn.length() > 9) {
                        throw new IllegalArgumentException("O ISSN do Jornal excedeu o tamanho máximo (9 caracteres).");
                    }else{
                        obra.setIssn(txtIssnJornal.getText());
                    }
                    obra.setPeriodicidade(txtPeriodicidadeJornal.getText());
                    obra.setNome(txtNomeJornal.getText()); // Campo corrigido para Jornal

                }
            }

            // 4. Inserção
            obraDAO.inserir(obra);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Cadastro");
            alert.setHeaderText(null);
            alert.setContentText("Obra cadastrada com sucesso!");
            alert.showAndWait();

            limparCampos();

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro de Dados");
            alert.setHeaderText(null);
            alert.setContentText("Certifique-se de que os campos numéricos (Série, Edição, Volume) estão preenchidos corretamente. Detalhes: " + e.getMessage());
            alert.showAndWait();
        } catch (IllegalArgumentException e) {
            // Captura erros da nossa validação (FKs vazias, Nome do Jornal vazio)
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro de Validação");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        } catch (Exception e) {
            // Captura erros gerais, incluindo possíveis falhas no DAO ou SQL
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText(null);
            alert.setContentText("Erro ao cadastrar obra: " + e.getMessage());
            e.printStackTrace();
            alert.showAndWait();
        }
    }

    // --- Métodos de Cadastro Auxiliares (Corrigidos para Debug) ---

    private void abrirModalCadastro(String fxmlPath, String title) throws IOException {
        // FERRAMENTA DE DEBUG: Imprimir a URL antes de tentar carregar
        URL location = getClass().getResource(fxmlPath);

        if (location == null) {
            // Lança uma exceção clara se o FXML não for encontrado
            throw new IOException("O recurso FXML não foi encontrado no caminho: " + fxmlPath +
                    ". Verifique o caminho e a estrutura de pastas/classpath.");
        }

        FXMLLoader loader = new FXMLLoader(location);
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);

        // O controller deve ser setado aqui ou no caller para manipular o retorno
        // Exemplo:
        // if (loader.getController() instanceof SeuController) {
        //     ((SeuController) loader.getController()).setStage(stage);
        // }

        stage.showAndWait();
    }


    @FXML
    private void cadastrarNovoAssunto() {
        try {
            String fxmlPath = "/inf/grupo/trabalhofinalrev2/view/CadastroAssuntoView.fxml";
            URL location = getClass().getResource(fxmlPath);

            if (location == null) {
                throw new IOException("O FXML não foi encontrado: " + fxmlPath);
            }

            FXMLLoader loader = new FXMLLoader(location);
            Parent root = loader.load();
            CadastroAssuntoController controller = loader.getController();

            Stage stage = new Stage();
            stage.setTitle("Cadastro de Novo Assunto");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            controller.setStage(stage);

            stage.showAndWait();

            Assunto novoAssunto = controller.getNovoAssuntoCadastrado();
            if (novoAssunto != null) {
                comboAssunto.getItems().add(novoAssunto);
                comboAssunto.getSelectionModel().select(novoAssunto);
            }
        } catch (IOException e) {
            System.err.println("Erro de localização/carregamento do FXML: " + e.getMessage());
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro de Carregamento");
            alert.setHeaderText(null);
            alert.setContentText("Não foi possível abrir a tela de cadastro de assunto. Verifique o caminho do FXML: " + e.getMessage());
            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText(null);
            alert.setContentText("Não foi possível abrir a tela de cadastro de assunto: " + e.getMessage());
            alert.showAndWait();
        }
    }


    @FXML
    private void cadastrarNovaEditora() {
        try {
            String fxmlPath = "/inf/grupo/trabalhofinalrev2/view/CadastroEditoraView.fxml";
            URL location = getClass().getResource(fxmlPath);

            if (location == null) {
                throw new IOException("O FXML não foi encontrado: " + fxmlPath);
            }

            FXMLLoader loader = new FXMLLoader(location);
            Parent root = loader.load();
            CadastroEditoraController controller = loader.getController();

            Stage stage = new Stage();
            stage.setTitle("Cadastro de Nova Editora");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            controller.setStage(stage);
            stage.showAndWait();

            Editora novaEditora = controller.getNovaEditoraCadastrada();
            if (novaEditora != null) {
                comboEditora.getItems().add(novaEditora);
                comboEditora.getSelectionModel().select(novaEditora);
            }
        } catch (IOException e) {
            System.err.println("Erro de localização/carregamento do FXML: " + e.getMessage());
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro de Carregamento");
            alert.setHeaderText(null);
            alert.setContentText("Não foi possível abrir a tela de cadastro de editora. Verifique o caminho do FXML: " + e.getMessage());
            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText(null);
            alert.setContentText("Não foi possível abrir a tela de cadastro de editora: " + e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void cadastrarNovoAutor() {
        try {
            String fxmlPath = "/inf/grupo/trabalhofinalrev2/view/CadastroAutorView.fxml";
            URL location = getClass().getResource(fxmlPath);

            if (location == null) {
                throw new IOException("O FXML não foi encontrado: " + fxmlPath);
            }

            FXMLLoader loader = new FXMLLoader(location);
            Parent root = loader.load();
            CadastroAutorController controller = loader.getController();

            Stage stage = new Stage();
            stage.setTitle("Cadastro de Novo Autor");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            controller.setStage(stage);
            stage.showAndWait();

            Autor novoAutor = controller.getNovoAutorCadastrado();
            if (novoAutor != null) {
                comboAutor.getItems().add(novoAutor);
                comboAutor.getSelectionModel().select(novoAutor);
            }
        } catch (IOException e) {
            System.err.println("Erro de localização/carregamento do FXML: " + e.getMessage());
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro de Carregamento");
            alert.setHeaderText(null);
            alert.setContentText("Não foi possível abrir a tela de cadastro de autor. Verifique o caminho do FXML: " + e.getMessage());
            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText(null);
            alert.setContentText("Não foi possível abrir a tela de cadastro de autor: " + e.getMessage());
            alert.showAndWait();
        }
    }

    private void limparCampos() {
        txtTituloPrincipal.clear();
        txtLocal.clear();
        txtData.clear();
        txtDescFisica.clear();

        comboTipo.getSelectionModel().clearSelection();
        comboAssunto.getSelectionModel().clearSelection();
        comboEditora.getSelectionModel().clearSelection();
        comboAutor.getSelectionModel().clearSelection();

        txtNumeroChamada.clear();
        txtChamadaLocal.clear();
        txtTituloUniforme.clear();
        txtIsbn.clear();
        txtSerie.clear();
        txtEdicao.clear();
        txtColecao.clear();
        txtNotasGerais.clear();
        txtVolumeLivro.clear();

        txtIssnRevista.clear();
        txtVolumeRevista.clear();
        txtPeriodicidadeRevista.clear();

        txtIssnJornal.clear();
        txtPeriodicidadeJornal.clear();
        txtNomeJornal.clear();

        this.caminhoCapaSelecionada = "";
        lblCaminhoCapa.setText("Nenhuma capa selecionada");
        lblCaminhoCapa.setStyle("-fx-text-fill: gray;");

        atualizarCampos();
    }

}