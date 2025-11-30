package inf.grupo.trabalhofinalrev2.controller;

import inf.grupo.trabalhofinalrev2.model.Obra;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DetalhesLivroController {

    // --- Campos de Detalhes da Obra (TextFields não editáveis) ---
    // Identificação
    @FXML private TextField txtIdObra;
    @FXML private TextField txtTituloPrincipal;
    @FXML private TextField txtTituloUniforme;
    @FXML private TextField txtLocal;
    @FXML private TextField txtData; // Campo corrigido no FXML e injetado aqui
    @FXML private TextField txtISBN;
    @FXML private TextField txtEdicao;
    @FXML private TextField txtVolume;

    // Associações
    @FXML private TextField txtAutor;
    @FXML private TextField txtEditora;
    @FXML private TextField txtAssunto;
    @FXML private TextField txtColecao;
    @FXML private TextField txtSerie;

    // Detalhes Físicos/Chama
    @FXML private TextField txtNumChamada;
    @FXML private TextField txtChamadaLocal;
    @FXML private TextField txtDescFisica;

    // Outros
    @FXML private TextArea txtAreaNotasGerais;

    private Obra obraAtual;

    @FXML
    public void initialize() {
        // Inicialização de componentes (não é necessário nada aqui agora)
    }

    /**
     * Define a obra atual e inicia o preenchimento dos campos.
     * @param obra Objeto Obra a ser exibido.
     */
    public void setObra(Obra obra) {
        this.obraAtual = obra;
        preencherDetalhes();
    }

    /**
     * Preenche todos os campos de texto com os detalhes da obra.
     * Implementa checagem de nulidade robusta para evitar NullPointerExceptions.
     */
    private void preencherDetalhes() {
        if (obraAtual == null) return;

        // 1. INFORMAÇÕES BÁSICAS - Tratando Nulls para Strings
        // Campos que NÃO devem ser nulos (Id é primitivo, tratado via String.valueOf)
        txtIdObra.setText(String.valueOf(obraAtual.getId()));

        // Campos que PODEM ser nulos
        txtLocal.setText(obraAtual.getLocal() != null ? obraAtual.getLocal() : "");
        txtTituloPrincipal.setText(obraAtual.getTituloPrincipal() != null ? obraAtual.getTituloPrincipal() : "");
        txtTituloUniforme.setText(obraAtual.getTituloUniforme() != null ? obraAtual.getTituloUniforme() : "");
        txtData.setText(obraAtual.getData() != null ? obraAtual.getData() : "");

        // Associações
        txtAutor.setText(obraAtual.getNome() != null ? obraAtual.getNome() : "");
        txtEditora.setText(obraAtual.getEditoraNome() != null ? obraAtual.getEditoraNome() : "");
        txtAssunto.setText(obraAtual.getAssuntoNome() != null ? obraAtual.getAssuntoNome() : "");
        txtColecao.setText(obraAtual.getColecao() != null ? obraAtual.getColecao() : "");

        // 2. DETALHES DA PUBLICAÇÃO
        txtISBN.setText(obraAtual.getIsbn() != null ? obraAtual.getIsbn() : "");
        txtNumChamada.setText(obraAtual.getNumeroChamada() != null ? obraAtual.getNumeroChamada() : "");
        txtChamadaLocal.setText(obraAtual.getChamadaLocal() != null ? obraAtual.getChamadaLocal() : "");

        // Campos numéricos (int) - Tratando 0 como vazio
        txtEdicao.setText(obraAtual.getEdicao() > 0 ? String.valueOf(obraAtual.getEdicao()) : "");
        txtVolume.setText(obraAtual.getVolume() > 0 ? String.valueOf(obraAtual.getVolume()) : "");
        txtSerie.setText(obraAtual.getSerie() > 0 ? String.valueOf(obraAtual.getSerie()) : "");

        // 3. DESCRIÇÃO FÍSICA E NOTAS
        txtDescFisica.setText(obraAtual.getDescFisica() != null ? obraAtual.getDescFisica() : "");
        txtAreaNotasGerais.setText(obraAtual.getNotasGerais() != null ? obraAtual.getNotasGerais() : "");
    }

    // ------------------------------------
    // MÉTODOS DE AÇÃO DOS BOTÕES
    // ------------------------------------

    /**
     * Fecha a janela de detalhes.
     * Assume que o Stage pode ser obtido a partir de qualquer componente FXML injetado (ex: txtIdObra).
     */
    @FXML
    private void fecharDetalhes() {
        // Obtém o Stage (janela) atual e o fecha
        // Uma vez que txtIdObra é o primeiro a ser preenchido e é um TextField, ele é um bom ponto de referência.
        if (txtIdObra != null && txtIdObra.getScene() != null) {
            Stage currentStage = (Stage) txtIdObra.getScene().getWindow();
            if (currentStage != null) {
                currentStage.close();
            }
        }
        // Se a inicialização falhar (embora improvável após o FXML ser corrigido), não tentará fechar.
    }
}