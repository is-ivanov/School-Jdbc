package ua.com.foxminded.sqljdbcschool.dao.interfaces;

import java.util.List;

import ua.com.foxminded.sqljdbcschool.entity.Course;
import ua.com.foxminded.sqljdbcschool.exception.DAOException;

public interface CourseDao extends Dao<Course> {

    List<Course> getCoursesForStudentId(int studentId) throws DAOException;

    List<Course> getCoursesMissingForStudentId(int studentId)
            throws DAOException;
}
