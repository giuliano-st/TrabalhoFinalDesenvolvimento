package inf.grupo.trabalhofinalrev2.model;

public class Autor {
    private int id;
    private String nome;
    private String nacionalidade;

    public Autor(int id, String nome, String nacionalidade) {
        this.id = id;
        this.nome = nome;
        this.nacionalidade = nacionalidade;
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getNacionalidade() { return nacionalidade; }

    @Override
    public String toString() {
        return nome; // mostra o nome no ComboBox
    }
}