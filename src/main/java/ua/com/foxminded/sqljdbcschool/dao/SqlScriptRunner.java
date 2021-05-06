package ua.com.foxminded.sqljdbcschool.dao;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;

import org.apache.ibatis.jdbc.ScriptRunner;

import ua.com.foxminded.sqljdbcschool.exception.DAOException;

public class SqlScriptRunner {

    private static final String MESSAGE_EXCEPTION = "Script %s not found!!";

    private final Connection connection;

    public SqlScriptRunner(Connection connection) {
        this.connection = connection;
    }

    public void runSqlScript(String scriptFilename) throws DAOException {
        ScriptRunner scriptRunner = new ScriptRunner(connection);
        try (InputStream inputStream = getClass().getClassLoader()
                .getResourceAsStream(scriptFilename);
                InputStreamReader reader = new InputStreamReader(
                        inputStream);) {
            scriptRunner.runScript(reader);
        } catch (IOException | NullPointerException e) {
            throw new DAOException(
                    String.format(MESSAGE_EXCEPTION, scriptFilename), e);
        }
    }
}
