package inf.grupo.trabalhofinalrev2.controller;

import inf.grupo.trabalhofinalrev2.dao.AssuntoDAO;
import inf.grupo.trabalhofinalrev2.model.Assunto;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CadastroAssuntoController {

    @FXML private TextField txtNovoAssunto;
    private AssuntoDAO assuntoDAO = new AssuntoDAO();
    private Stage stage;
    private Assunto novoAssuntoCadastrado;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    // Getter para pegar o novo objeto cadastrado de volta para a tela principal
    public Assunto getNovoAssuntoCadastrado() {
        return novoAssuntoCadastrado;
    }

    @FXML
    private void salvar() {
        String nome = txtNovoAssunto.getText().trim();

        if (nome.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Atenção", "O nome do assunto não pode ser vazio.");
            return;
        }

        try {
            // 1. Verificar se o assunto já existe
            if (assuntoDAO.existeAssunto(nome)) {
                showAlert(Alert.AlertType.WARNING, "Atenção", "O assunto '" + nome + "' já existe no banco de dados.");
                return;
            }

            // 2. Inserir
            Assunto assunto = new Assunto(nome);
            int id = assuntoDAO.inserir(assunto);

            // 3. Sucesso e preparação para retorno
            this.novoAssuntoCadastrado = new Assunto(id, nome);
            showAlert(Alert.AlertType.INFORMATION, "Sucesso", "Assunto cadastrado com sucesso!");

            // 4. Fechar a janela
            stage.close();

        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erro", "Erro ao salvar assunto: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void cancelar() {
        // Apenas fecha a janela (novoAssuntoCadastrado será null)
        stage.close();
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}