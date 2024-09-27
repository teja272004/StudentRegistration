import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/register")
public class register extends HttpServlet {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
   // private static final String JDBC_URL = "jdbc:mysql://localhost:3306/student_db";
    //private static final String JDBC_USER = "root"; // Your JDBC username
    //private static final String JDBC_PASSWORD = ""; // Your JDBC password

    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection cnt = DriverManager.getConnection("jdbc:mysql://localhost:3306/student_db", "root", "bhanuteja");
        return cnt;

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Retrieve form parameters
        // String id = req.getParameter("id");
        String name = req.getParameter("name");
        String registeredNumber = req.getParameter("registered-number");
        String dob = req.getParameter("dob");
        String gender = req.getParameter("gender");
        String branch = req.getParameter("branch");
        String year = req.getParameter("year");
        String semester = req.getParameter("semester");
        String collegeName = req.getParameter("college-name");

        // Database connection and insertion logic
        try (Connection conn = getConnection()) {
            String sql = "INSERT INTO students VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                // pstmt.setInt(1, Integer.parseInt(id)); // Set ID
                pstmt.setString(1, name);
                pstmt.setString(2, registeredNumber);
                pstmt.setDate(3, java.sql.Date.valueOf(dob));
                pstmt.setString(4, gender);
                pstmt.setString(5, branch);
                pstmt.setInt(6, Integer.parseInt(year));
                pstmt.setInt(7, Integer.parseInt(semester));
                pstmt.setString(8, collegeName);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        // Prepare response
        resp.setContentType("text/html");
        try (PrintWriter out = resp.getWriter()) {
            out.println("<html><body>");
            out.println("<h2>Registration Successful</h2>");
            out.println("<p>Thank you for registering, " + name + "!</p>");
            out.println("</body></html>");
        }
    }
    
   
}

