package inf.grupo.trabalhofinalrev2.dao;

import inf.grupo.trabalhofinalrev2.db.Conexao;
import inf.grupo.trabalhofinalrev2.model.ExemplarTabela;
import inf.grupo.trabalhofinalrev2.model.Obra;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ObraDAO {

    public List<Obra> buscarPorTipo(String tipo) {
        List<Obra> lista = new ArrayList<>();

        String sql = "SELECT * FROM obra WHERE Tipo = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tipo);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Obra o = mapearObra(rs);
                lista.add(o);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }


    public List<Obra> buscarRevistasFiltradas(String titulo, String data, String issn,
                                              String editora, String assunto, String periodicidade) {

        List<Obra> lista = new ArrayList<>();

        String sql = """
            SELECT  o.*,
                    e.Nome    AS editora_nome,
                    a.Assunto AS assunto_nome
            FROM obra o
            LEFT JOIN editora e   ON o.FK_Editora_ID = e.ID
            LEFT JOIN assuntos a   ON o.FK_Assunto_ID = a.ID
            WHERE o.Tipo = 'Revista'
              AND (? IS NULL OR o.Titulo_Principal LIKE ?)
              AND (? IS NULL OR o.Data LIKE ?)
              AND (? IS NULL OR o.ISSN LIKE ?)
              AND (? IS NULL OR e.Nome LIKE ?)
              AND (? IS NULL OR a.Assunto LIKE ?)
              AND (? IS NULL OR o.Periodicidade = ?)
            ORDER BY o.Titulo_Principal
        """;

        try (Connection conn = Conexao.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, titulo);
            ps.setString(2, "%" + titulo + "%");

            ps.setString(3, data);
            ps.setString(4, "%" + data + "%");

            ps.setString(5, issn);
            ps.setString(6, "%" + issn + "%");

            ps.setString(7, editora);
            ps.setString(8, "%" + editora + "%");

            ps.setString(9, assunto);
            ps.setString(10, "%" + assunto + "%");

            ps.setString(11, periodicidade);
            ps.setString(12, periodicidade);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Obra o = mapearObra(rs);
                o.setEditoraNome(rs.getString("editora_nome"));
                o.setAssuntoNome(rs.getString("assunto_nome"));
                lista.add(o);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }



    private Obra mapearObra(ResultSet rs) throws SQLException {
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
        o.setDescFisica(rs.getString("Desc_Fisica"));
        o.setCapa(rs.getString("Capa"));

        // FKs corretas conforme seu banco REAL
        o.setIdEditora(rs.getInt("FK_Editora_ID"));
        o.setIdAssunto(rs.getInt("FK_Assunto_ID"));

        return o;
    }

    public List<Obra> buscarJornaisFiltrados(String titulo, String editora) {

        List<Obra> lista = new ArrayList<>();

        String sql = """
        SELECT  o.*,
                e.Nome    AS editora_nome,
                a.Assunto AS assunto_nome
        FROM obra o
        LEFT JOIN editora e ON o.FK_Editora_ID = e.ID
        LEFT JOIN assuntos a ON o.FK_Assunto_ID = a.ID
        WHERE o.Tipo = 'Jornal'
          AND (? IS NULL OR o.Titulo_Principal LIKE ?)
          AND (? IS NULL OR e.Nome LIKE ?)
        ORDER BY o.Titulo_Principal
    """;

        try (Connection conn = Conexao.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, titulo);
            ps.setString(2, "%" + titulo + "%");

            ps.setString(3, editora);
            ps.setString(4, "%" + editora + "%");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Obra o = mapearObra(rs);
                o.setEditoraNome(rs.getString("editora_nome"));
                o.setAssuntoNome(rs.getString("assunto_nome"));
                lista.add(o);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public List<Obra> filtrarObras(String tipo, String titulo, String editora) {
        List<Obra> lista = new ArrayList<>();

        String sql = """
        SELECT  o.*,
                e.Nome    AS editora_nome,
                a.Assunto AS assunto_nome
        FROM obra o
        LEFT JOIN editora e ON o.FK_Editora_ID = e.ID
        LEFT JOIN assuntos a ON o.FK_Assunto_ID = a.ID
        WHERE o.Tipo = ?
          AND (? IS NULL OR o.Titulo_Principal LIKE ?)
          AND (? IS NULL OR e.Nome LIKE ?)
        ORDER BY o.Titulo_Principal
    """;

        try (Connection conn = Conexao.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, tipo);

            ps.setString(2, titulo);
            ps.setString(3, "%" + titulo + "%");

            ps.setString(4, editora);
            ps.setString(5, "%" + editora + "%");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Obra o = mapearObra(rs);
                o.setEditoraNome(rs.getString("editora_nome"));
                o.setAssuntoNome(rs.getString("assunto_nome"));
                lista.add(o);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public List<ExemplarTabela> buscarExemplaresNoBanco(int idObra) {
        List<ExemplarTabela> lista = new ArrayList<>();

        // A query seleciona o Número e a Disponibilidade da tabela 'Exemplar'
        // onde a chave estrangeira (FK_OBRA_ID) corresponde ao ID da obra.
        String sql = "SELECT Numero, Disponibilidade FROM Exemplar WHERE FK_OBRA_ID = ? ORDER BY Numero";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Define o ID da obra na cláusula WHERE
            stmt.setInt(1, idObra);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                // Cria um objeto ExemplarTabela para cada registro
                String numero = rs.getString("Numero");
                String disponibilidade = rs.getString("Disponibilidade");

                ExemplarTabela exemplar = new ExemplarTabela(numero, disponibilidade);
                lista.add(exemplar);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar exemplares no banco para Obra ID: " + idObra);
            e.printStackTrace();
        }

        return lista;
    }

    public int inserir(Obra obra) {
        String sql = "INSERT INTO OBRA (" +
                "Titulo_Principal, Capa, Local, Data, Desc_Fisica, Numero_Chamada, " +
                "Chamada_Local, Titulo_Uniforme, ISBN, Serie, Edicao, Colecao, " +
                "Notas_Gerais, ISSN, Volume, Periodicidade, Nome, Tipo, " +
                "FK_Assunto_ID, FK_Editora_ID, FK_Autores_ID" +
                ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, obra.getTituloPrincipal());
            stmt.setString(2, obra.getCapa());
            stmt.setString(3, obra.getLocal());
            stmt.setString(4, obra.getData());
            stmt.setString(5, obra.getDescFisica());
            stmt.setString(6, obra.getNumeroChamada());
            stmt.setString(7, obra.getChamadaLocal());
            stmt.setString(8, obra.getTituloUniforme());
            stmt.setString(9, obra.getIsbn());
            stmt.setObject(10, obra.getSerie(), Types.INTEGER);
            stmt.setObject(11, obra.getEdicao(), Types.INTEGER);
            stmt.setString(12, obra.getColecao());
            stmt.setString(13, obra.getNotasGerais());
            stmt.setString(14, obra.getIssn());
            stmt.setObject(15, obra.getVolume(), Types.INTEGER);
            stmt.setString(16, obra.getPeriodicidade());
            stmt.setString(17, obra.getNome());
            stmt.setString(18, obra.getTipo());
            stmt.setInt(19, obra.getIdAssunto());
            stmt.setInt(20, obra.getIdEditora());
            stmt.setInt(21, obra.getIdAutor());

            stmt.executeUpdate();

            // Recupera o ID gerado
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    int idGerado = rs.getInt(1);
                    obra.setId(idGerado);
                    return idGerado;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // erro
    }

}
