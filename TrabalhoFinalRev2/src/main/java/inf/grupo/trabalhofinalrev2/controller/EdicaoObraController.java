package inf.grupo.trabalhofinalrev2.controller;

import inf.grupo.trabalhofinalrev2.dao.ObraDAO;
import inf.grupo.trabalhofinalrev2.db.Conexao;
import inf.grupo.trabalhofinalrev2.model.Assunto;
import inf.grupo.trabalhofinalrev2.model.Autor;
import inf.grupo.trabalhofinalrev2.model.Editora;
import inf.grupo.trabalhofinalrev2.model.Obra;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EdicaoObraController {

    @FXML private TextField txtTituloPrincipal;
    @FXML private TextField txtCapa;
    @FXML private TextField txtLocal;
    @FXML private TextField txtData;
    @FXML private TextField txtDescFisica;
    @FXML private TextField txtNumeroChamada;
    @FXML private TextField txtChamadaLocal;
    @FXML private TextField txtTituloUniforme;
    @FXML private TextField txtIsbn;
    @FXML private TextField txtSerie;
    @FXML private TextField txtEdicao;
    @FXML private TextField txtColecao;
    @FXML private TextField txtNotasGerais;
    @FXML private TextField txtIssn;
    @FXML private TextField txtVolume;
    @FXML private TextField txtPeriodicidade;
    @FXML private TextField txtNome;
    @FXML private TextField txtTipo;

    @FXML private ComboBox<Assunto> comboAssunto;
    @FXML private ComboBox<Editora> comboEditora;
    @FXML private ComboBox<Autor> comboAutor;

    private Obra obraSelecionada;
    private ObraDAO obraDAO = new ObraDAO();

    private void carregarAssuntos() {
        String sql = "SELECT * FROM ASSUNTOS";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            comboAssunto.getItems().clear();
            while (rs.next()) {
                comboAssunto.getItems().add(new Assunto(rs.getInt("ID"), rs.getString("Assunto")));
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    private void carregarEditoras() {
        String sql = "SELECT * FROM EDITORA";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            comboEditora.getItems().clear();
            while (rs.next()) {
                comboEditora.getItems().add(new Editora(rs.getInt("ID"), rs.getString("Nome")));
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    private void carregarAutores() {
        String sql = "SELECT * FROM AUTORES";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            comboAutor.getItems().clear();
            while (rs.next()) {
                comboAutor.getItems().add(new Autor(
                        rs.getInt("ID"),
                        rs.getString("Nome"),
                        rs.getString("Nacionalidade")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setObraSelecionada(Obra obra) {
        System.out.println("Obra selecionada: " + obra.getTituloPrincipal());
        System.out.println("ID do autor da obra: " + obra.getIdAutor());
        this.obraSelecionada = obra;
        carregarAssuntos();
        carregarEditoras();
        carregarAutores();
        preencherCampos();

        if (obra.getIdAssunto() != null) {
            for (Assunto a : comboAssunto.getItems()) {
                if (a.getId() == obra.getIdAssunto()) {
                    comboAssunto.setValue(a);
                    break;
                }
            }
        }
        if (obra.getIdEditora() != null) {
            for (Editora e : comboEditora.getItems()) {
                if (e.getId() == obra.getIdEditora()) {
                    comboEditora.setValue(e);
                    break;
                }
            }
        }
        if (obra.getIdAutor() != null) {
            for (Autor b : comboAutor.getItems()) {
                if (b.getId() == obra.getIdAutor()) {
                    comboAutor.setValue(b);
                    break;
                }
            }
        }
    }

    private void preencherCampos() {
        if (obraSelecionada != null) {
            txtTituloPrincipal.setText(obraSelecionada.getTituloPrincipal());
            txtCapa.setText(obraSelecionada.getCapa());
            txtLocal.setText(obraSelecionada.getLocal());
            txtData.setText(obraSelecionada.getData());
            txtDescFisica.setText(obraSelecionada.getDescFisica());
            txtNumeroChamada.setText(obraSelecionada.getNumeroChamada());
            txtChamadaLocal.setText(obraSelecionada.getChamadaLocal());
            txtTituloUniforme.setText(obraSelecionada.getTituloUniforme());
            txtIsbn.setText(obraSelecionada.getIsbn());
            txtSerie.setText(String.valueOf(obraSelecionada.getSerie()));
            txtEdicao.setText(String.valueOf(obraSelecionada.getEdicao()));
            txtColecao.setText(obraSelecionada.getColecao());
            txtNotasGerais.setText(obraSelecionada.getNotasGerais());
            txtIssn.setText(obraSelecionada.getIssn());
            txtVolume.setText(String.valueOf(obraSelecionada.getVolume()));
            txtPeriodicidade.setText(obraSelecionada.getPeriodicidade());
            txtNome.setText(obraSelecionada.getNome());
            txtTipo.setText(obraSelecionada.getTipo());
        }
    }

    @FXML
    private void salvarAlteracoes() {
        if (obraSelecionada != null) {
            obraSelecionada.setTituloPrincipal(txtTituloPrincipal.getText());
            obraSelecionada.setCapa(txtCapa.getText());
            obraSelecionada.setLocal(txtLocal.getText());
            obraSelecionada.setData(txtData.getText());
            obraSelecionada.setDescFisica(txtDescFisica.getText());
            obraSelecionada.setNumeroChamada(txtNumeroChamada.getText());
            obraSelecionada.setChamadaLocal(txtChamadaLocal.getText());
            obraSelecionada.setTituloUniforme(txtTituloUniforme.getText());
            obraSelecionada.setIsbn(txtIsbn.getText());
            obraSelecionada.setSerie((txtSerie.getText()));
            obraSelecionada.setEdicao(Integer.parseInt(txtEdicao.getText()));
            obraSelecionada.setColecao(txtColecao.getText());
            obraSelecionada.setNotasGerais(txtNotasGerais.getText());
            obraSelecionada.setIssn(txtIssn.getText());
            obraSelecionada.setVolume(Integer.parseInt(txtVolume.getText()));
            obraSelecionada.setPeriodicidade(txtPeriodicidade.getText());
            obraSelecionada.setNome(txtNome.getText());
            obraSelecionada.setTipo(txtTipo.getText());

            Assunto assuntoSelecionado = comboAssunto.getValue();
            Editora editoraSelecionada = comboEditora.getValue();
            Autor autorSelecionado = comboAutor.getValue();

            if (assuntoSelecionado != null) obraSelecionada.setIdAssunto(assuntoSelecionado.getId());
            if (editoraSelecionada != null) obraSelecionada.setIdEditora(editoraSelecionada.getId());
            if (autorSelecionado != null) {
                obraSelecionada.setIdAutor(autorSelecionado.getId());
                obraSelecionada.setNome(autorSelecionado.getNome());
            } else {
                obraSelecionada.setIdAutor(null);
                obraSelecionada.setNome(null);
            }

            boolean sucesso = obraDAO.atualizar(obraSelecionada);
            Alert alert = new Alert(sucesso ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR,
                    sucesso ? "Obra atualizada com sucesso!" : "Erro ao atualizar obra.");
            alert.showAndWait();
        }
    }
}
