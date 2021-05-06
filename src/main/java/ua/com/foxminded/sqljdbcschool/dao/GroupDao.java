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

import ua.com.foxminded.sqljdbcschool.entity.Group;
import ua.com.foxminded.sqljdbcschool.exception.DAOException;
import ua.com.foxminded.sqljdbcschool.reader.Reader;

public class GroupDao implements Dao<Group> {
    private static final String FILENAME_SQL_QUERY = "sql_query.properties";
    private static final String PROPERTY_GROUP_ADD = "group.add";
    private static final String PROPERTY_GROUP_GET_BY_ID = "group.getById";
    private static final String PROPERTY_GROUP_GET_ALL = "group.getAll";
    private static final String PROPERTY_GROUP_UPDATE = "group.update";
    private static final String PROPERTY_GROUP_DELETE = "group.delete";
    private static final String PROPERTY_FIND_GROUPS_LESS_STUDENT_COUNT = "group.find.all.less.student.count";
    private static final String FIELD_GROUP_ID = "group_id";
    private static final String FIELD_GROUP_NAME = "group_name";
    private static final String MESSAGE_EXCEPTION_ADD = "Can't add group";
    private static final String MESSAGE_EXCEPTION_GET_BY_ID = "Can't get group by ID = ";
    private static final String MESSAGE_EXCEPTION_GET_ALL = "Can't get groups";
    private static final String MESSAGE_EXCEPTION_GROUP_UPDATE = "Can't update group ";
    private static final String MESSAGE_EXCEPTION_GROUP_DELETE = "Can't delete group ";

    private Reader reader = new Reader();
    private Properties sqlProp = reader.readProperties(FILENAME_SQL_QUERY);

    @Override
    public void add(Group group) throws DAOException {
        String sql = sqlProp.getProperty(PROPERTY_GROUP_ADD);
        try (Connection connection = DaoUtils.getConnection()) {

            try (PreparedStatement statement = connection
                    .prepareStatement(sql)) {
                statement.setString(1, group.getGroupName());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DAOException(MESSAGE_EXCEPTION_ADD, e);
        }

    }

    @Override
    public Optional<Group> getById(int groupId) throws DAOException {
        String sql = sqlProp.getProperty(PROPERTY_GROUP_GET_BY_ID);
        Group group = null;
        try (Connection connection = DaoUtils.getConnection();
                PreparedStatement statement = connection
                        .prepareStatement(sql)) {
            statement.setInt(1, groupId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    group = new Group(groupId,
                            resultSet.getString(FIELD_GROUP_NAME));
                }
            }
        } catch (SQLException e) {
            throw new DAOException(MESSAGE_EXCEPTION_GET_BY_ID + groupId, e);
        }
        return Optional.ofNullable(group);
    }

    @Override
    public List<Group> getAll() throws DAOException {
        String sql = sqlProp.getProperty(PROPERTY_GROUP_GET_ALL);
        List<Group> groups = new ArrayList<>();
        try (Connection connection = DaoUtils.getConnection();
                Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(sql)) {
                while (resultSet.next()) {
                    Group group = new Group(resultSet.getInt(FIELD_GROUP_ID),
                            resultSet.getString(FIELD_GROUP_NAME));
                    groups.add(group);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(MESSAGE_EXCEPTION_GET_ALL, e);
        }
        return groups;
    }

    @Override
    public void update(Group group) throws DAOException {
        String sql = sqlProp.getProperty(PROPERTY_GROUP_UPDATE);
        try (Connection connection = DaoUtils.getConnection();
                PreparedStatement statement = connection
                        .prepareStatement(sql)) {
            statement.setString(1, group.getGroupName());
            statement.setInt(2, group.getGroupId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(
                    MESSAGE_EXCEPTION_GROUP_UPDATE + group.getGroupId(), e);
        }

    }

    @Override
    public void delete(Group group) throws DAOException {
        String sql = sqlProp.getProperty(PROPERTY_GROUP_DELETE);
        try (Connection connection = DaoUtils.getConnection();
                PreparedStatement statement = connection
                        .prepareStatement(sql)) {
            statement.setInt(1, group.getGroupId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(
                    MESSAGE_EXCEPTION_GROUP_DELETE + group.getGroupId(), e);
        }

    }

    public List<Group> getGroupsWithLessEqualsStudentCount(int studentCount)
            throws DAOException {
        String sql = sqlProp
                .getProperty(PROPERTY_FIND_GROUPS_LESS_STUDENT_COUNT);

        List<Group> groups = new ArrayList<>();
        try (Connection connection = DaoUtils.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, studentCount);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Group group = new Group(resultSet.getInt(FIELD_GROUP_ID),
                            resultSet.getString(FIELD_GROUP_NAME));
                    groups.add(group);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(MESSAGE_EXCEPTION_GET_ALL, e);
        }
        return groups;
    }

}
