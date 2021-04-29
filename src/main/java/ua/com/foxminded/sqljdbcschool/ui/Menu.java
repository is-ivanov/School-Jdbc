package ua.com.foxminded.sqljdbcschool.ui;

import java.util.Properties;
import java.util.Scanner;

import ua.com.foxminded.sqljdbcschool.reader.Reader;

public class Menu {
    private static final String FILENAME_MENU_PROPERTIES = "menu.properties";

    private Reader reader = new Reader();
    private Properties propMenu = reader.readProperties(FILENAME_MENU_PROPERTIES);

    public int menu() {
        int selection;
        String menuFirstLevel = propMenu.getProperty("menu.level1");
        System.out.println(menuFirstLevel);
        Scanner input = new Scanner(System.in);
        selection = input.nextInt();
        input.close();
        
        
        return selection;
    }
}
