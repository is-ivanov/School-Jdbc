package ua.com.foxminded.sqljdbcschool.domain.generator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import ua.com.foxminded.sqljdbcschool.dao.StudentDao;
import ua.com.foxminded.sqljdbcschool.exception.DAOException;
import ua.com.foxminded.sqljdbcschool.exception.DomainException;

public class StudentCourseGenerator implements Generator {

    private static final String MESSAGE_MASK_EXCEPTION_CREATE = "Don't save student %d and course %d";
    private Random random = new Random();

    @Override
    public void generate(int numberStudent) {
        StudentDao studentDao = new StudentDao();
        try {
            studentDao.createTableStudentCourse();
        } catch (DAOException e) {
            throw new DomainException(e.getMessage(), e);
        }

        List<Integer[]> studentCourses = new ArrayList<>();
        for (int i = 0; i < numberStudent; i++) {
            assingCourses(i + 1, studentCourses);
        }

        saveInBase(studentCourses);
    }
    
    @SuppressWarnings("java:S5413")
    private void assingCourses(int studentId, List<Integer[]> studentCourses) {
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

    private void saveInBase(List<Integer[]> studentCourses) {
        StudentDao studentDao = new StudentDao();
        studentCourses.stream().forEach(pair -> {
            try {
                studentDao.addStudentCourse(pair[0], pair[1]);
            } catch (DAOException e) {
                throw new DomainException(String.format(
                        MESSAGE_MASK_EXCEPTION_CREATE, pair[0], pair[1]), e);
            }
        });
    }
}
