<%@ page import="java.util.List" %>
<%@ page import="com.example.model.Student" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Student List</title>
    <style>
        /* CSS Variables for Themes */
        :root {
            --background-color: #f4f4f9;
            --text-color: #333;
            --table-bg: #ffffff;
            --table-border: #dddddd;
            --header-bg: #f2f2f2;
            --button-bg: #4CAF50;
            --button-hover: rgba(76, 175, 80, 0.8);
            --button-active: rgba(76, 175, 80, 0.6);
            --delete-button-bg: #ff4d4d;
            --edit-button-bg: #008CBA;
            --toggle-bg: #ccc;
            --toggle-circle: #fff;
        }

        /* Dark Theme Variables */
        .dark-theme {
            --background-color: #121212;
            --text-color: #f0f0f0;
            --table-bg: #1e1e1e;
            --table-border: #333333;
            --header-bg: #2c2c2c;
            --button-bg: #00aaff;
            --button-hover: #0077cc;
            --button-active: #0056b3;
            --delete-button-bg: #ff4d4d;
            --edit-button-bg: #00aaff;
            --toggle-bg: #444;
            --toggle-circle: #00aaff;
        }

        /* Global Styles */
        body {
            font-family: Arial, sans-serif;
            background-color: var(--background-color);
            color: var(--text-color);
            margin: 0;
            padding: 20px;
            animation: fadeIn 1.5s ease-in-out; /* Page fade-in animation */
            transition: background-color 0.3s, color 0.3s;
        }

        /* Navigation Bar */
        .navbar {
            display: flex;
            justify-content: flex-end;
            align-items: center;
            margin-bottom: 20px;
        }

        /* Toggle Switch Styles */
        .toggle-switch {
            position: relative;
            display: inline-block;
            width: 60px;
            height: 34px;
        }

        .toggle-switch input {
            opacity: 0;
            width: 0;
            height: 0;
        }

        .slider {
            position: absolute;
            cursor: pointer;
            top: 0; left: 0;
            right: 0; bottom: 0;
            background-color: var(--toggle-bg);
            transition: background-color 0.4s;
            border-radius: 34px;
        }

        .slider:before {
            position: absolute;
            content: "";
            height: 26px; width: 26px;
            left: 4px; bottom: 4px;
            background-color: var(--toggle-circle);
            transition: transform 0.4s, background-color 0.4s;
            border-radius: 50%;
        }

        input:checked + .slider {
            background-color: var(--button-bg);
        }

        input:checked + .slider:before {
            transform: translateX(26px);
            background-color: var(--toggle-circle);
        }

        /* Heading Styles */
        h2 {
            text-align: center;
            color: var(--text-color);
            margin-bottom: 20px;
            animation: slideDown 1.2s ease-in-out;
        }

        /* Table Styles */
        table {
            width: 100%;
            border-collapse: collapse;
            background-color: var(--table-bg);
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); /* Table shadow */
            animation: fadeInUp 1s ease-in-out;
            transition: background-color 0.3s, color 0.3s;
        }

        table, th, td {
            border: 1px solid var(--table-border);
        }

        th, td {
            padding: 12px;
            text-align: left;
            transition: background-color 0.3s, color 0.3s;
        }

        th {
            background-color: var(--header-bg);
            color: var(--text-color);
        }

        tr:hover {
            background-color: var(--header-bg); /* Row hover effect */
            color: var(--text-color);
        }

        td {
            color: var(--text-color);
        }

        /* Button Styles */
        form {
            display: inline-block;
        }

        input[type="submit"] {
            background-color: var(--button-bg);
            color: white;
            padding: 8px 16px;
            margin: 4px 2px;
            border: none;
            cursor: pointer;
            font-size: 14px;
            transition: background-color 0.3s, transform 0.3s; /* Transition effects */
            border-radius: 5px;
            box-shadow: 0 3px 6px rgba(0, 0, 0, 0.1); /* Button shadow */
        }

        input[type="submit"].delete {
            background-color: var(--delete-button-bg);
        }

        input[type="submit"].edit {
            background-color: var(--edit-button-bg);
        }

        input[type="submit"]:hover {
            background-color: var(--button-hover);
            opacity: 0.9;
            transform: scale(1.05); /* Button hover effect: scaling */
        }

        input[type="submit"]:active {
            transform: scale(0.95); /* Button click effect: shrink */
        }

        /* Animations */
        @keyframes fadeIn {
            from { opacity: 0; }
            to { opacity: 1; }
        }

        @keyframes slideDown {
            0% { transform: translateY(-50px); opacity: 0; }
            100% { transform: translateY(0); opacity: 1; }
        }

        @keyframes fadeInUp {
            0% { transform: translateY(30px); opacity: 0; }
            100% { transform: translateY(0); opacity: 1; }
        }

        /* Responsive Design */
        @media (max-width: 768px) {
            table, th, td {
                font-size: 14px;
            }

            input[type="submit"] {
                padding: 6px 12px;
                font-size: 12px;
            }

            .toggle-switch {
                width: 50px;
                height: 28px;
            }

            .slider:before {
                height: 22px;
                width: 22px;
                left: 3px;
                bottom: 3px;
            }

            input:checked + .slider:before {
                transform: translateX(22px);
            }
        }
    </style>
</head>
<body>
    <!-- Navigation Bar with Theme Toggle -->
    <div class="navbar">
        <label class="toggle-switch">
            <input type="checkbox" id="theme-toggle">
            <span class="slider"></span>
        </label>
    </div>

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

    <!-- JavaScript for Theme Toggle -->
    <script>
        const toggleSwitch = document.getElementById('theme-toggle');
        const currentTheme = localStorage.getItem('theme') ? localStorage.getItem('theme') : null;

        if (currentTheme) {
            document.body.classList.add(currentTheme);

            if (currentTheme === 'dark-theme') {
                toggleSwitch.checked = true;
            }
        }

        toggleSwitch.addEventListener('change', function() {
            if (this.checked) {
                document.body.classList.add('dark-theme');
                localStorage.setItem('theme', 'dark-theme');
            } else {
                document.body.classList.remove('dark-theme');
                localStorage.setItem('theme', 'light-theme');
            }
        });
    </script>
</body>
</html>
