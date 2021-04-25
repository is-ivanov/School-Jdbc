package ua.com.foxminded.sqljdbcschool.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ua.com.foxminded.sqljdbcschool.entity.Student;

public class StudentGenerator {
    private Random random = new Random();

    public List<Student> generateStudents(int numberStudents) {
        List<Student> students = new ArrayList<>();
        for (int i = 0; i < numberStudents; i++) {
            String[] names = createStudentNames();
            Student student = new Student(i + 1, names[0], names[1]);
            students.add(student);
        }

        return splitStudents(students);
    }

    private String[] createStudentNames() {
        String[] firstNames = { "Ivan", "Peter", "John", "Emily", "Linda",
                "Julia", "Alex", "Irina", "Tom", "Patricia", "Bob", "Stephanie",
                "Aaron", "Tatyana", "Konstantin", "Olga", "Jim", "Samantha",
                "Tim", "Meryl" };
        String[] lastNames = { "Ivanov", "McQuin", "Lennon", "Ratakovski",
                "Hamilton", "Roberts", "Smith", "Collins", "Brady", "Kaas",
                "Marley", "Seymour", "Rodgers", "Ant", "Johnson", "Korbut",
                "Carrey", "Fox", "Robbins", "Streep" };

        String[] studentNames = new String[2];
        studentNames[0] = firstNames[random.nextInt(20)];
        studentNames[1] = lastNames[random.nextInt(20)];
        return studentNames;
    }

    private List<Student> splitStudents(List<Student> students) {
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
        List<Integer> variantSizes = Stream.iterate(0, n -> n + 1)
                .limit(31)
                .filter(n -> n == 0 || n > 19)
                .collect(Collectors.toList());
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

}
