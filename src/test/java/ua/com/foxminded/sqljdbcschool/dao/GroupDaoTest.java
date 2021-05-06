package ua.com.foxminded.sqljdbcschool.dao;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ua.com.foxminded.sqljdbcschool.exception.DAOException;

class GroupDaoTest {
    private GroupDao groupDao;

    @BeforeEach
    void setUp() throws Exception {
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

        
    @Test
    void test() {

    }

}
