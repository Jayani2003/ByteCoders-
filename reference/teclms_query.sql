CREATE DATABASE teclms;

USE teclms;

CREATE TABLE user (
  User_Id VARCHAR(10) NOT NULL,
  First_Name VARCHAR(20) NOT NULL,
  Last_Name VARCHAR(20) NOT NULL,
  DOB DATE NOT NULL,
  Telephone VARCHAR(15) NOT NULL,
  Address VARCHAR(50) NOT NULL,
  Email VARCHAR(70) NOT NULL,
  Password VARCHAR(150) NOT NULL,
  Age INT NOT NULL,
  User_Image LONGBLOB NOT NULL,
  Role ENUM('ADMIN', 'LECTURER', 'STUDENT', 'TECHNICAL_OFFICER') DEFAULT 'STUDENT',
  PRIMARY KEY (User_Id)
);

CREATE TABLE admin (
  Admin_Id VARCHAR(10) NOT NULL,
  PRIMARY KEY (Admin_Id),
  FOREIGN KEY (Admin_Id) REFERENCES user (User_Id)
);

CREATE TABLE lecturer (
  Lecturer_Id VARCHAR(10) NOT NULL,
  Department ENUM('ICT','BST','ET') NOT NULL,
  Enrollment_Date DATE NOT NULL,
  Position ENUM('PROFESSOR', 'SENIOR_LECTURER', 'LECTURER', 'ASSISTANT_LECTURER') DEFAULT 'LECTURER',
  PRIMARY KEY (Lecturer_Id),
  FOREIGN KEY (Lecturer_Id) REFERENCES user (User_Id)
);

CREATE TABLE student (
  Student_Id VARCHAR(10) NOT NULL,
  Level VARCHAR(20) NOT NULL,
  Department ENUM('ICT','BST','ET') NOT NULL,
  Academic_Status ENUM('I', 'II', 'III', 'IV') DEFAULT 'I',
  PRIMARY KEY (Student_Id),
  FOREIGN KEY (Student_Id) REFERENCES user (User_Id)
);

CREATE TABLE technical_officer (
  Technical_Id VARCHAR(10) NOT NULL,
  Enrollment_Date DATE NOT NULL,
  PRIMARY KEY (Technical_Id),
  FOREIGN KEY (Technical_Id) REFERENCES user (User_Id)
);

CREATE TABLE attendance (
  AttendanceRecord_Id VARCHAR(10) NOT NULL,
  Session_No INT NOT NULL,
  Medical_Status ENUM('MC','None') NOT NULL,
  Date DATE NOT NULL,
  Type ENUM('PRACTICAL','THEORY') NOT NULL,
  PRIMARY KEY (AttendanceRecord_Id)
);

CREATE TABLE course (
  Course_Id VARCHAR(10) NOT NULL,
  Course_Name VARCHAR(50) NOT NULL,
  Credit_Status ENUM('Credit','Non-Credit') NOT NULL,
  Credits INT NOT NULL,
  Type ENUM('GPA','NON_GPA') NOT NULL,
  PRIMARY KEY (Course_Id)
);

CREATE TABLE mark (
  MarkRecord_Id VARCHAR(10) NOT NULL,
  Quiz_01 FLOAT NOT NULL,
  Quiz_02 FLOAT NOT NULL,
  Quiz_03 FLOAT NOT NULL,
  Assignment FLOAT NOT NULL,
  Mid_Theory FLOAT NOT NULL,
  Mid_Practical FLOAT NOT NULL,
  Final_Theory FLOAT NOT NULL,
  Final_Practical FLOAT NOT NULL,
  Grade CHAR(4) NOT NULL,
  PRIMARY KEY (MarkRecord_Id)
);

CREATE TABLE medical (
  MedicalRecord_Id VARCHAR(10) NOT NULL,
  Approval_Status ENUM('Approved','Unapproved') NOT NULL,
  Submission_Date DATE NOT NULL,
  PRIMARY KEY (MedicalRecord_Id)
);

CREATE TABLE notice (
  Notice_Id VARCHAR(10) NOT NULL,
  Title VARCHAR(20) NOT NULL,
  Description VARCHAR(100) NOT NULL,
  Date_Posted DATE NOT NULL,
  PRIMARY KEY (Notice_Id)
);

CREATE TABLE stu_course (
  Student_Id VARCHAR(10) NOT NULL,
  Course_Id VARCHAR(10) NOT NULL,
  PRIMARY KEY (Student_Id, Course_Id),
  FOREIGN KEY (Course_Id) REFERENCES course (Course_Id),
  FOREIGN KEY (Student_Id) REFERENCES student (Student_Id)
);

CREATE TABLE tech_Officer_attendance (
  Technical_Id VARCHAR(10) NOT NULL,
  AttendanceRecord_Id VARCHAR(10) NOT NULL,
  PRIMARY KEY (Technical_Id, AttendanceRecord_Id),
  FOREIGN KEY (AttendanceRecord_Id) REFERENCES attendance (AttendanceRecord_Id),
  FOREIGN KEY (Technical_Id) REFERENCES technical_officer (Technical_Id)
);

CREATE TABLE tech_officer_medical (
  Technical_Id VARCHAR(10) NOT NULL,
  MedicalRecord_Id VARCHAR(10) NOT NULL,
  PRIMARY KEY (Technical_Id, MedicalRecord_Id),
  FOREIGN KEY (MedicalRecord_Id) REFERENCES medical (MedicalRecord_Id),
  FOREIGN KEY (Technical_Id) REFERENCES technical_officer (Technical_Id)
);

CREATE TABLE time_table (
  TimeTable_Id VARCHAR(10) NOT NULL,
  Date_Posted DATE NOT NULL,
  Department ENUM('ICT','BST','ET') NOT NULL,
  PRIMARY KEY (TimeTable_Id)
);

