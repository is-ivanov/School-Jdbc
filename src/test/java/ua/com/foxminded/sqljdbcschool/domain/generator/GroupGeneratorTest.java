package ua.com.foxminded.sqljdbcschool.domain.generator;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ua.com.foxminded.sqljdbcschool.dao.Dao;
import ua.com.foxminded.sqljdbcschool.entity.Group;
import ua.com.foxminded.sqljdbcschool.exception.DAOException;

@ExtendWith(MockitoExtension.class)
class GroupGeneratorTest {
    private Generator groupGenerator;

    @Mock
    Dao<Group> groupDao;
    
    @Mock
    Group group;
    
    @Mock
    Random random;
    
    @BeforeEach
    void setUp() throws Exception {
        groupGenerator = new GroupGenerator(groupDao);
    }

    @Test
    void test() throws DAOException {
        
        groupGenerator.generate(10);
        
        verify(groupDao, times(10)).add(group);
        
    }

}
