package ua.com.foxminded.sqljdbcschool.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import ua.com.foxminded.sqljdbcschool.entity.Group;
import ua.com.foxminded.sqljdbcschool.exception.DAOException;

class GroupDaoTest {
    private static final String FILENAME_STARTUP_SCRIPT = "create_all_tables_and_fill_test_data.sql";
    private static final String FILENAME_FINISH_SCRIPT = "drop_all_tables.sql";
    private GroupDao groupDao;

    @BeforeEach
    void setUp() throws Exception {
        groupDao = new GroupDao();
        try (Connection connection = DaoUtils.getConnection()) {
            SqlScriptRunner scriptRunner = new SqlScriptRunner(connection);
            scriptRunner.runSqlScript(FILENAME_STARTUP_SCRIPT);
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    @AfterEach
    void cleanData() throws Exception {
        try (Connection connection = DaoUtils.getConnection()) {
            SqlScriptRunner scriptRunner = new SqlScriptRunner(connection);
            scriptRunner.runSqlScript(FILENAME_FINISH_SCRIPT);
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    @Nested
    @DisplayName("test 'add' method")
    class testAdd {
        private static final String GROUP_NAME = "TestGroup";

        @Test
        @DisplayName("add group should create new record in DB with id=6")
        void testAddGroup() throws DAOException {
            Group group = new Group(GROUP_NAME);
            groupDao.add(group);
            String expectedGroupName = group.getGroupName();
            String actualGroupName = groupDao.getById(6).get().getGroupName();
            assertEquals(expectedGroupName, actualGroupName);
        }

    }
    
    @Nested
    @DisplayName("test 'getById' method")
    class testGetById{
        
        private static final String EXPECTED_NAME = "OR-41";

        @Test
        @DisplayName("get group by id=1 should return 'OR-41' group")
        void testGetGroupById() throws DAOException {
            String expectedGroupName = EXPECTED_NAME;
            String actualGroupName = groupDao.getById(1).get().getGroupName();
            assertEquals(expectedGroupName, actualGroupName);
        }
    }

}
