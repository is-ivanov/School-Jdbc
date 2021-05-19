package ua.com.foxminded.sqljdbcschool.domain.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import ua.com.foxminded.sqljdbcschool.dao.interfaces.StudentDao;
import ua.com.foxminded.sqljdbcschool.domain.generator.Generator;
import ua.com.foxminded.sqljdbcschool.entity.Student;
import ua.com.foxminded.sqljdbcschool.exception.DAOException;
import ua.com.foxminded.sqljdbcschool.exception.DomainException;

public class StudentService {
    private static final String MESSAGE_EXCEPTION_DELETE_STUDENT_COURSE = "Can't delete student %d from course %d";
    private static final String MESSAGE_MASK_EXCEPTION = "Don't save student %s in base";
    private static final String MESSAGE_MASK_EXCEPTION_CREATE = "Don't save student %d and course %d";

    private StudentDao studentDao;
    private Generator<Student> generator;
    private Random random;

    public StudentService(StudentDao studentDao, Generator<Student> generator,
            Random random) {
        this.studentDao = studentDao;
        this.generator = generator;
        this.random = random;
    }

    public void createTestStudents(int numberStudents) {
        List<Student> students = generator.generate(numberStudents);
        students.forEach(this::addStudentToBase);
    }

    public void createTestStudentsCourses(int numberStudents) {
        createTableStudentsCourses();

        List<Integer[]> studentCourses = new ArrayList<>();
        for (int i = 0; i < numberStudents; i++) {
            assignCourses(i + 1, studentCourses);
        }
        addStudentsCoursesToBase(studentCourses);
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

    private void createTableStudentsCourses() {
        try {
            studentDao.createTableStudentCourse();
        } catch (DAOException e) {
            throw new DomainException(e.getMessage(), e);
        }
    }

    private void assignCourses(int studentId, List<Integer[]> studentCourses) {
        int numberCoursesPerStudent = random.nextInt(2) + 1;
        List<Integer> coursesId = new LinkedList<>();
        Collections.addAll(coursesId, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        for (int i = 0; i < numberCoursesPerStudent; i++) {
            int numberCourse = random.nextInt(coursesId.size());
            Integer[] studentCourse = { studentId,
                    coursesId.get(numberCourse) };
            studentCourses.add(studentCourse);
            coursesId.remove(numberCourse);
        }
    }

    private void addStudentsCoursesToBase(List<Integer[]> studentsCourses) {
        studentsCourses.forEach(pair -> {
            try {
                studentDao.addStudentCourse(pair[0], pair[1]);
            } catch (DAOException e) {
                throw new DomainException(String.format(
                        MESSAGE_MASK_EXCEPTION_CREATE, pair[0], pair[1]), e);
            }
        });
    }
}
