package ua.com.foxminded.sqljdbcschool.ui.menuitem;

import java.util.List;
import java.util.Scanner;

import ua.com.foxminded.sqljdbcschool.domain.service.CourseService;
import ua.com.foxminded.sqljdbcschool.domain.service.StudentService;
import ua.com.foxminded.sqljdbcschool.entity.Course;

@SuppressWarnings("java:S106")
public class RemoveStudentFromCourseMenuItem extends MenuItem {
    private static final String MESSAGE_INPUT_COURSE_ID = "Input course_id from list courses for removing student: ";
    private static final String MESSAGE_INPUT_STUDENT_ID = "Input student_id for remove from course: ";
    private static final String MASK_MESSAGE_REMOVE_STUDENT_COURSE = "Student %d deleted from course %d";
    
    private CourseService courseService;
    private StudentService studentService;
    private Scanner scanner;

    public RemoveStudentFromCourseMenuItem(String name,
            CourseService courseService, StudentService studentService,
            Scanner scanner) {
        super(name);
        this.courseService = courseService;
        this.studentService = studentService;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.print(MESSAGE_INPUT_STUDENT_ID);
        int studentId = Integer.parseInt(scanner.nextLine());
        List<Course> courses = courseService.getCoursesForStudent(studentId);
        courses.stream().forEach(System.out::println);

        System.out.print(MESSAGE_INPUT_COURSE_ID);
        int courseId = Integer.parseInt(scanner.nextLine());
        studentService.removeStudentFromCourse(studentId, courseId);
        System.out.println(
                String.format(MASK_MESSAGE_REMOVE_STUDENT_COURSE,
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
        RemoveStudentFromCourseMenuItem other = (RemoveStudentFromCourseMenuItem) obj;
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
