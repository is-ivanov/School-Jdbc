package ua.com.foxminded.sqljdbcschool.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DaoUtils {
    private static final String DATABASE_URL = "jdbc:postgresql://localhost:5432/school";
    private static final String USER = "karyama";
    private static final String PASSWORD = "1234";
    private static final String MESSAGE_CONNECTION_ERROR = "Can't create connection";

    Connection getConnection() throws DAOException {
        Connection connection = null;
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
            connection = DriverManager.getConnection(DATABASE_URL, USER,
                    PASSWORD);
        } catch (SQLException e) {
            throw new DAOException(MESSAGE_CONNECTION_ERROR, e);
        }
        return connection;

    }
}
