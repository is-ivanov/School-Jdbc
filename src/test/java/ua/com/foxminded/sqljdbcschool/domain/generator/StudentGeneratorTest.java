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
import ua.com.foxminded.sqljdbcschool.entity.Student;
import ua.com.foxminded.sqljdbcschool.exception.DAOException;

@ExtendWith(MockitoExtension.class)
class StudentGeneratorTest {
    private StudentGenerator generator;
    private Student student;
    
    @Mock
    Dao<Student> studentDao;
    
    @Mock
    Random random;
    
    @BeforeEach
    void setUp() throws Exception {
        generator = new StudentGenerator(studentDao, random);
    }


    @Test
    @DisplayName("test generator with number student = 3 should call 3 times method studentDao.add")
    void test() throws DAOException {
        when(random.nextInt(anyInt())).thenReturn(0);
        student = new Student(0, 0, "Ivan", "Ivanov");
        
        generator.generate(3);
        verify(studentDao, times(3)).add(student);
        
    }

}
