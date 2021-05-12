package ua.com.foxminded.sqljdbcschool.ui;

import java.util.Scanner;

import ua.com.foxminded.sqljdbcschool.dao.CourseDao;
import ua.com.foxminded.sqljdbcschool.dao.GroupDao;
import ua.com.foxminded.sqljdbcschool.dao.StudentDao;
import ua.com.foxminded.sqljdbcschool.domain.service.CourseService;
import ua.com.foxminded.sqljdbcschool.domain.service.GroupService;
import ua.com.foxminded.sqljdbcschool.domain.service.StudentService;
import ua.com.foxminded.sqljdbcschool.ui.menuitem.MenuItemAddNewStudent;
import ua.com.foxminded.sqljdbcschool.ui.menuitem.MenuItemAddStudentToCourse;
import ua.com.foxminded.sqljdbcschool.ui.menuitem.MenuItemDeleteStudentById;
import ua.com.foxminded.sqljdbcschool.ui.menuitem.MenuItemFindGroupsLessStudentsCount;
import ua.com.foxminded.sqljdbcschool.ui.menuitem.MenuItemGetStudentsWithCourseName;
import ua.com.foxminded.sqljdbcschool.ui.menuitem.MenuItemRemoveStudentFromCourse;

@SuppressWarnings("java:S106")
public class MenuStart {

    public void startMenu() {
        Scanner scanner = new Scanner(System.in);

        Menu menu = new Menu(scanner);

        GroupDao groupDao = new GroupDao();
        StudentDao studentDao = new StudentDao();
        CourseDao courseDao = new CourseDao();

        GroupService groupService = new GroupService(groupDao);
        StudentService studentService = new StudentService(studentDao);
        CourseService courseService = new CourseService(courseDao);

        MenuItemFindGroupsLessStudentsCount menuItem1 = new MenuItemFindGroupsLessStudentsCount(
                "Find all groups with less or equals student count",
                groupService, scanner);
        menu.addMenuItem(1, menuItem1);

        MenuItemGetStudentsWithCourseName menuItem2 = new MenuItemGetStudentsWithCourseName(
                "Find all students related to course with given name",
                studentService, scanner);
        menu.addMenuItem(2, menuItem2);

        MenuItemAddNewStudent menuItem3 = new MenuItemAddNewStudent(
                "Add new student", studentService, scanner);
        menu.addMenuItem(3, menuItem3);

        MenuItemDeleteStudentById menuItem4 = new MenuItemDeleteStudentById(
                "Delete student by STUDENT_ID", studentService, scanner);
        menu.addMenuItem(4, menuItem4);

        MenuItemAddStudentToCourse menuItem5 = new MenuItemAddStudentToCourse(
                "Add a student to the course (from a list)", courseService,
                studentService, scanner);
        menu.addMenuItem(5, menuItem5);

        MenuItemRemoveStudentFromCourse menuItem6 = new MenuItemRemoveStudentFromCourse(
                "Remove the student from one of his or her courses",
                courseService, studentService, scanner);
        menu.addMenuItem(6, menuItem6);


        menu.initMenu();
        scanner.close();
    }

}
