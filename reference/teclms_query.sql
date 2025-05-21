-- Drop the database if it exists
DROP
DATABASE IF EXISTS teclms;

-- Create the database
CREATE
DATABASE teclms;

-- Use the database
USE
teclms;

-- Create the user table
CREATE TABLE user
(
    User_Id    VARCHAR(20)  NOT NULL,
    First_Name VARCHAR(20)  NOT NULL,
    Last_Name  VARCHAR(20)  NOT NULL,
    DOB        DATE         NOT NULL,
    Telephone  VARCHAR(15)  NOT NULL,
    Address    VARCHAR(50)  NOT NULL,
    Email      VARCHAR(70)  NOT NULL UNIQUE, -- Added UNIQUE constraint for email
    Password   VARCHAR(150) NOT NULL,
    Age        INT          NOT NULL,
    User_Image LONGBLOB,
    Role       ENUM('ADMIN', 'LECTURER', 'STUDENT', 'TECHNICAL_OFFICER') DEFAULT 'STUDENT',
    PRIMARY KEY (User_Id)
);

-- Create the admin table
CREATE TABLE admin
(
    Admin_Id VARCHAR(20) NOT NULL,
    PRIMARY KEY (Admin_Id),
    FOREIGN KEY (Admin_Id) REFERENCES user (User_Id) ON DELETE CASCADE
);

-- Create the lecturer table
CREATE TABLE lecturer
(
    Lecturer_Id     VARCHAR(20) NOT NULL,
    Department      ENUM('ICT', 'BST', 'ET') DEFAULT 'ICT',
    Enrollment_Date DATE        NOT NULL,
    Position        ENUM('PROFESSOR', 'SENIOR_LECTURER', 'LECTURER', 'ASSISTANT_LECTURER') DEFAULT 'LECTURER',
    PRIMARY KEY (Lecturer_Id),
    FOREIGN KEY (Lecturer_Id) REFERENCES user (User_Id) ON DELETE CASCADE
);

-- Create the student table
CREATE TABLE student
(
    Student_Id VARCHAR(20) NOT NULL,
    Level      VARCHAR(20) NOT NULL,
    Department ENUM('ICT', 'BST', 'ET') DEFAULT 'ICT',
    PRIMARY KEY (Student_Id),
    FOREIGN KEY (Student_Id) REFERENCES user (User_Id) ON DELETE CASCADE
);

-- Create the technical_officer table
CREATE TABLE technical_officer
(
    Technical_Id    VARCHAR(20) NOT NULL,
    Enrollment_Date DATE        NOT NULL,
    Department      ENUM('ICT', 'BST', 'ET') DEFAULT 'ICT',
    PRIMARY KEY (Technical_Id),
    FOREIGN KEY (Technical_Id) REFERENCES user (User_Id) ON DELETE CASCADE
);

-- Create the course table
CREATE TABLE course
(
    Level        ENUM('LEVEL1', 'LEVEL2', 'LEVEL3', 'LEVEL4') NOT NULL,
    Semester     ENUM('SEMESTER1', 'SEMESTER2') NOT NULL,
    Department   ENUM('ICT', 'ET', 'BST') NOT NULL,
    Course_Id    VARCHAR(10)  NOT NULL,
    Course_Name  VARCHAR(100) NOT NULL,
    Type         ENUM('THEORY', 'PRACTICAL', 'THEORY_PRACTICAL') NOT NULL,
    Lecturer_Id  VARCHAR(20)  NOT NULL, -- Changed to VARCHAR(20) to match lecturer.Lecturer_Id
    Week         VARCHAR(15)  NOT NULL,
    CreditStatus ENUM('CREDIT', 'NON_CREDIT') NOT NULL,
    Credits      INT          NOT NULL,
    P_Hours      INT          NOT NULL,
    T_Hours      INT          NOT NULL,
    PRIMARY KEY (Course_Id),
    FOREIGN KEY (Lecturer_Id) REFERENCES lecturer (Lecturer_Id) ON DELETE CASCADE
);

-- Create the attendance table
CREATE TABLE attendance
(
    AttendanceRecord_Id VARCHAR(20) NOT NULL,
    Technical_Id        VARCHAR(20) NOT NULL,
    Student_Id          VARCHAR(20) NOT NULL,
    Course_Id           VARCHAR(10) NOT NULL, -- Changed to VARCHAR(10) to match course.Course_Id
    Session_No          INT         NOT NULL,
    Status              ENUM('MC', 'PRESENT', 'ABSENT') DEFAULT 'PRESENT',
    Date                DATE        NOT NULL,
    Type                ENUM('PRACTICAL', 'THEORY') DEFAULT 'THEORY',
    PRIMARY KEY (AttendanceRecord_Id),
    FOREIGN KEY (Student_Id) REFERENCES student (Student_Id) ON DELETE CASCADE,
    FOREIGN KEY (Course_Id) REFERENCES course (Course_Id) ON DELETE CASCADE,
    FOREIGN KEY (Technical_Id) REFERENCES technical_officer (Technical_Id) ON DELETE CASCADE
);

-- Create the mark table
CREATE TABLE mark
(
    MarkRecord_Id   INT AUTO_INCREMENT PRIMARY KEY,
    Lecturer_Id     VARCHAR(20) NOT NULL,                                         -- Fixed typo: changed Lecture_Id to Lecturer_Id
    Student_Id      VARCHAR(20) NOT NULL,
    Course_Id       VARCHAR(10) NOT NULL,                                         -- Changed to VARCHAR(10) to match course.Course_Id
    Quiz_01         FLOAT DEFAULT 0,
    Quiz_02         FLOAT DEFAULT 0,
    Quiz_03         FLOAT DEFAULT 0,
    Quiz_04         FLOAT DEFAULT 0,
    Assignment_01   FLOAT DEFAULT 0,
    Assignment_02   FLOAT DEFAULT 0,
    Mid_Term        FLOAT DEFAULT 0,
    Final_Theory    FLOAT DEFAULT 0,
    Final_Practical FLOAT DEFAULT 0,
    FOREIGN KEY (Student_Id) REFERENCES student (Student_Id) ON DELETE CASCADE,
    FOREIGN KEY (Course_Id) REFERENCES course (Course_Id) ON DELETE CASCADE,
    FOREIGN KEY (Lecturer_Id) REFERENCES lecturer (Lecturer_Id) ON DELETE CASCADE -- Added foreign key constraint
);

-- Create the medical table
CREATE TABLE medical
(
    MedicalRecord_Id VARCHAR(20) NOT NULL,
    Student_Id       VARCHAR(20) NOT NULL,
    Course_Id        VARCHAR(10) NOT NULL, -- Changed to VARCHAR(10) to match course.Course_Id
    Approval_Status  ENUM('APPROVED', 'UNAPPROVED', 'PENDING') DEFAULT 'PENDING',
    Submission_Date  DATE        NOT NULL,
    Type             ENUM('PRACTICAL', 'THEORY') DEFAULT 'THEORY',
    PRIMARY KEY (MedicalRecord_Id),
    FOREIGN KEY (Student_Id) REFERENCES student (Student_Id) ON DELETE CASCADE,
    FOREIGN KEY (Course_Id) REFERENCES course (Course_Id) ON DELETE CASCADE
);

-- Create the notice table
CREATE TABLE notice
(
    Notice_Id   INT          NOT NULL AUTO_INCREMENT,
    Title       VARCHAR(20)  NOT NULL,
    Description VARCHAR(100) NOT NULL,
    Date_Posted DATE         NOT NULL,
    PRIMARY KEY (Notice_Id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Create the stu_course table
CREATE TABLE stu_course
(
    Student_Id VARCHAR(20) NOT NULL,
    Course_Id  VARCHAR(10) NOT NULL, -- Changed to VARCHAR(10) to match course.Course_Id
    PRIMARY KEY (Student_Id, Course_Id),
    FOREIGN KEY (Course_Id) REFERENCES course (Course_Id) ON DELETE CASCADE,
    FOREIGN KEY (Student_Id) REFERENCES student (Student_Id) ON DELETE CASCADE
);

-- Create the tech_officer_medical table
CREATE TABLE tech_officer_medical
(
    Technical_Id     VARCHAR(20) NOT NULL,
    MedicalRecord_Id VARCHAR(20) NOT NULL,
    PRIMARY KEY (Technical_Id, MedicalRecord_Id),
    FOREIGN KEY (MedicalRecord_Id) REFERENCES medical (MedicalRecord_Id) ON DELETE CASCADE,
    FOREIGN KEY (Technical_Id) REFERENCES technical_officer (Technical_Id) ON DELETE CASCADE
);

-- Create the time_table table
CREATE TABLE time_table
(
    TimeTable_Id INT          NOT NULL AUTO_INCREMENT,
    Level        VARCHAR(10)  NOT NULL,
    Semester     VARCHAR(20)  NOT NULL,
    Timetable    VARCHAR(512) NOT NULL,
    Date_Posted  DATE         NOT NULL,
    Department   ENUM('ICT', 'BST', 'ET') DEFAULT 'ICT',
    PRIMARY KEY (TimeTable_Id)
);

-- Create the course_materials table
CREATE TABLE course_materials
(
    Material_Id INT AUTO_INCREMENT PRIMARY KEY,
    Course_Id   VARCHAR(10),
    Lecturer_Id VARCHAR(20), -- Changed to VARCHAR(20) to match lecturer.Lecturer_Id
    Title       VARCHAR(255),
    Description TEXT,
    Link        VARCHAR(255),
    Upload_Date DATE,
    FOREIGN KEY (Course_Id) REFERENCES course (Course_Id),
    FOREIGN KEY (Lecturer_Id) REFERENCES lecturer (Lecturer_Id)
);

CREATE TABLE ca_final_marks
(
    Id         INT AUTO_INCREMENT PRIMARY KEY,
    Course_Id  VARCHAR(10) NOT NULL,
    Student_Id VARCHAR(20) NOT NULL,
    CA_Marks   DECIMAL(5, 2) DEFAULT 0,
    FULL_Marks DECIMAL(5, 2) DEFAULT 0,
    FOREIGN KEY (Course_Id) REFERENCES course (Course_Id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (Student_Id) REFERENCES student (Student_Id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT unique_student_course UNIQUE (Student_Id, Course_Id)
);
