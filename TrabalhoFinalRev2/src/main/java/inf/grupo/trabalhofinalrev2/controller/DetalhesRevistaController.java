package inf.grupo.trabalhofinalrev2.controller;

import inf.grupo.trabalhofinalrev2.model.Obra;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class DetalhesRevistaController {

    @FXML private TextField titulo;
    @FXML private TextField data;
    @FXML private TextField issn;
    @FXML private TextField editora;
    @FXML private TextField assunto;
    @FXML private TextField periodicidade;

    public void exibirDetalhes(Obra obra) {
        titulo.setText(obra.getTituloPrincipal());
        data.setText(obra.getData());
        issn.setText(obra.getIssn());
        editora.setText(obra.getEditoraNome());
        assunto.setText(obra.getAssuntoNome());
        periodicidade.setText(obra.getPeriodicidade());
    }
}
