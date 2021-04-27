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
    
    private static final String PROPERTY_SQL_DELETE_TABLES = "tables.delete.ifexist";
    private static final String FILENAME_SQL_QUERY_PROPERTIES = "sql_query.properties";
    private DaoUtils daoUtil = new DaoUtils();

    public void deleteTables() throws DAOException {
        Reader reader = new Reader();
        Properties properties = reader.readProperties(FILENAME_SQL_QUERY_PROPERTIES);
        
        
        String sqlCommands = properties.getProperty(PROPERTY_SQL_DELETE_TABLES);
        String[] sql = sqlCommands.split(";");

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

    public void createTables(String scriptFilename) throws DAOException {
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
                throw new DAOException(
                        String.format("Script %s not found!!", scriptFilename),
                        e);
            }

        } catch (SQLException e) {
            throw new DAOException("Can't run script", e);
        }
    }
}
