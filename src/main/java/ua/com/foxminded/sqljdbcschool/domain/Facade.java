package ua.com.foxminded.sqljdbcschool.domain;

import ua.com.foxminded.sqljdbcschool.dao.StartUpDao;
import ua.com.foxminded.sqljdbcschool.domain.generator.CourseGenerator;
import ua.com.foxminded.sqljdbcschool.domain.generator.Generator;
import ua.com.foxminded.sqljdbcschool.domain.generator.GroupGenerator;
import ua.com.foxminded.sqljdbcschool.domain.generator.StudentCourseGenerator;
import ua.com.foxminded.sqljdbcschool.domain.generator.StudentGenerator;
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
    private Generator groupGenerator;
    private Generator courseGenerator;
    private Generator studentGenerator;
    private Generator studentCourseGenerator;
    private MenuStarter menu;

    public Facade(StartUpDao startUpDao, Generator groupGenerator,
            Generator courseGenerator, Generator studentGenerator,
            Generator studentCourseGenerator, MenuStarter menu) {
        this.startUpDao = startUpDao;
        this.groupGenerator = groupGenerator;
        this.courseGenerator = courseGenerator;
        this.studentGenerator = studentGenerator;
        this.studentCourseGenerator = studentCourseGenerator;
        this.menu = menu;
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
        groupGenerator.generate(NUMBER_GROUPS);
        courseGenerator.generate(NUMBER_COURSES);
        studentGenerator.generate(NUMBER_STUDENTS);
        studentCourseGenerator.generate(NUMBER_STUDENTS);
    }

}
