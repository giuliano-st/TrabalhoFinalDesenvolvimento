package inf.grupo.trabalhofinalrev2.dao;

import inf.grupo.trabalhofinalrev2.db.Conexao;
import inf.grupo.trabalhofinalrev2.model.Obra;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ObraDAO {

    public List<Obra> buscarPorTipo(String tipo) {
        List<Obra> lista = new ArrayList<>();

        String sql = "SELECT * FROM OBRA WHERE Tipo = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tipo);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Obra o = new Obra();

                o.setId(rs.getInt("ID"));
                o.setTituloPrincipal(rs.getString("Titulo_Principal"));
                o.setLocal(rs.getString("Local"));
                o.setData(rs.getString("Data"));
                o.setTituloUniforme(rs.getString("Titulo_Uniforme"));
                o.setIsbn(rs.getString("ISBN"));
                o.setSerie(rs.getInt("Serie"));
                o.setEdicao(rs.getInt("Edicao"));
                o.setColecao(rs.getString("Colecao"));
                o.setIssn(rs.getString("ISSN"));
                o.setVolume(rs.getInt("Volume"));
                o.setPeriodicidade(rs.getString("Periodicidade"));
                o.setNome(rs.getString("Nome"));
                o.setTipo(rs.getString("Tipo"));

                lista.add(o);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
}
