package ua.com.foxminded.sqljdbcschool.ui;

import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import ua.com.foxminded.sqljdbcschool.domain.CourseService;
import ua.com.foxminded.sqljdbcschool.domain.GroupService;
import ua.com.foxminded.sqljdbcschool.domain.StudentServise;
import ua.com.foxminded.sqljdbcschool.entity.Course;
import ua.com.foxminded.sqljdbcschool.entity.Group;
import ua.com.foxminded.sqljdbcschool.entity.Student;
import ua.com.foxminded.sqljdbcschool.reader.Reader;

@SuppressWarnings("java:S106")
public class Menu {
    private static final String FILENAME_MENU_PROPERTIES = "menu.properties";
    private static final String MENU_FIRST_LEVEL = "menu.level1";
    private static final String MENU_SECOND_LEVEL_CHOOSE1 = "menu.level2.1";
    private static final String MENU_SECOND_LEVEL_CHOOSE2 = "menu.level2.2";
    private static final String MENU_SECOND_LEVEL_CHOOSE3_1 = "menu.level2.3.1";
    private static final String MENU_SECOND_LEVEL_CHOOSE3_2 = "menu.level2.3.2";
    private static final String MENU_SECOND_LEVEL_CHOOSE4 = "menu.level2.4";
    private static final String MENU_SECOND_LEVEL_CHOOSE5_1 = "menu.level2.5.1";
    private static final String MENU_SECOND_LEVEL_CHOOSE5_2 = "menu.level2.5.2";
    private static final String MENU_SECOND_LEVEL_CHOOSE6_1 = "menu.level2.6.1";
    private static final String MENU_SECOND_LEVEL_CHOOSE6_2 = "menu.level2.6.2";
    private static final String FORMAT_MASK_ADD_STUDENT_MESSAGE = "%s %s create";
    private static final String FORMAT_MASK_DELETE_STUDENT_MESSAGE = "Student with id %d deleted";
    private static final String FORMAT_MASK_ADD_STUDENT_COURSE_MESSAGE = "Student %d added to course %d";
    private static final String FORMAT_MASK_REMOVE_STUDENT_COURSE_MESSAGE = "Student %d deleted from course %d";

    private Reader reader = new Reader();
    private Properties propMenu = reader
            .readProperties(FILENAME_MENU_PROPERTIES);

    Scanner input = new Scanner(System.in);

    public void startMainMenu() {
        int selection;
        String menuFirstLevel = propMenu.getProperty(MENU_FIRST_LEVEL);
        do {
            System.out.println(menuFirstLevel);
            selection = Integer.parseInt(input.nextLine());
            if (selection == 1) {
                startSubMenuFindGroupsWithLessStudents();
            }
            if (selection == 2) {
                startSubMenuFindStudentsRelatedCourse();
            }
            if (selection == 3) {
                startSubMenuAddNewStudent();
            }
            if (selection == 4) {
                startSubMenuDeleteStudentById();
            }
            if (selection == 5) {
                startSubMenuAddStudentToCourse();
            }
            if (selection == 6) {
                startSubMenuRemoveStudentFromCourse();
            }
        } while (selection != 0);
        input.close();
    }

    private void startSubMenuFindGroupsWithLessStudents() {
        System.out.println(propMenu.getProperty(MENU_SECOND_LEVEL_CHOOSE1));
        int studentCount = Integer.parseInt(input.nextLine());
        GroupService service = new GroupService();
        List<Group> groups = service
                .getGroupsWithLessEqualsStudentCount(studentCount);
        groups.stream().forEach(System.out::println);

    }

    private void startSubMenuFindStudentsRelatedCourse() {
        System.out.println(propMenu.getProperty(MENU_SECOND_LEVEL_CHOOSE2));
        String courseName = input.nextLine();
        StudentServise service = new StudentServise();
        List<Student> students = service.getStudentsWithCourseName(courseName);
        students.stream().forEach(System.out::println);
    }

    private void startSubMenuAddNewStudent() {
        System.out.println(propMenu.getProperty(MENU_SECOND_LEVEL_CHOOSE3_1));
        String firstName = input.nextLine();
        System.out.println(propMenu.getProperty(MENU_SECOND_LEVEL_CHOOSE3_2));
        String lastName = input.nextLine();
        StudentServise service = new StudentServise();
        service.create(firstName, lastName);
        System.out.println(String.format(FORMAT_MASK_ADD_STUDENT_MESSAGE,
                firstName, lastName));
    }

    private void startSubMenuDeleteStudentById() {
        System.out.println(propMenu.getProperty(MENU_SECOND_LEVEL_CHOOSE4));
        int studentId = Integer.parseInt(input.nextLine());
        StudentServise servise = new StudentServise();
        servise.deleteById(studentId);
        System.out.println(
                String.format(FORMAT_MASK_DELETE_STUDENT_MESSAGE, studentId));
    }

    private void startSubMenuAddStudentToCourse() {
        System.out.println(propMenu.getProperty(MENU_SECOND_LEVEL_CHOOSE5_1));
        int studentId = Integer.parseInt(input.nextLine());

        CourseService courseService = new CourseService();
        List<Course> courses = courseService
                .getCoursesMissingForStudent(studentId);
        courses.stream().forEach(System.out::println);

        System.out.println(propMenu.getProperty(MENU_SECOND_LEVEL_CHOOSE5_2));
        int courseId = Integer.parseInt(input.nextLine());
        StudentServise studentServise = new StudentServise();
        studentServise.addStudentToCourse(studentId, courseId);
        System.out.println(String.format(FORMAT_MASK_ADD_STUDENT_COURSE_MESSAGE,
                studentId, courseId));

    }

    private void startSubMenuRemoveStudentFromCourse() {
        System.out.println(propMenu.getProperty(MENU_SECOND_LEVEL_CHOOSE6_1));
        int studentId = Integer.parseInt(input.nextLine());

        CourseService courseService = new CourseService();
        List<Course> courses = courseService.getCoursesForStudent(studentId);
        courses.stream().forEach(System.out::println);

        System.out.println(propMenu.getProperty(MENU_SECOND_LEVEL_CHOOSE6_2));
        int courseId = Integer.parseInt(input.nextLine());
        StudentServise studentServise = new StudentServise();
        studentServise.removeStudentFromCourse(studentId, courseId);
        System.out.println(
                String.format(FORMAT_MASK_REMOVE_STUDENT_COURSE_MESSAGE,
                        studentId, courseId));

    }

}
