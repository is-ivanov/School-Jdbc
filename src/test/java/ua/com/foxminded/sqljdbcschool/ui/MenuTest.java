package ua.com.foxminded.sqljdbcschool.ui;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ua.com.foxminded.sqljdbcschool.domain.service.GroupService;

@ExtendWith(MockitoExtension.class)
class MenuTest {
    private Menu menu;
    private final InputStream systemIn = System.in;
    private ByteArrayInputStream testIn;
    
    @Mock
    GroupService groupService;
    
    @BeforeEach
    void setUp() throws Exception {
        menu = new Menu();
    }
    
    private void provideInput(String data) {
        testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }

    @AfterEach
    public void restoreSystemInput() {
        System.setIn(systemIn);
    }
    
    @Test
    void test() {
        provideInput("1\n25\n");
        
        menu.startMainMenu();
        
    }

}
