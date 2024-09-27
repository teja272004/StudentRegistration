<%@ page import="java.util.List" %>
<%@ page import="com.example.model.Student" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Student List</title>
</head>
<body>

    <h2>Student List</h2>

    <%
    // Retrieve the 'students' object from the request
    Object obj = request.getAttribute("students");
    
    // Check if the attribute 'students' exists and is not null
    if (obj == null) {
    %>
        <p>No 'students' attribute found in the request. Unable to display the list.</p>
    <%
    } else {
        // Cast the object to List<Student>
        List<Student> students = (List<Student>) obj;
        
        // Check if the list is empty
        if (students.isEmpty()) {
    %>
        <p>No students available to display!</p>
    <%
        } else {
    %>
    
    <table border="1">
        <tr>
            <th>Name</th>
            <th>Registered Number</th>
            <th>Date of Birth</th>
            <th>Gender</th>
            <th>Branch</th>
            <th>Year</th>
            <th>Semester</th>
            <th>College Name</th>
        </tr>
        <%
            // Loop through each student in the list and display their information
            for (Student student : students) {
        %>
        <tr>
            <td><%= student.getName() %></td>
            <td><%= student.getRegisteredNumber() %></td>
            <td><%= student.getDob() %></td>
            <td><%= student.getGender() %></td>
            <td><%= student.getBranch() %></td>
            <td><%= student.getYear() %></td>
            <td><%= student.getSemester() %></td>
            <td><%= student.getCollegeName() %></td>
        </tr>
        <%
            } // End of for loop
        %>
    </table>
    
    <%
        } // End of empty check
    } // End of null check
    %>

</body>
</html>
