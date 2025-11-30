package inf.grupo.trabalhofinalrev2.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ExemplarTabela {

    // O campo 'Número' é um número, mas pode ser tratado como String para a Tabela
    private final StringProperty numero;
    private final StringProperty disponibilidade;

    public ExemplarTabela(String numero, String disponibilidade) {
        this.numero = new SimpleStringProperty(numero);
        this.disponibilidade = new SimpleStringProperty(disponibilidade);
    }

    // Getters para a TableView
    public StringProperty numeroProperty() {
        return numero;
    }

    public StringProperty disponibilidadeProperty() {
        return disponibilidade;
    }

    // Métodos de acesso (opcional, mas boa prática)
    public String getNumero() { return numero.get(); }
    public String getDisponibilidade() { return disponibilidade.get(); }
}