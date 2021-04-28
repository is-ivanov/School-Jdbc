package ua.com.foxminded.sqljdbcschool.dao;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Properties;

import org.apache.ibatis.jdbc.ScriptRunner;

import ua.com.foxminded.sqljdbcschool.exception.DAOException;
import ua.com.foxminded.sqljdbcschool.reader.Reader;

public class StartUpDao {
    private static final String DELIMITER_QUERIES = ";";
    private static final String PROPERTY_SQL_DELETE_TABLES = "tables.delete.ifexist";
    private static final String FILENAME_SQL_QUERY_PROPERTIES = "sql_query.properties";
    private static final String MESSAGE_EXCEPTION = "Can't run script";
    private static final String FORMAT_MASK_MESSAGE_EXCEPTION = "Script %s not found!!";
    private static final String FILENAME_SCRIPT_CREATE_TABLES = "create_all_tables.sql";

    private DaoUtils daoUtil = new DaoUtils();

    
    public void prepareTables() throws DAOException {
        deleteTables();
        createTables(FILENAME_SCRIPT_CREATE_TABLES);
    }
    
    
    private void deleteTables() throws DAOException {
        Reader reader = new Reader();
        Properties properties = reader
                .readProperties(FILENAME_SQL_QUERY_PROPERTIES);

        String sqlCommands = properties.getProperty(PROPERTY_SQL_DELETE_TABLES);
        String[] sql = sqlCommands.split(DELIMITER_QUERIES);

        try (Connection connection = daoUtil.getConnection()) {
            try (Statement statement = connection.createStatement()) {

                connection.setAutoCommit(false);
                for (int i = 0; i < sql.length; i++) {
                    statement.addBatch(sql[i]);
                }
                statement.executeBatch();
                connection.commit();
            }
        } catch (SQLException e) {
            throw new DAOException();
        }
    }
    
    private void createTables(String scriptFilename) throws DAOException {
        DaoUtils daoUtils = new DaoUtils();

        try (Connection connection = daoUtils.getConnection()) {

            ScriptRunner scriptRunner = null;

            scriptRunner = new ScriptRunner(connection);
            try (InputStream inputStream = getClass().getClassLoader()
                    .getResourceAsStream(scriptFilename);
                    InputStreamReader reader = new InputStreamReader(
                            inputStream);) {
                scriptRunner.runScript(reader);
            } catch (IOException | NullPointerException e) {
                throw new DAOException(String.format(
                        FORMAT_MASK_MESSAGE_EXCEPTION, scriptFilename), e);
            }

        } catch (SQLException e) {
            throw new DAOException(MESSAGE_EXCEPTION, e);
        }
    }
}
