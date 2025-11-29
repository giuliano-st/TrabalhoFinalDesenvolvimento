package inf.grupo.trabalhofinalrev2.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    // ⚠️ 1. URL de Conexão para SQL Server
    // host: localhost, porta: 1433, banco: museu
    // encrypt=true e trustServerCertificate=true são necessários para o driver moderno.
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=museu;encrypt=true;trustServerCertificate=true";

    // ⚠️ 2. Credenciais (Ajuste-as de acordo com seu servidor)
    private static final String USER = "admin"; // Ex: "sa", "userapp", etc.
    private static final String PASSWORD = "123"; // Senha do usuário acima

    /**
     * Estabelece e retorna a conexão com o banco de dados SQL Server.
     * @return Uma instância de Connection.
     * @throws SQLException Se ocorrer um erro de conexão.
     */
    public static Connection getConnection() throws SQLException {
        try {
            // DriverManager encontrará o driver mssql-jdbc automaticamente,
            // desde que esteja no classpath (via pom.xml).
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexão com o SQL Server (Banco 'museu') estabelecida com sucesso!");
            return connection;
        } catch (SQLException e) {
            System.err.println("❌ Erro ao conectar com o SQL Server. Verifique o servidor, credenciais e URL.");
            e.printStackTrace();
            throw e; // Lança a exceção para que a camada superior possa tratar a falha
        }
    }

    /**
     * Fecha a conexão com o banco de dados de forma segura.
     * @param connection A conexão a ser fechada.
     */
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Conexão fechada.");
            } catch (SQLException e) {
                System.err.println("Erro ao fechar a conexão.");
                e.printStackTrace();
            }
        }
    }
}