package inf.grupo.trabalhofinalrev2.dao;

import inf.grupo.trabalhofinalrev2.db.Conexao;
import inf.grupo.trabalhofinalrev2.model.Assunto;
import java.sql.*;

public class AssuntoDAO {

    /**
     * Verifica se um Assunto já existe no banco de dados.
     * @param nomeAssunto O nome do assunto a ser verificado.
     * @return true se o assunto já existir, false caso contrário.
     */
    public boolean existeAssunto(String nomeAssunto) throws SQLException {
        String sql = "SELECT COUNT(*) FROM ASSUNTOS WHERE Assunto = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nomeAssunto.toUpperCase()); // Verifica ignorando case, dependendo do seu DB
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    /**
     * Insere um novo Assunto no banco de dados.
     * @param assunto O objeto Assunto a ser inserido.
     * @return O ID gerado para o novo assunto.
     */
    public int inserir(Assunto assunto) throws SQLException {
        String sql = "INSERT INTO ASSUNTOS (Assunto) VALUES (?)";

        // Retorna as chaves geradas (para pegar o novo ID)
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, assunto.getAssunto());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1); // Retorna o ID
                }
            }
        }
        throw new SQLException("Erro ao obter o ID gerado para o novo assunto.");
    }
}
