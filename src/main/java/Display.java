import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.*;

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

    // Initialize Logger
    private static final Logger logger = Logger.getLogger(Display.class.getName());

  //  @Override
  //  public void init() throws ServletException {
    //    super.init();
    //  //  try {
    //        // Configure the logger with handler and formatter
    //        Handler fileHandler = new FileHandler("DisplayServlet.log", true);
    //        fileHandler.setFormatter(new SimpleFormatter());
    //        logger.addHandler(fileHandler);
    //        logger.setLevel(Level.INFO);
    //        logger.setUseParentHandlers(false); // Prevents logging to console
    //        logger.info("Display Servlet initialized.");
    //    } catch (IOException e) {
    //        throw new ServletException("Failed to initialize logger for Display servlet.", e);
    //    }
    //}
//
    private Connection getConnection() throws ClassNotFoundException, SQLException {
        logger.info("Attempting to establish database connection.");
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/student_db", "root", "bhanuteja");
        logger.info("Database connection established successfully.");
        return conn;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Received GET request.");
        String action = req.getParameter("action");
        String registeredNumber = req.getParameter("registeredNumber");
        String displayParam = req.getParameter("display");

        logger.info("Request Parameters - Action: " + action + ", Registered Number: " + registeredNumber + ", Display: " + displayParam);

        if ("edit".equalsIgnoreCase(action) && registeredNumber != null) {
            logger.info("Processing edit request for Registered Number: " + registeredNumber);
            processEditRequest(registeredNumber, req, resp);
        }

        if ("yes".equalsIgnoreCase(displayParam)) {
            logger.info("Processing display request.");
            processDisplayRequest(req, resp);
        }
    }

    private void processEditRequest(String registeredNumber, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Starting processEditRequest for Registered Number: " + registeredNumber);
        Student student = null;

        try (Connection conn = getConnection()) {
            String sql = "SELECT * FROM students WHERE registered_number = ?";
            logger.info("Preparing SQL query: " + sql);
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, registeredNumber);
                logger.info("Executing query with Registered Number: " + registeredNumber);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        logger.info("Student found with Registered Number: " + registeredNumber);
                        student = new Student();
                        student.setName(rs.getString("name"));
                        student.setRegisteredNumber(rs.getString("registered_number"));
                        student.setDob(rs.getDate("dob").toString());
                        student.setGender(rs.getString("gender"));
                        student.setBranch(rs.getString("branch"));
                        student.setYear(rs.getInt("yr"));
                        student.setSemester(rs.getInt("semester"));
                        student.setCollegeName(rs.getString("college_name"));
                    } else {
                        logger.warning("No student found with Registered Number: " + registeredNumber);
                    }
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            logger.log(Level.SEVERE, "Error in processEditRequest for Registered Number: " + registeredNumber, e);
            throw new ServletException("Database error during edit request.", e);
        }

        req.setAttribute("student", student);
        logger.info("Forwarding to NewFile.jsp with student data.");
        RequestDispatcher dispatcher = req.getRequestDispatcher("NewFile.jsp");
        dispatcher.forward(req, resp);
    }

    private void processDisplayRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Starting processDisplayRequest.");
        List<Student> students = new ArrayList<>();

        try (Connection conn = getConnection()) {
            String sql = "SELECT * FROM students";
            logger.info("Preparing SQL query: " + sql);
            try (PreparedStatement pstmt = conn.prepareStatement(sql);
                 ResultSet rs = pstmt.executeQuery()) {

                if (!rs.isBeforeFirst()) {
                    logger.info("No students found in the database.");
                } else {
                    logger.info("Students found. Fetching data...");
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
                        logger.fine("Added student: " + student.getRegisteredNumber());
                    }
                    logger.info("Total students fetched: " + students.size());
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            logger.log(Level.SEVERE, "Error in processDisplayRequest.", e);
            throw new ServletException("Database error during display request.", e);
        }

        req.setAttribute("students", students);
        logger.info("Forwarding to show.jsp with students data.");
        RequestDispatcher dispatcher = req.getRequestDispatcher("show.jsp");
        dispatcher.include(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Received POST request.");
        String action = req.getParameter("action");
        String registeredNumber = req.getParameter("registeredNumber");

        logger.info("Request Parameters - Action: " + action + ", Registered Number: " + registeredNumber);

        if ("delete".equalsIgnoreCase(action)) {
            logger.info("Processing delete request for Registered Number: " + registeredNumber);
            processDeleteRequest(registeredNumber, req, resp);
        } else {
            logger.info("Processing update request.");
            processUpdateRequest(req, resp);
        }
    }

    private void processDeleteRequest(String registeredNumber, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("Starting processDeleteRequest for Registered Number: " + registeredNumber);

        try (Connection conn = getConnection()) {
            String sql = "DELETE FROM students WHERE registered_number = ?";
            logger.info("Preparing SQL query: " + sql);
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, registeredNumber);
                logger.info("Executing delete for Registered Number: " + registeredNumber);
                int deleted = pstmt.executeUpdate();

                if (deleted > 0) {
                    logger.info("Student with Registered Number " + registeredNumber + " deleted successfully.");
                } else {
                    logger.warning("Failed to delete student with Registered Number " + registeredNumber + ".");
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            logger.log(Level.SEVERE, "Error in processDeleteRequest for Registered Number: " + registeredNumber, e);
            throw new IOException("Database error during delete request.", e);
        }

        // Redirect to the student list after deletion
        logger.info("Redirecting to show.jsp after deletion.");
        resp.sendRedirect("show.jsp");
    }

    private void processUpdateRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("Starting processUpdateRequest.");

        String registeredNumber = req.getParameter("registeredNumber");
        String name = req.getParameter("name");
        String dob = req.getParameter("dob");
        String gender = req.getParameter("gender");
        String branch = req.getParameter("branch");
        String yearStr = req.getParameter("year");
        String semesterStr = req.getParameter("semester");
        String collegeName = req.getParameter("college_name");

        logger.info("Updating student - Registered Number: " + registeredNumber + ", Name: " + name + ", DOB: " + dob + 
                    ", Gender: " + gender + ", Branch: " + branch + ", Year: " + yearStr + 
                    ", Semester: " + semesterStr + ", College Name: " + collegeName);

        // Validate and parse year and semester
        int year = 0;
        int semester = 0;
        try {
            year = Integer.parseInt(yearStr);
            semester = Integer.parseInt(semesterStr);
            logger.info("Parsed Year: " + year + ", Semester: " + semester);
        } catch (NumberFormatException e) {
            logger.log(Level.WARNING, "Invalid number format for Year or Semester.", e);
            // Handle the error appropriately, possibly by setting an error message in the request
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid input for Year or Semester.");
            return;
        }

        try (Connection conn = getConnection()) {
            String sql = "UPDATE students SET name = ?, dob = ?, gender = ?, branch = ?, yr = ?, semester = ?, college_name = ? WHERE registered_number = ?";
            logger.info("Preparing SQL query: " + sql);
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, name);
                pstmt.setString(2, dob);
                pstmt.setString(3, gender);
                pstmt.setString(4, branch);
                pstmt.setInt(5, year);
                pstmt.setInt(6, semester);
                pstmt.setString(7, collegeName);
                pstmt.setString(8, registeredNumber);

                logger.info("Executing update for Registered Number: " + registeredNumber);
                int updated = pstmt.executeUpdate();

                if (updated > 0) {
                    logger.info("Student with Registered Number " + registeredNumber + " updated successfully.");
                } else {
                    logger.warning("Failed to update student with Registered Number " + registeredNumber + ".");
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            logger.log(Level.SEVERE, "Error in processUpdateRequest for Registered Number: " + registeredNumber, e);
            throw new IOException("Database error during update request.", e);
        }

        // Redirect to the welcome page after update
        logger.info("Redirecting to welcome.html after update.");
        resp.sendRedirect("welcome.html");
    }

    @Override
    public void destroy() {
        super.destroy();
        logger.info("Display Servlet destroyed.");
        // Close all handlers to release resources
        for (Handler handler : logger.getHandlers()) {
            handler.close();
        }
    }
}
