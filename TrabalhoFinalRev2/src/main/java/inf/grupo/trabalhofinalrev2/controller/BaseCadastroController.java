package inf.grupo.trabalhofinalrev2.controller;

import javafx.scene.control.Alert;

import java.awt.*;

public abstract class BaseCadastroController<T> {
    public abstract void onSalvar();
    public abstract void onNovo();

    // Métodos comuns
    protected void limparCampos(TextField... campos) {
        for (TextField campo : campos) {
            //campo.clear();
        }
    }

    protected void mostrarMensagem(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, msg);
        alert.showAndWait();
    }
}
