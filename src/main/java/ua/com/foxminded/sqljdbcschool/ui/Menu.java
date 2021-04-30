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

    private Reader reader = new Reader();
    private Properties propMenu = reader
            .readProperties(FILENAME_MENU_PROPERTIES);

    Scanner input = new Scanner(System.in);

    public void startFirstLevelMenu() {
        int selection;
        String menuFirstLevel = propMenu.getProperty(MENU_FIRST_LEVEL);
        System.out.println(menuFirstLevel);
        selection = input.nextInt();
//        input.close();
        if (selection == 1) {
            startSecondLevelMenuForChoose1();

        }

    }

    private void startSecondLevelMenuForChoose1() {
        String menuSecondlevelChoose1 = propMenu
                .getProperty(MENU_SECOND_LEVEL_CHOOSE1);
        System.out.println(menuSecondlevelChoose1);
//        Scanner input = new Scanner(System.in);
        int studentCount = input.nextInt();
        input.close();
        GroupService service = new GroupService();
        service.findGroupsWithLessEqualsStudentCount(studentCount);
    }
}
