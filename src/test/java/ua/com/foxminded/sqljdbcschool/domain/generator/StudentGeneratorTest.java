package ua.com.foxminded.sqljdbcschool.domain.generator;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ua.com.foxminded.sqljdbcschool.entity.Student;
import ua.com.foxminded.sqljdbcschool.exception.DAOException;

@ExtendWith(MockitoExtension.class)
class StudentGeneratorTest {
    private static final String STUDENT_FIRST_NAME = "Ivan";
    private static final String STUDENT_LAST_NAME = "Ivanov";

    private StudentGenerator generator;

    @Mock
    Random random;

    @BeforeEach
    void setUp() throws Exception {
        generator = new StudentGenerator(random);
    }

    @Test
    @DisplayName("test generator with number student = 1 should return List students with 1 student")
    void test() throws DAOException {
        when(random.nextInt(anyInt())).thenReturn(0);
        List<Student> expectedStudents = new ArrayList<>();
        expectedStudents
                .add(new Student(0, 0, STUDENT_FIRST_NAME, STUDENT_LAST_NAME));
        List<Student> actualStudent = generator.generate(1);
        assertEquals(expectedStudents, actualStudent);

    }

}
