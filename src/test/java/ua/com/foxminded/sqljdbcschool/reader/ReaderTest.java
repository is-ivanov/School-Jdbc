package ua.com.foxminded.sqljdbcschool.reader;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ReaderTest {
    private static final String MESSAGE_FILENAME_IS_NULL = "Filename is null";
    private static final String FILENAME_MISSING_FILE = "missing file";
    private static final String MESSAGE_EXCEPTION_FILE_NOT_FOUND = "File \"missing file\" not found";
    private static final String FILENAME_PROPERTY_FILE = "db.properties";
    private static final String PROPERTY_DB_LOGIN = "db.login";
    private static final String EXPECTED_LOGIN = "karyama";

    private Reader reader;

    @BeforeEach
    void setUp() throws Exception {
        reader = new Reader();
    }

    @Nested
    @DisplayName("test readProperties method")
    class testReadPropperties {

        @Test
        @DisplayName("test null input string have to return IllegalArgumentException")
        void testNullInputString() {
            Exception exception = assertThrows(IllegalArgumentException.class,
                    () -> reader.readProperties(null));
            assertEquals(MESSAGE_FILENAME_IS_NULL, exception.getMessage());
        }

        @Test
        @DisplayName("test input missing file have to return IllegalArgumentException")
        void testInputMissingFile() {
            Exception exception = assertThrows(IllegalArgumentException.class,
                    () -> reader.readProperties(FILENAME_MISSING_FILE));
            assertEquals(MESSAGE_EXCEPTION_FILE_NOT_FOUND,
                    exception.getMessage());
        }

        @Test
        @DisplayName("test normal input propertyFile have to return expected values of properties")
        void testNormalInputPropertyFile() {
            Properties propDb = reader.readProperties(FILENAME_PROPERTY_FILE);
            String expectedDbLogin = EXPECTED_LOGIN;
            String actualDbLogin = propDb.getProperty(PROPERTY_DB_LOGIN);
            assertEquals(expectedDbLogin, actualDbLogin);
        }

    }

    @Nested
    @DisplayName("test readTxtDataFiles method")
    class testreadTxtDataFiles {

        @Test
        @DisplayName("test null input string have to return IllegalArgumentException")
        void testNullInputString() {
            Exception exception = assertThrows(IllegalArgumentException.class,
                    () -> reader.readTxtDataFiles(null));
            assertEquals(MESSAGE_FILENAME_IS_NULL, exception.getMessage());
        }

        @Test
        @DisplayName("test input missing file have to return IllegalArgumentException")
        void testInputMissingFile() {
            Exception exception = assertThrows(IllegalArgumentException.class,
                    () -> reader.readTxtDataFiles(FILENAME_MISSING_FILE));
            assertEquals(MESSAGE_EXCEPTION_FILE_NOT_FOUND,
                    exception.getMessage());
        }

        @Test
        @DisplayName("test normal input student_first_names have to return expected List<String>")
        void testNormalInputTxtFile() {
            String[] firstNames = {"Ivan", "Peter", "John"};
            List<String> expectedFirstNames = Arrays.asList(firstNames);
            List<String> actualFirstNames = reader.readTxtDataFiles("student_first_names.txt");
            assertEquals(expectedFirstNames, actualFirstNames);
        }

    }

}
