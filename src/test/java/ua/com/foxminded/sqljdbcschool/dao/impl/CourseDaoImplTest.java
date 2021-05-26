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
import ua.com.foxminded.sqljdbcschool.entity.Course;
import ua.com.foxminded.sqljdbcschool.exception.DAOException;

class CourseDaoImplTest {
    private static final String FILENAME_STARTUP_SCRIPT = "create_all_tables_and_fill_test_data.sql";
    private static final String FILENAME_FINISH_SCRIPT = "drop_all_tables.sql";
    private static final String TEST_COURSE_NAME = "TestCourse";
    private static final String TEST_COURSE_DESCRIPTION = "testCourse description";
    private static final String COURSE_NAME_ID1 = "math";
    private static final String COURSE_NAME_ID2 = "biology";
    private static final String COURSE_DESCRIPTION_ID1 = "course of Mathematics";
    private static final String COURSE_DESCRIPTION_ID2 = "course of Biology";

    private CourseDaoImpl courseDao;
    private Course courseId1;
    private Course courseId2;

    @BeforeEach
    void setUp() throws Exception {
        courseDao = new CourseDaoImpl();
        
        courseId1 = new Course(1, COURSE_NAME_ID1, COURSE_DESCRIPTION_ID1);
        courseId2 = new Course(2, COURSE_NAME_ID2, COURSE_DESCRIPTION_ID2);
        
        
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

        @Test
        @DisplayName("add course should create new record in testDB with id=3")
        void testAddCourse() throws DAOException {
            Course course = new Course(TEST_COURSE_NAME,
                    TEST_COURSE_DESCRIPTION);
            courseDao.add(course);
            String expectedCourseName = TEST_COURSE_NAME;
            String actualCourseName = courseDao.getById(3).get()
                    .getCourseName();
            assertEquals(expectedCourseName, actualCourseName);
        }

    }

    @Nested
    @DisplayName("test 'getById' method")
    class testGetById {

        @Test
        @DisplayName("get course by id=1 should return 'math' course")
        void testGetCourseById() throws DAOException {
            String expectedCourseName = COURSE_NAME_ID1;
            String actualCourseName = courseDao.getById(1).get()
                    .getCourseName();
            assertEquals(expectedCourseName, actualCourseName);
        }

        @Test
        @DisplayName("get course by non-existing id=6 should return empty Optional")
        void testGetCourseByNonExistingId() throws DAOException {
            Optional<Course> expected = Optional.empty();
            Optional<Course> actual = courseDao.getById(6);
            assertEquals(expected, actual);
        }

    }

    @Nested
    @DisplayName("test 'getAll' method")
    class testGetAll {

        @Test
        @DisplayName("get all courses from base should return all courses")
        void testGetAllGroups() throws DAOException {
            List<Course> expectedCourses = new ArrayList<>();
            expectedCourses.add(courseId1);
            expectedCourses.add(courseId2);

            assertEquals(expectedCourses, courseDao.getAll());
        }
    }

    @Nested
    @DisplayName("test 'update' method")
    class testUpdate {
        @Test
        @DisplayName("update name course id=1 should write new name and getById(1) return new name")
        void testUpdateCourseName() throws DAOException {
            Course newCourse = new Course(1, TEST_COURSE_NAME,
                    TEST_COURSE_DESCRIPTION);
            courseDao.update(newCourse);
            assertEquals(newCourse, courseDao.getById(1).get());
        }
    }

    @Nested
    @DisplayName("test 'delete' method")
    class testDelete {
        @Test
        @DisplayName("delete course id=1 should delete course and getById(1) return empty Optional")
        void testDeleteCourseById() throws DAOException {
            courseDao.delete(courseId1);
            Optional<Course> expected = Optional.empty();
            Optional<Course> actual = courseDao.getById(1);
            assertEquals(expected, actual);
        }
    }

    @Nested
    @DisplayName("test 'getCoursesForStudentId' method")
    class testGetCoursesForStudentId {
        @Test
        @DisplayName("get courses for student_id=2 should return list courses with one course id=2")
        void testGetCoursesForStudent2() throws DAOException {
            List<Course> courses = new ArrayList<>();
            courses.add(courseId2);
            assertEquals(courses, courseDao.getCoursesForStudentId(2));
        }
    }
    
    @Nested
    @DisplayName("test 'getCoursesMissingForStudentId' method")
    class testGetCoursesMissingForStudentId{
        @Test
        @DisplayName("get missing courses for student_id=2 should return list courses with one course id=1")
        void testGetCoursesMissingForStudent1() throws DAOException {
            List<Course> courses = new ArrayList<>();
            courses.add(courseId1);
            assertEquals(courses, courseDao.getCoursesMissingForStudentId(2));
        }
    }

}
