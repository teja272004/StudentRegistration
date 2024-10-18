CREATE DATABASE student_db;
USE student_db;
drop table students;
CREATE TABLE students (
    
    name VARCHAR(100),
    registered_number VARCHAR(50) primary key,
    dob DATE,
    gender VARCHAR(10),
    branch VARCHAR(50),
    yr INT,
    semester INT,
    college_name VARCHAR(100)
);
select * from students;

