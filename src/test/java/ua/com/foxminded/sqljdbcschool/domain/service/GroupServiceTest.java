package ua.com.foxminded.sqljdbcschool.domain.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ua.com.foxminded.sqljdbcschool.dao.GroupDao;
import ua.com.foxminded.sqljdbcschool.entity.Group;
import ua.com.foxminded.sqljdbcschool.exception.DAOException;
import ua.com.foxminded.sqljdbcschool.exception.DomainException;

@ExtendWith(MockitoExtension.class)
class GroupServiceTest {
    private GroupService service;

    @Mock
    private GroupDao groupDaoMock;

    private List<Group> groups;

    @BeforeEach
    void setUp() throws Exception {
        service = new GroupService(groupDaoMock);
        groups = new ArrayList<>();
    }

    @Test
    void testReturnListGroups() throws DAOException {
        when(groupDaoMock.getGroupsWithLessEqualsStudentCount(anyInt()))
                .thenReturn(groups);

        List<Group> actualGroups = service
                .getGroupsWithLessEqualsStudentCount(anyInt());
        assertEquals(groups, actualGroups);
    }

    @Test
    void testReturnDaoException() throws DAOException {
        when(groupDaoMock.getGroupsWithLessEqualsStudentCount(anyInt()))
                .thenThrow(DAOException.class);
        Exception exception = assertThrows(DomainException.class,
                () -> service.getGroupsWithLessEqualsStudentCount(anyInt()));
        assertEquals("Can't get groups", exception.getMessage());
    }

}
