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

import ua.com.foxminded.sqljdbcschool.dao.impl.StudentDaoImpl;
import ua.com.foxminded.sqljdbcschool.entity.Student;
import ua.com.foxminded.sqljdbcschool.exception.DAOException;
import ua.com.foxminded.sqljdbcschool.exception.DomainException;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {
    private static final String TEST_COURSE_NAME = "CourseName";
    private static final String TEST_FIRST_NAME = "First";
    private static final String TEST_LAST_NAME = "Last";
    private static final String MESSAGE_GET_EXCEPTION = "Can't get students";
    private static final String MESSAGE_CREATE_EXCEPTION = "Can't create student";

    private StudentService service;
    private List<Student> students;
    private Student student;

    @Mock
    private StudentDaoImpl studentDao;

    @BeforeEach
    void setUp() throws Exception {
        service = new StudentService(studentDao);
        students = new ArrayList<>();
        student = new Student(TEST_FIRST_NAME, TEST_LAST_NAME);
    }

    @Nested
    @DisplayName("test getStudentsWithCourseName method")
    class testGetStudentsWithCourseName {

        @Test
        @DisplayName("normal input should return List<Sudent>")
        void testReturnListStudent() throws DAOException {
            when(studentDao.getStudentsWithCourseName(anyString()))
                    .thenReturn(students);
            List<Student> actualStudents = service
                    .getStudentsWithCourseName(anyString());
            assertEquals(students, actualStudents);
        }

        @Test
        @DisplayName("when DAOException from Dao should return DomainException")
        void testThrowDomainException() throws DAOException {
            when(studentDao.getStudentsWithCourseName(TEST_COURSE_NAME))
                    .thenThrow(DAOException.class);

            Exception exception = assertThrows(DomainException.class,
                    () -> service.getStudentsWithCourseName(TEST_COURSE_NAME));
            assertEquals(MESSAGE_GET_EXCEPTION, exception.getMessage());
        }

    }

    @Nested
    @DisplayName("test create method")
    class testCreate {

        @Test
        @DisplayName("normal input should call studentDao.add method with right argument")
        void testCallStudentDaoAddMethod() throws DAOException {
            service.createStudent(TEST_FIRST_NAME, TEST_LAST_NAME);
            verify(studentDao, times(1)).add(student);
        }

        @Test
        @DisplayName("when DAOException from Dao should return DomainException")
        void testThrowDomainException() throws DAOException {
            doThrow(DAOException.class).when(studentDao).add(student);

            Exception exception = assertThrows(DomainException.class,
                    () -> service.createStudent(TEST_FIRST_NAME, TEST_LAST_NAME));
            assertEquals(MESSAGE_CREATE_EXCEPTION, exception.getMessage());

        }

    }

}
