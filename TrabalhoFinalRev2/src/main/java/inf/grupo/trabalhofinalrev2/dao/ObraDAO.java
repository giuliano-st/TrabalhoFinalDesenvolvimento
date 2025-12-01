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


    /**
     * MÉTODO CORRIGIDO: Adicionando a leitura dos campos Numero_Chamada, Chamada_Local e Notas_Gerais.
     */
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

        // === CAMPOS FALTANDO ADICIONADOS AQUI ===
        o.setNumeroChamada(rs.getString("Numero_Chamada"));
        o.setChamadaLocal(rs.getString("Chamada_Local"));
        o.setNotasGerais(rs.getString("Notas_Gerais"));
        // =======================================

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

    public List<Obra> buscarJornaisFiltrados(String titulo, String data, String issn, String nomeJornal, String assunto, String periodicidade) {
        List<Obra> jornais = new ArrayList<>();

        // A QUERY CORRIGIDA para usar os nomes de coluna do seu banco (com underline) e FKs corretas
        String sql = """
            SELECT  o.*, 
                    e.Nome AS editora_nome, 
                    a.Assunto AS assunto_nome 
            FROM OBRA o 
            LEFT JOIN EDITORA e ON o.FK_Editora_ID = e.ID 
            LEFT JOIN ASSUNTOS a ON o.FK_Assunto_ID = a.ID 
            WHERE o.Tipo = 'Jornal'
              AND (? IS NULL OR o.Titulo_Principal LIKE ?) 
              AND (? IS NULL OR o.Data LIKE ?) 
              AND (? IS NULL OR o.ISSN LIKE ?) 
              AND (? IS NULL OR o.Nome LIKE ?) 
              AND (? IS NULL OR a.Assunto LIKE ?)
              AND (? IS NULL OR o.Periodicidade = ?)
            ORDER BY o.Titulo_Principal
        """;
        // OBS: Note que o filtro do nome do jornal é feito diretamente na coluna o.Nome

        // Lista para armazenar os valores dos parâmetros
        List<Object> params = new ArrayList<>();

        // Adicionar filtros dinamicamente
        // Titulo
        if (titulo != null && !titulo.trim().isEmpty()) {
            params.add(titulo.trim());
            params.add("%" + titulo.trim() + "%");
        } else {
            params.add(null);
            params.add(null);
        }
        // Data
        if (data != null && !data.trim().isEmpty()) {
            params.add(data.trim());
            params.add("%" + data.trim() + "%");
        } else {
            params.add(null);
            params.add(null);
        }
        // ISSN
        if (issn != null && !issn.trim().isEmpty()) {
            params.add(issn.trim());
            params.add("%" + issn.trim() + "%");
        } else {
            params.add(null);
            params.add(null);
        }
        // NOME (Jornal)
        if (nomeJornal != null && !nomeJornal.trim().isEmpty()) {
            params.add(nomeJornal.trim());
            params.add("%" + nomeJornal.trim() + "%");
        } else {
            params.add(null);
            params.add(null);
        }
        // ASSUNTO
        if (assunto != null && !assunto.trim().isEmpty()) {
            params.add(assunto.trim());
            params.add("%" + assunto.trim() + "%");
        } else {
            params.add(null);
            params.add(null);
        }
        // PERIODICIDADE
        if (periodicidade != null) {
            params.add(periodicidade);
            params.add(periodicidade);
        } else {
            params.add(null);
            params.add(null);
        }


        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Atribuir os parâmetros
            // Usando o mesmo padrão de IF IS NULL do método buscarRevistasFiltradas
            for (int i = 0; i < params.size(); i++) {
                // O getObject() é mais seguro para atribuir null ou String
                stmt.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    // Chama o mapearObra para preencher todos os campos (ID, Titulo_Principal, Data, ISSN, Nome, etc.)
                    Obra obra = mapearObra(rs);

                    // Adiciona os nomes extras do JOIN
                    obra.setEditoraNome(rs.getString("editora_nome"));
                    obra.setAssuntoNome(rs.getString("assunto_nome"));

                    jornais.add(obra);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar jornais filtrados: " + e.getMessage());
            e.printStackTrace();
        }
        return jornais;
    }

    public List<Obra> buscarLivrosFiltrados(String titulo, String autorNome, String editoraNome, String data, String colecao, String isbn) {
        List<Obra> lista = new ArrayList<>();

        String sql = """
        SELECT  o.*,
                e.Nome    AS editora_nome,
                a.Assunto AS assunto_nome,
                aut.Nome  AS autor_nome
        FROM obra o
        LEFT JOIN editora e   ON o.FK_Editora_ID = e.ID
        LEFT JOIN assuntos a   ON o.FK_Assunto_ID = a.ID
        LEFT JOIN autores aut ON o.FK_Autores_ID = aut.ID  -- JOIN com Tabela Autores
        WHERE o.Tipo = 'LIVRO'
          AND (? IS NULL OR o.Titulo_Principal LIKE ?)
          AND (? IS NULL OR aut.Nome LIKE ?)              -- Filtro por Nome do Autor
          AND (? IS NULL OR e.Nome LIKE ?)                -- Filtro por Nome da Editora
          AND (? IS NULL OR o.Data LIKE ?)
          AND (? IS NULL OR o.Colecao LIKE ?)             -- Filtro por Coleção
          AND (? IS NULL OR o.ISBN LIKE ?)                -- Filtro por ISBN
        ORDER BY o.Titulo_Principal
    """;

        try (Connection conn = Conexao.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Função utilitária para aplicar o filtro NULL OR LIKE
            java.util.function.BiConsumer<Integer, String> applyFilter = (index, value) -> {
                try {
                    if (value != null && !value.trim().isEmpty()) {
                        ps.setString(index, value.trim());
                        ps.setString(index + 1, "%" + value.trim() + "%");
                    } else {
                        ps.setString(index, null);
                        ps.setString(index + 1, null);
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            };

            // Aplicação dos Filtros
            applyFilter.accept(1, titulo);
            applyFilter.accept(3, autorNome);
            applyFilter.accept(5, editoraNome);
            applyFilter.accept(7, data);
            applyFilter.accept(9, colecao);
            applyFilter.accept(11, isbn);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Obra o = mapearObra(rs);
                o.setEditoraNome(rs.getString("editora_nome"));
                o.setAssuntoNome(rs.getString("assunto_nome"));

                o.setNome(rs.getString("autor_nome"));

                lista.add(o);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean removerPorId(int id) {
        String sql = "DELETE FROM obra WHERE ID = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean atualizar(Obra obra) {
        String sql = "UPDATE obra SET " +
                "Titulo_Principal = ?, Capa = ?, Local = ?, Data = ?, Desc_Fisica = ?, " +
                "Numero_Chamada = ?, Chamada_Local = ?, Titulo_Uniforme = ?, ISBN = ?, " +
                "Serie = ?, Edicao = ?, Colecao = ?, Notas_Gerais = ?, ISSN = ?, " +
                "Volume = ?, Periodicidade = ?, Nome = ?, Tipo = ?, " +
                "FK_Assunto_ID = ?, FK_Editora_ID = ?, FK_Autores_ID = ? " +
                "WHERE ID = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

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
            if (obra.getIdAutor() != null) {
                stmt.setInt(21, obra.getIdAutor());
            } else {
                stmt.setNull(21, java.sql.Types.INTEGER);
            }
            stmt.setInt(22, obra.getId());

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



}