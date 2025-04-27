DROP DATABASE teclms;

CREATE
DATABASE teclms;

USE teclms;

CREATE TABLE user
(
    User_Id    VARCHAR(20)  NOT NULL,
    First_Name VARCHAR(20)  NOT NULL,
    Last_Name  VARCHAR(20)  NOT NULL,
    DOB        DATE         NOT NULL,
    Telephone  VARCHAR(15)  NOT NULL,
    Address    VARCHAR(50)  NOT NULL,
    Email      VARCHAR(70)  NOT NULL,
    Password   VARCHAR(150) NOT NULL,
    Age        INT          NOT NULL,
    User_Image LONGBLOB,
    Role       ENUM('ADMIN', 'LECTURER', 'STUDENT', 'TECHNICAL_OFFICER') DEFAULT 'STUDENT',
    PRIMARY KEY (User_Id)
);

CREATE TABLE admin
(
    Admin_Id VARCHAR(20) NOT NULL,
    PRIMARY KEY (Admin_Id),
    FOREIGN KEY (Admin_Id) REFERENCES user (User_Id) ON DELETE CASCADE
);

CREATE TABLE lecturer
(
    Lecturer_Id     VARCHAR(20) NOT NULL,
    Department      ENUM('ICT','BST','ET') DEFAULT 'ICT',
    Enrollment_Date DATE        NOT NULL,
    Position        ENUM('PROFESSOR', 'SENIOR_LECTURER', 'LECTURER', 'ASSISTANT_LECTURER') DEFAULT 'LECTURER',
    PRIMARY KEY (Lecturer_Id),
    FOREIGN KEY (Lecturer_Id) REFERENCES user (User_Id) ON DELETE CASCADE
);

CREATE TABLE student
(
    Student_Id VARCHAR(20) NOT NULL,
    Level      VARCHAR(20) NOT NULL,
    Department ENUM('ICT','BST','ET') DEFAULT 'ICT',
    PRIMARY KEY (Student_Id),
    FOREIGN KEY (Student_Id) REFERENCES user (User_Id) ON DELETE CASCADE
);

CREATE TABLE technical_officer
(
    Technical_Id    VARCHAR(20) NOT NULL,
    Enrollment_Date DATE        NOT NULL,
    Department      ENUM('ICT','BST','ET') DEFAULT 'ICT',
    PRIMARY KEY (Technical_Id),
    FOREIGN KEY (Technical_Id) REFERENCES user (User_Id) ON DELETE CASCADE
);

CREATE TABLE course (
                        Level ENUM('LEVEL1', 'LEVEL2', 'LEVEL3', 'LEVEL4') NOT NULL,
                        Semester ENUM('SEMESTER1', 'SEMESTER2') NOT NULL,
                        Department ENUM('ICT', 'ET', 'BST') NOT NULL,
                        Course_Id VARCHAR(20) NOT NULL,
                        CourseName VARCHAR(100) NOT NULL,
                        CourseType ENUM('THEORY', 'PRACTICAL','THEORY_PRACTICAL') NOT NULL,
                        Lecturer_Id VARCHAR(20) NOT NULL,
                        Week VARCHAR(10) NOT NULL,
                        CreditStatus ENUM('CREDIT', 'NON-CREDIT') NOT NULL,
                        Credits INT NOT NULL,
                        PracticalHours VARCHAR(10) NOT NULL,
                        TheoryHours VARCHAR(10) NOT NULL,
                        PRIMARY KEY (Course_Id)
);


CREATE TABLE attendance
(
    AttendanceRecord_Id VARCHAR(20) NOT NULL,
    Technical_Id        VARCHAR(20) NOT NULL,
    Student_Id          VARCHAR(20) NOT NULL,
    Course_Id           VARCHAR(20) NOT NULL,
    Session_No          INT         NOT NULL,
    Status              ENUM('MC','PRESENT', 'ABSENT') DEFAULT 'PRESENT',
    Date                DATE        NOT NULL,
    Type                ENUM('PRACTICAL','THEORY') DEFAULT 'THEORY',
    PRIMARY KEY (AttendanceRecord_Id),
    FOREIGN KEY (Student_Id) REFERENCES student (Student_Id) ON DELETE CASCADE,
    FOREIGN KEY (Course_Id) REFERENCES course (Course_Id) ON DELETE CASCADE,
    FOREIGN KEY (Technical_Id) REFERENCES technical_officer (Technical_Id) ON DELETE CASCADE
);

CREATE TABLE mark (
                      MarkRecord_Id   VARCHAR(20) NOT NULL,
                      Lecture_Id      VARCHAR(20) NOT NULL,
                      Student_Id      VARCHAR(20) NOT NULL,
                      Course_Id       VARCHAR(20) NOT NULL,
                      Quiz_01         FLOAT DEFAULT 0,
                      Quiz_02         FLOAT DEFAULT 0,
                      Quiz_03         FLOAT DEFAULT 0,
                      Quiz_04         FLOAT DEFAULT 0,
                      Assignment_01   FLOAT DEFAULT 0,
                      Assignment_02   FLOAT DEFAULT 0,
                      Mid_Term        FLOAT DEFAULT 0,
                      Final_Theory    FLOAT DEFAULT 0,
                      Final_Practical FLOAT DEFAULT 0,
                      Grade           CHAR(4) NOT NULL,
                      PRIMARY KEY (MarkRecord_Id),
                      FOREIGN KEY (Student_Id) REFERENCES student (Student_Id) ON DELETE CASCADE,
                      FOREIGN KEY (Course_Id) REFERENCES course (Course_Id) ON DELETE CASCADE,
                      UNIQUE (Student_Id, Course_Id)
);

CREATE TABLE medical
(
    MedicalRecord_Id VARCHAR(20)  NOT NULL,
    Student_Id       VARCHAR(20)  NOT NULL,
    Course_Id        VARCHAR(20)  NOT NULL,
    Approval_Status  ENUM('APPROVED','UNAPPROVED', 'PENDING') DEFAULT 'PENDING',
    Submission_Date  DATE         NOT NULL,
    Type             ENUM('PRACTICAL','THEORY') DEFAULT 'THEORY',
    PRIMARY KEY (MedicalRecord_Id),
    FOREIGN KEY (Student_Id) REFERENCES student (Student_Id) ON DELETE CASCADE,
    FOREIGN KEY (Course_Id) REFERENCES course (Course_Id) ON DELETE CASCADE
);

CREATE TABLE notice (
                        Notice_Id int NOT NULL AUTO_INCREMENT,
                        Title varchar(20) NOT NULL,
                        Description varchar(100) NOT NULL,
                        Date_Posted date NOT NULL,
                        PRIMARY KEY (Notice_Id)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE stu_course
(
    Student_Id VARCHAR(20) NOT NULL,
    Course_Id  VARCHAR(20) NOT NULL,
    PRIMARY KEY (Student_Id, Course_Id),
    FOREIGN KEY (Course_Id) REFERENCES course (Course_Id) ON DELETE CASCADE,
    FOREIGN KEY (Student_Id) REFERENCES student (Student_Id) ON DELETE CASCADE
);

CREATE TABLE lecture_course (
                                Lecturer_Id VARCHAR(20) NOT NULL,
                                Course_Id VARCHAR(20) NOT NULL,
                                PRIMARY KEY (Lecturer_Id, Course_Id),
                                FOREIGN KEY (Course_Id) REFERENCES course (Course_Id) ON DELETE CASCADE,
                                FOREIGN KEY (Lecturer_Id) REFERENCES lecturer (Lecturer_Id) ON DELETE CASCADE
);

CREATE TABLE tech_officer_medical
(
    Technical_Id     VARCHAR(20) NOT NULL,
    MedicalRecord_Id VARCHAR(20) NOT NULL,
    PRIMARY KEY (Technical_Id, MedicalRecord_Id),
    FOREIGN KEY (MedicalRecord_Id) REFERENCES medical (MedicalRecord_Id) ON DELETE CASCADE,
    FOREIGN KEY (Technical_Id) REFERENCES technical_officer (Technical_Id) ON DELETE CASCADE
);


CREATE TABLE time_table (
                            TimeTable_Id int NOT NULL AUTO_INCREMENT,
                            Level varchar(10) NOT NULL,
                            Semester varchar(20) NOT NULL,
                            Timetable varchar(50) NOT NULL,
                            Date_Posted date NOT NULL,
                            Department enum('ICT','BST','ET') DEFAULT 'ICT',
                            PRIMARY KEY (TimeTable_Id)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE course_materials
(
    Material_Id INT AUTO_INCREMENT PRIMARY KEY,
    Course_Id   VARCHAR(10),
    Lecturer_Id VARCHAR(10),
    Title       VARCHAR(255),
    Description TEXT,
    Link        VARCHAR(255),
    Upload_Date DATE ,
    FOREIGN KEY (Course_Id) REFERENCES course (Course_Id),
    FOREIGN KEY (Lecturer_Id) REFERENCES lecturer (Lecturer_Id)
);



