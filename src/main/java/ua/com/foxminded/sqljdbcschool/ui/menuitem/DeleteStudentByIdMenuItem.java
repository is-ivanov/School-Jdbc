package ua.com.foxminded.sqljdbcschool.ui.menuitem;

import java.util.Scanner;

import ua.com.foxminded.sqljdbcschool.domain.service.StudentService;

@SuppressWarnings("java:S106")
public class DeleteStudentByIdMenuItem extends MenuItem {
    private static final String MESSAGE_INPUT_STUDENT_ID = "Input student_id for deleting: ";
    private static final String MASK_MESSAGE_DELETE_STUDENT = "Student with id %d is deleted";
    private static final String MESSAGE_EXCEPTION_NOT_NUMBER = "You inputted not a number. Please input number ";
    
    private StudentService service;
    private Scanner scanner;

    public DeleteStudentByIdMenuItem(String name, StudentService service,
            Scanner scanner) {
        super(name);
        this.service = service;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        int studentId = inputStudentId();
        service.deleteById(studentId);
        System.out
                .println(String.format(MASK_MESSAGE_DELETE_STUDENT, studentId));
    }

    private int inputStudentId() {
        int result = -1;
        do{
            System.out.print(MESSAGE_INPUT_STUDENT_ID);
            try {
                result = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print(MESSAGE_EXCEPTION_NOT_NUMBER);
            }
        } while(result == -1);
        return result;
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
        DeleteStudentByIdMenuItem other = (DeleteStudentByIdMenuItem) obj;
        if (this.service == null) {
            if (other.service != null)
                return false;
        } else if (!this.service.equals(other.service))
            return false;
        return true;
    }

}
