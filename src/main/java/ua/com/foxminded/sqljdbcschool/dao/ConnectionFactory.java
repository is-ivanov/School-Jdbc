package ua.com.foxminded.sqljdbcschool.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import ua.com.foxminded.sqljdbcschool.exception.DAOException;
import ua.com.foxminded.sqljdbcschool.reader.Reader;

public class ConnectionFactory {
    private static final String FILENAME_DB_PROPERTIES = "db.properties";
    private static final String DB_DRIVER = "db.driver";
    private static final String DB_PASSWORD = "db.password";
    private static final String DB_LOGIN = "db.login";
    private static final String DB_URL = "db.url";
    private static final String MESSAGE_DRIVER_NOT_FOUND = "Driver not found";
    private static final String MESSAGE_CONNECTION_ERROR = "Connection failed";
    
    private ConnectionFactory() {
        
    }

    public static Connection getConnection() throws DAOException {
        Connection connection = null;
        Reader reader = new Reader();
        Properties properties = reader.readProperties(FILENAME_DB_PROPERTIES);

        try {
            Class.forName(properties.getProperty(DB_DRIVER));
        } catch (ClassNotFoundException e) {
            throw new DAOException(MESSAGE_DRIVER_NOT_FOUND, e);
        }

        try {
            connection = DriverManager.getConnection(
                    properties.getProperty(DB_URL),
                    properties.getProperty(DB_LOGIN),
                    properties.getProperty(DB_PASSWORD));
        } catch (SQLException e) {
            throw new DAOException(MESSAGE_CONNECTION_ERROR, e);
        }
        return connection;

    }

}
