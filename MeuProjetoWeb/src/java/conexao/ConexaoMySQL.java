package conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoMySQL
{

    // URL correta para JDBC MySQL
    private static final String URL = "jdbc:mysql://160.20.22.99:3360/fasiclin";
    private static final String USUARIO = "aluno45";
    private static final String SENHA = "cBd28+LQYz0=";

    public static Connection conectar()
    {
        try
        {
            // Carrega o driver JDBC do MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Estabelece a conexão
            return DriverManager.getConnection(URL, USUARIO, SENHA);
        } catch (ClassNotFoundException e)
        {
            System.err.println("Erro: Driver JDBC do MySQL não encontrado. Certifique-se de que o JAR está no classpath.");
            e.printStackTrace();
            return null;
        } catch (SQLException e)
        {
            System.err.println("Erro de conexão com o banco de dados: " + e.getMessage());
            e.printStackTrace();
            return null;
        } catch (Exception e)
        {
            System.err.println("Erro inesperado ao conectar: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
