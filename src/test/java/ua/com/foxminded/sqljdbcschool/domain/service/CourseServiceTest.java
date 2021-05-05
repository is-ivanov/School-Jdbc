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

import ua.com.foxminded.sqljdbcschool.dao.CourseDao;
import ua.com.foxminded.sqljdbcschool.entity.Course;
import ua.com.foxminded.sqljdbcschool.exception.DAOException;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {
    private CourseService service;
    private List<Course> courses;

    @Mock
    private CourseDao courseDao;

    @BeforeEach
    void setUp() throws Exception {
        service = new CourseService(courseDao);
        courses = new ArrayList<>();
    }

    @Nested
    @DisplayName("test getCoursesMissingForStuden method")
    class testGetCoursesMissingForStuden {

        @Test
        void testReturnListCourses() throws DAOException {
            when(courseDao.getCoursesMissingForStudentId(anyInt()))
                    .thenReturn(courses);

            List<Course> actualCourses = service
                    .getCoursesMissingForStudent(anyInt());

            assertEquals(courses, actualCourses);
        }
    }

}
