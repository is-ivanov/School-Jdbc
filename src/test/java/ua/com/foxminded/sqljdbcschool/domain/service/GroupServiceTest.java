package ua.com.foxminded.sqljdbcschool.domain.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ua.com.foxminded.sqljdbcschool.dao.impl.GroupDaoImpl;
import ua.com.foxminded.sqljdbcschool.domain.generator.Generator;
import ua.com.foxminded.sqljdbcschool.entity.Group;
import ua.com.foxminded.sqljdbcschool.exception.DAOException;
import ua.com.foxminded.sqljdbcschool.exception.DomainException;

@ExtendWith(MockitoExtension.class)
class GroupServiceTest {
    private static final int STUDENT_COUNT_EXCEPTION = 27;
    private static final String MESSAGE_EXCEPTION = "Can't get groups";

    private GroupService service;
    private List<Group> groups;
    private Group group;

    @Mock
    private GroupDaoImpl groupDaoMock;
    
    @Mock
    private Generator<Group> generatorMock;
    
    @BeforeEach
    void setUp() throws Exception {
        service = new GroupService(groupDaoMock, generatorMock);
        groups = new ArrayList<>();
    }
    
    @Nested
    @DisplayName("test 'createTestGroups' method")
    class testCreateTestGroups {
        private int numberGroups = 5;

        @Test
        @DisplayName("method should call generator.generate once")
        void testNumberCallsGenerator() throws DAOException {
            service.createTestGroups(numberGroups);

            verify(generatorMock, times(1)).generate(numberGroups);
        }

        @Test
        @DisplayName("method should call groupDao as much time as NumberGroups")
        void testNumberCallsDao() throws DAOException {
            for (int i = 0; i < numberGroups; i++) {
                groups.add(group);
            }
            when(generatorMock.generate(numberGroups)).thenReturn(groups);

            service.createTestGroups(numberGroups);

            verify(groupDaoMock, times(numberGroups)).add(group);
        }
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
        when(groupDaoMock
                .getGroupsWithLessEqualsStudentCount(STUDENT_COUNT_EXCEPTION))
                        .thenThrow(DAOException.class);
        Exception exception = assertThrows(DomainException.class, () -> service
                .getGroupsWithLessEqualsStudentCount(STUDENT_COUNT_EXCEPTION));
        assertEquals(MESSAGE_EXCEPTION, exception.getMessage());
    }

}
