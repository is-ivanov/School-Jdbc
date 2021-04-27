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

import ua.com.foxminded.sqljdbcschool.entity.Student;
import ua.com.foxminded.sqljdbcschool.exception.DAOException;

public class StudentDao implements Dao<Student> {
    private static final String FIELD_STUDENT_ID = "student_id";
    private static final String FIELD_GROUP_ID = "group_id";
    private static final String FIELD_FIRST_NAME = "first_name";
    private static final String FIELD_LAST_NAME = "last_name";

    private DaoUtils daoUtil = new DaoUtils();

    @Override
    public void add(Student student) throws DAOException {
        String sql = "INSERT INTO students(group_id, first_name, last_name)  VALUES (?, ?, ?)";
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
            throw new DAOException("Can't add student", e);
        }

    }

    @Override
    public Optional<Student> getById(int studentId) throws DAOException {
        String sql = "SELECT student_id, group_id, first_name, last_name FROM students WHERE student_id = ?";
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
            throw new DAOException("Can't get student by ID = " + studentId, e);
        }
        return Optional.ofNullable(student);
    }

    @Override
    public List<Student> getAll() throws DAOException {
        String sql = "SELECT student_id, group_id, first_name, last_name FROM students";
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
            throw new DAOException("Can't get students", e);
        }
        return students;
    }

    @Override
    public void update(Student student) throws DAOException {
        String sql = "UPDATE students SET group_id = ?, first_name = ?, last_name = ? WHERE student_id = ?";
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
                    "Can't update student " + student.getStudentId(), e);
        }
    }

    @Override
    public void delete(Student student) throws DAOException {
        String sql = "DELETE FROM students WHERE student_id = ?";
        try (Connection connection = daoUtil.getConnection();
                PreparedStatement statement = connection
                        .prepareStatement(sql)) {
            statement.setInt(1, student.getStudentId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(
                    "Can't delete student " + student.getStudentId(), e);
        }
    }

}
