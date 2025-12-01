package inf.grupo.trabalhofinalrev2.controller;

import inf.grupo.trabalhofinalrev2.model.Livro;
import javafx.fxml.FXML;

import java.awt.*;

public class LivroCadastroController extends ObraController<Livro> {
    @FXML private TextField txtTitulo;
    @FXML private TextField txtAutor;
    @FXML private TextField txtIsbn;

    @Override
    public void onSalvar() {
        Livro livro = new Livro();
        livro.setTitulo(txtTitulo.getText());
        livro.setAutor(txtAutor.getText());
        livro.setIsbn(txtIsbn.getText());
        mostrarMensagem("Livro cadastrado com sucesso!");
    }

    @Override
    public void onNovo() {
        limparCampos(txtTitulo, txtAutor, txtIsbn);
    }
}
