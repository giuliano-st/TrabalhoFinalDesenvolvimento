package inf.grupo.trabalhofinalrev2.dao;

import inf.grupo.trabalhofinalrev2.model.Livro;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LivroDAO {
    private Connection conn;

    public LivroDAO(Connection conn) {
        this.conn = conn;
    }

    public void salvar(Livro livro) throws SQLException {
        String sql = "INSERT INTO livro (titulo, autor, isbn) VALUES (?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, livro.getTitulo());
        stmt.setString(2, livro.getAutor());
        stmt.setString(3, livro.getIsbn());
        stmt.executeUpdate();
    }
}
