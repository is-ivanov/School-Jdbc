package ua.com.foxminded.sqljdbcschool.domain.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ua.com.foxminded.sqljdbcschool.dao.Dao;
import ua.com.foxminded.sqljdbcschool.dao.StudentDao;
import ua.com.foxminded.sqljdbcschool.entity.Student;
import ua.com.foxminded.sqljdbcschool.exception.DAOException;
import ua.com.foxminded.sqljdbcschool.exception.DomainException;
import ua.com.foxminded.sqljdbcschool.reader.Reader;

public class StudentGenerator implements Generator {
    private static final String FILENAME_FIRST_NAME_DATA = "student_first_names.txt";
    private static final String FILENAME_LAST_NAME_DATA = "student_last_names.txt";
    private static final String MESSAGE_MASK_EXCEPTION = "Don't save student %s in base";

    private Random random = new Random();

    public void generate(int numberStudents) {
        List<Student> students = new ArrayList<>();
        for (int i = 0; i < numberStudents; i++) {
            String[] names = createStudentNames();
            Student student = new Student( names[0], names[1]);
            students.add(student);
        }
        saveInBase(splitStudentsToGroups(students));
    }

    private String[] createStudentNames() {
        Reader reader = new Reader();
        List<String> firstNames = reader
                .readTxtDataFiles(FILENAME_FIRST_NAME_DATA);
        List<String> lastNames = reader
                .readTxtDataFiles(FILENAME_LAST_NAME_DATA);

        String[] studentNames = new String[2];
        studentNames[0] = firstNames.get(random.nextInt(20));
        studentNames[1] = lastNames.get(random.nextInt(20));

        return studentNames;
    }

    private List<Student> splitStudentsToGroups(List<Student> students) {
        int numberStudents = students.size();
        int[] sizeGroups = calculateSizeGroups(numberStudents);

        for (Student student : students) {
            for (int i = 0; i < sizeGroups.length; i++) {
                if (sizeGroups[i] != 0) {
                    student.setGroupId(i + 1);
                    sizeGroups[i]--;
                    break;
                }
            }
        }

        return students;
    }

    private int[] calculateSizeGroups(int numberStudents) {
        int[] numberStudentInGroups = new int[10];
        List<Integer> variantSizes = Stream.iterate(0, n -> n + 1).limit(31)
                .filter(n -> n == 0 || n > 19).collect(Collectors.toList());
        for (int i = 0; i < numberStudentInGroups.length; i++) {
            if (numberStudents > 10) {
                numberStudentInGroups[i] = variantSizes
                        .get(random.nextInt(variantSizes.size()));
            } else {
                numberStudentInGroups[i] = 0;
            }
            numberStudents -= numberStudentInGroups[i];
        }
        return numberStudentInGroups;
    }

    private void saveInBase(List<Student> students) {
        Dao<Student> studentDao = new StudentDao();
        students.stream().forEach(student -> {
            try {
                studentDao.add(student);
            } catch (DAOException e) {
                throw new DomainException(
                        String.format(MESSAGE_MASK_EXCEPTION, student), e);
            }
        });
    }
}
