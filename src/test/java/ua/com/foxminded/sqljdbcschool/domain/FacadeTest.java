package ua.com.foxminded.sqljdbcschool.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;

import ua.com.foxminded.sqljdbcschool.dao.StartUpDao;
import ua.com.foxminded.sqljdbcschool.domain.generator.Generator;
import ua.com.foxminded.sqljdbcschool.exception.DAOException;
import ua.com.foxminded.sqljdbcschool.ui.Menu;

@ExtendWith(MockitoExtension.class)
class FacadeTest {

    private Facade facade;

    @Mock
    private StartUpDao startUpDao;
    @Mock
    private Generator groupGenerator;
    @Mock
    private Generator courseGenerator;
    @Mock
    private Generator studenGenerator;
    @Mock
    private Generator studentCourseGenerator;
    @Mock
    private Menu menu;

    @BeforeEach
    void setUp() throws Exception {
        facade = new Facade();

    }

    @Nested
    @DisplayName("teating prepareBase method")
    class testPrepareBase {
        private static final int NUMBER_STUDENTS = 200;
        private static final int NUMBER_COURSES = 10;
        private static final int NUMBER_GROUPS = 10;
        
        @Test
        @DisplayName("test order call. should first call startupDao, then groupGenerator, courseGenerator, studentGenerator")
        void testOrderCallsObjects() throws DAOException {
            facade.prepareBase();
            InOrder inOrder = Mockito.inOrder(startUpDao, groupGenerator, courseGenerator, studenGenerator, studentCourseGenerator);
            
            inOrder.verify(startUpDao).prepareTables();
            inOrder.verify(groupGenerator).generate(NUMBER_GROUPS);
            inOrder.verify(courseGenerator).generate(NUMBER_COURSES);
            inOrder.verify(studenGenerator).generate(NUMBER_STUDENTS);
            inOrder.verify(studentCourseGenerator).generate(NUMBER_STUDENTS);
            
        }
    }

}
