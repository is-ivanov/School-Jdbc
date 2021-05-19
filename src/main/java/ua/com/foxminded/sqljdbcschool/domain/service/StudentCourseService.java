package ua.com.foxminded.sqljdbcschool.domain.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import ua.com.foxminded.sqljdbcschool.dao.interfaces.StudentDao;
import ua.com.foxminded.sqljdbcschool.exception.DAOException;
import ua.com.foxminded.sqljdbcschool.exception.DomainException;

@SuppressWarnings("java:S5413")
public class StudentCourseService {

    private static final String MESSAGE_MASK_EXCEPTION_CREATE = "Don't save student %d and course %d";

    private StudentDao studentDao;
    private Random random;

    public StudentCourseService(StudentDao studentDao, Random random) {
        this.studentDao = studentDao;
        this.random = random;
    }

    public void createTestStudentsCourses(int numberStudents) {
        createTable();

        List<Integer[]> studentCourses = new ArrayList<>();
        for (int i = 0; i < numberStudents; i++) {
            assignCourses(i + 1, studentCourses);
        }

        addStudentsCoursesToBase(studentCourses);
    }

    private void createTable() {
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
