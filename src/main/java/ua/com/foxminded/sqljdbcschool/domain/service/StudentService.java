package ua.com.foxminded.sqljdbcschool.domain.service;

import java.util.List;

import ua.com.foxminded.sqljdbcschool.dao.interfaces.StudentDao;
import ua.com.foxminded.sqljdbcschool.domain.generator.Generator;
import ua.com.foxminded.sqljdbcschool.entity.Student;
import ua.com.foxminded.sqljdbcschool.exception.DAOException;
import ua.com.foxminded.sqljdbcschool.exception.DomainException;

@SuppressWarnings("java:S106")
public class StudentService {
    private static final String MESSAGE_EXCEPTION_DELETE_STUDENT_COURSE = "Can't delete student %d from course %d";
    private static final String MESSAGE_MASK_EXCEPTION = "Don't save student %s in base";
    
    private StudentDao studentDao;
    private Generator<Student> generator;

    public StudentService(StudentDao studentDao, Generator<Student> generator) {
        this.studentDao = studentDao;
        this.generator = generator;
    }
    
    public void createTestStudents(int numberStudents) {
        List<Student> students = generator.generate(numberStudents);
        students.forEach(this::addStudentToBase);
    }
    
    public List<Student> getStudentsWithCourseName(String courseName) {
        try {
            return studentDao.getStudentsWithCourseName(courseName);
        } catch (DAOException e) {
            throw new DomainException("Can't get students", e);
        }
    }

    public void createStudent(String firstName, String lastName) {
        Student student = new Student(firstName, lastName);
        addStudentToBase(student);
    }
    
    public void addStudentToBase(Student student) {
        try {
          studentDao.add(student);
      } catch (DAOException e) {
          throw new DomainException(
                  String.format(MESSAGE_MASK_EXCEPTION, student), e);
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
