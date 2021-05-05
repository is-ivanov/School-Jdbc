package ua.com.foxminded.sqljdbcschool.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import ua.com.foxminded.sqljdbcschool.exception.UIException;

//example menu https://github.com/bryandh/genericmenu

@SuppressWarnings("java:S106")
public class NewMenu {
    private List<MenuItem> menuItems;
    private Scanner scanner;

    public NewMenu(List<MenuItem> menuItems, Scanner scanner) {
        this.menuItems = new ArrayList<>();
        this.scanner = new Scanner(System.in);
    }

    private void addMenuItem(String key, String name) {
        MenuItem menuItem = new MenuItem(key, name);
        menuItems.add(menuItem);
    }

    public void addMenuItem(String key, String name, Runnable runnable) {
        MenuItem menuItem = new MenuItem(key, name, runnable);
        menuItems.add(menuItem);
    }

    private void printMenuItems() {
        menuItems.forEach((a) -> System.out
                .println("[" + a.getKey() + "]" + a.getName()));
    }

    private void runCommand(String key) throws UIException {
        List<MenuItem> filteredMenuItems = menuItems.stream()
                .filter(a -> a.getKey().toLowerCase().equals(key))
                .collect(Collectors.toList());
        if (filteredMenuItems.isEmpty()) {
            filteredMenuItems.stream().forEach(a -> a.getRunnable().run());
        } else {
            throw new UIException(
                    "No valid option for '" + key + "' found, try again.");
        }
    }

    private String scanLine() {
        System.out.print("> ");
        return scanner.nextLine();
    }

    private void addDefaultItems() {
        addMenuItem("0", "Quit");
    }

    public void initMenu() {
        addDefaultItems();

        Boolean quit = false;

        while (!quit) {
            System.out.println("Choose queries from this choices");
            printMenuItems();
            String choice = scanLine();
            System.out.println("\nEntered " + choice);
            choice = choice.toLowerCase();
            try {
                if (choice.equals("0")) {
                    System.out.println("Quitting application...");
                    quit = true;
                } else {
                    runCommand(choice);
                }
            } catch (UIException e) {
                e.printStackTrace();
            }
            
            System.out.println();
        }
    }
}
