package ua.com.foxminded.sqljdbcschool.dao;

import java.sql.*;
import java.util.Properties;

import ua.com.foxminded.sqljdbcschool.exception.DAOException;
import ua.com.foxminded.sqljdbcschool.reader.Reader;

@SuppressWarnings("java:S106")
public class StartUpDao {
    private static final String DELIMITER_QUERIES = ";";
    private static final String PROPERTY_SQL_DELETE_TABLES = "tables.delete.ifexist";
    private static final String FILENAME_SQL_QUERY_PROPERTIES = "sql_query.properties";
    private static final String MESSAGE_EXCEPTION = "Can't run script";
    private static final String FILENAME_SCRIPT_CREATE_TABLES = "create_all_tables.sql";
    private static final String MESSAGE_TABLES_READY = "Tables is ready";

    public void prepareTables() throws DAOException {
        deleteTables();
        createTables(FILENAME_SCRIPT_CREATE_TABLES);
        System.out.println(MESSAGE_TABLES_READY);
    }

    private void deleteTables() throws DAOException {
        Reader reader = new Reader();
        Properties properties = reader
                .readProperties(FILENAME_SQL_QUERY_PROPERTIES);

        String sqlCommands = properties.getProperty(PROPERTY_SQL_DELETE_TABLES);
        String[] sql = sqlCommands.split(DELIMITER_QUERIES);

        try (Connection connection = DaoUtils.getConnection()) {
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

        try (Connection connection = DaoUtils.getConnection()) {

            SqlScriptRunner scriptRunner = new SqlScriptRunner(connection);
            scriptRunner.runSqlScript(scriptFilename);

        } catch (SQLException e) {
            throw new DAOException(MESSAGE_EXCEPTION, e);
        }
    }
}
