<%@ page import="java.util.List" %>
<%@ page import="com.example.model.Student" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Update Student</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: linear-gradient(135deg, #6a82fb, #fc5c7d);
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            animation: background-animation 15s ease infinite;
        }

        @keyframes background-animation {
            0% { background-position: 0% 50%; }
            50% { background-position: 100% 50%; }
            100% { background-position: 0% 50%; }
        }

        h2 {
            text-align: center;
            color: #fff;
            text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.6);
            margin-bottom: 20px;
        }

        form {
            background: rgba(255, 255, 255, 0.9);
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 4px 20px rgba(0, 0, 0, 0.2);
            width: 400px;
            transition: transform 0.2s;
        }

        form:hover {
            transform: scale(1.02);
        }

        table {
            width: 100%;
            margin-bottom: 20px;
        }

        table td {
            padding: 10px;
            vertical-align: middle;
        }

        table td:first-child {
            text-align: right;
            font-weight: bold;
            color: #333;
        }

        table input[type="text"],
        table input[type="date"],
        table input[type="number"],
        table select {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 5px;
            box-sizing: border-box;
            transition: border-color 0.3s;
        }

        table input[type="text"]:focus,
        table input[type="date"]:focus,
        table input[type="number"]:focus,
        table select:focus {
            border-color: #6a82fb;
            outline: none;
            box-shadow: 0 0 5px rgba(106, 130, 251, 0.5);
        }

        input[type="submit"] {
            width: 100%;
            padding: 10px;
            background: linear-gradient(135deg, #6a82fb, #fc5c7d);
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
            transition: background 0.3s, transform 0.2s;
            font-weight: bold;
        }

        input[type="submit"]:hover {
            background: linear-gradient(135deg, #fc5c7d, #6a82fb);
            transform: scale(1.05);
        }

        p {
            text-align: center;
            color: #ff0000;
            font-weight: bold;
        }
    </style>
</head>
<body>
    <h2>Update Student Details</h2>

    <c:if test="${not empty student}">
        <form action="Display" method="post">
            <!-- Hidden field to store the registered number as it should not be editable -->
            <input type="hidden" name="registeredNumber" value="${student.registeredNumber}" />

            <table>
                <tr>
                    <td>Name:</td>
                    <td><input type="text" name="name" value="${student.name}" /></td>
                </tr>
                <tr>
                    <td>Date of Birth:</td>
                    <td><input type="date" name="dob" value="${student.dob}" /></td>
                </tr>
                <tr>
                    <td>Gender:</td>
                    <td>
                        <select name="gender">
                            <option value="Male" ${student.gender == 'Male' ? 'selected' : ''}>Male</option>
                            <option value="Female" ${student.gender == 'Female' ? 'selected' : ''}>Female</option>
                            <option value="Other" ${student.gender == 'Other' ? 'selected' : ''}>Other</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>Branch:</td>
                    <td><input type="text" name="branch" value="${student.branch}" /></td>
                </tr>
                <tr>
                    <td>Year:</td>
                    <td><input type="number" name="year" value="${student.year}" /></td>
                </tr>
                <tr>
                    <td>Semester:</td>
                    <td><input type="number" name="semester" value="${student.semester}" /></td>
                </tr>
                <tr>
                    <td>College Name:</td>
                    <td><input type="text" name="college_name" value="${student.collegeName}" /></td>
                </tr>
            </table>

            <br />
            <input type="submit" value="Update Student" />
        </form>
    </c:if>

    <c:if test="${empty student}">
        <p>No student found to update.</p>
    </c:if>
</body>
</html>
