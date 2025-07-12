package conexao;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/LoginClienteServlet")
public class LoginClienteServlet extends HttpServlet
{

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String ID_PESSOAFIS = request.getParameter("ID_PESSOAFIS");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (ID_PESSOAFIS == null || ID_PESSOAFIS.trim().isEmpty()
                || email == null || email.trim().isEmpty()
                || password == null || password.trim().isEmpty())
        {
            out.println("<h2>❌ Erro: Todos os campos são obrigatórios.</h2>");
            return;
        }

        try (Connection conn = ConexaoMySQL.conectar();
                PreparedStatement stmt = conn.prepareStatement(
                        "SELECT * FROM USUARIO WHERE ID_PESSOAFIS = ? AND LOGUSUARIO = ? AND SENHAUSUA = ?"))
        {
            stmt.setInt(1, Integer.parseInt(ID_PESSOAFIS));
            stmt.setString(2, email);
            stmt.setString(3, password);

            try (ResultSet rs = stmt.executeQuery())
            {
                if (rs.next())
                {
                    out.println("<h2>✅ Login realizado com sucesso!</h2>");
                    response.setHeader("Refresh", "3;url=/MeuProjetoWeb/borba/Login/entrandoNoSite/entrou.html");
                    out.println("<p>Você será redirecionado em 3 segundos...</p>");
                } else
                {
                    response.setHeader("Refresh", "3;url=/MeuProjetoWeb/borba/Login/cliente/LoginCli.html");
                    out.println("<h2>❌ ID, email ou senha inválidos.</h2>");
                }
            }
        } catch (SQLException e)
        {
            out.println("<h2>Erro de Banco de Dados: " + e.getMessage() + "</h2>");
            e.printStackTrace();
        } catch (Exception e)
        {
            out.println("<h2>Erro Geral: " + e.getMessage() + "</h2>");
            e.printStackTrace();
        }
    }
}
