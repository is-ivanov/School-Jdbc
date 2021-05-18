package ua.com.foxminded.sqljdbcschool.domain.generator;

import static org.mockito.Mockito.*;

import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ua.com.foxminded.sqljdbcschool.dao.interfaces.Dao;
import ua.com.foxminded.sqljdbcschool.entity.Group;
import ua.com.foxminded.sqljdbcschool.exception.DAOException;

@ExtendWith(MockitoExtension.class)
class GroupGeneratorTest {
    private static final String TEST_GROUP_NAME = "-10";
    private Generator groupGenerator;
    private Group group;

    @Mock
    Dao<Group> groupDao;

    @Mock
    Random random;

    @BeforeEach
    void setUp() throws Exception {
        groupGenerator = new GroupGenerator(groupDao, random);
    }

    @Test
    @DisplayName("test generate with numberGroups = 10 should call 10 times method groupDao.add")
    void testVerifyCallsGroupDao() throws DAOException {

        group = new Group(0, TEST_GROUP_NAME);
        
        groupGenerator.generate(10);

        verify(groupDao, times(10)).add(group);

    }


}
