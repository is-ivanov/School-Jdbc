package ua.com.foxminded.sqljdbcschool.ui;

import java.util.Scanner;

import ua.com.foxminded.sqljdbcschool.dao.CourseDaoImpl;
import ua.com.foxminded.sqljdbcschool.dao.GroupDaoImpl;
import ua.com.foxminded.sqljdbcschool.dao.StudentDaoImpl;
import ua.com.foxminded.sqljdbcschool.domain.service.CourseService;
import ua.com.foxminded.sqljdbcschool.domain.service.GroupService;
import ua.com.foxminded.sqljdbcschool.domain.service.StudentService;
import ua.com.foxminded.sqljdbcschool.ui.menuitem.AddNewStudentMenuItem;
import ua.com.foxminded.sqljdbcschool.ui.menuitem.AddStudentToCourseMenuItem;
import ua.com.foxminded.sqljdbcschool.ui.menuitem.DeleteStudentByIdMenuItem;
import ua.com.foxminded.sqljdbcschool.ui.menuitem.FindGroupsLessStudentsCountMenuItem;
import ua.com.foxminded.sqljdbcschool.ui.menuitem.GetStudentsWithCourseNameMenuItem;
import ua.com.foxminded.sqljdbcschool.ui.menuitem.RemoveStudentFromCourseMenuItem;

@SuppressWarnings("java:S106")
public class MenuStarter {

    public void startMenu() {
        Scanner scanner = new Scanner(System.in);

        Menu menu = new Menu(scanner);

        GroupDaoImpl groupDao = new GroupDaoImpl();
        StudentDaoImpl studentDao = new StudentDaoImpl();
        CourseDaoImpl courseDao = new CourseDaoImpl();

        GroupService groupService = new GroupService(groupDao);
        StudentService studentService = new StudentService(studentDao);
        CourseService courseService = new CourseService(courseDao);

        FindGroupsLessStudentsCountMenuItem menuItem1 = new FindGroupsLessStudentsCountMenuItem(
                "Find all groups with less or equals student count",
                groupService, scanner);
        menu.addMenuItem(1, menuItem1);

        GetStudentsWithCourseNameMenuItem menuItem2 = new GetStudentsWithCourseNameMenuItem(
                "Find all students related to course with given name",
                studentService, scanner);
        menu.addMenuItem(2, menuItem2);

        AddNewStudentMenuItem menuItem3 = new AddNewStudentMenuItem(
                "Add new student", studentService, scanner);
        menu.addMenuItem(3, menuItem3);

        DeleteStudentByIdMenuItem menuItem4 = new DeleteStudentByIdMenuItem(
                "Delete student by STUDENT_ID", studentService, scanner);
        menu.addMenuItem(4, menuItem4);

        AddStudentToCourseMenuItem menuItem5 = new AddStudentToCourseMenuItem(
                "Add a student to the course (from a list)", courseService,
                studentService, scanner);
        menu.addMenuItem(5, menuItem5);

        RemoveStudentFromCourseMenuItem menuItem6 = new RemoveStudentFromCourseMenuItem(
                "Remove the student from one of his or her courses",
                courseService, studentService, scanner);
        menu.addMenuItem(6, menuItem6);


        menu.initMenu();
        scanner.close();
    }

}
