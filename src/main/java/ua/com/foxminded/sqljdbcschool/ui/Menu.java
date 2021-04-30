package ua.com.foxminded.sqljdbcschool.ui;

import java.util.Properties;
import java.util.Scanner;

import ua.com.foxminded.sqljdbcschool.domain.GroupService;
import ua.com.foxminded.sqljdbcschool.reader.Reader;


@SuppressWarnings("java:S106")
public class Menu {
    private static final String FILENAME_MENU_PROPERTIES = "menu.properties";
    private static final String MENU_FIRST_LEVEL = "menu.level1";
    private static final String MENU_SECOND_LEVEL_CHOOSE1 = "menu.level2.1";
    private static final String MENU_SECOND_LEVEL_CHOOSE2 = "menu.level2.2";

    private Reader reader = new Reader();
    private Properties propMenu = reader
            .readProperties(FILENAME_MENU_PROPERTIES);

    Scanner input = new Scanner(System.in);

    public void startFirstLevelMenu() {
        int selection;
        String menuFirstLevel = propMenu.getProperty(MENU_FIRST_LEVEL);
        System.out.println(menuFirstLevel);
        do {
            selection = input.nextInt();
            if (selection == 1) {
                startSecondLevelMenuForChoose1();
            }
            if (selection == 2) {
                startSecondLevelMenuForChoose1();
            }
            if (selection == 3) {
                startSecondLevelMenuForChoose1();
            }
            if (selection == 4) {
                startSecondLevelMenuForChoose1();
            }
            if (selection == 5) {
                startSecondLevelMenuForChoose1();
            }
            if (selection == 6) {
                startSecondLevelMenuForChoose1();
            }
        } while (selection == 0);
        input.close();
    }

    private void startSecondLevelMenuForChoose1() {
        String menuSecondlevelChoose = propMenu
                .getProperty(MENU_SECOND_LEVEL_CHOOSE1);
        System.out.println(menuSecondlevelChoose);
        int studentCount = input.nextInt();
        GroupService service = new GroupService();
        service.findGroupsWithLessEqualsStudentCount(studentCount);
    }
    
    private void startSecondLevelMenuForChoose2() {
        String menuSecondlevelChoose = propMenu
                .getProperty(MENU_SECOND_LEVEL_CHOOSE1);
        System.out.println(menuSecondlevelChoose);
        int studentCount = input.nextInt();
        GroupService service = new GroupService();
        service.findGroupsWithLessEqualsStudentCount(studentCount);
    }
}
