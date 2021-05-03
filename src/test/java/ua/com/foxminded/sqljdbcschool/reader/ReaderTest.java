package ua.com.foxminded.sqljdbcschool.reader;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ReaderTest {
    private static final String MESSAGE_FILENAME_IS_NULL = "Filename is null";
    private static final String FILENAME_MISSING_FILE = "missing file";

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
        @DisplayName("test input missing file have to return IOException")
        void testInputMissingFile() {
            Exception exception = assertThrows(IOException.class,
                    () -> reader.readProperties(FILENAME_MISSING_FILE));
            assertEquals("", exception.getMessage());
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

    }

}
