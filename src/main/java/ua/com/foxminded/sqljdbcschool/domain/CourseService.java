package ua.com.foxminded.sqljdbcschool.domain;

import java.util.List;

import ua.com.foxminded.sqljdbcschool.dao.CourseDao;
import ua.com.foxminded.sqljdbcschool.entity.Course;
import ua.com.foxminded.sqljdbcschool.exception.DAOException;
import ua.com.foxminded.sqljdbcschool.exception.DomainException;

public class CourseService {
    private CourseDao courseDao = new CourseDao();
    
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
}
