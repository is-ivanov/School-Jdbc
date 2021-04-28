package ua.com.foxminded.sqljdbcschool.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import ua.com.foxminded.sqljdbcschool.entity.Course;
import ua.com.foxminded.sqljdbcschool.exception.DAOException;
import ua.com.foxminded.sqljdbcschool.reader.Reader;

public class CourseDao implements Dao<Course> {
    private static final String FILENAME_SQL_QUERY = "sql_query.properties";
    private static final String PROPERTY_COURSE_ADD = "course.add";
    private static final String PROPERTY_COURSE_GET_BY_ID = "course.getById";
    private static final String PROPERTY_COURSE_GET_ALL = "course.getAll";
    private static final String PROPERTY_COURSE_UPDATE = "course.update";
    private static final String PROPERTY_COURSE_DELETE = "course.delete";
    private static final String FIELD_COURSE_ID = "course_id";
    private static final String FIELD_COURSE_NAME = "course_name";
    private static final String FIELD_COURSE_DESCRIPTION = "course_desription";
    private static final String MESSAGE_EXCEPTION_ADD = "Can't add course";
    private static final String MESSAGE_EXCEPTION_GET_BY_ID = "Can't get course by ID = ";
    private static final String MESSAGE_EXCEPTION_GET_ALL = "Can't get courses";
    private static final String MESSAGE_EXCEPTION_UPDATE = "Can't update course ";
    private static final String MESSAGE_EXCEPTION_DELETE = "Can't delete course ";

    private DaoUtils daoUtil = new DaoUtils();
    private Reader reader = new Reader();
    Properties sqlProp = reader.readProperties(FILENAME_SQL_QUERY);

    @Override
    public void add(Course course) throws DAOException {
        String sql = sqlProp.getProperty(PROPERTY_COURSE_ADD);
        try (Connection connection = daoUtil.getConnection()) {

            try (PreparedStatement statement = connection
                    .prepareStatement(sql)) {
                statement.setString(1, course.getCourseName());
                statement.setString(2, course.getCourseDescription());

                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DAOException(MESSAGE_EXCEPTION_ADD, e);
        }
    }

    @Override
    public Optional<Course> getById(int courseId) throws DAOException {
        String sql = sqlProp.getProperty(PROPERTY_COURSE_GET_BY_ID);
        Course course = null;
        try (Connection connection = daoUtil.getConnection();
                PreparedStatement statement = connection
                        .prepareStatement(sql)) {
            statement.setInt(1, courseId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    course = new Course();
                    course.setCourseId(courseId);
                    course.setCourseName(
                            resultSet.getString(FIELD_COURSE_NAME));
                    course.setCourseDescription(
                            resultSet.getString(FIELD_COURSE_DESCRIPTION));
                }
            }
        } catch (SQLException e) {
            throw new DAOException(MESSAGE_EXCEPTION_GET_BY_ID + courseId, e);
        }
        return Optional.ofNullable(course);
    }

    @Override
    public List<Course> getAll() throws DAOException {
        String sql = sqlProp.getProperty(PROPERTY_COURSE_GET_ALL);
        List<Course> courses = new ArrayList<>();
        try (Connection connection = daoUtil.getConnection();
                Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(sql)) {
                while (resultSet.next()) {
                    Course course = new Course();
                    course.setCourseId(resultSet.getInt(FIELD_COURSE_ID));
                    course.setCourseName(
                            resultSet.getString(FIELD_COURSE_NAME));
                    course.setCourseDescription(
                            resultSet.getString(FIELD_COURSE_DESCRIPTION));

                    courses.add(course);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(MESSAGE_EXCEPTION_GET_ALL, e);
        }
        return courses;
    }

    @Override
    public void update(Course course) throws DAOException {
        String sql = sqlProp.getProperty(PROPERTY_COURSE_UPDATE);
        try (Connection connection = daoUtil.getConnection();
                PreparedStatement statement = connection
                        .prepareStatement(sql)) {
            statement.setString(1, course.getCourseName());
            statement.setString(2, course.getCourseDescription());
            statement.setInt(3, course.getCourseId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(
                    MESSAGE_EXCEPTION_UPDATE + course.getCourseId(), e);
        }
    }

    @Override
    public void delete(Course course) throws DAOException {
        String sql = sqlProp.getProperty(PROPERTY_COURSE_DELETE);
        try (Connection connection = daoUtil.getConnection();
                PreparedStatement statement = connection
                        .prepareStatement(sql)) {
            statement.setInt(1, course.getCourseId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(
                    MESSAGE_EXCEPTION_DELETE + course.getCourseId(), e);
        }
    }

}
