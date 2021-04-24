package ua.com.foxminded.sqljdbcschool.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import ua.com.foxminded.sqljdbcschool.entity.Course;

public class CourseDao implements Dao<Course> {
    private static final String COURSE_ID = "course_id";
    private static final String COURSE_NAME = "course_name";
    private static final String COURSE_DESCRIPTION = "course_desription";

    private DaoUtils daoUtil = new DaoUtils();
    

    @Override
    public void add(Course course) throws DAOException {
        String sql = "INSERT INTO courses(course_name, course_description)  VALUES (?, ?)";
        try (Connection connection = daoUtil.getConnection()) {

            try (PreparedStatement statement = connection
                    .prepareStatement(sql)) {
                statement.setString(1, course.getCourseName());
                statement.setString(2, course.getCourseDescription());

                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DAOException("Can't add course", e);
        }
    }

    @Override
    public Optional<Course> get(int courseId) throws DAOException {
        String sql = "SELECT course_id, course_name, course_description FROM courses WHERE course_id = ?";
        Course course = null;
        try (Connection connection = daoUtil.getConnection();
                PreparedStatement statement = connection
                        .prepareStatement(sql)) {
            statement.setInt(1, courseId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    course = new Course();
                    course.setCourseId(courseId);
                    course.setCourseName(resultSet.getString(COURSE_NAME));
                    course.setCourseDescription(resultSet.getString(COURSE_DESCRIPTION));
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Can't get course by ID = " + courseId, e);
        }
        return Optional.ofNullable(course);
    }

    @Override
    public List<Course> getAll() throws DAOException {
        String sql = "SELECT course_id, course_name, course_description FROM courses";
        List<Course> courses = new ArrayList<>();
        try (Connection connection = daoUtil.getConnection();
                Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(sql)) {
                while (resultSet.next()) {
                    Course course = new Course();
                    course.setCourseId(resultSet.getInt(COURSE_ID));
                    course.setCourseName(resultSet.getString(COURSE_NAME));
                    course.setCourseDescription(resultSet.getString(COURSE_DESCRIPTION));

                    courses.add(course);
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Can't get courses", e);
        }
        return courses;
    }

    @Override
    public void update(Course course) throws DAOException {
        String sql = "UPDATE courses SET course_name = ?, course_description = ? WHERE course_id = ?";
        try (Connection connection = daoUtil.getConnection();
                PreparedStatement statement = connection
                        .prepareStatement(sql)) {
            statement.setString(1, course.getCourseName());
            statement.setString(2, course.getCourseDescription());
            statement.setInt(3, course.getCourseId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(
                    "Can't update course " + course.getCourseId(), e);
        }
    }

    @Override
    public void delete(Course course) throws DAOException {
        String sql = "DELETE FROM courses WHERE course_id = ?";
        try (Connection connection = daoUtil.getConnection();
                PreparedStatement statement = connection
                        .prepareStatement(sql)) {
            statement.setInt(1, course.getCourseId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(
                    "Can't delete course " + course.getCourseId(), e);
        }
    }

}
