package inf.grupo.trabalhofinalrev2.controller;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File;
import java.net.MalformedURLException;

public class CapaVisualizacaoController {

    @FXML private ImageView capaView;
    public void setCapaImage(String caminhoCapa) {
        if (caminhoCapa == null || caminhoCapa.isEmpty()) {
            System.err.println("Caminho da capa não fornecido.");
            return;
        }

        try {
            // Conversão do caminho do sistema para URL
            File file = new File(caminhoCapa);
            String imageUrl = file.toURI().toURL().toExternalForm();

            Image capaImage = new Image(imageUrl);
            capaView.setImage(capaImage);

        } catch (MalformedURLException e) {
            System.err.println("Erro: O caminho do arquivo é inválido: " + caminhoCapa);
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Erro ao carregar a imagem: " + e.getMessage());
            e.printStackTrace();
        }
    }
}