package ua.com.foxminded.sqljdbcschool.domain.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ua.com.foxminded.sqljdbcschool.dao.Dao;
import ua.com.foxminded.sqljdbcschool.dao.GroupDao;
import ua.com.foxminded.sqljdbcschool.entity.Group;
import ua.com.foxminded.sqljdbcschool.exception.DAOException;
import ua.com.foxminded.sqljdbcschool.exception.DomainException;

public class GroupGenerator implements Generator {
    private static final String MESSAGE_MASK_EXCEPTION = "Don't save group %s in base";
    private static final String GROUP_DELIMITER = "-";

    private Random random = new Random();

    public void generate(int numberGroups) {
        List<Group> groups = new ArrayList<>();
        for (int i = 0; i < numberGroups; i++) {
            Group group = new Group(generateName());
            groups.add(group);
        }
        saveInBase(groups);
    }

    private String generateName() {
        int leftLimit = 65;
        int rightLimit = 90;
        int targetStringLength = 2;

        String leftPartName = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new,
                        StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        int rightPartName = random.nextInt(90) + 10;

        return leftPartName + GROUP_DELIMITER + rightPartName;

    }

    private void saveInBase(List<Group> groups) {
        Dao<Group> groupDao = new GroupDao();
        groups.stream().forEach(group -> {
            try {
                groupDao.add(group);
            } catch (DAOException e) {
                throw new DomainException(
                        String.format(MESSAGE_MASK_EXCEPTION, group), e);
            }
        });
    }

}
