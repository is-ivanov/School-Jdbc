package ua.com.foxminded.sqljdbcschool.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    private GroupDao groupDao;

    @BeforeEach
    void setUp() throws Exception {
        groupDao = new GroupDao();
        try (Connection connection = DaoUtils.getConnection()) {
            SqlScriptRunner scriptRunner = new SqlScriptRunner(connection);
            scriptRunner
                    .runSqlScript("create_all_tables_and_fill_test_data.sql");
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }
    
    @AfterEach
    void cleanData() throws Exception {
        try (Connection connection = DaoUtils.getConnection()) {
            SqlScriptRunner scriptRunner = new SqlScriptRunner(connection);
            scriptRunner
                    .runSqlScript("drop_all_tables.sql");
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    @Nested
    @DisplayName("test 'add method'")
    class testAdd{
        @Test
        @DisplayName("add group should create new record in DB with id=6")
        void testAddGroup() throws DAOException {
            Group group = new Group("TestGroup");
            groupDao.add(group);
            String expectedGroupName = group.getGroupName();
            String actualGroupName = groupDao.getById(6).get().getGroupName();
            assertEquals(expectedGroupName, actualGroupName);
        }
        
//        @Test
//        @DisplayName("add ")
//        void test1() {
//            
//        }
        
    }

}
