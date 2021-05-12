package ua.com.foxminded.sqljdbcschool.ui.menuitem;

import java.util.List;
import java.util.Scanner;

import ua.com.foxminded.sqljdbcschool.domain.service.CourseService;
import ua.com.foxminded.sqljdbcschool.domain.service.StudentService;
import ua.com.foxminded.sqljdbcschool.entity.Course;

@SuppressWarnings("java:S106")
public class MenuItemAddStudentToCourse extends MenuItem {
    private static final String MASK_ADD_STUDENT_COURSE_MESSAGE = "Student %d added to course %d";

    private CourseService courseService;
    private StudentService studentService;
    private Scanner scanner;

    public MenuItemAddStudentToCourse(String name, CourseService courseService,
            StudentService studentService, Scanner scanner) {
        super(name);
        this.courseService = courseService;
        this.studentService = studentService;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.print("Input student_id for add to course: ");
        int studentId = Integer.parseInt(scanner.nextLine());
        List<Course> courses = courseService
                .getCoursesMissingForStudent(studentId);
        courses.stream().forEach(System.out::println);

        System.out.print(
                "Input course_id from list courses for adding student: ");
        int courseId = Integer.parseInt(scanner.nextLine());
        studentService.addStudentToCourse(studentId, courseId);
        System.out.println(String.format(MASK_ADD_STUDENT_COURSE_MESSAGE,
                studentId, courseId));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result
                + ((courseService == null) ? 0 : courseService.hashCode());
        result = prime * result
                + ((studentService == null) ? 0 : studentService.hashCode());
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
        MenuItemAddStudentToCourse other = (MenuItemAddStudentToCourse) obj;
        if (courseService == null) {
            if (other.courseService != null)
                return false;
        } else if (!courseService.equals(other.courseService))
            return false;
        if (studentService == null) {
            if (other.studentService != null)
                return false;
        } else if (!studentService.equals(other.studentService))
            return false;
        return true;
    }

}
