package ua.com.foxminded.sqljdbcschool.domain;

import ua.com.foxminded.sqljdbcschool.dao.DAOException;
import ua.com.foxminded.sqljdbcschool.dao.StartUpDao;
import ua.com.foxminded.sqljdbcschool.domain.generator.CourseGenerator;
import ua.com.foxminded.sqljdbcschool.domain.generator.Generator;
import ua.com.foxminded.sqljdbcschool.domain.generator.GroupGenerator;
import ua.com.foxminded.sqljdbcschool.domain.generator.StudentCourseGenerator;
import ua.com.foxminded.sqljdbcschool.domain.generator.StudentGenerator;

public class Facade {
    private static final int NUMBER_STUDENTS = 200;
    private static final int NUMBER_COURSES = 10;
    private static final int NUMBER_GROUPS = 10;

    public void prepareBase() throws DomainException {

        StartUpDao startUpDao = new StartUpDao();
        try {
            startUpDao.deleteTables();
            startUpDao.createTables("create_all_tables.sql");
        } catch (DAOException e) {
            throw new DomainException("Can't delete and create tables", e);
        }
        Generator groupGenerator = new GroupGenerator();
        groupGenerator.generate(NUMBER_GROUPS);

        Generator courseGenerator = new CourseGenerator();
        courseGenerator.generate(NUMBER_COURSES);

        Generator studentGenerator = new StudentGenerator();
        studentGenerator.generate(NUMBER_STUDENTS);
        
        Generator studentCourseGenerator = new StudentCourseGenerator();
        studentCourseGenerator.generate(NUMBER_STUDENTS);
        

        System.out.println("OK");
    }

    

}