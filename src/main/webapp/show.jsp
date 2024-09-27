<%@ page import="java.util.List" %>
<%@ page import="com.example.model.Student" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
    <title>Student List</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f9;
            margin: 0;
            padding: 20px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
        }

        table, th, td {
            border: 1px solid black;
        }

        th, td {
            padding: 10px;
            text-align: left;
        }

        th {
            background-color: #f2f2f2;
        }

        form {
            display: inline-block;
        }

        input[type="submit"] {
            background-color: #4CAF50;
            color: white;
            padding: 8px 16px;
            margin: 4px 2px;
            border: none;
            cursor: pointer;
            font-size: 14px;
        }

        input[type="submit"].delete {
            background-color: #ff4d4d;
        }

        input[type="submit"].edit {
            background-color: #008CBA;
        }

        input[type="submit"]:hover {
            opacity: 0.8;
        }
    </style>
</head>
<body>
    <h2>Student List</h2>



    <%
    
    List<Student> students = (List<Student>) request.getAttribute("students");


    if (students == null || students.isEmpty()) {
    %>
        <p>No students available to display!</p>
    <%
    } else {
    %>

    <table>
        <tr>
            <th>Name</th>
            <th>Registered Number</th>
            <th>Date of Birth</th>
            <th>Gender</th>
            <th>Branch</th>
            <th>Year</th>
            <th>Semester</th>
            <th>College Name</th>
            <th>Actions</th>
        </tr>
        <%
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
            <td>
                <!-- Edit button -->
                <form action="Display" method="GET">
                    <input type="hidden" name="action" value="edit" />
                    <input type="hidden" name="registeredNumber" value="<%= student.getRegisteredNumber() %>" />
                    <input type="submit" class="edit" value="Edit" name="edit" />
                </form>

                <!-- Delete button -->
                <form action="Display" method="POST" onsubmit="return confirm('Are you sure you want to delete this student?');">
                    <input type="hidden" name="action" value="delete" />
                    <input type="hidden" name="registeredNumber" value="<%= student.getRegisteredNumber() %>" />
                    <input type="submit" class="delete" value="Delete" />
                </form>
            </td>
        </tr>
        <%
            }
        %>
    </table>

    <%
    }
    %>

</body>
</html>
