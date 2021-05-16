package ua.com.foxminded.sqljdbcschool.domain;

import java.util.Random;

import ua.com.foxminded.sqljdbcschool.dao.CourseDao;
import ua.com.foxminded.sqljdbcschool.dao.Dao;
import ua.com.foxminded.sqljdbcschool.dao.GroupDao;
import ua.com.foxminded.sqljdbcschool.dao.StartUpDao;
import ua.com.foxminded.sqljdbcschool.dao.StudentDao;
import ua.com.foxminded.sqljdbcschool.domain.generator.CourseGenerator;
import ua.com.foxminded.sqljdbcschool.domain.generator.Generator;
import ua.com.foxminded.sqljdbcschool.domain.generator.GroupGenerator;
import ua.com.foxminded.sqljdbcschool.domain.generator.StudentCourseGenerator;
import ua.com.foxminded.sqljdbcschool.domain.generator.StudentGenerator;
import ua.com.foxminded.sqljdbcschool.entity.Course;
import ua.com.foxminded.sqljdbcschool.entity.Group;
import ua.com.foxminded.sqljdbcschool.entity.Student;
import ua.com.foxminded.sqljdbcschool.ui.MenuStarter;

public class Application {

    public static void main(String[] args) {

        StartUpDao startUpDao = new StartUpDao();
        Dao<Group> groupDao = new GroupDao();
        Dao<Student> studentDao = new StudentDao();
        Dao<Course> courseDao = new CourseDao();
        StudentDao studentCourseDao = new StudentDao(); 
        Random random = new Random();
        Generator groupGenerator = new GroupGenerator(groupDao, random);
        Generator courseGenerator = new CourseGenerator(courseDao);
        Generator studentGenerator = new StudentGenerator(studentDao, random);
        Generator studentCourseGenerator = new StudentCourseGenerator(
                studentCourseDao, random);
        MenuStarter menuStarter = new MenuStarter();

        Facade facade = new Facade(startUpDao, groupGenerator, courseGenerator,
                studentGenerator, studentCourseGenerator, menuStarter);
        facade.prepareBase();
        facade.workWithBase();

    }

}
