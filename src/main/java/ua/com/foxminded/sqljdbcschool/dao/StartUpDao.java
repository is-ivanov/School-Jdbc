package ua.com.foxminded.sqljdbcschool.dao;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.*;

import org.apache.ibatis.jdbc.ScriptRunner;

public class StartUpDao {

    private static final String MESSAGE_SCRIPT = "Script \"";
    private static final String MESSAGE_NOT_FOUND = "\" not found!!";

    private DaoUtils daoUtil = new DaoUtils();

    public void deleteTables() throws DAOException {
        String sqlCommands = "DROP TABLE IF EXISTS groups CASCADE; "
                + "DROP TABLE IF EXISTS students CASCADE; "
                + "DROP TABLE IF EXISTS courses CASCADE; "
                + "DROP TABLE IF EXISTS students_courses CASCADE";
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

    public void createTables(String scriptFileName) throws DAOException {
        DaoUtils daoUtils = new DaoUtils();

        try (Connection connection = daoUtils.getConnection()) {

            ScriptRunner scriptRunner = null;

            scriptRunner = new ScriptRunner(connection);
            try (InputStream inputStream = getClass().getClassLoader()
                    .getResourceAsStream(scriptFileName);
                    InputStreamReader reader = new InputStreamReader(
                            inputStream);) {
                scriptRunner.runScript(reader);
            } catch (IOException | NullPointerException e) {
                throw new DAOException(
                        MESSAGE_SCRIPT + scriptFileName + MESSAGE_NOT_FOUND, e);
            }

        } catch (SQLException e) {
            throw new DAOException("Can't run script", e);
        }
    }
}
