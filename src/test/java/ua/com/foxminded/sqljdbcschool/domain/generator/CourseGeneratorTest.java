package ua.com.foxminded.sqljdbcschool.domain.generator;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ua.com.foxminded.sqljdbcschool.dao.Dao;
import ua.com.foxminded.sqljdbcschool.entity.Course;
import ua.com.foxminded.sqljdbcschool.exception.DAOException;

@ExtendWith(MockitoExtension.class)
class CourseGeneratorTest {
    private CourseGenerator generator;
    private Course course1;
    private Course course2;

    @Mock
    Dao<Course> courseDao;

    @BeforeEach
    void setUp() throws Exception {
        generator = new CourseGenerator(courseDao);
    }

    @Test
    @DisplayName("test generator with number course = 2 should call courseDao.add(course1) and courseDao.add(course2)")
    void test() throws DAOException {
        course1 = new Course("math", "course of Mathematics");
        course2 = new Course("biology", "course of Biology");
        generator.generate(2);

        verify(courseDao).add(course1);
        verify(courseDao).add(course2);
    }

}
