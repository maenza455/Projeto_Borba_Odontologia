package conexao;

import java.sql.Connection;

public class TesteConexaoLocal
{

    public static void main(String[] args)
    {
        Connection conn = ConexaoMySQL.conectar();

        if (conn != null)
        {
            System.out.println("✅ Conectado com sucesso ao banco de dados!");
        } else
        {
            System.out.println("❌ Falha na conexão.");
        }
    }
}
