package ua.com.foxminded.sqljdbcschool.domain;

import ua.com.foxminded.sqljdbcschool.dao.Dao;
import ua.com.foxminded.sqljdbcschool.dao.GroupDao;
import ua.com.foxminded.sqljdbcschool.dao.StartUpDao;
import ua.com.foxminded.sqljdbcschool.domain.generator.CourseGenerator;
import ua.com.foxminded.sqljdbcschool.domain.generator.Generator;
import ua.com.foxminded.sqljdbcschool.domain.generator.GroupGenerator;
import ua.com.foxminded.sqljdbcschool.domain.generator.StudentCourseGenerator;
import ua.com.foxminded.sqljdbcschool.domain.generator.StudentGenerator;
import ua.com.foxminded.sqljdbcschool.entity.Group;
import ua.com.foxminded.sqljdbcschool.ui.MenuStarter;

public class Application {

    public static void main(String[] args) {

        StartUpDao startUpDao = new StartUpDao();
        Dao<Group> groupDao = new GroupDao();
        Generator groupGenerator = new GroupGenerator(groupDao);
        Generator courseGenerator = new CourseGenerator();
        Generator studentGenerator = new StudentGenerator();
        Generator studentCourseGenerator = new StudentCourseGenerator();
        MenuStarter menuStarter = new MenuStarter();

        Facade facade = new Facade(startUpDao, groupGenerator, courseGenerator,
                studentGenerator, studentCourseGenerator, menuStarter);
        facade.prepareBase();
        facade.workWithBase();

    }

}
