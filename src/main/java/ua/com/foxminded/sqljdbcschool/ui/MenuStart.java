package ua.com.foxminded.sqljdbcschool.ui;

import java.util.List;
import java.util.Scanner;

import ua.com.foxminded.sqljdbcschool.dao.GroupDao;
import ua.com.foxminded.sqljdbcschool.domain.service.GroupService;
import ua.com.foxminded.sqljdbcschool.entity.Group;

@SuppressWarnings("java:S106")
public class MenuStart {

    public void startMenu() {

        NewMenu menu = new NewMenu();

        menu.addMenuItem("1",
                "Find all groups with less or equals student count",
                this::startSubMenuFindGroupsWithLessStudents);
        menu.addMenuItem("2",
                "Find all students related to course with given name", () ->

                {
                    System.out.println(
                            "Find all students related to course with given name");
                });
        menu.addMenuItem("3", "Add new student", () -> {
            System.out.println("Add new student");
        });
        menu.addMenuItem("4", "Delete student by STUDENT_ID", () -> {
            System.out.println("Delete student by STUDENT_ID");
        });
        menu.addMenuItem("5", "Add a student to the course (from a list)",
                () -> {
                    System.out.println(
                            "Add a student to the course (from a list)");
                });
        menu.addMenuItem("6",
                "Remove the student from one of his or her courses", () -> {
                    System.out.println(
                            "Remove the student from one of his or her courses");
                });

        menu.initMenu();
    }

    private void startSubMenuFindGroupsWithLessStudents() {
        System.out.print("Input student count:");
        Scanner input = new Scanner(System.in);
        int studentCount = Integer.parseInt(input.nextLine());
        input.close();
        GroupDao groupDao = new GroupDao();
        GroupService service = new GroupService(groupDao);
        List<Group> groups = service
                .getGroupsWithLessEqualsStudentCount(studentCount);
        groups.stream().forEach(System.out::println);

    }

}
