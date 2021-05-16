package ua.com.foxminded.sqljdbcschool.domain.generator;

import static org.mockito.Mockito.*;

import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ua.com.foxminded.sqljdbcschool.dao.StudentDao;
import ua.com.foxminded.sqljdbcschool.exception.DAOException;

@ExtendWith(MockitoExtension.class)
class StudentCourseGeneratorTest {
    private StudentCourseGenerator generator;

    @Mock
    StudentDao studentDao;

    @Mock
    Random random;

    @BeforeEach
    void setUp() throws Exception {
        generator = new StudentCourseGenerator(studentDao, random);

    }

    @Test
    @DisplayName("test generator with number student = 3 should call studentDao.addStudentCourse for 3 students")
    void test() throws DAOException {

        generator.generate(3);
        verify(studentDao).addStudentCourse(1, 1);
        verify(studentDao).addStudentCourse(2, 1);
        verify(studentDao).addStudentCourse(3, 1);
    }

}
