// CadastroAutorController.java (No pacote inf.grupo.trabalhofinalrev2.controller)
package inf.grupo.trabalhofinalrev2.controller;

import inf.grupo.trabalhofinalrev2.dao.AutorDAO;
import inf.grupo.trabalhofinalrev2.model.Autor;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CadastroAutorController {

    @FXML private TextField txtNovoAutor;
    @FXML private TextField txtNacionalidade;

    private AutorDAO autorDAO = new AutorDAO();
    private Stage stage;
    private Autor novoAutorCadastrado;

    // Construtor ou método setStage
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    // Getter para pegar o novo objeto cadastrado de volta
    public Autor getNovoAutorCadastrado() {
        return novoAutorCadastrado;
    }

    @FXML
    private void salvar() {
        String nome = txtNovoAutor.getText().trim();
        String nacionalidade = txtNacionalidade.getText().trim();

        if (nome.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Atenção", "O nome do autor não pode ser vazio.");
            return;
        }

        // Validação da Nacionalidade (Máximo 3 letras)
        if (nacionalidade.isEmpty() || nacionalidade.length() > 3 || !nacionalidade.matches("[a-zA-Z]+")) {
            showAlert(Alert.AlertType.WARNING, "Atenção", "A nacionalidade deve ter no máximo 3 letras (Ex: BRA, USA).");
            return;
        }

        try {
            // 1. Verificar se o autor já existe (pelo nome)
            if (autorDAO.existeAutor(nome)) {
                showAlert(Alert.AlertType.WARNING, "Atenção", "O autor '" + nome + "' já existe no banco de dados.");
                return;
            }

            // 2. Inserir
            Autor autor = new Autor(0, nome, nacionalidade.toUpperCase());
            int id = autorDAO.inserir(autor);

            // 3. Sucesso e preparação para retorno
            this.novoAutorCadastrado = new Autor(id, nome, nacionalidade.toUpperCase());
            showAlert(Alert.AlertType.INFORMATION, "Sucesso", "Autor cadastrado com sucesso!");

            // 4. Fechar a janela
            stage.close();

        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erro", "Erro ao salvar autor: " + e.getMessage());
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