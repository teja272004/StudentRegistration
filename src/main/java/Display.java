import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Logger;
import com.example.model.Student;

@WebServlet("/display-students")
public class Display extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/student_db", "root", "bhanuteja");
    }
    private static final Logger LOGGER = Logger.getLogger(Display.class.getName());
   
    protected void doGet1(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.info("Fetching students from database...");
        
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Student> students = new ArrayList<>();
        
        // Database fetching logic
        try (Connection conn = getConnection()) {
            String sql = "SELECT * FROM students";
            try (PreparedStatement pstmt = conn.prepareStatement(sql);
                 ResultSet rs = pstmt.executeQuery()) {

                if (!rs.isBeforeFirst()) {
                    System.out.println("No students found in the database.");
                } else {
                    // Fetching the data and populating the list
                    while (rs.next()) {
                        Student student = new Student();
                        student.setName(rs.getString("name"));
                        student.setRegisteredNumber(rs.getString("registered_number"));
                        student.setDob(rs.getDate("dob").toString()); // Assuming DOB is not null
                        student.setGender(rs.getString("gender"));
                        student.setBranch(rs.getString("branch"));
                        student.setYear(rs.getInt("yr"));
                        student.setSemester(rs.getInt("semester"));
                        student.setCollegeName(rs.getString("college_name"));
                        students.add(student);
                    }
                    System.out.println("Total students fetched: " + students.size());
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        // Set the students list in the request and forward to JSP
        req.setAttribute("students", students);
        RequestDispatcher dispatcher = req.getRequestDispatcher("show.jsp");
        dispatcher.forward(req, resp);
    }
}
