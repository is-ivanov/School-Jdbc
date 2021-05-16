//TODO
//package ua.com.foxminded.sqljdbcschool.domain;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Nested;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InOrder;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import ua.com.foxminded.sqljdbcschool.dao.StartUpDao;
//import ua.com.foxminded.sqljdbcschool.domain.generator.Generator;
//import ua.com.foxminded.sqljdbcschool.exception.DAOException;
//import ua.com.foxminded.sqljdbcschool.ui.Menu;
//
//@ExtendWith(MockitoExtension.class)
//class FacadeTest {
//    @Mock
//    private Facade facadeMock;
//
//    @Mock
//    private StartUpDao startUpDaoMock;
//    @Mock
//    private Generator groupGeneratorMock;
//    @Mock
//    private Generator courseGeneratorMock;
//    @Mock
//    private Generator studenGeneratorMock;
//    @Mock
//    private Generator studentCourseGeneratorMock;
//    @Mock
//    private Menu menuMock;
//
//    @BeforeEach
//    void setUp() throws Exception {
//        facadeMock = new Facade();
//
//    }
//
//    @Nested
//    @DisplayName("teating prepareBase method")
//    class testPrepareBase {
//        private static final int NUMBER_STUDENTS = 200;
//        private static final int NUMBER_COURSES = 10;
//        private static final int NUMBER_GROUPS = 10;
//        
//        @Test
//        @DisplayName("test order call. should first call startupDao, then groupGenerator, courseGenerator, studentGenerator")
//        void testOrderCallsObjects() throws DAOException {
//            
//            facadeMock.prepareBase();
//
//            InOrder inOrder = inOrder(startUpDaoMock, groupGeneratorMock, courseGeneratorMock, studenGeneratorMock, studentCourseGeneratorMock);
//            
//            inOrder.verify(startUpDaoMock).prepareTables();
//            inOrder.verify(groupGeneratorMock).generate(NUMBER_GROUPS);
//            inOrder.verify(courseGeneratorMock).generate(NUMBER_COURSES);
//            inOrder.verify(studenGeneratorMock).generate(NUMBER_STUDENTS);
//            inOrder.verify(studentCourseGeneratorMock).generate(NUMBER_STUDENTS);
//            
//        }
//    }
//
//}
