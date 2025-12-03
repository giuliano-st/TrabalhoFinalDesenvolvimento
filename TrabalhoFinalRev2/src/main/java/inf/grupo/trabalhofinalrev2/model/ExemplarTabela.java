package inf.grupo.trabalhofinalrev2.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ExemplarTabela {

    private final StringProperty numero;
    private final StringProperty disponibilidade;

    public ExemplarTabela(String numero, String disponibilidade) {
        this.numero = new SimpleStringProperty(numero);
        this.disponibilidade = new SimpleStringProperty(disponibilidade);
    }

    // --- numero ---
    public String getNumero() {
        return numero.get();
    }

    public void setNumero(String numero) {
        this.numero.set(numero);
    }

    public StringProperty numeroProperty() {
        return numero;
    }

    // --- disponibilidade ---
    public String getDisponibilidade() {
        return disponibilidade.get();
    }

    public void setDisponibilidade(String disponibilidade) {
        this.disponibilidade.set(disponibilidade);
    }

    public StringProperty disponibilidadeProperty() {
        return disponibilidade;
    }
}