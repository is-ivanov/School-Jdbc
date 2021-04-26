package ua.com.foxminded.sqljdbcschool.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ua.com.foxminded.sqljdbcschool.dao.DAOException;
import ua.com.foxminded.sqljdbcschool.dao.GroupDao;
import ua.com.foxminded.sqljdbcschool.entity.Group;

public class GroupGenerator {
    private static final String GROUP_DELIMITER = "-";
    private static final String MESSAGE_IN_BASE = " in base";
    
    private Random random = new Random();

    public void generateGroups(int numberGroups) {
        List<Group> groups = new ArrayList<>();
        for (int i = 0; i < numberGroups; i++) {
            Group group = new Group(i + 1, generateNameGroup());
            groups.add(group);
        }
        saveGroupsInBase(groups);
    }

    private String generateNameGroup() {
        int leftLimit = 65;
        int rightLimit = 90;
        int targetStringLength = 2;

        String leftPartName = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength).collect(StringBuilder::new,
                        StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        int rightPartName = random.nextInt(90) + 10;

        return leftPartName + GROUP_DELIMITER + rightPartName;

    }
    
    private void saveGroupsInBase(List<Group> groups) {
        GroupDao groupDao = new GroupDao();
        groups.stream().forEach(group -> {
            try {
                groupDao.add(group);
            } catch (DAOException e) {
                throw new DomainException(
                        "Don't save group " + group + MESSAGE_IN_BASE, e);
            }
        });
    }

}
