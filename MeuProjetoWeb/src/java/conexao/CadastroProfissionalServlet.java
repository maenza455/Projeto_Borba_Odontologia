package conexao;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException; // Importar SQLException
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/CadastroProfissionalServlet") // Mapeamento do servlet
public class CadastroProfissionalServlet extends HttpServlet
{

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String password = request.getParameter("password"); // Captura a senha


        // Validação básica dos parâmetros
        if (nome == null || nome.trim().isEmpty()
                || email == null || email.trim().isEmpty())
        {
            out.println("<h2>❌ Erro: Todos os campos são obrigatórios.</h2>");
            return;
        }

        Connection conn = null;
        PreparedStatement stmt = null;

        try
        {
            conn = ConexaoMySQL.conectar();

            if (conn != null)
            {
                // Adicione a coluna 'password' e 'data_nascimento' (ou similar) na sua tabela 'usuarios'
                String sql = "INSERT INTO ATENDIODONTO (Nome, Email, senha) VALUES (?, ?, ?)";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, nome);
                stmt.setString(2, email);
                stmt.setString(3, password); // Em um ambiente real, a senha deve ser hashed!;

                int linhasAfetadas = stmt.executeUpdate();

                if (linhasAfetadas > 0)
                {
                    out.println("<h2>✅ Cadastro realizado com sucesso!</h2>");
                    // Redirecionar para a página de login após o cadastro
                    response.setHeader("Refresh", "3;url=/MeuProjetoWeb/borba/Login/profissional/LoginPro.html");
                    out.println("<p>Você será redirecionado para a página de login em 3 segundos...</p>");
                } else
                {
                    out.println("<h2>❌ Erro ao cadastrar usuário.</h2>");
                }
            } else
            {
                out.println("<h2>❌ Não foi possível conectar ao banco de dados.</h2>");
            }
        } catch (SQLException e)
        {
            out.println("<h2>Erro de Banco de Dados: " + e.getMessage() + "</h2>");
            e.printStackTrace(); // Para depuração
        } catch (Exception e)
        {
            out.println("<h2>Erro Geral: " + e.getMessage() + "</h2>");
            e.printStackTrace(); // Para depuração
        } finally
        {
            // Fechar recursos
            try
            {
                if (stmt != null)
                {
                    stmt.close();
                }
                if (conn != null)
                {
                    conn.close();
                }
            } catch (SQLException e)
            {
                System.err.println("Erro ao fechar recursos: " + e.getMessage());
            }
        }
    }
}
