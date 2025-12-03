package inf.grupo.trabalhofinalrev2.model;

public class Obra {

    private int id;
    private String tituloPrincipal;
    private String capa;
    private String local;
    private String data;
    private String descFisica;
    private String numeroChamada;
    private String chamadaLocal;
    private String tituloUniforme;
    private String isbn;
    private String serie;
    private int edicao;
    private String colecao;
    private String notasGerais;
    private String issn;
    private int volume;
    private String periodicidade;
    private String nome;
    private String tipo;
    private String editoraNome;
    private String assuntoNome;
    private Integer idEditora;
    private Integer idAssunto;
    private Integer idAutor; //ihh

    public Integer getIdEditora() {
        return idEditora;
    }

    public void setIdEditora(Integer idEditora) {
        this.idEditora = idEditora;
    }

    public Integer getIdAssunto() {
        return idAssunto;
    }

    public void setIdAssunto(Integer idAssunto) {
        this.idAssunto = idAssunto;
    }

    public String getEditoraNome() {
        return editoraNome;
    }

    public void setEditoraNome(String editoraNome) {
        this.editoraNome = editoraNome;
    }

    public String getAssuntoNome() {
        return assuntoNome;
    }

    public void setAssuntoNome(String assuntoNome) {
        this.assuntoNome = assuntoNome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTituloPrincipal() {
        return tituloPrincipal;
    }

    public void setTituloPrincipal(String tituloPrincipal) {
        this.tituloPrincipal = tituloPrincipal;
    }

    public String getCapa() {
        return capa;
    }

    public void setCapa(String capa) {
        this.capa = capa;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDescFisica() {
        return descFisica;
    }

    public void setDescFisica(String descFisica) {
        this.descFisica = descFisica;
    }

    public String getNumeroChamada() {
        return numeroChamada;
    }

    public void setNumeroChamada(String numeroChamada) {
        this.numeroChamada = numeroChamada;
    }

    public String getChamadaLocal() {
        return chamadaLocal;
    }

    public void setChamadaLocal(String chamadaLocal) {
        this.chamadaLocal = chamadaLocal;
    }

    public String getTituloUniforme() {
        return tituloUniforme;
    }

    public void setTituloUniforme(String tituloUniforme) {
        this.tituloUniforme = tituloUniforme;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public int getEdicao() {
        return edicao;
    }

    public void setEdicao(int edicao) {
        this.edicao = edicao;
    }

    public String getColecao() {
        return colecao;
    }

    public void setColecao(String colecao) {
        this.colecao = colecao;
    }

    public String getNotasGerais() {
        return notasGerais;
    }

    public void setNotasGerais(String notasGerais) {
        this.notasGerais = notasGerais;
    }

    public String getIssn() {
        return issn;
    }

    public void setIssn(String issn) {
        this.issn = issn;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public String getPeriodicidade() {
        return periodicidade;
    }

    public void setPeriodicidade(String periodicidade) {
        this.periodicidade = periodicidade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getIdAutor() { return idAutor; }

    public void setIdAutor(Integer idAutor) { this.idAutor = idAutor; }
}
