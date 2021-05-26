package ua.com.foxminded.sqljdbcschool.dao.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import ua.com.foxminded.sqljdbcschool.dao.ConnectionFactory;
import ua.com.foxminded.sqljdbcschool.dao.SqlScriptRunner;
import ua.com.foxminded.sqljdbcschool.entity.Student;
import ua.com.foxminded.sqljdbcschool.exception.DAOException;

class StudentDaoImplTest {
    private static final String FILENAME_STARTUP_SCRIPT = "create_all_tables_and_fill_test_data.sql";
    private static final String FILENAME_FINISH_SCRIPT = "drop_all_tables.sql";
    private static final String TEST_FIRST_NAME = "TestFirstName";
    private static final String TEST_LAST_NAME = "TestLastName";
    private static final String COURSE_ID1_NAME = "math";
    private static final String COURSE_ID2_NAME = "biology";

    private StudentDaoImpl studentDao;
    private Student studentId1;
    private Student studentId2;

    @BeforeEach
    void setUp() throws Exception {
        studentDao = new StudentDaoImpl();

        try (Connection connection = ConnectionFactory.getConnection()) {
            SqlScriptRunner scriptRunner = new SqlScriptRunner(connection);
            scriptRunner.runSqlScript(FILENAME_STARTUP_SCRIPT);
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }
    

    @AfterEach
    void tearDown() throws Exception {
        try (Connection connection = ConnectionFactory.getConnection()) {
            SqlScriptRunner scriptRunner = new SqlScriptRunner(connection);
            scriptRunner.runSqlScript(FILENAME_FINISH_SCRIPT);
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    @Nested
    @DisplayName("test 'add' method")
    class testAdd {

        private static final int EXPECTED_STUDENT_ID = 3;

        @Test
        @DisplayName("add student should create new record in testDB with id=3")
        void testAddStudent() throws DAOException {
            Student student = new Student(EXPECTED_STUDENT_ID, TEST_FIRST_NAME, TEST_LAST_NAME);
            studentDao.add(student);
            assertEquals(student, studentDao.getById(EXPECTED_STUDENT_ID).get());
        }
    }

    @Nested
    @DisplayName("test 'getById' method")
    class testGetById {

        @Test
        @DisplayName("get student by id=1 should return '1, 1, Wilmette Sambles' student")
        void testGetStudentById() throws DAOException {
            Student actualStudent = studentDao.getById(1).get();
            assertEquals(studentId1, actualStudent);
        }

        @Test
        @DisplayName("get student by non-existing id=6 should return empty Optional")
        void testGetStudentByNonExistingId() throws DAOException {
            Optional<Student> expected = Optional.empty();
            Optional<Student> actual = studentDao.getById(6);
            assertEquals(expected, actual);
        }
    }

    @Nested
    @DisplayName("test 'getAll' method")
    class testGetAll {

        @Test
        @DisplayName("get all students from base should return all 2 students")
        void testGetAllStudents() throws DAOException {
            List<Student> expectedStudents = getStudentTestData();
            assertEquals(expectedStudents, studentDao.getAll());
        }

        List<Student> getStudentTestData () {
            List<Student> result = new ArrayList<>();
            studentId1 = new Student(1, 1, "Wilmette", "Sambles");
            studentId2 = new Student(2, 3, "Kalinda", "Reicharz");
            result.add(studentId1);
            result.add(studentId2);
            return result;
        }
    }

    @Nested
    @DisplayName("test 'update' method")
    class testUpdate {
        @Test
        @DisplayName("update names student id=1 with groupId should write new names and getById(1) return new names")
        void testUpdateStudent1WithGroupId() throws DAOException {
            Student newStudent = new Student(1, 1, TEST_FIRST_NAME,
                    TEST_LAST_NAME);
            studentDao.update(newStudent);
            assertEquals(newStudent, studentDao.getById(1).get());
        }
        

        @Test
        @DisplayName("update names student id=1 without groupId should write new names and getById(1) return new names")
        void testUpdateStudent1WithoutGroupId() throws DAOException {
            Student newStudent = new Student(1, TEST_FIRST_NAME,
                    TEST_LAST_NAME);
            studentDao.update(newStudent);
            assertEquals(newStudent, studentDao.getById(1).get());
        }
    }

    @Nested
    @DisplayName("test 'delete' method")
    class testDelete {
        @Test
        @DisplayName("delete student id=1 should delete student and getById(1) return empty Optional")
        void testDeleteCourseById() throws DAOException {
            studentDao.delete(studentId1);
            Optional<Student> expected = Optional.empty();
            Optional<Student> actual = studentDao.getById(1);
            assertEquals(expected, actual);
        }
    }

    @Nested
    @DisplayName("test 'addStudentCourse' method")
    class testAddStudentCourse {
        private static final int STUDENT_ID = 2;

        @Test
        @DisplayName("add student id=3 to course id=1 should create new record")
        void testCreateTable() throws DAOException {
            studentDao.addStudentCourse(STUDENT_ID, 1);
            List<Student> students = studentDao
                    .getStudentsWithCourseName(COURSE_ID1_NAME);
            Student actualStudent = students.stream()
                    .filter(s -> s.getStudentId() == STUDENT_ID).findAny()
                    .orElse(null);
            assertEquals(studentId2, actualStudent);
        }
    }

    @Nested
    @DisplayName("test 'removeStudentFromCourse' method")
    class testRemoveStudentFromCourse {
        private static final int STUDENT_ID = 2;

        @Test
        @DisplayName("remove student id=2 from course id=2 should remove record from table students_courses")
        void testRemoveStudentId2FromCourseId2() throws DAOException {
            studentDao.removeStudentFromCourse(STUDENT_ID, 2);
            List<Student> students = studentDao
                    .getStudentsWithCourseName(COURSE_ID2_NAME);
            Student actualStudent = students.stream()
                    .filter(s -> s.getStudentId() == STUDENT_ID).findAny()
                    .orElse(null);
            assertNull(actualStudent);
        }
    }

    @Nested
    @DisplayName("test 'getStudentsWithCourseName' method")
    class testGetStudentsWithCourseName {
        @Test
        @DisplayName("get students from course 'math' should return student id=1")
        void testGetStudentsWithCourseMath() throws DAOException {
            List<Student> students = new ArrayList<>();
            students.add(studentId1);

            assertEquals(students,
                    studentDao.getStudentsWithCourseName(COURSE_ID1_NAME));
        }
    }

}
