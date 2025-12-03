package inf.grupo.trabalhofinalrev2.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    // host: localhost, porta: 1433, banco: museu
    private static final String URL = "jdbc:mysql://localhost:3306/museu?useSSL=false&serverTimezone=UTC";

    private static final String USER = "root";
    private static final String PASSWORD = "mysql";

    public static Connection getConnection() throws SQLException {
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexão com o SQL Server (Banco 'museu') estabelecida com sucesso!");
            return connection;
        } catch (SQLException e) {
            System.err.println("Erro ao conectar com o SQL Server. Verifique o servidor, credenciais e URL.");
            e.printStackTrace();
            throw e;
        }
    }

}