package ua.com.foxminded.sqljdbcschool.domain;

import java.util.List;

import ua.com.foxminded.sqljdbcschool.dao.CourseDao;
import ua.com.foxminded.sqljdbcschool.dao.DAOException;
import ua.com.foxminded.sqljdbcschool.dao.GroupDao;
import ua.com.foxminded.sqljdbcschool.dao.StartUpDao;
import ua.com.foxminded.sqljdbcschool.dao.StudentCourseDao;
import ua.com.foxminded.sqljdbcschool.dao.StudentDao;
import ua.com.foxminded.sqljdbcschool.entity.Course;
import ua.com.foxminded.sqljdbcschool.entity.Group;
import ua.com.foxminded.sqljdbcschool.entity.Student;

public class Facade {
    private static final String MESSAGE_IN_BASE = " in base";

    public void prepareBase() throws DomainException {

        StartUpDao startUpDao = new StartUpDao();
        try {
            startUpDao.deleteTables();
            startUpDao.createTables("create_all_tables.sql");
        } catch (DAOException e) {
            throw new DomainException("Can't delete and create tables", e);
        }
        GroupGenerator groupGenerator = new GroupGenerator();
        List<Group> groups = groupGenerator.generateGroups(10);
        saveGroupsInBase(groups);

        CourseGenerator courseGenerator = new CourseGenerator();
        List<Course> courses = courseGenerator.createCourses();
        saveCoursesInBase(courses);

        StudentGenerator studentGenerator = new StudentGenerator();
        List<Student> students = studentGenerator.generateStudents(200);
        saveStudentsInBase(students);
        
        StudentCourseDao studentCourseDao = new StudentCourseDao();
        try {
            studentCourseDao.createTable();
        } catch (DAOException e) {
            throw new DomainException("Can't create table students_courses", e);
        }

        System.out.println("OK");
    }

    private void saveGroupsInBase(List<Group> groups) {
        GroupDao groupDao = new GroupDao();
        groups.stream().forEach(group -> {
            try {
                groupDao.add(group);
            } catch (DAOException e) {
                throw new DomainException(
                        "Don't save group " + group + MESSAGE_IN_BASE, e);
            }
        });
    }

    private void saveCoursesInBase(List<Course> courses) {
        CourseDao courseDao = new CourseDao();
        courses.stream().forEach(course -> {
            try {
                courseDao.add(course);
            } catch (DAOException e) {
                throw new DomainException(
                        "Don't save course " + course + MESSAGE_IN_BASE, e);
            }
        });
    }

    private void saveStudentsInBase(List<Student> students) {
        StudentDao studentDao = new StudentDao();
        students.stream().forEach(student -> {
            try {
                studentDao.add(student);
            } catch (DAOException e) {
                throw new DomainException(
                        "Don't save student " + student + MESSAGE_IN_BASE, e);
            }
        });
    }

}
