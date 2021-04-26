package ua.com.foxminded.sqljdbcschool.domain;

import ua.com.foxminded.sqljdbcschool.dao.DAOException;
import ua.com.foxminded.sqljdbcschool.dao.StartUpDao;
import ua.com.foxminded.sqljdbcschool.dao.StudentCourseDao;

public class Facade {
    public void prepareBase() throws DomainException {

        StartUpDao startUpDao = new StartUpDao();
        try {
            startUpDao.deleteTables();
            startUpDao.createTables("create_all_tables.sql");
        } catch (DAOException e) {
            throw new DomainException("Can't delete and create tables", e);
        }
        GroupGenerator groupGenerator = new GroupGenerator();
        groupGenerator.generateGroups(10);

        CourseGenerator courseGenerator = new CourseGenerator();
        courseGenerator.createCourses();

        StudentGenerator studentGenerator = new StudentGenerator();
        studentGenerator.generateStudents(200);
        
        StudentCourseDao studentCourseDao = new StudentCourseDao();
        try {
            studentCourseDao.createTable();
        } catch (DAOException e) {
            throw new DomainException("Can't create table students_courses", e);
        }

        System.out.println("OK");
    }

    

}
