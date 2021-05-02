package ua.com.foxminded.sqljdbcschool.domain.service;

import java.util.List;

import ua.com.foxminded.sqljdbcschool.dao.StudentDao;
import ua.com.foxminded.sqljdbcschool.entity.Student;
import ua.com.foxminded.sqljdbcschool.exception.DAOException;
import ua.com.foxminded.sqljdbcschool.exception.DomainException;

@SuppressWarnings("java:S106")
public class StudentServise {
    private static final String MESSAGE_EXCEPTION_DELETE_STUDENT_COURSE = "Can't delete student %d from course %d";

    private StudentDao studentDao = new StudentDao();

    public List<Student> getStudentsWithCourseName(String courseName) {
        try {
            return studentDao.getStudentsWithCourseName(courseName);
        } catch (DAOException e) {
            throw new DomainException("Can't get students", e);
        }
    }

    public void create(String firstName, String lastName) {
        Student student = new Student(firstName, lastName);
        try {
            studentDao.add(student);
        } catch (DAOException e) {
            throw new DomainException("Can't create student", e);
        }
    }

    public void deleteById(int id) {
        Student student = new Student(id);
        try {
            studentDao.delete(student);
        } catch (DAOException e) {
            throw new DomainException("Can't delete student", e);
        }
    }

    public void addStudentToCourse(int studentId, int courseId) {
        try {
            studentDao.addStudentCourse(studentId, courseId);
        } catch (DAOException e) {
            throw new DomainException("Can't add student to course", e);
        }

    }

    public void removeStudentFromCourse(int studentId, int courseId) {
        try {
            studentDao.removeStudentFromCourse(studentId, courseId);
        } catch (DAOException e) {
            throw new DomainException(
                    String.format(MESSAGE_EXCEPTION_DELETE_STUDENT_COURSE,
                            studentId, courseId),
                    e);
        }
    }
}
