package ua.com.foxminded.sqljdbcschool.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import ua.com.foxminded.sqljdbcschool.entity.Group;

public class GroupDao implements Dao<Group> {
    private static final String GROUP_ID = "group_id";
    private static final String GROUP_NAME = "group_name";
    private DaoUtils daoUtil = new DaoUtils();

    @Override
    public void add(Group group) throws DAOException {
        String sql = "INSERT INTO groups(group_name)  VALUES (?)";
        try (Connection connection = daoUtil.getConnection()) {

            try (PreparedStatement statement = connection
                    .prepareStatement(sql)) {
                statement.setString(1, group.getGroupName());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DAOException("Can't add group", e);
        }

    }

    @Override
    public Optional<Group> get(int id) throws DAOException {
        String sql = "SELECT group_name FROM group WHERE group_id = ?";
        Group group = null;
        try (Connection connection = daoUtil.getConnection();
                PreparedStatement statement = connection
                        .prepareStatement(sql)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    group = new Group(id, resultSet.getString(GROUP_NAME));
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Can't get group by ID =" + id, e);
        }
        return Optional.of(group);
    }

    @Override
    public List<Group> getAll() throws DAOException {
        String sql = "SELECT group_id, group_name FROM group";
        List<Group> groups = new ArrayList<>();
        try (Connection connection = daoUtil.getConnection();
                Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(sql)) {
                while (resultSet.next()) {
                    Group group = new Group(resultSet.getInt(GROUP_ID),
                            resultSet.getString(GROUP_NAME));
                    groups.add(group);
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Can't get groups", e);
        }
        return groups;
    }

    @Override
    public void update(Group group) throws DAOException {
        String sql = "UPDATE groups SET group_name = ? WHERE proup_id = ?";
        try (Connection connection = daoUtil.getConnection();
                PreparedStatement statement = connection
                        .prepareStatement(sql)) {
            statement.setString(1, group.getGroupName());
            statement.setInt(2, group.getGroupId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Can't update group " + group.getGroupId(),
                    e);
        }

    }

    @Override
    public void delete(Group group) throws DAOException {
        String sql = "DELETE FROM groups WHERE group_id = ?";
        try (Connection connection = daoUtil.getConnection();
                PreparedStatement statement = connection
                        .prepareStatement(sql)) {
            statement.setInt(1, group.getGroupId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Can't delete group " + group.getGroupId(),
                    e);
        }

    }

}
