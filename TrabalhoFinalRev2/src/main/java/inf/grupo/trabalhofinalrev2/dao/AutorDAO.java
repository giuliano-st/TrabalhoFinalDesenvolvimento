// AutorDAO.java (No pacote inf.grupo.trabalhofinalrev2.dao)
package inf.grupo.trabalhofinalrev2.dao;

import inf.grupo.trabalhofinalrev2.db.Conexao;
import inf.grupo.trabalhofinalrev2.model.Autor;
import java.sql.*;

public class AutorDAO {

    /**
     * Verifica se um Autor (pelo nome) já existe no banco de dados.
     * @param nomeAutor O nome do autor a ser verificado.
     * @return true se o autor já existir, false caso contrário.
     */
    public boolean existeAutor(String nomeAutor) throws SQLException {
        // Assume-se que o Nome é suficiente para a verificação de duplicidade
        String sql = "SELECT COUNT(*) FROM AUTORES WHERE Nome = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Verifica ignorando case (dependendo da configuração do seu DB)
            stmt.setString(1, nomeAutor);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    /**
     * Insere um novo Autor no banco de dados, incluindo a Nacionalidade.
     * @param autor O objeto Autor a ser inserido.
     * @return O ID gerado para o novo autor.
     */
    public int inserir(Autor autor) throws SQLException {
        String sql = "INSERT INTO AUTORES (Nome, Nacionalidade) VALUES (?, ?)";

        // Retorna as chaves geradas (para pegar o novo ID)
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, autor.getNome());
            // Garante que a nacionalidade seja salva em maiúsculas (padrão de códigos)
            stmt.setString(2, autor.getNacionalidade().toUpperCase());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1); // Retorna o ID
                }
            }
        }
        throw new SQLException("Erro ao obter o ID gerado para o novo autor.");
    }
}