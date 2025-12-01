// EditoraDAO.java (No pacote inf.grupo.trabalhofinalrev2.dao)
package inf.grupo.trabalhofinalrev2.dao;

import inf.grupo.trabalhofinalrev2.db.Conexao;
import inf.grupo.trabalhofinalrev2.model.Editora;
import java.sql.*;

public class EditoraDAO {

    /**
     * Verifica se uma Editora já existe no banco de dados.
     * @param nomeEditora O nome da editora a ser verificada.
     * @return true se a editora já existir, false caso contrário.
     */
    public boolean existeEditora(String nomeEditora) throws SQLException {
        String sql = "SELECT COUNT(*) FROM EDITORA WHERE Nome = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Usamos .toUpperCase() para uma checagem case-insensitive (depende do seu DB)
            stmt.setString(1, nomeEditora.toUpperCase());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    /**
     * Insere uma nova Editora no banco de dados.
     * @param editora O objeto Editora a ser inserido.
     * @return O ID gerado para a nova editora.
     */
    public int inserir(Editora editora) throws SQLException {
        String sql = "INSERT INTO EDITORA (Nome) VALUES (?)";

        // Retorna as chaves geradas (para pegar o novo ID)
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, editora.getNome());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1); // Retorna o ID
                }
            }
        }
        throw new SQLException("Erro ao obter o ID gerado para a nova editora.");
    }
}