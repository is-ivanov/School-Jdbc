package ua.com.foxminded.sqljdbcschool.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class Reader {

    private static final String MASK_MESSAGE_FILE_NOT_FOUND = "File %s not found";

    public Properties readProperties(String filename) {
        Properties properties = new Properties();

        try (InputStream input = getClass().getClassLoader()
                .getResourceAsStream(filename)) {

            properties.load(input);
        } catch (IOException e) {
            System.err.println(
                    String.format(MASK_MESSAGE_FILE_NOT_FOUND, filename));

        }
        return properties;
    }

    public List<String> readTxtDataFiles(String filename) {
        List<String> fileContents = new ArrayList<>();
        try (InputStream inputStream = getClass().getClassLoader()
                .getResourceAsStream(filename);
                InputStreamReader inputStreamReader = new InputStreamReader(
                        inputStream);
                BufferedReader bufferedReader = new BufferedReader(
                        inputStreamReader);) {
            fileContents = bufferedReader.lines().collect(Collectors.toList());

        } catch (IOException | NullPointerException e) {
            System.err.println(
                    String.format(MASK_MESSAGE_FILE_NOT_FOUND, filename));
        }
        return fileContents;

    }
}
