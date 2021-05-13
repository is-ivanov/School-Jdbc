package ua.com.foxminded.sqljdbcschool.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import ua.com.foxminded.sqljdbcschool.entity.Group;
import ua.com.foxminded.sqljdbcschool.exception.DAOException;

class GroupDaoTest {
    private static final String FILENAME_STARTUP_SCRIPT = "create_all_tables_and_fill_test_data.sql";
    private static final String FILENAME_FINISH_SCRIPT = "drop_all_tables.sql";
    private static final String NAME_GROUP_ID1 = "OR-41";
    private static final String NAME_GROUP_ID2 = "GM-87";
    private static final String NAME_GROUP_ID3 = "XI-12";
    private static final String TEST_GROUP_NAME = "TestGroup";

    private GroupDao groupDao;

    @BeforeEach
    void setUp() throws Exception {
        groupDao = new GroupDao();
        try (Connection connection = DaoUtils.getConnection()) {
            SqlScriptRunner scriptRunner = new SqlScriptRunner(connection);
            scriptRunner.runSqlScript(FILENAME_STARTUP_SCRIPT);
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    @AfterEach
    void cleanData() throws Exception {
        try (Connection connection = DaoUtils.getConnection()) {
            SqlScriptRunner scriptRunner = new SqlScriptRunner(connection);
            scriptRunner.runSqlScript(FILENAME_FINISH_SCRIPT);
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    @Nested
    @DisplayName("test 'add' method")
    class testAdd {

        @Test
        @DisplayName("add group should create new record in testDB with id=4")
        void testAddGroup() throws DAOException {
            Group group = new Group(TEST_GROUP_NAME);
            groupDao.add(group);
            String expectedGroupName = group.getGroupName();
            String actualGroupName = groupDao.getById(4).get().getGroupName();
            assertEquals(expectedGroupName, actualGroupName);
        }

    }

    @Nested
    @DisplayName("test 'getById' method")
    class testGetById {

        @Test
        @DisplayName("get group by id=1 should return 'OR-41' group")
        void testGetGroupById() throws DAOException {
            String expectedGroupName = NAME_GROUP_ID1;
            String actualGroupName = groupDao.getById(1).get().getGroupName();
            assertEquals(expectedGroupName, actualGroupName);
        }

        @Test
        @DisplayName("get group by non-existing id=6 should return empty Optional")
        void testGetGroupByNonExistingId() throws DAOException {
            Optional<Group> expected = Optional.empty();
            Optional<Group> actual = groupDao.getById(6);
            assertEquals(expected, actual);
        }

    }

    @Nested
    @DisplayName("test 'getAll' method")
    class testGetAll {

        @Test
        @DisplayName("get all groups from base should return all groups")
        void testGetAllGroups() throws DAOException {
            List<Group> expectedGroups = new ArrayList<>();
            expectedGroups.add(new Group(1, NAME_GROUP_ID1));
            expectedGroups.add(new Group(2, NAME_GROUP_ID2));
            expectedGroups.add(new Group(3, NAME_GROUP_ID3));

            assertEquals(expectedGroups, groupDao.getAll());
        }
    }

    @Nested
    @DisplayName("test 'update' method")
    class testUpdate {
        @Test
        @DisplayName("update name group id=1 should write new name and getById(1) return new name")
        void testUpdateGroupName() throws DAOException {
            Group newGroup = new Group(1, TEST_GROUP_NAME);
            groupDao.update(newGroup);
            String actualName = groupDao.getById(1).get().getGroupName();
            assertEquals(TEST_GROUP_NAME, actualName);
        }

    }

    @Nested
    @DisplayName("test 'delete' method")
    class testDelete {
        @Test
        @DisplayName("delete group id=1 should delete group and getById(1) return empty Optional")
        void testDeleteGroupById() throws DAOException {
            Group group = new Group(1, NAME_GROUP_ID1);
            groupDao.delete(group);
            Optional<Group> expected = Optional.empty();
            Optional<Group> actual = groupDao.getById(1);
            assertEquals(expected, actual);
        }
    }

    @Nested
    @DisplayName("test 'getGroupsWithLessEqualsStudentCount' method")
    class getGroupsWithLessEqualsStudentCount {

    }

}
