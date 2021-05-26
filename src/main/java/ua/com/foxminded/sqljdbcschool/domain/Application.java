package ua.com.foxminded.sqljdbcschool.domain;

import java.util.Random;

import ua.com.foxminded.sqljdbcschool.dao.StartUpDao;
import ua.com.foxminded.sqljdbcschool.dao.impl.CourseDaoImpl;
import ua.com.foxminded.sqljdbcschool.dao.impl.GroupDaoImpl;
import ua.com.foxminded.sqljdbcschool.dao.impl.StudentDaoImpl;
import ua.com.foxminded.sqljdbcschool.dao.interfaces.CourseDao;
import ua.com.foxminded.sqljdbcschool.dao.interfaces.GroupDao;
import ua.com.foxminded.sqljdbcschool.dao.interfaces.StudentDao;
import ua.com.foxminded.sqljdbcschool.domain.generator.CourseGenerator;
import ua.com.foxminded.sqljdbcschool.domain.generator.Generator;
import ua.com.foxminded.sqljdbcschool.domain.generator.GroupGenerator;
import ua.com.foxminded.sqljdbcschool.domain.generator.StudentGenerator;
import ua.com.foxminded.sqljdbcschool.domain.service.CourseService;
import ua.com.foxminded.sqljdbcschool.domain.service.GroupService;
import ua.com.foxminded.sqljdbcschool.domain.service.StudentService;
import ua.com.foxminded.sqljdbcschool.entity.Course;
import ua.com.foxminded.sqljdbcschool.entity.Group;
import ua.com.foxminded.sqljdbcschool.entity.Student;

public class Application {

    public static void main(String[] args) {

        StartUpDao startUpDao = new StartUpDao();
        Random random = new Random();

        GroupDao groupDao = new GroupDaoImpl();
        Generator<Group> groupGenerator = new GroupGenerator(random);
        GroupService groupService = new GroupService(groupDao, groupGenerator);

        CourseDao courseDao = new CourseDaoImpl();
        Generator<Course> courseGenerator = new CourseGenerator();
        CourseService courseService = new CourseService(courseDao,
                courseGenerator);

        StudentDao studentDao = new StudentDaoImpl();
        Generator<Student> studentGenerator = new StudentGenerator(random);
        StudentService studentService = new StudentService(studentDao,
                studentGenerator, random);


        Facade facade = new Facade(startUpDao, groupService,
                courseService, studentService);
        
        facade.prepareBase();
        facade.workWithBase();

    }

}
