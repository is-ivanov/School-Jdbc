package ua.com.foxminded.sqljdbcschool.ui.menuitem;

import java.util.List;
import java.util.Scanner;

import ua.com.foxminded.sqljdbcschool.domain.service.GroupService;
import ua.com.foxminded.sqljdbcschool.entity.Group;

@SuppressWarnings("java:S106")
public class MenuItemFindGroupsLessStudentsCount extends MenuItem {
    private static final String INPUT_STUDENT_COUNT = "Input student count: ";

    private Scanner scanner;
    private GroupService service;

    public MenuItemFindGroupsLessStudentsCount(String name,
            GroupService service, Scanner scanner) {
        super(name);
        this.service = service;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        int studentCount = inputStudentCount();
        List<Group> groups = service
                .getGroupsWithLessEqualsStudentCount(studentCount);
        groups.stream().forEach(System.out::println);
    }

    private int inputStudentCount() {
        int studentCount = 0;
        System.out.print(INPUT_STUDENT_COUNT);
        while (scanner.hasNext()) {
            studentCount = Integer.parseInt(scanner.nextLine());
            break;
        }
        return studentCount;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result
                + ((this.service == null) ? 0 : this.service.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        MenuItemFindGroupsLessStudentsCount other = (MenuItemFindGroupsLessStudentsCount) obj;
        if (this.service == null) {
            if (other.service != null)
                return false;
        } else if (!this.service.equals(other.service))
            return false;
        return true;
    }

}
