package inf.grupo.trabalhofinalrev2.model;

public class Assunto {
    private int id;
    private String assunto;

    public Assunto(int id, String assunto) {
        this.id = id;
        this.assunto = assunto;
    }

    public Assunto(String assunto) {
        this.assunto = assunto;
    }

    public int getId() { return id; }
    public String getAssunto() { return assunto; }

    @Override
    public String toString() {
        return assunto; // mostra o nome no ComboBox
    }
}