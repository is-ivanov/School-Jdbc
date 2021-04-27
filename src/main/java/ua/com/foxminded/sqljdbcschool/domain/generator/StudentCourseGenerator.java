package ua.com.foxminded.sqljdbcschool.domain.generator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import ua.com.foxminded.sqljdbcschool.dao.StudentCourseDao;
import ua.com.foxminded.sqljdbcschool.entity.StudentCourse;
import ua.com.foxminded.sqljdbcschool.exception.DAOException;
import ua.com.foxminded.sqljdbcschool.exception.DomainException;

public class StudentCourseGenerator implements Generator {

    private Random random = new Random();

    @Override
    public void generate(int numberStudent) {
        StudentCourseDao studentCourseDao = new StudentCourseDao();
        try {
            studentCourseDao.createTable();
        } catch (DAOException e) {
            throw new DomainException("Can't create table students_courses", e);
        }
        List<StudentCourse> studentCourses = new ArrayList<>();
        for (int i = 0; i < numberStudent; i++) {
            assingCourses(i + 1, studentCourses);
        }

        saveInBase(studentCourses);
    }

    private void assingCourses(int studentId,
            List<StudentCourse> studentCourses) {
        int numberCourses = random.nextInt(2) + 1;
        List<Integer> coursesId = new LinkedList<>();
        Collections.addAll(coursesId, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        for (int i = 0; i < numberCourses; i++) {
            int numberCourse = random.nextInt(coursesId.size());
            StudentCourse studentCourse = new StudentCourse(studentId,
                    coursesId.get(numberCourse));
            studentCourses.add(studentCourse);
            coursesId.remove(numberCourse);
        }
    }

    private void saveInBase(List<StudentCourse> studentCourses) {
        StudentCourseDao studentCourseDao = new StudentCourseDao();
        studentCourses.stream().forEach(pair -> {
            try {
                studentCourseDao.add(pair);
            } catch (DAOException e) {
                throw new DomainException(
                        "Don't save sudent " + pair.getStudentId()
                                + " and course " + pair.getCourseId(),
                        e);
            }
        });
    }
}
