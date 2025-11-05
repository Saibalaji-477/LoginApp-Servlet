package p1;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // GET request → show login page
        response.sendRedirect("login.html");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String uname = request.getParameter("uname");
        String pwd = request.getParameter("pwd");

        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            if (conn != null) {
                String sql = "SELECT * FROM users WHERE username=? AND password=?";
                pst = conn.prepareStatement(sql);
                pst.setString(1, uname.trim());
                pst.setString(2, pwd.trim());
                rs = pst.executeQuery();

                if (rs.next()) {
                    // Successful login → forward to welcome.jsp
                    request.setAttribute("uname", uname);
                    RequestDispatcher rd = request.getRequestDispatcher("welcome.jsp");
                    rd.forward(request, response);
                } else {
                    // Failed login → redirect back to login page
                    response.sendRedirect("login.html");
                }
            } else {
                response.getWriter().println("<h3>❌ Database connection failed!</h3>");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("<h3>❌ Error: " + e.getMessage() + "</h3>");
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
            try { if (pst != null) pst.close(); } catch (SQLException e) {}
            try { if (conn != null) conn.close(); } catch (SQLException e) {}
        }
    }
}
