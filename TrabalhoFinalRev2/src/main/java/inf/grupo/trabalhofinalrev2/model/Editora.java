package inf.grupo.trabalhofinalrev2.model;

public class Editora {
    private int id;
    private String nome;

    public Editora(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public int getId() { return id; }
    public String getNome() { return nome; }

    @Override
    public String toString() {
        return nome;
    }
}
