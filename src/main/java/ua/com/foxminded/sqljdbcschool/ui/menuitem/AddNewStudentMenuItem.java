package ua.com.foxminded.sqljdbcschool.ui.menuitem;

import java.util.Scanner;

import ua.com.foxminded.sqljdbcschool.domain.service.StudentService;

@SuppressWarnings("java:S106")
public class AddNewStudentMenuItem extends MenuItem {
    private static final String MESSAGE_FIRST_NAME = "Input first name: ";
    private static final String MESSAGE_LAST_NAME = "Input last name: ";
    private static final String MASK_ADD_STUDENT_MESSAGE = "Student %s %s is created";

    private StudentService service;
    private Scanner scanner;

    public AddNewStudentMenuItem(String name, StudentService service,
            Scanner scanner) {
        super(name);
        this.service = service;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.print(MESSAGE_FIRST_NAME);
        String firstName = scanner.nextLine();
        System.out.print(MESSAGE_LAST_NAME);
        String lastName = scanner.nextLine();
        service.create(firstName, lastName);
        System.out.println(String.format(MASK_ADD_STUDENT_MESSAGE,
                firstName, lastName));
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
        AddNewStudentMenuItem other = (AddNewStudentMenuItem) obj;
        if (this.service == null) {
            if (other.service != null)
                return false;
        } else if (!this.service.equals(other.service))
            return false;
        return true;
    }

}
