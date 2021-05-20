package ua.com.foxminded.sqljdbcschool.domain.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ua.com.foxminded.sqljdbcschool.dao.impl.StudentDaoImpl;
import ua.com.foxminded.sqljdbcschool.domain.generator.Generator;
import ua.com.foxminded.sqljdbcschool.entity.Student;
import ua.com.foxminded.sqljdbcschool.exception.DAOException;
import ua.com.foxminded.sqljdbcschool.exception.DomainException;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {
    private static final String TEST_COURSE_NAME = "CourseName";
    private static final String TEST_FIRST_NAME = "First";
    private static final String TEST_LAST_NAME = "Last";
    private static final String MESSAGE_GET_EXCEPTION = "Can't get students";
    private static final String MESSAGE_CREATE_EXCEPTION = "Don't save student %s in base";

    private StudentService service;
    private List<Student> students;
    private Student student;
    private int numberStudents = 5;

    @Mock
    private StudentDaoImpl studentDaoMock;

    @Mock
    private Generator<Student> generatorMock;

    @Mock
    private Random randomMock;

    @BeforeEach
    void setUp() throws Exception {
        service = new StudentService(studentDaoMock, generatorMock, randomMock);
        students = new ArrayList<>();
        student = new Student(TEST_FIRST_NAME, TEST_LAST_NAME);
    }

    @Nested
    @DisplayName("test 'createTestStudents' method")
    class testCreateTestStudents {

        @Test
        @DisplayName("method should call generator.generate once")
        void testNubmerCallsGenerator() throws DAOException {
            service.createTestStudents(numberStudents);

            verify(generatorMock, times(1)).generate(numberStudents);
        }

        @Test
        @DisplayName("method should call studentDao as much time as numberStudents")
        void testNumberCallsDao() throws DAOException {
            for (int i = 0; i < numberStudents; i++) {
                students.add(student);
            }
            when(generatorMock.generate(numberStudents)).thenReturn(students);

            service.createTestStudents(numberStudents);

            verify(studentDaoMock, times(numberStudents)).add(student);
        }
    }

    @Nested
    @DisplayName("test 'createTestStudentsCourses' method")
    class testCreateTestStudentsCourses {

        @Test
        @DisplayName("method should call studentDao as much time as numberStudents")
        void testSizeListstudentCourses() throws DAOException {
            service.createTestStudentsCourses(numberStudents);

            verify(studentDaoMock, times(numberStudents))
                    .addStudentCourse(anyInt(), anyInt());
        }
    }

    @Nested
    @DisplayName("test getStudentsWithCourseName method")
    class testGetStudentsWithCourseName {

        @Test
        @DisplayName("normal input should return List<Sudent>")
        void testReturnListStudent() throws DAOException {
            when(studentDaoMock.getStudentsWithCourseName(anyString()))
                    .thenReturn(students);
            List<Student> actualStudents = service
                    .getStudentsWithCourseName(anyString());
            assertEquals(students, actualStudents);
        }

        @Test
        @DisplayName("when DAOException from Dao should return DomainException")
        void testThrowDomainException() throws DAOException {
            when(studentDaoMock.getStudentsWithCourseName(TEST_COURSE_NAME))
                    .thenThrow(DAOException.class);

            Exception exception = assertThrows(DomainException.class,
                    () -> service.getStudentsWithCourseName(TEST_COURSE_NAME));
            assertEquals(MESSAGE_GET_EXCEPTION, exception.getMessage());
        }

    }

    @Nested
    @DisplayName("test createStudent method")
    class testCreate {

        @Test
        @DisplayName("normal input should call studentDao.add method with right argument")
        void testCallStudentDaoAddMethod() throws DAOException {
            service.createStudent(TEST_FIRST_NAME, TEST_LAST_NAME);
            verify(studentDaoMock, times(1)).add(student);
        }

        @Test
        @DisplayName("when DAOException from Dao should return DomainException")
        void testThrowDomainException() throws DAOException {
            doThrow(DAOException.class).when(studentDaoMock).add(student);

            Exception exception = assertThrows(DomainException.class,
                    () -> service.createStudent(TEST_FIRST_NAME,
                            TEST_LAST_NAME));
            String expectedMessage = String.format(MESSAGE_CREATE_EXCEPTION,
                    student);
            assertEquals(expectedMessage, exception.getMessage());

        }

    }

    // TODO
    @Nested
    @DisplayName("test 'deleteById' method")
    class testDeleteById {

    }

    // TODO
    @Nested
    @DisplayName("test 'addStudentToCourse' method")
    class testAddStudentToCourse {

    }

    // TODO
    @Nested
    @DisplayName("test 'removeStudentFromCourse' method")
    class testRemoveStudentFromCourse {

    }
}
