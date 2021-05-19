package ua.com.foxminded.sqljdbcschool.domain;

import ua.com.foxminded.sqljdbcschool.dao.StartUpDao;
import ua.com.foxminded.sqljdbcschool.domain.service.CourseService;
import ua.com.foxminded.sqljdbcschool.domain.service.GroupService;
import ua.com.foxminded.sqljdbcschool.domain.service.StudentCourseService;
import ua.com.foxminded.sqljdbcschool.domain.service.StudentService;
import ua.com.foxminded.sqljdbcschool.exception.DAOException;
import ua.com.foxminded.sqljdbcschool.exception.DomainException;
import ua.com.foxminded.sqljdbcschool.ui.MenuStarter;

@SuppressWarnings("java:S106")
public class Facade {
    private static final String MESSAGE_START_PREPARE = "Starting prepare base";
    private static final String MESSAGE_FINISH_PREPARE = "Database prepared";
    private static final int NUMBER_STUDENTS = 200;
    private static final int NUMBER_COURSES = 10;
    private static final int NUMBER_GROUPS = 10;
    private static final String MESSAGE_EXCEPTION_CREATE_TABLES = "Can't delete and create tables";

    private StartUpDao startUpDao;
    private MenuStarter menu;
    private GroupService groupService;
    private CourseService courseService;
    private StudentService studentService;
    private StudentCourseService studentCourseService;


    public Facade(StartUpDao startUpDao, MenuStarter menu,
            GroupService groupService, CourseService courseService,
            StudentService studentService,
            StudentCourseService studentCourseService) {
        this.startUpDao = startUpDao;
        this.menu = menu;
        this.groupService = groupService;
        this.courseService = courseService;
        this.studentService = studentService;
        this.studentCourseService = studentCourseService;
    }

    public void prepareBase() throws DomainException {
        System.out.println(MESSAGE_START_PREPARE);
        createTables();
        fillTables();
        System.out.println(MESSAGE_FINISH_PREPARE);
    }

    public void workWithBase() {
        menu.startMenu();
    }

    private void createTables() {
        try {
            startUpDao.prepareTables();
        } catch (DAOException e) {
            throw new DomainException(MESSAGE_EXCEPTION_CREATE_TABLES, e);
        }
    }

    private void fillTables() {
        groupService.createTestGroups(NUMBER_GROUPS);
        courseService.createTestCourses(NUMBER_COURSES);
        studentService.createTestStudents(NUMBER_STUDENTS);
        studentCourseService.createTestStudentsCourses(NUMBER_STUDENTS);
    }

}
