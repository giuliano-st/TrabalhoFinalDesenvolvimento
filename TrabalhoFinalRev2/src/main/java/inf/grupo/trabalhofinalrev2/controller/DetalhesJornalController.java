// Salvar como DetalhesJornalController.java
package inf.grupo.trabalhofinalrev2.controller;

import inf.grupo.trabalhofinalrev2.dao.ObraDAO;
import inf.grupo.trabalhofinalrev2.model.ExemplarTabela;
import inf.grupo.trabalhofinalrev2.model.Obra;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.util.List;

public class DetalhesJornalController { // ALTERADO: Nome da classe

    @FXML private TextField titulo;
    @FXML private TextField data;
    @FXML private TextField issn;
    @FXML private TextField nomeJornal; // ALTERADO: Era 'editora'
    @FXML private TextField assunto;
    @FXML private TextField periodicidade;
    @FXML private TextField Id;
    // REMOVIDO: @FXML private TextField edicao;
    @FXML private TextField localizacao;
    @FXML private TextField descricao;
    private String caminhoCapa;
    private int idObra;

    public void exibirDetalhes(Obra obra) {
        titulo.setText(obra.getTituloPrincipal());
        data.setText(obra.getData());
        issn.setText(obra.getIssn());
        nomeJornal.setText(obra.getNome()); // ALTERADO: Usa obra.getNome()
        assunto.setText(obra.getAssuntoNome());
        periodicidade.setText(obra.getPeriodicidade());
        Id.setText(""+obra.getId());
        // REMOVIDO: edicao.setText(""+obra.getEdicao());
        localizacao.setText(obra.getLocal());
        descricao.setText(obra.getDescFisica());
        caminhoCapa = obra.getCapa();
        idObra = obra.getId();
    }

    // Para a exibição da imagem

    @FXML private Button btn_Capa;

    @FXML
    public void handleVisualizarCapa(ActionEvent event) {
        if (caminhoCapa == null || caminhoCapa.isEmpty()) {
            System.out.println("Caminho da capa não registrado ou vazio.");
            return;
        }

        try {
            // 1. Carregar o FXML e o Controller
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/inf/grupo/trabalhofinalrev2/view/CapaVisualizacao.fxml"));
            Parent root = loader.load();

            // 2. Obter o Controller da nova janela
            CapaVisualizacaoController capaController = loader.getController();

            // 3. Passar os dados (caminho da capa)
            capaController.setCapaImage(caminhoCapa);

            // 4. Configurar e exibir a nova janela (Stage)
            Stage capaStage = new Stage();
            capaStage.setTitle("Capa da Obra: " + titulo.getText());
            capaStage.setScene(new Scene(root));
            capaStage.show();

        } catch (Exception e) {
            System.err.println("Erro ao carregar a janela de visualização da capa.");
            e.printStackTrace();
        }
    }

    // para disponibilidade
    private ObraDAO obraDAO = new ObraDAO();
    @FXML
    public void handleVerificarDisponibilidade(ActionEvent event) {
        if (idObra <= 0) {
            System.err.println("ID da Obra não encontrado ou inválido. Não é possível verificar a disponibilidade.");
            return;
        }

        try {
            // 1. CHAMA O Metodo
            List<ExemplarTabela> dadosExemplares = obraDAO.buscarExemplaresNoBanco(idObra);

            // 2. Carregar o FXML e o Controller da nova janela
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/inf/grupo/trabalhofinalrev2/view/DisponibilidadeView.fxml"));
            Parent root = loader.load();

            // 3. Obter o Controller e passar os dados
            DisponibilidadeController dispController = loader.getController();
            dispController.setExemplares(dadosExemplares);

            // 4. Configurar e exibir a nova janela
            Stage dispStage = new Stage();
            dispStage.setTitle("Disponibilidade - ID " + idObra);
            dispStage.setScene(new Scene(root));
            dispStage.show();

        } catch (Exception e) {
            System.err.println("Erro ao abrir a janela de disponibilidade.");
            e.printStackTrace();
        }
    }

}