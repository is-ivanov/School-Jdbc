package ua.com.foxminded.sqljdbcschool.domain.generator;

import java.util.List;
import java.util.stream.Collectors;

import ua.com.foxminded.sqljdbcschool.dao.interfaces.Dao;
import ua.com.foxminded.sqljdbcschool.entity.Course;
import ua.com.foxminded.sqljdbcschool.exception.DAOException;
import ua.com.foxminded.sqljdbcschool.exception.DomainException;
import ua.com.foxminded.sqljdbcschool.reader.Reader;

public class CourseGenerator implements Generator {
    private static final String FILENAME_COURSES_DATA = "courses.txt";
    private static final String DELIMITER = ",";
    private static final String MESSAGE_MASK_EXCEPTION = "Don't save course %s in base";

    private Dao<Course> courseDao;

    public CourseGenerator(Dao<Course> courseDao) {
        this.courseDao = courseDao;
    }

    public void generate(int number) {

        Reader reader = new Reader();

        List<String> lines = reader.readTxtDataFiles(FILENAME_COURSES_DATA);

        List<Course> courses = lines.stream()
                .map(line -> line.split(DELIMITER))
                .limit(number)
                .map(arr -> new Course(arr[0], arr[1]))
                .collect(Collectors.toList());

        saveInBase(courses);
    }

    private void saveInBase(List<Course> courses) {
        courses.stream().forEach(course -> {
            try {
                courseDao.add(course);
            } catch (DAOException e) {
                throw new DomainException(
                        String.format(MESSAGE_MASK_EXCEPTION, course), e);
            }
        });
    }
}
