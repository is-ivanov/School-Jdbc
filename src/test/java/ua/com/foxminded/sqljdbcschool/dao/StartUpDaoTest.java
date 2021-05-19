package ua.com.foxminded.sqljdbcschool.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ua.com.foxminded.sqljdbcschool.dao.impl.CourseDaoImpl;
import ua.com.foxminded.sqljdbcschool.dao.impl.GroupDaoImpl;
import ua.com.foxminded.sqljdbcschool.dao.impl.StudentDaoImpl;
import ua.com.foxminded.sqljdbcschool.entity.Course;
import ua.com.foxminded.sqljdbcschool.entity.Group;
import ua.com.foxminded.sqljdbcschool.entity.Student;
import ua.com.foxminded.sqljdbcschool.exception.DAOException;

class StartUpDaoTest {
    private static final String FILENAME_STARTUP_SCRIPT = "create_all_tables_and_fill_test_data.sql";
    private static final String FILENAME_FINISH_SCRIPT = "drop_all_tables.sql";
    
    private StartUpDao startUpDao;
    @BeforeEach
    void setUp() throws Exception {
        startUpDao = new StartUpDao();
        
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

    @Test
    @DisplayName("test prepareTables should create empty table 'groups'")
    void testPrepareTablesEmptyTableGroups() throws DAOException {
        startUpDao.prepareTables();
        
        List<Group> expectedGroups = new ArrayList<>();
        GroupDaoImpl groupDao = new GroupDaoImpl();
        List<Group> actualGroups = groupDao.getAll();
        
        assertEquals(expectedGroups, actualGroups);
    }
    
    @Test
    @DisplayName("test prepareTables should create empty table 'students'")
    void testPrepareTablesEmptyTableStudents() throws DAOException {
        startUpDao.prepareTables();
        
        List<Student> expectedStudents = new ArrayList<>();
        StudentDaoImpl studentDao = new StudentDaoImpl();
        List<Student> actualStudents = studentDao.getAll();
        
        assertEquals(expectedStudents, actualStudents);
    }
    
    @Test
    @DisplayName("test prepareTables should create empty table 'courses'")
    void testPrepareTablesEmptyTableCourses() throws DAOException {
        startUpDao.prepareTables();
        
        List<Course> expectedCourses = new ArrayList<>();
        CourseDaoImpl courseDao = new CourseDaoImpl();
        List<Course> actualCourses = courseDao.getAll();
        
        assertEquals(expectedCourses, actualCourses);
    }

}
