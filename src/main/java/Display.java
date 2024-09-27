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

import com.example.model.Student;

@WebServlet("/Display")

public class Display extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/student_db", "root", "bhanuteja");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        String registeredNumber = req.getParameter("registeredNumber");

        if ("edit".equals(action) && registeredNumber != null) {
            // Edit: Fetch the student by registered number
            processEditRequest(registeredNumber, req, resp);
        } 
           
        if(req.getParameter("display")!=null) {
        	 processDisplayRequest(req, resp);
        }
    }

    private void processEditRequest(String registeredNumber, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Student student = null;
        
        try (Connection conn = getConnection()) {
            String sql = "SELECT * FROM students WHERE registered_number = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, registeredNumber);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        student = new Student();
                        student.setName(rs.getString("name"));
                        student.setRegisteredNumber(rs.getString("registered_number"));
                        student.setDob(rs.getDate("dob").toString());
                        student.setGender(rs.getString("gender"));
                        student.setBranch(rs.getString("branch"));
                        student.setYear(rs.getInt("yr"));
                        student.setSemester(rs.getInt("semester"));
                        student.setCollegeName(rs.getString("college_name"));
                    }
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        req.setAttribute("student", student);
        RequestDispatcher dispatcher = req.getRequestDispatcher("NewFile.jsp");
        dispatcher.forward(req, resp);

    }

    private void processDisplayRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Student> students = new ArrayList<>();
        
        try (Connection conn = getConnection()) {
            String sql = "SELECT * FROM students";
            try (PreparedStatement pstmt = conn.prepareStatement(sql);
                 ResultSet rs = pstmt.executeQuery()) {

                if (!rs.isBeforeFirst()) {
                    System.out.println("No students found in the database.");
                } else {
                    while (rs.next()) {
                        Student student = new Student();
                        student.setName(rs.getString("name"));
                        student.setRegisteredNumber(rs.getString("registered_number"));
                        student.setDob(rs.getDate("dob").toString());
                        student.setGender(rs.getString("gender"));
                        student.setBranch(rs.getString("branch"));
                        student.setYear(rs.getInt("yr"));
                        student.setSemester(rs.getInt("semester"));
                        student.setCollegeName(rs.getString("college_name"));
                        students.add(student);
                    }
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        req.setAttribute("students", students);
        RequestDispatcher dispatcher = req.getRequestDispatcher("show.jsp");
        dispatcher.include(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        String registeredNumber = req.getParameter("registeredNumber");

        if ("delete".equals(action)) {
            // Handle delete request
            processDeleteRequest(registeredNumber, req, resp);
        } else {
            // Handle update request
            processUpdateRequest(req, resp);
        }
    }

    private void processDeleteRequest(String registeredNumber, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (Connection conn = getConnection()) {
            String sql = "DELETE FROM students WHERE registered_number = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, registeredNumber);
                int deleted = pstmt.executeUpdate();

                if (deleted > 0) {
                    System.out.println("Student with registered number " + registeredNumber + " deleted successfully.");
                } else {
                    System.out.println("Failed to delete student with registered number " + registeredNumber);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        // Redirect to the student list after deletion
        resp.sendRedirect("show.jsp");
    }

    private void processUpdateRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String registeredNumber = req.getParameter("registeredNumber");
        String name = req.getParameter("name");
        String dob = req.getParameter("dob");
        String gender = req.getParameter("gender");
        String branch = req.getParameter("branch");
        int year = Integer.parseInt(req.getParameter("year"));
        int semester = Integer.parseInt(req.getParameter("semester"));
        String collegeName = req.getParameter("college_name");

        try (Connection conn = getConnection()) {
            String sql = "UPDATE students SET name = ?, dob = ?, gender = ?, branch = ?, yr = ?, semester = ?, college_name = ? WHERE registered_number = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, name);
                pstmt.setString(2, dob);
                pstmt.setString(3, gender);
                pstmt.setString(4, branch);
                pstmt.setInt(5, year);
                pstmt.setInt(6, semester);
                pstmt.setString(7, collegeName);
                pstmt.setString(8, registeredNumber);
                int updated = pstmt.executeUpdate();

                if (updated > 0) {
                    System.out.println("Student with registered number " + registeredNumber + " updated successfully.");
                } else {
                    System.out.println("Failed to update student with registered number " + registeredNumber);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        // Redirect to the student list after update
        resp.sendRedirect("welcome.html");
    }
}