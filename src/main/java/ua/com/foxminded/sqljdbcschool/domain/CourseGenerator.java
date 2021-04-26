package ua.com.foxminded.sqljdbcschool.domain;

import java.util.ArrayList;
import java.util.List;

import ua.com.foxminded.sqljdbcschool.dao.CourseDao;
import ua.com.foxminded.sqljdbcschool.dao.DAOException;
import ua.com.foxminded.sqljdbcschool.entity.Course;

public class CourseGenerator {
    private static final String MESSAGE_IN_BASE = " in base";

    public void createCourses() {
        List<Course> courses = new ArrayList<>();
        courses.add(new Course(1, "math", "course of Mathematichs"));
        courses.add(new Course(2, "biology", "course of Biology"));
        courses.add(new Course(3, "chemistry", "course of Chemistry"));
        courses.add(new Course(4, "physics", "course of Physics"));
        courses.add(new Course(5, "philosophy", "course of Philosophy"));
        courses.add(new Course(6, "drawing", "course of Drawing"));
        courses.add(new Course(7, "literature", "course of Literature"));
        courses.add(new Course(8, "English", "course of English"));
        courses.add(new Course(9, "geography", "course of Geography"));
        courses.add(new Course(10, "physical training",
                "course of Physical training"));

        saveCoursesInBase(courses);
    }
    
    private void saveCoursesInBase(List<Course> courses) {
        CourseDao courseDao = new CourseDao();
        courses.stream().forEach(course -> {
            try {
                courseDao.add(course);
            } catch (DAOException e) {
                throw new DomainException(
                        "Don't save course " + course + MESSAGE_IN_BASE, e);
            }
        });
    }
}
