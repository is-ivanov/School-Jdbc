package ua.com.foxminded.sqljdbcschool.reader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Reader {

    public Properties readProperties(String filename) {
        Properties properties = new Properties();
        
        try (InputStream input = getClass().getClassLoader()
                .getResourceAsStream(filename)) {
            
            properties.load(input);
        } catch (IOException e) {
            System.err
            .println(String.format("File %s not found", filename));
            
        }
        return properties;
    }
}
