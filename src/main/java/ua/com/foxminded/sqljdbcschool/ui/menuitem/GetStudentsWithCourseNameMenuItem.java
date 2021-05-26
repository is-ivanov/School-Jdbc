package ua.com.foxminded.sqljdbcschool.ui.menuitem;

import java.util.List;
import java.util.Scanner;

import ua.com.foxminded.sqljdbcschool.domain.service.StudentService;
import ua.com.foxminded.sqljdbcschool.entity.Student;

@SuppressWarnings("java:S106")
public class GetStudentsWithCourseNameMenuItem extends MenuItem {
    private static final String MESSAGE_INPUT_COURSE_NAME = "Input course name: ";
    private StudentService service;
    private Scanner scanner;

    public GetStudentsWithCourseNameMenuItem(String name,
            StudentService service, Scanner scanner) {
        super(name);
        this.scanner = scanner;
        this.service = service;
    }

    @Override
    public void execute() {
        System.out.print(MESSAGE_INPUT_COURSE_NAME);
        String courseName = scanner.nextLine();
        List<Student> students = service.getStudentsWithCourseName(courseName);
        students.stream().forEach(System.out::println);
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
        GetStudentsWithCourseNameMenuItem other = (GetStudentsWithCourseNameMenuItem) obj;
        if (this.service == null) {
            if (other.service != null)
                return false;
        } else if (!this.service.equals(other.service))
            return false;
        return true;
    }

}
