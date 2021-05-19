package ua.com.foxminded.sqljdbcschool.domain;

import java.util.Random;

import ua.com.foxminded.sqljdbcschool.dao.CourseDaoImpl;
import ua.com.foxminded.sqljdbcschool.dao.GroupDaoImpl;
import ua.com.foxminded.sqljdbcschool.dao.StartUpDao;
import ua.com.foxminded.sqljdbcschool.dao.StudentDaoImpl;
import ua.com.foxminded.sqljdbcschool.dao.interfaces.CourseDao;
import ua.com.foxminded.sqljdbcschool.dao.interfaces.GroupDao;
import ua.com.foxminded.sqljdbcschool.dao.interfaces.StudentDao;
import ua.com.foxminded.sqljdbcschool.domain.generator.CourseGenerator;
import ua.com.foxminded.sqljdbcschool.domain.generator.Generator;
import ua.com.foxminded.sqljdbcschool.domain.generator.GroupGenerator;
import ua.com.foxminded.sqljdbcschool.domain.generator.StudentGenerator;
import ua.com.foxminded.sqljdbcschool.domain.service.StudentCourseService;
import ua.com.foxminded.sqljdbcschool.ui.MenuStarter;

public class Application {

    public static void main(String[] args) {

        StartUpDao startUpDao = new StartUpDao();
        GroupDao groupDao = new GroupDaoImpl();
        StudentDao studentDao = new StudentDaoImpl();
        CourseDao courseDao = new CourseDaoImpl();
        Random random = new Random();
        Generator groupGenerator = new GroupGenerator(groupDao, random);
        Generator courseGenerator = new CourseGenerator(courseDao);
        Generator studentGenerator = new StudentGenerator(studentDao, random);
        Generator studentCourseGenerator = new StudentCourseService(
                studentCourseDao, random);
        MenuStarter menuStarter = new MenuStarter();

        Facade facade = new Facade(startUpDao, studentGenerator, studentCourseGenerator, menuStarter);
        facade.prepareBase();
        facade.workWithBase();

    }

}
