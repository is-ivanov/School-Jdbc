package ua.com.foxminded.sqljdbcschool.dao.interfaces;

import java.util.List;

import ua.com.foxminded.sqljdbcschool.entity.Student;
import ua.com.foxminded.sqljdbcschool.exception.DAOException;

public interface StudentDao extends Dao<Student> {

    void createTableStudentCourse() throws DAOException;

    void addStudentCourse(int studentId, int courseId) throws DAOException;

    void removeStudentFromCourse(int studentId, int courseId)
            throws DAOException;

    List<Student> getStudentsWithCourseName(String courseName)
            throws DAOException;

}
