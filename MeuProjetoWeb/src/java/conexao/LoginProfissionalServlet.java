package conexao;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/LoginProfissionalServlet")
public class LoginProfissionalServlet extends HttpServlet
{

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String idProfissional = request.getParameter("ID_PROFISSIO");
        String logUsuario = request.getParameter("email"); // campo input
        String senha = request.getParameter("password");

        if (idProfissional == null || logUsuario == null || senha == null
                || idProfissional.trim().isEmpty() || logUsuario.trim().isEmpty() || senha.trim().isEmpty())
        {
            out.println("<h2>❌ Todos os campos são obrigatórios.</h2>");
            return;
        }

        try (Connection conn = ConexaoMySQL.conectar())
        {
            if (conn != null)
            {
                String sql = "SELECT * FROM USUARIO WHERE ID_PROFISSIO = ? AND LOGUSUARIO = ? AND SENHAUSUA = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, Integer.parseInt(idProfissional));
                stmt.setString(2, logUsuario);
                stmt.setString(3, senha); // Em produção, use hash!

                ResultSet rs = stmt.executeQuery();
                if (rs.next())
                {
                    out.println("<h2>✅ Login realizado com sucesso!</h2>");
                    response.setHeader("Refresh", "3;url=/MeuProjetoWeb/borba/Login/entrandoNoSite/entrou.html");
                    out.println("<p>Você será redirecionado em 3 segundos...</p>");
                } else
                {
                    response.setHeader("Refresh", "3;url=/MeuProjetoWeb/borba/Login/cliente/LoginPro.html");
                    out.println("<h2>❌ ID, usuário ou senha inválidos.</h2>");
                }
                rs.close();
                stmt.close();
            } else
            {
                out.println("<h2>❌ Erro ao conectar com o banco de dados.</h2>");
            }
        } catch (SQLException | NumberFormatException e)
        {
            out.println("<h2>Erro: " + e.getMessage() + "</h2>");
            e.printStackTrace();
        }
    }
}
