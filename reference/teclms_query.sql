DROP DATABASE teclms;

CREATE
DATABASE teclms;

USE
teclms;

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

CREATE TABLE course
(
    Course_Id     VARCHAR(20) NOT NULL,
    Course_Name   VARCHAR(50) NOT NULL,
    Credit_Status ENUM('CREDIT','NON_CREDIT') DEFAULT 'CREDIT',
    Credits       INT         NOT NULL,
    Type          ENUM('PRACTICAL','PRACTICAL_THEORY', 'THEORY') DEFAULT 'PRACTICAL_THEORY',
    P_Hours       INT,
    T_Hours       INT,
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

CREATE TABLE mark
(
    MarkRecord_Id   VARCHAR(20) NOT NULL,
    Lecture_Id      VARCHAR(20) NOT NULL,
    Student_Id      VARCHAR(20) NOT NULL,
    Course_Id       VARCHAR(20) NOT NULL,
    Quiz_01         FLOAT,
    Quiz_02         FLOAT,
    Quiz_03         FLOAT,
    Quiz_04         FLOAT,
    Assignment_01   FLOAT,
    Assignment_02   FLOAT,
    Mid_Term        FLOAT,
    Final_Theory    FLOAT,
    Final_Practical FLOAT,
    Grade           CHAR(4)     NOT NULL,
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

CREATE TABLE notice
(
    Notice_Id   VARCHAR(20)  NOT NULL,
    Title       VARCHAR(20)  NOT NULL,
    Description VARCHAR(100) NOT NULL,
    Date_Posted DATE         NOT NULL,
    PRIMARY KEY (Notice_Id)
);

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

CREATE TABLE time_table
(
    TimeTable_Id VARCHAR(20) NOT NULL,
    Level        VARCHAR(10) NOT NULL,
    Semester     VARCHAR(20) NOT NULL,
    Timetable    VARCHAR(50) NOT NULL,
    Date_Posted  DATE        NOT NULL DEFAULT NOW(),
    Department   ENUM('ICT', 'BST', 'ET') DEFAULT 'ICT',
    PRIMARY KEY (TimeTable_Id)
);

CREATE TABLE course_materials
(
    Material_Id INT AUTO_INCREMENT PRIMARY KEY,
    Course_Id   VARCHAR(10),
    Lecturer_Id VARCHAR(10),
    Title       VARCHAR(255),
    Description TEXT,
    Link        VARCHAR(255),
    Upload_Date DATE DEFAULT NOW(),
    FOREIGN KEY (Course_Id) REFERENCES course (Course_Id),
    FOREIGN KEY (Lecturer_Id) REFERENCES lecturer (Lecturer_Id)
);


INSERT INTO user (User_Id, First_Name, Last_Name, DOB, Telephone, Address, Email, Password, Age, Role)
VALUES ('AD0001', 'Nimal', 'Perera', '1980-05-15', '0771234567', '123 Galle Road, Colombo 03', 'nimal.perera@teclms.lk',
        '$2a$12$/fdo01d1vFqY32kKqyWUe.ZShGUx9rF/bNUlKuUnI1M3QL8pelwlW', 45, 'ADMIN'),

       ('LE0001', 'Kamal', 'Fernando', '1975-08-20', '0712345678', '45 Temple Road, Kandy', 'kamal.fernando@teclms.lk',
        '$2a$12$/fdo01d1vFqY32kKqyWUe.ZShGUx9rF/bNUlKuUnI1M3QL8pelwlW', 50, 'LECTURER'),
       ('LE0002', 'Sunil', 'Rajapaksa', '1982-03-10', '0763456789', '78 Anuradhapura Road, Kurunegala',
        'sunil.rajapaksa@teclms.lk', '$2a$12$/fdo01d1vFqY32kKqyWUe.ZShGUx9rF/bNUlKuUnI1M3QL8pelwlW', 43, 'LECTURER'),
       ('LE0003', 'Priyanka', 'Silva', '1985-11-25', '0754567890', '12 Negombo Road, Gampaha',
        'priyanka.silva@teclms.lk', '$2a$12$/fdo01d1vFqY32kKqyWUe.ZShGUx9rF/bNUlKuUnI1M3QL8pelwlW', 40, 'LECTURER'),
       ('LE0004', 'Samantha', 'Priyadarshana', '1983-10-25', '0752598134', '133 Kandy Road, Kadavatha',
        'samantha.priyadarshana@teclms.lk', '$2a$12$/fdo01d1vFqY32kKqyWUe.ZShGUx9rF/bNUlKuUnI1M3QL8pelwlW', 55, 'LECTURER'),
       ('LE0005', 'Dinesha', 'Kulathunga', '1981-09-25', '0789531247', '14 Ja ala Road, Gampaha',
        'Dinesha.priyadarshana@teclms.lk', '$2a$12$/fdo01d1vFqY32kKqyWUe.ZShGUx9rF/bNUlKuUnI1M3QL8pelwlW', 44,
        'LECTURER'),

       ('TG1344', 'Dilshan', 'Bandara', '2000-02-15', '0785678901', '34 Matara Road, Galle',
        'dilshan.bandara@teclms.lk', '$2a$12$/fdo01d1vFqY32kKqyWUe.ZShGUx9rF/bNUlKuUnI1M3QL8pelwlW', 23, 'STUDENT'),
       ('TG1345', 'Nayana', 'Wickramasinghe', '2001-07-22', '0766789012', '56 Kandy Road, Kegalle',
        'nayana.wick@teclms.lk', '$2a$12$/fdo01d1vFqY32kKqyWUe.ZShGUx9rF/bNUlKuUnI1M3QL8pelwlW', 22, 'STUDENT'),
       ('TG1346', 'Saman', 'Alwis', '1999-09-30', '0717890123', '89 Ratnapura Road, Kalutara', 'saman.alwis@teclms.lk',
        '$2a$12$/fdo01d1vFqY32kKqyWUe.ZShGUx9rF/bNUlKuUnI1M3QL8pelwlW', 24, 'STUDENT'),
       ('TG1347', 'Chamari', 'Atapattu', '2000-11-12', '0728901234', '23 Colombo Road, Panadura',
        'chamari.ata@teclms.lk', '$2a$12$/fdo01d1vFqY32kKqyWUe.ZShGUx9rF/bNUlKuUnI1M3QL8pelwlW', 23, 'STUDENT'),
       ('TG1348', 'Nipun', 'Karunaratne', '2001-03-25', '0712345678', '14 Hill Street, Kandy', 'nipun.karu@teclms.lk',
        '$2a$12$/fdo01d1vFqY32kKqyWUe.ZShGUx9rF/bNUlKuUnI1M3QL8pelwlW', 22, 'STUDENT'),
       ('TG1349', 'Anjali', 'Wickramasinghe', '2002-07-18', '0754321987', '456 Main Street, Galle',
        'anjali.wick@teclms.lk', '$2a$12$/fdo01d1vFqY32kKqyWUe.ZShGUx9rF/bNUlKuUnI1M3QL8pelwlW', 21, 'STUDENT'),
       ('TG1350', 'Dineth', 'Perera', '2000-02-02', '0701239876', '77 Lake View, Kurunegala', 'dineth.per@teclms.lk',
        '$2a$12$/fdo01d1vFqY32kKqyWUe.ZShGUx9rF/bNUlKuUnI1M3QL8pelwlW', 24, 'STUDENT'),
       ('TG1351', 'Isuri', 'Fernando', '2001-05-09', '0765432109', '56 Palm Grove, Negombo', 'isuri.fer@teclms.lk',
        '$2a$12$/fdo01d1vFqY32kKqyWUe.ZShGUx9rF/bNUlKuUnI1M3QL8pelwlW', 23, 'STUDENT'),
       ('TG1352', 'Thilina', 'Bandara', '2002-08-22', '0789091234', '19 Green Lane, Matara', 'thilina.ban@teclms.lk',
        '$2a$12$/fdo01d1vFqY32kKqyWUe.ZShGUx9rF/bNUlKuUnI1M3QL8pelwlW', 22, 'STUDENT'),
       ('TG1353', 'Harshi', 'Senanayake', '2000-12-01', '0745623210', '102 Sea Road, Trincomalee',
        'harshi.sena@teclms.lk', '$2a$12$/fdo01d1vFqY32kKqyWUe.ZShGUx9rF/bNUlKuUnI1M3QL8pelwlW', 24, 'STUDENT'),
       ('TG1354', 'Kavindu', 'Weerasinghe', '2001-11-11', '0756677890', '45 Temple Road, Anuradhapura',
        'kavindu.wee@teclms.lk', '$2a$12$/fdo01d1vFqY32kKqyWUe.ZShGUx9rF/bNUlKuUnI1M3QL8pelwlW', 23, 'STUDENT'),
       ('TG1355', 'Nilakshi', 'Silva', '2003-01-15', '0712233445', '88 Hill Crest, Colombo 5',
        'nilakshi.silva@teclms.lk', '$2a$12$/fdo01d1vFqY32kKqyWUe.ZShGUx9rF/bNUlKuUnI1M3QL8pelwlW', 22, 'STUDENT'),
       ('TG1356', 'Ravindu', 'Jayasinghe', '2000-06-06', '0721112233', '33 Lotus Lane, Ratnapura',
        'ravindu.jaya@teclms.lk', '$2a$12$/fdo01d1vFqY32kKqyWUe.ZShGUx9rF/bNUlKuUnI1M3QL8pelwlW', 24, 'STUDENT'),
       ('TG1357', 'Sanduni', 'Rajapaksha', '2001-09-17', '0787654321', '29 Garden Way, Polonnaruwa',
        'sanduni.raj@teclms.lk', '$2a$12$/fdo01d1vFqY32kKqyWUe.ZShGUx9rF/bNUlKuUnI1M3QL8pelwlW', 23, 'STUDENT'),
       ('TG1358', 'Kasun', 'Madushanka', '2002-04-30', '0775432123', '60 Park Road, Kalutara', 'kasun.madu@teclms.lk',
        '$2a$12$/fdo01d1vFqY32kKqyWUe.ZShGUx9rF/bNUlKuUnI1M3QL8pelwlW', 22, 'STUDENT'),
       ('TG1359', 'Tharushi', 'Edirisinghe', '2000-08-03', '0749988776', '5 Sunset Street, Nuwara Eliya',
        'tharushi.edi@teclms.lk', '$2a$12$/fdo01d1vFqY32kKqyWUe.ZShGUx9rF/bNUlKuUnI1M3QL8pelwlW', 24, 'STUDENT'),
       ('TG1360', 'Ramesh', 'Kumar', '2003-02-14', '0734567890', '123 Unity Lane, Batticaloa', 'ramesh.kumar@teclms.lk',
        '$2a$12$/fdo01d1vFqY32kKqyWUe.ZShGUx9rF/bNUlKuUnI1M3QL8pelwlW', 21, 'STUDENT'),
       ('TG1361', 'Hiruni', 'Perera', '2001-10-10', '0708765432', '7 Lotus Park, Jaffna', 'hiruni.per@teclms.lk',
        '$2a$12$/fdo01d1vFqY32kKqyWUe.ZShGUx9rF/bNUlKuUnI1M3QL8pelwlW', 23, 'STUDENT'),
       ('TG1362', 'Supun', 'Wijesekara', '2000-07-21', '0723456789', '41 City Lane, Gampaha', 'supun.wije@teclms.lk',
        '$2a$12$/fdo01d1vFqY32kKqyWUe.ZShGUx9rF/bNUlKuUnI1M3QL8pelwlW', 24, 'STUDENT'),
       ('TG1363', 'Imesha', 'Ranasinghe', '2002-06-12', '0711234567', '92 Lake Road, Kegalle', 'imesha.rana@teclms.lk',
        '$2a$12$/fdo01d1vFqY32kKqyWUe.ZShGUx9rF/bNUlKuUnI1M3QL8pelwlW', 22, 'STUDENT'),

       ('TO0001', 'Ruwan', 'Jayawardena', '1990-04-18', '0778901234', '23 Badulla Road, Nuwara Eliya',
        'ruwan.jaya@teclms.lk', '$2a$12$/fdo01d1vFqY32kKqyWUe.ZShGUx9rF/bNUlKuUnI1M3QL8pelwlW', 33,
        'TECHNICAL_OFFICER'),
       ('TO0002', 'Anoma', 'Ratnayake', '1992-12-05', '0759012345', '67 Trincomalee Road, Batticaloa',
        'anoma.rat@teclms.lk', '$2a$12$/fdo01d1vFqY32kKqyWUe.ZShGUx9rF/bNUlKuUnI1M3QL8pelwlW', 31, 'TECHNICAL_OFFICER'),
       ('TO0003', 'Ruwan', 'Jayalath', '1990-03-22', '0746789123', '12 Station Road, Galle', 'ruwan.jaya@teclms.lk',
        '$2a$12$/fdo01d1vFqY32kKqyWUe.ZShGUx9rF/bNUlKuUnI1M3QL8pelwlW', 34, 'TECHNICAL_OFFICER'),
       ('TO0004', 'Sithara', 'Wijeratne', '1994-08-15', '0719988776', '88 Palm Street, Kurunegala',
        'sithara.wije@teclms.lk', '$2a$12$/fdo01d1vFqY32kKqyWUe.ZShGUx9rF/bNUlKuUnI1M3QL8pelwlW', 30,
        'TECHNICAL_OFFICER');

INSERT INTO admin (Admin_Id)
VALUES ('AD0001');

INSERT INTO lecturer (Lecturer_Id, Department, Enrollment_Date, Position)
VALUES ('LE0001', 'ICT', '2010-06-15', 'PROFESSOR'),
       ('LE0002', 'ICT', '2015-03-20', 'SENIOR_LECTURER'),
       ('LE0003', 'ICT', '2018-01-10', 'LECTURER'),
       ('LE0004', 'ICT', '2020-09-01', 'LECTURER'),
       ('LE0005', 'ICT', '2022-02-18', 'ASSISTANT_PROFESSOR');



INSERT INTO student (Student_Id, Level, Department)
VALUES ('TG1344', 'II', 'ICT'),
       ('TG1345', 'II', 'ICT'),
       ('TG1346', 'II', 'ICT'),
       ('TG1347', 'II', 'ICT'),
       ('TG1348', 'II', 'ICT'),
       ('TG1349', 'II', 'ICT'),
       ('TG1350', 'II', 'ICT'),
       ('TG1351', 'II', 'ICT'),
       ('TG1352', 'II', 'ICT'),
       ('TG1353', 'II', 'ICT'),
       ('TG1354', 'II', 'ICT'),
       ('TG1355', 'II', 'ICT'),
       ('TG1356', 'II', 'ICT'),
       ('TG1357', 'II', 'ICT'),
       ('TG1358', 'II', 'ICT'),
       ('TG1359', 'II', 'ICT'),
       ('TG1360', 'II', 'ICT'),
       ('TG1361', 'II', 'ICT'),
       ('TG1362', 'II', 'ICT'),
       ('TG1363', 'II', 'ICT');

INSERT INTO technical_officer (Technical_Id, Enrollment_Date, Department)
VALUES ('TO0001', '2015-08-15', 'ICT'),
       ('TO0002', '2018-02-20', 'ICT'),
       ('TO0003', '2021-02-10', 'ICT'),
       ('TO0004', '2018-10-08', 'ICT');

INSERT INTO course (Course_Id, Course_Name, Credit_Status, Credits, Type, T_Hours, P_Hours)
VALUES ('ICT2113', 'Data Structures and Algorithms', 'CREDIT', 3, 'PRACTICAL_THEORY', 4, 2),
       ('ICT2133', '  Object Oriented Programming Practicum', 'CREDIT', 3, 'PRACTICAL_THEORY', 4, 2);

INSERT INTO course (Course_Id, Course_Name, Credit_Status, Credits, Type, T_Hours)
VALUES ('ICT2122', 'Object Oriented Programming', 'CREDIT', 2, 'THEORY', 2),
       ('ICT2152', 'E-Commerce Implementation, Management and Security', 'CREDIT', 2, 'THEORY', 2);

INSERT INTO course (Course_Id, Course_Name, Credit_Status, Credits, Type, P_Hours)
VALUES ('ICT2142', 'Object Oriented Analysis and Design', 'CREDIT', 2, 'PRACTICAL', 2);

INSERT INTO lecture_course (Lecturer_Id, Course_Id)
VALUES
    ('LE0001', 'ICT2113'),
    ('LE0002', 'ICT2133'),
    ('LE0003', 'ICT2122'),
    ('LE0004', 'ICT2152'),
    ('LE0005', 'ICT2142');

INSERT INTO stu_course (Student_Id, Course_Id)
VALUES ('TG1344', 'ICT2113'),
       ('TG1344', 'ICT2122'),
       ('TG1344', 'ICT2133'),
       ('TG1344', 'ICT2142'),
       ('TG1344', 'ICT2152'),
       ('TG1345', 'ICT2113'),
       ('TG1345', 'ICT2122'),
       ('TG1345', 'ICT2133'),
       ('TG1345', 'ICT2142'),
       ('TG1345', 'ICT2152'),
       ('TG1346', 'ICT2113'),
       ('TG1346', 'ICT2122'),
       ('TG1346', 'ICT2133'),
       ('TG1346', 'ICT2142'),
       ('TG1346', 'ICT2152'),
       ('TG1347', 'ICT2113'),
       ('TG1347', 'ICT2122'),
       ('TG1347', 'ICT2133'),
       ('TG1347', 'ICT2142'),
       ('TG1347', 'ICT2152'),
       ('TG1348', 'ICT2113'),
       ('TG1348', 'ICT2122'),
       ('TG1348', 'ICT2133'),
       ('TG1348', 'ICT2142'),
       ('TG1348', 'ICT2152'),
       ('TG1349', 'ICT2113'),
       ('TG1349', 'ICT2122'),
       ('TG1349', 'ICT2133'),
       ('TG1349', 'ICT2142'),
       ('TG1349', 'ICT2152'),
       ('TG1350', 'ICT2113'),
       ('TG1350', 'ICT2122'),
       ('TG1350', 'ICT2133'),
       ('TG1350', 'ICT2142'),
       ('TG1350', 'ICT2152'),
       ('TG1351', 'ICT2113'),
       ('TG1351', 'ICT2122'),
       ('TG1351', 'ICT2133'),
       ('TG1351', 'ICT2142'),
       ('TG1351', 'ICT2152'),
       ('TG1352', 'ICT2113'),
       ('TG1352', 'ICT2122'),
       ('TG1352', 'ICT2133'),
       ('TG1352', 'ICT2142'),
       ('TG1352', 'ICT2152'),
       ('TG1353', 'ICT2113'),
       ('TG1353', 'ICT2122'),
       ('TG1353', 'ICT2133'),
       ('TG1353', 'ICT2142'),
       ('TG1353', 'ICT2152'),
       ('TG1354', 'ICT2113'),
       ('TG1354', 'ICT2122'),
       ('TG1354', 'ICT2133'),
       ('TG1354', 'ICT2142'),
       ('TG1354', 'ICT2152'),
       ('TG1355', 'ICT2113'),
       ('TG1355', 'ICT2122'),
       ('TG1355', 'ICT2133'),
       ('TG1355', 'ICT2142'),
       ('TG1355', 'ICT2152'),
       ('TG1356', 'ICT2113'),
       ('TG1356', 'ICT2122'),
       ('TG1356', 'ICT2133'),
       ('TG1356', 'ICT2142'),
       ('TG1356', 'ICT2152'),
       ('TG1357', 'ICT2113'),
       ('TG1357', 'ICT2122'),
       ('TG1357', 'ICT2133'),
       ('TG1357', 'ICT2142'),
       ('TG1357', 'ICT2152'),
       ('TG1358', 'ICT2113'),
       ('TG1358', 'ICT2122'),
       ('TG1358', 'ICT2133'),
       ('TG1358', 'ICT2142'),
       ('TG1358', 'ICT2152'),
       ('TG1359', 'ICT2113'),
       ('TG1359', 'ICT2122'),
       ('TG1359', 'ICT2133'),
       ('TG1359', 'ICT2142'),
       ('TG1359', 'ICT2152'),
       ('TG1360', 'ICT2113'),
       ('TG1360', 'ICT2122'),
       ('TG1360', 'ICT2133'),
       ('TG1360', 'ICT2142'),
       ('TG1360', 'ICT2152'),
       ('TG1361', 'ICT2113'),
       ('TG1361', 'ICT2122'),
       ('TG1361', 'ICT2133'),
       ('TG1361', 'ICT2142'),
       ('TG1361', 'ICT2152'),
       ('TG1362', 'ICT2113'),
       ('TG1362', 'ICT2122'),
       ('TG1362', 'ICT2133'),
       ('TG1362', 'ICT2142'),
       ('TG1362', 'ICT2152'),
       ('TG1363', 'ICT2113'),
       ('TG1363', 'ICT2122'),
       ('TG1363', 'ICT2133'),
       ('TG1363', 'ICT2142'),
       ('TG1363', 'ICT2152');

-- For ICT2113
INSERT INTO mark (MarkRecord_Id, Lecture_Id, Quiz_01, Quiz_02, Quiz_03, Mid_Term, Final_Theory, Final_Practical, Grade, Course_Id,
                  Student_Id)
VALUES ('MR0001', "LE0001", 8, 7, 9, 18, 30, 28, 'A', 'ICT2113', 'TG1344'),
       ('MR0006', "LE0001", 5, 7, 6, 14, 26, 24, 'B+', 'ICT2113', 'TG1345'),
       ('MR0011', "LE0001", 6, 6, 5, 14, 25, 25, 'B+', 'ICT2113', 'TG1346'),
       ('MR0016', "LE0001", 5, 7, 8, 15, 26, 16, 'B', 'ICT2113', 'TG1347'),
       ('MR0021', "LE0001", 7, 8, 6, 16, 28, 18, 'B', 'ICT2113', 'TG1348'),
       ('MR0026', "LE0001", 5, 6, 5, 13, 26, 27, 'B+', 'ICT2113', 'TG1349'),
       ('MR0031', "LE0001", 6, 7, 8, 15, 27, 18, 'B', 'ICT2113', 'TG1350'),
       ('MR0036', "LE0001", 7, 8, 7, 16, 29, 19, 'B+', 'ICT2113', 'TG1351'),
       ('MR0041', "LE0001", 6, 7, 6, 15, 32, 29, 'A', 'ICT2113', 'TG1352'),
       ('MR0046', "LE0001", 7, 8, 7, 16, 28, 20, 'B+', 'ICT2113', 'TG1353'),
       ('MR0051', "LE0001", 6, 7, 6, 15, 27, 18, 'B', 'ICT2113', 'TG1354'),
       ('MR0056', "LE0001", 5, 5, 6, 12, 23, 15, 'C+', 'ICT2113', 'TG1355'),
       ('MR0061', "LE0001", 6, 7, 6, 14, 37, 18, 'A-', 'ICT2113', 'TG1356'),
       ('MR0066', "LE0001", 7, 6, 8, 15, 27, 18, 'B', 'ICT2113', 'TG1357'),
       ('MR0071', "LE0001", 8, 8, 7, 17, 30, 20, 'A-', 'ICT2113', 'TG1358'),
       ('MR0076', "LE0001", 6, 7, 6, 14, 25, 27, 'B+', 'ICT2113', 'TG1359'),
       ('MR0081', "LE0001", 5, 5, 6, 13, 25, 17, 'B-', 'ICT2113', 'TG1360'),
       ('MR0086', "LE0001", 6, 6, 7, 15, 27, 18, 'B', 'ICT2113', 'TG1361'),
       ('MR0091', "LE0001", 5, 6, 6, 13, 25, 17, 'B-', 'ICT2113', 'TG1362'),
       ('MR0096', "LE0001", 7, 8, 8, 17, 21, 20, 'B', 'ICT2113', 'TG1363');


-- For ICT2122
INSERT INTO mark (MarkRecord_Id, Lecture_Id, Quiz_01, Quiz_02, Quiz_03, Quiz_04, Assignment_01, Mid_Term, Final_Theory, Grade,
                  Course_Id, Student_Id)
VALUES ('MR0002', "LE0002", 7, 8, 8, 9, 7, 14, 39, 'B', 'ICT2122', 'TG1344'),
       ('MR0007', "LE0002", 6, 6, 7, 6, 5, 19, 45, 'A-', 'ICT2122', 'TG1345'),
       ('MR0012', "LE0002", 7, 8, 8, 8, 7, 12, 58, 'A+', 'ICT2122', 'TG1346'),
       ('MR0017', "LE0002", 6, 8, 7, 9, 6, 13, 29, 'C+', 'ICT2122', 'TG1347'),
       ('MR0022', "LE0002", 5, 7, 8, 9, 7, 15, 46, 'A-', 'ICT2122', 'TG1348'),
       ('MR0027', "LE0002", 6, 7, 6, 7, 5, 16, 28, 'C+', 'ICT2122', 'TG1349'),
       ('MR0032', "LE0002", 5, 5, 6, 5, 3, 12, 36, 'C+', 'ICT2122', 'TG1350'),
       ('MR0037', "LE0002", 6, 6, 7, 6, 4, 19, 25, 'C', 'ICT2122', 'TG1351'),
       ('MR0042', "LE0002", 7, 6, 8, 7, 4, 14, 46, 'B+', 'ICT2122', 'TG1352'),
       ('MR0047', "LE0002", 6, 6, 6, 6, 4, 18, 25, 'C', 'ICT2122', 'TG1353'),
       ('MR0052', "LE0002", 7, 8, 8, 6, 6, 14, 59, 'A+', 'ICT2122', 'TG1354'),
       ('MR0057', "LE0002", 7, 8, 7, 6, 6, 14, 28, 'C+', 'ICT2122', 'TG1355'),
       ('MR0062', "LE0002", 7, 8, 8, 7, 7, 15, 39, 'B', 'ICT2122', 'TG1356'),
       ('MR0067', "LE0002", 6, 7, 6, 6, 4, 12, 37, 'B-', 'ICT2122', 'TG1357'),
       ('MR0072', "LE0002", 5, 6, 6, 6, 3, 11, 24, 'D+', 'ICT2122', 'TG1358'),
       ('MR0077', "LE0002", 8, 8, 9, 7, 8, 16, 30, 'B', 'ICT2122', 'TG1359'),
       ('MR0082', "LE0002", 7, 8, 7, 7, 6, 14, 33, 'B-', 'ICT2122', 'TG1360'),
       ('MR0087', "LE0002", 7, 8, 8, 7, 7, 15, 29, 'B-', 'ICT2122', 'TG1361'),
       ('MR0092', "LE0002", 8, 8, 9, 8, 8, 16, 30, 'B', 'ICT2122', 'TG1362'),
       ('MR0097', "LE0002", 6, 7, 6, 7, 5, 13, 47, 'B+', 'ICT2122', 'TG1363');

-- For ICT2133
INSERT INTO mark (MarkRecord_Id, Lecture_Id, Quiz_01, Quiz_02, Quiz_03, Assignment_01, Assignment_02, Final_Theory, Final_Practical,
                  Grade, Course_Id, Student_Id)
VALUES ('MR0003', "LE0003", 6, 6, 7, 8, 6, 30, 28, 'B', 'ICT2133', 'TG1344'),
       ('MR0008', "LE0003", 8, 8, 7, 9, 8, 23, 29, 'A-', 'ICT2133', 'TG1345'),
       ('MR0013', "LE0003", 9, 5, 7, 8, 3, 23, 26, 'B', 'ICT2133', 'TG1346'),
       ('MR0018', "LE0003", 7, 6, 9, 8, 4, 21, 25, 'B-', 'ICT2133', 'TG1347'),
       ('MR0023', "LE0003", 6, 6, 7, 8, 5, 22, 27, 'B-', 'ICT2133', 'TG1348'),
       ('MR0028', "LE0003", 7, 8, 7, 8, 6, 24, 29, 'A-', 'ICT2133', 'TG1349'),
       ('MR0033', "LE0003", 8, 7, 7, 6, 6, 34, 28, 'A-', 'ICT2133', 'TG1350'),
       ('MR0038', "LE0003", 8, 9, 8, 7, 7, 25, 28, 'A', 'ICT2133', 'TG1351'),
       ('MR0043', "LE0003", 5, 5, 6, 6, 2, 20, 23, 'C', 'ICT2133', 'TG1352'),
       ('MR0048', "LE0003", 8, 9, 9, 8, 8, 26, 30, 'A', 'ICT2133', 'TG1353'),
       ('MR0053', "LE0003", 5, 6, 7, 6, 3, 21, 25, 'B-', 'ICT2133', 'TG1354'),
       ('MR0058', "LE0003", 6, 6, 7, 7, 5, 23, 26, 'B', 'ICT2133', 'TG1355'),
       ('MR0063', "LE0003", 6, 6, 6, 6, 4, 31, 24, 'B-', 'ICT2133', 'TG1356'),
       ('MR0068', "LE0003", 5, 5, 5, 5, 2, 20, 22, 'C', 'ICT2133', 'TG1357'),
       ('MR0073', "LE0003", 6, 7, 6, 7, 4, 22, 25, 'B-', 'ICT2133', 'TG1358'),
       ('MR0078', "LE0003", 7, 7, 6, 6, 6, 23, 28, 'B+', 'ICT2133', 'TG1359'),
       ('MR0083', "LE0003", 6, 6, 6, 6, 4, 22, 26, 'B-', 'ICT2133', 'TG1360'),
       ('MR0088', "LE0003", 5, 5, 6, 6, 2, 20, 24, 'C', 'ICT2133', 'TG1361'),
       ('MR0093', "LE0003", 7, 6, 6, 7, 5, 33, 28, 'A-', 'ICT2133', 'TG1362'),
       ('MR0098', "LE0003", 5, 6, 5, 6, 3, 21, 25, 'C+', 'ICT2133', 'TG1363');


-- For ICT2142
INSERT INTO mark (MarkRecord_Id, Lecture_Id, Assignment_01, Mid_Term, Final_Practical, Grade, Course_Id, Student_Id)
VALUES ('MR0004', "LE0004", 19, 16, 39, 'B+', 'ICT2142', 'TG1344'),
       ('MR0009', "LE0004", 20, 17, 45, 'A', 'ICT2142', 'TG1345'),
       ('MR0014', "LE0004", 12, 10, 58, 'A', 'ICT2142', 'TG1346'),
       ('MR0019', "LE0004", 12, 10, 29, 'C', 'ICT2142', 'TG1347'),
       ('MR0024', "LE0004", 18, 15, 46, 'A-', 'ICT2142', 'TG1348'),
       ('MR0029', "LE0004", 12, 10, 28, 'C', 'ICT2142', 'TG1349'),
       ('MR0034', "LE0004", 17, 15, 36, 'B', 'ICT2142', 'TG1350'),
       ('MR0039', "LE0004", 13, 11, 25, 'C-', 'ICT2142', 'TG1351'),
       ('MR0044', "LE0004", 18, 16, 46, 'A', 'ICT2142', 'TG1352'),
       ('MR0049', "LE0004", 12, 10, 25, 'C-', 'ICT2142', 'TG1353'),
       ('MR0054', "LE0004", 18, 16, 59, 'A+', 'ICT2142', 'TG1354'),
       ('MR0059', "LE0004", 19, 17, 28, 'B-', 'ICT2142', 'TG1355'),
       ('MR0064', "LE0004", 12, 10, 39, 'B-', 'ICT2142', 'TG1356'),
       ('MR0069', "LE0004", 18, 15, 37, 'B+', 'ICT2142', 'TG1357'),
       ('MR0074', "LE0004", 16, 14, 24, 'C', 'ICT2142', 'TG1358'),
       ('MR0079', "LE0004", 12, 10, 30, 'C', 'ICT2142', 'TG1359'),
       ('MR0084', "LE0004", 18, 17, 33, 'B', 'ICT2142', 'TG1360'),
       ('MR0089', "LE0004", 18, 17, 29, 'B-', 'ICT2142', 'TG1361'),
       ('MR0094', "LE0004", 14, 12, 30, 'C+', 'ICT2142', 'TG1362'),
       ('MR0099', "LE0004", 18, 17, 47, 'A', 'ICT2142', 'TG1363');

-- For ICT2152
INSERT INTO mark (MarkRecord_Id, Lecture_Id, Quiz_01, Quiz_02, Quiz_03, Assignment_01, Assignment_02, Final_Theory, Grade,
                  Course_Id, Student_Id)
VALUES ('MR0005', "LE0005", 7, 6, 6, 8, 5, 57, 'A+', 'ICT2152', 'TG1344'),
       ('MR0010', "LE0005", 5, 5, 6, 5, 2, 44, 'B+', 'ICT2152', 'TG1345'),
       ('MR0015', "LE0005", 8, 7, 6, 9, 9, 68, 'A+', 'ICT2152', 'TG1346'),
       ('MR0020', "LE0005", 9, 8, 7, 6, 8, 30, 'B+', 'ICT2152', 'TG1347'),
       ('MR0025', "LE0005", 6, 7, 6, 6, 4, 47, 'A', 'ICT2152', 'TG1348'),
       ('MR0030', "LE0005", 8, 7, 8, 9, 8, 36, 'A', 'ICT2152', 'TG1349'),
       ('MR0035', "LE0005", 6, 6, 7, 6, 5, 37, 'B+', 'ICT2152', 'TG1350'),
       ('MR0040', "LE0005", 7, 8, 7, 6, 6, 47, 'A+', 'ICT2152', 'TG1351'),
       ('MR0045', "LE0005", 6, 7, 7, 6, 5, 27, 'B-', 'ICT2152', 'TG1352'),
       ('MR0050', "LE0005", 7, 7, 8, 7, 6, 57, 'A+', 'ICT2152', 'TG1353'),
       ('MR0055', "LE0005", 6, 6, 6, 7, 4, 26, 'B-', 'ICT2152', 'TG1354'),
       ('MR0060', "LE0005", 5, 5, 6, 6, 3, 45, 'A-', 'ICT2152', 'TG1355'),
       ('MR0065', "LE0005", 8, 8, 7, 8, 8, 30, 'B+', 'ICT2152', 'TG1356'),
       ('MR0070', "LE0005", 6, 6, 7, 6, 4, 66, 'A+', 'ICT2152', 'TG1357'),
       ('MR0075', "LE0005", 5, 5, 6, 5, 2, 33, 'B-', 'ICT2152', 'TG1358'),
       ('MR0080', "LE0005", 6, 6, 6, 7, 4, 47, 'A', 'ICT2152', 'TG1359'),
       ('MR0085', "LE0005", 5, 6, 5, 5, 3, 44, 'B+', 'ICT2152', 'TG1360'),
       ('MR0090', "LE0005", 6, 6, 7, 7, 5, 28, 'B-', 'ICT2152', 'TG1361'),
       ('MR0095', "LE0005", 5, 5, 5, 6, 2, 53, 'A', 'ICT2152', 'TG1362'),
       ('MR0100', "LE0005", 6, 6, 6, 6, 4, 66, 'A+', 'ICT2152', 'TG1363');

INSERT INTO notice (Notice_Id, Title, Description, Date_Posted)
VALUES ('NT0001', 'Exam Schedule', 'The semester final exam timetable has been released.', '2025-01-10'),
       ('NT0002', 'Lab Closed', 'The ICT lab will be closed on Friday for maintenance.', '2025-01-12'),
       ('NT0003', 'Guest Lecture', 'Join the guest lecture on AI trends this Thursday at 10AM.', '2025-01-14'),
       ('NT0004', 'Holiday Notice', 'University will remain closed on 1st May for Labor Day.', '2025-01-15'),
       ('NT0005', 'Project Submission', 'Final year project submissions are due by April 30.', '2025-01-16'),
       ('NT0006', 'Course Drop', 'Course drop deadline is extended to April 25.', '2025-01-17'),
       ('NT0007', 'Library Hours', 'New library hours are 8AM to 6PM on weekdays.', '2025-01-18'),
       ('NT0008', 'Hackathon', 'Registrations are open for the inter-faculty hackathon.', '2025-01-19'),
       ('NT0009', 'Results Released', 'Second-year first semester results are now available.', '2025-01-20'),
       ('NT0010', 'Workshop', 'UI/UX design workshop on April 26 in Lab 3.', '2025-01-21');


INSERT INTO medical (MedicalRecord_Id, Approval_Status, Submission_Date, Course_Id, Student_Id, Type)
VALUES ('MD0001', 'Approved', '2025-01-20', 'ICT2113', 'TG1346', 'PRACTICAL'),
       ('MD0002', 'Unapproved', '2025-01-21', 'ICT2122', 'TG1353', 'THEORY'),
       ('MD0003', 'Approved', '2025-01-22', 'ICT2133', 'TG1350', 'PRACTICAL'),
       ('MD0004', 'Approved', '2025-01-23', 'ICT2142', 'TG1355', 'PRACTICAL'),
       ('MD0005', 'Pending', '2025-01-24', 'ICT2152', 'TG1357', 'THEORY'),
       ('MD0006', 'Approved', '2025-01-20', 'ICT2113', 'TG1349', 'PRACTICAL'),
       ('MD0007', 'Unapproved', '2025-01-21', 'ICT2122', 'TG1361', 'THEORY'),
       ('MD0008', 'Approved', '2025-01-22', 'ICT2133', 'TG1352', 'THEORY'),
       ('MD0009', 'Pending', '2025-01-22', 'ICT2133', 'TG1344', 'THEORY'),
       ('MD0010', 'Pending', '2025-01-21', 'ICT2122', 'TG1359', 'THEORY');

INSERT INTO tech_officer_medical(Technical_Id, MedicalRecord_Id)
VALUES ('TO0001', 'MD0001'),
       ('TO0002', 'MD0003'),
       ('TO0002', 'MD0004'),
       ('TO0004', 'MD0006'),
       ('TO0003', 'MD0008');


INSERT INTO attendance (AttendanceRecord_Id, Technical_Id, Student_Id, Course_Id, Session_No, Status, Date, Type)
VALUES ('AT0001', 'TO0001', 'TG1344', 'ICT2113', 1, 'PRESENT', '2025-1-20', 'THEORY'),
       ('AT0002', 'TO0001', 'TG1345', 'ICT2113', 1, 'ABSENT', '2025-1-20', 'THEORY'),
       ('AT0003', 'TO0001', 'TG1346', 'ICT2113', 1, 'PRESENT', '2025-1-20', 'THEORY'),
       ('AT0004', 'TO0001', 'TG1347', 'ICT2113', 1, 'PRESENT', '2025-1-20', 'THEORY'),
       ('AT0005', 'TO0001', 'TG1348', 'ICT2113', 1, 'ABSENT', '2025-1-20', 'THEORY'),
       ('AT0006', 'TO0001', 'TG1349', 'ICT2113', 1, 'PRESENT', '2025-1-20', 'THEORY'),
       ('AT0007', 'TO0001', 'TG1350', 'ICT2113', 1, 'PRESENT', '2025-1-20', 'THEORY'),
       ('AT0008', 'TO0001', 'TG1351', 'ICT2113', 1, 'ABSENT', '2025-1-20', 'THEORY'),
       ('AT0009', 'TO0001', 'TG1352', 'ICT2113', 1, 'PRESENT', '2025-1-20', 'THEORY'),
       ('AT0010', 'TO0001', 'TG1353', 'ICT2113', 1, 'ABSENT', '2025-1-20', 'THEORY'),
       ('AT0011', 'TO0001', 'TG1354', 'ICT2113', 1, 'PRESENT', '2025-1-20', 'THEORY'),
       ('AT0012', 'TO0001', 'TG1355', 'ICT2113', 1, 'ABSENT', '2025-1-20', 'THEORY'),
       ('AT0013', 'TO0001', 'TG1356', 'ICT2113', 1, 'ABSENT', '2025-1-20', 'THEORY'),
       ('AT0014', 'TO0001', 'TG1357', 'ICT2113', 1, 'PRESENT', '2025-1-20', 'THEORY'),
       ('AT0015', 'TO0001', 'TG1358', 'ICT2113', 1, 'ABSENT', '2025-1-20', 'THEORY'),
       ('AT0016', 'TO0001', 'TG1359', 'ICT2113', 1, 'PRESENT', '2025-1-20', 'THEORY'),
       ('AT0017', 'TO0001', 'TG1361', 'ICT2113', 1, 'ABSENT', '2025-1-20', 'THEORY'),
       ('AT0018', 'TO0001', 'TG1362', 'ICT2113', 1, 'PRESENT', '2025-1-20', 'THEORY'),
       ('AT0019', 'TO0001', 'TG1363', 'ICT2113', 1, 'ABSENT', '2025-1-20', 'THEORY'),
       ('AT0020', 'TO0001', 'TG1344', 'ICT2113', 1, 'PRESENT', '2025-1-20', 'PRACTICAL'),
       ('AT0021', 'TO0001', 'TG1345', 'ICT2113', 1, 'ABSENT', '2025-1-20', 'PRACTICAL'),
       ('AT0022', 'TO0001', 'TG1346', 'ICT2113', 1, 'MC', '2025-1-20', 'PRACTICAL'),
       ('AT0023', 'TO0001', 'TG1347', 'ICT2113', 1, 'PRESENT', '2025-1-20', 'PRACTICAL'),
       ('AT0024', 'TO0001', 'TG1348', 'ICT2113', 1, 'ABSENT', '2025-1-20', 'PRACTICAL'),
       ('AT0025', 'TO0001', 'TG1349', 'ICT2113', 1, 'MC', '2025-1-20', 'PRACTICAL'),
       ('AT0026', 'TO0001', 'TG1350', 'ICT2113', 1, 'PRESENT', '2025-1-20', 'PRACTICAL'),
       ('AT0027', 'TO0001', 'TG1351', 'ICT2113', 1, 'ABSENT', '2025-1-20', 'PRACTICAL'),
       ('AT0028', 'TO0001', 'TG1352', 'ICT2113', 1, 'PRESENT', '2025-1-20', 'PRACTICAL'),
       ('AT0029', 'TO0001', 'TG1353', 'ICT2113', 1, 'ABSENT', '2025-1-20', 'PRACTICAL'),
       ('AT0030', 'TO0001', 'TG1354', 'ICT2113', 1, 'PRESENT', '2025-1-20', 'PRACTICAL'),
       ('AT0031', 'TO0001', 'TG1355', 'ICT2113', 1, 'ABSENT', '2025-1-20', 'PRACTICAL'),
       ('AT0032', 'TO0001', 'TG1356', 'ICT2113', 1, 'PRESENT', '2025-1-20', 'PRACTICAL'),
       ('AT0033', 'TO0001', 'TG1357', 'ICT2113', 1, 'ABSENT', '2025-1-20', 'PRACTICAL'),
       ('AT0034', 'TO0001', 'TG1358', 'ICT2113', 1, 'PRESENT', '2025-1-20', 'PRACTICAL'),
       ('AT0035', 'TO0001', 'TG1359', 'ICT2113', 1, 'ABSENT', '2025-1-20', 'PRACTICAL'),
       ('AT0036', 'TO0001', 'TG1361', 'ICT2113', 1, 'PRESENT', '2025-1-20', 'PRACTICAL'),
       ('AT0037', 'TO0001', 'TG1362', 'ICT2113', 1, 'ABSENT', '2025-1-20', 'PRACTICAL'),
       ('AT0038', 'TO0001', 'TG1363', 'ICT2113', 1, 'PRESENT', '2025-1-20', 'PRACTICAL'),

       ('AT0039', 'TO0002', 'TG1344', 'ICT2122', 1, 'ABSENT', '2025-1-21', 'THEORY'),
       ('AT0040', 'TO0002', 'TG1345', 'ICT2122', 1, 'PRESENT', '2025-1-21', 'THEORY'),
       ('AT0041', 'TO0002', 'TG1346', 'ICT2122', 1, 'ABSENT', '2025-1-21', 'THEORY'),
       ('AT0042', 'TO0002', 'TG1347', 'ICT2122', 1, 'PRESENT', '2025-1-21', 'THEORY'),
       ('AT0043', 'TO0002', 'TG1348', 'ICT2122', 1, 'ABSENT', '2025-1-21', 'THEORY'),
       ('AT0044', 'TO0002', 'TG1349', 'ICT2122', 1, 'PRESENT', '2025-1-21', 'THEORY'),
       ('AT0045', 'TO0002', 'TG1350', 'ICT2122', 1, 'ABSENT', '2025-1-21', 'THEORY'),
       ('AT0046', 'TO0002', 'TG1351', 'ICT2122', 1, 'PRESENT', '2025-1-21', 'THEORY'),
       ('AT0047', 'TO0002', 'TG1352', 'ICT2122', 1, 'ABSENT', '2025-1-21', 'THEORY'),
       ('AT0048', 'TO0002', 'TG1353', 'ICT2122', 1, 'ABSENT', '2025-1-21', 'THEORY'),
       ('AT0049', 'TO0002', 'TG1354', 'ICT2122', 1, 'PRESENT', '2025-1-21', 'THEORY'),
       ('AT0050', 'TO0002', 'TG1355', 'ICT2122', 1, 'ABSENT', '2025-1-21', 'THEORY'),
       ('AT0051', 'TO0002', 'TG1356', 'ICT2122', 1, 'PRESENT', '2025-1-21', 'THEORY'),
       ('AT0052', 'TO0002', 'TG1357', 'ICT2122', 1, 'ABSENT', '2025-1-21', 'THEORY'),
       ('AT0053', 'TO0002', 'TG1358', 'ICT2122', 1, 'PRESENT', '2025-1-21', 'THEORY'),
       ('AT0054', 'TO0002', 'TG1359', 'ICT2122', 1, 'ABSENT', '2025-1-21', 'THEORY'),
       ('AT0055', 'TO0002', 'TG1361', 'ICT2122', 1, 'ABSENT', '2025-1-21', 'THEORY'),
       ('AT0056', 'TO0002', 'TG1362', 'ICT2122', 1, 'PRESENT', '2025-1-21', 'THEORY'),
       ('AT0057', 'TO0002', 'TG1363', 'ICT2122', 1, 'ABSENT', '2025-1-21', 'THEORY'),

       ('AT0058', 'TO0003', 'TG1344', 'ICT2133', 1, 'ABSENT', '2025-1-22', 'THEORY'),
       ('AT0059', 'TO0003', 'TG1345', 'ICT2133', 1, 'ABSENT', '2025-1-22', 'THEORY'),
       ('AT0060', 'TO0003', 'TG1346', 'ICT2133', 1, 'PRESENT', '2025-1-22', 'THEORY'),
       ('AT0061', 'TO0003', 'TG1347', 'ICT2133', 1, 'ABSENT', '2025-1-22', 'THEORY'),
       ('AT0062', 'TO0003', 'TG1348', 'ICT2133', 1, 'PRESENT', '2025-1-22', 'THEORY'),
       ('AT0063', 'TO0003', 'TG1349', 'ICT2133', 1, 'ABSENT', '2025-1-22', 'THEORY'),
       ('AT0064', 'TO0003', 'TG1350', 'ICT2133', 1, 'PRESENT', '2025-1-22', 'THEORY'),
       ('AT0065', 'TO0003', 'TG1351', 'ICT2133', 1, 'PRESENT', '2025-1-22', 'THEORY'),
       ('AT0066', 'TO0003', 'TG1352', 'ICT2133', 1, 'MC', '2025-1-22', 'THEORY'),
       ('AT0067', 'TO0003', 'TG1353', 'ICT2133', 1, 'ABSENT', '2025-1-22', 'THEORY'),
       ('AT0068', 'TO0003', 'TG1354', 'ICT2133', 1, 'PRESENT', '2025-1-22', 'THEORY'),
       ('AT0069', 'TO0003', 'TG1356', 'ICT2133', 1, 'ABSENT', '2025-1-22', 'THEORY'),
       ('AT0070', 'TO0003', 'TG1357', 'ICT2133', 1, 'PRESENT', '2025-1-22', 'THEORY'),
       ('AT0071', 'TO0003', 'TG1358', 'ICT2133', 1, 'ABSENT', '2025-1-22', 'THEORY'),
       ('AT0072', 'TO0003', 'TG1359', 'ICT2133', 1, 'PRESENT', '2025-1-22', 'THEORY'),
       ('AT0073', 'TO0003', 'TG1361', 'ICT2133', 1, 'ABSENT', '2025-1-22', 'THEORY'),
       ('AT0074', 'TO0003', 'TG1362', 'ICT2133', 1, 'PRESENT', '2025-1-22', 'THEORY'),
       ('AT0075', 'TO0003', 'TG1363', 'ICT2133', 1, 'ABSENT', '2025-1-22', 'THEORY'),
       ('AT0076', 'TO0003', 'TG1344', 'ICT2133', 1, 'PRESENT', '2025-1-22', 'PRACTICAL'),
       ('AT0077', 'TO0003', 'TG1345', 'ICT2133', 1, 'ABSENT', '2025-1-22', 'PRACTICAL'),
       ('AT0078', 'TO0003', 'TG1346', 'ICT2133', 1, 'PRESENT', '2025-1-22', 'PRACTICAL'),
       ('AT0079', 'TO0003', 'TG1347', 'ICT2133', 1, 'ABSENT', '2025-1-22', 'PRACTICAL'),
       ('AT0080', 'TO0003', 'TG1348', 'ICT2133', 1, 'PRESENT', '2025-1-22', 'PRACTICAL'),
       ('AT0081', 'TO0003', 'TG1349', 'ICT2133', 1, 'ABSENT', '2025-1-22', 'PRACTICAL'),
       ('AT0082', 'TO0003', 'TG1350', 'ICT2133', 1, 'MC', '2025-1-22', 'PRACTICAL'),
       ('AT0083', 'TO0003', 'TG1351', 'ICT2133', 1, 'PRESENT', '2025-1-22', 'PRACTICAL'),
       ('AT0084', 'TO0003', 'TG1352', 'ICT2133', 1, 'PRESENT', '2025-1-22', 'PRACTICAL'),
       ('AT0085', 'TO0003', 'TG1353', 'ICT2133', 1, 'ABSENT', '2025-1-22', 'PRACTICAL'),
       ('AT0086', 'TO0003', 'TG1354', 'ICT2133', 1, 'PRESENT', '2025-1-22', 'PRACTICAL'),
       ('AT0087', 'TO0003', 'TG1356', 'ICT2133', 1, 'ABSENT', '2025-1-22', 'PRACTICAL'),
       ('AT0088', 'TO0003', 'TG1357', 'ICT2133', 1, 'PRESENT', '2025-1-22', 'PRACTICAL'),
       ('AT0089', 'TO0003', 'TG1358', 'ICT2133', 1, 'ABSENT', '2025-1-22', 'PRACTICAL'),
       ('AT0090', 'TO0003', 'TG1359', 'ICT2133', 1, 'PRESENT', '2025-1-22', 'PRACTICAL'),
       ('AT0091', 'TO0003', 'TG1361', 'ICT2133', 1, 'ABSENT', '2025-1-22', 'PRACTICAL'),
       ('AT0092', 'TO0003', 'TG1362', 'ICT2133', 1, 'PRESENT', '2025-1-22', 'PRACTICAL'),
       ('AT0093', 'TO0003', 'TG1363', 'ICT2133', 1, 'ABSENT', '2025-1-22', 'PRACTICAL'),

       ('AT0094', 'TO0004', 'TG1344', 'ICT2142', 1, 'PRESENT', '2025-1-23', 'PRACTICAL'),
       ('AT0095', 'TO0004', 'TG1345', 'ICT2142', 1, 'ABSENT', '2025-1-23', 'PRACTICAL'),
       ('AT0096', 'TO0004', 'TG1346', 'ICT2142', 1, 'PRESENT', '2025-1-23', 'PRACTICAL'),
       ('AT0097', 'TO0004', 'TG1347', 'ICT2142', 1, 'ABSENT', '2025-1-23', 'PRACTICAL'),
       ('AT0098', 'TO0004', 'TG1348', 'ICT2142', 1, 'PRESENT', '2025-1-23', 'PRACTICAL'),
       ('AT0099', 'TO0004', 'TG1349', 'ICT2142', 1, 'ABSENT', '2025-1-23', 'PRACTICAL'),
       ('AT0100', 'TO0004', 'TG1350', 'ICT2142', 1, 'PRESENT', '2025-1-23', 'PRACTICAL'),
       ('AT0101', 'TO0004', 'TG1351', 'ICT2142', 1, 'ABSENT', '2025-1-23', 'PRACTICAL'),
       ('AT0102', 'TO0004', 'TG1352', 'ICT2142', 1, 'PRESENT', '2025-1-23', 'PRACTICAL'),
       ('AT0103', 'TO0004', 'TG1353', 'ICT2142', 1, 'ABSENT', '2025-1-23', 'PRACTICAL'),
       ('AT0104', 'TO0004', 'TG1354', 'ICT2142', 1, 'PRESENT', '2025-1-23', 'PRACTICAL'),
       ('AT0105', 'TO0004', 'TG1355', 'ICT2142', 1, 'MC', '2025-1-23', 'PRACTICAL'),
       ('AT0106', 'TO0004', 'TG1356', 'ICT2142', 1, 'ABSENT', '2025-1-23', 'PRACTICAL'),
       ('AT0107', 'TO0004', 'TG1357', 'ICT2142', 1, 'PRESENT', '2025-1-23', 'PRACTICAL'),
       ('AT0108', 'TO0004', 'TG1358', 'ICT2142', 1, 'ABSENT', '2025-1-23', 'PRACTICAL'),
       ('AT0109', 'TO0004', 'TG1359', 'ICT2142', 1, 'PRESENT', '2025-1-23', 'PRACTICAL'),
       ('AT0110', 'TO0004', 'TG1361', 'ICT2142', 1, 'ABSENT', '2025-1-23', 'PRACTICAL'),
       ('AT0111', 'TO0004', 'TG1362', 'ICT2142', 1, 'PRESENT', '2025-1-23', 'PRACTICAL'),
       ('AT0112', 'TO0004', 'TG1363', 'ICT2142', 1, 'ABSENT', '2025-1-23', 'PRACTICAL'),

       ('AT0113', 'TO0003', 'TG1344', 'ICT2152', 1, 'PRESENT', '2025-1-24', 'THEORY'),
       ('AT0114', 'TO0003', 'TG1345', 'ICT2152', 1, 'ABSENT', '2025-1-24', 'THEORY'),
       ('AT0115', 'TO0003', 'TG1346', 'ICT2152', 1, 'PRESENT', '2025-1-24', 'THEORY'),
       ('AT0116', 'TO0003', 'TG1347', 'ICT2152', 1, 'ABSENT', '2025-1-24', 'THEORY'),
       ('AT0117', 'TO0003', 'TG1348', 'ICT2152', 1, 'PRESENT', '2025-1-24', 'THEORY'),
       ('AT0118', 'TO0003', 'TG1349', 'ICT2152', 1, 'ABSENT', '2025-1-24', 'THEORY'),
       ('AT0119', 'TO0003', 'TG1350', 'ICT2152', 1, 'PRESENT', '2025-1-24', 'THEORY'),
       ('AT0120', 'TO0003', 'TG1351', 'ICT2152', 1, 'ABSENT', '2025-1-24', 'THEORY'),
       ('AT0121', 'TO0003', 'TG1352', 'ICT2152', 1, 'PRESENT', '2025-1-24', 'THEORY'),
       ('AT0122', 'TO0003', 'TG1353', 'ICT2152', 1, 'ABSENT', '2025-1-24', 'THEORY'),
       ('AT0123', 'TO0003', 'TG1354', 'ICT2152', 1, 'PRESENT', '2025-1-24', 'THEORY'),
       ('AT0124', 'TO0003', 'TG1355', 'ICT2152', 1, 'ABSENT', '2025-1-24', 'THEORY'),
       ('AT0125', 'TO0003', 'TG1356', 'ICT2152', 1, 'PRESENT', '2025-1-24', 'THEORY'),
       ('AT0126', 'TO0003', 'TG1357', 'ICT2152', 1, 'ABSENT', '2025-1-24', 'THEORY'),
       ('AT0127', 'TO0003', 'TG1358', 'ICT2152', 1, 'ABSENT', '2025-1-24', 'THEORY'),
       ('AT0128', 'TO0003', 'TG1359', 'ICT2152', 1, 'PRESENT', '2025-1-24', 'THEORY'),
       ('AT0129', 'TO0003', 'TG1361', 'ICT2152', 1, 'ABSENT', '2025-1-24', 'THEORY'),
       ('AT0130', 'TO0003', 'TG1362', 'ICT2152', 1, 'PRESENT', '2025-1-24', 'THEORY'),
       ('AT0131', 'TO0003', 'TG1363', 'ICT2152', 1, 'ABSENT', '2025-1-24', 'THEORY');
