package ua.com.foxminded.sqljdbcschool.domain.generator;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ua.com.foxminded.sqljdbcschool.entity.Course;
import ua.com.foxminded.sqljdbcschool.exception.DAOException;

class CourseGeneratorTest {
    private CourseGenerator generator;

    @BeforeEach
    void setUp() throws Exception {
        generator = new CourseGenerator();
    }

    @Test
    @DisplayName("test generator with number course = 2 should return List with 2 courses")
    void test() throws DAOException {
        List<Course> expectedCourses = new ArrayList<>();
        expectedCourses.add(new Course("math", "course of Mathematics"));
        expectedCourses.add(new Course("biology", "course of Biology"));
        
        List<Course> actualCourses = generator.generate(2);
        assertEquals(expectedCourses, actualCourses);
    }

}
