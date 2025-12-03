package inf.grupo.trabalhofinalrev2.controller;

import inf.grupo.trabalhofinalrev2.model.Obra;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DetalhesLivroController {


    @FXML private TextField txtIdObra;
    @FXML private TextField txtTituloPrincipal;
    @FXML private TextField txtTituloUniforme;
    @FXML private TextField txtLocal;
    @FXML private TextField txtData;
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
    }

    public void setObra(Obra obra) {
        this.obraAtual = obra;
        preencherDetalhes();
    }

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
        txtSerie.setText(obraAtual.getSerie() != null ? obraAtual.getSerie() : "");

        // Campos numéricos (int) - Tratando 0 como vazio
        txtEdicao.setText(obraAtual.getEdicao() > 0 ? String.valueOf(obraAtual.getEdicao()) : "");
        txtVolume.setText(obraAtual.getVolume() > 0 ? String.valueOf(obraAtual.getVolume()) : "");

        // 3. DESCRIÇÃO FÍSICA E NOTAS
        txtDescFisica.setText(obraAtual.getDescFisica() != null ? obraAtual.getDescFisica() : "");
        txtAreaNotasGerais.setText(obraAtual.getNotasGerais() != null ? obraAtual.getNotasGerais() : "");
    }

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
    }
}