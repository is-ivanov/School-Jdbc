package ua.com.foxminded.sqljdbcschool.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import ua.com.foxminded.sqljdbcschool.exception.DAOException;
import ua.com.foxminded.sqljdbcschool.reader.Reader;

public class DaoUtils {
    private static final String FILENAME_DB_PROPERTIES = "db.properties";
    private static final String MESSAGE_CONNECTION_ERROR = "Can't create connection";

    public Connection getConnection() throws DAOException {
        Connection connection = null;
        Reader reader = new Reader();
        Properties properties = reader.readProperties(FILENAME_DB_PROPERTIES);
        
        
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
            connection = DriverManager.getConnection(properties.getProperty("db_url"),
                    properties.getProperty("login"),
                    properties.getProperty("password"));
        } catch (SQLException e) {
            throw new DAOException(MESSAGE_CONNECTION_ERROR, e);
        }
        return connection;

    }
    

}
