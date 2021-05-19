package ua.com.foxminded.sqljdbcschool.domain.generator;

import java.util.List;
import java.util.stream.Collectors;

import ua.com.foxminded.sqljdbcschool.entity.Course;
import ua.com.foxminded.sqljdbcschool.reader.Reader;

public class CourseGenerator implements Generator<Course> {
    private static final String FILENAME_COURSES_DATA = "courses.txt";
    private static final String DELIMITER = ",";
    
    public List<Course> generate(int number) {

        Reader reader = new Reader();

        List<String> lines = reader.readTxtDataFiles(FILENAME_COURSES_DATA);

        return lines.stream()
                .map(line -> line.split(DELIMITER))
                .limit(number)
                .map(arr -> new Course(arr[0], arr[1]))
                .collect(Collectors.toList());
    }
}
