package ua.com.foxminded.sqljdbcschool.domain.service;

import java.util.List;

import ua.com.foxminded.sqljdbcschool.dao.interfaces.CourseDao;
import ua.com.foxminded.sqljdbcschool.domain.generator.Generator;
import ua.com.foxminded.sqljdbcschool.entity.Course;
import ua.com.foxminded.sqljdbcschool.exception.DAOException;
import ua.com.foxminded.sqljdbcschool.exception.DomainException;

public class CourseService {
    private static final String MESSAGE_MASK_EXCEPTION = "Don't save course %s in base";

    private CourseDao courseDao;
    private Generator<Course> generator;

    public CourseService(CourseDao courseDao, Generator<Course> generator) {
        this.courseDao = courseDao;
        this.generator = generator;
    }

    public void createTestCourses(int numberCourses) {
        List<Course> courses = generator.generate(numberCourses);
        courses.forEach(this::addCourseToBase);
    }

    public List<Course> getCoursesMissingForStudent(int studentId) {
        try {
            return courseDao.getCoursesMissingForStudentId(studentId);
        } catch (DAOException e) {
            throw new DomainException("Can't get courses", e);
        }
    }

    public List<Course> getCoursesForStudent(int studentId) {
        try {
            return courseDao.getCoursesForStudentId(studentId);
        } catch (DAOException e) {
            throw new DomainException("Can't get courses", e);
        }
    }

    public void addCourseToBase(Course course) {
        try {
            courseDao.add(course);
        } catch (DAOException e) {
            throw new DomainException(
                    String.format(MESSAGE_MASK_EXCEPTION, course), e);
        }
    }
}
