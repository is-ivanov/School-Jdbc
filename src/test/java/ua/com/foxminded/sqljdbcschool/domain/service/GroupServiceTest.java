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

@ExtendWith(MockitoExtension.class)
class GroupServiceTest {
    private GroupService service;
    
    @Mock
    private GroupDao groupDaoMock;
    
    @Mock
    private List<Group> groups;
    
    @BeforeEach
    void setUp() throws Exception {
        service = new GroupService();
        groups = new ArrayList<>();
    }

    @Test
    void test() throws DAOException {
        when(groupDaoMock.getGroupsWithLessEqualsStudentCount(anyInt())).thenReturn(groups);
        
        List<Group> actualGroups = service.getGroupsWithLessEqualsStudentCount(anyInt());
        assertEquals(groups, actualGroups);
    }

}
