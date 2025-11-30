// CadastroEditoraController.java (No pacote inf.grupo.trabalhofinalrev2.controller)
package inf.grupo.trabalhofinalrev2.controller;

import inf.grupo.trabalhofinalrev2.dao.EditoraDAO;
import inf.grupo.trabalhofinalrev2.model.Editora;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CadastroEditoraController {

    @FXML private TextField txtNovaEditora;
    private EditoraDAO editoraDAO = new EditoraDAO();
    private Stage stage;
    private Editora novaEditoraCadastrada;

    // Método chamado ao abrir a tela
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    // Getter para pegar o novo objeto cadastrado de volta para a tela principal
    public Editora getNovaEditoraCadastrada() {
        return novaEditoraCadastrada;
    }

    @FXML
    private void salvar() {
        String nome = txtNovaEditora.getText().trim();

        if (nome.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Atenção", "O nome da editora não pode ser vazio.");
            return;
        }

        try {
            // 1. Verificar se a editora já existe
            if (editoraDAO.existeEditora(nome)) {
                showAlert(Alert.AlertType.WARNING, "Atenção", "A editora '" + nome + "' já existe no banco de dados.");
                return;
            }

            // 2. Inserir
            Editora editora = new Editora(0, nome); // Usa 0 como placeholder, o ID será gerado
            int id = editoraDAO.inserir(editora);

            // 3. Sucesso e preparação para retorno
            this.novaEditoraCadastrada = new Editora(id, nome);
            showAlert(Alert.AlertType.INFORMATION, "Sucesso", "Editora cadastrada com sucesso!");

            // 4. Fechar a janela
            stage.close();

        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erro", "Erro ao salvar editora: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void cancelar() {
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