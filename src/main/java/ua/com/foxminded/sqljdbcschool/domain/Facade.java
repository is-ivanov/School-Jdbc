package ua.com.foxminded.sqljdbcschool.domain;

import ua.com.foxminded.sqljdbcschool.dao.DAOException;
import ua.com.foxminded.sqljdbcschool.dao.StartUpDao;
import ua.com.foxminded.sqljdbcschool.dao.StudentCourseDao;
import ua.com.foxminded.sqljdbcschool.domain.generator.CourseGenerator;
import ua.com.foxminded.sqljdbcschool.domain.generator.Generator;
import ua.com.foxminded.sqljdbcschool.domain.generator.GroupGenerator;
import ua.com.foxminded.sqljdbcschool.domain.generator.StudentGenerator;

public class Facade {
    public void prepareBase() throws DomainException {

        StartUpDao startUpDao = new StartUpDao();
        try {
            startUpDao.deleteTables();
            startUpDao.createTables("create_all_tables.sql");
        } catch (DAOException e) {
            throw new DomainException("Can't delete and create tables", e);
        }
        Generator groupGenerator = new GroupGenerator();
        groupGenerator.generate(10);

        Generator courseGenerator = new CourseGenerator();
        courseGenerator.generate(10);

        Generator studentGenerator = new StudentGenerator();
        studentGenerator.generate(200);
        
        StudentCourseDao studentCourseDao = new StudentCourseDao();
        try {
            studentCourseDao.createTable();
        } catch (DAOException e) {
            throw new DomainException("Can't create table students_courses", e);
        }

        System.out.println("OK");
    }

    

}
