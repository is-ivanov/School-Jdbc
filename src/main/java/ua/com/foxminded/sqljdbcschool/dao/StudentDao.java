package ua.com.foxminded.sqljdbcschool.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import ua.com.foxminded.sqljdbcschool.entity.Student;
import ua.com.foxminded.sqljdbcschool.exception.DAOException;
import ua.com.foxminded.sqljdbcschool.reader.Reader;

public class StudentDao implements Dao<Student> {
    private static final String MESSAGE_EXCEPTION_ADD_STUDENT_COURSE = "Can't add pair student-course";
    private static final String PROPERTY_STUDENT_COURSE_ADD = "student_course.add";
    private static final String MESSAGE_EXCEPTION_CREATE_TABLE = "Can't create student_course table";
    private static final String FILENAME_SQL_QUERY = "sql_query.properties";
    private static final String PROPERTY_STUDENT_ADD = "student.add";
    private static final String PROPERTY_STUDENT_GET_BY_ID = "student.getById";
    private static final String PROPERTY_STUDENT_GET_ALL = "student.getAll";
    private static final String PROPERTY_STUDENT_UPDATE = "student.update";
    private static final String PROPERTY_STUDENT_DELETE = "student.delete";
    private static final String PROPERTY_STUDENT_COURSE_CREATE_TABLE = "student_course.create.table";
    private static final String FIELD_STUDENT_ID = "student_id";
    private static final String FIELD_GROUP_ID = "group_id";
    private static final String FIELD_FIRST_NAME = "first_name";
    private static final String FIELD_LAST_NAME = "last_name";
    private static final String MESSAGE_EXCEPTION_ADD = "Can't add student";
    private static final String MESSAGE_EXCEPTION_GET_BY_ID = "Can't get student by ID = ";
    private static final String MESSAGE_EXCEPTION_GET_ALL = "Can't get students";
    private static final String MESSAGE_EXCEPTION_UPDATE = "Can't update student ";
    private static final String MESSAGE_EXCEPTION_DELETE = "Can't delete student ";

    private DaoUtils daoUtil = new DaoUtils();
    private Reader reader = new Reader();
    private Properties sqlProp = reader.readProperties(FILENAME_SQL_QUERY);

    @Override
    public void add(Student student) throws DAOException {
        String sql = sqlProp.getProperty(PROPERTY_STUDENT_ADD);
        try (Connection connection = daoUtil.getConnection()) {

            try (PreparedStatement statement = connection
                    .prepareStatement(sql)) {
                if (student.getGroupId() != 0) {
                    statement.setInt(1, student.getGroupId());
                } else {
                    statement.setNull(1, Types.INTEGER);
                }
                statement.setString(2, student.getFirstName());
                statement.setString(3, student.getLastName());

                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DAOException(MESSAGE_EXCEPTION_ADD, e);
        }

    }

    @Override
    public Optional<Student> getById(int studentId) throws DAOException {
        String sql = sqlProp.getProperty(PROPERTY_STUDENT_GET_BY_ID);
        Student student = null;
        try (Connection connection = daoUtil.getConnection();
                PreparedStatement statement = connection
                        .prepareStatement(sql)) {
            statement.setInt(1, studentId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    student = new Student();
                    student.setStudentId(studentId);
                    student.setGroupId(resultSet.getInt(FIELD_GROUP_ID));
                    student.setFirstName(resultSet.getString(FIELD_FIRST_NAME));
                    student.setLastName(resultSet.getString(FIELD_LAST_NAME));
                }
            }
        } catch (SQLException e) {
            throw new DAOException(MESSAGE_EXCEPTION_GET_BY_ID + studentId, e);
        }
        return Optional.ofNullable(student);
    }

    @Override
    public List<Student> getAll() throws DAOException {
        String sql = sqlProp.getProperty(PROPERTY_STUDENT_GET_ALL);
        List<Student> students = new ArrayList<>();
        try (Connection connection = daoUtil.getConnection();
                Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(sql)) {
                while (resultSet.next()) {
                    Student student = new Student();
                    student.setStudentId(resultSet.getInt(FIELD_STUDENT_ID));
                    student.setGroupId(resultSet.getInt(FIELD_GROUP_ID));
                    student.setFirstName(resultSet.getString(FIELD_FIRST_NAME));
                    student.setLastName(resultSet.getString(FIELD_LAST_NAME));

                    students.add(student);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(MESSAGE_EXCEPTION_GET_ALL, e);
        }
        return students;
    }

    @Override
    public void update(Student student) throws DAOException {
        String sql = sqlProp.getProperty(PROPERTY_STUDENT_UPDATE);
        try (Connection connection = daoUtil.getConnection();
                PreparedStatement statement = connection
                        .prepareStatement(sql)) {
            statement.setInt(1, student.getGroupId());
            statement.setString(2, student.getFirstName());
            statement.setString(3, student.getLastName());
            statement.setInt(4, student.getStudentId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(
                    MESSAGE_EXCEPTION_UPDATE + student.getStudentId(), e);
        }
    }

    @Override
    public void delete(Student student) throws DAOException {
        String sql = sqlProp.getProperty(PROPERTY_STUDENT_DELETE);
        try (Connection connection = daoUtil.getConnection();
                PreparedStatement statement = connection
                        .prepareStatement(sql)) {
            statement.setInt(1, student.getStudentId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(
                    MESSAGE_EXCEPTION_DELETE + student.getStudentId(), e);
        }
    }

    public void createTableStudentCourse() throws DAOException {
        String sql = sqlProp.getProperty(PROPERTY_STUDENT_COURSE_CREATE_TABLE);
        try (Connection connection = daoUtil.getConnection()) {
            try (PreparedStatement statement = connection
                    .prepareStatement(sql)) {
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DAOException(MESSAGE_EXCEPTION_CREATE_TABLE, e);
        }
    }

    public void addStudentCourse(int student_id, int course_id)
            throws DAOException {
        String sql = sqlProp.getProperty(PROPERTY_STUDENT_COURSE_ADD);
        try (Connection connection = daoUtil.getConnection()) {
            try (PreparedStatement statement = connection
                    .prepareStatement(sql)) {
                statement.setInt(1, student_id);
                statement.setInt(2, course_id);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DAOException(MESSAGE_EXCEPTION_ADD_STUDENT_COURSE, e);
        }

    }

//    public List<Integer> getCoursesByStudentId(int student_id) {
//        List<Integer> courses;
//        
//        return courses;
//    }
//
//    public List<Integer> getstudentsByCourseId(int course_id) {
//
//    }

}
